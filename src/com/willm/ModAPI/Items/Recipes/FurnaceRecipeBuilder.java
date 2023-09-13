package com.willm.ModAPI.Items.Recipes;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice.ExactChoice;

public class FurnaceRecipeBuilder {

	final FurnaceRecipe fr;
	
	public FurnaceRecipeBuilder(FurnaceRecipe fr)
	{
		this.fr = fr;
	}
	
	public static FurnaceRecipeBuilder createFurncaceRecipe(String key, ItemStack result, Material input, float xp, int cooktime)
	{
		FurnaceRecipe fr = new FurnaceRecipe(NamespacedKey.fromString(key, com.willm.CoreMOD.Main.INSTANCE), result, input, xp, cooktime);
		Bukkit.getServer().addRecipe(fr);
		return new FurnaceRecipeBuilder(fr);
	}

	public static FurnaceRecipeBuilder createFurncaceRecipe(String key, ItemStack result, ExactChoice input, float xp, int cooktime) {
		FurnaceRecipe fr = new FurnaceRecipe(NamespacedKey.fromString(key, com.willm.CoreMOD.Main.INSTANCE), result, input, xp, cooktime);
		Bukkit.getServer().addRecipe(fr);
		return new FurnaceRecipeBuilder(fr);
	} 
}
