# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in C:\Android\sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}
-optimizationpasses 5
-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclasses
-dontpreverify
-ignorewarnings
-verbose
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*

-dontwarn
-dontskipnonpubliclibraryclassmembers

-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class * extends java.net.URLDecoder
-keep public class com.android.vending.licensing.ILicensingService

-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}

-keepclasseswithmembernames class * {
    native <methods>;
}
-keepclasseswithmembernames class * {
    public <init>(android.content.Context, android.util.AttributeSet);
}
-keepclasseswithmembernames class * {
    public <init>(android.content.Context, android.util.AttributeSet, int);
}
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}
-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}

-keepattributes Signature

-keepattributes Exceptions,InnerClasses,Signature,SourceFile,LineNumberTable
-keepattributes *Annotation*

-keep class android.support.v4.** { *; }
-keep interface android.support.v4.app.** { *; }
-dontwarn android.support.v4.*
-dontwarn android.support.**

-keep class **.R$* {*;}
-keep class **.R{*;}

#-libraryjars libs/android-support-v4.jar

-keep public class * extends android.support.v4.**
-keep public class * extends android.app.Fragment

####### 参考http://www.jianshu.com/p/0ef702c206fa ##########
-dontwarn javax.annotation.**
-dontwarn javax.inject.**
# OkHttp3   （retrofit 里的okhttp3 和我的拦截器 版本不一致 compile 'com.squareup.okhttp3:logging-interceptor:3.0.0-RC1' 时需要设置）
#-dontwarn okhttp3.logging.**
#-keep class okhttp3.internal.**{*;}
#-dontwarn okio.**
# Retrofit
-dontwarn retrofit2.**
-keep class retrofit2.** { *; }
-keepattributes Signature
-keepattributes Exceptions
# RxJava RxAndroid
-dontwarn sun.misc.**
-keepclassmembers class rx.internal.util.unsafe.*ArrayQueue*Field* {
    long producerIndex;
    long consumerIndex;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueProducerNodeRef {
    rx.internal.util.atomic.LinkedQueueNode producerNode;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueConsumerNodeRef {
    rx.internal.util.atomic.LinkedQueueNode consumerNode;
}

# Gson
#-keep class com.google.gson.stream.** { *; }
#-keepattributes EnclosingMethod
#这是你定义的实体类，将整个包不混淆，或者将gson解析相关的bean分别不混淆
#-keep class com.qiufg.model.**{*;}
-keep class com.qiufg.model.GirlBean{*;}
-keep class com.qiufg.model.AndroidBean{*;}

###############################################################



# adding this in to preserve line numbers so that the stack traces
# can be remapped
-renamesourcefileattribute SourceFile
-keepattributes SourceFile,LineNumberTable