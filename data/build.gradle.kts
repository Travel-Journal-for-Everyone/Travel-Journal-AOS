plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    // Dagger-Hilt
    id("com.google.devtools.ksp")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.tedmoon99.data"
    compileSdk = 35

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        debug {
            buildConfigField("String", "BASE_URL", "\"https://api.travel-journal.shop/\"")
        }

        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            buildConfigField("String", "BASE_URL", "\"https://api.toucheese.shop/\"")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }

    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    val paging_version = "3.3.6"
    val datastore_version = "1.1.4"

    // testImplementation
    testImplementation(libs.junit)
    testImplementation(libs.androidx.paging.common) // Paging 라이브러리

    // androidTestImplementation
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.runner)

    // implementation
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)
    implementation(project(":domain"))
    implementation("androidx.datastore:datastore-preferences:$datastore_version")
    implementation(libs.retrofit)
    implementation(libs.squareup.converter.gson)
    implementation(libs.okhttp3.logging.interceptor)
    implementation(libs.androidx.junit.ktx)
    implementation("com.kakao.sdk:v2-all:2.20.6") // 전체 모듈 설치 (로그인, 공유, 메시지, 피커, 내비, 인증)
    implementation("androidx.paging:paging-runtime:$paging_version")
    implementation("androidx.paging:paging-compose:3.3.6")
}