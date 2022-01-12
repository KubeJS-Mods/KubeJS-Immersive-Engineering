package dev.latvian.kubejs.immersiveengineering;

import blusunrize.immersiveengineering.api.multiblocks.MultiblockHandler;
import dev.latvian.kubejs.KubeJSPlugin;
import dev.latvian.kubejs.immersiveengineering.event.MultiblockFormEventJS;
import dev.latvian.kubejs.immersiveengineering.recipe.AlloyRecipeJS;
import dev.latvian.kubejs.immersiveengineering.recipe.ArcFurnaceRecipeJS;
import dev.latvian.kubejs.immersiveengineering.recipe.BlastFurnaceFuelRecipeJS;
import dev.latvian.kubejs.immersiveengineering.recipe.BlastFurnaceRecipeJS;
import dev.latvian.kubejs.immersiveengineering.recipe.ClocheFertilizerRecipeJS;
import dev.latvian.kubejs.immersiveengineering.recipe.ClocheRecipeJS;
import dev.latvian.kubejs.immersiveengineering.recipe.CokeOvenRecipeJS;
import dev.latvian.kubejs.immersiveengineering.recipe.CrusherRecipeJS;
import dev.latvian.kubejs.immersiveengineering.recipe.MetalPressRecipeJS;
import dev.latvian.kubejs.immersiveengineering.recipe.SawmillRecipeJS;
import dev.latvian.kubejs.recipe.RegisterRecipeHandlersEvent;
import dev.latvian.kubejs.recipe.minecraft.ShapedRecipeJS;
import dev.latvian.kubejs.script.ScriptType;
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
		//event.register("immersiveengineering:squeezer", SqueezerRecipeSerializer::new);
		//event.register("immersiveengineering:refinery", RefineryRecipeSerializer::new);
		//event.register("immersiveengineering:mixer", MixerRecipeSerializer::new);
		//event.register("immersiveengineering:mineral_mix", MineralMixSerializer::new);

		//event.register("immersivepetroleum:distillation", DistillationRecipeSerializer::new);
		//event.register("immersivepetroleum:reservoirs", ReservoirTypeSerializer::new);
	}

	@SubscribeEvent
	public void onMultiblockForm(MultiblockHandler.MultiblockFormEvent event) {
		if (new MultiblockFormEventJS(event).post(ScriptType.SERVER, MultiblockFormEventJS.ID)) {
			event.setCanceled(true);
		}
	}
}
