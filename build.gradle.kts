// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    val kotlinVersion by extra("1.6.10")
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath(Deps.Plugin.gradle)
        classpath(Deps.Kotlin.gradlePlugin)
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion")

        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
        maven("https://jitpack.io")
    }
}

task("clean", Delete::class) {
    delete = setOf(rootProject.buildDir)
}