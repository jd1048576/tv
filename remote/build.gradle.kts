plugins {
    id("kotlin")
    id("kotlin-kapt")
}

dependencies {
    implementation(project(":base"))

    implementation(KOTLIN_STDLIB)
    implementation(KOTLIN_COROUTINES_CORE)

    implementation(DAGGER)
    kapt(DAGGER_COMPILER)

    implementation(MOSHI)
    kapt(MOSHI_CODEGEN)

    implementation(OKHTTP3)

    implementation(RETROFIT)
    implementation(RETROFIT_MOSHI)
}
