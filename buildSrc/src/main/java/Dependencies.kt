object Dependencies {

    object Kotlin {
        const val version = "1.3.40"
        const val stdlib = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:$version"

        object Coroutines {
            private const val version = "1.2.1"
            const val core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$version"
            const val android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:$version"
        }
    }

    object Android {
        const val activity = "androidx.activity:activity:1.0.0-beta01"
        const val appCompat = "androidx.appcompat:appcompat:1.1.0-beta01"
        const val constraintLayout = "androidx.constraintlayout:constraintlayout:2.0.0-beta2"
        const val core = "androidx.core:core-ktx:1.2.0-alpha02"
        const val fragment = "androidx.fragment:fragment:1.1.0-beta01"
        const val preference = "androidx.preference:preference:1.1.0-beta01"
        const val recyclerView = "androidx.recyclerview:recyclerview:1.1.0-alpha06"

        object Lifecycle {
            private const val version = "2.2.0-alpha01"
            const val livedata = "androidx.lifecycle:lifecycle-livedata-ktx:$version"
            const val runtime = "androidx.lifecycle:lifecycle-runtime-ktx:$version"
            const val viewmodel = "androidx.lifecycle:lifecycle-viewmodel-ktx:$version"
        }

        object Navigation {
            const val version = "2.1.0-alpha05"
            const val common = "androidx.navigation:navigation-common-ktx:$version"
            const val fragment = "androidx.navigation:navigation-fragment-ktx:$version"
            const val runtime = "androidx.navigation:navigation-runtime-ktx:$version"
            const val ui = "androidx.navigation:navigation-ui-ktx:$version"
        }

        object Paging {
            const val runtime = "androidx.paging:paging-runtime-ktx:2.1.0"
        }

        object Room {
            private const val version = "2.1.0"
            const val core = "androidx.room:room-ktx:$version"
            const val compiler = "androidx.room:room-compiler:$version"
        }
    }

    object Material {
        const val material = "com.google.android.material:material:1.1.0-alpha07"
    }

    object Dagger {
        private const val version = "2.23.2"
        const val dagger = "com.google.dagger:dagger:$version"
        const val compiler = "com.google.dagger:dagger-compiler:$version"
        const val android = "com.google.dagger:dagger-android:$version"
        const val androidSupport = "com.google.dagger:dagger-android-support:$version"
        const val androidProcessor = "com.google.dagger:dagger-android-processor:$version"
    }

    object Okhttp3 {
        const val okhttp3 = "com.squareup.okhttp3:okhttp:3.14.1"
    }

    object Moshi {
        private const val version = "1.8.0"
        const val moshi = "com.squareup.moshi:moshi:$version"
        const val kotlinCodegen = "com.squareup.moshi:moshi-kotlin-codegen:$version"
    }

    object Retrofit {
        private const val version = "2.6.0"
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

    object Stetho {
        private const val version = "1.5.1"
        const val stetho = "com.facebook.stetho:stetho:$version"
        const val okhttp3 = "com.facebook.stetho:stetho-okhttp3:$version"
    }

    object LeakCanary {
        const val leakCanary = "com.squareup.leakcanary:leakcanary-android:2.0-alpha-1"
    }
}