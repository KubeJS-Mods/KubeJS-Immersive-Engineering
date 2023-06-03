package dev.latvian.mods.kubejs.immersiveengineering.event;

import dev.latvian.mods.kubejs.event.EventGroup;
import dev.latvian.mods.kubejs.event.EventHandler;
import dev.latvian.mods.kubejs.event.Extra;

public interface IEEvents {
    EventGroup GROUP = EventGroup.of("IEEvents");

    EventHandler MULTIBLOCK_FORM = IEEvents.GROUP.startup("multiblockForm", () -> MultiblockFormEventJS.class).extra(Extra.ID).hasResult();
}
