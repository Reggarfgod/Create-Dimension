

package com.reggarf.mods.create_dimension.init;


import com.reggarf.mods.create_better_villagers.init.CreateBetterVillagersModVillagerProfessions;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.common.BasicItemListing;

import net.minecraft.world.item.Items;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.npc.VillagerProfession;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public class CreateDimensionModTrades {
	@SubscribeEvent
	public static void registerTrades(VillagerTradesEvent event) {
		if (event.getType() == CreateBetterVillagersModVillagerProfessions.ANDESITE_WORKER.get()) {
			event.getTrades().get(1).add(new BasicItemListing(new ItemStack(Items.EMERALD, 3),

					new ItemStack(CreateDimensionModItems.STEAMWORKS_REALM.get()), 2, 10, 0.06f));
		}
	}
}
