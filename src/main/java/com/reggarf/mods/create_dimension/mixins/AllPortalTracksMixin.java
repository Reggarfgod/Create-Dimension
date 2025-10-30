package com.reggarf.mods.create_dimension.mixins;

import com.simibubi.create.content.trains.track.AllPortalTracks;
import com.simibubi.create.api.contraption.train.PortalTrackProvider;
import com.reggarf.mods.create_dimension.registry.ModBlocks;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Portal;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AllPortalTracks.class)
public class AllPortalTracksMixin {

    @Inject(method = "registerDefaults", at = @At("TAIL"))
    private static void create_dimension$registerSteamworksPortal(CallbackInfo ci) {
        ResourceKey<Level> steamworksRealm = ResourceKey.create(
                Registries.DIMENSION,
                ResourceLocation.fromNamespaceAndPath("create_dimension", "steamworks_realm")
        );

        Block portalBlock = ModBlocks.STEAMWORKS_REALM_PORTAL.get();

        // Register portal track provider for custom portal
        PortalTrackProvider provider = (level, face) ->
                PortalTrackProvider.fromPortal(
                        level, face,
                        Level.OVERWORLD,
                        steamworksRealm,
                        (Portal) portalBlock
                );

        PortalTrackProvider.REGISTRY.register(portalBlock, provider);
    }
}
