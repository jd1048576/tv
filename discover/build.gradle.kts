import Dependencies.Android
import Dependencies.Dagger
import Dependencies.Kotlin
import Dependencies.Material
import Dependencies.Timber

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
        testInstrumentationRunner = Config.testRunner
    }

    compileOptions {
        targetCompatibility = JavaVersion.VERSION_1_8
        sourceCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {
    implementation(project(":base"))
    implementation(project(":ui"))
    implementation(project(":app"))

    implementation(Kotlin.stdlib)

    implementation(Android.appCompat)
    implementation(Android.activity)
    implementation(Android.fragment)

    implementation(Android.Navigation.fragment)

    implementation(Material.material)

    implementation(Dagger.dagger)
    kapt(Dagger.compiler)

    implementation(Timber.timber)
}
