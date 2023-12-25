package com.willm.ModAPI.Entities;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;

public class CustomEntity {

	final private String name;
	final private EntityType type;
	
	int maxHealth;
	
	public CustomEntity(String name, EntityType type, int maxHealth)
	{
		this.name = name;
		this.type = type;
		this.maxHealth = maxHealth;
	}
	
	@SuppressWarnings("deprecation")
	public Entity Summon(Location location)
	{
		Entity e = location.getWorld().spawnEntity(location, type);
		e.setCustomName(name);
		e.setCustomNameVisible(false);
		
		if(e instanceof LivingEntity)
		{
			LivingEntity le = (LivingEntity)e;
			le.setMaxHealth(maxHealth);
			le.setHealth(maxHealth);
		}
		
		return e;
	}
	
	public boolean IsCustomEntity(Entity e)
	{
		return e.getCustomName().equalsIgnoreCase(this.name.toLowerCase()) && e.getType() == this.type;
	}
	
	public final String GetName()
	{
		return name;
	}
	
	public final EntityType GetType()
	{
		return type;
	}
}
