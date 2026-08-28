package com.reggarf.mods.create_dimension.world.feature;

import com.reggarf.mods.create_dimension.CreateDimensionMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModFeatures {
    public static final DeferredRegister<Feature<?>> REGISTRY = DeferredRegister.create(Registries.FEATURE, CreateDimensionMod.MODID);

    public static final DeferredHolder<Feature<?>, Feature<NoneFeatureConfiguration>> LAYERED_ORES = REGISTRY.register(
            "layered_ores",
            () -> new LayeredOreFeature(NoneFeatureConfiguration.CODEC)
    );
}
