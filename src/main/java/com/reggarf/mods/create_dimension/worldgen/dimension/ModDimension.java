
package com.reggarf.mods.create_dimension.worldgen.dimension;

import net.neoforged.neoforge.client.event.RegisterDimensionSpecialEffectsEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.api.distmarker.Dist;

import net.minecraft.world.phys.Vec3;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.DimensionSpecialEffects;

public class ModDimension {
	@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
	public static class CreatedimensionSpecialEffectsHandler {
		@SubscribeEvent
        public static void registerDimensionSpecialEffects(RegisterDimensionSpecialEffectsEvent event) {
            DimensionSpecialEffects customEffect = new DimensionSpecialEffects(300f, true, DimensionSpecialEffects.SkyType.NORMAL, false, false) {
                @Override
                public Vec3 getBrightnessDependentFogColor(Vec3 color, float sunHeight) {
                    return color.multiply(sunHeight * 0.94 + 0.06, sunHeight * 0.94 + 0.06, sunHeight * 0.91 + 0.09);
                }
                @Override
                public boolean isFoggyAt(int x, int y) {
                    return false;
                }
            };
            event.register(ResourceLocation.parse("create_dimension:steamworks_realm"), customEffect);
        }
	}
}
