package dev.latvian.kubejs.immersiveengineering;

import dev.latvian.kubejs.recipe.RegisterRecipeHandlersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * @author LatvianModder
 */
@Mod(KubeJSImmersiveEngineering.MOD_ID)
@Mod.EventBusSubscriber(modid = KubeJSImmersiveEngineering.MOD_ID)
public class KubeJSImmersiveEngineering
{
	public static final String MOD_ID = "kubejs_immersive_engineering";

	@SubscribeEvent
	public static void registerRecipeHandlers(RegisterRecipeHandlersEvent event)
	{
		event.register("immersiveengineering:alloy", AlloyRecipeJS::new);
		event.register("immersiveengineering:blast_furnace", BlastFurnaceRecipeJS::new);
		event.register("immersiveengineering:blast_furnace_fuel", BlastFurnaceFuelRecipeJS::new);
		event.register("immersiveengineering:coke_oven", CokeOvenRecipeJS::new);
		event.register("immersiveengineering:cloche", ClocheRecipeJS::new);
		//event.register("immersiveengineering:fertilizer", ClocheFertilizerSerializer::new);
		//event.register("immersiveengineering:blueprint", BlueprintCraftingRecipeSerializer::new);
		event.register("immersiveengineering:metal_press", MetalPressRecipeJS::new);
		//event.register("immersiveengineering:arc_furnace", ArcFurnaceRecipeSerializer::new);
		//event.register("immersiveengineering:bottling_machine", BottlingMachineRecipeSerializer::new);
		event.register("immersiveengineering:crusher", CrusherRecipeJS::new);
		//event.register("immersiveengineering:sawmill", SawmillRecipeSerializer::new);
		//event.register("immersiveengineering:fermenter", FermenterRecipeSerializer::new);
		//event.register("immersiveengineering:squeezer", SqueezerRecipeSerializer::new);
		//event.register("immersiveengineering:refinery", RefineryRecipeSerializer::new);
		//event.register("immersiveengineering:mixer", MixerRecipeSerializer::new);
		//event.register("immersiveengineering:mineral_mix", MineralMixSerializer::new);

		//event.register("immersivepetroleum:distillation", DistillationRecipeSerializer::new);
		//event.register("immersivepetroleum:reservoirs", ReservoirTypeSerializer::new);
	}
}