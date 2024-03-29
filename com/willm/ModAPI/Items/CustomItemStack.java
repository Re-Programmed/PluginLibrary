package com.willm.ModAPI.Items;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.attribute.AttributeModifier.Operation;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.BlastingRecipe;
import org.bukkit.inventory.CampfireRecipe;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.SmokingRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import com.willm.ModAPI.Main;
import com.willm.ModAPI.Blocks.CustomBlock;
import com.willm.ModAPI.Items.Recipes.FurnaceRecipeBuilder;
import com.willm.ModAPI.Items.Recipes.RecipeBuilder;
import com.willm.ModAPI.Items.Recipes.RecipeTemplate;
import com.willm.ModAPI.Items.Recipes.RecipeTemplates;

public class CustomItemStack {
	private String name;
	Material mat;
	
	int cmd;
	
	ItemStack relatedItem;
	
	CustomBlock relatedBlock;
	public CustomBlock getRelatedBlock() {return relatedBlock;}
	
	public void setRelatedBlock(CustomBlock cb) {relatedBlock = cb;}
		
	public CustomItemStack(String name, Material mat, int cmd, CustomBlock relatedBlock)
	{
		setup(name, mat, cmd);
		
		this.relatedBlock = relatedBlock;
	}
	
	//Adds bonus absorption and food level.
	public CustomItemStack AddFoodModifier(int food, int absorption)
	{
		AddLoreLine(ChatColor.YELLOW + "" + food + " Food, " + absorption + " Absorption");
		Main.ConsumableRegistry.add(this);
		return this;
	}
	
	public boolean CheckForCustomItem(ItemStack item)
	{
		if(item.getType() == relatedItem.getType())
		{
			if(item.hasItemMeta())
			{
				ItemMeta im = item.getItemMeta();
				if(im.hasCustomModelData())
				{
					if(im.getCustomModelData() == cmd)
					{
						return true;
					}
				}
			}
		}
		
		return false;
	}
	
	public CustomItemStack(String name, Material mat, int cmd)
	{
		setup(name, mat, cmd);
	}
	
	public CustomItemStack AddEnchant(Enchantment enchant, int power, boolean ignore)
	{
		ItemMeta m = relatedItem.getItemMeta();
		m.addEnchant(enchant, power, ignore);
		relatedItem.setItemMeta(m);
		return this;
	}
	
	public CustomItemStack AddFlags(ItemFlag... flags)
	{
		ItemMeta m = relatedItem.getItemMeta();
		m.addItemFlags(flags);
		relatedItem.setItemMeta(m);
		return this;
	}
	
	public CustomItemStack Attribute(Attribute attrib, float amount)
	{
		ItemMeta m = relatedItem.getItemMeta();
		m.addAttributeModifier(attrib, new AttributeModifier(UUID.randomUUID(), "add_" + attrib.toString().toLowerCase().replace(' ', '_') + "_" + amount + "_custom", amount, Operation.ADD_NUMBER));
		relatedItem.setItemMeta(m);
		return this;
	}
	
	public CustomItemStack Attribute(Attribute attrib, float amount, EquipmentSlot slot)
	{
		ItemMeta m = relatedItem.getItemMeta();
		m.addAttributeModifier(attrib, new AttributeModifier(UUID.randomUUID(), "add_" + attrib.toString().toLowerCase().replace(' ', '_') + "_" + amount + "_custom", amount, Operation.ADD_NUMBER, slot));
		relatedItem.setItemMeta(m);
		return this;
	}
	
	ItemStack setup(String name, Material mat, int cmd)
	{
		this.setName(name);
		this.mat = mat;
		this.cmd = cmd;
		
		relatedItem = new ItemStack(mat);
		
		ItemMeta meta = relatedItem.getItemMeta();
		
		meta.setDisplayName(ChatColor.WHITE + name);
		meta.setCustomModelData(cmd);
		
		ArrayList<String> str = new ArrayList<String>();
		
		meta.setLore(str);
		
		relatedItem.setItemMeta(meta);
		
		return relatedItem;
	}
	
