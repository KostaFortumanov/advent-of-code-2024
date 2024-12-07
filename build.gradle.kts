plugins {
    kotlin("jvm") version "2.0.21"
}

group = "me.kfort"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.1")
}

java {
    targetCompatibility = JavaVersion.VERSION_22
}

kotlin {
    jvmToolchain(23)
}

tasks.test {
    useJUnitPlatform()
}