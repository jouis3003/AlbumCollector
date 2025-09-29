# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

#I haven't add much experience with adding proguard rules so I'm following a guide
#https://nameisjayant.medium.com/write-proguard-rules-for-your-android-app-2fb2954151f6

# ----------- KEEP ANNOTATIONS (Compose, Hilt, etc.) -----------
-keepattributes *Annotation*, InnerClasses, Signature, EnclosingMethod

# ----------- KEEP Jetpack Compose -----------
# Compose compiler generates synthetic methods that shouldn't be stripped
-keep class androidx.compose.** { *; }
-dontwarn androidx.compose.**

# ----------- KEEP Hilt / Dagger -----------
-keep class dagger.hilt.** { *; }
-keep class javax.inject.** { *; }
-keep class dagger.internal.** { *; }
-dontwarn dagger.hilt.**
-dontwarn javax.inject.**

# Keep generated Hilt classes
-keep class * extends dagger.hilt.internal.GeneratedComponent { *; }
-keep class * implements dagger.hilt.internal.GeneratedComponent { *; }

# Don't strip metadata used for dependency injection
-keepclassmembers class ** {
    @dagger.** <fields>;
    @dagger.** <methods>;
}

# ----------- KEEP Retrofit -----------
# Retrofit uses reflection to call APIs
-keep interface retrofit2.** { *; }
-keep class retrofit2.** { *; }
-dontwarn retrofit2.**

# Keep your Retrofit API interfaces
-keep class pt.techchallenge.albumcollector.data.network.** { *; }

# ----------- KEEP Gson Models -----------
# Gson uses reflection for serialization/deserialization
-keep class pt.techchallenge.albumcollector.data.models.** { *; }
-keepclassmembers class pt.techchallenge.albumcollector.data.models.** {
    <fields>;
}

# Optional: prevent Gson from removing field names
-keepattributes Signature
-keepattributes *Annotation*

# ----------- KEEP Room -----------
-keep class androidx.room.** { *; }
-dontwarn androidx.room.**

# Room uses annotations for entity/schema info
-keepclassmembers class * {
    @androidx.room.* <methods>;
    @androidx.room.* <fields>;
}

# ----------- AndroidX Core (Optional) -----------
-dontwarn androidx.**
-keep class androidx.** { *; }

# ----------- General Safe Settings -----------
-dontwarn kotlin.**
-dontwarn org.jetbrains.annotations.**
-keep class kotlin.Metadata { *; }

# ----------- Optional: Keep ViewModel / SavedState Handles -----------
-keep class pt.techchallenge.albumcollector.ui.**.viewmodel.** { *; }
-keepclassmembers class * extends androidx.lifecycle.ViewModel {
    <init>();
}

# Keep OkHttp
-keep class okhttp3.** { *; }
-dontwarn okhttp3.**

# Keep Coil
-keep class coil3.** { *; }
-dontwarn coil3.**
