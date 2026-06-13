plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("androidx.navigation.safeargs.kotlin")
}

android {
    namespace = "com.sixyears.onestory"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.sixyears.onestory"
        minSdk = 31
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        // Set your girlfriend's birthday here (month is 1-12)
        buildConfigField("int", "BIRTHDAY_MONTH", "8")
        buildConfigField("int", "BIRTHDAY_DAY", "15")
        buildConfigField("int", "RELATIONSHIP_START_YEAR", "2018")
        buildConfigField("int", "RELATIONSHIP_START_MONTH", "8")
        buildConfigField("int", "RELATIONSHIP_START_DAY", "15")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    buildFeatures {
        viewBinding = true
        buildConfig = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {
    implementation("androidx.core:core-ktx:1.13.1")
    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("com.google.android.material:material:1.12.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")

    // Lifecycle / MVVM
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.4")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.8.4")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.4")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.8.1")

    // Navigation
    implementation("androidx.navigation:navigation-fragment-ktx:2.7.7")
    implementation("androidx.navigation:navigation-ui-ktx:2.7.7")

    // WorkManager
    implementation("androidx.work:work-runtime-ktx:2.9.1")

    // RecyclerView
    implementation("androidx.recyclerview:recyclerview:1.3.2")
    implementation("androidx.cardview:cardview:1.0.0")

    // Lottie
    implementation("com.airbnb.android:lottie:6.4.0")

    // Image zoom/view pager
    implementation("androidx.viewpager2:viewpager2:1.1.0")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.2.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")
}
