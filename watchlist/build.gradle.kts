import Dependencies.Android
import Dependencies.Dagger
import Dependencies.Kotlin
import Dependencies.Timber
import Dependencies.Material

plugins {
    id(Plugins.androidDynamicFeature)
    id(Plugins.kotlinAndroid)
    id(Plugins.kotlinKapt)
}

android {
    compileSdkVersion(Config.compileSdkVersion)
    buildToolsVersion(Config.buildToolsVersion)

    defaultConfig {
        minSdkVersion(Config.minSdkVersion)
        targetSdkVersion(Config.targetSdkVersion)
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    compileOptions {
        targetCompatibility = JavaVersion.VERSION_1_8
        sourceCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {
    implementation(project(":app"))

    implementation(Kotlin.stdlib)
    implementation(Kotlin.Coroutines.core)
    implementation(Kotlin.Coroutines.android)

    implementation(Android.appCompat)
    implementation(Android.activity)
    implementation(Android.fragment)
    implementation(Android.constraintLayout)
    implementation(Android.coreKtx)

    implementation(Android.Navigation.fragment)
    implementation(Android.Navigation.ui)
    implementation(Android.Navigation.commonKtx)
    implementation(Android.Navigation.fragmentKtx)
    implementation(Android.Navigation.runtimeKtx)
    implementation(Android.Navigation.uiKtx)

    implementation(Material.material)

    implementation(Dagger.dagger)
    kapt(Dagger.compiler)

    implementation(Timber.timber)
}
