package com.willm.CoreMOD;

import org.bukkit.inventory.ItemStack;

public class OvenRecipe {

	public final ItemStack[] inputs;
	public final ItemStack out;
	
	public final boolean usePlate;
	
	public OvenRecipe(ItemStack out, boolean usePlate, ItemStack... in)
	{
		this.usePlate = usePlate;
		this.out = out;
		inputs = in;
	}
	
}
