import Config.Dependencies.Android
import Config.Dependencies.Dagger
import Config.Dependencies.Glide
import Config.Dependencies.Kotlin
import Config.Dependencies.Material
import Config.Dependencies.Retrofit
import Config.Dependencies.Timber

plugins {
    id(Config.Plugins.androidDynamicFeature)
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
    implementation(project(":base"))
    implementation(project(":navigation"))
    implementation(project(":ui"))
    implementation(project(":viewmodel"))
    implementation(project(":local"))
    implementation(project(":remote"))
    implementation(project(":data"))
    implementation(project(":app"))

    implementation(Kotlin.stdlib)
    implementation(Kotlin.Coroutines.core)
    implementation(Kotlin.Coroutines.android)

    implementation(Android.activity)
    implementation(Android.appCompat)
    implementation(Android.constraintLayout)
    implementation(Android.fragment)
    implementation(Android.recyclerView)
    implementation(Android.Lifecycle.livedata)
    implementation(Android.Lifecycle.viewmodel)
    implementation(Android.Paging.runtime)
    implementation(Android.Room.core)

    implementation(Material.material)

    implementation(Dagger.dagger)
    kapt(Dagger.compiler)

    implementation(Glide.glide)

    implementation(Retrofit.retrofit)

    implementation(Timber.timber)
}
