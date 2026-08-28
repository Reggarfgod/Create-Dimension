package com.reggarf.mods.create_dimension.client;



import com.reggarf.mods.create_dimension.ponder.ModPonderPlugin;
import net.createmod.ponder.foundation.PonderIndex;
import net.minecraftforge.client.event.ModelEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class ModClientIniter {

    public static void onCtorClient(IEventBus modEventBus) {

        modEventBus.addListener(ModClientIniter::clientInit);
        modEventBus.addListener(ModClientIniter::onRegisterAdditionalModels);

    }
    public static void clientInit(final FMLClientSetupEvent event) {
        PonderIndex.addPlugin(new ModPonderPlugin());
        ModClient.onInitializeClient(event);
    }
    public static void onRegisterAdditionalModels(ModelEvent.RegisterAdditional event){

    }
}