import Config.Dependencies.Android
import Config.Dependencies.Dagger
import Config.Dependencies.Kotlin
import Config.Dependencies.Moshi
import Config.Dependencies.Timber

plugins {
    id(Config.Plugins.androidLibrary)
    id(Config.Plugins.kotlinAndroid)
    id(Config.Plugins.kotlinKapt)
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
    implementation(Kotlin.stdlib)
    implementation(Kotlin.Coroutines.core)
    implementation(Kotlin.Coroutines.android)

    implementation(Android.activity)
    implementation(Android.appCompat)
    implementation(Android.core)
    implementation(Android.fragment)

    implementation(Dagger.dagger)
    kapt(Dagger.compiler)

    implementation(Moshi.moshi)

    implementation(Timber.timber)
}
