package com.willm.CoreMOD.DifficultyExtension;

import org.bukkit.ChatColor;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Monster;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntitySpawnEvent;

public class DifficultyEvents implements Listener {

	public static int CURRENT_ADDED_DIFFICULTY = 0;
	public static boolean SHOW_MOB_HEALTH = true;

	@EventHandler
	public void EntitySpawnEvent(EntitySpawnEvent event)
	{
		if(CURRENT_ADDED_DIFFICULTY <= 0)
		{
			return;
		}
		
		if(event.getEntity() instanceof Monster)
		{
			Monster m = (Monster)event.getEntity();
			
			AttributeInstance healthAttribute = m.getAttribute(Attribute.GENERIC_MAX_HEALTH);
			healthAttribute.setBaseValue(m.getHealth() + CURRENT_ADDED_DIFFICULTY);
			
			m.setHealth(m.getHealth() + CURRENT_ADDED_DIFFICULTY);
			
			if(!SHOW_MOB_HEALTH) {return;}
			event.getEntity().setCustomName(event.getEntity().getName() + ChatColor.RED + " " + ((int)m.getHealth()) + "♡");
		}
	}
	
	@EventHandler
	public void EntityDamageEventUpdate(EntityDamageEvent event)
	{
		if(!SHOW_MOB_HEALTH) {return;}
		if(event.getEntity() instanceof Monster)
		{
			Monster m = (Monster)event.getEntity();
			
			if(m.getHealth() - event.getDamage() < 0)
			{
				return;
			}
			
			event.getEntity().setCustomName(event.getEntity().getName().substring(0, event.getEntity().getType().toString().length()) + ChatColor.RED + " " + ((int)m.getHealth() - (int)event.getDamage()) + "♡");
		}
	}
	
}
