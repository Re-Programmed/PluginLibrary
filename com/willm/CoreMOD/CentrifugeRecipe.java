package com.willm.CoreMOD;

import org.bukkit.inventory.ItemStack;

public abstract class CentrifugeRecipe {

	public abstract boolean CheckForRecipe(ItemStack item, int level);
	
	public abstract String GetLore(int level);
	
	public abstract ItemStack Result();
	
	
}
