package com.willm.CoreMOD.blocks;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.block.DoubleChest;
import org.bukkit.command.Command;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class StorageTerminalEvents implements Listener {

	static int CheckAction(InventoryAction action, int amount)
	{
		switch(action)
		{
		case CLONE_STACK:
			return 0;
		case COLLECT_TO_CURSOR:
			return -1;
		case DROP_ALL_CURSOR:
			return -1;
		case DROP_ALL_SLOT:
			return -1;
		case DROP_ONE_CURSOR:
			return -1;
		case DROP_ONE_SLOT:
			return -1;
		case HOTBAR_MOVE_AND_READD:
			return -1;
		case HOTBAR_SWAP:
			return -1;
		case MOVE_TO_OTHER_INVENTORY:
			return amount;
		case NOTHING:
			return -1;
		case PICKUP_ALL:
			return amount;
		case PICKUP_HALF:
			return -1;
		case PICKUP_ONE:
			return 1;
		case PICKUP_SOME:
			return -1;
		case PLACE_ALL:
			return -1;
		case PLACE_ONE:
			return -1;
		case PLACE_SOME:
			return -1;
		case SWAP_WITH_CURSOR:
			return -1;
		case UNKNOWN:
			return -1;
		default:
			return -1;
		
		}
	}
	
	@EventHandler
	public void InventoryClick(InventoryClickEvent event)
	{
		if(event.getClickedInventory() == null) {return;}
		
		
		/*
		 * CHECK FOR SHIFT CLICKS (AHHH);
		 */
		if(event.getWhoClicked().getOpenInventory() != null && event.getWhoClicked().getOpenInventory().getTopInventory() != null)
		{
			if(event.getWhoClicked().getOpenInventory().getTopInventory().getSize() == 54)
			{
				ItemStack indicator = event.getWhoClicked().getOpenInventory().getTopInventory().getItem(53);
				if(indicator != null)
				{
					if(indicator.hasItemMeta() && indicator.getItemMeta().hasDisplayName())
					{
						if(indicator.getType() == Material.CHEST && indicator.getItemMeta().getCustomModelData() == 11)
						{
							if(indicator.getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GOLD + "Storage Terminal"))
							{
								if(event.getClickedInventory() != event.getWhoClicked().getOpenInventory().getTopInventory())
								{
									if(event.getClick() == ClickType.SHIFT_LEFT || event.getClick() == ClickType.SHIFT_RIGHT)
									{
										if(event.getClickedInventory().getItem(event.getSlot()) == null)
										{
											event.setCancelled(true);
											return;
										}
										List<ItemStack> is = List.of(event.getClickedInventory().getItem(event.getSlot()));
										
										String loreLine = event.getInventory().getItem(53).getItemMeta().getLore().get(event.getWhoClicked().getOpenInventory().getTopInventory().getItem(53).getItemMeta().getLore().size() - 1);
										
										int x = Integer.parseInt(loreLine.split(", ")[0]);
										int y = Integer.parseInt(loreLine.split(", ")[1]);
										int z = Integer.parseInt(loreLine.split(", ")[2]);

										WorkbenchSystem ws = new WorkbenchSystem(new Location(event.getWhoClicked().getWorld(), x, y, z).getBlock());
										
										if(!WorkbenchItemRegistry.AddItemsToWorkbenchSystem(ws, is))
										{
											event.setCancelled(true);
										}
										
										return;
									}
								}else {
									if(event.getSlot() == 49)
									{
										if(event.getCursor() != null && event.getClickedInventory().getItem(event.getSlot()) != null && event.getClickedInventory().getItem(event.getSlot()).getType() == Material.HOPPER)
										{
											if(event.getCursor().hasItemMeta() && event.getCursor().getItemMeta().hasDisplayName())
											{												
												StorageKeyCommand.ExecuteCommand(event.getWhoClicked(), true, "sk", event.getCursor().getItemMeta().getDisplayName().toLowerCase().replace(ChatColor.WHITE + "", "").replace(" ", ""));
											}else {
												StorageKeyCommand.ExecuteCommand(event.getWhoClicked(), true, "sk", event.getCursor().getType().toString().toLowerCase().replace("_", ""));
											}
										}
										
										event.setCancelled(true);
										
										return;
									}
									
									if(event.getClick() == ClickType.NUMBER_KEY)
									{
										event.setCancelled(true);
										return;
									}
									
									if(event.getClick() == ClickType.CONTROL_DROP)
									{
										event.setCancelled(true);
										return;
									}
									
									if(event.getClick() == ClickType.DROP)
									{
										event.setCancelled(true);
										return;
									}
								}
							}
						}
					}
				}
			}
		}
		
		if(event.getClickedInventory().getSize() == 54)
		{
			ItemStack indicator = event.getClickedInventory().getItem(53);
			if(indicator != null)
			{
				if(indicator.hasItemMeta() && indicator.getItemMeta().hasDisplayName())
				{
					if(indicator.getType() == Material.CHEST && indicator.getItemMeta().getCustomModelData() == 11)
					{
						if(indicator.getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GOLD + "Storage Terminal"))
						{
							if(event.getSlot() > 45)
							{
								event.setCancelled(true);
							}else {
								ItemStack clickedItem = event.getClickedInventory().getItem(event.getSlot());
								
								if(clickedItem == null)
								{
									if(event.getCursor() != null)
									{
										List<ItemStack> is = List.of(event.getCursor());
										
										String loreLine = event.getInventory().getItem(53).getItemMeta().getLore().get(event.getInventory().getItem(53).getItemMeta().getLore().size() - 1);
										
										int x = Integer.parseInt(loreLine.split(", ")[0]);
										int y = Integer.parseInt(loreLine.split(", ")[1]);
										int z = Integer.parseInt(loreLine.split(", ")[2]);

										WorkbenchSystem ws = new WorkbenchSystem(new Location(event.getWhoClicked().getWorld(), x, y, z).getBlock());
										
										if(!WorkbenchItemRegistry.AddItemsToWorkbenchSystem(ws, is))
										{
											event.setCancelled(true);
										}
										
										return;
									}
									
									event.setCancelled(true);
									return;
								}
								
								if(clickedItem.hasItemMeta() && clickedItem.getItemMeta().hasLore())
								{
									String checkStr = clickedItem.getItemMeta().getLore().get(clickedItem.getItemMeta().getLore().size() - 1);
									
									int x = Integer.parseInt(checkStr.replace(ChatColor.GREEN + "", "").split(", ")[0]);
									int y = Integer.parseInt(checkStr.replace(ChatColor.GREEN + "", "").split(", ")[1]);
									int z = Integer.parseInt(checkStr.replace(ChatColor.GREEN + "", "").split(", ")[2]);

									Location chestLocation = new Location(event.getWhoClicked().getWorld(), x, y, z);
									
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
											int amntLeft = CheckAction(event.getAction(), clickedItem.getAmount());
											
											if(amntLeft == -1)
											{
												event.setCancelled(true);
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
											int amntLeft = CheckAction(event.getAction(), clickedItem.getAmount());
											
											if(amntLeft == -1)
											{
												event.setCancelled(true);
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
							
							event.setCancelled(true);
							
						}
					}
				}
			}
		}
	}
	
}
