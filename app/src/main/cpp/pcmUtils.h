//
// Created by chenjianhua on 2017/8/10.
//

#ifndef FFMPEGSHARELIB_PCMUTILS_H
#define FFMPEGSHARELIB_PCMUTILS_H

#include <jni.h>
#include <string.h>
#include <malloc.h>
#include <math.h>

int simplest_pcm16le_split(char *url);

int simplest_pcm16le_halfvolumeleft(char *url);

int simplest_pcm16le_doublespeed(char *url);

int simplest_pcm16le_to_pcm8(char *url);

int simplest_pcm16le_cut_singlechannel(char *url, int start_num, int dur_num);

int
simplest_pcm16le_to_wave(const char *pcmpath, int channels, int sample_rate, const char *wavepath);

#endif //FFMPEGSHARELIB_PCMUTILS_H
