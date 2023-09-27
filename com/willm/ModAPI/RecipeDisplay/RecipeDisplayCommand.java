package com.willm.ModAPI.RecipeDisplay;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Keyed;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.BlastingRecipe;
import org.bukkit.inventory.CampfireRecipe;
import org.bukkit.inventory.CookingRecipe;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.SmokingRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import com.willm.ModAPI.Main;
import com.willm.ModAPI.Items.CustomItemStack;

public class RecipeDisplayCommand implements CommandExecutor {

	@SuppressWarnings("rawtypes")
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(label.equalsIgnoreCase("recipes"))
		{
			if(args.length == 1)
			{
				if(sender instanceof Player)
				{
					Player player = (Player)sender;
					if(args[0].startsWith("item:"))
					{
						for(CustomItemStack cis : Main.CustomItemRegistry)
						{
							if(cis.getName().replace(ChatColor.WHITE + "", "").toLowerCase().replace(' ', '_').equalsIgnoreCase(args[0].replace("item:", "")))
							{
								ItemStack i = cis.GetMyItemStack();
								List<Recipe> recipes = RecipeDisplayEvents.GetItemCreationRecipes(i);
								List<Recipe> possibleThings = RecipeDisplayEvents.GetItemUseRecipes(i);
								List<CustomRecipeType> customRecipes = RecipeDisplayEvents.GetItemCreationRecipesCustom(i);
								List<CustomRecipeType> possibleCustomThings = RecipeDisplayEvents.GetItemUseRecipesCustom(i);
								
								if(recipes.size() == 1 && customRecipes.size() == 0)
								{
									Bukkit.getServer().dispatchCommand(player, "recipes " + ((Keyed)recipes.get(0)).getKey().toString());
								}else if(recipes.size() == 0 && customRecipes.size() == 1)
								{
									Bukkit.getServer().dispatchCommand(player, "recipes " + customRecipes.get(0).GetKey());
								
								}else {
									RecipeSelectorMenu rsm = new RecipeSelectorMenu(ChatColor.GREEN + "Select Recipe: " + RecipeUtils.ItemToString(i), recipes, possibleThings, customRecipes, possibleCustomThings);
									player.openInventory(rsm.getInventory());

								}
							}
						}
						return true;
					}
					
				}
				if(sender instanceof Player)
				{
				
					Recipe r = RecipeUtils.GetRecipeFromKey(args[0]);
					
					if(r == null)
					{
						for(CustomRecipeType crt : RecipeDisplay.CUSTOM_RECIPES)
						{
							
							if(crt.GetKey().equals(args[0]))
							{
								Player p = (Player)sender;
								p.openInventory(CreateCustomRecipeDisplay(crt).getInventory());
								return true;
							}
						
						}
						
						sender.sendMessage(ChatColor.RED + "Could not find recipe " + args[0]);
						return false;
					}
					
					//MAKE ALL OF THESE A FUNCTION WITH GENERIC PLEASE!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
					if(r instanceof ShapedRecipe)
					{
					
						Player p = (Player)sender;
						p.openInventory(CreateShapedRecipeDisplay((ShapedRecipe)r).getInventory());
						return true;
					
					}
					
					if(r instanceof ShapelessRecipe)
					{
					
						Player p = (Player)sender;
						p.openInventory(CreateShaplessRecipeDisplay((ShapelessRecipe)r).getInventory());
						return true;
					
					}
					
					if(r instanceof CookingRecipe)
					{
					
						Player p = (Player)sender;
						p.openInventory(CreateFurnaceRecipeDisplay((CookingRecipe)r).getInventory());
						return true;
					
					}
				}
				
				return false;
			}else if(args.length == 0)
			{
				if(sender instanceof Player)
				{
					Player player = (Player)sender;
					ItemStack i = player.getEquipment().getItemInMainHand();
					if(i != null)
					{
						
							List<Recipe> recipes = RecipeDisplayEvents.GetItemCreationRecipes(i);
							List<Recipe> possibleThings = RecipeDisplayEvents.GetItemUseRecipes(i);
							List<CustomRecipeType> customRecipes = RecipeDisplayEvents.GetItemCreationRecipesCustom(i);
							List<CustomRecipeType> possibleCustomThings = RecipeDisplayEvents.GetItemUseRecipesCustom(i);
							
							if(possibleThings.size() > 0 || possibleCustomThings.size() > 0)
							{
								RecipeSelectorMenu rsm = new RecipeSelectorMenu(ChatColor.GREEN + "Select Recipe: " + RecipeUtils.ItemToString(i), recipes, possibleThings, customRecipes, possibleCustomThings);
								player.openInventory(rsm.getInventory());
								return true;
							}
							
							if(((recipes.size() == 1 && customRecipes.size() == 0)))
							{
								Bukkit.getServer().dispatchCommand(player, "recipes " + ((Keyed)recipes.get(0)).getKey().toString());
							}else if(recipes.size() == 0 && customRecipes.size() == 1)
							{
								Bukkit.getServer().dispatchCommand(player, "recipes " + customRecipes.get(0).GetKey());
							
							}else {
								RecipeSelectorMenu rsm = new RecipeSelectorMenu(ChatColor.GREEN + "Select Recipe: " + RecipeUtils.ItemToString(i), recipes, possibleThings, customRecipes, possibleCustomThings);
								player.openInventory(rsm.getInventory());

							}
						
					}
				}
			}
		}
		
