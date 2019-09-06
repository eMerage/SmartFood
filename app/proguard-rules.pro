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
-dontwarn okhttp3.**
-dontwarn okio.**
-dontwarn javax.annotation.**
-dontwarn org.conscrypt.**
-dontwarn javax.naming.**
-dontwarn javax.servlet.**
-dontwarn org.slf4j.**
-dontwarn retrofit2.Platform$Java8
-dontwarn sun.misc.Unsafe
-dontwarn javax.annotation.Nullable
-dontwarn javax.annotation.ParametersAreNonnullByDefault
-dontwarn javax.annotation.CheckReturnValue
-dontwarn javax.annotation.CheckForNull
-dontwarn javax.annotation.concurrent.GuardedBy
-dontwarn javax.annotation.concurrent.Immutable
-dontwarn javax.annotation.concurrent.ThreadSafe
-dontwarn javax.annotation.concurrent.NotThreadSafe
-dontwarn kotlin.jvm.internal.Intrinsics
-dontwarn com.squareup.okhttp3.**
-dontwarn org.jetbrains.annotations.**
-dontwarn com.instagram.common.json.**
-dontwarn com.fasterxml.jackson.databind.ext.DOMSerializer
-dontwarn com.squareup.javawriter.JavaWriter
-dontwarn com.google.common.primitives.UnsignedBytes*
-dontwarn kotlin.jvm.internal.Intrinsics
-dontwarn javax.annotation.ParametersAreNonnullByDefault
-dontwarn org.jetbrains.annotations.NotNull
-dontwarn kotlin.Metadata
-dontwarn kotlin.jvm.JvmName
-dontwarn kotlin.Unit
-dontwarn com.fasterxml.**
-dontwarn okio.**
-dontwarn retrofit2.**