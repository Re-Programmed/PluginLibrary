package com.willm.ModAPI.RecipeDisplay;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import com.willm.ModAPI.Items.CustomItemStack;

public class CustomRecipeType {
	private final ItemStack displayItem;
	private final String recipeName;
	private final List<ItemStack> inputs;
	private final ItemStack output;
	
	private final String key;
	
	private final ItemStack infoItem;

	private List<ItemStack> byproducts = new ArrayList<ItemStack>();
	
	public CustomRecipeType AddByproduct(ItemStack byproduct)
	{
		byproducts.add(byproduct);
		return this;
	}
	
	public List<ItemStack> GetByproducts()
	{
		return byproducts;
	}
	
	public CustomRecipeType(String recipeName, Material displayMat, String key, Plugin keyNamespace, ItemStack infoItem, ItemStack output, ItemStack... inputs)
	{
		this.recipeName = recipeName;
		
		displayItem = new CustomItemStack(ChatColor.GOLD + recipeName, displayMat, 0).AddFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS).GetMyItemStack();
		
		this.inputs = List.of(inputs);
		this.output = output;
		
		this.infoItem = infoItem;
		
		if(infoItem != null)
		{
			ItemMeta m = this.infoItem.getItemMeta();
			
			m.addEnchant(Enchantment.LUCK, 1, true);
			m.addItemFlags(ItemFlag.HIDE_ENCHANTS);
			
			this.infoItem.setItemMeta(m);
		}
		
		this.key = NamespacedKey.fromString(key, keyNamespace).toString();
	}
	
	public CustomRecipeType(String recipeName, ItemStack displayItem, String key, Plugin keyNamespace, ItemStack infoItem, ItemStack output, ItemStack... inputs)
	{
		this.recipeName = recipeName;
		
		this.displayItem = displayItem;
		
		this.inputs = List.of(inputs);
		this.output = output;
		
		this.infoItem = infoItem;
		
		if(infoItem != null)
		{
			ItemMeta m = this.infoItem.getItemMeta();
			
			m.addEnchant(Enchantment.LUCK, 1, true);
			m.addItemFlags(ItemFlag.HIDE_ENCHANTS);
			
			this.infoItem.setItemMeta(m);
		}
		
		this.key = NamespacedKey.fromString(key, keyNamespace).toString();
	}
	
	public ItemStack GetInfoItem()
	{
		return infoItem;
	}
	
	public ItemStack GetDisplayItem()
	{
		return displayItem;
	}
	
	public String GetName()
	{
		return recipeName;
	}
	
	public List<ItemStack> GetInputs()
	{
		return inputs;
	}
	
	public ItemStack GetResult()
	{
		return output;
	}
	
	public String GetKey()
	{
		return key;
		
	}
	
}
