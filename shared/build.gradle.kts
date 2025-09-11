import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpackConfig

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    id("kotlin-parcelize")
}

kotlin {
    androidTarget {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }
    
    iosArm64()
    iosSimulatorArm64()
    
    // 暂时禁用JVM平台，避免expect/actual实现问题
    // jvm()
    
    // 添加编译器选项来抑制expect/actual警告
    targets.all {
        compilations.all {
            compileTaskProvider.configure {
                compilerOptions {
                    freeCompilerArgs.add("-Xexpect-actual-classes")
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
            
            // 导航 - PreCompose (专为KMP设计的导航库)
            implementation("moe.tlaster:precompose:1.6.0")
            
            // 状态管理 - Essenty (ViewModel 需要)
            implementation("com.arkivanov.essenty:lifecycle:2.1.0")
            implementation("com.arkivanov.essenty:back-handler:2.1.0")
            implementation("com.arkivanov.essenty:state-keeper:2.1.0")
            
            // 图片加载 - Ktor Image Loader
            implementation("io.ktor:ktor-client-resources:${libs.versions.ktor.get()}")
            
            // 存储 - DataStore (替代SharedPreferences)
            implementation("androidx.datastore:datastore-preferences-core:1.1.1")
            
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
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
            
            // 协程测试
            implementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.8.0")
        }
        
        androidMain.dependencies {
            implementation("io.ktor:ktor-client-android:${libs.versions.ktor.get()}")
            implementation("com.arkivanov.decompose:extensions-compose-jetbrains:2.2.0")
        }
        
        iosMain.dependencies {
            implementation("io.ktor:ktor-client-darwin:${libs.versions.ktor.get()}")
            
            // iOS 特定的 Compose 依赖
            implementation(compose.ui)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.runtime)
        }
        
        jvmMain.dependencies {
            implementation("io.ktor:ktor-client-cio:${libs.versions.ktor.get()}")
            implementation("com.arkivanov.decompose:extensions-compose-jetbrains:2.2.0")
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
