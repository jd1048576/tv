plugins {
    id("com.android.library")
    id("kotlin-android")
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

    buildFeatures {
        resValues = true
    }
}

dependencies {
    implementation(KOTLIN_STDLIB)

    implementation(ANDROIDX_NAVIGATION_COMMON)
    implementation(ANDROIDX_NAVIGATION_UI)
}
