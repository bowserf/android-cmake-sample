#  Add native library dependency in an Android application by using CMake

The first steps to use the Android NDK can be hard for an Android developer. We are used to code in Java/Kotlin, to compile with Gradle. To use the Android NDK, we need to manage C/C++ languages, use the JNI library to do the bridge between the Java layer and the C/CC+ layer and to compile with CMake.

Particularly, CMake tool can be pretty hard to learn and to use at the beginning.

CMake is a tool used to prepare the build process of a software. Its role is to find dependencies between composants of a project, to organize the build order between composants. It generates files with commands which will be used by other tools whose goal is to organize, at a lower level, the compilation of a project such as Make, Ninja, Apple's Xcode or Microsoft Visual Studio. 

In an introduction, we will see how to generate a configured Android project to build native code with Android Studio Wizard. Just after, we will see how to do it manually to understand what is exactly done.
After this introduction, the main goal of this article will be to explain how to add a dependency to your Android project when the dependency is:
- the source code of a library with an already CMakeLists configured to build it.
- a prebuilt `shared` library (.so).
- a prebuilt `static` library (.a).

## Easily configure an Android project to build C/C++ code by using Android Studio Wizard.

By using the Android Studio wizard, we can easily create an Android project configured to compile C/C++ code. For this:
- click on: `File -> New -> New Project`.
- select `Native C++`.
- fill information like for any other project.
- click on next.

There is a dedicated panel for C/C++ project where we have to select the toolchain, it means which version of C++ do we want to use.

Now, we have a configured Android project to compile native code.

Compare to a basic Android project, some C/C++ files has been added and there is a mysterious `CMakeLists.txt` file.

The next part explains how to manually do what have been done by Android Studio to understand the first CMake commands.

## Manually configure an Android module to build C/C++ code.

In this part, we will explain the basics to compile C/C++ with the use of CMake. If you already know the basics of CMake and how to build a basic Android project with it, you can skip this part.

To know how to configure the compilation of your code, CMake needs some information from us. These information are placed in a file called `CMakeLists.txt`.

Even if your application will contains native code, we still use Gradle to generate our APK. We need to give him the path to the CMakeLists.txt file to compile our native code:

In the `build.gradle` of your Android module, put:
```
android {
    ...
    externalNativeBuild {
        cmake {
            // define the path where the CMakeLists has been put for this module.
            path "src/main/cpp/CMakeLists.txt"
        }
    }
}
```

Now that Gradle knows where is our CMakeLists, we can add the required information.

Let see a minimal CMakeLists file and we will explain each line after:
```
cmake_minimum_required(VERSION 3.4.1)

project (myLibrary)

add_library(
        ${PROJECT_NAME}
        SHARED
        library_source_code.cpp)
```

The command `cmake_minimum_required` is self explanatory. It allows to define which version of CMake can use this CMakeLists. Some commands are added in new CMake version so all CMakeLists can't be used by any CMake version tool.

The command `project` allows to define a name. This value can be access with the variable `PROJECT_NAME`. So, you have to write ${PROJECT_NAME} to access it.

The last line is the one which says that we are currently building a library.

This command takes several parameters:
- the first one is the name of the file which will be generated. By using `${PROJECT_NAME}`, we give the name of the project to the library.
- the second parameter is the type of library that we will generate. In our case, we want to generate a `.so` file so we must generate a `shared` library. We must pass `SHARED` but we could pass `STATIC` if we want a static library.
- the last parameter is the source code file of the library. Here, there is only one file but we can pass a list of `.c` or `.cpp` files.

Some more information about the second parameter. We want to generate a shared library `.so` file because Android can only load this type library. It can't directly load a static library (`.a`). However, we can have static libraries in our project, it's just that these libraries have to be used by shared library to be usable.

## Add a dependency to a library with a CMakeLists:

You have the source code of a library, a CMakeLists to build that library and you want to link it to your main library.

We should add a link from the CMakeLists of the `app` module to the CMakeLists of the library module.

