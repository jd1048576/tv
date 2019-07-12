import Config.Dependencies.Android
import Config.Dependencies.Glide
import Config.Dependencies.Kotlin
import Config.Dependencies.Material
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
