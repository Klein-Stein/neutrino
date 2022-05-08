plugins {
    kotlin("multiplatform")
    kotlin("native.cocoapods")
    id("convention.publication")
    id("com.android.library")
    id("org.jetbrains.dokka")
}

group = "io.github.klein-stein"
version = "2.0"

kotlin {
    android()
    iosX64()
    iosArm64()
    iosSimulatorArm64()

    android {
        publishLibraryVariants("release", "debug")
    }

    cocoapods {
        summary = "Simple DI for KMM"
        homepage = "https://klein-stein.github.io/neutrino/"
        ios.deploymentTarget = "14.1"
        framework {
            baseName = "neutrino"
        }
    }

    sourceSets {
        val commonMain by getting
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        val androidMain by getting
        val androidTest by getting
        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting
        val iosMain by creating {
            dependsOn(commonMain)
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)
        }
        val iosX64Test by getting
        val iosArm64Test by getting
        val iosSimulatorArm64Test by getting
        val iosTest by creating {
            dependsOn(commonTest)
            iosX64Test.dependsOn(this)
            iosArm64Test.dependsOn(this)
            iosSimulatorArm64Test.dependsOn(this)
        }
    }
}

android {
    compileSdk = 32
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    defaultConfig {
        minSdk = 16
        targetSdk = 32
    }
}

tasks.dokkaHtml.configure {
    outputDirectory.set(buildDir.resolve("dokkaHtml"))
}
