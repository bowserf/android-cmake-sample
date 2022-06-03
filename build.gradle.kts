// Top-level build file where you can add configuration options common to all sub-projects/modules.

buildscript {
    extra.set("compileSdkProjectVersion", 31)
    extra.set("minSdkProjectVersion", 21)
    extra.set("targetSdkProjectVersion", 31)

    repositories {
        google()
        mavenCentral()
    }

    dependencies {
        classpath("com.android.tools.build:gradle:7.2.1")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.6.21")
        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle.kts files
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
    }
}
