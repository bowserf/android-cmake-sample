#ifndef ANDROID_CMAKE_SAMPLE_CALCULATOR_H
#define ANDROID_CMAKE_SAMPLE_CALCULATOR_H

#include <jni.h>
#include "Multiply.h"
#include "Addition.h"
#include "Subtraction.h"

extern "C" {

JNIEXPORT jlong Java_fr_bowserf_cmakesample_Calculator_multiply(
        JNIEnv *env,
        jobject /* this */,
        jlong value1,
        jlong value2
);

JNIEXPORT jlong Java_fr_bowserf_cmakesample_Calculator_add(
        JNIEnv *env,
        jobject /* this */,
        jlong value1,
        jlong value2
);

JNIEXPORT jlong Java_fr_bowserf_cmakesample_Calculator_minus(
        JNIEnv *env,
        jobject /* this */,
        jlong value1,
        jlong value2
);

}

#endif //ANDROID_CMAKE_SAMPLE_CALCULATOR_H
