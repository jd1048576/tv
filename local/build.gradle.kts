plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-kapt")
}

android {
    compileSdkVersion(COMPILE_SDK_VERSION)
    buildToolsVersion(BUILD_TOOLS_VERSION)

    defaultConfig {
        minSdkVersion(MIN_SDK_VERSION)
        targetSdkVersion(TARGET_SDK_VERSION)
        testInstrumentationRunner = TEST_INSTRUMENTATION_RUNNER
    }

    compileOptions {
        targetCompatibility = JavaVersion.VERSION_1_8
        sourceCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {
    implementation(project(":base"))

    implementation(KOTLIN_STDLIB)
    implementation(KOTLIN_COROUTINES_CORE)

    implementation(ANDROIDX_ROOM)
    kapt(ANDROIDX_ROOM_COMPILER)

    implementation(DAGGER)
    kapt(DAGGER_COMPILER)

    implementation(MOSHI)
}
