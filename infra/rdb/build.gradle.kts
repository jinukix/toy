import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
    id("com.jinuk.toy.spring-boot-jpa")
    id("java-test-fixtures")
}

val testContainersVersion = "1.20.0"

dependencies {
    runtimeOnly("com.mysql:mysql-connector-j")

    testFixturesImplementation("org.springframework.boot:spring-boot-starter-test")
    testFixturesImplementation("org.testcontainers:junit-jupiter:${testContainersVersion}")
    testFixturesImplementation("org.testcontainers:mysql:$testContainersVersion")
}

val bootJar: BootJar by tasks
val jar: Jar by tasks

bootJar.enabled = false
jar.enabled = true
