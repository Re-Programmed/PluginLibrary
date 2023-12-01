package com.willm.CoreMOD.AdvancedCrafter;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import com.willm.ModAPI.Items.CustomItemStack;

public class AdvancedCrafterRecipeInput {

	final Material inMat;
	final CustomItemStack inStack;
	
	public ItemStack GetMyCheck()
	{
		if(inMat != Material.AIR)
		{
			return new ItemStack(inMat);
		}
		
		return inStack.GetAmountClone(1);
	}
	
	public boolean CheckRecipe(ItemStack is)
	{
		if(inMat != Material.AIR)
		{
			return is.getType() == inMat;
		}
		
		if(inStack != null)
		{
			return inStack.CheckForCustomItem(is);
		}
		
		System.out.println("Advanced Recipe contains null item");
		return false;
	}
	
	public AdvancedCrafterRecipeInput(Material in)
	{
		inMat = in;
		inStack = null;
	}
	
	public AdvancedCrafterRecipeInput(CustomItemStack in)
	{
		inMat = Material.AIR;
		inStack = in;
	}
	
}
