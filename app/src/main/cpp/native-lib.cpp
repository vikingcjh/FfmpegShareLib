#include <jni.h>
#include <string>

extern "C"{
#include "libavcodec/avcodec.h"
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
