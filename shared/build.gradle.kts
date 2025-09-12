import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpackConfig

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    id("kotlin-parcelize")
    kotlin("native.cocoapods")
//    // KuiklyUI插件 (暂时注释，因为插件可能未发布)
//    // id("com.tencent.kuikly-open.kuikly")
}

repositories {
    google()
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    maven("https://mirrors.tencent.com/nexus/repository/maven-public/")
    gradlePluginPortal()
}

kotlin {
    // Android目标
    androidTarget {
        publishLibraryVariantsGroupedByFlavor = true
        publishLibraryVariants("release", "debug")
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }
    
    // iOS目标
    iosX64()
    iosArm64()
    iosSimulatorArm64()
    
    // JVM目标
    jvm()
    
//    // CocoaPods配置
//    cocoapods {
//        summary = "KMP Universal App Shared Module"
//        homepage = "https://github.com/your-org/kmp-universal-app"
//        version = "1.0.0"
//        ios.deploymentTarget = "14.1"
//        podfile = project.file("../iosApp/Podfile")
//        framework {
//            baseName = "shared"
//            isStatic = true
//            license = "MIT"
//        }
//        extraSpecAttributes["resources"] = "['src/commonMain/assets/**']"
//    }
    
    // 添加编译器选项来抑制警告
    targets.all {
        compilations.all {
            compileTaskProvider.configure {
                compilerOptions {
                    freeCompilerArgs.add("-Xexpect-actual-classes")
                    // 抑制 iOS 平台特定的警告
                    if (this@all.name.contains("ios", ignoreCase = true)) {
                        freeCompilerArgs.add("-Xsuppress-version-warnings")
                        freeCompilerArgs.add("-Xallow-unstable-dependencies")
                    }
                }
            }
        }
    }
    
    // 暂时移除WASM目标，避免依赖兼容性问题
    // @OptIn(ExperimentalWasmDsl::class)
    // wasmJs {
    //     browser()
    // }
    
    sourceSets {
        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting
        val iosMain by creating {
            dependsOn(sourceSets.getByName("commonMain"))
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)
        }
        
        val iosX64Test by getting
        val iosArm64Test by getting
        val iosSimulatorArm64Test by getting
        val iosTest by creating {
            dependsOn(sourceSets.getByName("commonTest"))
            iosX64Test.dependsOn(this)
            iosArm64Test.dependsOn(this)
            iosSimulatorArm64Test.dependsOn(this)
        }
        
        commonMain.dependencies {
            // 网络请求 - Ktor
            implementation("io.ktor:ktor-client-core:${libs.versions.ktor.get()}")
            implementation("io.ktor:ktor-client-content-negotiation:${libs.versions.ktor.get()}")
            implementation("io.ktor:ktor-serialization-kotlinx-json:${libs.versions.ktor.get()}")
            implementation("io.ktor:ktor-client-logging:${libs.versions.ktor.get()}")
            
            // 序列化
            implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.7.3")
            
            // 协程
            implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:${libs.versions.kotlinx.coroutines.get()}")
            
            // 日期时间
            implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.6.1")
            
            // 简化的状态管理 - 只保留必要的依赖
            implementation("com.arkivanov.essenty:lifecycle:2.1.0")
            
            // 图片加载 - Ktor Image Loader
            implementation("io.ktor:ktor-client-resources:${libs.versions.ktor.get()}")
            
            // 存储 - DataStore (替代SharedPreferences) - 暂时注释，iOS平台下载有问题
            // implementation("androidx.datastore:datastore-preferences-core:1.1.1")
            
            // 日志 - Kermit (专为KMP设计)
            implementation("co.touchlab:kermit:2.0.4")
            
            // 依赖注入 - Koin (轻量级DI框架)
            implementation("io.insert-koin:koin-core:3.5.0")
            implementation("io.insert-koin:koin-compose:1.1.0")
            
            
            // Compose Multiplatform
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.animation)
            implementation(compose.animationGraphics)
            
            // Material Icons for KMP - 使用正确的依赖
            implementation("org.jetbrains.compose.material:material-icons-extended:1.7.1")
            
            // 移除KuiklyUI依赖，使用Material3
            // implementation(libs.kuikly.core)
            // implementation(libs.kuikly.compose)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
            
            // 协程测试
            implementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.8.0")
        }
        
        androidMain.dependencies {
            implementation("io.ktor:ktor-client-android:${libs.versions.ktor.get()}")
        }
        
        iosMain.dependencies {
            implementation("io.ktor:ktor-client-darwin:${libs.versions.ktor.get()}")
            // implementation("com.tencent.kuikly-open:core:${libs.versions.kuikly.get()}")
            // iOS 特定的 Compose 依赖 - 使用更稳定的版本
            implementation(compose.ui)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.runtime)
            implementation(compose.components.resources)
            implementation(compose.animation)
            
            // iOS 特定的 Material Icons - 避免 SkikoKey 问题
            implementation("org.jetbrains.compose.material:material-icons-core:1.7.1")
        }
        
        jvmMain.dependencies {
            implementation("io.ktor:ktor-client-cio:${libs.versions.ktor.get()}")
        }
        
        jvmTest.dependencies {
            // 测试框架 - Kotest
            implementation("io.kotest:kotest-framework-engine:5.8.1")
            implementation("io.kotest:kotest-assertions-core:5.8.1")
            implementation("io.kotest:kotest-property:5.8.1")
            implementation("io.kotest:kotest-runner-junit5:5.8.1")
            
            // Mock框架 - MockK
            implementation("io.mockk:mockk:1.13.8")
            
            // 依赖注入测试
            implementation("io.insert-koin:koin-test:3.5.0")
            
            // 数据库测试 - 暂时注释
        }
        
        // wasmJsMain.dependencies {
        //     implementation("io.ktor:ktor-client-js:${libs.versions.ktor.get()}")
        // }
    }
}

android {
    namespace = "com.example.kmpuniversalapp.shared"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }
}

// sqldelight {
//     databases {
//         create("AppDatabase") {
//             packageName.set("com.example.kmpuniversalapp")
//             schemaOutputDirectory.set(file("src/commonMain/sqldelight/databases"))
//         }
//     }
// }

// Kuikly插件配置 (暂时注释，因为插件可能未发布)
// kuikly {
//     // JS产物配置
//     js {
//         // 构建产物名
//         outputName("kmp-universal-app")
//         // 可选：分包构建时的页面列表，如果为空则构建全部页面
//         // addSplitPage("route","home")
//     }
// }
