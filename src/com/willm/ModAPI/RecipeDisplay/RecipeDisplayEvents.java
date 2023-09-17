package com.willm.ModAPI.RecipeDisplay;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Keyed;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.CookingRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;

public class RecipeDisplayEvents implements Listener {
	
	@EventHandler
	public void InventoryClick(InventoryClickEvent event)
	{
		if(event.getClickedInventory().getSize() == 54)
		{
			ItemStack checkItem = event.getClickedInventory().getItem(18);
			if(checkItem != null)
			{
				if(checkItem.getType() != Material.BLACK_STAINED_GLASS_PANE) {return;}
				if(checkItem.hasItemMeta())
				{
					if(checkItem.getItemMeta().hasCustomModelData())
					{
						event.setCancelled(true);
						
						ItemStack clickedItem = event.getClickedInventory().getItem(event.getSlot());
						if(clickedItem != null)
						{
							if(clickedItem.hasItemMeta())
							{
								if(clickedItem.getItemMeta().hasLore())
								{
									List<String> lore = clickedItem.getItemMeta().getLore();
									
									String lastLine = lore.get(lore.size() - 1);
									
									if(lastLine.startsWith(ChatColor.DARK_GRAY + ""))
									{
										Bukkit.getServer().dispatchCommand(event.getWhoClicked(), "recipes " + lastLine.replace(ChatColor.DARK_GRAY + "", ""));
									}
								}
							}
						}
					}
				}
			}
		}
		
		if(event.getClickedInventory().getItem(0) != null)
		{
			if(event.getClickedInventory().getItem(0).getType() == Material.BLACK_STAINED_GLASS_PANE)
			{
				if(event.getClickedInventory().getItem(0).getItemMeta().hasCustomModelData())
				{
					ItemStack i = event.getClickedInventory().getItem(event.getSlot());
					if(i != null)
					{
						if(i.getType() != Material.BLACK_STAINED_GLASS_PANE && i.getType() != Material.ORANGE_STAINED_GLASS_PANE)
						{
							
							List<Recipe> recipes = GetItemCreationRecipes(i);
							List<Recipe> possibleThings = GetItemUseRecipes(i);
							List<CustomRecipeType> customRecipes = GetItemCreationRecipesCustom(i);
							List<CustomRecipeType> possibleCustomThings = GetItemUseRecipesCustom(i);
															
							if(event.getClick() == ClickType.RIGHT)
							{
								RecipeSelectorMenu rsm = new RecipeSelectorMenu(ChatColor.GREEN + "Select Recipe: " + RecipeUtils.ItemToString(i), recipes, possibleThings, customRecipes, possibleCustomThings);
								event.getWhoClicked().openInventory(rsm.getInventory());
								event.setCancelled(true);
								return;
							}
							
							if(((recipes.size() == 1 && customRecipes.size() == 0)))
							{
								Bukkit.getServer().dispatchCommand(event.getWhoClicked(), "recipes " + ((Keyed)recipes.get(0)).getKey().toString());
							}else if(recipes.size() == 0 && customRecipes.size() == 1)
							{
								Bukkit.getServer().dispatchCommand(event.getWhoClicked(), "recipes " + customRecipes.get(0).GetKey());
							
							}else {
								RecipeSelectorMenu rsm = new RecipeSelectorMenu(ChatColor.GREEN + "Select Recipe: " + RecipeUtils.ItemToString(i), recipes, possibleThings, customRecipes, possibleCustomThings);
								event.getWhoClicked().openInventory(rsm.getInventory());

							}
						}
					
					}
					
					event.setCancelled(true);
				}
			}
		}
	}
	
	
	public static List<CustomRecipeType> GetItemUseRecipesCustom(ItemStack i)
	{
		List<CustomRecipeType> possibleThings = new ArrayList<CustomRecipeType>();

		for(CustomRecipeType crt : RecipeDisplay.CUSTOM_RECIPES)
		{
			if(TwoItemsSimilar(crt.GetDisplayItem(), i))
			{
				possibleThings.add(crt);
				continue;
			}

			for(ItemStack is : crt.GetInputs())
			{
				if(i.getType() != is.getType()) {continue;}
				if(is.hasItemMeta())
				{
					if(is.getItemMeta().hasCustomModelData())
					{
						if(i.hasItemMeta())
						{
							if(i.getItemMeta().hasCustomModelData())
							{
								if(i.getItemMeta().getCustomModelData() == is.getItemMeta().getCustomModelData())
								{
									possibleThings.add(crt);
									break;
								}
							}
						}
						continue;
					}
				}
				
				possibleThings.add(crt);
			}
			
		}
		
		return possibleThings;
	}
	
