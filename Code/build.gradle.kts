import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

group = "com.jmd"
version = "1.0"
description = "地图下载器"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
    mavenLocal()
    maven {
        url = uri("https://maven.aliyun.com/nexus/content/groups/public/")
    }
    mavenCentral()
}

plugins {
    java
    kotlin("jvm") version "1.9.10"
    kotlin("plugin.spring") version "1.9.10"
    kotlin("plugin.lombok") version "1.9.10"
    id("io.freefair.lombok") version "8.4"
    id("org.springframework.boot") version "3.2.2"
    id("io.spring.dependency-management") version "1.1.4"
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-websocket")
    implementation("org.springframework.boot:spring-boot-starter-tomcat")
    implementation("org.springframework.boot:spring-boot-configuration-processor")
    implementation("com.squareup.okhttp3:okhttp")
    implementation("io.reactivex.rxjava3:rxjava")
    implementation("org.xerial:sqlite-jdbc")
    implementation("org.mybatis.spring.boot:mybatis-spring-boot-starter:3.0.3")
    implementation("commons-io:commons-io:2.15.1")
    implementation("com.alibaba.fastjson2:fastjson2:2.0.45")
    implementation(files("lib/ui/flatlaf-3.3.jar"))
    implementation(files("lib/ui/flatlaf-intellij-themes-3.3.jar"))
    implementation(files("lib/ui/JTattoo-1.6.13.jar"))
    implementation(files("lib/opencv/opencv-4.9.0-0.jar"))
}

sourceSets {
    main {
        resources {
            srcDirs("src/main/java")
        }
    }
}

tasks.jar {
    enabled = true
    manifest {
        attributes(mapOf("Main-Class" to "com.jmd.Application"))
    }
}

tasks.withType<JavaCompile>() {
    options.encoding = "UTF-8"
}

tasks.withType<Javadoc>() {
    options.encoding = "UTF-8"
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "17"
    }
}
