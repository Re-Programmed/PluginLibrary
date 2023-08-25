package com.willm.ModAPI.Enchantments;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.willm.ModAPI.Items.CustomItemStack;
import com.willm.ModAPI.Items.ItemCreator;

public class CustomEnchantment {
	
	public static List<CustomEnchantment> CustomEnchantments = new ArrayList<CustomEnchantment>();
	
	private final String name;
	
	private final CustomItemStack book;
	
	private final Enchantment myEnchantment;
	
	private final List<Material> CompatibleMats = new ArrayList<Material>();
	
	public CustomEnchantment(String name)
	{
		this.name = name;
		this.myEnchantment = new EnchantmentWrapper(name.toLowerCase().replace(' ', '_'), name, 1);
		
		book = ItemCreator.RegisterNewItem(new CustomItemStack("Book Of " + name, Material.ENCHANTED_BOOK, 10001));
		
		book.AddLoreLine(ChatColor.GRAY + name);
		
		CustomEnchantments.add(this);
	}
	
	public CustomEnchantment(String name, Material... compatible)
	{
		this.name = name;
		this.myEnchantment = new EnchantmentWrapper(name.toLowerCase().replace(' ', '_'), name, 1);
		
		book = ItemCreator.RegisterNewItem(new CustomItemStack("Book Of " + name, Material.ENCHANTED_BOOK, 10001));
		
		book.AddLoreLine(ChatColor.GRAY + name);
		
		CustomEnchantments.add(this);
		
		for(Material m : compatible)
		{
			CompatibleMats.add(m);
		}
	}
	
	public CustomEnchantment(String name, EnchantCompatibleTemplates... compatible)
	{
		this.name = name;
		this.myEnchantment = new EnchantmentWrapper(name.toLowerCase().replace(' ', '_'), name, 1);
		
		book = ItemCreator.RegisterNewItem(new CustomItemStack("Book Of " + name, Material.ENCHANTED_BOOK, 10001));
		
		book.AddLoreLine(ChatColor.GRAY + name);
		
		CustomEnchantments.add(this);
		
		for(EnchantCompatibleTemplates ect : compatible)
		{
			for(Material m : ect.Materials)
			{
				CompatibleMats.add(m);
			}
		}
	}
	
	public boolean CheckCompatible(Material mat)
	{
		if(CompatibleMats.isEmpty()) {return true;}
		
		return CompatibleMats.contains(mat);
	}
	
	public static CustomEnchantment GetEnchantFromName(String name)
	{
		for(CustomEnchantment ce : CustomEnchantments)
		{
			if(ce.getName().toLowerCase().replace("" + ChatColor.GRAY, "").equalsIgnoreCase(name.toLowerCase().replace("" + ChatColor.GRAY, "")))
			{
				return ce;
			}
		}
		
		return null;
	}
	
	public static boolean hasEnchant(ItemStack i, String name)
	{
		if(i == null) {return false;}
		if(!i.hasItemMeta()) {return false;}
		ItemMeta m = i.getItemMeta();
		if(m.hasLore())
		{
			for(String s : m.getLore())
			{
				if(s.toLowerCase().replace("" + ChatColor.GRAY, "").equalsIgnoreCase(name.toLowerCase()))
				{
					return true;
				}
			}
		}
		
		return false;
	}
	
	public boolean ApplyEnchant(ItemStack item)
	{
		ItemMeta m = item.getItemMeta();
		m.addEnchant(myEnchantment, 1, true);
		
		List<String> lore = m.getLore();
		
		if(lore == null)
		{
			lore = new ArrayList<String>();
		}else {
			for(String s : lore)
			{
				if(s.toLowerCase().replace(" ", "_").replace(ChatColor.GRAY + "", "").equalsIgnoreCase(name.toLowerCase().replace(" ", "_").replace(ChatColor.GRAY + "", "")))
				{
					return false;
				}
				
			}
		}
		
		lore.add(ChatColor.GRAY + name);
		
		m.setLore(lore);
		
		item.setItemMeta(m);
		return true;
	}
	
	public String getName()
	{
		return name;
	}
	
	public CustomItemStack getBook()
	{
		return book;
	}
	
}
