package com.willm.CoreMOD;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

public class CustomItemEffectsEvents implements Listener {

	public static String BLEEDING_EFFECT = ChatColor.RED + "Bleeding";
	
	@EventHandler
	public void PlayerDamage(EntityDamageByEntityEvent event)
	{
		if(event.getDamager() instanceof LivingEntity)
		{
			LivingEntity le = (LivingEntity)event.getDamager();
			if(HasCustomItemEffect(BLEEDING_EFFECT, le.getEquipment().getItemInMainHand()))
			{
				event.setDamage(event.getDamage() + 1.5);
				
				ShowCustomParticle(event.getEntity().getLocation(), "redstone_block");
			}
		}
	}
	
	public static void ShowCustomParticle(Location location, String block)
	{
		Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "particle block " + block + " " + location.getX() + " " + location.getY() + " " + location.getZ() + " 1 1 1 1 15 normal");
	}
	
	public static boolean HasCustomItemEffect(String effect, ItemStack item)
	{
		if(item == null) {return false;}
		if(item.hasItemMeta())
		{
			if(item.getItemMeta().hasLore())
			{
				if(item.getItemMeta().getLore().contains(effect))
				{
					return true;
				}
			}
		}
		return false;
	}
}
