plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.ksp)
    id("com.google.gms.google-services")
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
    implementation(libs.core.ktx)
    implementation(libs.androidx.espresso.core)
    implementation(libs.androidx.appcompat)

    //  Test
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

      // api
//    implementation(libs.retrofit)
//    implementation(libs.converter.gson)
//    implementation(libs.kotlinx.coroutines.android)
//    implementation(libs.logging.interceptor)

//    material icons extension
    implementation(libs.accompanist.systemuicontroller)
    implementation(libs.androidx.material.icons.extended)

//    implementation(libs.coil.compose)
//    implementation(libs.coil.gif)
//
//    // Koin
    implementation(libs.koin.android)
    implementation(libs.koin.androidx.compose)

//    // Room
//    implementation(libs.androidx.room.runtime)
//    implementation(libs.androidx.room.ktx)
//    ksp(libs.androidx.room.compiler)

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

    // Google Identity
    implementation(libs.google.identity)
    implementation(libs.play.services.auth)

    // Firebase ML Kit & AutoML Vision Edge
    implementation("com.google.firebase:firebase-ml-model-interpreter:22.0.3")
    implementation("com.google.firebase:firebase-ml-vision:24.1.0")
    implementation("com.google.firebase:firebase-ml-vision-automl:19.0.0")

    // TensorFlow Lite (TFLite) for model inference
    implementation("org.tensorflow:tensorflow-lite:2.10.0")

    // CameraX for capturing images
    implementation("androidx.camera:camera-core:1.2.0")
    implementation("androidx.camera:camera-lifecycle:1.2.0")
    implementation("androidx.camera:camera-view:1.2.0")

}