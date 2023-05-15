package com.willm.ModAPI.Items.Recipes;

import org.bukkit.Material;

public enum RecipeTemplates {

	//ALL TEMPLATES USE THIS KEY:
	// I = Ingot
	
	Pickaxe(new RecipeTemplate("III", " s ", " s ", new TemplateMaterial('s', Material.STICK))),
	Axe(new RecipeTemplate("II ", "Is ", " s ", new TemplateMaterial('s', Material.STICK))),
	Hoe(new RecipeTemplate("II ", " s ", " s ", new TemplateMaterial('s', Material.STICK))),
	Shovel(new RecipeTemplate(" I ", " s ", " s ", new TemplateMaterial('s', Material.STICK))),
	Sword(new RecipeTemplate(" I ", " I ", " s ", new TemplateMaterial('s', Material.STICK))),
	
	Helmet(new RecipeTemplate("III", "I I", "   ")),
	Chestplate(new RecipeTemplate("I I", "III", "III")),
	Leggings(new RecipeTemplate("III", "I I", "I I")),
	Boots(new RecipeTemplate("   ", "I I", "I I")),
	
	Ingot_Block(new RecipeTemplate("III", "III", "III")),
	Ingot_Middle(new RecipeTemplate("   ", " I ", "   ")),
	
	Door(new RecipeTemplate("II ", "II ", "II "));

	
	public RecipeTemplate MyTemplate;
	
	RecipeTemplates(RecipeTemplate template)
	{
		MyTemplate = template;
	}
	
}
