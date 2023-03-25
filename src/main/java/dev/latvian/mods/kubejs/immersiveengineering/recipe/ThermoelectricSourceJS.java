package dev.latvian.mods.kubejs.immersiveengineering.recipe;

import blusunrize.immersiveengineering.api.crafting.builders.ThermoelectricSourceBuilder;
import dev.latvian.mods.kubejs.block.state.BlockStatePredicate;
import dev.latvian.mods.kubejs.recipe.IngredientMatch;
import dev.latvian.mods.kubejs.recipe.ItemInputTransformer;
import dev.latvian.mods.kubejs.recipe.ItemOutputTransformer;
import dev.latvian.mods.kubejs.recipe.RecipeArguments;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

public class ThermoelectricSourceJS extends IERecipeJS {

    public ResourceLocation heatBlock;
    public Boolean isTag = false;

    @Override
    public void create(RecipeArguments args) {
        String s = args.getString(0, "");
        if (s.startsWith("#")) {
            isTag = true;
            heatBlock = new ResourceLocation(s.substring(1));
        } else {
            heatBlock = new ResourceLocation(s);
        }
    }

    public ThermoelectricSourceJS kelvin(int temp) {
        json.addProperty(ThermoelectricSourceBuilder.TEMPERATURE_KEY, temp);
        save();
        return this;
    }

    public ThermoelectricSourceJS celsius(int temp) {
        return kelvin(ThermoelectricSourceBuilder.TemperatureScale.CELSIUS.toKelvin(temp));
    }

    public ThermoelectricSourceJS fahrenheit(int temp) {
        return kelvin(ThermoelectricSourceBuilder.TemperatureScale.FAHRENHEIT.toKelvin(temp));
    }

    @Override
    public void deserialize() {
        if (json.has(ThermoelectricSourceBuilder.SINGLE_BLOCK_KEY)) {
            heatBlock = new ResourceLocation(json.get(ThermoelectricSourceBuilder.SINGLE_BLOCK_KEY).getAsString());
        } else if (json.has(ThermoelectricSourceBuilder.BLOCK_TAG_KEY)) {
            isTag = true;
            heatBlock = new ResourceLocation(json.get(ThermoelectricSourceBuilder.BLOCK_TAG_KEY).getAsString());
        }
    }

    @Override
    public void serialize() {
        if (isTag) {
            json.addProperty(ThermoelectricSourceBuilder.BLOCK_TAG_KEY, heatBlock.toString());
        } else {
            json.addProperty(ThermoelectricSourceBuilder.SINGLE_BLOCK_KEY, heatBlock.toString());
        }
    }

    @Override
    public boolean hasInput(IngredientMatch match) {
        return false;
    }

    @Override
    public boolean replaceInput(IngredientMatch match, Ingredient with, ItemInputTransformer transformer) {
        return false;
    }

    @Override
    public boolean hasOutput(IngredientMatch match) {
        return false;
    }

    @Override
    public boolean replaceOutput(IngredientMatch match, ItemStack with, ItemOutputTransformer transformer) {
        return false;
    }
}
