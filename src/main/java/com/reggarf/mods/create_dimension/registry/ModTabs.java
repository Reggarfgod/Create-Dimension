
package com.reggarf.mods.create_dimension.registry;

import com.reggarf.mods.create_dimension.CreateDimensionMod;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.network.chat.Component;
import net.minecraft.core.registries.Registries;

public class ModTabs {
	public static final DeferredRegister<CreativeModeTab> REGISTRY = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, CreateDimensionMod.MODID);
	public static final RegistryObject<CreativeModeTab> CREATE_DIMENSION_TAB = REGISTRY
			.register("create_dimension_tab", () -> CreativeModeTab.builder()
					.title(Component.translatable("item_group.create_dimension.create_dimension_tab"))
					.icon(() -> new ItemStack(ModItems.STEAMWORKS_REALM.get()))
					.displayItems((parameters, tabData) -> {
				tabData.accept(ModItems.STEAMWORKS_REALM.get());
			}).build());
}
