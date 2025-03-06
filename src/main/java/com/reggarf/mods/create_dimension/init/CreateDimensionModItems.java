

package com.reggarf.mods.create_dimension.init;


import com.reggarf.mods.create_dimension.CreateDimensionMod;
import com.reggarf.mods.create_dimension.item.SteamworksRealmItem;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.DeferredRegister;

import net.minecraft.world.item.Item;

public class CreateDimensionModItems {
	public static final DeferredRegister<Item> REGISTRY = DeferredRegister.create(ForgeRegistries.ITEMS, CreateDimensionMod.MODID);
	public static final RegistryObject<Item> STEAMWORKS_REALM = REGISTRY.register("steamworks_realm", () -> new SteamworksRealmItem());

}
