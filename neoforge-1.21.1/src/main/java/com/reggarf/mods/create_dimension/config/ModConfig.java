package com.reggarf.mods.create_dimension.config;

import net.neoforged.neoforge.common.ModConfigSpec;

import java.util.List;

public class ModConfig {
    public static final ModConfigSpec SPEC;
    public static final Common COMMON;

    static {
        ModConfigSpec.Builder builder = new ModConfigSpec.Builder();
        COMMON = new Common(builder);
        SPEC = builder.build();
    }

    public static class Common {
        // Dimension Flat Generator Settings
        public final ModConfigSpec.ConfigValue<String> DIMENSION_BIOME;
        public final ModConfigSpec.BooleanValue GENERATE_LAKES;
        public final ModConfigSpec.BooleanValue GENERATE_FEATURES;
        public final ModConfigSpec.ConfigValue<List<? extends String>> FLAT_LAYERS;

        // Individual Layer Heights
        public final ModConfigSpec.IntValue BEDROCK_HEIGHT;
        public final ModConfigSpec.IntValue END_STONE_HEIGHT;
        public final ModConfigSpec.IntValue NETHERRACK_HEIGHT;
        public final ModConfigSpec.IntValue DEEPSLATE_HEIGHT;
        public final ModConfigSpec.IntValue STONE_HEIGHT;
        public final ModConfigSpec.IntValue ANDESITE_HEIGHT;
        public final ModConfigSpec.IntValue DIRT_HEIGHT;
        public final ModConfigSpec.IntValue GRASS_BLOCK_HEIGHT;

        // Ore Feature Configs
        public final ModConfigSpec.DoubleValue CHUNK_SPAWN_CHANCE;
        public final ModConfigSpec.DoubleValue GLOBAL_ORE_MULTIPLIER;
        public final ModConfigSpec.DoubleValue GLOBAL_VEIN_SIZE_MULTIPLIER;

        // Layer Ore Boundaries
        public final ModConfigSpec.IntValue END_MIN_Y;
        public final ModConfigSpec.IntValue END_MAX_Y;
        public final ModConfigSpec.IntValue NETHER_MIN_Y;
        public final ModConfigSpec.IntValue NETHER_MAX_Y;
        public final ModConfigSpec.IntValue DEEPSLATE_MIN_Y;
        public final ModConfigSpec.IntValue DEEPSLATE_MAX_Y;
        public final ModConfigSpec.IntValue STONE_MIN_Y;
        public final ModConfigSpec.IntValue STONE_MAX_Y;

