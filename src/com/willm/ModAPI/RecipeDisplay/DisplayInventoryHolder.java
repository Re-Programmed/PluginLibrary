package com.willm.ModAPI.RecipeDisplay;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import com.willm.ModAPI.Items.CustomItemStack;
import com.willm.ModAPI.Terrain.OreEvents;

public class DisplayInventoryHolder implements InventoryHolder {

	protected final Inventory myInventory;
	
	public DisplayInventoryHolder(int size, String name)
	{
		myInventory = Bukkit.createInventory(this, size, name);
	}
	
	@Override
	public Inventory getInventory() {
		return myInventory;
	}

	public void PopulateItem(ItemStack item, int... locations)
	{
		for(int i : locations)
		{
			myInventory.setItem(i, item);
		}
	}
	
	public void PopulateItem(ItemStack item)
	{
		for(int i = 0; i < myInventory.getSize(); i++)
		{
			myInventory.setItem(i, item);
		}
	}
	
	public static final ItemStack GetDisplayGlass()
	{
		return new CustomItemStack("", Material.BLACK_STAINED_GLASS_PANE, 1).GetMyItemStack();
	}
}
