#!/bin/bash

# KMP Universal App 运行脚本
# 使用方法: ./run.sh [platform]
# 平台选项: android, desktop, web, ios, all

PLATFORM=${1:-all}

echo "🚀 启动 KMP Universal App - 平台: $PLATFORM"

# 检查Gradle是否可用
if ! command -v ./gradlew &> /dev/null; then
    echo "❌ 错误: 找不到 gradlew 文件"
    exit 1
fi

# 同步项目
echo "📦 同步项目依赖..."
./gradlew build

case $PLATFORM in
    "android")
        echo "📱 启动 Android 应用..."
        ./gradlew :composeApp:runDebugAndroid
        ;;
    "desktop")
        echo "🖥️ 启动 Desktop 应用..."
        ./gradlew :composeApp:runDesktop
        ;;
    "web")
        echo "🌐 启动 Web 应用..."
        ./gradlew :composeApp:jsBrowserDevelopmentRun
        ;;
    "ios")
        echo "🍎 启动 iOS 应用..."
        echo "注意: iOS 应用需要在 Xcode 中运行"
        ./gradlew :composeApp:buildXCFramework
        echo "✅ iOS 框架构建完成，请在 Xcode 中打开 iosApp 项目"
        ;;
    "all")
        echo "🔄 启动所有平台..."
        echo "1. 启动 Desktop 应用..."
        ./gradlew :composeApp:runDesktop &
        DESKTOP_PID=$!
        
        echo "2. 启动 Web 应用..."
        ./gradlew :composeApp:jsBrowserDevelopmentRun &
        WEB_PID=$!
        
        echo "3. 启动 Android 应用..."
        ./gradlew :composeApp:runDebugAndroid &
        ANDROID_PID=$!
        
        echo "✅ 所有平台已启动"
        echo "按 Ctrl+C 停止所有应用"
        
        # 等待用户中断
        trap "kill $DESKTOP_PID $WEB_PID $ANDROID_PID 2>/dev/null; exit" INT
        wait
        ;;
    *)
        echo "❌ 未知平台: $PLATFORM"
        echo "可用平台: android, desktop, web, ios, all"
        exit 1
        ;;
esac
