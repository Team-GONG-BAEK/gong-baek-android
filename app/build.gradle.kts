plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.hilt.android)
    alias(libs.plugins.ksp)
    alias(libs.plugins.ktlint)
}

android {
    namespace = "com.sopt.gongbaek"
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "com.sopt.gongbaek"
        minSdk = libs.versions.minSdk.get().toInt()
        targetSdk = libs.versions.targetSdk.get().toInt()
        versionCode = libs.versions.versionCode.get().toInt()
        versionName = libs.versions.versionName.get()

        buildConfigField("String", "GONGBAEK_BASE_URL", "\"${project.getLocalProperty("gongbaek.base.url")}\"")
        buildConfigField("String", "KAKAO_NATIVE_APP_KEY", "\"${project.getLocalProperty("kakao.native.app.key")}\"")

        manifestPlaceholders["KAKAO_NATIVE_APP_KEY"] = project.getLocalProperty("kakao.native.app.key")
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    lint {
        disable.add("CoroutineCreationDuringComposition")
    }

//    signingConfigs {
//        create("release") {
//            storeFile = file(project.getLocalProperty(""))
//            storePassword = project.getLocalProperty("")
//            keyAlias = project.getLocalProperty("")
//            keyPassword = project.getLocalProperty("")
//        }
//    }

    buildTypes {
        debug {
            isMinifyEnabled = false
        }
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
//            signingConfig = signingConfig.getByName("release")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = libs.versions.jvmTarget.get()
    }

    buildFeatures {
        compose = true
        buildConfig = true
    }
}

dependencies {
    // Core
    implementation(libs.bundles.core)

    // Kotlin Serialization
    implementation(libs.kotlinx.serialization.json)

    // Jetpack Compose
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.bundles.compose)
    androidTestImplementation(platform(libs.androidx.compose.bom))

    // Dependency Injection (Hilt)
    implementation(libs.bundles.di)
    ksp(libs.hilt.compiler)

    // Networking
    implementation(platform(libs.okhttp.bom))
    implementation(libs.bundles.networking)

    // Logging
    implementation(libs.timber)

    // Testing
    testImplementation(libs.junit)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.bundles.testing)

    // Kakao SDK
    implementation(libs.kakao.user)
}

ktlint {
    android = true
    debug = true
    coloredOutput = true
    verbose = true
    outputToConsole = true
}
