package com.willm.CoreMOD.Alloying.Crucibles;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerBucketFillEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.willm.CoreMOD.Alloying.Molten.ForgeRegistry;

public class CrucibleEvents implements Listener {

	@EventHandler
	public void AddMoltenMetal(PlayerInteractEvent event)
	{
		
	}
	
	@EventHandler
	public void SteelBucketEmpty(PlayerBucketEmptyEvent event)
	{
		if(event.getPlayer().getEquipment().getItemInMainHand().hasItemMeta() && event.getPlayer().getEquipment().getItemInMainHand().getItemMeta().hasLore())
		{
			if(event.getPlayer().getEquipment().getItemInMainHand().getItemMeta().getLore().get(0).contains(ForgeRegistry.STEEL_BUCKET_LORE))
			{
				event.getPlayer().getEquipment().setItemInMainHand(ForgeRegistry.steel_enforced_bucket.GetMyItemStack());
				if(event.getBucket() == Material.WATER_BUCKET)
				{
					event.getBlock().setType(Material.WATER);
				}else if(event.getBucket() == Material.LAVA_BUCKET)
				{
					event.getBlock().setType(Material.LAVA);
				}
				event.setCancelled(true);
			}
		}
	}
	
	@EventHandler
	public void SteelBucketFill(PlayerBucketFillEvent event)
	{
		if(ForgeRegistry.steel_enforced_bucket.CheckForCustomItem(event.getPlayer().getEquipment().getItemInMainHand()))
		{
			
			ItemStack is = event.getItemStack();
			ItemMeta im = is.getItemMeta();
			im.setLore(List.of(ChatColor.DARK_GRAY + ForgeRegistry.STEEL_BUCKET_LORE));
			im.setCustomModelData(ForgeRegistry.steel_enforced_bucket.getCustomModelData());
			is.setItemMeta(im);
			
			event.setItemStack(is);
		}
	}
	
	
	
}
