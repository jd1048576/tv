import Config.Dependencies.Dagger
import Config.Dependencies.Kotlin
import Config.Dependencies.Moshi
import Config.Dependencies.Okhttp3
import Config.Dependencies.Retrofit
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

        buildConfigField("String", "TMDB_API_KEY", "\"${localProperty("TMDB_API_KEY")}\"")
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

    implementation(Dagger.dagger)
    kapt(Dagger.compiler)

    implementation(Okhttp3.okhttp3)

    implementation(Moshi.moshi)
    kapt(Moshi.kotlinCodegen)

    implementation(Retrofit.retrofit)
    implementation(Retrofit.converterMoshi)

    implementation(Timber.timber)
}
