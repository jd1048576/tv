import Config.Dependencies.Android
import Config.Dependencies.Dagger
import Config.Dependencies.Kotlin
import Config.Dependencies.Material
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
}

dependencies {
    implementation(project(":base"))
    implementation(project(":navigation"))
    implementation(project(":ui"))
    implementation(project(":app"))

    implementation(Kotlin.stdlib)

    implementation(Android.activity)
    implementation(Android.appCompat)
    implementation(Android.fragment)
    implementation(Android.Navigation.fragment)

    implementation(Material.material)

    implementation(Dagger.dagger)
    kapt(Dagger.compiler)

    implementation(Timber.timber)
}
