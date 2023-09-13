package com.willm.ModAPI.Items.Recipes;

import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

import com.willm.ModAPI.Items.CustomItemStack;

public class RecipeTemplate {

	final String Row1;
	final String Row2;
	final String Row3;
	
	final TemplateMaterial[] defaultMats;
	
	public TemplateMaterial[] getTempMats() {return defaultMats;}
	
	public ShapedRecipe Generate(String name, ItemStack output)
	{
		ShapedRecipe sr = new ShapedRecipe(NamespacedKey.minecraft(name), output);
		sr.shape(Row1, Row2, Row3);
		
		for(TemplateMaterial m : defaultMats)
		{
			sr.setIngredient(m.Char, m.Material);
		}
		
		return sr;
	}
	
	public ShapedRecipe Generate(String name, CustomItemStack output)
	{
		return Generate(name, output.GetMyItemStack());
	}
	
	public RecipeTemplate(String Row1, String Row2, String Row3, TemplateMaterial... mats)
	{
		this.Row1 = Row1;
		this.Row2 = Row2;
		this.Row3 = Row3;
		defaultMats = mats;
	}
}

