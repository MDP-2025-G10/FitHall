import java.io.FileInputStream
import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.ksp)
    alias(libs.plugins.google.services)
}

val localProperties = Properties().apply {
    val localPropertiesFile = rootProject.file("local.properties")
    if (localPropertiesFile.exists()) {
        load(FileInputStream(localPropertiesFile))
    }
}

android {
    namespace = "com.example.mdp"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.mdp"
        minSdk = 28
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        multiDexEnabled = true

        val usdaApiKey = localProperties.getProperty("USDA_API_KEY") ?: ""
        val imgurClientSecret = localProperties.getProperty("IMGUR_CLIENT_SECRET") ?: ""
        val imgurClientId = localProperties.getProperty("IMGUR_CLIENT_ID") ?: ""
        buildConfigField("String", "USDA_API_KEY", "\"$usdaApiKey\"")
        buildConfigField("String", "IMGUR_CLIENT_SECRET", "\"$imgurClientSecret\"")
        buildConfigField("String", "IMGUR_CLIENT_ID", "\"$imgurClientId\"")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    packaging {
        resources {
            excludes += "META-INF/INDEX.LIST"
            excludes += "META-INF/DEPENDENCIES"
            excludes += "META-INF/io.netty.versions.properties"
        }
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.navigation.compose.android)
    implementation(libs.androidx.runtime.livedata)
    implementation(libs.androidx.espresso.core)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.runtime)

    //  Test
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // RESTFul API
//    implementation(libs.kotlinx.coroutines.android)
//    implementation(libs.logging.interceptor)
    implementation(libs.retrofit)
    implementation(libs.converter.gson)

    //  Room
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    ksp(libs.androidx.room.compiler)

    //  material icons extension
    implementation(libs.accompanist.systemuicontroller)
    implementation(libs.androidx.material.icons.extended)

    //  Koin
    implementation(libs.koin.android)
    implementation(libs.koin.androidx.compose)

    // Testing dependencies
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    testImplementation(libs.kotlin.test)

    // Firebase dependencies
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.analytics)
    implementation(libs.firebase.firestore)
    implementation(libs.firebase.auth.ktx)

    // Credential Manager
    implementation(libs.androidx.credentials.play.services.auth)
    implementation(libs.androidx.credentials)

    // Google Sign-In
    implementation(libs.googleid)

    //  Vico Recharts
    implementation(libs.vico.compose)
    implementation(libs.vico.compose.m3)

    // Kotlin Coroutines
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android)


    //  work manager
     implementation(libs.androidx.work.runtime.ktx)

    //  gms
    implementation(libs.play.services.auth)

    //  Coil
    implementation(libs.coil.network.okhttp)
    implementation(libs.coil.compose)
    implementation(libs.coil.kt.coil.compose)
    // Firebase ML Kit & AutoML Vision Edge
    implementation("com.google.mlkit:image-labeling-automl:16.2.1")

    // TensorFlow Lite (TFLite) for model inference
    implementation("org.tensorflow:tensorflow-lite:2.10.0")
//    implementation("org.tensorflow:tensorflow-lite-support:2.10.0")

    // CameraX for capturing images
    implementation("androidx.camera:camera-core:1.4.2")
    implementation("androidx.camera:camera-lifecycle:1.4.2")
    implementation("androidx.camera:camera-view:1.4.2")
    implementation("androidx.camera:camera-camera2:1.4.2")

}


