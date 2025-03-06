

package com.reggarf.mods.create_dimension.init;



import com.reggarf.mods.create_dimension.CreateDimensionMod;
import com.reggarf.mods.create_dimension.block.SteamworksRealmPortalBlock;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.DeferredRegister;

import net.minecraft.world.level.block.Block;

public class CreateDimensionModBlocks {
	public static final DeferredRegister<Block> REGISTRY = DeferredRegister.create(ForgeRegistries.BLOCKS, CreateDimensionMod.MODID);
	public static final RegistryObject<Block> STEAMWORKS_REALM_PORTAL = REGISTRY.register("steamworks_realm_portal", () -> new SteamworksRealmPortalBlock());

}
