plugins {
    id("com.android.dynamic-feature")
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
    implementation(project(":app"))
    implementation(project(":base"))
    implementation(project(":navigation"))
    implementation(project(":ui"))

    implementation(KOTLIN_STDLIB)

    implementation(ANDROIDX_ACTIVITY)
    implementation(ANDROIDX_APPCOMPAT)
    implementation(ANDROIDX_FRAGMENT)
    implementation(ANDROIDX_NAVIGATION_FRAGMENT)

    implementation(DAGGER)
    kapt(DAGGER_COMPILER)

    implementation(MATERIAL)
}
