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

    signingConfigs {
        create("release") {
            storeFile = rootProject.file(project.getLocalProperty("storeFile"))
            storePassword = project.getLocalProperty("storePassword")
            keyAlias = project.getLocalProperty("keyAlias")
            keyPassword = project.getLocalProperty("keyPassword")
        }
    }

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
            signingConfig = signingConfigs.getByName("release")
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
    implementation(platform(libs.androidx.compose.bom)) // Jetpack Compose BOM 설정
    implementation(libs.bundles.compose) // Jetpack Compose 관련 라이브러리들
    androidTestImplementation(platform(libs.androidx.compose.bom)) // Compose UI 테스트를 위한 BOM

    // DataStore
    implementation(libs.bundles.datastore)

    // Dependency Injection (Hilt)
    implementation(libs.bundles.di) // Hilt 관련 라이브러리들
    ksp(libs.hilt.compiler) // Hilt 컴파일러를 위한 KSP

    // Networking
    implementation(platform(libs.okhttp.bom)) // OkHttp BOM 설정
    implementation(libs.bundles.networking) // Retrofit과 OkHttp 관련 라이브러리들

    // Logging
    implementation(libs.timber)

    // Debug Dependencies (UI Tooling for Debugging)
    debugImplementation(libs.bundles.debug) // 디버깅을 위한 UI 툴링 관련 라이브러리들

    // Testing
    testImplementation(libs.junit) // JUnit 라이브러리
    androidTestImplementation(libs.bundles.testing) // Android UI 테스트 관련 라이브러리들

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
