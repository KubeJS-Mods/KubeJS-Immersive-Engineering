package dev.latvian.mods.kubejs.immersiveengineering;

import blusunrize.immersiveengineering.api.crafting.AlloyRecipe;
import blusunrize.immersiveengineering.api.crafting.ArcFurnaceRecipe;
import blusunrize.immersiveengineering.api.crafting.BlastFurnaceFuel;
import blusunrize.immersiveengineering.api.crafting.BlastFurnaceRecipe;
import blusunrize.immersiveengineering.api.crafting.ClocheFertilizer;
import blusunrize.immersiveengineering.api.crafting.ClocheRecipe;
import blusunrize.immersiveengineering.api.crafting.CokeOvenRecipe;
import blusunrize.immersiveengineering.api.crafting.CrusherRecipe;
import blusunrize.immersiveengineering.api.crafting.MetalPressRecipe;
import blusunrize.immersiveengineering.api.crafting.MixerRecipe;
import blusunrize.immersiveengineering.api.crafting.SawmillRecipe;
import blusunrize.immersiveengineering.api.crafting.SqueezerRecipe;
import blusunrize.immersiveengineering.api.multiblocks.MultiblockHandler;
import blusunrize.immersiveengineering.common.util.RecipeSerializers;
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
		event.registerShaped(RecipeSerializers.TURN_AND_COPY_SERIALIZER.getId());

		event.register(AlloyRecipe.SERIALIZER.getId(), AlloyRecipeJS::new);
		event.register(BlastFurnaceRecipe.SERIALIZER.getId(), BlastFurnaceRecipeJS::new);
		event.register(BlastFurnaceFuel.SERIALIZER.getId(), BlastFurnaceFuelRecipeJS::new);
		event.register(CokeOvenRecipe.SERIALIZER.getId(), CokeOvenRecipeJS::new);
		event.register(ClocheRecipe.SERIALIZER.getId(), ClocheRecipeJS::new);
		event.register(ClocheFertilizer.SERIALIZER.getId(), ClocheFertilizerRecipeJS::new);
		//event.register("immersiveengineering:blueprint", BlueprintCraftingRecipeSerializer::new);
		event.register(MetalPressRecipe.SERIALIZER.getId(), MetalPressRecipeJS::new);
		event.register(ArcFurnaceRecipe.SERIALIZER.getId(), ArcFurnaceRecipeJS::new);
		//event.register("immersiveengineering:bottling_machine", BottlingMachineRecipeSerializer::new);
		event.register(CrusherRecipe.SERIALIZER.getId(), CrusherRecipeJS::new);
		event.register(SawmillRecipe.SERIALIZER.getId(), SawmillRecipeJS::new);
		//event.register("immersiveengineering:fermenter", FermenterRecipeSerializer::new);
		event.register(SqueezerRecipe.SERIALIZER.getId(), SqueezerRecipeJS::new);
		//event.register("immersiveengineering:refinery", RefineryRecipeSerializer::new);
		event.register(MixerRecipe.SERIALIZER.getId(), MixerRecipeJS::new);
		//event.register("immersiveengineering:mineral_mix", MineralMixSerializer::new);

		//event.register("immersivepetroleum:distillation", DistillationRecipeSerializer::new);
		//event.register("immersivepetroleum:reservoirs", ReservoirTypeSerializer::new);
	}

	@SubscribeEvent
	public void onMultiblockForm(MultiblockHandler.MultiblockFormEvent event) {
		if (new MultiblockFormEventJS(event).post(ScriptType.STARTUP, MultiblockFormEventJS.ID)) {
			event.setCanceled(true);
		}
	}
}
