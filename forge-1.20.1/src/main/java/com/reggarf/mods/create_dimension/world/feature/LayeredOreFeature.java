package com.reggarf.mods.create_dimension.world.feature;

import com.mojang.serialization.Codec;
import com.reggarf.mods.create_dimension.config.ModConfig;
import com.simibubi.create.AllBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

import java.util.*;
import java.util.function.Predicate;

public class LayeredOreFeature extends Feature<NoneFeatureConfiguration> {

    // Forge specific ground tags
    private static final TagKey<Block> FORGE_ORES_STONE = TagKey.create(Registries.BLOCK, new ResourceLocation("forge", "ores_in_ground/stone"));
    private static final TagKey<Block> FORGE_ORES_DEEPSLATE = TagKey.create(Registries.BLOCK, new ResourceLocation("forge", "ores_in_ground/deepslate"));
    private static final TagKey<Block> FORGE_ORES_NETHERRACK = TagKey.create(Registries.BLOCK, new ResourceLocation("forge", "ores_in_ground/netherrack"));
    private static final TagKey<Block> FORGE_ORES_END_STONE = TagKey.create(Registries.BLOCK, new ResourceLocation("forge", "ores_in_ground/end_stone"));

    // Common (c:) specific ground tags
    private static final TagKey<Block> C_ORES_STONE = TagKey.create(Registries.BLOCK, new ResourceLocation("c", "ores_in_ground/stone"));
    private static final TagKey<Block> C_ORES_DEEPSLATE = TagKey.create(Registries.BLOCK, new ResourceLocation("c", "ores_in_ground/deepslate"));
    private static final TagKey<Block> C_ORES_NETHERRACK = TagKey.create(Registries.BLOCK, new ResourceLocation("c", "ores_in_ground/netherrack"));
    private static final TagKey<Block> C_ORES_END_STONE = TagKey.create(Registries.BLOCK, new ResourceLocation("c", "ores_in_ground/end_stone"));

    // Universal ore tags implemented by almost all tech/magic/resource mods
    private static final TagKey<Block> FORGE_ORES_ALL = TagKey.create(Registries.BLOCK, new ResourceLocation("forge", "ores"));
    private static final TagKey<Block> C_ORES_ALL = TagKey.create(Registries.BLOCK, new ResourceLocation("c", "ores"));

    public LayeredOreFeature(Codec<NoneFeatureConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        WorldGenLevel level = context.level();
        BlockPos origin = context.origin();
        RandomSource random = context.random();

        // Distance / Gap check between chunks
        float chunkSpawnChance = ModConfig.COMMON.CHUNK_SPAWN_CHANCE.get().floatValue();
        if (chunkSpawnChance < 1.0f && random.nextFloat() > chunkSpawnChance) {
            return false;
        }

        int chunkBaseX = origin.getX() & ~15;
        int chunkBaseZ = origin.getZ() & ~15;

        // 1. END STONE LAYER
        generateLayerOres(
                level, random, chunkBaseX, chunkBaseZ,
                ModConfig.COMMON.END_MIN_Y.get(), ModConfig.COMMON.END_MAX_Y.get(),
                state -> state.is(Blocks.END_STONE),
                collectEndOres()
        );

        // 2. NETHERRACK LAYER
        generateLayerOres(
                level, random, chunkBaseX, chunkBaseZ,
                ModConfig.COMMON.NETHER_MIN_Y.get(), ModConfig.COMMON.NETHER_MAX_Y.get(),
                state -> state.is(Blocks.NETHERRACK),
                collectNetherOres()
        );

        // 3. DEEPSLATE LAYER
        generateLayerOres(
                level, random, chunkBaseX, chunkBaseZ,
                ModConfig.COMMON.DEEPSLATE_MIN_Y.get(), ModConfig.COMMON.DEEPSLATE_MAX_Y.get(),
                state -> state.is(Blocks.DEEPSLATE),
                collectDeepslateOres()
        );

        // 4. STONE & ANDESITE LAYER
        generateLayerOres(
                level, random, chunkBaseX, chunkBaseZ,
                ModConfig.COMMON.STONE_MIN_Y.get(), ModConfig.COMMON.STONE_MAX_Y.get(),
                state -> state.is(Blocks.STONE) || state.is(Blocks.ANDESITE),
                collectStoneOres()
        );

        return true;
    }

