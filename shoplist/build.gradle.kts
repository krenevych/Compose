import com.android.build.api.variant.ResValue

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.hilt)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.example.shoplist"
    compileSdk {
        version = release(37)
    }

    defaultConfig {
        applicationId = "com.example.shoplist"
        minSdk = 26
        targetSdk = 37
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    // Android appends the build type suffix after the flavor suffix.
    buildTypes {
        debug {
            isDebuggable = true
            applicationIdSuffix = ".debug"
            versionNameSuffix = "-debug"
        }
        release {
            applicationIdSuffix = ".release"
            optimization {
                enable = true
                packageScope = setOf("androidx.**", "kotlin.**", "kotlinx.**")
            }
        }
    }

    // Each flavor has its own repository and application ID segment.
    flavorDimensions += "dataSrc"
    productFlavors {
        create("stub") {
            dimension = "dataSrc"
            applicationIdSuffix = ".stub"
            versionNameSuffix = "-stub"
        }
        create("db") {
            dimension = "dataSrc"
            applicationIdSuffix = ".db"
            versionNameSuffix = "-db"
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    buildFeatures {
        buildConfig = true
        compose = true
        resValues = true
    }
}

androidComponents {
    onVariants { variant ->
        val flavor = requireNotNull(variant.flavorName)
        val buildType = requireNotNull(variant.buildType)

        // The manifest uses this resource for both the application and its launcher activity.
        variant.resValues.put(
            variant.makeResValueKey("string", "app_name"),
            ResValue("shoplist ($flavor $buildType)"),
        )

        // Rename the APK through AGP so its output metadata points to the same file.
        variant.outputs.single().outputFileName.set("shoplist-$flavor-$buildType.apk")
    }
}

dependencies {
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
    // Room and its processor are only needed for the db flavor.
    "dbImplementation"(libs.androidx.room.runtime)
    "kspDb"(libs.androidx.room.compiler)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.runtime.compose)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.junit)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
    debugImplementation(libs.androidx.compose.ui.tooling)
}
