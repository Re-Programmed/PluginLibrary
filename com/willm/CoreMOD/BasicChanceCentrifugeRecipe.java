package com.willm.CoreMOD;

import java.util.Random;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class BasicChanceCentrifugeRecipe extends CentrifugeRecipe {

	final Material in;
	final ItemStack out;
	final float chance1000;
	final int level, maxLevel;
	
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
				return BasicRandom.nextInt(1001) < chance1000;
			}
			
		}
		
		return false;
	}

	@Override
	public ItemStack Result() {
		return out;
	}

}
