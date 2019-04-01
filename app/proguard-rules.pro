# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile



        -keep public class android.support.v7.widget.** { *; }
        -keep public class android.support.v7.internal.widget.** { *; }
        -keep public class android.support.v7.internal.view.menu.** { *; }
        -keep class android.support.v7.internal.** { *; }
        -keep interface android.support.v7.internal.** { *; }
        -keep class android.support.v7.** { *; }
        -keep interface android.support.v7.** { *; }

        -keep public class * extends android.support.v4.view.ActionProvider {
            public <init>(android.content.Context);
            }

# picasso, Square Picasso specific rules
        -dontwarn com.squareup.okhttp.**

#cardview-v7
        #http://stackoverflow.com/questions/29679177/cardview-shadow-not-appearing-in-lollipop-after-obfuscate-with-proguard/29698051
        -keep class android.support.v7.widget.RoundRectDrawable { *; }

# OkHttp
        -keepattributes Signature
        -keepattributes *Annotation*
        -keep class okhttp3.** { *; }
        -keep interface okhttp3.** { *; }
        -dontwarn okhttp3.**

# gson
        # Gson uses generic type information stored in a class file when working with fields. Proguard
        # Retain declared checked exceptions for use by a Proxy instance.
        -keepattributes Exceptions

        # For using GSON @Expose annotation
        -keepattributes *Annotation*

        -keepattributes EnclosingMethod
        # Gson specific classes
        -dontwarn sun.misc.**
        -keep class sun.misc.Unsafe { *; }
        -keep class com.google.gson.examples.android.model.** { *; }
        -keep class com.google.gson.stream.** { *; }

        # Prevent proguard from stripping interface information from TypeAdapterFactory,
        # JsonSerializer, JsonDeserializer instances (so they can be used in @JsonAdapter)
        -keep class * implements com.google.gson.TypeAdapterFactory
        -keep class * implements com.google.gson.JsonSerializer
        -keep class * implements com.google.gson.JsonDeserializer

# Fabric
        -keep class com.crashlytics.** { *; }
        -keep class com.crashlytics.android.**
        -keepattributes SourceFile, LineNumberTable, *Annotation*


# retrofit 2
        -keep class retrofit.** { *; }
            -keepclasseswithmembers class * {
                 @retrofit.http.* *;
          }
        -dontwarn retrofit2.Platform$Java8
        # Platform calls Class.forName on types which do not exist on Android to determine platform.
        -dontnote retrofit2.Platform

        -dontwarn javax.annotation.**

# Ignore warnings for Okio , Package java.nio.* isn't available on Android and will be never called
        -dontwarn okio.**

#Glide
        -dontwarn com.bumptech.glide.**
        -keep public class * implements com.bumptech.glide.module.GlideModule
        -keep public class * extends com.bumptech.glide.module.AppGlideModule
        -keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
            **[] $VALUES;
            public *;
        }

#Ignore warnings for sun and w3c,Package com.sun.org.* and com.w3c.dom isn't available on Android and will be never called
         -dontwarn com.sun.org.**
         -dontwarn com.w3c.dom.**
         -dontwarn org.w3c.dom.ls.**
#Warning:org.w3c.dom.ls.LSLoadEvent: can't find superclass or interface org.w3c.dom.events.Event
#-dontwarn com.google.android.gms.**

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}
