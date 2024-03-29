plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    //id("com.google.devtools.ksp") version "1.8.10-1.0.9" apply false
}



android {
    namespace = "com.study.storeapplication"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.study.storeapplication"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        kapt{
            arguments {arg("room.schemaLocation", "$projectDir/schemas")}
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

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("androidx.activity:activity-ktx:1.8.2")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.navigation:navigation-fragment-ktx:2.7.7")
    implementation("androidx.navigation:navigation-ui-ktx:2.7.7")
    implementation("com.android.identity:identity-credential:20231002")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    implementation ("androidx.lifecycle:lifecycle-livedata-ktx:$2.7.0")
    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0")
    implementation ("androidx.room:room-runtime:2.6.1") // Библиотека "Room"
    kapt ("androidx.room:room-compiler:2.6.1") // Кодогенератор
    implementation ("androidx.room:room-ktx:2.6.1")


    implementation ("com.squareup.picasso:picasso:2.5.2")

    implementation ("com.google.android.gms:play-services-maps:18.1.0")

    //Оплата Google
    implementation ("com.google.android.gms:play-services-wallet:18.1.3")
    //implementation ("com.android.support:support-v4:24.1.1")
    //implementation ("com.android.support:appcompat-v7:24.1.1")
    implementation ("com.google.android.gms:play-services-location:21.2.0")
}
