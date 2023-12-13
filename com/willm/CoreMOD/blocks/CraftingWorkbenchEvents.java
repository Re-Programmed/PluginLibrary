package com.willm.CoreMOD.blocks;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.block.DoubleChest;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class CraftingWorkbenchEvents implements Listener {

	public void RemoveFromSystem(ItemStack clickedItem, Player p, int amount)
	{
		if(clickedItem.hasItemMeta() && clickedItem.getItemMeta().hasLore())
		{
			String checkStr = clickedItem.getItemMeta().getLore().get(clickedItem.getItemMeta().getLore().size() - 1);
			
			int x = Integer.parseInt(checkStr.replace(ChatColor.GREEN + "", "").split(", ")[0]);
			int y = Integer.parseInt(checkStr.replace(ChatColor.GREEN + "", "").split(", ")[1]);
			int z = Integer.parseInt(checkStr.replace(ChatColor.GREEN + "", "").split(", ")[2]);

			Location chestLocation = new Location(p.getWorld(), x, y, z);
			
			if(chestLocation.getBlock().getType() == Material.CHEST)
			{
				Chest c = (Chest)chestLocation.getBlock().getState();
				
				Inventory check;
				
				if(c.getInventory().getHolder() instanceof DoubleChest)
				{
					DoubleChest dc = (DoubleChest)c.getInventory().getHolder();
					
					check = dc.getInventory();
					
				}else {
					check = c.getBlockInventory();
				}
				
				if(clickedItem.getItemMeta().hasCustomModelData())
				{
					int amntLeft = amount;
					
					if(amntLeft == -1)
					{
						return;
					}
					
					for(ItemStack is : check)
					{
						if(is != null && is.getType() == clickedItem.getType() && is.hasItemMeta() && is.getItemMeta().hasCustomModelData() && is.getItemMeta().getCustomModelData() == clickedItem.getItemMeta().getCustomModelData())
						{
							if(is.getAmount() > amntLeft)
							{
								is.setAmount(is.getAmount() - amntLeft);
								amntLeft = 0;
								break;
							}else if(is.getAmount() == amntLeft) {
								check.removeItem(is);
								amntLeft = 0;
								break;
							}else {
								amntLeft -= is.getAmount();
								check.removeItem(is);
							}
						}
					}
					
					if(amntLeft <= 0)
					{
						//Success
						List<String> lore = clickedItem.getItemMeta().getLore();
						lore.remove(lore.size() - 1);
						ItemMeta im = clickedItem.getItemMeta();
						im.setLore(lore);
						clickedItem.setItemMeta(im);
						return;
					}
				}else {
					int amntLeft = amount;
					
					if(amntLeft == -1)
					{
						return;
					}
					
					for(ItemStack is : check)
					{
						if(is != null && is.getType() == clickedItem.getType() && ((is.hasItemMeta() && !is.getItemMeta().hasCustomModelData()) || !is.hasItemMeta()))
						{
							if(is.getAmount() > amntLeft)
							{
								is.setAmount(is.getAmount() - amntLeft);
								amntLeft = 0;
								break;
							}else if(is.getAmount() == amntLeft) {
								check.removeItem(is);
								amntLeft = 0;
								break;
							}else {
								amntLeft -= is.getAmount();
								check.removeItem(is);
							}
						}
					}
					
					if(amntLeft <= 0)
					{
						//Success
						List<String> lore = clickedItem.getItemMeta().getLore();
						lore.remove(lore.size() - 1);
						ItemMeta im = clickedItem.getItemMeta();
						im.setLore(lore);
						clickedItem.setItemMeta(im);
						return;
					}
				}
			}
		}
	}
	
	private static int[] craftCheckSlots = new int[]{
			14, 15, 16,
			23, 24, 25,
			32, 33, 34
	};
	
	@EventHandler
	public void ClickInventory(InventoryClickEvent event)
	{
		if(event.getClickedInventory() == null)
		{
			return;
		}
		
		if(event.getWhoClicked().getOpenInventory().getTopInventory() == null)
		{
			return;
		}
		
		if(!(event.getWhoClicked() instanceof Player)) {return;}
		
		if(event.getClickedInventory() == event.getWhoClicked().getOpenInventory().getTopInventory())
		{
			//Clicked on the search menu.
			if(event.getClickedInventory().getSize() == 54)
			{
				ItemStack checkItem = event.getClickedInventory().getItem(53);
				
				if(checkItem == null) 
				{
					return;
				}
				
				if(WorkbenchItemRegistry.crafting_workbench.CheckForCustomItem(checkItem))
				{
					event.setCancelled(true);
				
					if(event.getSlot() == 49)
					{
						if(event.getClickedInventory().getItem(49).getType() == Material.HOPPER)
						{
							int x = Integer.parseInt(event.getClickedInventory().getItem(53).getItemMeta().getLore().get(0).split(", ")[0]);
							int y = Integer.parseInt(event.getClickedInventory().getItem(53).getItemMeta().getLore().get(0).split(", ")[1]);
							int z = Integer.parseInt(event.getClickedInventory().getItem(53).getItemMeta().getLore().get(0).split(", ")[2]);

							Location loc = new Location(event.getWhoClicked().getWorld(), x, y, z);
							
							ItemStack cursor = event.getWhoClicked().getItemOnCursor();
							String query = (cursor.hasItemMeta() && cursor.getItemMeta().hasDisplayName()) ? cursor.getItemMeta().getDisplayName().replace(" ", "").replace(ChatColor.WHITE + "", "").toLowerCase() : cursor.getType().toString().toLowerCase().replace("_", "");
									
							StorageKeyCommand.CreateCraftingSearchResultInventory((Player)event.getWhoClicked(), loc.getBlock(), query);
							
							return;
						}
					}
					
					if(event.getSlot() < 45)
					{
						ItemStack is = event.getClickedInventory().getItem(event.getSlot());
						if(is != null)
						{
							int x = Integer.parseInt(event.getClickedInventory().getItem(53).getItemMeta().getLore().get(0).split(", ")[0]);
							int y = Integer.parseInt(event.getClickedInventory().getItem(53).getItemMeta().getLore().get(0).split(", ")[1]);
							int z = Integer.parseInt(event.getClickedInventory().getItem(53).getItemMeta().getLore().get(0).split(", ")[2]);

							Location loc = new Location(event.getWhoClicked().getWorld(), x, y, z);
							
							StorageKeyCommand.AttemptRecipe(is.getItemMeta().getLore().get(0), (Player)event.getWhoClicked(), loc.getBlock());
							
							return;
						}
					}
					
				}
				//Clicked on the display menu.
			} else if(event.getClickedInventory().getSize() == 45)
			{
				ItemStack testItem = event.getClickedInventory().getItem(22);
				if(testItem != null && testItem.hasItemMeta() && testItem.getItemMeta().hasCustomModelData() && testItem.getItemMeta().getCustomModelData() == 0 && (testItem.getType() == Material.GREEN_CONCRETE || testItem.getType() == Material.RED_CONCRETE))
				{
					event.setCancelled(true);
					
					//Attempting recipe.
					if(event.getSlot() == 22)
					{
						if(testItem.getType() == Material.RED_CONCRETE)
						{
							event.getWhoClicked().sendMessage(ChatColor.RED + "Unavailable recipe.");
							return;
						}else {
							for(int i : craftCheckSlots)
							{
								ItemStack is = event.getClickedInventory().getItem(i);
								
								if(is == null) {continue;}
								
								RemoveFromSystem(is, (Player)event.getWhoClicked(), 1);
							}
							
							event.getWhoClicked().setItemOnCursor(event.getClickedInventory().getItem(13));
							
							int x = Integer.parseInt(event.getClickedInventory().getItem(22).getItemMeta().getLore().get(1).split(", ")[0]);
							int y = Integer.parseInt(event.getClickedInventory().getItem(22).getItemMeta().getLore().get(1).split(", ")[1]);
							int z = Integer.parseInt(event.getClickedInventory().getItem(22).getItemMeta().getLore().get(1).split(", ")[2]);

							Location loc = new Location(event.getWhoClicked().getWorld(), x, y, z);
							
							StorageKeyCommand.AttemptRecipe(event.getClickedInventory().getItem(22).getItemMeta().getLore().get(0), (Player) event.getWhoClicked(), loc.getBlock());
						}
						
						
						//Clicked on inventory item.
					}else {
						if(event.getSlot() < 45)
						{
							ItemStack cursor = event.getClickedInventory().getItem(event.getSlot());
							if(cursor == null) {return;}
							
							int x = Integer.parseInt(event.getClickedInventory().getItem(22).getItemMeta().getLore().get(1).split(", ")[0]);
							int y = Integer.parseInt(event.getClickedInventory().getItem(22).getItemMeta().getLore().get(1).split(", ")[1]);
							int z = Integer.parseInt(event.getClickedInventory().getItem(22).getItemMeta().getLore().get(1).split(", ")[2]);

							Location loc = new Location(event.getWhoClicked().getWorld(), x, y, z);
							String query = (cursor.hasItemMeta() && cursor.getItemMeta().hasDisplayName()) ? cursor.getItemMeta().getDisplayName().replace(" ", "").replace(ChatColor.WHITE + "", "").toLowerCase() : cursor.getType().toString().toLowerCase().replace("_", "");

							StorageKeyCommand.CreateCraftingSearchResultInventory((Player)event.getWhoClicked(), loc.getBlock(), query);
							return;
						}
					}
				}
			}
			
		}
	}

}
