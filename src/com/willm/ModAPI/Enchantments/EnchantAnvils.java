package com.willm.ModAPI.Enchantments;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class EnchantAnvils implements Listener {

	@EventHandler
	public void EnchantAnvil(PrepareAnvilEvent event)
	{
		ItemStack in1 = event.getInventory().getItem(0);
		ItemStack in2 = event.getInventory().getItem(1);
		
		if(in1 != null && in2 != null)
		{
			if(in2.getType() == Material.ENCHANTED_BOOK)
			{
				ItemMeta im = in2.getItemMeta();
				
				if(im.hasLore())
				{
					CustomEnchantment e = CustomEnchantment.GetEnchantFromName(im.getLore().get(0));
					if(e != null)
					{
						if(e.CheckCompatible(in1.getType()))
						{
							ItemStack out = in1.clone();
							e.ApplyEnchant(out);
							
							event.setResult(out);
						}
					}
				}
			}
		}
	}
	
	@EventHandler
	public void ClickOutputAnvil(InventoryClickEvent event)
	{
		if(event.getSlot() == 2)
		{
			if(event.getClickedInventory().getType() == InventoryType.ANVIL)
			{
				ItemStack in1 = event.getClickedInventory().getItem(0);
				ItemStack in2 = event.getClickedInventory().getItem(1);
				
				if(in2.getType() == Material.ENCHANTED_BOOK)
				{
					ItemMeta im = in2.getItemMeta();
					
					if(im.hasLore())
					{
						CustomEnchantment e = CustomEnchantment.GetEnchantFromName(im.getLore().get(0));
						if(e != null)
						{
							if(e.CheckCompatible(in1.getType()))
							{
								event.getWhoClicked().setItemOnCursor(event.getClickedInventory().getItem(2));
								event.getClickedInventory().clear();
							}
						}
					}
				}
			}
		}
	}
	
}
