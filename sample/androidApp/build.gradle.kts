plugins {
    id("com.android.application")
    kotlin("android")
}

android {
    compileSdk = 32
    defaultConfig {
        applicationId = "io.github.kleinstein.neutrino.sample.android"
        minSdk = 16
        targetSdk = 32
        versionCode = 1
        versionName = "1.0"
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    namespace = "io.github.kleinstein.neutrino.sample.android"
}

dependencies {
    implementation(project(":shared"))
    implementation("io.github.klein-stein:neutrino:2.0")
    implementation("com.google.android.material:material:1.6.0")
    implementation("androidx.appcompat:appcompat:1.4.1")
    implementation("androidx.constraintlayout:constraintlayout:2.1.3")
}