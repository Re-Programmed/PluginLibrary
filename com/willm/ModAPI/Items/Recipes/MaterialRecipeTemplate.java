package com.willm.ModAPI.Items.Recipes;

import org.bukkit.Material;

public enum MaterialRecipeTemplate {

	PLANKS(Material.OAK_PLANKS, Material.BIRCH_PLANKS, Material.ACACIA_PLANKS, Material.JUNGLE_PLANKS, Material.DARK_OAK_PLANKS, Material.SPRUCE_PLANKS, Material.CRIMSON_PLANKS, Material.WARPED_PLANKS);
	
	public final Material[] mats;
	
	private MaterialRecipeTemplate(Material... mats) {
		this.mats = mats;
	}
}
