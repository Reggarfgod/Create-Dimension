package com.reggarf.mods.create_dimension.compat.jei;

import com.reggarf.mods.create_dimension.CreateDimensionMod;
import com.reggarf.mods.create_dimension.compat.jer.ModJERPlugin;
import jeresources.entry.WorldGenEntry;
import jeresources.jei.JEIConfig;
import jeresources.registry.WorldGenRegistry;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.fml.ModList;

import java.util.List;

@JeiPlugin
public class ModJEIPlugin implements IModPlugin {

    public static final ResourceLocation PLUGIN_UID = ResourceLocation.fromNamespaceAndPath(CreateDimensionMod.MODID, "jei_plugin");

    @Override
    public ResourceLocation getPluginUid() {
        return PLUGIN_UID;
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        if (ModList.get().isLoaded("jeresources")) {
            try {
                ModJERPlugin.registerOres(null);
                List<WorldGenEntry> entries = WorldGenRegistry.getInstance().getWorldGen();
                if (entries != null && !entries.isEmpty()) {
                    registration.addRecipes(JEIConfig.WORLD_GEN_TYPE, entries);
                }
            } catch (Throwable t) {
                CreateDimensionMod.LOGGER.error("Failed to register recipes in JEI plugin", t);
            }
        }
    }
}
