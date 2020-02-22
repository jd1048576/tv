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
    }
}

dependencies {
    implementation(project(":common:log"))
    implementation(project(":data:local"))
    implementation(project(":data:mapper"))
    implementation(project(":data:remote"))

    implementation(KOTLIN_STDLIB)
    implementation(KOTLIN_COROUTINES_CORE)

    implementation(ANDROIDX_CORE)
    implementation(ANDROIDX_ROOM)
    implementation(ANDROIDX_WORK)

    implementation(DAGGER)
    kapt(DAGGER_COMPILER)

    implementation(RETROFIT)
}
