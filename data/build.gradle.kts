import Dependencies.Dagger
import Dependencies.Kotlin
import Dependencies.Okhttp3

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
    implementation(project(":local"))
    implementation(project(":remote"))

    implementation(Kotlin.stdlib)

    implementation(Dagger.dagger)
    kapt(Dagger.compiler)

    implementation(Okhttp3.okhttp3)
}
