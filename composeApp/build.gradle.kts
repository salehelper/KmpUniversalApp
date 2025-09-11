import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpackConfig

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.composeHotReload)
}

kotlin {
    androidTarget {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }
    
    listOf(
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
        }
    }
    
    // 暂时注释掉JVM和Web平台，专注于iOS和Android
    // jvm()
    //
    // // Web平台暂时注释掉，因为依赖兼容性问题
    // @OptIn(ExperimentalWasmDsl::class)
    //     wasmJs {
    //         browser {
    //             commonWebpackConfig {
    //                 outputFileName = "composeApp.js"
    //             }
    //         }
    //         binaries.executable()
    // }
    
    sourceSets {
        androidMain.dependencies {
            implementation(compose.preview)
            implementation(libs.androidx.activity.compose)
        }
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(projects.shared)
            // PreCompose 导航库 - 使用稳定版本
            implementation("moe.tlaster:precompose:1.6.0")
            // Koin 依赖注入
            implementation("io.insert-koin:koin-core:3.5.0")
            implementation("io.insert-koin:koin-compose:1.1.0")
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
        // jvmMain.dependencies {
        //     implementation(compose.desktop.currentOs)
        //     implementation(libs.kotlinx.coroutinesSwing)
        // }
        
        // wasmJsMain.dependencies {
        //     implementation(compose.html.core)
        // }
    }
}

android {
    namespace = "com.example.kmpuniversalapp"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "com.example.kmpuniversalapp"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

configurations.all {
    // 解决Compose UI测试库版本冲突
    exclude(group = "androidx.compose.ui", module = "ui-test-junit4-android")
}

dependencies {
    debugImplementation(compose.uiTooling)
}

// 暂时注释掉Desktop配置，使用Web平台
// compose.desktop {
//     application {
//         mainClass = "com.example.kmpuniversalapp.MainKt"
//         
//         // 添加JVM参数解决Skiko问题
//         jvmArgs += listOf(
//             "-Xmx2g",
//             "-Dskiko.renderApi=SOFTWARE",
//             "-Dskiko.macOS.useMetal=false",
//             "-Dskiko.macOS.useSoftwareRenderer=true"
//         )
//
//         nativeDistributions {
//             targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
//             packageName = "com.example.kmpuniversalapp"
//             packageVersion = "1.0.0"
//         }
//     }
// }
