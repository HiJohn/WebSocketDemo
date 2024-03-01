plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin)
//    id("kotlin-kapt")
}
android {
    compileSdk = libs.versions.compileSdk.get().toInt()
    namespace = "com.exam.myapp"
    defaultConfig {
        applicationId = "com.exam.myapp"
        minSdk = libs.versions.compileSdk.get().toInt()
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

    buildFeatures {
//        dataBinding = true
        viewBinding = true
        buildConfig = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }

}

dependencies {
//    implementation (fileTree(dir: "libs", include: ["*.jar"]))
    implementation(libs.appcompat)
    implementation(libs.androidx.ktx)
    implementation(libs.constraintlayout)
    implementation(libs.material)

    implementation(libs.bundles.okhttp)
    implementation(libs.bundles.lifecycle)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidXJunit)
    androidTestImplementation(libs.androidXEspresso)

    implementation(project(":utils"))
    implementation(project(":netstatelib"))
    implementation(project(":jscall"))
    implementation(project(":navs"))
    implementation(project(":dbfile"))

    implementation("androidx.core:core-splashscreen:1.0.0")

}
