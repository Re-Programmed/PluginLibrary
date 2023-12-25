package com.willm.CoreMOD;

import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;

import com.willm.ModAPI.Items.BlockCreator;
import com.willm.ModAPI.Items.CustomItemStack;
import com.willm.ModAPI.Items.ItemCreator;
import com.willm.ModAPI.Items.MusicDisc.MusicDisc;
import com.willm.ModAPI.Items.Recipes.RecipeBuilder;

public class SillyItems {

	public static void RegisterSillyItems()
	{
		CustomItemStack ingyatt = ItemCreator.RegisterNewItem(new CustomItemStack("Ingyatt", Material.GREEN_CONCRETE, 500231));
		BlockCreator.RegisterNewBlock(ingyatt).SetMineAs(Material.HAY_BLOCK).SetRequiredTool("HOE").SetConstBlock(false);
		
		ingyatt.getRecipe(1, "MM", "NN").AddMaterial('M', Material.MILK_BUCKET).AddMaterial('N', Material.NETHERRACK).Finalize();
		
		MusicDisc md = new MusicDisc(Material.STONE_SWORD, 15002, "William McGlumphy - Game Time", "core_mod.music.game_time", 2);
		md.AddFlags(ItemFlag.HIDE_ATTRIBUTES);
		ItemCreator.RegisterNewItem(md);
		
		md.getRecipe(1, " G ", "GLG", " G ", "craft_md_disc").AddMaterial('G', Material.GOLD_INGOT).AddMaterial('L', RecipeBuilder.ItemStackInput(MyItems.lead_ingot)).Finalize();
		
		MusicDisc gyatt_md = new MusicDisc(Material.STONE_SWORD, 15003, "Sticking Out Your Gyatt For The Rizzler", "core_mod.pk.gyatt", 0.5f);
		gyatt_md.AddFlags(ItemFlag.HIDE_ATTRIBUTES);
		ItemCreator.RegisterNewItem(gyatt_md);
		
		gyatt_md.getRecipe(1, " G ", "GLG", " G ", "craft_gyatt_md_disc").AddMaterial('G', Material.IRON_INGOT).AddMaterial('L', RecipeBuilder.ItemStackInput(ingyatt)).Finalize();
		
		MusicDisc skibidi_md = new MusicDisc(Material.STONE_SWORD, 15004, "Skibidi Toilet", "core_mod.pk.skibidi", 0.1f);
		skibidi_md.AddFlags(ItemFlag.HIDE_ATTRIBUTES);
		ItemCreator.RegisterNewItem(skibidi_md);
		
		skibidi_md.getRecipe(1, " G ", "GLG", " G ", "craft_skibidi_md_disc").AddMaterial('G', Material.DIAMOND).AddMaterial('L', RecipeBuilder.ItemStackInput(gyatt_md)).Finalize();
		
		MusicDisc chopin_prelude_20 = new MusicDisc(Material.STONE_SWORD, 15005, "Chopin Prelude No20 In C Minor", "core_mod.music.chopin_20", 0.1f);
		chopin_prelude_20.AddFlags(ItemFlag.HIDE_ATTRIBUTES);
		ItemCreator.RegisterNewItem(chopin_prelude_20);
		
		chopin_prelude_20.getRecipe(1, " G ", "GLG", " G ", "craft_chopin20_md_disc").AddMaterial('G', Material.IRON_NUGGET).AddMaterial('L', RecipeBuilder.ItemStackInput(MyItems.tungsten_ingot)).Finalize();
		
	}
	
}
