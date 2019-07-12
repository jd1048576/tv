import Config.Dependencies.Android
import Config.Dependencies.Kotlin
import Config.Dependencies.Timber

plugins {
    id(Config.Plugins.androidLibrary)
    id(Config.Plugins.kotlinAndroid)
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

    implementation(Android.activity)
    implementation(Android.appCompat)
    implementation(Android.core)
    implementation(Android.fragment)
    implementation(Android.Navigation.common)
    implementation(Android.Navigation.fragment)
    implementation(Android.Navigation.runtime)
    implementation(Android.Navigation.ui)

    implementation(Timber.timber)
}
