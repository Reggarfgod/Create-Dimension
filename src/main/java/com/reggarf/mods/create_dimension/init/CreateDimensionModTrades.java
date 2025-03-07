package com.reggarf.mods.create_dimension.init;

import com.reggarf.mods.create_better_villagers.init.ModVillagerProfessions;
import net.neoforged.neoforge.event.village.VillagerTradesEvent;
import net.neoforged.neoforge.common.BasicItemListing;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;

import net.minecraft.world.item.Items;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.npc.VillagerProfession;

@EventBusSubscriber
public class CreateDimensionModTrades {
	@SubscribeEvent
	public static void registerTrades(VillagerTradesEvent event) {
		if (event.getType() == ModVillagerProfessions.ANDESITE_WORKER.get()) {
			event.getTrades().get(1).add(new BasicItemListing(new ItemStack(Items.EMERALD, 3), new ItemStack(CreateDimensionModItems.STEAMWORKS_REALM.get()), 2, 5, 0.05f));
		}
	}
}
