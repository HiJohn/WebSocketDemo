plugins{
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin)
}
android {
    namespace  = "com.exam.wslib"

    compileSdk  = libs.versions.compileSdk.get().toInt()
    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()

        testInstrumentationRunner =  "androidx.test.runner.AndroidJUnitRunner"
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

    buildFeatures{
        viewBinding = true
    }

}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs","include" to listOf("*.jar","*.aar"))))

    compileOnly(project(":netstatelib"))
    implementation(libs.bundles.okhttp)
    implementation(libs.androidx.ktx)

    implementation(libs.constraintlayout)

    implementation(libs.appcompat)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidXJunit)
    androidTestImplementation(libs.androidXEspresso)
}
