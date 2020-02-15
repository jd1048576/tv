plugins {
    id("com.android.dynamic-feature")
    id("kotlin-android")
    id("kotlin-kapt")
}

android {
    compileSdkVersion(COMPILE_SDK_VERSION)

    defaultConfig {
        minSdkVersion(MIN_SDK_VERSION)
        targetSdkVersion(TARGET_SDK_VERSION)
        testInstrumentationRunner = TEST_INSTRUMENTATION_RUNNER
    }

    compileOptions {
        targetCompatibility = JavaVersion.VERSION_1_8
        sourceCompatibility = JavaVersion.VERSION_1_8
    }

    buildFeatures.apply { // FIXME remove .apply
        buildConfig = true
        resValues = true
        viewBinding = true
    }
}

dependencies {
    implementation(project(":app"))
    implementation(project(":common:navigation"))
    implementation(project(":common:ui"))

    implementation(KOTLIN_STDLIB)

    implementation(ANDROIDX_ACTIVITY)
    implementation(ANDROIDX_APPCOMPAT)
    implementation(ANDROIDX_FRAGMENT)
    implementation(ANDROIDX_NAVIGATION_FRAGMENT)

    implementation(DAGGER)
    kapt(DAGGER_COMPILER)

    implementation(MATERIAL)
}
