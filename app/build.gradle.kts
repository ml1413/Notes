plugins {
    id("com.android.application")
    id("com.google.dagger.hilt.android")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("com.google.devtools.ksp")
    id("com.google.gms.google-services")


}

android {
    namespace = "com.hutapp.org.notes.hut.android"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.hutapp.org.notes.hut.android"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.06"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
        //localData.now()
        isCoreLibraryDesugaringEnabled = true
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")
    implementation("androidx.activity:activity-compose:1.8.2")
    implementation(platform("androidx.compose:compose-bom:2023.08.00"))
    implementation(platform("androidx.compose:compose-bom:2023.08.00"))
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.08.00"))


    //localData.now()
    coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:2.0.4")
    implementation("com.maxkeppeler.sheets-compose-dialogs:core:1.0.2")
    // CALENDAR
    implementation("com.maxkeppeler.sheets-compose-dialogs:calendar:1.0.2")
    // CLOCK
    implementation("com.maxkeppeler.sheets-compose-dialogs:clock:1.0.2")

    // runtime-livedata
    implementation("androidx.compose.runtime:runtime-livedata:1.5.4")
    // firebase
    implementation("com.google.firebase:firebase-analytics:21.5.0")
    // googleSplashScreen
    implementation("androidx.core:core-splashscreen:1.0.1")
    //lifecycle-viewmodel
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.7.0")
    // navigation-compose
    implementation("androidx.navigation:navigation-compose:2.7.6")
    //Room
    implementation("androidx.room:room-ktx:2.5.0")
    annotationProcessor("androidx.room:room-compiler:2.5.0")
    ksp("androidx.room:room-compiler:2.5.0")
    //Hilt
    implementation("com.google.dagger:hilt-android:2.47")
    kapt("com.google.dagger:hilt-compiler:2.47")

    //accompanist-permissions
    implementation("com.google.accompanist:accompanist-permissions:0.32.0")


    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.08.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
}