package com.willm.ModAPI.Items.Recipes;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.RecipeChoice.ExactChoice;
import org.bukkit.inventory.RecipeChoice.MaterialChoice;
import org.bukkit.inventory.ShapedRecipe;

import com.willm.ModAPI.Items.CustomItemStack;

public class RecipeBuilder {

	final ShapedRecipe mySr;
	
	boolean registered = false;
	
	public RecipeBuilder(ShapedRecipe mySr)
	{
		this.mySr = mySr;
	}
	
	public RecipeBuilder AddMaterial(char key, Material mat)
	{
		if(registered) {System.out.println("[WARNING] Tried to assign a material to a recipe builder that has already been discarded!"); return this;}
		mySr.setIngredient(key, mat);
		return this;
	}
	
	public void Finalize()
	{
		Bukkit.getServer().addRecipe(mySr);
		registered = true;
	}
	
	public static RecipeChoice.ExactChoice ItemStackInput(CustomItemStack item){ return ItemStackInput(item.GetMyItemStack()); }
	
	//Allows input of item stacks for crafting.
	public static RecipeChoice.ExactChoice ItemStackInput(ItemStack item)
	{
		return new RecipeChoice.ExactChoice(item);
	}
	
	//Allows optional crafting substitutes.
	public static RecipeChoice.ExactChoice MultiItemStackInput(ItemStack... item)
	{
		return new RecipeChoice.ExactChoice(item);
	}

	public static RecipeChoice.ExactChoice MultiItemStackInput(CustomItemStack... item)
	{
		ItemStack[] is = new ItemStack[item.length];
		
		int i = 0;
		for(CustomItemStack cis : item)
		{
			is[i] = cis.GetMyItemStack();
			i++;
		}
		
		return new RecipeChoice.ExactChoice(is);
	}

	public RecipeBuilder AddMaterial(char key, ExactChoice ec) {
		if(registered) {System.out.println("[WARNING] Tried to assign a material to a recipe builder that has already been discarded!"); return this;}
		mySr.setIngredient(key, ec);
		return this;
	}
	
	public RecipeBuilder AddMaterial(char key, MaterialChoice ec) {
		if(registered) {System.out.println("[WARNING] Tried to assign a material to a recipe builder that has already been discarded!"); return this;}
		mySr.setIngredient(key, ec);
		return this;
	}

	//Multi material inputs allow optional crafting recipes.
	
	public static RecipeChoice.MaterialChoice MultiMaterialInput(Material... materials) {
		return new RecipeChoice.MaterialChoice(materials);
	}
	
	//Takes in a template for a recipe material.
	public static RecipeChoice.MaterialChoice MultiMaterialInput(MaterialRecipeTemplate... materials) {
		
		int i = 0;
		
		for(MaterialRecipeTemplate mrt : materials)
		{
			for(Material m2 : mrt.mats)
			{
				i++;
			}
		}
		
		Material[] m = new Material[i];
		
		i = 0;
		for(MaterialRecipeTemplate mrt : materials)
		{
			for(Material m2 : mrt.mats)
			{
				m[i] = m2;
				i++;
			}
		}
		
		return new RecipeChoice.MaterialChoice(m);
	}
}
