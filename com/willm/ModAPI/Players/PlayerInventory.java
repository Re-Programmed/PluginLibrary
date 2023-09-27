package com.willm.ModAPI.Players;

import java.util.HashMap;

import org.bukkit.inventory.ItemStack;

import com.willm.ModAPI.Items.CustomItemStack;

public class PlayerInventory {

	final org.bukkit.inventory.PlayerInventory relInventory;
	
	public PlayerInventory(org.bukkit.inventory.PlayerInventory inventory)
	{
		relInventory = inventory;
	}
	
	public HashMap<Integer, ItemStack> addItem(CustomItemStack item)
	{
		return relInventory.addItem(item.GetMyItemStack());
	}
}
