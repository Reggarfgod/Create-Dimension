package com.reggarf.mods.create_dimension.world.dimension;


import com.mojang.datafixers.util.Pair;
import com.reggarf.mods.create_dimension.CreateDimensionMod;
import com.reggarf.mods.create_dimension.registry.ModBiomes;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.*;
import net.minecraft.world.level.dimension.BuiltinDimensionTypes;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;

import java.util.List;
import java.util.OptionalLong;

public class ModDimension {

    public static final ResourceKey<LevelStem> STEAMWORKS_REALM_KEY =
            ResourceKey.create(Registries.LEVEL_STEM, new ResourceLocation(CreateDimensionMod.MODID, "steamworks_realm"));

    public static final ResourceKey<Level> STEAMWORKS_REALM_LEVEL_KEY =
            ResourceKey.create(Registries.DIMENSION, new ResourceLocation(CreateDimensionMod.MODID, "steamworks_realm"));

    public static final ResourceKey<DimensionType> STEAMWORKS_REALM_DIM_TYPE =
            ResourceKey.create(Registries.DIMENSION_TYPE, new ResourceLocation(CreateDimensionMod.MODID, "steamworks_realm_type"));


    public static void bootstrapType(BootstapContext<DimensionType> context) {
        context.register(STEAMWORKS_REALM_DIM_TYPE, new DimensionType(
                OptionalLong.of(12000),
                false, false, false, false,
                1.0,
                true, false,
                -64, 704, 704,
                BlockTags.INFINIBURN_OVERWORLD,
                BuiltinDimensionTypes.OVERWORLD_EFFECTS,
                1.0f,
                new DimensionType.MonsterSettings(false, false, ConstantInt.of(0), 0)
        ));
    }

    public static void bootstrapStem(BootstapContext<LevelStem> context) {
        HolderGetter<Biome> biomes = context.lookup(Registries.BIOME);
        HolderGetter<DimensionType> dimTypes = context.lookup(Registries.DIMENSION_TYPE);
        HolderGetter<NoiseGeneratorSettings> noiseSettings = context.lookup(Registries.NOISE_SETTINGS);

        NoiseBasedChunkGenerator chunkGenerator = new NoiseBasedChunkGenerator(
                MultiNoiseBiomeSource.createFromList(
                        new Climate.ParameterList<>(List.of(
                                Pair.of(Climate.parameters(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F),
                                        biomes.getOrThrow(ModBiomes.MINING))
                        ))
                ),
                noiseSettings.getOrThrow(NoiseGeneratorSettings.AMPLIFIED)
        );

        context.register(STEAMWORKS_REALM_KEY,
                new LevelStem(dimTypes.getOrThrow(STEAMWORKS_REALM_DIM_TYPE), chunkGenerator));
    }
}
