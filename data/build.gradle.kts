import Dependencies.Android
import Dependencies.Dagger
import Dependencies.Kotlin
import Dependencies.Okhttp3
import Dependencies.Retrofit

plugins {
    id(Plugins.androidLibrary)
    id(Plugins.kotlinAndroid)
    id(Plugins.kotlinKapt)
}

android {
    compileSdkVersion(Config.compileSdkVersion)
    buildToolsVersion(Config.buildToolsVersion)

    defaultConfig {
        minSdkVersion(Config.minSdkVersion)
        targetSdkVersion(Config.targetSdkVersion)
        testInstrumentationRunner = Config.testRunner
    }

    compileOptions {
        targetCompatibility = JavaVersion.VERSION_1_8
        sourceCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {
    implementation(project(":base"))
    implementation(project(":local"))
    implementation(project(":remote"))

    implementation(Kotlin.stdlib)
    implementation(Kotlin.Coroutines.core)
    implementation(Kotlin.Coroutines.android)

    implementation(Android.preference)
    implementation(Android.Lifecycle.livedata)
    implementation(Android.Paging.runtime)
    implementation(Android.Room.core)

    implementation(Dagger.dagger)
    kapt(Dagger.compiler)

    implementation(Okhttp3.okhttp3)

    implementation(Retrofit.retrofit)
}
