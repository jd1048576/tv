import Dependencies.Android
import Dependencies.Dagger
import Dependencies.Glide
import Dependencies.Kotlin
import Dependencies.LeakCanary
import Dependencies.Material
import Dependencies.Retrofit
import Dependencies.Stetho
import Dependencies.Timber

plugins {
    id(Plugins.androidApplication)
    id(Plugins.kotlinAndroid)
    id(Plugins.kotlinKapt)
}

android {
    compileSdkVersion(Config.compileSdkVersion)
    buildToolsVersion(Config.buildToolsVersion)

    defaultConfig {
        applicationId = "jdr.tv"
        minSdkVersion(Config.minSdkVersion)
        targetSdkVersion(Config.targetSdkVersion)
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    signingConfigs {
        getByName("debug") {
            storeFile = rootProject.file("signing/debug.jks")
            storePassword = "android"
            keyAlias = "debug"
            keyPassword = "android"
        }
    }

    buildTypes {
        getByName("debug") {
            signingConfig = signingConfigs.getByName("debug")
            applicationIdSuffix = ".debug"
            isDebuggable = true
        }
        getByName("release") {
            signingConfig = signingConfigs.getByName("debug")
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
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
    implementation(Dagger.android)
    implementation(Dagger.androidSupport)
    kapt(Dagger.androidProcessor)

    implementation(Glide.glide)
    kapt(Glide.compiler)

    implementation(Timber.timber)
   // debugImplementation(LeakCanary.leakCanary)
}
