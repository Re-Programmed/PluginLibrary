package com.willm.CoreMOD;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.willm.ModAPI.CustomDeathMessages;

public class MilkFeatures implements Listener {
	
	
	
	@EventHandler
	public void MilkPlayer(PlayerInteractEntityEvent event)
	{
		ItemStack mh = event.getPlayer().getEquipment().getItemInMainHand();
		if(mh != null)
		{
			if(mh.getType() == Material.BUCKET)
			{
				if(event.getRightClicked() instanceof LivingEntity)
				{
					ItemStack CustomMilk = new ItemStack(Material.MILK_BUCKET, 1);
					ItemMeta im = CustomMilk.getItemMeta();
					
					im.setDisplayName(event.getRightClicked().getName() + "'s Milk");
					im.addEnchant(Enchantment.LUCK, 1, true);
					im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
					
					CustomMilk.setItemMeta(im);
					
					event.getPlayer().getWorld().dropItem(event.getPlayer().getLocation(), CustomMilk).setPickupDelay(0);
					
					if(mh.getAmount() > 1)
					{
						mh.setAmount(mh.getAmount() - 1);
						event.getPlayer().getEquipment().setItemInMainHand(mh);
					}else {
						event.getPlayer().getEquipment().setItemInMainHand(null);
					}
										
					((LivingEntity)event.getRightClicked()).damage(3);
					
					if(((LivingEntity)event.getRightClicked()) instanceof Player)
					{
						if(((LivingEntity)event.getRightClicked()).getHealth() < 4)
						{
							CustomDeathMessages.SetCustomDeathMessage(event.getRightClicked().getName(), event.getRightClicked().getName() + " was milked to death by " + event.getPlayer().getName());
							((LivingEntity)event.getRightClicked()).damage(1000);
						}
					}
					
					event.setCancelled(true);
				}
			}
		}
	}
	
}
