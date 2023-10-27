package com.willm.ModAPI.Commands;

import org.bukkit.Bukkit;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

public class CreativeMenuInventory implements InventoryHolder {

	private final Inventory m_inventory;
	public final Player Player;
	public ItemStack[] Content;
	public int Page;
	
	public Sign MySign;
	
	public final boolean IsSearch;
	
	public CreativeMenuInventory(Player player, ItemStack[] content, int page, boolean isSearch)
	{
		this.IsSearch = isSearch;
		this.m_inventory = Bukkit.createInventory(this, 54, "Creative Menu");
		this.Player = player;
		this.Content = content;
		this.Page = page;
	}
	
	public CreativeMenuInventory(Player player, ItemStack[] content, int page)
	{
		this.IsSearch = false;
		this.m_inventory = Bukkit.createInventory(this, 54, "Creative Menu");
		this.Player = player;
		this.Content = content;
		this.Page = page;
	}

	@Override
	public org.bukkit.inventory.Inventory getInventory() {
		return m_inventory;
	}
}
