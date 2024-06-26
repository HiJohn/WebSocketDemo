plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin)
}

android {
    namespace  = "me.jscall"
    compileSdk  = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        minSdk  = libs.versions.minSdk.get().toInt()

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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

    buildFeatures{
        viewBinding = true
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.androidx.ktx)
    implementation(libs.constraintlayout)
    implementation(libs.material)
    implementation(project(":utils"))

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidXJunit)
    androidTestImplementation(libs.androidXEspresso)
}