	public CustomItemStack AddLoreLine(String line) {return AddLoreLine(line, false);}
	public CustomItemStack AddLoreLine(String line, boolean ignoreRepeats)
	{
		ItemMeta m = relatedItem.getItemMeta();

		List<String> lore = null;
		
		if(m.hasLore())
		{
			lore = m.getLore();
			
			if(ignoreRepeats && lore.contains(line)) {return this;}
		}else {
			lore = new ArrayList<String>();
		}
		
		lore.add(line);
		
		m.setLore(lore);
		relatedItem.setItemMeta(m);
		
		return this;
	}
	
	public RecipeBuilder getRecipe(int count, String row1, String row2, String row3, String name)
	{
		ItemStack result = relatedItem.clone();
		result.setAmount(count);
		ShapedRecipe sr = new ShapedRecipe(NamespacedKey.fromString(name, com.willm.CoreMOD.Main.INSTANCE), result);
		sr.shape(row1, row2, row3);
				
		return new RecipeBuilder(sr);
	}
	
	public RecipeBuilder getRecipe(int count, RecipeTemplate rt, String name)
	{

		ItemStack result = relatedItem.clone();
		result.setAmount(count);
		
		ShapedRecipe sr = rt.Generate(name, result);
				
		return new RecipeBuilder(sr);
	}
	
	public ShapelessRecipe GenUnshaped(String name, Material... r)
	{
		
		ShapelessRecipe sr = new ShapelessRecipe(NamespacedKey.fromString(name, com.willm.CoreMOD.Main.INSTANCE), relatedItem.clone());		
		for(Material m : r)
		{
			sr.addIngredient(m);
		}	
				
		return sr;
	}
	
	public ShapelessRecipe GenUnshaped(String name) {return GenUnshaped(name, 1);}
	public ShapelessRecipe GenUnshaped(String name, int count)
	{
		ItemStack clone = relatedItem.clone();
		clone.setAmount(count);
		ShapelessRecipe sr = new ShapelessRecipe(NamespacedKey.fromString(name, com.willm.CoreMOD.Main.INSTANCE), clone);		
				
		return sr;
	}
	
	public RecipeBuilder getRecipe(int count, RecipeTemplates rt, String name)
	{
		
		ItemStack result = relatedItem.clone();
		result.setAmount(count);
		
		ShapedRecipe sr = rt.MyTemplate.Generate(name, result);
				
		return new RecipeBuilder(sr);
	}
	
	public RecipeBuilder getRecipe(int count, String row1, String row2, String row3)
	{

		ItemStack result = relatedItem.clone();
		result.setAmount(count);
		ShapedRecipe sr = new ShapedRecipe(NamespacedKey.fromString(name.toLowerCase().replace(' ', '_'), com.willm.CoreMOD.Main.INSTANCE), result);
		sr.shape(row1, row2, row3);
		
		
		return new RecipeBuilder(sr);
	}
	
	public RecipeBuilder getRecipe(int count, String row1, String row2)
	{

		ItemStack result = relatedItem.clone();
		result.setAmount(count);
		ShapedRecipe sr = new ShapedRecipe(NamespacedKey.fromString(name.toLowerCase().replace(' ', '_'), com.willm.CoreMOD.Main.INSTANCE), result);
		sr.shape(row1, row2);
		
		
		return new RecipeBuilder(sr);
	}
	
	public RecipeBuilder getRecipe(String name, int count, String row1, String row2)
	{

		ItemStack result = relatedItem.clone();
		result.setAmount(count);
		ShapedRecipe sr = new ShapedRecipe(NamespacedKey.fromString(name.toLowerCase().replace(' ', '_'), com.willm.CoreMOD.Main.INSTANCE), result);
		sr.shape(row1, row2);
		
		
		return new RecipeBuilder(sr);
	}
	
