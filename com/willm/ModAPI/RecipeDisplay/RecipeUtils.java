package com.willm.ModAPI.RecipeDisplay;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

import org.bukkit.Bukkit;
import org.bukkit.Keyed;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;

public final class RecipeUtils {

	
	public static List<Recipe> GetAddedRecipes()
	{
		List<Recipe> ret = new ArrayList<Recipe>();
		
		Iterator<Recipe> recipes = Bukkit.getServer().recipeIterator();
		
		while(recipes.hasNext())
		{
			Recipe r = recipes.next();
			
			ret.add(r);
		}
		
		return ret;
	}
	
	public static String ItemToString(ItemStack item)
	{
		if(item == null) {return "";}
		if(item.hasItemMeta())
		{
			if(item.getItemMeta().hasCustomModelData())
			{
				return item.getItemMeta().getDisplayName() + (item.getAmount() == 1 ? "" : " x " + item.getAmount());
			}
		}
		
		return upperCaseAllFirstCharacters(item.getType().toString().toLowerCase().replace('_', ' ')) + (item.getAmount() == 1 ? "" : " x " + item.getAmount());
	}
	
	public static String upperCaseAllFirstCharacters(String text) {
	    String regex = "\\b(.)(.*?)\\b";
	    String result = Pattern.compile(regex).matcher(text).replaceAll(
	            match -> match.group(1).toUpperCase() + match.group(2)
	    );

	    return result;
	}
	
	public static Recipe GetRecipeFromKey(String key)
	{		
		Iterator<Recipe> recipes = Bukkit.getServer().recipeIterator();
		
		while(recipes.hasNext())
		{
			Recipe r = recipes.next();
			
			if(r instanceof Keyed)
			{
				Keyed cr = (Keyed)r;
				
				if(key.equals(cr.getKey().toString()) || key.equals(cr.getKey().toString().replace(cr.getKey().getNamespace(), "")))
				{
					return r;
				}
			}
		}
		
		return null;
	}
	
	public static List<String> GetRecipeNames()
	{
		List<String> ret = new ArrayList<String>();
		
		Iterator<Recipe> recipes = Bukkit.getServer().recipeIterator();
		
		while(recipes.hasNext())
		{
			Recipe r = recipes.next();
			
			if(r instanceof Keyed)
			{
				Keyed cr = (Keyed)r;
				
				if(!cr.getKey().getNamespace().equalsIgnoreCase("minecraft"))
				{
					ret.add(cr.getKey().toString());
				}
				
			}
		}
		
		return ret;
	}
}
