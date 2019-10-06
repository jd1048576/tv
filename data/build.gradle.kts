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

        buildConfigField("String", "TMDB_API_KEY", "\"${gradleProperty("TMDB_API_KEY")}\"")
    }

    compileOptions {
        targetCompatibility = JavaVersion.VERSION_1_8
        sourceCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {
    implementation(project(":base"))
    implementation(project(":local"))
    implementation(project(":remote"))
    implementation(project(":work"))

    implementation(KOTLIN_STDLIB)

    implementation(ANDROIDX_APPCOMPAT)
    implementation(ANDROIDX_PREFERENCE)
    implementation(ANDROIDX_ROOM)
    implementation(ANDROIDX_WORK)

    implementation(COIL)

    implementation(DAGGER)
    kapt(DAGGER_COMPILER)

    implementation(FIREBASE_CRASHLYTICS)

    implementation(OKHTTP3)

    implementation(RETROFIT)
}
