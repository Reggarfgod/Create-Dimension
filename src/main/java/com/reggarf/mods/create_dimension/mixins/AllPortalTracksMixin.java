package com.reggarf.mods.create_dimension.mixins;

import com.reggarf.mods.create_dimension.registry.ModBlocks;

import com.reggarf.mods.create_dimension.world.dimension.ModDimension;
import com.reggarf.mods.create_dimension.world.teleporter.ModTeleporter;
import com.simibubi.create.api.contraption.train.PortalTrackProvider;
import com.simibubi.create.content.trains.track.AllPortalTracks;
import net.createmod.catnip.math.BlockFace;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.portal.PortalInfo;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = AllPortalTracks.class, remap = false)
public abstract class AllPortalTracksMixin {

    @Inject(method = "registerDefaults", at = @At("TAIL"))
    private static void registerSteamworksPortal(CallbackInfo ci) {
        PortalTrackProvider.REGISTRY.register(ModBlocks.STEAMWORKS_REALM_PORTAL.get(), AllPortalTracksMixin::steamworksRealm);
    }

    /**
     * Defines how Create trains teleport between Overworld and Steamworks Realm.
     */
    private static PortalTrackProvider.Exit steamworksRealm(ServerLevel level, BlockFace face) {
        ResourceKey<Level> overworld = Level.OVERWORLD;
        ResourceKey<Level> steamworks = ModDimension.STEAMWORKS_REALM_LEVEL_KEY;

        return PortalTrackProvider.fromProbe(level, face, overworld, steamworks, (otherLevel, probe) -> {
            PortalInfo info = ModTeleporter.getPortalInfo(otherLevel, probe);
            return info;
        });
    }
    //without delay
//    private static PortalTrackProvider.Exit steamworksRealm(ServerLevel level, BlockFace face) {
//        ResourceKey<Level> overworld = Level.OVERWORLD;
//        ResourceKey<Level> steamworks = ResourceKey.create(
//                Registries.DIMENSION,
//                new ResourceLocation("create_dimension", "steamworks_realm"));
//
//        return PortalTrackProvider.fromProbe(level, face, overworld, steamworks, (otherLevel, probe) -> {
//            // use custom teleporter class to locate the destination portal
//            PortalInfo info = ModTeleporter.getPortalInfo(otherLevel, probe);
//            return info;
//        });
//    }

//with delay on
//    private static PortalTrackProvider.Exit steamworksRealm(ServerLevel level, BlockFace face) {
//        ResourceKey<Level> overworld = Level.OVERWORLD;
//        ResourceKey<Level> steamworks = ResourceKey.create(
//                Registries.DIMENSION,
//                new ResourceLocation("create_dimension", "steamworks_realm"));
//
//        return PortalTrackProvider.fromProbe(level, face, overworld, steamworks, (otherLevel, probe) -> {
//            if (otherLevel == null || !otherLevel.hasChunksAt(probe.blockPosition(), probe.blockPosition()))
//                return null; // Wait until fully loaded
//            return ModTeleporter.getPortalInfo(otherLevel, probe);
//        });
//    }

}
