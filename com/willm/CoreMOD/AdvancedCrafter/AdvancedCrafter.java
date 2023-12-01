package com.willm.CoreMOD.AdvancedCrafter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import com.willm.CoreMOD.Main;
import com.willm.CoreMOD.MyItems;
import com.willm.ModAPI.Utils;
import com.willm.ModAPI.Items.CustomItemStack;
import com.willm.ModAPI.RecipeDisplay.CustomRecipeType;
import com.willm.ModAPI.RecipeDisplay.RecipeDisplay;

public class AdvancedCrafter {

	private static HashMap<Inventory, Block> openInventories = new HashMap<Inventory, Block>();
	
	public static List<AdvancedCrafterRecipe> recipes = new ArrayList<AdvancedCrafterRecipe>();
	
	public static void OpenInventory(Player p, Block b)
	{
		Inventory newInv = Bukkit.createInventory(p, 5 * 9, "Industrial Crafter");
		p.openInventory(newInv);
		
		openInventories.put(newInv, b);
	}
	
	public static void RegisterRecipes()
	{

		recipes.add(new AdvancedCrafterRecipe(MyItems.centrifuge_tops[7].GetAmountClone(1),
			new AdvancedCrafterRecipeInput(MyItems.gearshift), new AdvancedCrafterRecipeInput(MyItems.tubing), new AdvancedCrafterRecipeInput(MyItems.tubing), new AdvancedCrafterRecipeInput(MyItems.tubing), new AdvancedCrafterRecipeInput(Material.COMPARATOR),
			new AdvancedCrafterRecipeInput(MyItems.tubing), new AdvancedCrafterRecipeInput(MyItems.redstone_dish), new AdvancedCrafterRecipeInput(MyItems.redstone_dish), new AdvancedCrafterRecipeInput(MyItems.redstone_dish), new AdvancedCrafterRecipeInput(MyItems.tubing),
			new AdvancedCrafterRecipeInput(MyItems.tubing), new AdvancedCrafterRecipeInput(MyItems.redstone_dish), new AdvancedCrafterRecipeInput(MyItems.centrifuge_tops[6]), new AdvancedCrafterRecipeInput(MyItems.redstone_dish), new AdvancedCrafterRecipeInput(MyItems.tubing),
			new AdvancedCrafterRecipeInput(MyItems.tubing), new AdvancedCrafterRecipeInput(MyItems.redstone_dish), new AdvancedCrafterRecipeInput(MyItems.redstone_dish), new AdvancedCrafterRecipeInput(MyItems.redstone_dish), new AdvancedCrafterRecipeInput(MyItems.tubing),
			new AdvancedCrafterRecipeInput(Material.COMPARATOR), new AdvancedCrafterRecipeInput(MyItems.tubing), new AdvancedCrafterRecipeInput(MyItems.tubing), new AdvancedCrafterRecipeInput(MyItems.tubing), new AdvancedCrafterRecipeInput(MyItems.gearshift)
		));
		
		RecipeDisplay.CUSTOM_RECIPES.add(new CustomRecipeType("Industrial Crafting", MyItems.advanced_crafter.GetMyItemStack(), "advanced_crafting_redstone_centrifuge", Main.INSTANCE, 
				
				new CustomItemStack(ChatColor.RED + "-->", MyItems.advanced_crafter.getType(), MyItems.advanced_crafter.getCustomModelData()).AddEnchant(Enchantment.LUCK, 1, true).AddFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS).GetMyItemStack(),
				
				MyItems.centrifuge_tops[7].GetMyItemStack(), new CustomItemStack(ChatColor.YELLOW + "Click To View Pattern...", Material.CRAFTING_TABLE, 1523).AddLoreLine("0").GetMyItemStack()));
	}

	public static void GenCraftingView(Inventory i, int recipe)
	{
		int iv = 0, rp = 0;
		while(iv < i.getSize())
		{
			boolean foundgp = false;
			for(int gp : glassPaneSlots)
			{
				if(gp == iv)
				{
					i.setItem(iv, new CustomItemStack("", Material.BLACK_STAINED_GLASS_PANE, 0).GetMyItemStack());
					foundgp = true;
					break;
				}
			}
			
			if(!foundgp)
			{
				i.setItem(iv, recipes.get(recipe).inputs[rp].GetMyCheck());
				rp++;
			}
			
			iv++;
		}
	}

	private static final Integer[] glassPaneSlots = new Integer[] {
		0, 1, 7, 8,
		9, 10, 16, 17,
		18, 19, 25, 26,
		27, 28, 34, 35,
		36, 37, 43, 44
	};
	
	private static final Integer[] craftAnimation = new Integer[] {
			2, 3, 4, 5, 6, 
			15, 24, 33, 42,
			41, 40, 39, 38,
			29, 20, 11, 12,
			14, 22, 30, 32,
			13, 21, 23, 31
		};
	
	private static final Integer[] craftSetAnimation = new Integer[] {
			4, 13, 20, 21, 40, 31, 24, 23
		};
	
	public static void DoCraftAnimation(Inventory i, ItemStack result)
	{
		int iv = 0;
		for(final Integer integer : craftAnimation)
		{
			craftAnimThread(i, integer, (iv++) * 5, null, Sound.ENTITY_ITEM_PICKUP);
		}
		
		iv += 2;
		
		for(final Integer integer : craftSetAnimation)
		{
			craftAnimThread(i, integer, (iv++) * 5, new ItemStack(Material.BLUE_STAINED_GLASS_PANE), Sound.BLOCK_CHAIN_BREAK);
		}
		
		craftAnimThread(i, 22, (iv++) * 5, new ItemStack(Material.GRAY_CONCRETE), Sound.ENTITY_EXPERIENCE_ORB_PICKUP);
		
		iv += 2;
		
		craftAnimThread(i, 22, (iv++) * 5, result, "core_mod.steam");
		
		iv += craftSetAnimation.length;
		
		for(final Integer integer : craftSetAnimation)
		{
			craftAnimThread(i, integer, (iv--) * 5, null, "core_mod.kiln_spin");
		}
		
		iv += craftSetAnimation.length;
		iv += 1; 
		
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.INSTANCE, new Runnable() {

			@Override
			public void run() {
				for(Integer ivd : glassPaneSlots)
				{
					i.setItem(ivd, new CustomItemStack("", Material.RED_STAINED_GLASS_PANE, 0).GetAmountClone(1));
				}
			}
			
		}, iv * 5);
	}
	
	private static void craftAnimThread(final Inventory i, final int nInt, int time, final ItemStack set, final Sound sound)
	{
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.INSTANCE, new Runnable() {

			@Override
			public void run() {
				System.out.println("ANIM - " + nInt);
				i.setItem(nInt, set);
				
				if(i.getViewers().size() > 0)
				{
					if(i.getViewers().get(0) instanceof Player)
					{
						Player p = (Player)i.getViewers().get(0);
						p.playSound(p, sound, 1f, 1f);
					}
				}
			}
			
		}, time);
	}
	
	private static void craftAnimThread(final Inventory i, final int nInt, int time, final ItemStack set, final String sound)
	{
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.INSTANCE, new Runnable() {

			@Override
			public void run() {
				System.out.println("ANIM - " + nInt);
				i.setItem(nInt, set);
				
				if(i.getViewers().size() > 0)
				{
					if(i.getViewers().get(0) instanceof Player)
					{
						Player p = (Player)i.getViewers().get(0);
						Utils.PlayCustomSound(sound, p.getLocation());
					}
				}
			}
			
		}, time);
	}
	
	public static void Tick()
	{
		for(Entry<Inventory, Block> OpenInventory : openInventories.entrySet())
		{
			if(OpenInventory.getValue().getType() == Material.AIR || OpenInventory.getKey().getViewers().size() == 0) {
				
				for(Integer i : craftAnimation)
				{
					if(OpenInventory.getKey().getItem(i) != null)
					{
						OpenInventory.getValue().getWorld().dropItemNaturally(OpenInventory.getValue().getLocation(), OpenInventory.getKey().getItem(i));
					}
				}
				
				openInventories.remove(OpenInventory.getKey());
				return;
			}
			
			if(OpenInventory.getKey().getItem(0) == null || OpenInventory.getKey().getItem(0).getType() != Material.GREEN_STAINED_GLASS_PANE)
			{
				for(Integer i : glassPaneSlots)
				{
					OpenInventory.getKey().setItem(i, new CustomItemStack("", Material.RED_STAINED_GLASS_PANE, 0).GetAmountClone(1));
				}
			}
			
			if(OpenInventory.getKey().getItem(42) != null)
			{
				for(AdvancedCrafterRecipe acr : recipes)
				{
					if(acr.CheckRecipe(OpenInventory.getKey()))
					{
						System.out.println("MADE IT");
						
						for(Integer i : glassPaneSlots)
						{
							OpenInventory.getKey().setItem(i, new CustomItemStack("", Material.GREEN_STAINED_GLASS_PANE, 0).GetAmountClone(1));
						}
						
						DoCraftAnimation(OpenInventory.getKey(), acr.GetResult());
						
						return;
					}
				}
			}
		}
	}
	
}
