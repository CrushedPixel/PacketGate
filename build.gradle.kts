import org.spongepowered.gradle.plugin.config.PluginLoaders
import org.spongepowered.plugin.metadata.PluginDependency


plugins {
    `maven-publish`
    `java-library`
    id("org.spongepowered.gradle.plugin") version "1.1.1"
    id("org.spongepowered.gradle.vanilla") version "0.2"
}

group = "eu.crushedpixel.sponge"
version = "0.2.0"

val javaTarget = 11

java {
    sourceCompatibility = JavaVersion.toVersion(javaTarget)
    targetCompatibility = JavaVersion.toVersion(javaTarget)
}

repositories {
    mavenCentral()
}

dependencies {
    compileOnly("org.spongepowered:mixin:0.8.3")
}

minecraft {
    version("1.16.5")
}

sponge {
    apiVersion("8.0.0")
    plugin("packetgate") {
        loader(PluginLoaders.JAVA_PLAIN)
        displayName("PacketGate")
        mainClass("eu.crushedpixel.sponge.packetgate.plugin.PluginPacketGate")
        description("Sponge library to manipulate incoming and outgoing Packets. ")
        links {
            homepage("https://github.com/CrushedPixel/PacketGate")
            source("https://github.com/CrushedPixel/PacketGate")
            issues("https://github.com/CrushedPixel/PacketGate/issues")
        }
        contributor("CrushedPixel") {
            description("Lead developer")
        }
        contributor("Masa") {
            description("API8 port")
        }
        dependency("spongeapi") {
            loadOrder(PluginDependency.LoadOrder.AFTER)
            optional(false)
        }
    }
}

tasks.withType<Jar> {
    manifest {
        attributes["MixinConfigs"] = "mixins.packetgate.json"
    }
}

tasks.withType(JavaCompile::class).configureEach {
    options.apply {
        encoding = "utf-8" // Consistent source file encoding
        if (JavaVersion.current().isJava10Compatible) {
            release.set(javaTarget)
        }
    }
}

// Make sure all tasks which produce archives (jar, sources jar, javadoc jar, etc) produce more consistent output
tasks.withType(AbstractArchiveTask::class).configureEach {
    isReproducibleFileOrder = true
    isPreserveFileTimestamps = false
}