plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-kapt")
}

val releaseSigning = file(".signing/app-release.jks").exists()

android {
    compileSdkVersion(COMPILE_SDK_VERSION)

    defaultConfig {
        applicationId = "jdr.tv"
        minSdkVersion(MIN_SDK_VERSION)
        targetSdkVersion(TARGET_SDK_VERSION)
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = TEST_INSTRUMENTATION_RUNNER
    }

    signingConfigs {
        getByName("debug") {
            storeFile = file(".signing/app-debug.jks")
            storePassword = "android"
            keyAlias = "debug"
            keyPassword = "android"
        }
        if (releaseSigning) create("release") {
            storeFile = file(".signing/app-release.jks")
            storePassword = stringProperty("TV_RELEASE_STORE_PASSWORD")
            keyAlias = stringProperty("TV_RELEASE_KEY_ALIAS")
            keyPassword = stringProperty("TV_RELEASE_KEY_PASSWORD")
        }
    }

    buildTypes {
        getByName("debug") {
            signingConfig = signingConfigs.getByName("debug")
            applicationIdSuffix = ".debug"
            isDebuggable = true
        }
        getByName("release") {
            signingConfig = signingConfigs.getByName(if (releaseSigning) "release" else "debug")
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
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

    packagingOptions {
        exclude("/**/*.pro")
        exclude("/*.properties")
        exclude("/fabric/*.properties")
        exclude("/META-INF/*.version")
    }

    dynamicFeatures = mutableSetOf(
        ":feature:details",
        ":feature:discover",
        ":feature:schedule",
        ":feature:search",
        ":feature:settings",
        ":feature:shows",
        ":feature:watchlist"
    )
}

dependencies {
    implementation(project(":common:extensions"))
    implementation(project(":common:log"))
    implementation(project(":common:navigation"))
    implementation(project(":common:ui"))
    implementation(project(":common:viewmodel"))
    implementation(project(":data:core"))

    implementation(KOTLIN_STDLIB)
    implementation(KOTLIN_COROUTINES_CORE)
    implementation(KOTLIN_COROUTINES_ANDROID)

    implementation(ANDROIDX_APPCOMPAT)
    implementation(ANDROIDX_ACTIVITY)
    implementation(ANDROIDX_CONSTRAINTLAYOUT)
    implementation(ANDROIDX_CORE)
    implementation(ANDROIDX_FRAGMENT)
    implementation(ANDROIDX_LIFECYCLE_VIEWMODEL)
    implementation(ANDROIDX_NAVIGATION_COMMON)
    implementation(ANDROIDX_NAVIGATION_FRAGMENT)
    implementation(ANDROIDX_NAVIGATION_RUNTIME)
    implementation(ANDROIDX_NAVIGATION_UI)

    implementation(DAGGER)
    kapt(DAGGER_COMPILER)

    implementation(FIREBASE_CORE)
    implementation(FIREBASE_CRASHLYTICS)

    implementation(MATERIAL)

    implementation(OKHTTP3)
}

if (file("google-services.json").exists()) {
    apply(plugin = "com.google.gms.google-services")
    apply(plugin = "io.fabric")
}
