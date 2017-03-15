//
// Created by qiufg on 2017/3/15.
//
#include <jni.h>
#include <string>

#include "com_qiufg_hotfix_BugFixManager.h"
#include "art.h"

using namespace art::mirror;

//完成替换
JNIEXPORT void JNICALL Java_com_qiufg_hotfix_BugFixManager_replaceArt
        (JNIEnv *env, jobject jobj, jobject src, jobject dest) {

    ArtMethod *smeth = (ArtMethod *) env->FromReflectedMethod(src);
    ArtMethod *dmeth = (ArtMethod *) env->FromReflectedMethod(dest);

    //属性值替换赋值
    //当访问原来的错误的方法的指针时，这是操作的内存区域，实际上是正确方法的区域
    reinterpret_cast<Class *>(dmeth->declaring_class_)->class_loader_ =
            reinterpret_cast<Class *>(smeth->declaring_class_)->class_loader_; //for plugin classloader
    reinterpret_cast<Class *>(dmeth->declaring_class_)->clinit_thread_id_ =
            reinterpret_cast<Class *>(smeth->declaring_class_)->clinit_thread_id_;
    reinterpret_cast<Class *>(dmeth->declaring_class_)->status_ =
            reinterpret_cast<Class *>(smeth->declaring_class_)->status_ - 1;
    //for reflection invoke
    reinterpret_cast<Class *>(dmeth->declaring_class_)->super_class_ = 0;

    smeth->declaring_class_ = dmeth->declaring_class_;
    smeth->dex_cache_resolved_types_ = dmeth->dex_cache_resolved_types_;
    //smeth->access_flags_ = dmeth->access_flags_ | 0x0001;
    smeth->access_flags_ = dmeth->access_flags_;
    smeth->dex_cache_resolved_methods_ = dmeth->dex_cache_resolved_methods_;
    smeth->dex_code_item_offset_ = dmeth->dex_code_item_offset_;
    smeth->method_index_ = dmeth->method_index_;
    smeth->dex_method_index_ = dmeth->dex_method_index_;

    smeth->ptr_sized_fields_.entry_point_from_interpreter_ =
            dmeth->ptr_sized_fields_.entry_point_from_interpreter_;

    smeth->ptr_sized_fields_.entry_point_from_jni_ =
            dmeth->ptr_sized_fields_.entry_point_from_jni_;
    smeth->ptr_sized_fields_.entry_point_from_quick_compiled_code_ =
            dmeth->ptr_sized_fields_.entry_point_from_quick_compiled_code_;
}