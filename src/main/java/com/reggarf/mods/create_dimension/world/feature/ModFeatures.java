package com.reggarf.mods.create_dimension.world.feature;

import com.reggarf.mods.create_dimension.CreateDimensionMod;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModFeatures {
    public static final DeferredRegister<Feature<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.FEATURES, CreateDimensionMod.MODID);

    public static final RegistryObject<Feature<NoneFeatureConfiguration>> LAYERED_ORES = REGISTRY.register(
            "layered_ores",
            () -> new LayeredOreFeature(NoneFeatureConfiguration.CODEC)
    );
}
