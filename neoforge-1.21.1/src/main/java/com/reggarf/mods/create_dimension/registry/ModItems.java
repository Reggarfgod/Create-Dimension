package com.reggarf.mods.create_dimension.registry;

import com.reggarf.mods.create_dimension.CreateDimensionMod;
import com.reggarf.mods.create_dimension.item.igniter;
import com.tterrag.registrate.util.entry.ItemEntry;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredItem;

import net.minecraft.world.item.Item;

import static com.reggarf.mods.create_dimension.CreateDimensionMod.REGISTRATE;


public class ModItems {

    public static final ItemEntry<igniter> STEAMWORKS_REALM = REGISTRATE
            .item("steamworks_realm", igniter::new)
            .register();
    public static void register() {}
}
