#include <jni.h>
#include <string>
#include <android/log.h>

#define LOGE(format, ...)  __android_log_print(ANDROID_LOG_ERROR, "(>_<)", format, ##__VA_ARGS__)
#define LOGI(format, ...)  __android_log_print(ANDROID_LOG_INFO,  "(^_^)", format, ##__VA_ARGS__)


extern "C" {
//#include "libavcodec/avcodec.h"
#include "liveStreamImlp.h"
#include "muxing.h"

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

extern "C"
JNIEXPORT jint JNICALL
Java_com_soul_learn_ffmpegsharelib_jniUtils_FfmpegStreamUtils_muxing(JNIEnv *env, jclass type,
                                                                     jstring outputPath_) {
    const char *outputPath = env->GetStringUTFChars(outputPath_, 0);

    LOGI("in muxing");
//    LOGI((const char*)outputPath);
    // TODO
    char output_str[500]={0};
    sprintf(output_str,"%s",env->GetStringUTFChars(outputPath_, NULL));

    LOGI("%s",output_str);
    mux(output_str);

    LOGI("after muxing");

    env->ReleaseStringUTFChars(outputPath_, outputPath);

    return 0;
}

extern "C"
JNIEXPORT jint JNICALL
Java_com_soul_learn_ffmpegsharelib_jniUtils_StreamHelper_init(JNIEnv *env, jclass type,
                                                                   jstring outputPath_) {
    const char *outputPath = env->GetStringUTFChars(outputPath_, 0);

    // TODO
    LOGI("in init");
//    LOGI((const char*)outputPath);
    // TODO
    char output_str[500] = {0};
    sprintf(output_str, "%s", env->GetStringUTFChars(outputPath_, NULL));

    LOGI("%s", output_str);

    int n = init(output_str);
    LOGI("after init");

    env->ReleaseStringUTFChars(outputPath_, outputPath);
    return n;
}

extern "C"
JNIEXPORT jint JNICALL
Java_com_soul_learn_ffmpegsharelib_jniUtils_StreamHelper_initStartTime(JNIEnv *env, jclass type) {

    // TODO
    initStartTime();

    return 0;
}

extern "C"
JNIEXPORT jint JNICALL
Java_com_soul_learn_ffmpegsharelib_jniUtils_StreamHelper_sendVideo(JNIEnv *env, jclass type,
                                                                        jbyteArray yuvdata_) {
    jbyte *yuvdata = env->GetByteArrayElements(yuvdata_, NULL);

    // TODO
    sendVideo(yuvdata);

    env->ReleaseByteArrayElements(yuvdata_, yuvdata, 0);
    return 0;
}

extern "C"
JNIEXPORT jint JNICALL
Java_com_soul_learn_ffmpegsharelib_jniUtils_StreamHelper_sendAudio(JNIEnv *env, jclass type,
                                                                        jbyteArray audiodata_,
                                                                        jint size) {
    jbyte *audiodata = env->GetByteArrayElements(audiodata_, NULL);

    // TODO
    sendAudio(audiodata, size);

    env->ReleaseByteArrayElements(audiodata_, audiodata, 0);
    return 0;
}

extern "C"
JNIEXPORT jint JNICALL
Java_com_soul_learn_ffmpegsharelib_jniUtils_StreamHelper_destroy(JNIEnv *env, jclass type) {

    // TODO
    destroy();
    return 0;

}

extern "C"
JNIEXPORT jint JNICALL
Java_com_soul_learn_ffmpegsharelib_jniUtils_StreamHelper_finishStream(JNIEnv *env,
                                                                           jclass type) {

    // TODO
    finishStream();
    return 0;

}


