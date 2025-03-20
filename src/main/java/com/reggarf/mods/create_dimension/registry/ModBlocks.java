package com.reggarf.mods.create_dimension.registry;

import com.reggarf.mods.create_dimension.CreateDimensionMod;
import com.reggarf.mods.create_dimension.block.ModPortalBlock;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredBlock;

import net.minecraft.world.level.block.Block;



public class ModBlocks {
	public static final DeferredRegister.Blocks REGISTRY = DeferredRegister.createBlocks(CreateDimensionMod.MODID);
	public static final DeferredBlock<Block> STEAMWORKS_REALM_PORTAL = REGISTRY.register("steamworks_realm_portal", ModPortalBlock::new);

}
