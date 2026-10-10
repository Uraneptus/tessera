# About
Tessera is a wip minecraft modding library for helping with datagen. <br>
The majority of systems here were originally made by [Sammy;](https://github.com/SammySemicolon) for [Lodestone](https://github.com/LodestarMC/Lodestone) and were taken with permission.

Tessera is meant to be used mostly inside the dev environment, so the user doesn't need another dependency when downloading a mod that uses Tessera. However to use some features you need to include the Tessera core modules inside your jar files.

### Feature Overview
- TesseraBlockProperties extend the Vanilla BlockProperties and can be used to define some datagen settings when registering your mod blocks, eg. Tags, Render Type (This requires the bundled core modules)
- Simplified creation of Block SoundTypes (This requires the bundled core modules)
- Simplified Item Model & Blockstate generation
- Automatically generate block & item tags for common blocks (planks, buttons, etc.)
- Language Provider Utilities
