plugins {
    id("org.jetbrains.intellij") version "1.16.0"
    java
}

group = "org.example"
version = "1.0-SNAPSHOT"


repositories {
    mavenCentral()
}

dependencies {
    implementation("com.alibaba:fastjson:1.2.78")
    implementation("cn.hutool:hutool-all:5.7.16")
    implementation("mysql:mysql-connector-java:8.0.28")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.6.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
    implementation("org.freemarker:freemarker:2.3.28")
}

// See https://github.com/JetBrains/gradle-intellij-plugin/
intellij {
    version.set("2021.3.3")
    plugins.add("java")
}

tasks.withType<JavaCompile>(){
    options.encoding="UTF-8"
}

tasks.withType<Javadoc>(){
    options.encoding = "UTF-8"
}

tasks {
    patchPluginXml {
        changeNotes.set("""
            Add change notes here.<br>
            <em>most HTML tags may be used</em>        """.trimIndent())
    }
}
tasks.getByName<Test>("test") {
    useJUnitPlatform()
}