	public static List<CustomRecipeType> GetItemCreationRecipesCustom(ItemStack i)
	{
		List<CustomRecipeType> possibleThings = new ArrayList<CustomRecipeType>();

		for(CustomRecipeType crt : RecipeDisplay.CUSTOM_RECIPES)
		{
			if(i.getType() != crt.GetResult().getType()) {continue;}
			if(crt.GetResult().hasItemMeta())
			{
				if(crt.GetResult().getItemMeta().hasCustomModelData())
				{
					if(i.hasItemMeta())
					{
						if(i.getItemMeta().hasCustomModelData())
						{
							if(i.getItemMeta().getCustomModelData() == crt.GetResult().getItemMeta().getCustomModelData())
							{
								possibleThings.add(crt);
							}
						}
					}
					continue;
				}
			}
			
			if(i.hasItemMeta())
			{
				if(i.getItemMeta().hasCustomModelData()) {continue;}
			}
			
			possibleThings.add(crt);
		}
		
		return possibleThings;
	}
	
	public static List<Recipe> GetItemUseRecipes(ItemStack i)
	{
		List<Recipe> possibleThings = new ArrayList<Recipe>();

		Iterator<Recipe> possibleIteration = Bukkit.getServer().recipeIterator();
		while(possibleIteration.hasNext())
		{
			Recipe r = possibleIteration.next();
			
			if(r instanceof ShapedRecipe)
			{
				ShapedRecipe sr = (ShapedRecipe)r;
				
				for(ItemStack i1 : sr.getIngredientMap().values())
				{
					if(TwoItemsSimilar(i1, i))
					{
						possibleThings.add(r);

						break;
					}
				}
				
			}else if(r instanceof ShapelessRecipe)
			{
				ShapelessRecipe sr = (ShapelessRecipe)r;
				
				for(ItemStack i1 : sr.getIngredientList())
				{
					if(TwoItemsSimilar(i1, i))
					{
						possibleThings.add(r);

						break;
					}
				}
			}else if(r instanceof CookingRecipe)
			{
				@SuppressWarnings("rawtypes")
				CookingRecipe sr = (CookingRecipe)r;
				if(TwoItemsSimilar(sr.getInput(), i))
				{
					possibleThings.add(r);
				}
			}
		}
		
		return possibleThings;
	}
	
	public static boolean TwoItemsSimilar(ItemStack i1, ItemStack i2)
	{
		if(i1 == null || i2 == null) {return false;}
		if(i1.getType() != i2.getType()) {
			return false;
		}
		
		if(i1.hasItemMeta())
		{
			if(i1.getItemMeta().hasCustomModelData())
			{
				if(i2.hasItemMeta())
				{
					if(i2.getItemMeta().hasCustomModelData())
					{
						return i1.getItemMeta().getCustomModelData() == i2.getItemMeta().getCustomModelData();
					}
				}
				
				return false;
			}
		}
		
		if(i2.hasItemMeta())
		{
			if(i2.getItemMeta().hasCustomModelData())
			{
				return false;
			}
		}
		
		return true;
	}
	
	public static List<Recipe> GetItemCreationRecipes(ItemStack i)
	{
		List<Recipe> recipes = new ArrayList<Recipe>();
		for(Recipe r : Bukkit.getServer().getRecipesFor(i))
		{
			if(i.hasItemMeta())
			{
				if(i.getItemMeta().hasCustomModelData())
				{
					if(r.getResult().hasItemMeta())
					{
						if(r.getResult().getItemMeta().hasCustomModelData())
						{
							if(r.getResult().getItemMeta().getCustomModelData() == i.getItemMeta().getCustomModelData())
							{
								recipes.add(r);
							}
						}
					}
					
					continue;
				}
			}
			
			if(!r.getResult().hasItemMeta())
			{
				recipes.add(r);
				continue;
			}
			
			if(!r.getResult().getItemMeta().hasCustomModelData())
			{
				recipes.add(r);
				continue;
			}
			
		}
		
		return recipes;
		
		
		
	}
}
