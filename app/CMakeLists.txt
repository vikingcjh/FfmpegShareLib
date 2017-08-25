# For more information about using CMake with Android Studio, read the
# documentation: https://d.android.com/studio/projects/add-native-code.html

# Sets the minimum version of CMake required to build the native library.

cmake_minimum_required(VERSION 3.4.1)


set(lib_ffmpeg_DIR ${CMAKE_SOURCE_DIR}/src/main/jniLibs/armeabi-v7a)
include_directories(
     ${CMAKE_SOURCE_DIR}/src/main/cpp/ffmpegUtil
     ${CMAKE_SOURCE_DIR}/src/main/cpp/include
     ${CMAKE_SOURCE_DIR}/src/main/cpp



)

# 查找所有源码 并拼接到路径列表
aux_source_directory(src/main/cpp SRC_LIST)
aux_source_directory(src/main/cpp/ffmpegUtil CORE_SRC_LIST)
list(APPEND SRC_LIST ${CORE_SRC_LIST})


# Creates and names a library, sets it as either STATIC
# or SHARED, and provides the relative paths to its source code.
# You can define multiple libraries, and CMake builds them for you.
# Gradle automatically packages shared libraries with your APK.

add_library(avcodec_lib SHARED IMPORTED)
set_target_properties(avcodec_lib PROPERTIES IMPORTED_LOCATION
                          ${lib_ffmpeg_DIR}/libavcodec.so)
add_library(avfilter_lib SHARED IMPORTED)
set_target_properties(avfilter_lib PROPERTIES IMPORTED_LOCATION
                          ${lib_ffmpeg_DIR}/libavfilter.so)
add_library(avformat_lib SHARED IMPORTED)
set_target_properties(avformat_lib PROPERTIES IMPORTED_LOCATION
                     ${lib_ffmpeg_DIR}/libavformat.so)
add_library(avutil_lib SHARED IMPORTED)
set_target_properties(avutil_lib PROPERTIES IMPORTED_LOCATION
                     ${lib_ffmpeg_DIR}/libavutil.so)
add_library(avresample_lib SHARED IMPORTED)
set_target_properties(avresample_lib PROPERTIES IMPORTED_LOCATION
                     ${lib_ffmpeg_DIR}/libavresample.so)
add_library(swresample_lib SHARED IMPORTED)
set_target_properties(swresample_lib PROPERTIES IMPORTED_LOCATION
                     ${lib_ffmpeg_DIR}/libswresample.so)
add_library(swscale_lib SHARED IMPORTED)
set_target_properties(swscale_lib PROPERTIES IMPORTED_LOCATION
                     ${lib_ffmpeg_DIR}/libswscale.so)



add_library( # Sets the name of the library.
             native-lib

             # Sets the library as a shared library.
             SHARED

             # Provides a relative path to your source file(s).
             ${SRC_LIST} )
#             src/main/cpp/native-lib.cpp

# Searches for a specified prebuilt library and stores the path as a
# variable. Because CMake includes system libraries in the search path by
# default, you only need to specify the name of the public NDK library
# you want to add. CMake verifies that the library exists before
# completing its build.

find_library( # Sets the name of the path variable.
              log-lib

              # Specifies the name of the NDK library that
              # you want CMake to locate.
              log )

# Specifies libraries CMake should link to your target library. You
# can link multiple libraries, such as libraries you define in this
# build script, prebuilt third-party libraries, or system libraries.

target_link_libraries( # Specifies the target library.
                       native-lib
                       android
                       # Links the target library to the log library
                       # included in the NDK.
                       ${log-lib}
                       avcodec_lib
                       avfilter_lib
                       avformat_lib
                       avutil_lib
                       avresample_lib
                       swresample_lib
                       swscale_lib)