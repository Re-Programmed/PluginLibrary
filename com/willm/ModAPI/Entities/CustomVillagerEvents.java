package com.willm.ModAPI.Entities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Villager;
import org.bukkit.entity.Villager.Profession;
import org.bukkit.entity.memory.MemoryKey;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.MerchantRecipe;

import com.willm.ModAPI.Main;

public class CustomVillagerEvents implements Listener {
	
	HashMap<Villager, CustomVillager> customVillagers = new HashMap<Villager, CustomVillager>(); 
	ArrayList<Location> villagerWorkstations = new ArrayList<Location>();
	
	public CustomVillagerEvents() {
		

		Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(Main.PLUGIN, new Runnable() {
			
			public void run() { 
				int w = 0;
				for(Entry<Villager, CustomVillager> entry : customVillagers.entrySet())
				{
					if(villagerWorkstations.get(w) == null)
					{
						continue;
					}
					
					entry.getKey().setMemory(MemoryKey.LAST_WORKED_AT_POI, (long)0);
					List<MerchantRecipe> mr = entry.getKey().getRecipes();
					int i = 0;
					
					for(MerchantRecipe m : mr)
					{
						m.setUses(0);
						entry.getKey().setRecipe(i, m);
						i++;
					}
					
					w++;
				}
			}
		}, 8 * 60 * 20, 8 * 60 * 20);
		
	}
	
	@EventHandler
	public void WorkstationBreakEvent(BlockBreakEvent event)
	{
		int w = 0;
		for(Entry<Villager, CustomVillager> ce : customVillagers.entrySet())
		{
			System.out.println("VILLAGER NO");

			if(villagerWorkstations.get(w).distance(event.getBlock().getLocation()) < 0.8)
			{
				villagerWorkstations.set(w, null);
				
				
				if(ce.getKey().getVillagerExperience() < 1 && ce.getKey().getVillagerLevel() <= 1)
				{
					ce.getKey().setProfession(Profession.NONE);
					ce.getKey().setCustomName(null);
					villagerWorkstations.remove(w);
					return;
				}
			}
			w++;
		}
	}
	
	@EventHandler
	public void WorkstationPlaceEvent(BlockPlaceEvent event)
	{
		for(CustomEntity ce : Main.CustomEntityRegistry)
		{
			if(ce instanceof CustomVillager)
			{
				CustomVillager villager = (CustomVillager)ce;

				if(villager.GetWorkstation().CheckForCustomBlock(event.getBlock()))
				{
					for(Entity e : event.getBlock().getWorld().getNearbyEntities(event.getBlock().getLocation(), 10f, 10f, 10f))
					{
						if(e instanceof Villager)
						{
							Villager v = (Villager)e;
							if(v.getProfession() == Profession.NONE)
							{
								event.getBlock().getWorld().spawnParticle(Particle.VILLAGER_HAPPY, event.getBlock().getLocation(), 100, 0.8, 0.8, 0.8);
								System.out.println("SET POTENTIAL SITE");
								
								Villager newV = ((Villager)villager.Summon(v.getLocation()));
								newV.setMemory(MemoryKey.JOB_SITE, new Location(v.getWorld(), 0, 0, 0));
								villagerWorkstations.add(event.getBlock().getLocation());
								newV.setMemory(MemoryKey.HOME, null);
								v.remove();
								
								customVillagers.put(newV, villager);
								return;
							}
						}
					}
				}
			}
		}
	}

}
