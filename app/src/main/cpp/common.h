#ifndef COMMON_H_
#define COMMON_H_

#include <jni.h>
#include <android/log.h>

#define  LOG_TAG    "jason"
#define  LOGD(...)  __android_log_print(ANDROID_LOG_DEBUG,LOG_TAG,__VA_ARGS__)
#define  LOGW(...)  __android_log_print(ANDROID_LOG_WARN,LOG_TAG,__VA_ARGS__)
#define  LOGE(...)  __android_log_print(ANDROID_LOG_ERROR,LOG_TAG,__VA_ARGS__)							\

#endif /* COMMON_H_ */
