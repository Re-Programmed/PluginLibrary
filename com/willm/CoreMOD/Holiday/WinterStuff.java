package com.willm.CoreMOD.Holiday;

import org.bukkit.Material;

import com.willm.ModAPI.Blocks.BlockDirectionData;
import com.willm.ModAPI.Blocks.CustomStates.CustomBaseMaterialRetainingBlock;
import com.willm.ModAPI.Items.BlockCreator;
import com.willm.ModAPI.Items.CustomItemStack;
import com.willm.ModAPI.Items.ItemCreator;
import com.willm.ModAPI.Items.Recipes.RecipeBuilder;

public class WinterStuff {
	public static CustomItemStack christmas_tree;
	
	public static void RegisterWinterStuff()
	{
		christmas_tree = ItemCreator.RegisterNewItem(new CustomItemStack("Christmas Tree", Material.WARPED_TRAPDOOR, 100001));
		
		BlockCreator.RegisterNewBlock(christmas_tree, new CustomBaseMaterialRetainingBlock(christmas_tree)).SetDirectional(BlockDirectionData.PLAYER_RELATIVE);
		
		christmas_tree.getRecipe(1, "glg", "lSl", " p ").AddMaterial('g', Material.GLOWSTONE_DUST).AddMaterial('l', Material.STICK)
		.AddMaterial('S', RecipeBuilder.MultiMaterialInput(Material.OAK_SAPLING, Material.BIRCH_SAPLING, Material.SPRUCE_SAPLING, Material.ACACIA_SAPLING, Material.JUNGLE_SAPLING, Material.CHERRY_SAPLING, Material.DARK_OAK_SAPLING))
		.AddMaterial('p', Material.FLOWER_POT).Finalize();
	}
}
