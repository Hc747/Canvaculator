plugins {
    id("java")
}

group = "com.github.hc747"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

java {
    toolchain {
        vendor = JvmVendorSpec.AMAZON
        languageVersion = JavaLanguageVersion.of(21)
    }
}

dependencies {
    implementation("jakarta.annotation:jakarta.annotation-api:2.1.1")
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}