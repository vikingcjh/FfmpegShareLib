#include <jni.h>
#include <string>

extern "C" {
#include "libavcodec/avcodec.h"
}

extern "C"
JNIEXPORT jbyteArray JNICALL
Java_com_soul_learn_ffmpegsharelib_jniUtils_YuvUtils_Yuv444Split(JNIEnv *env, jclass type) {

    // TODO
//    char c[10]="abcde12345";
    const char *c ="中国abcde12345";
//    unsigned char *pic=(unsigned char *)malloc(4);
    jbyteArray jch = NULL;
    jch = env->NewByteArray(strlen(c));
    env->SetByteArrayRegion(jch, 0, strlen(c), (jbyte *)c);
    return (jbyteArray)jch;

}


extern "C"
JNIEXPORT jstring JNICALL
Java_com_soul_learn_ffmpegsharelib_MainActivity_stringFromJNI(
        JNIEnv *env,
        jobject /* this */) {
    char info[10000] = { 0 };
    sprintf(info, "%s\n", avcodec_configuration());
    return env->NewStringUTF(info);

    /*std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());*/
}


