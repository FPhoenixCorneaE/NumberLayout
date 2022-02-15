plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-kapt")
}

android {
    compileSdk = Deps.Android.compileSdkVersion
    buildToolsVersion = Deps.Android.buildToolsVersion
    defaultConfig {
        applicationId = Deps.Android.applicationId
        minSdk = Deps.Android.minSdkVersion
        targetSdk = Deps.Android.targetSdkVersion
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
    lint {
        isCheckReleaseBuilds = false
        isAbortOnError = false
    }
    buildFeatures {
        dataBinding = true
        viewBinding = true
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