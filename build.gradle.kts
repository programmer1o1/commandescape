plugins {
    id("fabric-loom")
}

repositories {
    mavenCentral()
    maven("https://maven.fabricmc.net/")
}

dependencies {
    minecraft("com.mojang:minecraft:${project.property("mod.mc_title")}")
    mappings("net.fabricmc:yarn:${project.property("deps.yarn")}:v2")
    modImplementation("net.fabricmc:fabric-loader:${project.property("deps.fabric_loader")}")
}

// Set Java compatibility based on Minecraft version
val mcVersion = project.property("mod.mc_title").toString()
val javaVersion = when {
    mcVersion.startsWith("1.19") -> JavaVersion.VERSION_17
    mcVersion.startsWith("1.20") && mcVersion < "1.20.5" -> JavaVersion.VERSION_17
    else -> JavaVersion.VERSION_21
}

java {
    sourceCompatibility = javaVersion
    targetCompatibility = javaVersion
}

tasks {
    processResources {
        duplicatesStrategy = DuplicatesStrategy.EXCLUDE

        inputs.property("version", project.property("mod.version"))
        inputs.property("mc_dep", project.property("mod.mc_dep"))

        filesMatching("fabric.mod.json") {
            expand(mapOf(
                "version" to project.property("mod.version"),
                "mc_dep" to project.property("mod.mc_dep")
            ))
        }
    }

    jar {
        duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    }

    withType<JavaCompile> {
        options.release.set(javaVersion.majorVersion.toInt())
    }
}

// Set the archive base name
base {
    archivesName.set("${project.property("mod.id")}-mc${project.property("mod.mc_title")}")
}