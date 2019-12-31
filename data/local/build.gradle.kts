plugins {
    id("kotlin")
    id("kotlin-kapt")
}

dependencies {
    implementation(KOTLIN_STDLIB)
    implementation(KOTLIN_COROUTINES_CORE)

    implementation(ANDROIDX_ROOM_COMMON)

    implementation(MOSHI)
    kapt(MOSHI_CODEGEN)
}
