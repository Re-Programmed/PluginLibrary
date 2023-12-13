package com.willm.CoreMOD.blocks;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Keyed;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Chest;
import org.bukkit.block.DoubleChest;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.CookingRecipe;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import com.willm.ModAPI.Items.CustomItemStack;
import com.willm.ModAPI.RecipeDisplay.RecipeUtils;

public class StorageKeyCommand implements CommandExecutor {

	private static CustomItemStack storageTerminalIndicator = new CustomItemStack(ChatColor.GOLD + "Storage Terminal", Material.CHEST, 11);
	
	private static int[] populateGlass = new int[] {
			0, 1, 2, 3, 4, 5, 6, 7, 8,
			9, 13, 17,
			18, 26,
			27, 31, 35,
			36, 37, 38, 39, 40, 41, 42, 43, 44
			
	};
	
	private static ItemStack black_glass = new CustomItemStack("", Material.BLUE_STAINED_GLASS_PANE, 0).GetMyItemStack();
	
	public static void AttemptRecipe(String recipeKey, Player p, Block b)
	{
		Recipe r = RecipeUtils.GetRecipeFromKey(recipeKey);
		
		Inventory displayResult = Bukkit.createInventory(p, 45, "Attempting Recipe");
		
		for(int i : populateGlass)
		{
			displayResult.setItem(i, black_glass);
		}
		
		displayResult.setItem(22, new CustomItemStack("Confirm", Material.GREEN_CONCRETE, 0).AddEnchant(Enchantment.LUCK, 0, true).AddFlags(ItemFlag.HIDE_ENCHANTS).AddLoreLine(recipeKey).AddLoreLine(b.getX() + ", " + b.getY() + ", " + b.getZ()).GetMyItemStack());
		
WorkbenchSystem ws = new WorkbenchSystem(b);
		
		List<ItemStack> allItems = new ArrayList<ItemStack>();
		
		for(Entry<Block, Workbench> wb : ws.Benches.entrySet())
		{
			if(wb.getValue().GetType() == WorkbenchType.STORAGE_BENCH)
			{
				if(wb.getKey().getRelative(BlockFace.UP).getState() instanceof Chest)
				{
					Chest c = (Chest)wb.getKey().getRelative(BlockFace.UP).getState();
					
					if(c.getInventory().getHolder() instanceof DoubleChest)
					{
						DoubleChest dc = (DoubleChest)c.getInventory().getHolder();
						
						for(ItemStack is : dc.getInventory())
						{
							if(is != null)
							{
								is = is.clone();
								
								ItemMeta im = is.getItemMeta();
								List<String> lore = im.hasLore() ? im.getLore() : new ArrayList<String>();
								
								lore.add(ChatColor.GREEN + "" + c.getLocation().getBlockX() + ", " + c.getLocation().getBlockY() + ", " + c.getLocation().getBlockZ());
								
								im.setLore(lore);
								is.setItemMeta(im);
								
								allItems.add(is);
							}
						}
						
					}else {
						for(ItemStack is : c.getBlockInventory())
						{
							if(is != null)
							{
								is = is.clone();
								
								ItemMeta im = is.getItemMeta();
								List<String> lore = im.hasLore() ? im.getLore() : new ArrayList<String>();
								
								lore.add(ChatColor.GREEN + "" + c.getLocation().getBlockX() + ", " + c.getLocation().getBlockY() + ", " + c.getLocation().getBlockZ());
								
								im.setLore(lore);
								is.setItemMeta(im);
								
								allItems.add(is);
							}
						}
					}
				}
			}
		}
		
		int currSlot = 9;
		int currDispSlot = 13;
		HashMap<ItemStack, Integer> usedStorage = new HashMap<ItemStack, Integer>();
		
	    if(r instanceof ShapelessRecipe)
		{
			ShapelessRecipe sr = (ShapelessRecipe)r;
			for(ItemStack in : sr.getIngredientList())
			{
				currSlot++;
				currDispSlot++;
				if(currSlot == 13)
				{
					currSlot = 19;
				}
				
				if(currSlot == 22)
				{
					currSlot = 28;
				}
				
				if(currDispSlot == 17)
				{
					currDispSlot = 23;
				}
				
				if(currDispSlot == 26)
				{
					currDispSlot = 32;
				}
								
				if(in == null) {continue;}
				
				displayResult.setItem(currSlot, in);
									
				boolean found = false;
				
				for(ItemStack is : allItems)
				{
					if(usedStorage.containsKey(is) && usedStorage.get(is) >= is.getAmount()) {continue;}
					
					if(in.hasItemMeta() && in.getItemMeta().hasCustomModelData())
					{
						if(is.hasItemMeta() && is.getItemMeta().hasCustomModelData())
						{
							if(is.getType() == in.getType() && is.getItemMeta().getCustomModelData() == in.getItemMeta().getCustomModelData())
							{
								ItemStack isc = is.clone();
								isc.setAmount(1);
								displayResult.setItem(currDispSlot, isc);
								
								if(usedStorage.containsKey(is))
								{
									usedStorage.put(is, usedStorage.get(is) + 1);
								}else {
									usedStorage.put(is, 1);
								}
								
								found = true;
								break;
							}
						}
					}else {
						if(is.getType() == in.getType())
						{
							ItemStack isc = is.clone();
							isc.setAmount(1);
							displayResult.setItem(currDispSlot, isc);
							
							if(usedStorage.containsKey(is))
							{
								usedStorage.put(is, usedStorage.get(is) + 1);
							}else {
								usedStorage.put(is, 1);
							}
							
							found = true;
							break;
						}
					}
				}
				
				if(!found)
				{
					displayResult.setItem(22, new CustomItemStack("Unavailable", Material.RED_CONCRETE, 0).AddLoreLine(recipeKey).AddLoreLine(b.getX() + ", " + b.getY() + ", " + b.getZ()).AddLoreLine(ChatColor.RED + "You do not have all the items to perform this recipe.").AddEnchant(Enchantment.LUCK, 0, true).AddFlags(ItemFlag.HIDE_ENCHANTS).GetMyItemStack());
					displayResult.setItem(currDispSlot, new CustomItemStack(ChatColor.RED + "Item Unavailable", Material.RED_STAINED_GLASS_PANE, 0).AddLoreLine(ChatColor.RED + "This item is not in your workbench system.").GetMyItemStack());
				}
			}
		}else if(r instanceof ShapedRecipe)
		{
			ShapedRecipe sr = (ShapedRecipe)r;
			for(String s : sr.getShape())
			{
				for(int i = 0; i < s.length(); i++)
				{
					currSlot++;
					currDispSlot++;
					if(s.length() == 3)
					{
						if(currSlot == 13)
						{
							currSlot = 19;
						}
						
						if(currSlot == 22)
						{
							currSlot = 28;
						}
						
						if(currDispSlot == 17)
						{
							currDispSlot = 23;
						}
						
						if(currDispSlot == 26)
						{
							currDispSlot = 32;
						}
					}
					
					if(s.length() == 2)
					{
						if(currSlot == 12)
						{
							currSlot = 19;
						}
						
						if(currSlot == 21)
						{
							currSlot = 28;
						}
						
						if(currDispSlot == 16)
						{
							currDispSlot = 23;
						}
						
						if(currDispSlot == 25)
						{
							currDispSlot = 32;
						}
					}
					
					if(s.length() == 1)
					{
						if(currSlot == 11)
						{
							currSlot = 19;
						}
						
						if(currSlot == 20)
						{
							currSlot = 28;
						}
						
						if(currDispSlot == 15)
						{
							currDispSlot = 23;
						}
						
						if(currDispSlot == 24)
						{
							currDispSlot = 32;
						}
					}

					char c = s.charAt(i);
				
					if(c == ' ') {continue;}
					
					ItemStack in = sr.getIngredientMap().get(c);
					
					if(in == null) {continue;}
					
					displayResult.setItem(currSlot, in);
										
					
					boolean found = false;
					
					for(ItemStack is : allItems)
					{
						if(usedStorage.containsKey(is) && usedStorage.get(is) >= is.getAmount()) {continue;}
						
						if(in.hasItemMeta() && in.getItemMeta().hasCustomModelData())
						{
							if(is.hasItemMeta() && is.getItemMeta().hasCustomModelData())
							{
								if(is.getType() == in.getType() && is.getItemMeta().getCustomModelData() == in.getItemMeta().getCustomModelData())
								{
									ItemStack isc = is.clone();
									isc.setAmount(1);
									displayResult.setItem(currDispSlot, isc);
									
									if(usedStorage.containsKey(is))
									{
										usedStorage.put(is, usedStorage.get(is) + 1);
									}else {
										usedStorage.put(is, 1);
									}
									
									found = true;
									break;
								}
							}

						}else {
							if(is.getType() == in.getType())
							{
								ItemStack isc = is.clone();
								isc.setAmount(1);
								displayResult.setItem(currDispSlot, isc);
								
								if(usedStorage.containsKey(is))
								{
									usedStorage.put(is, usedStorage.get(is) + 1);
								}else {
									usedStorage.put(is, 1);
								}
								
								found = true;
								break;
							}
						}
					}
					
					if(!found)
					{
						displayResult.setItem(22, new CustomItemStack("Unavailable", Material.RED_CONCRETE, 0).AddLoreLine(recipeKey).AddLoreLine(b.getX() + ", " + b.getY() + ", " + b.getZ()).AddLoreLine(ChatColor.RED + "You do not have all the items to perform this recipe.").AddEnchant(Enchantment.LUCK, 0, true).AddFlags(ItemFlag.HIDE_ENCHANTS).GetMyItemStack());
						displayResult.setItem(currDispSlot, new CustomItemStack(ChatColor.RED + "Item Unavailable", Material.RED_STAINED_GLASS_PANE, 0).AddLoreLine(ChatColor.RED + "This item is not in your workbench system.").GetMyItemStack());
					}
							
				}
			}
			
			
		}
		
		displayResult.setItem(13, r.getResult());
		
		p.openInventory(displayResult);
	}
	
