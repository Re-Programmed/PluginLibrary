package com.willm.ModAPI.Terrain;

import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.World.Environment;
import org.bukkit.block.Biome;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

import com.willm.ModAPI.Main;

public class OreEvents implements Listener {
	
	public static ArrayList<Location> preventBlocks = new ArrayList<Location>();
	
	@EventHandler
	public void PreventOreDupe(BlockPlaceEvent event)
	{
		for(Ore ore : Main.Populator.getOres())
		{
			if(event.getBlock().getType() == ore.drop.GetMyItemStack().getType())
			{
				if(event.getBlock().getBiome() == Biome.THE_END)
				{
					preventBlocks.add(event.getBlock().getLocation());
				}
			}
		}
	}
	
	@EventHandler
	public void MineBlock(BlockBreakEvent event)
	{
		if(event.getBlock().getWorld().getEnvironment() != Environment.THE_END && !preventBlocks.contains(event.getBlock().getLocation()))
		{
			if(event.getBlock().getBiome() == Biome.THE_END)
			{
				for(Ore ore : Main.Populator.getOres())
				{
					if(event.getBlock().getType() == ore.drop.GetMyItemStack().getType())
					{
						event.setDropItems(false);
						event.setExpToDrop(ore.xp);
						ore.drop.DropNaturally(event.getBlock().getLocation());
					}
				}
			}
		}
	}
}
