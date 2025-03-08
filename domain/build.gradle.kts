plugins {
    id("java-library")
    alias(libs.plugins.jetbrains.kotlin.jvm)
    id("com.google.devtools.ksp")
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

kotlin {
    compilerOptions {
        jvmTarget = org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_11
    }
}

dependencies {
    // Dagger Core (순수 Kotlin 모듈에서 Dagger 사용)
    implementation(libs.dagger)
    ksp(libs.dagger.compiler)

    // Kotlin Coroutines (비동기 처리)
}