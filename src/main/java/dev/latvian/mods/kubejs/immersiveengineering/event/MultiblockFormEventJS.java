package dev.latvian.mods.kubejs.immersiveengineering.event;

import blusunrize.immersiveengineering.api.multiblocks.MultiblockHandler;
import dev.latvian.mods.kubejs.level.BlockContainerJS;
import dev.latvian.mods.kubejs.player.PlayerEventJS;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

public class MultiblockFormEventJS extends PlayerEventJS {
	private final MultiblockHandler.MultiblockFormEvent event;

	public MultiblockFormEventJS(MultiblockHandler.MultiblockFormEvent event) {
		this.event = event;
	}

	@Override
	public Player getEntity() { return event.getEntity(); }

	public BlockPos getClickedPos() {
		return event.getClickedBlock();
	}

	public BlockContainerJS getClickedBlock() {
		return new BlockContainerJS(getLevel(), getClickedPos());
	}

	public ResourceLocation getMultiblock() {
		return event.getMultiblock().getUniqueName();
	}

	public Vec3i getSize() {
		return event.getMultiblock().getSize(getLevel());
	}
}
