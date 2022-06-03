#include "Calculator.h"

extern "C" {

JNIEXPORT jlong Java_fr_bowserf_cmakesample_Calculator_multiply(
        JNIEnv *env,
        jobject /* this */,
        jlong value1,
        jlong value2) {
    return multiply(value1, value2);
}

JNIEXPORT jlong Java_fr_bowserf_cmakesample_Calculator_add(
        JNIEnv *env,
        jobject /* this */,
        jlong value1,
        jlong value2) {
    return add(value1, value2);
}

JNIEXPORT jlong Java_fr_bowserf_cmakesample_Calculator_minus(
        JNIEnv *env,
        jobject /* this */,
        jlong value1,
        jlong value2) {
    return minus(value1, value2);
}

}