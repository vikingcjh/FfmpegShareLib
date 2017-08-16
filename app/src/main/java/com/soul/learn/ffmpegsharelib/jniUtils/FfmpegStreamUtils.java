package com.soul.learn.ffmpegsharelib.jniUtils;

/**
 * Created by cjh on 2017/8/16.
 */

public class FfmpegStreamUtils {
    public static native int stream(String filePath,String outputPath);
}
