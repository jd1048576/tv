repositories {
    google()
    jcenter()
}

plugins {
    `kotlin-dsl`
}

dependencies {
    implementation(gradleApi())
    implementation(gradleKotlinDsl())
    implementation("com.android.tools.build:gradle:3.5.0-beta05")
}
