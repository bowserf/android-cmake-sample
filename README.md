[![NDK version](https://img.shields.io/static/v1.svg?label=NDK&message=version%2019&color=blue)]()
[![CMake version](https://img.shields.io/static/v1.svg?label=CMake&message=3.6.0&color=red)]()

# Android & CMake sample

Put his hands in the native code (C/C++) with the Android NDK can be a hard task for an Android developer used to
Java/Kotlin and Gradle.

This sample shows several use cases about how to use CMake:
- to build native library without Android Studio
- to use prebuilt library (shared (.so) or static (.a))
- to depends on another native module from your native module
- to build Android application with native code.

## Project

The application allows you to do some computations on 2 numbers given in input. Computations are
realized by native libraries, written in C/C++, embedded in the application. Each computation
is realized by one of the library and each library is imported in a different way using CMake.

The project contains 4 modules:
- app
- subdirectory
- shared_library
- static_library

### Modules

The `app` module contains the Android application code and depends on other modules to do
computations.

Other modules contains C/C++ code and each one does one different computation:
- `subdirectory` does a multiplication on 2 numbers.
- `shared_library` does an addition on 2 numbers.
- `static_library` does a subtraction on 2 numbers.

#### App module

This module contains the code of the Android application. It contains Kotlin code to manage a basic
Android application but also C/C++ code to communicate with the native libraries this application
depends on.

Dependencies on native libraries are defined in the CMakeList targeted by the `build.gradle` of the
module:

```
externalNativeBuild {
    cmake {
        path "src/main/cpp/CMakeLists.txt"
    }
}
```

The C/C++ code uses JNI to create a bridge between Kotlin code and pure C/C++ code. It allows to
write functions which are called by Kotlin code and from inside JNI function, to call any C/C++
function.


#### Subdirectory module

This module is imported by the `app` module with a CMake dependency. The CMakeList used by the `app`
module targets the CMakeList of this module with the command `add_subdirectory`.

The library is built as a shared library. A `.so` file is generated and contains all the code.

The `include` directory contains all `public` headers required by the `app` module to be able to use
it.

The library exposes its headers by using `target_include_directories` with `PUBLIC` visibility.


#### Shared_library module

This module allows to generate a shared library, a `.so` file, by executing the `library_built.sh`
script.

This module is not built each time the `app` module is built. `app` module only targets the
pre-generated `.so` file and its headers inside the `include` directory.

To import headers in the `app` module, we use the command `include_directories`


#### Static_library module

This module allows to generate a static library, a `.a` file, by executing the `library_built.sh`
script.

This module is not built each time the `app` module is built. `app` module only targets the
pre-generated `.a` file and its headers inside the `include` directory.

To import headers in the `app` module, we use the command `include_directories`


## Resources:

- [Android NDK guides](https://developer.android.com/ndk/guides)
- [Android NDK roadmap](https://android.googlesource.com/platform/ndk/+/master/docs/Roadmap.md)
- [Configure CMake from developer android web site](https://developer.android.com/studio/projects/configure-cmake)
- [Configure CMake from android ndk web site](https://developer.android.com/ndk/guides/cmake)
- [CMake changelog](https://cmake.org/cmake/help/latest/release/index.html)
- [Codelab to create a Hello-CMake](https://codelabs.developers.google.com/codelabs/android-studio-cmake/index.html#0)
- [Android NDK samples](https://github.com/googlesamples/android-ndk/tree/master)


