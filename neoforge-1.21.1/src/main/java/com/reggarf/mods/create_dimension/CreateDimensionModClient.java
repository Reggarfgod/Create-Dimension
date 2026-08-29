package com.reggarf.mods.create_dimension;

import com.reggarf.mods.create_dimension.compat.jer.ModJERPlugin;
import com.reggarf.mods.create_dimension.ponder.ModPonderPlugin;
import net.createmod.catnip.config.ui.BaseConfigScreen;
import net.createmod.ponder.foundation.PonderIndex;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModList;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.ModelEvent;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

public class CreateDimensionModClient {

    public static void onCtorClient(IEventBus modEventBus) {
        modEventBus.addListener(CreateDimensionModClient::clientInit);
        modEventBus.addListener(CreateDimensionModClient::onRegisterAdditionalModels);
    }

    public static void clientInit(final FMLClientSetupEvent event) {
        PonderIndex.addPlugin(new ModPonderPlugin());

        ModLoadingContext.get().registerExtensionPoint(
                IConfigScreenFactory.class,
                () -> (mc, previous) -> new BaseConfigScreen(previous, CreateDimensionMod.MODID)
        );

        if (ModList.get().isLoaded("jeresources")) {
            ModJERPlugin.registerOres(null);
        }
    }

    public static void onRegisterAdditionalModels(ModelEvent.RegisterAdditional event) {
    }
}
