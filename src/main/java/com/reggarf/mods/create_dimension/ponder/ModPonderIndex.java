package com.reggarf.mods.create_dimension.ponder;


import com.reggarf.mods.create_dimension.ponder.modscenes.PortalScenes;
import com.reggarf.mods.create_dimension.registry.ModItems;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.infrastructure.ponder.scenes.highLogistics.FrogAndConveyorScenes;
import com.tterrag.registrate.util.entry.ItemProviderEntry;
import com.tterrag.registrate.util.entry.RegistryEntry;
import net.createmod.ponder.api.registration.PonderSceneRegistrationHelper;
import net.minecraft.resources.ResourceLocation;

public class ModPonderIndex {

    public static void register(PonderSceneRegistrationHelper<ResourceLocation> helper) {

        PonderSceneRegistrationHelper<ItemProviderEntry<?,?>> HELPER = helper.withKeyFunction(RegistryEntry::getId);

        HELPER.forComponents(
                         ModItems.STEAMWORKS_REALM,
                        AllBlocks.RAILWAY_CASING)
                .addStoryBoard("portal", PortalScenes::portalIgnite);

    }
}
