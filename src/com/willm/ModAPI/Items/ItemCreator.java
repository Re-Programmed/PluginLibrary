package com.willm.ModAPI.Items;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.willm.ModAPI.Main;
import com.willm.ModAPI.MobDrop;
import com.willm.ModAPI.Blocks.CustomBlock;

public class ItemCreator {

	public static CustomItemStack RegisterNewItem(CustomItemStack item)
	{
		Main.CustomItemRegistry.add(item);
		return item;
	}
	
	public static Plant RegisterPlant(CustomItemStack drop, int amount, int growth_chance, CustomBlock... block)
	{
		Plant p = new Plant(drop, amount, growth_chance, block);
		Main.PlantRegistry.add(p);
		return p;
	}
	
	public static CustomItemStack RegisterMobDrop(MobDrop et, CustomItemStack drop, int amount)
	{
		Main.customMobDrops.put(et, drop.GetAmountClone(amount));
		return drop;
	}

	public static ItemStack CreateNamedItemStack(Material mat, String name, int amount)
	{
		ItemStack is = new ItemStack(mat, amount);
		
		ItemMeta im = is.getItemMeta();
		
		im.setDisplayName(ChatColor.WHITE + name);
		
		is.setItemMeta(im);
		return is;
	}

	public static void SetItemCustomModelData(ItemStack disp, int displayCustomModelData) {
		ItemMeta im = disp.getItemMeta();
		
		im.setCustomModelData(displayCustomModelData);
		
		disp.setItemMeta(im);
	}
}