	//Creates a recipe where the item specified just needs to be placed in the crafting table at one spot to make the result.
	public void createSingleItemRecipe(String name, int count, RecipeChoice.ExactChoice item)
	{
		this.getRecipe(name + "_1", count, "S ", "  ").AddMaterial('S', item).Finalize();
		this.getRecipe(name + "_2", count, "  ", " S").AddMaterial('S', item).Finalize();
		this.getRecipe(name + "_3", count, " S", "  ").AddMaterial('S', item).Finalize();
		this.getRecipe(name + "_4", count, "  ", "S ").AddMaterial('S', item).Finalize();
	}
	
	public void createSingleItemRecipe(String name, int count, Material item)
	{
		this.getRecipe(name + "_1", count, "S ", "  ").AddMaterial('S', item).Finalize();
		this.getRecipe(name + "_2", count, "  ", " S").AddMaterial('S', item).Finalize();
		this.getRecipe(name + "_3", count, " S", "  ").AddMaterial('S', item).Finalize();
		this.getRecipe(name + "_4", count, "  ", "S ").AddMaterial('S', item).Finalize();
	}
	
	
	public FurnaceRecipeBuilder getFurnaceRecipe(Material input, float xp, int cooktime, String name)
	{
		return FurnaceRecipeBuilder.createFurncaceRecipe(name, this.relatedItem, input, xp, cooktime);
	}
	
	public FurnaceRecipeBuilder getFurnaceRecipe(RecipeChoice.ExactChoice input, float xp, int cooktime, String name)
	{
		return FurnaceRecipeBuilder.createFurncaceRecipe(name, this.relatedItem, input, xp, cooktime);
	}
	
	public SmokingRecipe getSmokingRecipe(RecipeChoice.ExactChoice input, float xp, int cooktime, String name)
	{
		SmokingRecipe br = new SmokingRecipe(NamespacedKey.fromString(name, com.willm.CoreMOD.Main.INSTANCE), this.GetMyItemStack(), input, xp, cooktime);
		Bukkit.getServer().addRecipe(br);
		return br;
	}
	
	public CampfireRecipe getCampfireRecipe(RecipeChoice.ExactChoice input, float xp, int cooktime, String name)
	{
		CampfireRecipe br = new CampfireRecipe(NamespacedKey.fromString(name, com.willm.CoreMOD.Main.INSTANCE), this.GetMyItemStack(), input, xp, cooktime);
		Bukkit.getServer().addRecipe(br);
		return br;
	}
	
	public BlastingRecipe getBlastingRecipe(RecipeChoice.ExactChoice input, float xp, int cooktime, String name)
	{
		BlastingRecipe br = new BlastingRecipe(NamespacedKey.fromString(name, com.willm.CoreMOD.Main.INSTANCE), this.GetMyItemStack(), input, xp, cooktime);
		Bukkit.getServer().addRecipe(br);
		return br;
	}
	
	public BlastingRecipe getBlastingRecipe(Material input, float xp, int cooktime, String name)
	{
		BlastingRecipe br = new BlastingRecipe(NamespacedKey.fromString(name, com.willm.CoreMOD.Main.INSTANCE), this.GetMyItemStack(), input, xp, cooktime);
		Bukkit.getServer().addRecipe(br);
		return br;
	}
	
	public ItemStack GetMyItemStack()
	{
		return relatedItem;
	}

	public String getName() {
		return name;
	}

	void setName(String name) {
		this.name = name;
	}
	
	public void DropNaturally(Location location)
	{
		location.getWorld().dropItemNaturally(location, relatedItem);
	}

	public int getCustomModelData() {
		return relatedItem.getItemMeta().getCustomModelData();
	}

	public Material getType() {
		return relatedItem.getType();
	}

	public ItemStack GetAmountClone(int i) {
		ItemStack is = this.GetMyItemStack().clone();
		is.setAmount(i);
		return is;
	}

	public void SetLore(List<String> lore) {
		ItemMeta m = this.GetMyItemStack().getItemMeta();
		m.setLore(lore);
		
		this.GetMyItemStack().setItemMeta(m);
	}

}
