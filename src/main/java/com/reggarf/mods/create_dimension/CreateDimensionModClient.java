package com.reggarf.mods.create_dimension;



import com.reggarf.mods.create_dimension.ponder.ModPonderPlugin;
import net.createmod.ponder.foundation.PonderIndex;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.ModelEvent;


public class CreateDimensionModClient {

    public static void onCtorClient(IEventBus modEventBus) {

        modEventBus.addListener(CreateDimensionModClient::clientInit);
        modEventBus.addListener(CreateDimensionModClient::onRegisterAdditionalModels);
    }
    public static void clientInit(final FMLClientSetupEvent event) {
        PonderIndex.addPlugin(new ModPonderPlugin());
    }
    public static void onRegisterAdditionalModels(ModelEvent.RegisterAdditional event){
    }
}