To do that, we need to call the command [`add_subdirectory`](https://cmake.org/cmake/help/v3.6/command/add_subdirectory.html):
```
add_subdirectory(
        /path/to/the/directory/with/CMakeLists/)
```

In the case where the targeted directory is not a subdirectory of the source, we must add one more parameter:
```
add_subdirectory(
    /path/to/directory/with/CMakeLists/
    ./name_of_directory_with_CMakeLists)
```

By example, if your directory libray has the relative path `/my-project/subdirectory` and your source directory is `/my-project/app/src/main/cpp`, the new command should be:
```
add_subdirectory(
    ${CMAKE_CURRENT_SOURCE_DIR}/../../../../subdirectory
    ./my-library)
```

If the library doesn't expose its public headers, you should import them by yourself. Call the command `include_directories` with the paths of the directories where the headers are.

In our case, all public headers are located in an `include` directory:
```
include_directories(${CMAKE_CURRENT_SOURCE_DIR}/../../../../subdirectory/include/)
```

The last step is to link the library we are adding to the main library. The command here is [`target_link_libraries`](https://cmake.org/cmake/help/v3.6/command/target_link_libraries.html#command:target_link_libraries):
```
target_link_libraries(
    ${PROJECT_NAME}
    subdirectoryLibrary)
```

- The first parameter is the name of the main library, here it's the project name.
- The second parameter is the name of the library we are adding.

Now, the dependency will be build at the same time as your Android application and you can use functions from the library inside your code.
 
## Add a dependency to a prebuilt library (`.so` or `.a`)

That you have a prebuilt library in the form of a `.so` file or `.a` file, the way to depend on it, is pretty the same way.

The first step is to indicate that we will add a library to this project. For this, call [`add_library`](https://cmake.org/cmake/help/v3.6/command/add_library.html) with several parameters:
```
add_library(  
    my_prebuilt_library  
    SHARED
    IMPORTED)  
```
- the first parameter is the name you want to give to the library. This name will be used in other commands we will use.
- the second parameter is the type of library you want to import: `STATIC` or `SHARED`.
- the last parameter is different from the first time we saw this command. Before, we pass a list of C/C++ files to compile. Here, the word `IMPORTED` is used to indicate that we will reference a library file outside our project. No build rules are generated for this library with this parameter, which is logic because we have a prebuilt library file.

The next command to call is [`set_target_properties`](https://cmake.org/cmake/help/latest/command/set_target_properties.html):
```
set_target_properties(  
    my_prebuilt_library  
    PROPERTIES
    IMPORTED_LOCATION ${STATIC_LIBRARY_A})  
 ```
- the first parameter is the same name used for the first command, the name of your libray.
- the word `PROPERTIES` has to be used as the second parameter.
- the last line allows to define a property with its value. By using the [`IMPORTED_LOCATION`](https://cmake.org/cmake/help/latest/prop_tgt/IMPORTED_LOCATION.html) property, we can define the location of our library on the disk.

The next command is to link the prebuilt library to our main library. It is [`target_link_libraries`](https://cmake.org/cmake/help/v3.6/command/target_link_libraries.html#command:target_link_libraries):
```
target_link_libraries(  
    ${PROJECT_NAME}  
    my_prebuilt_library)
```
- the firs parameter is the name of the library we want to add the dependency.
- the second parameter is the name of the library we want to add.

There is one last step to do now that we have added to the build the prebuilt library, we need to add library headers to be able to use it inside our code.

Call `include_directories` and pass the directory path with public headers inside as parameter:
```
include_directories(
    ${CMAKE_CURRENT_SOURCE_DIR}/../../my_prebuilt_library/include/) 
```

Now, you can call functions of the prebuilt library inside your own code. The prebuilt library will be linked to your Android application. If it's a shared library, the `.so`will be visible inside the final APK, if it's a `.a`, it will be merged inside your own native libray.

## Summary

Now, you know how to add dependencies to a native library for your Android application by using CMake. We look at the 3 ways to add dependencies and we discovered some new commands of CMake to do it.

You are now able to use any native library for your project in order to build the best Android application !

## Resources:

- [Android NDK guides](https://developer.android.com/ndk/guides)
- [Android NDK roadmap](https://android.googlesource.com/platform/ndk/+/master/docs/Roadmap.md)
- [Configure CMake from developer android web site](https://developer.android.com/studio/projects/configure-cmake)
- [Configure CMake from android ndk web site](https://developer.android.com/ndk/guides/cmake)
- [CMake changelog](https://cmake.org/cmake/help/latest/release/index.html)
- [Codelab to create a Hello-CMake](https://codelabs.developers.google.com/codelabs/android-studio-cmake/index.html#0)
- [Android NDK samples](https://github.com/googlesamples/android-ndk/tree/master)
