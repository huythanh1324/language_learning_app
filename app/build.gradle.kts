plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.google.gms.google.services)
}


android {
    namespace = "com.example.languagelearningapp"
    compileSdk = 34


    defaultConfig {
        applicationId = "com.example.languagelearningapp"
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
    }

}


dependencies {
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(libs.firebase.auth)
    implementation(libs.firebase.database)
    implementation(libs.androidx.espresso.intents)
    testImplementation(libs.junit)
    testImplementation(libs.androidx.core)
    testImplementation(libs.androidx.junit)
    testImplementation(libs.androidx.core)
    testImplementation(libs.androidx.core)
    testImplementation(libs.androidx.core)
    testImplementation(libs.androidx.core)
    testImplementation(libs.androidx.core)
    testImplementation(libs.androidx.core)
    testImplementation(libs.androidx.core)
    testImplementation(libs.androidx.core)
    testImplementation(libs.junit.jupiter)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // Các thư viện kiểm thử AndroidX
    testImplementation (libs.androidx.junit)
    testImplementation (libs.androidx.core.v170)
    testImplementation (libs.runner)

    // Mockito cho mock đối tượng trong unit test
    testImplementation (libs.mockito.core)
    // Mockito core cho kiểm thử thiết bị


    // Mockito inline
    androidTestImplementation (libs.mockito.inline)
    // JUnit cho unit test

    testImplementation (libs.mockito.junit.jupiter)
    androidTestImplementation (libs.androidx.espresso.core.v351)

    // Các thư viện kiểm thử khác
    testImplementation(libs.junit)
    testImplementation(libs.androidx.core)
    testImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation (libs.androidx.core.v150)
    androidTestImplementation(libs.androidx.espresso.intents)
    androidTestImplementation(libs.androidx.espresso.intents)


}