package com.willm.CoreMOD;

import org.bukkit.ChatColor;
import org.bukkit.Material;

import com.willm.ModAPI.Items.CustomItemStack;
import com.willm.ModAPI.Items.ItemCreator;
import com.willm.ModAPI.Items.Recipes.RecipeBuilder;

public class Weapons {

	public static CustomItemStack battle_axe;
	
	public static void RegisterItems()
	{
		battle_axe = ItemCreator.RegisterNewItem(new CustomItemStack("Battle Axe", Material.DIAMOND_AXE, 12002).AddLoreLine(ChatColor.RED + "Bleeding"));
		battle_axe.getRecipe(1, "LSL", "LSL", " S ").AddMaterial('L', RecipeBuilder.ItemStackInput(MyItems.lead_ingot)).AddMaterial('S', Material.STICK).Finalize();
	}
	
}
