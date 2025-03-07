package com.reggarf.mods.create_dimension.init;

import com.reggarf.mods.create_dimension.CreateDimensionMod;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.network.chat.Component;
import net.minecraft.core.registries.Registries;



public class CreateDimensionModTabs {
	public static final DeferredRegister<CreativeModeTab> REGISTRY = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, CreateDimensionMod.MODID);
	public static final DeferredHolder<CreativeModeTab, CreativeModeTab> CREATEDIMENSIONTAB = REGISTRY.register("createdimensiontab",
			() -> CreativeModeTab.builder().title(Component.translatable("item_group.create_dimension.create_dimension_tab"))
					.icon(() -> new ItemStack(CreateDimensionModItems.STEAMWORKS_REALM.get())).displayItems((parameters, tabData) -> {
				tabData.accept(CreateDimensionModItems.STEAMWORKS_REALM.get());
			}).build());
}
