plugins {
    id("kotlin")
    id("kotlin-kapt")
}

dependencies {
    implementation(project(":common:extensions"))

    implementation(KOTLIN_STDLIB)
    implementation(KOTLIN_COROUTINES_CORE)

    implementation(ANDROIDX_ROOM_COMMON)

    implementation(MOSHI)
    kapt(MOSHI_CODEGEN)
}