	private static boolean listStringHas(List<String> s, String sc)
	{
		for(String scheck : s)
		{
			if(scheck.equalsIgnoreCase(sc))
			{
				return true;
			}
		}
		
		return false;
	}
	
	public static void CreateCraftingSearchResultInventory(Player p, Block b, String search)
	{
		WorkbenchSystem ws = new WorkbenchSystem(b);
		
		List<ItemStack> allItems = new ArrayList<ItemStack>();
		
		for(Entry<Block, Workbench> wb : ws.Benches.entrySet())
		{
			if(wb.getValue().GetType() == WorkbenchType.STORAGE_BENCH)
			{
				if(wb.getKey().getRelative(BlockFace.UP).getState() instanceof Chest)
				{
					Chest c = (Chest)wb.getKey().getRelative(BlockFace.UP).getState();
					
					if(c.getInventory().getHolder() instanceof DoubleChest)
					{
						DoubleChest dc = (DoubleChest)c.getInventory().getHolder();
						
						for(ItemStack is : dc.getInventory())
						{
							if(is != null)
							{
								is = is.clone();
								
								ItemMeta im = is.getItemMeta();
								List<String> lore = im.hasLore() ? im.getLore() : new ArrayList<String>();
								
								lore.add(ChatColor.GREEN + "" + c.getLocation().getBlockX() + ", " + c.getLocation().getBlockY() + ", " + c.getLocation().getBlockZ());
								
								im.setLore(lore);
								is.setItemMeta(im);
								
								allItems.add(is);
							}
						}
						
					}else {
						for(ItemStack is : c.getBlockInventory())
						{
							if(is != null)
							{
								is = is.clone();
								
								ItemMeta im = is.getItemMeta();
								List<String> lore = im.hasLore() ? im.getLore() : new ArrayList<String>();
								
								lore.add(ChatColor.GREEN + "" + c.getLocation().getBlockX() + ", " + c.getLocation().getBlockY() + ", " + c.getLocation().getBlockZ());
								
								im.setLore(lore);
								is.setItemMeta(im);
								
								allItems.add(is);
							}
						}
					}
				}
			}
		}
		
		List<String> allItemsNames = new ArrayList<String>();
		
		for(ItemStack is : allItems)
		{
			allItemsNames.add((is.hasItemMeta() && is.getItemMeta().hasDisplayName()) ? is.getItemMeta().getDisplayName().toLowerCase().replace(ChatColor.WHITE + "", "").replace(" ", "") : is.getType().toString().toLowerCase().replace("_", ""));
		}
		
		int maxItems = 45;
		
		Inventory searchResultDisplay = Bukkit.createInventory(p, maxItems + 9, "Nearby Crafting Search - " + search);
		searchResultDisplay.setItem(53, new CustomItemStack(ChatColor.RED + "Crafting Workbench Search", Material.WARPED_TRAPDOOR, 60002).AddLoreLine(b.getX() + ", " + b.getY() + ", " + b.getZ()).GetMyItemStack());
		searchResultDisplay.setItem(49, new CustomItemStack("Search", Material.HOPPER, 0).AddEnchant(Enchantment.LUCK, 1, true).AddFlags(ItemFlag.HIDE_ENCHANTS).GetMyItemStack());

		int i = 0;
	recipe_loop:
		for(Recipe r : RecipeUtils.GetAddedRecipes())
		{
			String rname = ((Keyed)r).getKey().toString();
			
			String itemRes = (r.getResult().hasItemMeta() && r.getResult().getItemMeta().hasDisplayName()) ? r.getResult().getItemMeta().getDisplayName().toLowerCase().replace(" ", "").replace(ChatColor.WHITE + "", "") : r.getResult().getType().toString().toLowerCase().replace("_", "");
			if(itemRes.toLowerCase().replace("_", "").startsWith(search))
			{
				ItemStack is = r.getResult();
				ItemMeta im = is.getItemMeta();
				im.setDisplayName(ChatColor.GOLD + (im.hasDisplayName() ? im.getDisplayName() : RecipeUtils.upperCaseAllFirstCharacters(is.getType().toString().toLowerCase().replace('_', ' '))));
				
				List<String> lore = new ArrayList<String>();
				
				lore.add(rname);
				
				if(r instanceof ShapedRecipe)
				{
					ShapedRecipe sr = (ShapedRecipe)r;
					for(ItemStack isc : sr.getIngredientMap().values())
					{
						if(isc == null) {continue;}
						
						String appString = ((isc.hasItemMeta() && isc.getItemMeta().hasDisplayName()) ? isc.getItemMeta().getDisplayName().replace(ChatColor.WHITE + "", "") : RecipeUtils.upperCaseAllFirstCharacters(isc.getType().toString().toLowerCase().replace("_", " ")));

						lore.add(ChatColor.GREEN + "" + appString);	
					}
				}else if(r instanceof ShapelessRecipe)
				{
					ShapelessRecipe sr = (ShapelessRecipe)r;
					for(ItemStack isc : sr.getIngredientList())
					{
						
						if(isc == null) {continue;}
						String appString = ((isc.hasItemMeta() && isc.getItemMeta().hasDisplayName()) ? isc.getItemMeta().getDisplayName().replace(ChatColor.WHITE + "", "") : RecipeUtils.upperCaseAllFirstCharacters(isc.getType().toString().toLowerCase().replace("_", " ")));

						
						lore.add(ChatColor.GREEN + "" + appString);
					}
				}else {
					continue recipe_loop;
				}
				
				im.setLore(lore);
				is.setItemMeta(im);
				
				searchResultDisplay.setItem(i++, is);
				if(i > 44)
				{
					break;
				}
			}
		}
		
		p.openInventory(searchResultDisplay);
	}
	
