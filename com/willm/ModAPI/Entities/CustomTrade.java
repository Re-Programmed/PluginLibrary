package com.willm.ModAPI.Entities;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.MerchantRecipe;

public class CustomTrade extends MerchantRecipe {

	final int RecipeLevel;
	
	public CustomTrade(ItemStack result, int maxUses, boolean experienceReward, int villagerExp, int recipeLevel) {
		super(result, 0, maxUses, experienceReward, villagerExp, 0, 0, 0);
		RecipeLevel = recipeLevel;
	}

	public CustomTrade AddIngrident(ItemStack i)
	{
		addIngredient(i);
		return this;
	}
	
	
}
