package dev.latvian.mods.kubejs.immersiveengineering.recipe;

import blusunrize.immersiveengineering.api.crafting.builders.WindmillBiomeBuilder;
import dev.latvian.mods.kubejs.recipe.RecipeKey;
import dev.latvian.mods.kubejs.recipe.component.NumberComponent;
import dev.latvian.mods.kubejs.recipe.schema.RecipeSchema;

public interface WindmillBiomeSchema {
    RecipeKey<IERecipes.BiomeFilter> BIOME = IERecipes.BIOME_FILTER_COMPONENT.key("biome_filter");
    RecipeKey<Float> MODIFIER = NumberComponent.FLOAT.key(WindmillBiomeBuilder.MODIFIER_KEY).optional(1F).alwaysWrite();

    RecipeSchema SCHEMA = new RecipeSchema(IERecipeJS.class, IERecipeJS::new, BIOME, MODIFIER);
}