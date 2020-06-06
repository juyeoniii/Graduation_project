#include <jni.h>
#include <string>

extern "C" JNIEXPORT jstring JNICALL
Java_com_example_reproject_MainActivity_stringFromJNI(
        JNIEnv* env,
        jobject /* this */) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}
extern "C"
JNIEXPORT jint JNICALL
Java_com_googlecode_leptonica_android_Pix_nativeGetRefCount(JNIEnv *env, jclass clazz,
                                                            jlong native_pix) {
    // TODO: implement nativeGetRefCount()
}extern "C"
JNIEXPORT jlong JNICALL
Java_com_googlecode_leptonica_android_Pix_nativeCreatePix(JNIEnv *env, jclass clazz, jint w, jint h,
                                                          jint d) {
    // TODO: implement nativeCreatePix()
}extern "C"
JNIEXPORT jlong JNICALL
Java_com_googlecode_leptonica_android_Pix_nativeCreateFromData(JNIEnv *env, jclass clazz,
                                                               jbyteArray data, jint w, jint h,
                                                               jint d) {
    // TODO: implement nativeCreateFromData()
}extern "C"
JNIEXPORT jbyteArray JNICALL
Java_com_googlecode_leptonica_android_Pix_nativeGetData(JNIEnv *env, jclass clazz,
                                                        jlong native_pix) {
    // TODO: implement nativeGetData()
}extern "C"
JNIEXPORT jlong JNICALL
Java_com_googlecode_leptonica_android_Pix_nativeClone(JNIEnv *env, jclass clazz, jlong native_pix) {
    // TODO: implement nativeClone()
}extern "C"
JNIEXPORT jlong JNICALL
Java_com_googlecode_leptonica_android_Pix_nativeCopy(JNIEnv *env, jclass clazz, jlong native_pix) {
    // TODO: implement nativeCopy()
}extern "C"
JNIEXPORT jboolean JNICALL
Java_com_googlecode_leptonica_android_Pix_nativeInvert(JNIEnv *env, jclass clazz,
                                                       jlong native_pix) {
    // TODO: implement nativeInvert()
}extern "C"
JNIEXPORT void JNICALL
Java_com_googlecode_leptonica_android_Pix_nativeDestroy(JNIEnv *env, jclass clazz,
                                                        jlong native_pix) {
    // TODO: implement nativeDestroy()
}extern "C"
JNIEXPORT jboolean JNICALL
Java_com_googlecode_leptonica_android_Pix_nativeGetDimensions(JNIEnv *env, jclass clazz,
                                                              jlong native_pix,
                                                              jintArray dimensions) {
    // TODO: implement nativeGetDimensions()
}extern "C"
JNIEXPORT jint JNICALL
Java_com_googlecode_leptonica_android_Pix_nativeGetWidth(JNIEnv *env, jclass clazz,
                                                         jlong native_pix) {
    // TODO: implement nativeGetWidth()
}extern "C"
JNIEXPORT jint JNICALL
Java_com_googlecode_leptonica_android_Pix_nativeGetHeight(JNIEnv *env, jclass clazz,
                                                          jlong native_pix) {
    // TODO: implement nativeGetHeight()
}extern "C"
JNIEXPORT jint JNICALL
Java_com_googlecode_leptonica_android_Pix_nativeGetDepth(JNIEnv *env, jclass clazz,
                                                         jlong native_pix) {
    // TODO: implement nativeGetDepth()
}extern "C"
JNIEXPORT jint JNICALL
Java_com_googlecode_leptonica_android_Pix_nativeGetPixel(JNIEnv *env, jclass clazz,
                                                         jlong native_pix, jint x, jint y) {
    // TODO: implement nativeGetPixel()
}extern "C"
JNIEXPORT void JNICALL
Java_com_googlecode_leptonica_android_Pix_nativeSetPixel(JNIEnv *env, jclass clazz,
                                                         jlong native_pix, jint x, jint y,
                                                         jint color) {
    // TODO: implement nativeSetPixel()
}extern "C"
JNIEXPORT jint JNICALL
Java_com_googlecode_leptonica_android_Pix_nativeGetSpp(JNIEnv *env, jclass clazz,
                                                       jlong native_pix) {
    // TODO: implement nativeGetSpp()
}extern "C"
JNIEXPORT jint JNICALL
Java_com_googlecode_leptonica_android_Pix_nativeGetInputFormat(JNIEnv *env, jclass clazz,
                                                               jlong native_pix) {
    // TODO: implement nativeGetInputFormat()
}