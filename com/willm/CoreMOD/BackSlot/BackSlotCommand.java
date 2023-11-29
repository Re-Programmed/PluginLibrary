package com.willm.CoreMOD.BackSlot;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;

public class BackSlotCommand implements CommandExecutor {

	public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
	{
		if(label.equalsIgnoreCase("backslot"))
		{
			if(sender instanceof Player)
			{
				Player p = (Player)sender;
				
				if(BackSlotEvents.BackSlotDisplays.containsKey(p))
				{
					Item i = p.getWorld().dropItem(p.getLocation(), BackSlotEvents.BackSlotDisplays.get(p).getEquipment().getItemInMainHand());	
					i.setOwner(p.getUniqueId());
					i.setPickupDelay(0);
					
					BackSlotEvents.BackSlotDisplays.get(p).remove();
					BackSlotEvents.BackSlotDisplays.remove(p);
					
					p.sendMessage(ChatColor.YELLOW + "Removed the item from your back slot.");
					return true;
				}
				
				
				if(p.getEquipment().getItemInMainHand() != null && p.getEquipment().getItemInMainHand().getType() != Material.AIR)
				{
					
					ArmorStand as = (ArmorStand)p.getWorld().spawnEntity(p.getLocation(), EntityType.ARMOR_STAND);
					
					as.setInvulnerable(true);
					as.setInvisible(true);
					as.setMarker(true);
					
					as.getEquipment().setItemInMainHand(p.getEquipment().getItemInMainHand());
					p.getEquipment().setItemInMainHand(null);
					
					BackSlotEvents.BackSlotDisplays.put(p, as);
					p.sendMessage(ChatColor.YELLOW + "Placed an item in your back slot.");
					return true;
				}
				
				p.sendMessage(ChatColor.RED + "The item to put in your back slot must be in your hand.");
				return false;
			}
		}
		
		return false;
	}
	
}
