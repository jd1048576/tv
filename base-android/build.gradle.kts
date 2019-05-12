import Dependencies.Android
import Dependencies.Dagger
import Dependencies.Kotlin
import Dependencies.Timber

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
}

dependencies {

    implementation(Kotlin.stdlib)
    implementation(Kotlin.Coroutines.core)
    implementation(Kotlin.Coroutines.android)

    implementation(Android.appCompat)
    implementation(Android.activity)
    implementation(Android.core)
    implementation(Android.fragment)

    implementation(Dagger.dagger)
    kapt(Dagger.compiler)

    implementation(Timber.timber)
}
