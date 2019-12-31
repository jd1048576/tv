plugins {
    id("kotlin")
}

dependencies {
    implementation(project(":data:local"))
    implementation(project(":data:remote"))

    implementation(KOTLIN_STDLIB)
}
