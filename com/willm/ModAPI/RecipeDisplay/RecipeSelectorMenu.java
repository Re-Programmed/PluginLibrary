package com.willm.ModAPI.RecipeDisplay;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.bukkit.ChatColor;
import org.bukkit.Keyed;
import org.bukkit.Material;
import org.bukkit.inventory.BlastingRecipe;
import org.bukkit.inventory.CampfireRecipe;
import org.bukkit.inventory.CookingRecipe;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.SmokingRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import com.willm.ModAPI.Items.CustomItemStack;

public class RecipeSelectorMenu extends DisplayInventoryHolder {

	public RecipeSelectorMenu(String name, List<Recipe> recipes, List<Recipe> possibleThings, List<CustomRecipeType> customRecipes, List<CustomRecipeType> possibleCustomRecipes) {
		
		super(54, name);
		
		
		
		PopulateItem(new CustomItemStack(" ", Material.BLACK_STAINED_GLASS_PANE, 0).GetMyItemStack(), new int[] {18, 19, 20, 21, 22, 23, 24, 25, 26});
		
		int slot = 0;
		for(Recipe r : recipes)
		{
			initRecipe(r, slot++);
		}
		
		for(CustomRecipeType r : customRecipes)
		{
			initRecipe(r, slot++);
		}
		
		slot = 27;
		for(Recipe r : possibleThings)
		{
			initRecipe(r, slot++);
		}
		
		for(CustomRecipeType r : possibleCustomRecipes)
		{
			initRecipe(r, slot++);
		}
		
	}
	
	private void initRecipe(CustomRecipeType r, int currSlot)
	{
		if(currSlot > 53) {return;}

		ItemStack recipeChoice = r.GetResult().clone();
		ItemMeta meta = recipeChoice.getItemMeta();
		
		List<String> lore = new ArrayList<String>();
		String type = "";
		
		type = ChatColor.RED + r.GetName();
		for(ItemStack in : r.GetInputs())
		{
			lore.add(ChatColor.GREEN + "    " + RecipeUtils.ItemToString(in).replace(ChatColor.WHITE + "", ""));
		}
		
		
		HashMap<String, Integer> combinedLore = new HashMap<String, Integer>();
		for(String s : lore)
		{
			boolean cont = false;
			int i = 0;
			for(Entry<String, Integer> s1 : combinedLore.entrySet())
			{
				if(s1.getKey().equals(s))
				{
					cont = true;
					
					combinedLore.put(s1.getKey(), combinedLore.get(s1.getKey()) + 1);
					
					break;
				}
				
				i++;
			}
			if(cont)
			{
				continue;
			}
			
			combinedLore.put(s, 1);
		}
		
		List<String> newLore = new ArrayList<String>();
		newLore.add(ChatColor.GREEN + "Ingredients: ");

		for(Entry<String, Integer> s : combinedLore.entrySet())
		{
			if(s.getKey().equalsIgnoreCase("")) {continue;}
			if(s.getValue() == 1)
			{
				newLore.add(s.getKey());
			}else {
				newLore.add(s.getKey() + " x " + s.getValue());
			}
		}
		
		
		newLore.add(type);
		
		newLore.add(ChatColor.DARK_GRAY + r.GetKey().toString());
		
		meta.setLore(newLore);
		recipeChoice.setItemMeta(meta);
				
		myInventory.setItem(currSlot, recipeChoice);
	}
	
	private void initRecipe(Recipe r, int currSlot)
	{
		ItemStack recipeChoice = r.getResult().clone();
		ItemMeta meta = recipeChoice.getItemMeta();
		
		List<String> lore = new ArrayList<String>();
		String type = "";
		
		if(r instanceof ShapedRecipe)
		{
			type = ChatColor.RED + "Shaped Crafting";
			ShapedRecipe sr = (ShapedRecipe)r;
			for(int i = 0; i < sr.getShape().length; i++)
			{
				String row = sr.getShape()[i];
				
				for(char c : row.toCharArray())
				{
					if(!sr.getIngredientMap().containsKey(c) || c == ' ' || sr.getIngredientMap().get(c) == null) {continue;}
					lore.add(ChatColor.GREEN + "    " + RecipeUtils.ItemToString(sr.getIngredientMap().get(c)).replace(ChatColor.WHITE + "", ""));
				}

			}
		}else if(r instanceof ShapelessRecipe)
		{
			type = ChatColor.RED + "Shapeless Crafting";
			ShapelessRecipe sr = (ShapelessRecipe)r;
			for(ItemStack i : sr.getIngredientList())
			{
				lore.add(ChatColor.GREEN + "    " + RecipeUtils.ItemToString(i).replace(ChatColor.WHITE + "", ""));
			}
		}else if(r instanceof CookingRecipe)
		{
			if(r instanceof FurnaceRecipe)
			{
				type = ChatColor.RED + "Smelting";
			}else if(r instanceof BlastingRecipe)
			{
				type = ChatColor.RED + "Blasting";
			}else if(r instanceof CampfireRecipe)
			{
				type = ChatColor.RED + "Cooking (Campfire)";
			}else if(r instanceof SmokingRecipe)
			{
				type = ChatColor.RED + "Smoking";
			}
			
			
			@SuppressWarnings("rawtypes")
			CookingRecipe cr = (CookingRecipe)r;
			
			lore.add(ChatColor.GREEN + "    " + RecipeUtils.ItemToString(cr.getInput()).replace(ChatColor.WHITE + "", ""));
		}
		
		HashMap<String, Integer> combinedLore = new HashMap<String, Integer>();
		for(String s : lore)
		{
			boolean cont = false;
			int i = 0;
			for(Entry<String, Integer> s1 : combinedLore.entrySet())
			{
				if(s1.getKey().equals(s))
				{
					cont = true;
					
					combinedLore.put(s1.getKey(), combinedLore.get(s1.getKey()) + 1);
					
					break;
				}
				
				i++;
			}
			if(cont)
			{
				continue;
			}
			
			combinedLore.put(s, 1);
		}
		
		List<String> newLore = new ArrayList<String>();
		newLore.add(ChatColor.GREEN + "Ingredients: ");

		for(Entry<String, Integer> s : combinedLore.entrySet())
		{
			if(s.getKey().equalsIgnoreCase("")) {continue;}
			if(s.getValue() == 1)
			{
				newLore.add(s.getKey());
			}else {
				newLore.add(s.getKey() + " x " + s.getValue());
			}
		}
		
		
		newLore.add(type);
		
		Keyed key = (Keyed)r;
		newLore.add(ChatColor.DARK_GRAY + key.getKey().toString());
		
		meta.setLore(newLore);
		recipeChoice.setItemMeta(meta);
		
		if(currSlot < 54)
		{
			myInventory.setItem(currSlot, recipeChoice);
		}
	}
	

}
