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

    implementation(Android.Room.runtime)
    implementation(Android.Room.ktx)
    kapt(Android.Room.compiler)

    implementation(Android.Paging.runtime)

    implementation(Dagger.dagger)
    kapt(Dagger.compiler)

    implementation(Moshi.moshi)

    implementation(Timber.timber)
}
