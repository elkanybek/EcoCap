plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)

    id("com.google.devtools.ksp") version "1.9.20-1.0.14"

    id("com.google.dagger.hilt.android") version "2.50" apply false
    id("com.google.protobuf") version "0.9.4" apply false

    val IS_ANDROID_BUILD = true
}


android {
    namespace = "com.example.ecocap"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.ecocap"
        minSdk = 24
        targetSdk = 34
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
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
            excludes += "**/attach_hotspot_windows.dll"
            excludes += "META-INF/licenses/**"
            excludes += "META-INF/LICENSE.md"
            excludes += "META-INF/LICENSE-notice.md"
        }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.4"
    }
}

dependencies {


    //navigation
    implementation("androidx.navigation:navigation-compose:2.8.2")

    implementation(libs.androidx.core.ktx)


    //Room
    ksp("androidx.room:room-compiler:${rootProject.extra["room_version"]}")
    implementation(libs.androidx.room.runtime)
    ksp(libs.androidx.room.compiler)
    implementation(libs.androidx.room.ktx)
    implementation(libs.androidx.room.runtime)
    annotationProcessor(libs.androidx.room.compiler)

    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)

    // ml-kit
    implementation("com.google.mlkit:object-detection:17.0.1")
    implementation("com.google.mlkit:image-labeling:17.0.8")

    implementation(libs.vision.common)
    implementation(libs.image.labeling.common)
    implementation(libs.androidx.navigation.runtime.ktx)


    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    implementation(libs.coil.compose)
}
}

