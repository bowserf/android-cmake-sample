#ifndef ANDROID_CMAKE_SAMPLE_CALCULATOR_H
#define ANDROID_CMAKE_SAMPLE_CALCULATOR_H

#include <jni.h>
#include "Multiply.h"

extern "C" {

JNIEXPORT jlong JNICALL Java_fr_bowserf_cmakesample_Calculator_multiply(
        JNIEnv *env,
        jobject /* this */,
        jlong value1,
        jlong value2);

};


#endif //ANDROID_CMAKE_SAMPLE_CALCULATOR_H
