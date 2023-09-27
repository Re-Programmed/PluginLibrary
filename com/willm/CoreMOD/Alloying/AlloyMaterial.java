package com.willm.CoreMOD.Alloying;

import org.bukkit.Material;

import com.willm.CoreMOD.MyItems;

public enum AlloyMaterial {
	WOOD(1, "Wooden", new MaterialCustomItemCompare(Material.OAK_PLANKS)),
	STONE(2, "Stone", new MaterialCustomItemCompare(Material.COBBLESTONE)),
	IRON(3, "Iron", new MaterialCustomItemCompare(Material.IRON_INGOT)),
	GOLD(4, "Golden", new MaterialCustomItemCompare(Material.GOLD_INGOT)),
	DIAMOND(5, "Diamond", new MaterialCustomItemCompare(Material.DIAMOND)),
	NETHERITE(6, "Netherite", new MaterialCustomItemCompare(Material.NETHERITE_INGOT)),
	TUNGSTEN(7, "Tungsten", new MaterialCustomItemCompare()),
	PLATINUM(8, "Platinum", new MaterialCustomItemCompare()),
	LEAD(9, "Lead", new MaterialCustomItemCompare()),
	HIGH_CARBON_STEEL(10, "High Carbon Steel", new MaterialCustomItemCompare());
	
	public final int Index;
	public final String NameModifer;
	
	public MaterialCustomItemCompare Compare;
	
	public static void Register()
	{
		TUNGSTEN.Compare = new MaterialCustomItemCompare(MyItems.tungsten_ingot);
		PLATINUM.Compare = new MaterialCustomItemCompare(MyItems.platinum_ingot);
		LEAD.Compare = new MaterialCustomItemCompare(MyItems.lead_ingot);
		HIGH_CARBON_STEEL.Compare = new MaterialCustomItemCompare(MyItems.high_carbon_steel);
	}
	
	AlloyMaterial(int index, String name, MaterialCustomItemCompare compare)
	{
		Compare = compare;
		Index = index;
		this.NameModifer = name;
	}
}
