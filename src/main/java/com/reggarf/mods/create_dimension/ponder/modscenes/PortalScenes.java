package com.reggarf.mods.create_dimension.ponder.modscenes;

import com.reggarf.mods.create_dimension.registry.ModBlocks;
import com.reggarf.mods.create_dimension.registry.ModItems;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.foundation.ponder.CreateSceneBuilder;
import net.createmod.ponder.api.scene.SceneBuilder;
import net.createmod.ponder.api.scene.SceneBuildingUtil;
import net.createmod.ponder.api.scene.Selection;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;

public class PortalScenes {

    public static void portalIgnite(SceneBuilder builder, SceneBuildingUtil util) {
        CreateSceneBuilder scene = new CreateSceneBuilder(builder);

        scene.title("train_portal", "Creating and Lighting a Train Casing Portal");
        scene.configureBasePlate(0, 0, 9);
        scene.scaleSceneView(0.8f);
        scene.rotateCameraY(-120);
        scene.setSceneOffsetY(-1.5f);
        scene.world().showSection(util.select().fromTo(0, 0, 0, 9, 7, 9), Direction.UP);
        scene.idle(10);
        BlockPos base = util.grid().at(4, 1, 4);
        Selection frame = util.select().fromTo(3, 1, 4, 6, 5, 4); // 4 wide (x=3→6), 5 tall (y=1→5)
        scene.overlay().showText(60)
                .text("Start by building a 4x5 portal frame using Train Casings")
                .attachKeyFrame()
                .placeNearTarget()
                .pointAt(util.vector().of(4.5, 3, 4));
        scene.idle(20);
        for (int x = 3; x <= 6; x++) {
            scene.world().setBlock(util.grid().at(x, 1, 4), AllBlocks.RAILWAY_CASING.get().defaultBlockState(), false);
        }
        scene.idle(10);
        for (int y = 2; y <= 5; y++) {
            scene.world().setBlock(util.grid().at(3, y, 4), AllBlocks.RAILWAY_CASING.get().defaultBlockState(), false);
            scene.world().setBlock(util.grid().at(6, y, 4), AllBlocks.RAILWAY_CASING.get().defaultBlockState(), false);
        }
        scene.idle(10);
        for (int x = 3; x <= 6; x++) {
            scene.world().setBlock(util.grid().at(x, 5, 4), AllBlocks.RAILWAY_CASING.get().defaultBlockState(), false);
        }
        scene.idle(20);
        ItemStack igniter = new ItemStack(ModItems.STEAMWORKS_REALM.get());
        scene.overlay().showControls(util.vector().of(4.5, 3, 4), net.createmod.catnip.math.Pointing.RIGHT, 60)
                .rightClick()
                .withItem(igniter);
        scene.overlay().showText(60)
                .text("Ignite the portal using the Steamworks Realm Igniter")
                .attachKeyFrame()
                .placeNearTarget()
                .pointAt(util.vector().of(4.5, 3, 4));
        scene.idle(40);
        scene.effects().indicateSuccess(util.grid().at(4, 3, 4));
        for (int y = 2; y <= 4; y++) {
            for (int x = 4; x <= 5; x++) {
                scene.world().setBlock(util.grid().at(x, y, 4),
                        ModBlocks.STEAMWORKS_REALM_PORTAL.get().defaultBlockState(), false);
            }
        }
        scene.idle(20);
        scene.overlay().showText(80)
                .text("The Train Portal activates, ready to transport you across dimensions!")
                .attachKeyFrame()
                .placeNearTarget()
                .pointAt(util.vector().of(4.5, 3, 4));
        scene.idle(80);
    }
}
