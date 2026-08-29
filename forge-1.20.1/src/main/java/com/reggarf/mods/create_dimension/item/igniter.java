package com.reggarf.mods.create_dimension.item;

import com.reggarf.mods.create_dimension.block.ModPortalBlock;
import net.minecraft.ChatFormatting;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.InteractionResult;
import net.minecraft.core.BlockPos;
import org.jetbrains.annotations.Nullable;
import net.minecraft.client.gui.screens.Screen;

import java.util.List;

public class igniter extends Item {

    public igniter(Properties properties) {
        super(properties
                .rarity(Rarity.COMMON)
                .durability(64));
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel,
                                List<Component> tooltipComponents, TooltipFlag pIsAdvanced) {

        tooltipComponents.add(Component.literal("§7[Hold §eShift§7 for Summary]").withStyle(ChatFormatting.DARK_GRAY));
        if (Screen.hasShiftDown()) {
            tooltipComponents.add(Component.translatable("tooltip.create_dimension.steamworks_realm_igniter.tooltip")
                    .withStyle(ChatFormatting.GOLD));
        }
        super.appendHoverText(pStack, pLevel, tooltipComponents, pIsAdvanced);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Player entity = context.getPlayer();
        BlockPos pos = context.getClickedPos().relative(context.getClickedFace());
        ItemStack itemstack = context.getItemInHand();
        Level world = context.getLevel();

        if (!entity.mayUseItemAt(pos, context.getClickedFace(), itemstack)) {
            return InteractionResult.FAIL;
        } else {
            boolean success = false;
            if (world.isEmptyBlock(pos)) {
                ModPortalBlock.portalSpawn(world, pos);
                itemstack.hurtAndBreak(1, entity, c -> c.broadcastBreakEvent(context.getHand()));
                success = true;
            }
            return success ? InteractionResult.SUCCESS : InteractionResult.FAIL;
        }
    }
}
