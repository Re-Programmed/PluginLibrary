package com.willm.CoreMOD.Shopping;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import com.willm.ModAPI.Items.CustomItemStack;

public class ShopBlockUI implements InventoryHolder {

	private final Inventory myInventory;

	public ShopBlockUI()
	{
		myInventory = Bukkit.createInventory(this, 27, ChatColor.DARK_GREEN + "Shop");
	}
	
	private ItemStack getPlaceholderItem()
	{
		return new CustomItemStack("", Material.BLACK_STAINED_GLASS_PANE, 0).GetMyItemStack();
	}
	
	public void MenuSelection(Location location)
	{
		ItemStack setPrice = new CustomItemStack(ChatColor.GREEN + "Set Price", Material.EMERALD, 0).AddEnchant(Enchantment.LUCK, 1, true).AddFlags(ItemFlag.HIDE_ENCHANTS).GetMyItemStack();
		myInventory.addItem(setPrice);
		
		ItemStack retrieveAll = new CustomItemStack(ChatColor.BLUE + "Retrieve Profits", Material.HOPPER, 0).AddEnchant(Enchantment.LUCK, 1, true).AddFlags(ItemFlag.HIDE_ENCHANTS).GetMyItemStack();
		myInventory.addItem(retrieveAll);

		ItemStack confirmationItem = new CustomItemStack("Shop", ShopBlock.SHOP_BLOCK_ITEM.getType(), ShopBlock.SHOP_BLOCK_ITEM.getCustomModelData()).AddEnchant(Enchantment.LUCK, 1, true).AddFlags(ItemFlag.HIDE_ENCHANTS)
			.AddLoreLine(ChatColor.DARK_GRAY + "Linked Store: " + location.getWorld().getName() + " " + location.getBlockX() + " " + location.getBlockY() + " " + location.getBlockZ())
				.GetMyItemStack();
		myInventory.setItem(myInventory.getSize() - 1, confirmationItem);
	}
	
	@Override
	public Inventory getInventory() {
		return myInventory;
	}
	
}