		return false;
	}

	public static final int TypeDisplaySlot = 18;
	public static final int[] ShapedCraftGlassSlots = new int[] {0, 1, 5, 6, 7, 8, 9, 10, 14, 16, 17, 18, 19, 23, 24, 25, 26};
	public static final int[] ShapedCraftInSlots = new int[] {2, 3, 4, 11, 12, 13, 20, 21, 22};
	public static final int CookingRecipeInSlot = 11;
	public static final int ShapedCraftOutSlot = 15;
	public static final int CookingRecipeDataSlot = 13;

	public static DisplayInventoryHolder CreateCustomRecipeDisplay(CustomRecipeType crt)
	{
		String craftName = crt.GetResult().getType().name();
		if(crt.GetResult().hasItemMeta())
		{
			if(crt.GetResult().getItemMeta().hasCustomModelData())
			{
				craftName = crt.GetResult().getItemMeta().getDisplayName().replace("" + ChatColor.WHITE, "");
			}
		}
		
		DisplayInventoryHolder inventory = new DisplayInventoryHolder(27, ChatColor.values()[(int)(Math.random() * (ChatColor.values().length - 6))] + craftName + " x " + crt.GetResult().getAmount());
		
		//If recipe has 1 input
		if(crt.GetInputs().size() == 1)
		{
			inventory.PopulateItem(DisplayInventoryHolder.GetDisplayGlass());
			
			inventory.getInventory().setItem(CookingRecipeInSlot, AddMenuActionsInfo(crt.GetInputs().get(0)));
			
			inventory.getInventory().setItem(ShapedCraftOutSlot, AddMenuActionsInfo(crt.GetResult()));
			
			if(crt.GetInfoItem() != null)
			{
				inventory.getInventory().setItem(CookingRecipeDataSlot, crt.GetInfoItem());
			}
			
		//Recipe has 2 inputs
		}else if(crt.GetInputs().size() == 2)
		{
			inventory.PopulateItem(DisplayInventoryHolder.GetDisplayGlass());
			
			inventory.getInventory().setItem(CookingRecipeInSlot, AddMenuActionsInfo(crt.GetInputs().get(0)));
			inventory.getInventory().setItem(CookingRecipeInSlot + 1, AddMenuActionsInfo(crt.GetInputs().get(1)));
			
			inventory.getInventory().setItem(ShapedCraftOutSlot + 1, AddMenuActionsInfo(crt.GetResult()));
			
			if(crt.GetInfoItem() != null)
			{
				inventory.getInventory().setItem(CookingRecipeDataSlot + 1, crt.GetInfoItem());
			}

		//Recipe has a bunch. (show like shapeless recipe)
		}else {
			inventory.PopulateItem(DisplayInventoryHolder.GetDisplayGlass(), ShapedCraftGlassSlots);
			
			int slotId = 0;
			for(ItemStack is : crt.GetInputs())
			{
				if(slotId >= ShapedCraftInSlots.length) {break;}
				inventory.getInventory().setItem(ShapedCraftInSlots[slotId++], AddMenuActionsInfo(is));
			}
			
			
			while(slotId < 9)
			{
				inventory.getInventory().setItem(ShapedCraftInSlots[slotId], new CustomItemStack("", Material.ORANGE_STAINED_GLASS_PANE, 0).GetMyItemStack());
				slotId++;
			}
			inventory.getInventory().setItem(ShapedCraftOutSlot, AddMenuActionsInfo(crt.GetResult()));
			
			if(crt.GetInfoItem() != null)
			{
				inventory.getInventory().setItem(CookingRecipeDataSlot + 1, crt.GetInfoItem());
			}

		}
		

		if(crt.GetByproducts().size() > 0)
		{
			int sl = 9 + (crt.GetInputs().size() == 2 ? ShapedCraftOutSlot + 1 : ShapedCraftOutSlot);
			
			for(ItemStack is : crt.GetByproducts())
			{
				ItemStack loreItem = is.clone();
				ItemMeta im = loreItem.getItemMeta();
				List<String> lore = new ArrayList<String>();
				lore.add(ChatColor.RED + "Additional Product");
				im.setLore(lore);
				loreItem.setItemMeta(im);
				
				inventory.getInventory().setItem(sl, AddMenuActionsInfo(loreItem));
				sl++;
				if(sl >= 27) {break;}
			}
		}
		
		
		inventory.getInventory().setItem(TypeDisplaySlot, crt.GetDisplayItem());
		
		
		return inventory;
	}
	
	public static DisplayInventoryHolder CreateShapedRecipeDisplay(ShapedRecipe recipe)
	{
		String craftName = recipe.getResult().getType().name();
		if(recipe.getResult().hasItemMeta())
		{
			if(recipe.getResult().getItemMeta().hasCustomModelData())
			{
				craftName = recipe.getResult().getItemMeta().getDisplayName().replace("" + ChatColor.WHITE, "");
			}
		}
		
		DisplayInventoryHolder inventory = new DisplayInventoryHolder(27, ChatColor.values()[(int)(Math.random() * (ChatColor.values().length - 6))] + craftName + " x " + recipe.getResult().getAmount());

		
		inventory.PopulateItem(DisplayInventoryHolder.GetDisplayGlass(), ShapedCraftGlassSlots);
		inventory.getInventory().setItem(TypeDisplaySlot, new CustomItemStack(ChatColor.GOLD + "Shaped Crafting", Material.CRAFTING_TABLE, 0).GetMyItemStack());

		int currSlot = 0;
		for(String row : recipe.getShape())
		{
			for(char c : row.toCharArray())
			{
				if(!recipe.getIngredientMap().containsKey(c)) {continue;}
				inventory.getInventory().setItem(ShapedCraftInSlots[currSlot], AddMenuActionsInfo(recipe.getIngredientMap().get(c)));
				currSlot++;
			}
			
			
			currSlot += 3 - row.length();
		}
		
		inventory.getInventory().setItem(ShapedCraftOutSlot, AddMenuActionsInfo(recipe.getResult()));
		
		return inventory;
	}
	
	public static DisplayInventoryHolder CreateShaplessRecipeDisplay(ShapelessRecipe recipe)
	{
		String craftName = recipe.getResult().getType().name();
		if(recipe.getResult().hasItemMeta())
		{
			if(recipe.getResult().getItemMeta().hasCustomModelData())
			{
				craftName = recipe.getResult().getItemMeta().getDisplayName().replace("" + ChatColor.WHITE, "");
			}
		}
		
		DisplayInventoryHolder inventory = new DisplayInventoryHolder(27, ChatColor.values()[(int)(Math.random() * (ChatColor.values().length - 6))] + craftName + " x " + recipe.getResult().getAmount());

		
		inventory.PopulateItem(DisplayInventoryHolder.GetDisplayGlass(), ShapedCraftGlassSlots);
		inventory.getInventory().setItem(TypeDisplaySlot, new CustomItemStack(ChatColor.GOLD + "Shapeless Crafting", Material.CRAFTING_TABLE, 0).GetMyItemStack());

		int currSlot = 0;
		for(ItemStack item : recipe.getIngredientList())
		{
			inventory.getInventory().setItem(ShapedCraftInSlots[currSlot], AddMenuActionsInfo(item));
			currSlot++;
		}
		
		while(currSlot < 9)
		{
			inventory.getInventory().setItem(ShapedCraftInSlots[currSlot], new CustomItemStack("", Material.ORANGE_STAINED_GLASS_PANE, 0).GetMyItemStack());
			currSlot++;
		}
		
		inventory.getInventory().setItem(ShapedCraftOutSlot, AddMenuActionsInfo(recipe.getResult()));
		
		return inventory;
	}
	
	public static DisplayInventoryHolder CreateFurnaceRecipeDisplay(@SuppressWarnings("rawtypes") CookingRecipe recipe)
	{
		String craftName = recipe.getResult().getType().name();
		if(recipe.getResult().hasItemMeta())
		{
			if(recipe.getResult().getItemMeta().hasCustomModelData())
			{
				craftName = recipe.getResult().getItemMeta().getDisplayName().replace("" + ChatColor.WHITE, "");
			}
		}
		
		DisplayInventoryHolder inventory = new DisplayInventoryHolder(27, ChatColor.values()[(int)(Math.random() * (ChatColor.values().length - 6))] + craftName + " x " + recipe.getResult().getAmount());

		
		inventory.PopulateItem(DisplayInventoryHolder.GetDisplayGlass());
		if(recipe instanceof FurnaceRecipe)
		{
			inventory.getInventory().setItem(TypeDisplaySlot, new CustomItemStack(ChatColor.GOLD + "Smelting", Material.FURNACE, 0).GetMyItemStack());
		}else if(recipe instanceof BlastingRecipe)
		{
			inventory.getInventory().setItem(TypeDisplaySlot, new CustomItemStack(ChatColor.GOLD + "Blasting", Material.BLAST_FURNACE, 0).GetMyItemStack());
		}else if(recipe instanceof CampfireRecipe)
		{
			inventory.getInventory().setItem(TypeDisplaySlot, new CustomItemStack(ChatColor.GOLD + "Cooking", Material.CAMPFIRE, 0).GetMyItemStack());
		}else if(recipe instanceof SmokingRecipe)
		{
			inventory.getInventory().setItem(TypeDisplaySlot, new CustomItemStack(ChatColor.GOLD + "Smoking", Material.SMOKER, 0).GetMyItemStack());
		}
				
		inventory.getInventory().setItem(CookingRecipeInSlot, AddMenuActionsInfo(recipe.getInput()));
		inventory.getInventory().setItem(ShapedCraftOutSlot, AddMenuActionsInfo(recipe.getResult()));
		
		int estSeconds = (int)Math.round(((double)recipe.getCookingTime())/20d);
		
		ItemStack dataItem = new ItemStack(Material.ORANGE_STAINED_GLASS_PANE, estSeconds);
		
		ItemMeta dataMeta = dataItem.getItemMeta();
		dataMeta.setDisplayName(ChatColor.GREEN + "" + ChatColor.MAGIC + "--" + ChatColor.RESET + "" + ChatColor.GREEN + " INFO " + ChatColor.MAGIC + "--");
		
		
		dataMeta.addEnchant(Enchantment.LUCK, 1, true);
		dataMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		
		ArrayList<String> lore = new ArrayList<String>();
		lore.add(ChatColor.RED + "Cook Time: " + estSeconds + "s");
		lore.add(ChatColor.YELLOW + "XP: " + recipe.getExperience());
		
		dataMeta.setLore(lore);
		dataItem.setItemMeta(dataMeta);
		
		inventory.getInventory().setItem(CookingRecipeDataSlot, dataItem);
		
		return inventory;
	}

	//Adds the (Right-Click to view all recipes, Left-Click to see recipe) text.
	public static ItemStack AddMenuActionsInfo(ItemStack is)
	{
		if(is == null) {return null;}
		
		ItemMeta im = is.getItemMeta();
		List<String> lore = im.getLore();
		if(!im.hasLore())
		{
			lore = new ArrayList<String>();
		}
		
		lore.add(ChatColor.DARK_GRAY + "LMB - View Recipes");
		lore.add(ChatColor.DARK_GRAY + "RMB - View Recipe List");
		
		im.setLore(lore);
		ItemStack ret = is.clone();
		ret.setItemMeta(im);
		return ret;
	}
	
}