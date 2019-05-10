#include "Calculator.h"

JNIEXPORT jlong JNICALL Java_fr_bowserf_cmakesample_Calculator_multiply(
        JNIEnv *env,
        jobject /* this */,
        jlong value1,
        jlong value2) {
    return multiply(value1, value2);
}