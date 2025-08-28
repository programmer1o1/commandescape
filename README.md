# Command Escape Mod

A client-side Fabric mod that allows you to use backslash (\) to escape commands in chat.

## Features

- Type `\/command` to send `/command` as a regular chat message
- Works on all servers without requiring server-side installation
- Supports Minecraft versions 1.14.4 to 1.21.4

## Usage

Simply prefix any command with a backslash to send it as regular text:
- `\/lol` sends "/lol" in chat
- `\/tp Player 0 0 0` sends "/tp Player 0 0 0" as text

## Building

```bash
# Build for current active version
./gradlew build

# Build for specific version
./gradlew :1.21.4:build

# Build all versions
./gradlew chiseledBuild
```

## Installation

1. Install Fabric Loader
2. Download the mod JAR for your Minecraft version
3. Place it in your .minecraft/mods folder
4. Launch Minecraft with the Fabric profile

## License

MIT License