    private void generateLayerOres(
            WorldGenLevel level,
            RandomSource random,
            int chunkBaseX,
            int chunkBaseZ,
            int minY,
            int maxY,
            Predicate<BlockState> targetFilter,
            List<OreConfig> ores
    ) {
        if (ores.isEmpty() || maxY <= minY) return;

        BlockPos.MutableBlockPos mutablePos = new BlockPos.MutableBlockPos();
        float globalOreMultiplier = ModConfig.COMMON.GLOBAL_ORE_MULTIPLIER.get().floatValue();
        float globalVeinSizeMultiplier = ModConfig.COMMON.GLOBAL_VEIN_SIZE_MULTIPLIER.get().floatValue();

        for (OreConfig ore : ores) {
            float totalRate = ore.veinsPerChunk * globalOreMultiplier;
            int attempts = (int) Math.floor(totalRate);
            float remainder = totalRate - attempts;
            if (remainder > 0 && random.nextFloat() < remainder) {
                attempts++;
            }

            int finalVeinSize = Math.max(1, Math.round(ore.veinSize * globalVeinSizeMultiplier));

            for (int count = 0; count < attempts; count++) {
                int centerX = chunkBaseX + random.nextInt(16);
                int centerY = minY + random.nextInt(maxY - minY + 1);
                int centerZ = chunkBaseZ + random.nextInt(16);

                placeVein(level, random, targetFilter, ore.state, centerX, centerY, centerZ, finalVeinSize, chunkBaseX, chunkBaseZ, minY, maxY, mutablePos);
            }
        }
    }

    private void placeVein(
            WorldGenLevel level,
            RandomSource random,
            Predicate<BlockState> targetFilter,
            BlockState oreState,
            int centerX,
            int centerY,
            int centerZ,
            int veinSize,
            int chunkBaseX,
            int chunkBaseZ,
            int minY,
            int maxY,
            BlockPos.MutableBlockPos mutablePos
    ) {
        float radiusX = (veinSize / 2.8f) * (0.75f + random.nextFloat() * 0.35f);
        float radiusY = (veinSize / 3.2f) * (0.75f + random.nextFloat() * 0.35f);
        float radiusZ = (veinSize / 2.8f) * (0.75f + random.nextFloat() * 0.35f);

        int rX = Math.max(1, (int) Math.ceil(radiusX));
        int rY = Math.max(1, (int) Math.ceil(radiusY));
        int rZ = Math.max(1, (int) Math.ceil(radiusZ));

        for (int dx = -rX; dx <= rX; dx++) {
            int x = centerX + dx;
            if (x < chunkBaseX || x >= chunkBaseX + 16) continue;

            for (int dz = -rZ; dz <= rZ; dz++) {
                int z = centerZ + dz;
                if (z < chunkBaseZ || z >= chunkBaseZ + 16) continue;

                for (int dy = -rY; dy <= rY; dy++) {
                    int y = centerY + dy;
                    if (y < minY || y > maxY) continue;

                    double distSq = ((double) (dx * dx) / (radiusX * radiusX))
                            + ((double) (dy * dy) / (radiusY * radiusY))
                            + ((double) (dz * dz) / (radiusZ * radiusZ));

                    if (distSq <= 1.0) {
                        mutablePos.set(x, y, z);
                        if (targetFilter.test(level.getBlockState(mutablePos))) {
                            level.setBlock(mutablePos, oreState, 2);
                        }
                    }
                }
            }
        }
    }

    private List<OreConfig> collectEndOres() {
        Set<Block> seen = new HashSet<>();
        List<OreConfig> configs = new ArrayList<>();

        collectOresFromTag(FORGE_ORES_END_STONE, seen, configs);
        collectOresFromTag(C_ORES_END_STONE, seen, configs);
        collectOresFromGeneralTag(FORGE_ORES_ALL, seen, configs, "end");
        collectOresFromGeneralTag(C_ORES_ALL, seen, configs, "end");

        return configs;
    }

