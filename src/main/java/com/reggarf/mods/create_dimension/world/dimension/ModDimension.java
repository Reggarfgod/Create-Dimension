package com.reggarf.mods.create_dimension.world.dimension;

import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.BuiltinDimensionTypes;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.dimension.LevelStem;

import java.util.OptionalLong;

import static com.reggarf.mods.create_dimension.CreateDimensionMod.MODID;

public class ModDimension {
    public static final ResourceKey<LevelStem> STEAMWORKS_REALM_KEY = ResourceKey.create(Registries.LEVEL_STEM,
            new ResourceLocation(MODID, "steamworks_realm"));
    public static final ResourceKey<Level> STEAMWORKS_REALM_LEVEL_KEY = ResourceKey.create(Registries.DIMENSION,
            new ResourceLocation(MODID, "steamworks_realm"));
    public static final ResourceKey<DimensionType> STEAMWORKS_REALM_DIM_TYPE = ResourceKey.create(Registries.DIMENSION_TYPE,
            new ResourceLocation(MODID, "steamworks_realm_type"));


    public static void bootstrapType(BootstapContext<DimensionType> context) {
        context.register(STEAMWORKS_REALM_DIM_TYPE, new DimensionType(
                OptionalLong.of(12000), // fixedTime
                false, // hasSkylight
                false, // hasCeiling
                false, // ultraWarm
                false, // natural
                0.85, // coordinateScale
                true, // bedWorks
                false, // respawnAnchorWorks
                -64, // minY
                704, // height
                704, // logicalHeight
                BlockTags.INFINIBURN_OVERWORLD, // infiniburn
                BuiltinDimensionTypes.OVERWORLD_EFFECTS, // effectsLocation
                0f, // ambientLight
                new DimensionType.MonsterSettings(false, false, ConstantInt.of(0), 0)));
    }

    public static void bootstrapStem(BootstapContext<LevelStem> context) {

    }
}