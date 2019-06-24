import Dependencies.Dagger
import Dependencies.Kotlin
import Dependencies.Moshi
import Dependencies.Okhttp3
import Dependencies.Retrofit
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

        buildConfigField("String", "TMDB_API_KEY", "\"${localProperty("TMDB_API_KEY")}\"")
    }

    buildTypes {
        getByName("release") {
            consumerProguardFiles("okhttp3-proguard-rules.pro", "retrofit-proguard-rules.pro")
        }
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
