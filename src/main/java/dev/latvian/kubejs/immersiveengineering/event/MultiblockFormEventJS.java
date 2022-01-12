package dev.latvian.kubejs.immersiveengineering.event;

import blusunrize.immersiveengineering.api.multiblocks.MultiblockHandler;
import dev.latvian.kubejs.entity.EntityJS;
import dev.latvian.kubejs.player.PlayerEventJS;
import dev.latvian.kubejs.world.BlockContainerJS;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.resources.ResourceLocation;

public class MultiblockFormEventJS extends PlayerEventJS {
	public static final String ID = "ie.multiblock.form";

	@Override
	public boolean canCancel() {
		return true;
	}

	private final MultiblockHandler.MultiblockFormEvent event;

	public MultiblockFormEventJS(MultiblockHandler.MultiblockFormEvent event) {
		this.event = event;
	}

	@Override
	public EntityJS getEntity() {
		return entityOf(event.getPlayer());
	}

	public BlockPos getClickedPos() {
		return event.getClickedBlock();
	}

	public BlockContainerJS getClickedBlock() {
		return new BlockContainerJS(getWorld().minecraftWorld, getClickedPos());
	}

	public ResourceLocation getMultiblock() {
		return event.getMultiblock().getUniqueName();
	}

	public Vec3i getSize() {
		return event.getMultiblock().getSize(getWorld().minecraftWorld);
	}
}
