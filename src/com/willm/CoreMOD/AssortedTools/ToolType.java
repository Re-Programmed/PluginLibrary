package com.willm.CoreMOD.AssortedTools;

import org.bukkit.Material;

public enum ToolType {
	Pickaxe(org.bukkit.Material.DIAMOND_PICKAXE),
	Sword(org.bukkit.Material.DIAMOND_SWORD),
	Shovel(org.bukkit.Material.DIAMOND_SHOVEL),
	Hoe(org.bukkit.Material.DIAMOND_HOE),
	Axe(org.bukkit.Material.DIAMOND_AXE),
	Shield(org.bukkit.Material.SHIELD);
	
	public final Material Material;
	
	ToolType(Material material)
	{
		Material = material;
	}
}
