package com.example.kmpuniversalapp

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform