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

        buildConfigField("String", "TMDB_API_KEY", "\"${stringProperty("TMDB_API_KEY")}\"")

        javaCompileOptions {
            annotationProcessorOptions {
                arguments = mapOf(
                    "room.schemaLocation" to "$projectDir/schemas",
                    "room.incremental" to "true"
                )
            }
        }
    }

    compileOptions {
        targetCompatibility = JavaVersion.VERSION_1_8
        sourceCompatibility = JavaVersion.VERSION_1_8
    }

    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    implementation(project(":common:log"))
    implementation(project(":data:local"))
    implementation(project(":data:remote"))
    implementation(project(":work"))

    implementation(KOTLIN_STDLIB)

    implementation(ANDROIDX_APPCOMPAT)
    implementation(ANDROIDX_FRAGMENT)
    implementation(ANDROIDX_PREFERENCE)
    implementation(ANDROIDX_ROOM)
    implementation(ANDROIDX_ROOM)
    kapt(ANDROIDX_ROOM_COMPILER)
    implementation(ANDROIDX_WORK)

    implementation(COIL)

    implementation(DAGGER)
    kapt(DAGGER_COMPILER)

    implementation(FIREBASE_CRASHLYTICS)

    implementation(MOSHI)

    implementation(OKHTTP3)

    implementation(RETROFIT)
    implementation(RETROFIT_MOSHI)
}
