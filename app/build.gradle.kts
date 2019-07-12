import Config.Dependencies.Android
import Config.Dependencies.Dagger
import Config.Dependencies.Glide
import Config.Dependencies.Kotlin
import Config.Dependencies.Material
import Config.Dependencies.Okhttp3
import Config.Dependencies.Timber

plugins {
    id(Config.Plugins.androidApplication)
    id(Config.Plugins.kotlinAndroid)
    id(Config.Plugins.kotlinKapt)
}

val releaseSigning = rootProject.file(".signing/app-release.jks").exists()

android {
    compileSdkVersion(Config.compileSdkVersion)
    buildToolsVersion(Config.buildToolsVersion)

    defaultConfig {
        applicationId = "jdr.tv"
        minSdkVersion(Config.minSdkVersion)
        targetSdkVersion(Config.targetSdkVersion)
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = Config.testRunner
    }

    signingConfigs {
        getByName("debug") {
            storeFile = rootProject.file(".signing/app-debug.jks")
            storePassword = "android"
            keyAlias = "debug"
            keyPassword = "android"
        }
        if (releaseSigning) create("release") {
            storeFile = rootProject.file(".signing/app-release.jks")
            storePassword = localProperty("STORE_PASSWORD")
            keyAlias = localProperty("KEY_ALIAS")
            keyPassword = localProperty("KEY_PASSWORD")
        }
    }

    buildTypes {
        getByName("debug") {
            signingConfig = signingConfigs.getByName("debug")
            applicationIdSuffix = ".debug"
            isDebuggable = true
        }
        getByName("release") {
            signingConfig = signingConfigs.getByName(if (releaseSigning) "release" else "debug")
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    compileOptions {
        targetCompatibility = JavaVersion.VERSION_1_8
        sourceCompatibility = JavaVersion.VERSION_1_8
    }

    dataBinding {
        isEnabled = true
    }

    dynamicFeatures = mutableSetOf(":details", ":discover", ":schedule", ":search", ":settings", ":shows", ":watchlist")
}

dependencies {
    implementation(project(":base"))
    implementation(project(":ui"))
    implementation(project(":viewmodel"))
    implementation(project(":local"))
    implementation(project(":remote"))
    implementation(project(":data"))

    implementation(Kotlin.stdlib)
    implementation(Kotlin.Coroutines.core)
    implementation(Kotlin.Coroutines.android)

    implementation(Android.appCompat)
    implementation(Android.activity)
    implementation(Android.fragment)
    implementation(Android.constraintLayout)
    implementation(Android.core)
    implementation(Android.preference)
    implementation(Android.Lifecycle.livedata)
    implementation(Android.Lifecycle.viewmodel)
    implementation(Android.Navigation.common)
    implementation(Android.Navigation.fragment)
    implementation(Android.Navigation.runtime)
    implementation(Android.Navigation.ui)

    implementation(Material.material)

    implementation(Dagger.dagger)
    kapt(Dagger.compiler)

    implementation(Okhttp3.okhttp3)

    implementation(Glide.glide)
    implementation(Glide.okhttp3) {
        isTransitive = false
    }
    kapt(Glide.compiler)

    implementation(Timber.timber)
    // debugImplementation(LeakCanary.leakCanary)
}
