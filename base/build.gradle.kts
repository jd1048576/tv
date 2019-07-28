import Config.Dependencies.Dagger
import Config.Dependencies.Kotlin
import Config.Dependencies.Moshi

plugins {
    id(Config.Plugins.kotlin)
    id(Config.Plugins.kotlinKapt)
}

dependencies {
    implementation(Kotlin.stdlib)
    implementation(Kotlin.Coroutines.core)

    implementation(Dagger.dagger)
    kapt(Dagger.compiler)

    implementation(Moshi.moshi)
}
