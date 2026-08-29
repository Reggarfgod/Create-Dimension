CHANGELOG [Version: 1.0.3]

### BUGFIX
- ClassNotFoundException
- Emerald Gen

CHANGELOG [Version: 1.0.4]

Note:
Before updating the mod, please make a backup of your world to prevent any potential data loss or compatibility issues.

# Fix

1. Trains can now travel through portals!

# Update

3. Updated portal textures for a fresh new look
4. World performance has been slightly optimized

# Added

5. can set respawn
6. added ponder
7. increase the world height (640) to allow building taller structures

# Balance

8. Changed Andesite portal block to Train CASING just from one side

CHANGELOG [Version: 2.0.0]

### Fixed
- Fixed world crashing and loading errors when creating or joining worlds.
- Fixed trains not traveling through portals properly on Minecraft 1.21.1.
- Fixed game startup issues and missing library errors on modern versions.
- Fixed mod compatibility registration ordering issues between JEI and JER.
- Clamped graph calculation bounds to prevent out-of-bounds rendering errors in JER's distribution graphs.

### Added
- **Craftable Igniter**: The Dimension Igniter now has its own crafting recipe and no longer requires trading with custom villagers.
- **Modded Ore Support**: Ores added by other mods (copper, zinc, tin, silver, etc.) will now automatically generate in their matching dimension layers!
- **Just Enough Resources (JER) & JEI World Gen Compatibility**:
  - Full support for displaying **Steamworks Realm** ore generation in the **World Gen** recipe tab within Just Enough Items (JEI) via Just Enough Resources (JER).
  - Dynamic discovery and visualization for all procedural ores across each dimension layer:
    - **End Layer** ($Y = -63$ to $0$)
    - **Nether Layer** ($Y = 1$ to $64$)
    - **Deepslate Layer** ($Y = 65$ to $128$)
    - **Stone & Andesite Layer** ($Y = 129$ to $255+$)
  - Automatically incorporates modded ores discovered through `c:ores_in_ground/*`, `neoforge:ores_in_ground/*`, and `forge:ores_in_ground/*` tags.
  - Accurate vein sizes, spawn frequencies (veins per chunk), and multipliers reflecting your active mod configuration (`GLOBAL_ORE_MULTIPLIER` and `GLOBAL_VEIN_SIZE_MULTIPLIER`).
  - Added localization and tooltip labels for the dimension (`jer.dim.create_dimension:steamworks_realm`).
- **New Config Options (`create_dimension/common.toml`)**:
  - Customize ore spawn amounts and cluster spacing.
  - Adjust vein sizes to make ore deposits smaller or larger.
  - Change the height and thickness of each ground layer (Bedrock, End Stone, Netherrack, Deepslate, Stone, Andesite, Dirt, and Grass).
  - Customize the exact Y-levels where each type of ore can be found.

### Changed
- **Removed Dependency**: Removed dependency on `create:bettervillagers`—all features including the portal igniter are fully self-contained.
- **Taller Dimension**: Increased the total world height to 704 blocks, giving you even more vertical space for massive Create mining rigs and train systems.
- **Smarter & Smoother Ore Spawning**: Rebuilt the ore generation system from the ground up for better world performance and cleaner ore distribution.
- **General Performance & Cleanup**: Streamlined mod files for faster loading times and better modpack compatibility.
