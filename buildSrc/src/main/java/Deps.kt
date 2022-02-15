/**
 * @desc：依赖库管理
 * @since：2021-04-02 11:29
 */
object Deps {

    object Plugin {
        const val gradle = "com.android.tools.build:gradle:7.0.3"
    }

    object Android {
        const val compileSdkVersion = 30
        const val buildToolsVersion = "30.0.3"
        const val applicationId = "com.fphoenixcorneae.numberlayout"
        const val minSdkVersion = 21
        const val targetSdkVersion = 30
        const val versionCode = 1
        const val versionName = "1.0"
    }

    object Test {
        const val junit = "junit:junit:4.13.2"
        const val junitExt = "androidx.test.ext:junit:1.1.2"
        const val espressoCore = "androidx.test.espresso:espresso-core:3.3.0"
    }

    object Kotlin {
        private const val kotlinVersion = "1.6.10"
        const val gradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion"
        const val stdlib = "org.jetbrains.kotlin:kotlin-stdlib:$kotlinVersion"
    }

    object AndroidX {
        const val appcompat = "androidx.appcompat:appcompat:1.2.0"
        const val coreKtx = "androidx.core:core-ktx:1.3.2"
        const val constraintLayout = "androidx.constraintlayout:constraintlayout:2.0.4"
    }

    const val material = "com.google.android.material:material:1.3.0"
}