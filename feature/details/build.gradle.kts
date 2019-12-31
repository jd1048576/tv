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

    buildFeatures {
        buildConfig = true
        dataBinding = true
        resValues = true
    }
}

dependencies {
    implementation(project(":app"))
    implementation(project(":common:extensions"))
    implementation(project(":common:log"))
    implementation(project(":common:navigation"))
    implementation(project(":common:ui"))
    implementation(project(":common:viewmodel"))
    implementation(project(":data:core"))
    implementation(project(":data:local"))
    implementation(project(":data:mapper"))
    implementation(project(":data:remote"))

    implementation(KOTLIN_STDLIB)
    implementation(KOTLIN_COROUTINES_CORE)

    implementation(ANDROIDX_ACTIVITY)
    implementation(ANDROIDX_APPCOMPAT)
    implementation(ANDROIDX_CONSTRAINTLAYOUT)
    implementation(ANDROIDX_FRAGMENT)
    implementation(ANDROIDX_LIFECYCLE_VIEWMODEL)
    implementation(ANDROIDX_NAVIGATION_COMMON)
    implementation(ANDROIDX_NAVIGATION_FRAGMENT)
    implementation(ANDROIDX_NAVIGATION_RUNTIME)
    implementation(ANDROIDX_NAVIGATION_UI)
    implementation(ANDROIDX_RECYCLERVIEW)
    implementation(ANDROIDX_ROOM)
    implementation(ANDROIDX_VIEWPAGER2)

    implementation(COIL)

    implementation(DAGGER)
    kapt(DAGGER_COMPILER)

    implementation(MATERIAL)

    implementation(RETROFIT)
}
