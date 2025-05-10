

package com.reggarf.mods.create_dimension.registry;



import com.reggarf.mods.create_better_villagers.config.ModConfigs;
import com.reggarf.mods.create_better_villagers.init.ModVillagers;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.common.BasicItemListing;

import net.minecraft.world.item.Items;
import net.minecraft.world.item.ItemStack;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ModTrades {
	@SubscribeEvent
	public static void registerTrades(VillagerTradesEvent event) {
		if (ModConfigs.ENABLE_ANDESITE_WORKER.get()) {
		if (event.getType() == ModVillagers.ANDESITE_WORKER.get()) {
			event.getTrades().get(1).add(new BasicItemListing(new ItemStack(Items.EMERALD, 3),

					new ItemStack(ModItems.STEAMWORKS_REALM.get()), 2, 10, 0.06f));
		   }
		}
	}
}
