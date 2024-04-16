import java.io.ByteArrayOutputStream

plugins {
    java
    `maven-publish`
}

val group = "io.github.selemba1000"
val version = "1.0.0"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.2"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation("net.java.dev.jna:jna:5.14.0")
    implementation("com.github.hypfvieh:dbus-java-core:5.0.0")
    implementation("com.github.hypfvieh:dbus-java-transport-jnr-unixsocket:5.0.0")
}

tasks.withType<Test>().configureEach {
    useJUnitPlatform()
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = group//"io.github.selemba1000"
            artifactId = "JavaMediaTransportControls"
            version = version//"1.0.0"

            from(components["java"])
        }
    }
}

tasks.register<NativeCompile>("build_x86_debug") {
    inputs.property("platform" , "Win32")
    inputs.property("configuration" , "Debug")
}

tasks.register<NativeCompile>("build_x86_release") {
    inputs.property("platform" , "Win32")
    inputs.property("configuration" , "Release")
}

tasks.register<NativeCompile>("build_x64_debug") {
    inputs.property("platform" , "x64")
    inputs.property("configuration" , "Debug")
}

tasks.register<NativeCompile>("build_x64_release") {
    inputs.property("platform" , "x64")
    inputs.property("configuration" , "Release")
}

tasks.register("build_debug"){
    group = "build native"
    dependsOn("build_x64_debug","build_x86_debug")
}

tasks.register("build_release"){
    group = "build native"
    dependsOn("build_x64_release","build_x86_release")
}

tasks.register<Delete>("clean_native"){
    group = "build native"
    delete(layout.projectDirectory.dir("src/main/sources").files(".build","bin"))
}

tasks.register<Copy>("copy_x86_debug"){
    from("src/main/sources/bin/Debug/x86/SMTCAdapter.dll")
    into("src/main/resources/win32-x86")
    dependsOn("build_debug")
}
tasks.register<Copy>("copy_x86_release"){
    from("src/main/sources/bin/Release/x86/SMTCAdapter.dll")
    into("src/main/resources/win32-x86")
    dependsOn("build_release")
}
tasks.register<Copy>("copy_x64_debug"){
    from("src/main/sources/bin/Debug/x64/SMTCAdapter.dll")
    into("src/main/resources/win32-x86-64")
    dependsOn("build_debug")
}
tasks.register<Copy>("copy_x64_release"){
    from("src/main/sources/bin/Release/x64/SMTCAdapter.dll")
    into("src/main/resources/win32-x86-64")
    dependsOn("build_release")
}

tasks.register("copy_debug"){
    group = "build native"
    dependsOn("copy_x86_debug","copy_x64_debug")
}
tasks.register("copy_release"){
    group = "build native"
    dependsOn("copy_x86_release","copy_x64_release")
}

abstract class NativeCompile : DefaultTask() {

    @get:InputFiles
    val source = arrayOf("src/main/sources/SMTCAdapter.vcxproj","src/main/sources/SMTCAdapter.cpp","src/main/sources/SMTCAdapter.h").map { File(it) }

    @get:OutputDirectories
    val output = arrayOf(File("src/main/sources/.build"),File("src/main/sources/bin"))

    @TaskAction
    fun compile() {
        val platform = inputs.properties["platform"]
        val configuration = inputs.properties["configuration"]
        val source = inputs.files
        val command = "MSBuild.exe -m /property:Platform=${platform} /property:Configuration=${configuration}"
        val stream = ByteArrayOutputStream()
        project.exec{
            workingDir = source.single { it.extension == "vcxproj" }.parentFile
            commandLine(command.split(" "))
            standardOutput = stream
        }
    }

}