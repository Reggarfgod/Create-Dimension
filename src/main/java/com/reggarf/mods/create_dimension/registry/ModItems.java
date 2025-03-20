package com.reggarf.mods.create_dimension.registry;

import com.reggarf.mods.create_dimension.CreateDimensionMod;
import com.reggarf.mods.create_dimension.item.igniter;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredItem;

import net.minecraft.world.item.Item;



public class ModItems {
	public static final DeferredRegister.Items REGISTRY = DeferredRegister.createItems(CreateDimensionMod.MODID);
	public static final DeferredItem<Item> STEAMWORKS_REALM = REGISTRY.register("steamworks_realm", igniter::new);

}
