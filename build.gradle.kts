val ktorVersion: String by project
val kotlinVersion: String by project
val logbackVersion: String by project
val exposedVersion: String by project
val hikaricpVersion: String by project
val postgresqlVersion: String by project
val h2Version: String by project

plugins {
    kotlin("jvm") version "2.0.0"
    id("io.ktor.plugin") version "2.3.9"
    id("org.jetbrains.kotlin.plugin.serialization") version "2.0.0"
}

group = "com.example"
version = "0.0.1"

application {
    mainClass.set("io.ktor.server.netty.EngineMain")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

repositories {
    mavenCentral()
}

tasks.withType<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar> {
    manifest {
        attributes["Main-Class"] = "io.ktor.server.netty.EngineMain"
    }
}

tasks.register<JavaExec>("runLocal") {
    group = "application"
    description = "로컬 설정으로 애플리케이션 실행"

    mainClass.set("com.example.ApplicationKt")
    classpath = sourceSets["main"].runtimeClasspath

    systemProperty("config.file", "src/main/resources/application-local.conf")
}

tasks.register<JavaExec>("runProd") {
    group = "application"
    description = "프로덕션 설정으로 애플리케이션 실행"

    mainClass.set("com.example.ApplicationKt")
    classpath = sourceSets["main"].runtimeClasspath

    systemProperty("config.file", "src/main/resources/application.conf")
}

dependencies {
    implementation(libs.ktor.server.content.negotiation)
    implementation(libs.ktor.server.core)
    implementation(libs.ktor.serialization.kotlinx.json)
    implementation(libs.ktor.server.netty)
    implementation("ch.qos.logback:logback-classic:$logbackVersion")
    testImplementation(libs.ktor.server.test.host)
    testImplementation(libs.kotlin.test.junit)

    // Exposed
    implementation("org.jetbrains.exposed:exposed-core:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-jdbc:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-java-time:$exposedVersion")

    //HikariCP
    implementation("com.zaxxer:HikariCP:$hikaricpVersion")

    //Postgres
    implementation("org.postgresql:postgresql:$postgresqlVersion")

    //H2
    implementation("com.h2database:h2:$h2Version")

    //Koin
    implementation("io.insert-koin:koin-ktor:3.5.0")
    implementation("io.insert-koin:koin-logger-slf4j:3.5.0")
}
