package dev.latvian.mods.kubejs.immersiveengineering;

import blusunrize.immersiveengineering.api.multiblocks.MultiblockHandler;
import dev.latvian.mods.kubejs.KubeJSPlugin;
import dev.latvian.mods.kubejs.immersiveengineering.event.MultiblockFormEventJS;
import dev.latvian.mods.kubejs.immersiveengineering.recipe.AlloyRecipeJS;
import dev.latvian.mods.kubejs.immersiveengineering.recipe.ArcFurnaceRecipeJS;
import dev.latvian.mods.kubejs.immersiveengineering.recipe.BlastFurnaceFuelRecipeJS;
import dev.latvian.mods.kubejs.immersiveengineering.recipe.BlastFurnaceRecipeJS;
import dev.latvian.mods.kubejs.immersiveengineering.recipe.ClocheFertilizerRecipeJS;
import dev.latvian.mods.kubejs.immersiveengineering.recipe.ClocheRecipeJS;
import dev.latvian.mods.kubejs.immersiveengineering.recipe.CokeOvenRecipeJS;
import dev.latvian.mods.kubejs.immersiveengineering.recipe.CrusherRecipeJS;
import dev.latvian.mods.kubejs.immersiveengineering.recipe.MetalPressRecipeJS;
import dev.latvian.mods.kubejs.immersiveengineering.recipe.MixerRecipeJS;
import dev.latvian.mods.kubejs.immersiveengineering.recipe.SawmillRecipeJS;
import dev.latvian.mods.kubejs.immersiveengineering.recipe.SqueezerRecipeJS;
import dev.latvian.mods.kubejs.recipe.RegisterRecipeHandlersEvent;
import dev.latvian.mods.kubejs.recipe.minecraft.ShapedRecipeJS;
import dev.latvian.mods.kubejs.script.ScriptType;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ImmersiveEngineeringKubeJSPlugin extends KubeJSPlugin {
	@Override
	public void init() {
		MinecraftForge.EVENT_BUS.register(this);
	}

	@Override
	public void addRecipes(RegisterRecipeHandlersEvent event) {
		event.register("immersiveengineering:turn_and_copy", ShapedRecipeJS::new); // Yes this isnt the same but it should work

		event.register("immersiveengineering:alloy", AlloyRecipeJS::new);
		event.register("immersiveengineering:blast_furnace", BlastFurnaceRecipeJS::new);
		event.register("immersiveengineering:blast_furnace_fuel", BlastFurnaceFuelRecipeJS::new);
		event.register("immersiveengineering:coke_oven", CokeOvenRecipeJS::new);
		event.register("immersiveengineering:cloche", ClocheRecipeJS::new);
		event.register("immersiveengineering:fertilizer", ClocheFertilizerRecipeJS::new);
		//event.register("immersiveengineering:blueprint", BlueprintCraftingRecipeSerializer::new);
		event.register("immersiveengineering:metal_press", MetalPressRecipeJS::new);
		event.register("immersiveengineering:arc_furnace", ArcFurnaceRecipeJS::new);
		//event.register("immersiveengineering:bottling_machine", BottlingMachineRecipeSerializer::new);
		event.register("immersiveengineering:crusher", CrusherRecipeJS::new);
		event.register("immersiveengineering:sawmill", SawmillRecipeJS::new);
		//event.register("immersiveengineering:fermenter", FermenterRecipeSerializer::new);
		event.register("immersiveengineering:squeezer", SqueezerRecipeJS::new);
		//event.register("immersiveengineering:refinery", RefineryRecipeSerializer::new);
		event.register("immersiveengineering:mixer", MixerRecipeJS::new);
		//event.register("immersiveengineering:mineral_mix", MineralMixSerializer::new);

		//event.register("immersivepetroleum:distillation", DistillationRecipeSerializer::new);
		//event.register("immersivepetroleum:reservoirs", ReservoirTypeSerializer::new);
	}

	@SubscribeEvent
	public void onMultiblockForm(MultiblockHandler.MultiblockFormEvent event) {
		if(event.getPlayer().getLevel().isClientSide) return;
		if (new MultiblockFormEventJS(event).post(ScriptType.SERVER, MultiblockFormEventJS.ID)) {
			event.setCanceled(true);
		}
	}
}
