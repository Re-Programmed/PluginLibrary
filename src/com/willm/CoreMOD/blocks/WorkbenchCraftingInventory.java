package com.willm.CoreMOD.blocks;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import com.willm.ModAPI.Items.CustomItemStack;

public class WorkbenchCraftingInventory implements InventoryHolder {
	
	public static String GetName(){return ChatColor.GOLD + "Workbench Crafting";}

	Inventory myInventory;
		
	private final static ItemStack displayGlass = new CustomItemStack("", Material.GRAY_STAINED_GLASS_PANE, 0).GetMyItemStack();
	
	 public WorkbenchCraftingInventory()
	 {
		 myInventory = Bukkit.createInventory(this, 54, "Workbench (Crafting)");
	 }
	
	@Override
	public Inventory getInventory() {
		return myInventory;
	}
	
	private final static List<Integer> craftingInputSlots = List.of(2,3,4,11,12,13,20,21,22);
	private final static int craftingOutputSlot = 15;
	
	private final static int inventoryViewSlotStart = 57 - 21;
	
	public void PopulateItems()
	{
		for(int i = 0; i < myInventory.getSize(); i++)
		{
			if(craftingInputSlots.contains(i) || craftingOutputSlot == i)
			{
				continue;
			}
			
			if(inventoryViewSlotStart <= i) {break;}
			
			myInventory.setItem(i, displayGlass);
		}
	}

	//Adds all the storage workbenches.
	public void PopulateStorageSources(Block b)
	{
		WorkbenchSystem system = new WorkbenchSystem(b);
		system.GenerateInventory();
		int i = inventoryViewSlotStart;
		for(ItemStack is : system.getInventory().getStorageContents())
		{
			if(is != null && is.hasItemMeta() && is.getItemMeta().hasLore() && is.getItemMeta().getLore().get(1).replace(ChatColor.DARK_GRAY + "", "").equals(WorkbenchType.STORAGE_BENCH.toString()))
			{
				myInventory.setItem(i, is);
				i++;
				if(i >= 53) {break;}
			}

		}
		
		myInventory.setItem(53, new CustomItemStack(GetName(), Material.WARPED_TRAPDOOR, 60002).AddEnchant(Enchantment.LUCK, 1, true).AddFlags(ItemFlag.HIDE_ENCHANTS).GetMyItemStack());
	}
}
