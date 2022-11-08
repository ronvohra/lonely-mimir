import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val ktorVersion = "2.1.2"
val kotlinVersion = "1.7.20"
val exposedVersion = "0.39.2"
val flywayVersion = "9.4.0"
val h2Version = "2.1.214"
val hikariVersion = "5.0.1"
val junitVersion = "5.9.0"
val koinVersion = "3.2.2"
val logbackVersion = "1.4.3"
val mockitoVersion = "4.0.0"
val postgresVersion = "42.5.0"
val apacheCsvVersion = "1.9.0"

plugins {
    kotlin("jvm") version "1.7.20"
    kotlin("plugin.serialization") version "1.7.21"
    id("org.jetbrains.kotlinx.kover") version "0.6.0"
    application
}

group = "io.github.ronvohra"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.ktor:ktor-server-content-negotiation-jvm:$ktorVersion")
    implementation("io.ktor:ktor-server-core-jvm:$ktorVersion")
    implementation("io.ktor:ktor-serialization-kotlinx-json-jvm:$ktorVersion")
    implementation("io.ktor:ktor-server-call-logging-jvm:$ktorVersion")
    implementation("io.ktor:ktor-server-netty-jvm:$ktorVersion")

//    implementation("io.ktor:ktor-server-cors:$ktorVersion")
//    implementation("io.ktor:ktor-server-auth:$ktorVersion")
//    implementation("io.ktor:ktor-server-auth-jwt:$ktorVersion")

    implementation("org.jetbrains.exposed:exposed-core:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-dao:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-jdbc:$exposedVersion")
    implementation("org.flywaydb:flyway-core:$flywayVersion")
    implementation("com.zaxxer:HikariCP:$hikariVersion")
//    implementation("org.postgresql:postgresql:$postgresVersion")
    implementation("com.h2database:h2:$h2Version")

    implementation("ch.qos.logback:logback-classic:$logbackVersion")
    implementation("io.insert-koin:koin-ktor:$koinVersion")
    implementation("org.apache.commons:commons-csv:$apacheCsvVersion")

    implementation("io.insert-koin:koin-logger-slf4j:$koinVersion")

    testImplementation("org.junit.jupiter:junit-jupiter:$junitVersion")
    testImplementation("io.ktor:ktor-server-test-host:$ktorVersion")
    testImplementation("io.insert-koin:koin-test-junit5:$koinVersion")
    testImplementation("org.jetbrains.kotlin:kotlin-test:$kotlinVersion")
    testImplementation("org.mockito.kotlin:mockito-kotlin:$mockitoVersion")
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "17"
}

application {
    mainClass.set("io.ktor.server.netty.EngineMain")
}

// For Heroku deployment
tasks {
    create("stage").dependsOn("installDist")
}
