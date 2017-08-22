package com.soul.learn.ffmpegsharelib.jniUtils;

/**
 * Created by cjh on 2017/8/18.
 */

public class StreamHelper {
    public static native int init(String outputPath);

    public static native int initStartTime();

    public static native int sendVideo(byte[] yuvdata);

    public static native int sendAudio(byte[] audiodata,int size);

    public static native int destroy();

    public static native int finishStream();

    public static synchronized int senddata(byte[] data,int size,boolean isAudio) {
        if (isAudio) {
            sendAudio(data, size);
        } else {
            sendVideo(data);
        }
        return 0;
    }
}