	public static void CreateSearchResultInventory(Player p, ArmorStand as, String search, boolean allowContinuedSearches)
	{
		int maxItems = 64 * 45;
		
		Inventory searchResultDisplay = Bukkit.createInventory(p, maxItems/64 + 9, "Nearby Inventory Search - " + search);
		
		
		storageTerminalIndicator.SetLore(new ArrayList<String>());
		storageTerminalIndicator.AddLoreLine(as.getLocation().getBlockX() + ", " + as.getLocation().getBlockY() + ", " + as.getLocation().getBlockZ());
		
		searchResultDisplay.setItem(53, storageTerminalIndicator.GetMyItemStack());
		
		WorkbenchSystem system = new WorkbenchSystem(as.getLocation().getBlock());
		for(Entry<Block, Workbench> entry : system.Benches.entrySet())
		{
			if(maxItems == 0)
			{
				break;
			}
			
			if(entry.getValue().GetType() == WorkbenchType.STORAGE_BENCH)
			{
				if(entry.getKey().getRelative(BlockFace.UP).getType() != Material.AIR && entry.getKey().getRelative(BlockFace.UP).getState() instanceof Chest)
				{
					Chest c = ((Chest)entry.getKey().getRelative(BlockFace.UP).getState());
					Inventory checkInv = c.getBlockInventory();
					
					if(c.getInventory().getHolder() instanceof DoubleChest)
					{
						checkInv = ((DoubleChest)c.getInventory().getHolder()).getInventory();
					}
					
					for(ItemStack is : checkInv)
					{
						if(is == null) {continue;}
						
						if(is.hasItemMeta() && is.getItemMeta().hasDisplayName())
						{
							if(is.getItemMeta().getDisplayName().toLowerCase().replace(" ", "").replace(ChatColor.WHITE + "", "").startsWith(search))
							{
								if(is.getAmount() > maxItems)
								{
									continue;
								}
								
								is = is.clone();
								
								ItemMeta im = is.getItemMeta();
								List<String> lore = im.hasLore() ? im.getLore() : new ArrayList<String>();
								
								lore.add(ChatColor.GREEN + "" + c.getLocation().getBlockX() + ", " + c.getLocation().getBlockY() + ", " + c.getLocation().getBlockZ());
								
								im.setLore(lore);
								is.setItemMeta(im);
																						
								searchResultDisplay.addItem(is);
								
								maxItems -= is.getAmount();
							}
						}else {
							if(is.getType().toString().toLowerCase().replace("_", "").startsWith(search))
							{
								if(is.getAmount() > maxItems)
								{
									continue;
								}
								
								is = is.clone();
								
								ItemMeta im = is.getItemMeta();
								List<String> lore = im.hasLore() ? im.getLore() : new ArrayList<String>();
								
								lore.add(ChatColor.GREEN + "" + c.getLocation().getBlockX() + ", " + c.getLocation().getBlockY() + ", " + c.getLocation().getBlockZ());
								
								im.setLore(lore);
								is.setItemMeta(im);
																						
								searchResultDisplay.addItem(is);
								
								maxItems -= is.getAmount();
							}
						}
					}
				}
			}
		}
		
		if(allowContinuedSearches)
		{
			searchResultDisplay.setItem(49, new CustomItemStack("Search", Material.HOPPER, 0).AddLoreLine(ChatColor.BLUE + "Click on this item with the item to search for.").AddEnchant(Enchantment.LUCK, 1, true).AddFlags(ItemFlag.HIDE_ENCHANTS).GetMyItemStack());
		}
		
		p.openInventory(searchResultDisplay);
	}
	
