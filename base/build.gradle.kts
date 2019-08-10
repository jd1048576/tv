plugins {
    id("kotlin")
    id("kotlin-kapt")
}

dependencies {
    implementation(KOTLIN_STDLIB)
    implementation(KOTLIN_COROUTINES_CORE)

    implementation(DAGGER)
    kapt(DAGGER_COMPILER)

    implementation(MOSHI)
}
