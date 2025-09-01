package dev.latvian.mods.kubejs.immersiveengineering.event;

import dev.latvian.mods.kubejs.event.EventGroup;
import dev.latvian.mods.kubejs.event.EventTargetType;
import dev.latvian.mods.kubejs.event.TargetedEventHandler;
import net.minecraft.resources.ResourceLocation;

public interface IEEvents {
	EventGroup GROUP = EventGroup.of("IEEvents");

	TargetedEventHandler<ResourceLocation> MULTIBLOCK_FORM = IEEvents.GROUP.server("multiblockForm", () -> MultiblockFormKubeEvent.class).supportsTarget(EventTargetType.ID).hasResult();
}
