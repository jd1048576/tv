object Config {
    const val minSdkVersion = 26
    const val targetSdkVersion = 29
    const val compileSdkVersion = 29
    const val buildToolsVersion = "29.0.1"
    const val testRunner = "androidx.test.runner.AndroidJUnitRunner"

    const val kotlinVersion = "1.3.41"

    object Plugins {
        const val androidGradle = "com.android.tools.build:gradle:3.5.0-rc02"
        const val kotlinGradle = "org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion"
        const val googleServicesGradle = "com.google.gms:google-services:4.3.0"
        const val fabricGradle = "io.fabric.tools:gradle:1.31.0"

        const val androidApplication = "com.android.application"
        const val androidLibrary = "com.android.library"
        const val androidDynamicFeature = "com.android.dynamic-feature"
        const val kotlin = "kotlin"
        const val kotlinAndroid = "kotlin-android"
        const val kotlinKapt = "kotlin-kapt"
        const val googleServices = "com.google.gms.google-services"
        const val fabric = "io.fabric"
    }

    object Dependencies {

        object Kotlin {
            const val stdlib = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlinVersion"

            object Coroutines {
                private const val version = "1.2.1"
                const val core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$version"
                const val android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:$version"
            }
        }

        object Android {
            const val activity = "androidx.activity:activity:1.1.0-alpha01"
            const val appCompat = "androidx.appcompat:appcompat:1.1.0-rc01"
            const val constraintLayout = "androidx.constraintlayout:constraintlayout:2.0.0-beta2"
            const val core = "androidx.core:core-ktx:1.2.0-alpha02"
            const val fragment = "androidx.fragment:fragment-ktx:1.2.0-alpha01"
            const val preference = "androidx.preference:preference:1.1.0-rc01"
            const val recyclerView = "androidx.recyclerview:recyclerview:1.1.0-beta01"

            object Lifecycle {
                private const val version = "2.2.0-alpha02"
                const val livedata = "androidx.lifecycle:lifecycle-livedata-ktx:$version"
                const val runtime = "androidx.lifecycle:lifecycle-runtime-ktx:$version"
                const val viewmodel = "androidx.lifecycle:lifecycle-viewmodel-ktx:$version"
            }

            object Navigation {
                const val version = "2.1.0-beta02"
                const val common = "androidx.navigation:navigation-common-ktx:$version"
                const val fragment = "androidx.navigation:navigation-fragment-ktx:$version"
                const val runtime = "androidx.navigation:navigation-runtime-ktx:$version"
                const val ui = "androidx.navigation:navigation-ui-ktx:$version"
            }

            object Room {
                private const val version = "2.1.0"
                const val core = "androidx.room:room-ktx:$version"
                const val compiler = "androidx.room:room-compiler:$version"
            }
        }

        object Firebase {
            const val core = "com.google.firebase:firebase-core:17.0.1"
            const val crashlytics = "com.crashlytics.sdk.android:crashlytics:2.10.1"
        }

        object Material {
            const val material = "com.google.android.material:material:1.1.0-alpha09"
        }

        object Dagger {
            private const val version = "2.24"
            const val dagger = "com.google.dagger:dagger:$version"
            const val compiler = "com.google.dagger:dagger-compiler:$version"
        }

        object Okhttp3 {
            const val okhttp3 = "com.squareup.okhttp3:okhttp:4.0.1"
        }

        object Moshi {
            private const val version = "1.8.0"
            const val moshi = "com.squareup.moshi:moshi:$version"
            const val kotlinCodegen = "com.squareup.moshi:moshi-kotlin-codegen:$version"
        }

        object Retrofit {
            private const val version = "2.6.1"
            const val retrofit = "com.squareup.retrofit2:retrofit:$version"
            const val converterMoshi = "com.squareup.retrofit2:converter-moshi:$version"
        }

        object Glide {
            private const val version = "4.9.0"
            const val glide = "com.github.bumptech.glide:glide:$version"
            const val compiler = "com.github.bumptech.glide:compiler:$version"
            const val okhttp3 = "com.github.bumptech.glide:okhttp3-integration:$version"
        }

        object Timber {
            const val timber = "com.jakewharton.timber:timber:4.7.1"
        }

        /*object LeakCanary {
            const val leakCanary = "com.squareup.leakcanary:leakcanary-android:2.0-alpha-1"
        }*/
    }
}
