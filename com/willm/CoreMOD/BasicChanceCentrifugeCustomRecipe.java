package com.willm.CoreMOD;

import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;

import com.willm.ModAPI.Items.CustomItemStack;

public class BasicChanceCentrifugeCustomRecipe extends CentrifugeRecipe {
	
	final CustomItemStack in;
	final ItemStack out;
	final float chance1000;
	final int level, maxLevel;
	
	static final Random BasicRandom = new Random();
	
	
	public BasicChanceCentrifugeCustomRecipe(CustomItemStack in, ItemStack out, float chance1000, int level, int maxLevel) {
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
			return ChatColor.GRAY + in.getName() + " -> " + ChatColor.GRAY + ((out.hasItemMeta() && out.getItemMeta().hasDisplayName()) ? out.getItemMeta().getDisplayName().replace(ChatColor.WHITE + "", "") : out.getType().toString().replace("_", " ")) + " (" + (chance1000/10) + "%)";
		}
		
		return null;
	}



	@Override
	public boolean ContainsRecipe(ItemStack item, int level) {
		if(level >= this.level && level < this.maxLevel)
		{
			if(in.CheckForCustomItem(item))
			{
				return BasicRandom.nextInt(1001) < chance1000;
			}
			
		}
		
		return false;
	}
}
