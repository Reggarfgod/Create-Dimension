package com.reggarf.mods.create_dimension.compat.jer;

import com.reggarf.mods.create_dimension.config.ModConfig;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllItems;
import jeresources.api.IJERAPI;
import jeresources.api.IJERPlugin;
import jeresources.api.IWorldGenRegistry;
import jeresources.api.JERPlugin;
import jeresources.api.conditionals.Conditional;
import jeresources.api.distributions.DistributionSquare;
import jeresources.api.drop.LootDrop;
import jeresources.api.restrictions.DimensionRestriction;
import jeresources.api.restrictions.Restriction;
import jeresources.entry.WorldGenEntry;
import jeresources.registry.WorldGenRegistry;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.fml.ModList;

import java.util.*;

@JERPlugin
public class ModJERPlugin implements IJERPlugin {

    public static final ResourceKey<Level> STEAMWORKS_REALM = ResourceKey.create(
            Registries.DIMENSION,
            ResourceLocation.fromNamespaceAndPath("create_dimension", "steamworks_realm")
    );

    // Tags matching LayeredOreFeature
    private static final TagKey<Block> C_ORES_STONE = TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath("c", "ores_in_ground/stone"));
    private static final TagKey<Block> C_ORES_DEEPSLATE = TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath("c", "ores_in_ground/deepslate"));
    private static final TagKey<Block> C_ORES_NETHERRACK = TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath("c", "ores_in_ground/netherrack"));
    private static final TagKey<Block> C_ORES_END_STONE = TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath("c", "ores_in_ground/end_stone"));

    private static final TagKey<Block> NEOFORGE_ORES_STONE = TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath("neoforge", "ores_in_ground/stone"));
    private static final TagKey<Block> NEOFORGE_ORES_DEEPSLATE = TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath("neoforge", "ores_in_ground/deepslate"));
    private static final TagKey<Block> NEOFORGE_ORES_NETHERRACK = TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath("neoforge", "ores_in_ground/netherrack"));
    private static final TagKey<Block> NEOFORGE_ORES_END_STONE = TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath("neoforge", "ores_in_ground/end_stone"));

    private static final TagKey<Block> C_ORES_ALL = TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath("c", "ores"));
    private static final TagKey<Block> NEOFORGE_ORES_ALL = TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath("neoforge", "ores"));

    private static boolean registered = false;

    @Override
    public void receive(IJERAPI jerApi) {
        registerOres(jerApi.getWorldGenRegistry());
    }

    public static synchronized void registerOres(IWorldGenRegistry iRegistry) {
        if (registered) return;
        registered = true;

        Restriction dimRestriction = new Restriction(new DimensionRestriction(STEAMWORKS_REALM));

        int stoneMin = ModConfig.SPEC.isLoaded() ? ModConfig.COMMON.STONE_MIN_Y.get() : 129;
        int stoneMax = Math.min(255, ModConfig.SPEC.isLoaded() ? ModConfig.COMMON.STONE_MAX_Y.get() : 287);
        int deepslateMin = ModConfig.SPEC.isLoaded() ? ModConfig.COMMON.DEEPSLATE_MIN_Y.get() : 65;
        int deepslateMax = ModConfig.SPEC.isLoaded() ? ModConfig.COMMON.DEEPSLATE_MAX_Y.get() : 128;
        int netherMin = ModConfig.SPEC.isLoaded() ? ModConfig.COMMON.NETHER_MIN_Y.get() : 1;
        int netherMax = ModConfig.SPEC.isLoaded() ? ModConfig.COMMON.NETHER_MAX_Y.get() : 64;
        int endMin = ModConfig.SPEC.isLoaded() ? ModConfig.COMMON.END_MIN_Y.get() : -63;
        int endMax = ModConfig.SPEC.isLoaded() ? ModConfig.COMMON.END_MAX_Y.get() : 0;

        float globalOreMultiplier = ModConfig.SPEC.isLoaded() ? ModConfig.COMMON.GLOBAL_ORE_MULTIPLIER.get().floatValue() : 0.8f;
        float globalVeinSizeMultiplier = ModConfig.SPEC.isLoaded() ? ModConfig.COMMON.GLOBAL_VEIN_SIZE_MULTIPLIER.get().floatValue() : 0.9f;

        // 1. END LAYER ORES
        for (OreInfo ore : collectEndOres()) {
            registerConfiguredOre(iRegistry, dimRestriction, ore, endMin, endMax, globalVeinSizeMultiplier, globalOreMultiplier);
        }

        // 2. NETHER LAYER ORES
        for (OreInfo ore : collectNetherOres()) {
            registerConfiguredOre(iRegistry, dimRestriction, ore, netherMin, netherMax, globalVeinSizeMultiplier, globalOreMultiplier);
        }

        // 3. DEEPSLATE LAYER ORES
        for (OreInfo ore : collectDeepslateOres()) {
            registerConfiguredOre(iRegistry, dimRestriction, ore, deepslateMin, deepslateMax, globalVeinSizeMultiplier, globalOreMultiplier);
        }

        // 4. STONE & ANDESITE LAYER ORES
        for (OreInfo ore : collectStoneOres()) {
            registerConfiguredOre(iRegistry, dimRestriction, ore, stoneMin, stoneMax, globalVeinSizeMultiplier, globalOreMultiplier);
        }
    }

    private static void registerConfiguredOre(
            IWorldGenRegistry iRegistry,
            Restriction restriction,
            OreInfo ore,
            int minY,
            int maxY,
            float veinSizeMultiplier,
            float oreMultiplier
    ) {
        int finalVeinSize = Math.max(1, Math.round(ore.veinSize * veinSizeMultiplier));
        int finalVeinsPerChunk = Math.max(1, Math.round(ore.veinsPerChunk * oreMultiplier));

        LootDrop drop = ore.drop != null ? ore.drop : getDefaultDrop(ore.block);
        registerOre(iRegistry, restriction, ore.block, null, finalVeinSize, finalVeinsPerChunk, minY, maxY, drop);
    }

    private static List<OreInfo> collectEndOres() {
        Set<Block> seen = new HashSet<>();
        List<OreInfo> configs = new ArrayList<>();

        collectOresFromTag(C_ORES_END_STONE, seen, configs);
        collectOresFromTag(NEOFORGE_ORES_END_STONE, seen, configs);
        collectOresFromGeneralTag(C_ORES_ALL, seen, configs, "end");
        collectOresFromGeneralTag(NEOFORGE_ORES_ALL, seen, configs, "end");

        return configs;
    }

    private static List<OreInfo> collectNetherOres() {
        Set<Block> seen = new HashSet<>();
        List<OreInfo> configs = new ArrayList<>();

        collectOresFromTag(C_ORES_NETHERRACK, seen, configs);
        collectOresFromTag(NEOFORGE_ORES_NETHERRACK, seen, configs);
        collectOresFromGeneralTag(C_ORES_ALL, seen, configs, "nether");
        collectOresFromGeneralTag(NEOFORGE_ORES_ALL, seen, configs, "nether");

        // Fallbacks
        addFallbackOre(Blocks.NETHER_QUARTZ_ORE, 7, 2.5f, new LootDrop(new ItemStack(Items.QUARTZ), 1, 1, Conditional.affectedByFortune), seen, configs);
        addFallbackOre(Blocks.NETHER_GOLD_ORE, 5, 1.5f, new LootDrop(new ItemStack(Items.GOLD_NUGGET), 2, 6, Conditional.affectedByFortune), seen, configs);
        addFallbackOre(Blocks.ANCIENT_DEBRIS, 3, 0.3f, new LootDrop(new ItemStack(Items.ANCIENT_DEBRIS), 1, 1), seen, configs);

        return configs;
    }

    private static List<OreInfo> collectDeepslateOres() {
        Set<Block> seen = new HashSet<>();
        List<OreInfo> configs = new ArrayList<>();

        collectOresFromTag(C_ORES_DEEPSLATE, seen, configs);
        collectOresFromTag(NEOFORGE_ORES_DEEPSLATE, seen, configs);
        collectOresFromGeneralTag(C_ORES_ALL, seen, configs, "deepslate");
        collectOresFromGeneralTag(NEOFORGE_ORES_ALL, seen, configs, "deepslate");

        // Fallbacks
        addFallbackOre(Blocks.DEEPSLATE_COAL_ORE, 8, 2.0f, new LootDrop(new ItemStack(Items.COAL), 1, 1, Conditional.affectedByFortune), seen, configs);
        addFallbackOre(Blocks.DEEPSLATE_IRON_ORE, 6, 2.0f, new LootDrop(new ItemStack(Items.RAW_IRON), 1, 1, Conditional.affectedByFortune), seen, configs);
        addFallbackOre(Blocks.DEEPSLATE_COPPER_ORE, 7, 1.8f, new LootDrop(new ItemStack(Items.RAW_COPPER), 2, 5, Conditional.affectedByFortune), seen, configs);
        addFallbackOre(Blocks.DEEPSLATE_GOLD_ORE, 5, 1.0f, new LootDrop(new ItemStack(Items.RAW_GOLD), 1, 1, Conditional.affectedByFortune), seen, configs);
        addFallbackOre(Blocks.DEEPSLATE_REDSTONE_ORE, 5, 1.0f, new LootDrop(new ItemStack(Items.REDSTONE), 4, 5, Conditional.affectedByFortune), seen, configs);
        addFallbackOre(Blocks.DEEPSLATE_LAPIS_ORE, 4, 0.7f, new LootDrop(new ItemStack(Items.LAPIS_LAZULI), 4, 9, Conditional.affectedByFortune), seen, configs);
        addFallbackOre(Blocks.DEEPSLATE_DIAMOND_ORE, 4, 0.6f, new LootDrop(new ItemStack(Items.DIAMOND), 1, 1, Conditional.affectedByFortune), seen, configs);
        addFallbackOre(Blocks.DEEPSLATE_EMERALD_ORE, 3, 0.2f, new LootDrop(new ItemStack(Items.EMERALD), 1, 1, Conditional.affectedByFortune), seen, configs);

        if (AllBlocks.DEEPSLATE_ZINC_ORE != null && AllBlocks.DEEPSLATE_ZINC_ORE.get() != null) {
            addFallbackOre(AllBlocks.DEEPSLATE_ZINC_ORE.get(), 6, 1.8f, new LootDrop(AllItems.RAW_ZINC.asStack(), 1, 1, Conditional.affectedByFortune), seen, configs);
        }

        return configs;
    }

    private static List<OreInfo> collectStoneOres() {
        Set<Block> seen = new HashSet<>();
        List<OreInfo> configs = new ArrayList<>();

        collectOresFromTag(C_ORES_STONE, seen, configs);
        collectOresFromTag(NEOFORGE_ORES_STONE, seen, configs);
        collectOresFromGeneralTag(C_ORES_ALL, seen, configs, "stone");
        collectOresFromGeneralTag(NEOFORGE_ORES_ALL, seen, configs, "stone");

        // Fallbacks
        addFallbackOre(Blocks.COAL_ORE, 8, 2.5f, new LootDrop(new ItemStack(Items.COAL), 1, 1, Conditional.affectedByFortune), seen, configs);
        addFallbackOre(Blocks.IRON_ORE, 6, 2.2f, new LootDrop(new ItemStack(Items.RAW_IRON), 1, 1, Conditional.affectedByFortune), seen, configs);
        addFallbackOre(Blocks.COPPER_ORE, 7, 2.0f, new LootDrop(new ItemStack(Items.RAW_COPPER), 2, 5, Conditional.affectedByFortune), seen, configs);
        addFallbackOre(Blocks.GOLD_ORE, 5, 1.0f, new LootDrop(new ItemStack(Items.RAW_GOLD), 1, 1, Conditional.affectedByFortune), seen, configs);
        addFallbackOre(Blocks.REDSTONE_ORE, 5, 1.0f, new LootDrop(new ItemStack(Items.REDSTONE), 4, 5, Conditional.affectedByFortune), seen, configs);
        addFallbackOre(Blocks.LAPIS_ORE, 4, 0.7f, new LootDrop(new ItemStack(Items.LAPIS_LAZULI), 4, 9, Conditional.affectedByFortune), seen, configs);
        addFallbackOre(Blocks.DIAMOND_ORE, 3, 0.4f, new LootDrop(new ItemStack(Items.DIAMOND), 1, 1, Conditional.affectedByFortune), seen, configs);
        addFallbackOre(Blocks.EMERALD_ORE, 3, 0.2f, new LootDrop(new ItemStack(Items.EMERALD), 1, 1, Conditional.affectedByFortune), seen, configs);

        if (AllBlocks.ZINC_ORE != null && AllBlocks.ZINC_ORE.get() != null) {
            addFallbackOre(AllBlocks.ZINC_ORE.get(), 6, 2.0f, new LootDrop(AllItems.RAW_ZINC.asStack(), 1, 1, Conditional.affectedByFortune), seen, configs);
        }

        return configs;
    }

    private static void collectOresFromTag(TagKey<Block> tagKey, Set<Block> seen, List<OreInfo> configs) {
        Optional<HolderSet.Named<Block>> optionalHolderSet = BuiltInRegistries.BLOCK.getTag(tagKey);
        if (optionalHolderSet.isEmpty()) return;

        for (Holder<Block> holder : optionalHolderSet.get()) {
            Block block = holder.value();
            if (block == null || block == Blocks.AIR || seen.contains(block)) continue;
            seen.add(block);
            configs.add(createOreInfo(block));
        }
    }

    private static void collectOresFromGeneralTag(TagKey<Block> tagKey, Set<Block> seen, List<OreInfo> configs, String layerType) {
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
                configs.add(createOreInfo(block));
            }
        }
    }

    private static OreInfo createOreInfo(Block block) {
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

        return new OreInfo(block, veinSize, veinsPerChunk, getDefaultDrop(block));
    }

    private static LootDrop getDefaultDrop(Block block) {
        if (block == Blocks.COAL_ORE || block == Blocks.DEEPSLATE_COAL_ORE) {
            return new LootDrop(new ItemStack(Items.COAL), 1, 1, Conditional.affectedByFortune);
        } else if (block == Blocks.IRON_ORE || block == Blocks.DEEPSLATE_IRON_ORE) {
            return new LootDrop(new ItemStack(Items.RAW_IRON), 1, 1, Conditional.affectedByFortune);
        } else if (block == Blocks.COPPER_ORE || block == Blocks.DEEPSLATE_COPPER_ORE) {
            return new LootDrop(new ItemStack(Items.RAW_COPPER), 2, 5, Conditional.affectedByFortune);
        } else if (block == Blocks.GOLD_ORE || block == Blocks.DEEPSLATE_GOLD_ORE) {
            return new LootDrop(new ItemStack(Items.RAW_GOLD), 1, 1, Conditional.affectedByFortune);
        } else if (block == Blocks.REDSTONE_ORE || block == Blocks.DEEPSLATE_REDSTONE_ORE) {
            return new LootDrop(new ItemStack(Items.REDSTONE), 4, 5, Conditional.affectedByFortune);
        } else if (block == Blocks.LAPIS_ORE || block == Blocks.DEEPSLATE_LAPIS_ORE) {
            return new LootDrop(new ItemStack(Items.LAPIS_LAZULI), 4, 9, Conditional.affectedByFortune);
        } else if (block == Blocks.DIAMOND_ORE || block == Blocks.DEEPSLATE_DIAMOND_ORE) {
            return new LootDrop(new ItemStack(Items.DIAMOND), 1, 1, Conditional.affectedByFortune);
        } else if (block == Blocks.EMERALD_ORE || block == Blocks.DEEPSLATE_EMERALD_ORE) {
            return new LootDrop(new ItemStack(Items.EMERALD), 1, 1, Conditional.affectedByFortune);
        } else if (block == Blocks.NETHER_QUARTZ_ORE) {
            return new LootDrop(new ItemStack(Items.QUARTZ), 1, 1, Conditional.affectedByFortune);
        } else if (block == Blocks.NETHER_GOLD_ORE) {
            return new LootDrop(new ItemStack(Items.GOLD_NUGGET), 2, 6, Conditional.affectedByFortune);
        } else if (block == Blocks.ANCIENT_DEBRIS) {
            return new LootDrop(new ItemStack(Items.ANCIENT_DEBRIS), 1, 1);
        }
        return new LootDrop(new ItemStack(block.asItem()), 1, 1);
    }

    private static void addFallbackOre(Block block, int veinSize, float veinsPerChunk, LootDrop drop, Set<Block> seen, List<OreInfo> configs) {
        if (block != null && !seen.contains(block)) {
            seen.add(block);
            configs.add(new OreInfo(block, veinSize, veinsPerChunk, drop));
        }
    }

    private static void registerOre(
            IWorldGenRegistry iRegistry,
            Restriction restriction,
            ItemLike oreBlock,
            ItemLike deepslateBlock,
            int veinSize,
            int veinsPerChunk,
            int minY,
            int maxY,
            LootDrop drop
    ) {
        if (oreBlock == null) return;
        ItemStack oreStack = new ItemStack(oreBlock);
        if (oreStack.isEmpty()) return;

        ItemStack deepslateStack = deepslateBlock != null ? new ItemStack(deepslateBlock) : ItemStack.EMPTY;
        DistributionSquare dist = new DistributionSquare(veinSize, veinsPerChunk, minY, maxY);

        if (iRegistry != null) {
            if (!deepslateStack.isEmpty()) {
                iRegistry.register(oreStack, deepslateStack, dist, restriction, drop);
            } else {
                iRegistry.register(oreStack, dist, restriction, drop);
            }
        }

        try {
            WorldGenEntry entry;
            if (!deepslateStack.isEmpty()) {
                entry = new WorldGenEntry(oreStack, deepslateStack, dist, restriction, false, new LootDrop[]{drop});
            } else {
                entry = new WorldGenEntry(oreStack, dist, restriction, false, new LootDrop[]{drop});
            }
            WorldGenRegistry.getInstance().registerEntry(entry);
        } catch (Throwable ignored) {
        }
    }

    private static class OreInfo {
        final Block block;
        final int veinSize;
        final float veinsPerChunk;
        final LootDrop drop;

        OreInfo(Block block, int veinSize, float veinsPerChunk, LootDrop drop) {
            this.block = block;
            this.veinSize = veinSize;
            this.veinsPerChunk = veinsPerChunk;
            this.drop = drop;
        }
    }
}
