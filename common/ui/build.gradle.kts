plugins {
    id("com.android.library")
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

    buildFeatures {
        resValues = true
        viewBinding = true
    }
}

dependencies {
    implementation(KOTLIN_STDLIB)
    implementation(KOTLIN_COROUTINES_CORE)

    implementation(ANDROIDX_ACTIVITY)
    implementation(ANDROIDX_APPCOMPAT)
    implementation(ANDROIDX_CONSTRAINTLAYOUT)
    implementation(ANDROIDX_CORE)
    implementation(ANDROIDX_FRAGMENT)
    implementation(ANDROIDX_RECYCLERVIEW)

    implementation(COIL)

    implementation(CYCLER)

    implementation(MATERIAL)
}
