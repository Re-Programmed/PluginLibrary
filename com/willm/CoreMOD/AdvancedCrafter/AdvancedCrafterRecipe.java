package com.willm.CoreMOD.AdvancedCrafter;

import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class AdvancedCrafterRecipe {
	public AdvancedCrafterRecipeInput[] inputs;
	
	private final ItemStack result;
	
	public AdvancedCrafterRecipe(ItemStack result, AdvancedCrafterRecipeInput... in)
	{
		this.result = result;
		inputs = in;
	}
	
	public ItemStack GetResult()
	{
		return result;
	}
	
	public boolean CheckRecipe(Inventory inv)
	{
		int i = 0;
		int slot = 2;
		
		while(i < 25)
		{
			if(inv.getItem(slot) == null) {break;}
			
			if(inv.getItem(slot).getType() == Material.RED_STAINED_GLASS_PANE)
			{
				slot++;
				continue;
			}
			
			if(!inputs[i].CheckRecipe(inv.getItem(slot)))
			{
				break;
			}
			
			slot++;
			i++;
		}
		
		return i == 25;
	}

}
