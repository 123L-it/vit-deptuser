import com.google.protobuf.gradle.protoc
import com.google.protobuf.gradle.*
import org.gradle.language.jvm.tasks.ProcessResources
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.springframework.boot.gradle.tasks.bundling.BootBuildImage


plugins {
    id("org.springframework.boot") version "2.5.0"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    kotlin("jvm") version "1.5.10"
    kotlin("plugin.spring") version "1.5.10"
    id("org.jlleitschuh.gradle.ktlint") version "10.1.0"
    id("org.jmailen.kotlinter") version "3.4.4"
    id("org.jetbrains.kotlin.plugin.noarg") version "1.5.20-RC"
    id("com.google.protobuf") version "0.8.17"
}

group = "com.123L.vit"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_1_8

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.junit.jupiter:junit-jupiter:5.7.0")
    implementation("io.jsonwebtoken:jjwt-api:0.11.2")
    implementation("io.jsonwebtoken:jjwt-impl:0.11.2")
    implementation("io.jsonwebtoken:jjwt-gson:0.11.2")
    implementation("io.grpc:grpc-protobuf:1.33.1")
    implementation("io.grpc:grpc-stub:1.33.1")
    implementation("io.grpc:grpc-netty:1.33.1")
    api("com.google.protobuf:protobuf-java-util:3.13.0")
    implementation("io.grpc:grpc-all:1.33.1")
    api("io.grpc:grpc-kotlin-stub:0.2.1")
    implementation("io.grpc:protoc-gen-grpc-kotlin:0.1.5")

    implementation("com.google.protobuf:protobuf-java:3.18.0")

    developmentOnly("org.springframework.boot:spring-boot-devtools")
    runtimeOnly("org.postgresql:postgresql")
    testImplementation("io.mockk:mockk:1.11.0")
    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude(module = "junit")
        exclude(module = "mockito-core")
    }
    testImplementation("com.ninja-squad:springmockk:3.0.1")
    testImplementation("org.junit.jupiter:junit-jupiter-api")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
    testImplementation("org.springframework.security:spring-security-test")
}

noArg {
    annotation("com.l.vit.annotations.NoArg")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "1.8"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.withType<ProcessResources> {
    filesMatching("application.properties") {
        expand(project.properties)
    }
}

tasks.withType<BootBuildImage> {
    builder = "paketobuildpacks/builder:tiny"
    environment = mapOf("BP_NATIVE_IMAGE" to "true")
}

sourceSets {
    main {
        proto {
            srcDir("src/main/resources/proto")
        }
    }
}

protobuf {
    protoc{
        artifact = "com.google.protobuf:protoc:3.10.1"
    }
    plugins {
        id("grpc"){
            artifact = "io.grpc:protoc-gen-grpc-java:1.33.1"
        }
        id("grpckt") {
            artifact = "io.grpc:protoc-gen-grpc-kotlin:0.1.5"
        }
    }
    generateProtoTasks {
        all().forEach {
            it.plugins {
                id("grpc")
                id("grpckt")
            }
        }
    }
}

