package dev.latvian.mods.kubejs.immersiveengineering.recipe;

import blusunrize.immersiveengineering.api.crafting.builders.ThermoelectricSourceBuilder;
import dev.latvian.mods.kubejs.recipe.RecipeJS;
import dev.latvian.mods.kubejs.recipe.RecipeKey;
import dev.latvian.mods.kubejs.recipe.component.NumberComponent;
import dev.latvian.mods.kubejs.recipe.schema.RecipeSchema;

public interface ThermoelectricSourceSchema {
    class ThermoelectricSourceRecipeJS extends IERecipeJS {
        public RecipeJS kelvin(int temp) {
            return setValue(TEMPERATURE, temp);
        }

        public RecipeJS celsius(int temp) {
            return kelvin(ThermoelectricSourceBuilder.TemperatureScale.CELSIUS.toKelvin(temp));
        }

        public RecipeJS fahrenheit(int temp) {
            return kelvin(ThermoelectricSourceBuilder.TemperatureScale.FAHRENHEIT.toKelvin(temp));
        }
    }

    RecipeKey<IERecipes.HeatSource> HEAT_SOURCE = IERecipes.HEAT_SOURCE_COMPONENT.key("heat_source");
    RecipeKey<Integer> TEMPERATURE = NumberComponent.INT.key(ThermoelectricSourceBuilder.TEMPERATURE_KEY).alwaysWrite();

    RecipeSchema SCHEMA = new RecipeSchema(ThermoelectricSourceRecipeJS.class, ThermoelectricSourceRecipeJS::new, HEAT_SOURCE, TEMPERATURE);
}
