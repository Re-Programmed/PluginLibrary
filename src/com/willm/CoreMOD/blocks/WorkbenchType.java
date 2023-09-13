package com.willm.CoreMOD.blocks;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;

import com.willm.ModAPI.Items.CustomItemStack;

public enum WorkbenchType {

	CRAFTING_BENCH(new CustomItemStack(ChatColor.GOLD + "Crafting Workbench", Material.WARPED_TRAPDOOR, 60002).AddEnchant(Enchantment.LUCK, 1, true).AddFlags(ItemFlag.HIDE_ENCHANTS)),
	STORAGE_BENCH(new CustomItemStack(ChatColor.RED + "Disconnected Storage Workbench", Material.CHEST, 0).AddEnchant(Enchantment.LUCK, 1, true).AddFlags(ItemFlag.HIDE_ENCHANTS)),
	NONE(null);
	
	public final CustomItemStack DisplayItem;
	public Workbench RelativeBench;
	
	WorkbenchType(CustomItemStack displayItem)
	{
		DisplayItem = displayItem;
	}
}
