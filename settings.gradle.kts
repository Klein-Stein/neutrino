pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
    }
}

rootProject.name = "neutrino"
include(":neutrino")
includeBuild("convention-plugins")