        public Common(ModConfigSpec.Builder builder) {
            builder.push("dimension_settings");
            builder.comment("Settings for the Steamworks Realm flat dimension generator");

            DIMENSION_BIOME = builder
                    .comment("The default biome for the Steamworks Realm dimension.")
                    .define("biome", "create_dimension:mining");

            GENERATE_LAKES = builder
                    .comment("Whether to generate lakes in the flat dimension.")
                    .define("lakes", false);

            GENERATE_FEATURES = builder
                    .comment("Whether to generate features (ores, structures, etc.) in the flat dimension.")
                    .define("features", true);

            FLAT_LAYERS = builder
                    .comment("List of flat world layers formatted as 'block_id, height'.",
                             "Bottom-to-top order starting from min build height (Y = -64).")
                    .defineListAllowEmpty(
                            List.of("flat_layers"),
                            () -> List.of(
                                    "minecraft:bedrock, 1",
                                    "minecraft:end_stone, 64",
                                    "minecraft:netherrack, 64",
                                    "minecraft:deepslate, 64",
                                    "minecraft:stone, 119",
                                    "minecraft:andesite, 40",
                                    "minecraft:dirt, 4",
                                    "minecraft:grass_block, 1"
                            ),
                            obj -> obj instanceof String str && str.contains(",")
                    );

            builder.push("layer_heights");
            builder.comment("Individual layer block heights in the flat generator");

            BEDROCK_HEIGHT = builder
                    .comment("Bedrock layer thickness (starts at Y = -64)")
                    .defineInRange("bedrock_height", 1, 0, 256);

            END_STONE_HEIGHT = builder
                    .comment("End Stone layer thickness (Y = -63 to 0)")
                    .defineInRange("end_stone_height", 64, 0, 512);

            NETHERRACK_HEIGHT = builder
                    .comment("Netherrack layer thickness (Y = 1 to 64)")
                    .defineInRange("netherrack_height", 64, 0, 512);

            DEEPSLATE_HEIGHT = builder
                    .comment("Deepslate layer thickness (Y = 65 to 128)")
                    .defineInRange("deepslate_height", 64, 0, 512);

            STONE_HEIGHT = builder
                    .comment("Stone layer thickness (Y = 129 to 247)")
                    .defineInRange("stone_height", 119, 0, 512);

            ANDESITE_HEIGHT = builder
                    .comment("Andesite layer thickness (Y = 248 to 287)")
                    .defineInRange("andesite_height", 40, 0, 512);

            DIRT_HEIGHT = builder
                    .comment("Dirt layer thickness (Y = 288 to 291)")
                    .defineInRange("dirt_height", 4, 0, 64);

            GRASS_BLOCK_HEIGHT = builder
                    .comment("Grass Block layer thickness (Y = 292)")
                    .defineInRange("grass_block_height", 1, 0, 64);

            builder.pop();
            builder.pop();

            builder.push("ore_generation");
            builder.comment("Settings for Layered Ore generation in the Steamworks Realm");

            CHUNK_SPAWN_CHANCE = builder
                    .comment("Controls distance and empty spacing between ore spawns.",
                             "1.0 = Generates ores in every chunk.",
                             "0.75 = Generates ores in 75% of chunks (spaces out ore clusters).")
                    .defineInRange("chunk_spawn_chance", 0.75D, 0.0D, 1.0D);

            GLOBAL_ORE_MULTIPLIER = builder
                    .comment("Multiplier for total vein count per chunk.")
                    .defineInRange("global_ore_multiplier", 0.8D, 0.0D, 64.0D);

            GLOBAL_VEIN_SIZE_MULTIPLIER = builder
                    .comment("Multiplier for vein size (number of ore blocks per cluster).")
                    .defineInRange("global_vein_size_multiplier", 0.9D, 0.0D, 64.0D);

            builder.push("layer_boundaries");
            builder.comment("Y-level boundaries for each ore generation layer");

            END_MIN_Y = builder
                    .comment("Minimum Y level for End ores")
                    .defineInRange("end_min_y", -63, -64, 704);

            END_MAX_Y = builder
                    .comment("Maximum Y level for End ores")
                    .defineInRange("end_max_y", 0, -64, 704);

            NETHER_MIN_Y = builder
                    .comment("Minimum Y level for Nether ores")
                    .defineInRange("nether_min_y", 1, -64, 704);

            NETHER_MAX_Y = builder
                    .comment("Maximum Y level for Nether ores")
                    .defineInRange("nether_max_y", 64, -64, 704);

            DEEPSLATE_MIN_Y = builder
                    .comment("Minimum Y level for Deepslate ores")
                    .defineInRange("deepslate_min_y", 65, -64, 704);

            DEEPSLATE_MAX_Y = builder
                    .comment("Maximum Y level for Deepslate ores")
                    .defineInRange("deepslate_max_y", 128, -64, 704);

            STONE_MIN_Y = builder
                    .comment("Minimum Y level for Stone/Andesite ores")
                    .defineInRange("stone_min_y", 129, -64, 704);

            STONE_MAX_Y = builder
                    .comment("Maximum Y level for Stone/Andesite ores (covers Stone and Andesite layers)")
                    .defineInRange("stone_max_y", 287, -64, 704);

            builder.pop();
            builder.pop();
        }
    }
}
