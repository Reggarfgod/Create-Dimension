package com.reggarf.mods.create_dimension.ponder;




import net.createmod.ponder.api.registration.PonderPlugin;
import net.createmod.ponder.api.registration.PonderSceneRegistrationHelper;
import net.createmod.ponder.api.registration.PonderTagRegistrationHelper;
import net.minecraft.resources.ResourceLocation;

import static com.reggarf.mods.create_dimension.CreateDimensionMod.MODID;


public class ModPonderPlugin implements PonderPlugin {
    @Override
    public String getModId() {
        return MODID;
    }

    @Override
    public void registerScenes(PonderSceneRegistrationHelper<ResourceLocation> helper) {
        ModPonderIndex.register(helper);
    }

    @Override
    public void registerTags(PonderTagRegistrationHelper<ResourceLocation> helper) {
        ModPonderTags.register(helper);
    }
}
