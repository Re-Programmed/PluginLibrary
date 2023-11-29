package com.willm.ModAPI.Items;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
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
	
	private final CustomItemStack crop_source;
	
	public Plant(CustomItemStack drops, int drop_count, int growth_chance, CustomItemStack crop_source, CustomBlock... plant_block)
	{
		this.crop_source = crop_source;
		this.growth_chance = growth_chance;
		this.plant_block = plant_block;
		this.drops = drops;
		this.drop_count = drop_count;
	}
	
	public CustomItemStack GetCropSource()
	{
		return crop_source;
	}
	
	public void BreakPlant(BlockBreakEvent event, int i)
	{
		if(drop_count - i >= 1) {
			event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(), drops.GetAmountClone(drop_count - i));
		}
		
		Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "particle block wheat[age=7] " + event.getBlock().getLocation().getBlockX() + " " + event.getBlock().getLocation().getBlockY() + " " + event.getBlock().getLocation().getBlockZ() + " 0.4 0.4 0.4 1 20 normal");
		event.getBlock().getWorld().playSound(event.getBlock().getLocation(), Sound.BLOCK_CROP_BREAK, 1, 1);
	}
	

	public void FinishBreakPlant(BlockBreakEvent event, int i) 
	{
		event.setCancelled(true);
		event.getBlock().setType(Material.FARMLAND);
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
