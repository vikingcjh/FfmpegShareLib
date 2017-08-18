//
// Created by chenjianhua on 2017/8/14.
//

#ifndef FFMPEGSHARELIB_MUXING_H
#define FFMPEGSHARELIB_MUXING_H

#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <math.h>
#include <android/log.h>

#include <libavutil/avassert.h>
#include <libavutil/channel_layout.h>
#include <libavutil/opt.h>
#include <libavutil/mathematics.h>
#include <libavutil/timestamp.h>
#include <libavformat/avformat.h>
#include <libswscale/swscale.h>
#include <libswresample/swresample.h>

int mux(char *outputfilename);

#endif //FFMPEGSHARELIB_MUXING_H
