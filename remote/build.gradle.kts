import Config.Dependencies.Dagger
import Config.Dependencies.Kotlin
import Config.Dependencies.Moshi
import Config.Dependencies.Okhttp3
import Config.Dependencies.Retrofit

plugins {
    id(Config.Plugins.kotlin)
    id(Config.Plugins.kotlinKapt)
}

dependencies {
    implementation(project(":base"))

    implementation(Kotlin.stdlib)
    implementation(Kotlin.Coroutines.core)

    implementation(Dagger.dagger)
    kapt(Dagger.compiler)

    implementation(Okhttp3.okhttp3)

    implementation(Moshi.moshi)
    kapt(Moshi.kotlinCodegen)

    implementation(Retrofit.retrofit)
    implementation(Retrofit.converterMoshi)
}
