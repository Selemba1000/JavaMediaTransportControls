import com.vanniktech.maven.publish.JavaLibrary
import com.vanniktech.maven.publish.JavadocJar
import com.vanniktech.maven.publish.SonatypeHost
import java.io.ByteArrayOutputStream

plugins {
    java
    //`maven-publish`
    //signing
    id("com.vanniktech.maven.publish") version "0.28.0"
}

val groupVal = "io.github.selemba1000"
val nameVal = "JavaMediaTransportControls"
val versionVal = "0.0.1"

repositories {
    mavenCentral()
}

java{
    //withJavadocJar()
    //withSourcesJar()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.2"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation("net.java.dev.jna:jna:5.14.0")
    implementation("com.github.hypfvieh:dbus-java-core:4.3.1")
    implementation("com.github.hypfvieh:dbus-java-transport-jnr-unixsocket:4.3.1")
}

tasks.withType<Test>().configureEach {
    useJUnitPlatform()
}

mavenPublishing{
    coordinates(groupVal,nameVal,versionVal)

    configure(JavaLibrary(
        javadocJar = JavadocJar.Javadoc(),
        sourcesJar = true
    ))

    pom {
        name.set("JavaMediaTransportControls")
        description.set("A simple Java library to interface with system media controls.")
        url.set("https://github.com/Selemba1000/JavaMediaTransportControls")
        licenses {
            license {
                name.set("MIT License")
                url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                distribution.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
            }
        }
        developers {
            developer {
                id.set("Selemba1000")
                name.set("Sebastian Lempik")
                email.set("sebastian.lempik@gmx.de")
                url.set("https://github.com/Selemba1000")
            }
        }
        scm {
            connection.set("scm:git:git://https://github.com/Selemba1000/JavaMediaTransportControls.git")
            developerConnection.set("scm:git:ssh://git@github.com:Selemba1000/JavaMediaTransportControls.git")
            url.set("https://github.com/Selemba1000/JavaMediaTransportControls")
        }
    }

    publishToMavenCentral(SonatypeHost.CENTRAL_PORTAL)
    signAllPublications()
}

tasks.javadoc {
    if (JavaVersion.current().isJava9Compatible) {
        (options as StandardJavadocDocletOptions).addBooleanOption("html5", true)
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