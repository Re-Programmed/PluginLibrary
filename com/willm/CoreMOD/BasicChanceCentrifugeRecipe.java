package com.willm.CoreMOD;

import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class BasicChanceCentrifugeRecipe extends CentrifugeRecipe {

	final Material in;
	final ItemStack out;
	final float chance1000;
	final int level, maxLevel; //min inclusive, max exclusive
	
	static final Random BasicRandom = new Random();
	
	
	public BasicChanceCentrifugeRecipe(Material in, ItemStack out, float chance1000, int level, int maxLevel) {
		this.in = in;
		this.out = out;
		this.level = level;
		this.maxLevel = maxLevel;
		
		this.chance1000 = chance1000;
	}
	
	@Override
	public boolean CheckForRecipe(ItemStack item, int level) {
		return ContainsRecipe(item, level) && BasicRandom.nextInt(1001) < chance1000;
	}

	@Override
	public ItemStack Result() {
		return out;
	}

	@Override
	public String GetLore(int level) {
		if(level >= this.level && level < this.maxLevel)
		{
			return ChatColor.GRAY + in.toString().replace("_", " ") + " -> " + ChatColor.GRAY + ((out.hasItemMeta() && out.getItemMeta().hasDisplayName()) ? out.getItemMeta().getDisplayName() : out.getType().toString().replace("_", " ")) + " (" + (chance1000/10) + "%)";
		}
		
		return null;
	}

	@Override
	public boolean ContainsRecipe(ItemStack item, int level) {
		if(level >= this.level && level < this.maxLevel)
		{
			if(item.getType() == in)
			{
				if(item.hasItemMeta())
				{
					if(item.getItemMeta().hasCustomModelData())
					{
						return false;
					}
				}
				return true;
			}
			
		}
		
		return false;
	}

}
