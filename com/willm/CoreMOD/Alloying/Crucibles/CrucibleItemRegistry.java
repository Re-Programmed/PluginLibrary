package com.willm.CoreMOD.Alloying.Crucibles;

import org.bukkit.Material;

import com.willm.CoreMOD.Alloying.AlloyMaterial;
import com.willm.ModAPI.Items.BlockCreator;
import com.willm.ModAPI.Items.CustomItemStack;
import com.willm.ModAPI.Items.ItemCreator;

public class CrucibleItemRegistry {

	public static CustomItemStack basic_crucible;
	
	public static void RegisterItems()
	{
		basic_crucible = ItemCreator.RegisterNewItem(new CustomItemStack("Basic Crucible", Material.WARPED_TRAPDOOR, 15510));
		BlockCreator.RegisterNewBlock(basic_crucible, new BasicCrucible(basic_crucible));
		
		AlloyMaterial.Register();
	}
	
}
