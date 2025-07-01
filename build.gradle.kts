plugins {
    id("java")
}

group = "com.mc1510ty"
version = "1.0.0"

repositories {
    maven {
        url = uri("https://repo.papermc.io/repository/maven-public/")
    }
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.21.7-R0.1-SNAPSHOT")
    implementation("redis.clients:jedis:5.1.0")
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(21))
}

