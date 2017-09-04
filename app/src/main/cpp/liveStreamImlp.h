//
// Created by chenjianhua on 2017/8/18.
//

#ifndef FFMPEGSHARELIB_LIVESTREAMIMLP_H
#define FFMPEGSHARELIB_LIVESTREAMIMLP_H

#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <math.h>
#include <android/log.h>
#include <jni.h>
#include <pthread.h>

#include "libavutil/avassert.h"
#include "libavutil/channel_layout.h"
#include "libavutil/opt.h"
#include "libavutil/mathematics.h"
#include "libavutil/timestamp.h"
#include "libavformat/avformat.h"
#include "libswscale/swscale.h"
#include "libswresample/swresample.h"


int init(char *outputfilename);

int initStartTime();

int sendVideo(jbyte *yuv);

int sendAudio(jbyte *au, int size);

int sendAudioShort(jshort* au,int shortsize);

int destroy();

int finishStream();

#endif //FFMPEGSHARELIB_LIVESTREAMIMLP_H
