package com.willm.ModAPI.Entities;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Villager;
import org.bukkit.entity.Villager.Profession;
import org.bukkit.entity.memory.MemoryKey;
import org.bukkit.inventory.MerchantRecipe;

import com.willm.ModAPI.Blocks.CustomBlock;

public class CustomVillager extends CustomEntity {

	final private Profession profession;
	final private CustomBlock workstation;
	
	final private List<CustomTrade> customTrades = new ArrayList<CustomTrade>();
	
	public CustomVillager(String customProfession, Profession profession, int maxHealth, CustomBlock workstation, CustomTrade... trades) {
		super(customProfession + " Villager", EntityType.VILLAGER, maxHealth);
		
		this.workstation = workstation;
		this.profession = profession;
		
		for(CustomTrade t : trades)
		{
			customTrades.add(t);
		}
	}
	
	public void UpdateVillagerLevel(Villager v)
	{
		int level = v.getVillagerLevel();
		
		List<MerchantRecipe> recipes = new ArrayList<MerchantRecipe>();
		
		for(CustomTrade ct : customTrades)
		{
			if(ct.RecipeLevel <= level)
			{
				recipes.add(ct);
			}
		}
		
		v.setRecipes(recipes);
	}
	
	@Override
	public Entity Summon(Location location)
	{
		Villager villager = (Villager)super.Summon(location);
		
		villager.setMemory(MemoryKey.JOB_SITE, new Location(location.getWorld(), 0,0,0));
		villager.setMemory(MemoryKey.HOME, location);
		
		villager.setProfession(profession);
		
		System.out.println("SPAWNED CUSTOM VILLAGER");
		UpdateVillagerLevel(villager);

		return villager;
	}

	public final CustomBlock GetWorkstation()
	{
		return workstation;
	}
}