    private List<OreConfig> collectNetherOres() {
        Set<Block> seen = new HashSet<>();
        List<OreConfig> configs = new ArrayList<>();

        collectOresFromTag(FORGE_ORES_NETHERRACK, seen, configs);
        collectOresFromTag(C_ORES_NETHERRACK, seen, configs);
        collectOresFromGeneralTag(FORGE_ORES_ALL, seen, configs, "nether");
        collectOresFromGeneralTag(C_ORES_ALL, seen, configs, "nether");

        // Vanilla fallbacks
        addFallbackOre(Blocks.NETHER_QUARTZ_ORE, 7, 2.5f, seen, configs);
        addFallbackOre(Blocks.NETHER_GOLD_ORE, 5, 1.5f, seen, configs);
        addFallbackOre(Blocks.ANCIENT_DEBRIS, 3, 0.3f, seen, configs);

        return configs;
    }

    private List<OreConfig> collectDeepslateOres() {
        Set<Block> seen = new HashSet<>();
        List<OreConfig> configs = new ArrayList<>();

        collectOresFromTag(FORGE_ORES_DEEPSLATE, seen, configs);
        collectOresFromTag(C_ORES_DEEPSLATE, seen, configs);
        collectOresFromGeneralTag(FORGE_ORES_ALL, seen, configs, "deepslate");
        collectOresFromGeneralTag(C_ORES_ALL, seen, configs, "deepslate");

        // Vanilla fallbacks
        addFallbackOre(Blocks.DEEPSLATE_COAL_ORE, 8, 2.0f, seen, configs);
        addFallbackOre(Blocks.DEEPSLATE_IRON_ORE, 6, 2.0f, seen, configs);
        addFallbackOre(Blocks.DEEPSLATE_COPPER_ORE, 7, 1.8f, seen, configs);
        addFallbackOre(Blocks.DEEPSLATE_GOLD_ORE, 5, 1.0f, seen, configs);
        addFallbackOre(Blocks.DEEPSLATE_REDSTONE_ORE, 5, 1.0f, seen, configs);
        addFallbackOre(Blocks.DEEPSLATE_LAPIS_ORE, 4, 0.7f, seen, configs);
        addFallbackOre(Blocks.DEEPSLATE_DIAMOND_ORE, 4, 0.6f, seen, configs);
        addFallbackOre(Blocks.DEEPSLATE_EMERALD_ORE, 3, 0.2f, seen, configs);

        // Create Mod Zinc
        if (AllBlocks.DEEPSLATE_ZINC_ORE != null && AllBlocks.DEEPSLATE_ZINC_ORE.get() != null) {
            addFallbackOre(AllBlocks.DEEPSLATE_ZINC_ORE.get(), 6, 1.8f, seen, configs);
        }

        return configs;
    }

    private List<OreConfig> collectStoneOres() {
        Set<Block> seen = new HashSet<>();
        List<OreConfig> configs = new ArrayList<>();

        collectOresFromTag(FORGE_ORES_STONE, seen, configs);
        collectOresFromTag(C_ORES_STONE, seen, configs);
        collectOresFromGeneralTag(FORGE_ORES_ALL, seen, configs, "stone");
        collectOresFromGeneralTag(C_ORES_ALL, seen, configs, "stone");

        // Vanilla fallbacks
        addFallbackOre(Blocks.COAL_ORE, 8, 2.5f, seen, configs);
        addFallbackOre(Blocks.IRON_ORE, 6, 2.2f, seen, configs);
        addFallbackOre(Blocks.COPPER_ORE, 7, 2.0f, seen, configs);
        addFallbackOre(Blocks.GOLD_ORE, 5, 1.0f, seen, configs);
        addFallbackOre(Blocks.REDSTONE_ORE, 5, 1.0f, seen, configs);
        addFallbackOre(Blocks.LAPIS_ORE, 4, 0.7f, seen, configs);
        addFallbackOre(Blocks.DIAMOND_ORE, 3, 0.4f, seen, configs);
        addFallbackOre(Blocks.EMERALD_ORE, 3, 0.2f, seen, configs);

        // Create Mod Zinc
        if (AllBlocks.ZINC_ORE != null && AllBlocks.ZINC_ORE.get() != null) {
            addFallbackOre(AllBlocks.ZINC_ORE.get(), 6, 2.0f, seen, configs);
        }

        return configs;
    }

