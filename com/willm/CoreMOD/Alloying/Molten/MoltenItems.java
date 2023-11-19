package com.willm.CoreMOD.Alloying.Molten;

import java.util.HashMap;

import org.bukkit.Material;

import com.willm.CoreMOD.Alloying.AlloyMaterial;
import com.willm.ModAPI.Items.CustomItemStack;
import com.willm.ModAPI.Items.ItemCreator;

public class MoltenItems {

	public static HashMap<AlloyMaterial, CustomItemStack> MoltenVariants = new HashMap<AlloyMaterial, CustomItemStack>();
	
	public static void RegisterMolten()
	{
		CustomItemStack molten_iron = ItemCreator.RegisterNewItem(new CustomItemStack("Molten Iron", Material.IRON_INGOT, 12001));
		MoltenVariants.put(AlloyMaterial.IRON, molten_iron);
	}
	
}
