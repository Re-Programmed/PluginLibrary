package com.willm.CoreMOD;

import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;

import com.willm.ModAPI.Items.BlockCreator;
import com.willm.ModAPI.Items.CustomItemStack;
import com.willm.ModAPI.Items.ItemCreator;
import com.willm.ModAPI.Items.MusicDisc.MusicDisc;

public class SillyItems {

	public static void RegisterSillyItems()
	{
		CustomItemStack ingyatt = ItemCreator.RegisterNewItem(new CustomItemStack("Ingyatt", Material.GREEN_CONCRETE, 500231));
		BlockCreator.RegisterNewBlock(ingyatt).SetMineAs(Material.HAY_BLOCK).SetRequiredTool("HOE").SetConstBlock(false);
		
		ingyatt.getRecipe(1, "MM", "NN").AddMaterial('M', Material.MILK_BUCKET).AddMaterial('N', Material.NETHERRACK).Finalize();
		
		MusicDisc md = new MusicDisc(Material.STONE_SWORD, 15002, "William McGlumphy - Game Time", "core_mod.music.game_time", 2);
		md.AddFlags(ItemFlag.HIDE_ATTRIBUTES);
		ItemCreator.RegisterNewItem(md);
	}
	
}