    private void collectOresFromTag(TagKey<Block> tagKey, Set<Block> seen, List<OreConfig> configs) {
        Optional<HolderSet.Named<Block>> optionalHolderSet = BuiltInRegistries.BLOCK.getTag(tagKey);
        if (optionalHolderSet.isEmpty()) return;

        for (Holder<Block> holder : optionalHolderSet.get()) {
            Block block = holder.value();
            if (block == null || block == Blocks.AIR || seen.contains(block)) continue;
            seen.add(block);

            configs.add(createConfigForOre(block));
        }
    }

    private void collectOresFromGeneralTag(TagKey<Block> tagKey, Set<Block> seen, List<OreConfig> configs, String layerType) {
        Optional<HolderSet.Named<Block>> optionalHolderSet = BuiltInRegistries.BLOCK.getTag(tagKey);
        if (optionalHolderSet.isEmpty()) return;

        for (Holder<Block> holder : optionalHolderSet.get()) {
            Block block = holder.value();
            if (block == null || block == Blocks.AIR || seen.contains(block)) continue;

            String path = BuiltInRegistries.BLOCK.getKey(block).getPath().toLowerCase(Locale.ROOT);

            boolean matches = false;
            switch (layerType) {
                case "end":
                    matches = path.contains("end");
                    break;
                case "nether":
                    matches = path.contains("nether") || path.contains("debris");
                    break;
                case "deepslate":
                    matches = path.contains("deepslate") || path.contains("grimstone");
                    break;
                case "stone":
                    matches = !path.contains("end") && !path.contains("nether") && !path.contains("debris") && !path.contains("deepslate") && !path.contains("grimstone");
                    break;
            }

            if (matches) {
                seen.add(block);
                configs.add(createConfigForOre(block));
            }
        }
    }

    private OreConfig createConfigForOre(Block block) {
        String name = BuiltInRegistries.BLOCK.getKey(block).getPath().toLowerCase(Locale.ROOT);
        int veinSize;
        float veinsPerChunk;

        if (name.contains("debris") || name.contains("netherite")) {
            veinSize = 3;
            veinsPerChunk = 0.3f;
        } else if (name.contains("emerald")) {
            veinSize = 3;
            veinsPerChunk = 0.2f;
        } else if (name.contains("diamond")) {
            veinSize = 3;
            veinsPerChunk = 0.5f;
        } else if (name.contains("lapis")) {
            veinSize = 4;
            veinsPerChunk = 0.7f;
        } else if (name.contains("gold") || name.contains("redstone")) {
            veinSize = 5;
            veinsPerChunk = 1.0f;
        } else if (name.contains("iron") || name.contains("zinc")) {
            veinSize = 6;
            veinsPerChunk = 2.0f;
        } else if (name.contains("copper")) {
            veinSize = 7;
            veinsPerChunk = 2.0f;
        } else if (name.contains("coal") || name.contains("quartz")) {
            veinSize = 8;
            veinsPerChunk = 2.5f;
        } else {
            veinSize = 5;
            veinsPerChunk = 1.2f;
        }

        return new OreConfig(block.defaultBlockState(), veinSize, veinsPerChunk);
    }

    private void addFallbackOre(Block block, int veinSize, float veinsPerChunk, Set<Block> seen, List<OreConfig> configs) {
        if (block != null && !seen.contains(block)) {
            seen.add(block);
            configs.add(new OreConfig(block.defaultBlockState(), veinSize, veinsPerChunk));
        }
    }

    private static class OreConfig {
        final BlockState state;
        final int veinSize;
        final float veinsPerChunk;

        OreConfig(BlockState state, int veinSize, float veinsPerChunk) {
            this.state = state;
            this.veinSize = veinSize;
            this.veinsPerChunk = veinsPerChunk;
        }
    }
}
