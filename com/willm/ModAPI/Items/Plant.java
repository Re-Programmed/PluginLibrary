package com.willm.ModAPI.Items;

import java.util.Random;

import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.event.block.BlockBreakEvent;

import com.willm.ModAPI.Blocks.CustomBlock;

public class Plant {

	//Register blocks in order from fully grown to sprout.
	private final CustomBlock[] plant_block;
	public CustomBlock[] GetCustomBlock() {return plant_block;}
	
	private final CustomItemStack drops;
	
	private final int drop_count;
	
	private final int growth_chance; //Out of 100001
	
	
	public Plant(CustomItemStack drops, int drop_count, int growth_chance, CustomBlock... plant_block)
	{
		this.growth_chance = growth_chance;
		this.plant_block = plant_block;
		this.drops = drops;
		this.drop_count = drop_count;
	}
	
	public void BreakPlant(BlockBreakEvent event, int i)
	{
		event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(), drops.GetAmountClone(drop_count - i));
	}

	public void CheckStand(ArmorStand as, Random random)
	{
		Location l = as.getLocation().getBlock().getLocation();
		if(random.nextInt(100001) < growth_chance)
		{
			GrowPlant(l);
		}
	}

	public void GrowPlant(Location l)
	{
		int i = 0;
		for(CustomBlock cb : plant_block) 
		{
			
			if(cb.CheckForCustomBlock(l.getBlock()))
			{
				
				if(i != 0)
				{
					
					cb.Remove(l);
					
					plant_block[i - 1].Place(l);
					return;
				}
			}
			i++;
		}
	}
	
}
