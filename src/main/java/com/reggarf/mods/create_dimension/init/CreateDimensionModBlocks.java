package com.reggarf.mods.create_dimension.init;

import com.reggarf.mods.create_dimension.CreateDimensionMod;
import com.reggarf.mods.create_dimension.block.CreatedimensionPortalBlock;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredBlock;

import net.minecraft.world.level.block.Block;



public class CreateDimensionModBlocks {
	public static final DeferredRegister.Blocks REGISTRY = DeferredRegister.createBlocks(CreateDimensionMod.MODID);
	public static final DeferredBlock<Block> STEAMWORKS_REALM_PORTAL = REGISTRY.register("steamworks_realm_portal", CreatedimensionPortalBlock::new);

}
