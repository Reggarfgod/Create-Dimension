package com.reggarf.mods.create_dimension.registry;

import com.reggarf.mods.create_better_villagers.Create_better_villagers;

import com.reggarf.mods.create_better_villagers.init.ModVillagers;
import net.neoforged.neoforge.event.village.VillagerTradesEvent;
import net.neoforged.neoforge.common.BasicItemListing;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;

import net.minecraft.world.item.Items;
import net.minecraft.world.item.ItemStack;

@EventBusSubscriber
public class ModTrades {
	@SubscribeEvent
	public static void registerTrades(VillagerTradesEvent event) {
		if (Create_better_villagers.CONFIG.common.ENABLE_ANDESITE_WORKER) {
			if (event.getType() == ModVillagers.ANDESITE_WORKER.value()) {
				event.getTrades().get(1).add(new BasicItemListing(new ItemStack(Items.EMERALD, 3), new ItemStack(ModItems.STEAMWORKS_REALM.get()), 2, 5, 0.05f));
			}
		}
	}
}
