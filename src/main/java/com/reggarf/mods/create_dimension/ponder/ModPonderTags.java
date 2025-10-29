package com.reggarf.mods.create_dimension.ponder;


import com.reggarf.mods.create_dimension.CreateDimensionMod;
import com.reggarf.mods.create_dimension.registry.ModBlocks;
import com.reggarf.mods.create_dimension.registry.ModItems;
import com.simibubi.create.AllBlocks;
import com.tterrag.registrate.util.entry.RegistryEntry;
import net.createmod.catnip.platform.CatnipServices;
import net.createmod.ponder.api.registration.PonderTagRegistrationHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ItemLike;

import static com.simibubi.create.infrastructure.ponder.AllCreatePonderTags.KINETIC_RELAYS;

public class ModPonderTags {
    public static final ResourceLocation
    PORTAL = loc("portal");
    private static ResourceLocation loc(String id) {
        return CreateDimensionMod.asResource(id);
    }

    public static void register(PonderTagRegistrationHelper<ResourceLocation> helper) {
        PonderTagRegistrationHelper<RegistryEntry<?>> HELPER = helper.withKeyFunction(RegistryEntry::getId);
        PonderTagRegistrationHelper<ItemLike> itemHelper = helper.withKeyFunction(
                CatnipServices.REGISTRIES::getKeyOrThrow);

        helper.registerTag(PORTAL)
                .item(ModItems.STEAMWORKS_REALM.asItem())
                .title("Steamworks Realm Portal")
                .description("A dimensional gateway powered by Create’s engineering trains and players can travel through it just like a Nether Portal.")
                .register();


        HELPER.addToTag(PORTAL)
                //BASE
                .add(ModItems.STEAMWORKS_REALM);
    }
}
