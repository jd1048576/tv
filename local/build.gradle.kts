import Dependencies.Android
import Dependencies.Dagger
import Dependencies.Kotlin
import Dependencies.Moshi
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

    compileOptions {
        targetCompatibility = JavaVersion.VERSION_1_8
        sourceCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {
    implementation(project(":base"))

    implementation(Kotlin.stdlib)
    implementation(Kotlin.Coroutines.core)
    implementation(Kotlin.Coroutines.android)

    implementation(Android.Lifecycle.livedata)
    implementation(Android.Paging.runtime)
    implementation(Android.Room.core)
    kapt(Android.Room.compiler)

    implementation(Dagger.dagger)
    kapt(Dagger.compiler)

    implementation(Moshi.moshi)

    implementation(Timber.timber)
}
