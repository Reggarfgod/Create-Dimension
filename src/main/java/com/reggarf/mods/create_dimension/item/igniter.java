
package com.reggarf.mods.create_dimension.item;


import com.reggarf.mods.create_dimension.block.ModPortalBlock;
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

import java.util.List;

public class igniter extends Item {
	public igniter() {
		super(new Properties().rarity(Rarity.COMMON).durability(64));
	}

	@Override
	public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> tooltipComponents, TooltipFlag pIsAdvanced) {
		tooltipComponents.add(Component.translatable("tooltip.create_dimension.steamworks_realm_igniter.tooltip"));
		tooltipComponents.add(Component.translatable("tooltip.create_dimension.not_craftable_steamworks_realm_igniter.tooltip"));
		tooltipComponents.add(Component.translatable("tooltip.create_dimension.ores_info"));
		tooltipComponents.add(Component.translatable("tooltip.create_dimension.iron"));
		tooltipComponents.add(Component.translatable("tooltip.create_dimension.gold"));
		tooltipComponents.add(Component.translatable("tooltip.create_dimension.copper"));
		tooltipComponents.add(Component.translatable("tooltip.create_dimension.zinc"));
		tooltipComponents.add(Component.translatable("tooltip.create_dimension.diamond"));
		tooltipComponents.add(Component.translatable("tooltip.create_dimension.emerald"));
		tooltipComponents.add(Component.translatable("tooltip.create_dimension.lapis"));
		tooltipComponents.add(Component.translatable("tooltip.create_dimension.quartz"));
		tooltipComponents.add(Component.translatable("tooltip.create_dimension.redstone"));
		tooltipComponents.add(Component.translatable("tooltip.create_dimension.netherite"));

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
			int x = pos.getX();
			int y = pos.getY();
			int z = pos.getZ();
			boolean success = false;
			if (world.isEmptyBlock(pos) && true) {
				ModPortalBlock.portalSpawn(world, pos);
				itemstack.hurtAndBreak(1, entity, c -> c.broadcastBreakEvent(context.getHand()));
				success = true;
			}
			return success ? InteractionResult.SUCCESS : InteractionResult.FAIL;
		}
	}
}
