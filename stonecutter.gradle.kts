plugins {
    id("dev.kikugie.stonecutter")
    id("fabric-loom") version "1.11-SNAPSHOT" apply false
    // id("me.modmuss50.mod-publish-plugin") version "0.8.+" apply false // Publishes builds to hosting websites
}

stonecutter active "1.21.8"

stonecutter parameters {
    swaps["mod_version"] = "\"" + property("mod.version") + "\";"
    swaps["minecraft"] = "\"" + node.metadata.version + "\";"
    constants["release"] = true
}