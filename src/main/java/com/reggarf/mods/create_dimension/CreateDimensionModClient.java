package com.reggarf.mods.create_dimension;



import com.reggarf.mods.create_dimension.ponder.ModPonderPlugin;
import net.createmod.ponder.foundation.PonderIndex;
import net.minecraftforge.client.event.ModelEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

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