	public static boolean ExecuteCommand(CommandSender sender, boolean ignoreStandingCheck, String label, String... args)
	{
		if(label.equalsIgnoreCase("storagekey") || label.equalsIgnoreCase("sk"))
		{

			if(sender instanceof Player)
			{
				Player p = (Player)sender;

				if(!ignoreStandingCheck)
				{
					if(WorkbenchItemRegistry.crafting_workbench.getRelatedBlock().CheckForCustomBlock(p.getLocation().getBlock()))
					{
						if(args.length > 0)
						{
							String search = "";
							for(String s : args)
							{
								search = search + s;
							}
							
							CreateCraftingSearchResultInventory(p, p.getLocation().getBlock(), search);
							
							sender.sendMessage(ChatColor.GREEN + "Opened crafting network. (Query: " + search + ")");
							return true;
						}else {
							sender.sendMessage(ChatColor.RED + "Please enter what you would like to search for.");
							return false;
						}
						
					}
					
					ItemStack heldItem = p.getEquipment().getItemInMainHand();
					if(!WorkbenchItemRegistry.storage_key.CheckForCustomItem(heldItem))
					{
						
						if(!WorkbenchItemRegistry.storage_terminal.getRelatedBlock().CheckForCustomBlock(p.getLocation().getBlock()))
						{
							p.sendMessage(ChatColor.RED + "You must have a storage key, be standing on a storage terminal, or be standing on a crafting workbench.");
							return false;
						}
					}
				}
				
				for(Entity e : p.getNearbyEntities(40f, 40f, 40f))
				{

					if(e instanceof ArmorStand)
					{
						ArmorStand as = (ArmorStand)e;
						
						
						if(WorkbenchItemRegistry.storage_terminal.getRelatedBlock().CheckForCustomBlock(as.getLocation().getBlock()))
						{		
							if(args.length > 0)
							{
								String search = "";
								for(String s : args)
								{
									search = search + s;
								}
								
								CreateSearchResultInventory(p, as, search, true);
								
								sender.sendMessage(ChatColor.GREEN + "Opened nearby storage network. (Query: " + search + ")");
								return true;
							}
							
							((Workbench)WorkbenchItemRegistry.storage_terminal.getRelatedBlock()).OnInteract(as.getLocation().getBlock(), p);
							sender.sendMessage(ChatColor.GREEN + "Opened nearby storage network.");
							return true;
						}
					}
				}
			}
		}
		
		
		return false;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		return ExecuteCommand(sender, false, label, args);
	}

	
	
}
