plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-android-extensions")
    id("kotlin-kapt")
}

android {
    compileSdkVersion(Deps.Android.compileSdkVersion)
    buildToolsVersion(Deps.Android.buildToolsVersion)
    defaultConfig {
        applicationId(Deps.Android.applicationId)
        minSdkVersion(Deps.Android.minSdkVersion)
        targetSdkVersion(Deps.Android.targetSdkVersion)
        versionCode = Deps.Android.versionCode
        versionName = Deps.Android.versionName

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }
    dexOptions {
        jumboMode = true
    }
    lintOptions {
        isCheckReleaseBuilds = false
        isAbortOnError = false
    }
    buildFeatures {
        dataBinding = true
    }
}

dependencies {
    testImplementation(Deps.Test.junit)
    androidTestImplementation(Deps.Test.junitExt)
    androidTestImplementation(Deps.Test.espressoCore)
    implementation(Deps.Kotlin.stdlib)
    implementation(Deps.AndroidX.coreKtx)
    implementation(Deps.AndroidX.appcompat)
    implementation(Deps.AndroidX.constraintLayout)
    implementation(Deps.material)
}