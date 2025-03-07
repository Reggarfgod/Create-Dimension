package com.reggarf.mods.create_dimension.init;

import com.reggarf.mods.create_dimension.CreateDimensionMod;
import com.reggarf.mods.create_dimension.item.CreatedimensionItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredItem;

import net.minecraft.world.item.Item;



public class CreateDimensionModItems {
	public static final DeferredRegister.Items REGISTRY = DeferredRegister.createItems(CreateDimensionMod.MODID);
	public static final DeferredItem<Item> STEAMWORKS_REALM = REGISTRY.register("steamworks_realm", CreatedimensionItem::new);

}
