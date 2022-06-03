plugins {
    id("com.android.application")
    kotlin("android")
}

val compileSdkProjectVersion: Int by project
val minSdkProjectVersion: Int by project
val targetSdkProjectVersion: Int by project

android {
    compileSdk = compileSdkProjectVersion

    defaultConfig {
        applicationId = "fr.bowserf.cmakesample"
        minSdk = minSdkProjectVersion
        targetSdk = targetSdkProjectVersion
        versionCode = 1
        versionName = "1.0"

        // specify for which architectures we want to generate native library files.
        ndk {
            abiFilters.addAll(listOf("armeabi-v7a", "x86", "x86_64", "arm64-v8a"))
        }

        // define the path where prebuilt ".so" files are embedded in the final APK
        /*sourceSets {
            debug {
                jniLibs.srcDirs "../shared_library/prebuilt/debug"
            }
            release {
                jniLibs.srcDirs "../shared_library/prebuilt/release"
            }
        }*/
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android.txt"))
            proguardFiles("proguard-rules.pro")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    externalNativeBuild {
        cmake {
            // define the path where the CMakeList has been put for this module.
            path("src/main/cpp/CMakeLists.txt")
            // specify the CMake version we want to use.
            version = "3.18.1"
        }
    }
}

dependencies {
    // Kotlin
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk7:1.6.21")

    // Android support
    implementation("com.android.support:appcompat-v7:28.0.0")
    implementation("com.android.support.constraint:constraint-layout:1.1.3")
}
