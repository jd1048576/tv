import Dependencies.Android
import Dependencies.Glide
import Dependencies.Kotlin
import Dependencies.Material
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

    dataBinding {
        isEnabled = true
    }
}

dependencies {
    implementation(Kotlin.stdlib)

    implementation(Android.activity)
    implementation(Android.appCompat)
    implementation(Android.constraintLayout)
    implementation(Android.core)
    implementation(Android.fragment)
    implementation(Android.recyclerView)
    implementation(Android.Paging.runtime)

    implementation(Material.material)

    implementation(Glide.glide)

    implementation(Timber.timber)
}
