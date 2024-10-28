plugins {
    id("java")
    id("application")
    id("checkstyle")
    id("jacoco")
}

group = "hexlet.code"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(platform("com.fasterxml.jackson:jackson-bom:2.17.1"))
    implementation("com.fasterxml.jackson.core:jackson-databind")
    implementation("com.fasterxml.jackson.core:jackson-core")
    implementation("com.fasterxml.jackson.core:jackson-annotations")

    implementation("info.picocli:picocli:4.7.6")
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
    finalizedBy(tasks.jacocoTestReport) // Генерируем отчет после тестов
}

jacoco {
    toolVersion = "0.8.12"
}

tasks.jacocoTestReport { reports { xml.required.set(true) } }

checkstyle {
    toolVersion = "10.12.4"
}

tasks.withType<Checkstyle> {
    reports {
        xml.required.set(true)
        html.required.set(true)
    }
    source = sourceSets.main.get().allSource
    configFile = file("config/checkstyle/checkstyle.xml")
}
