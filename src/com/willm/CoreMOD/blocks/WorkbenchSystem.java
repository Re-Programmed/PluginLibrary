package com.willm.CoreMOD.blocks;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Chest;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import com.willm.CoreMOD.BlockEvents;
import com.willm.ModAPI.Main;
import com.willm.ModAPI.Blocks.CustomBlock;
import com.willm.ModAPI.Items.CustomItemStack;

public class WorkbenchSystem implements InventoryHolder {

	public HashMap<Block, Workbench> Benches = new HashMap<Block, Workbench>();
	
	private Inventory displayMenu;
	
	public static String GetName(){return ChatColor.DARK_GREEN + "Workbench Menu";}
	
	public WorkbenchSystem(Block b)
	{
		displayMenu = Bukkit.createInventory(this, 54, GetName());
		displayMenu.setItem(53, new CustomItemStack(GetName(), WorkbenchItemRegistry.wooden_top_workbench.getType(), WorkbenchItemRegistry.wooden_top_workbench.getCustomModelData()).AddEnchant(Enchantment.LUCK, 1, true).AddFlags(ItemFlag.HIDE_ENCHANTS).GetMyItemStack());
		PopulateBench(Benches, b);
	}
	
	public void GenerateInventory()
	{
		int i = 0;
		for(Workbench w : Benches.values())
		{
			if(w.GetType() != WorkbenchType.NONE)
			{
				Location location = Benches.keySet().toArray(new Block[0])[i].getLocation();
				String displayName = w.GetType().DisplayItem.getName();
				ItemStack displayType = new ItemStack(w.GetType().DisplayItem.getType());
				if(w.GetType() == WorkbenchType.STORAGE_BENCH)
				{
					if(location.getBlock().getRelative(BlockFace.UP).getState() instanceof Chest)
					{
						Chest c = (Chest)location.getBlock().getRelative(BlockFace.UP).getState();
						if(c.getCustomName() != null)
						{
							displayName = ChatColor.GREEN + c.getCustomName();
						}else {
							displayName = ChatColor.GREEN + "Unnamed Storage";
						}
						
						if(c.getBlockInventory().getStorageContents().length > 0)
						{
							for(ItemStack is : c.getBlockInventory().getStorageContents())
							{
								if(is != null)
								{
									displayType = is;
									break;
								}
							}
						}
					}
				}
				
				if(w.GetType() == WorkbenchType.MACHINE_WORKBENCH)
				{
					if(location.getBlock().getRelative(BlockFace.UP).getType() == Material.DISPENSER)
					{
						for(CustomBlock cb : Main.CustomBlockRegistry)
						{
							if(cb.getMachineTemplate() != null)
							{
								if(cb.CheckForCustomBlock(location.getBlock().getRelative(BlockFace.UP)))
								{
									displayName = ChatColor.BLUE + cb.getName().replace(ChatColor.WHITE + "", "");
									displayType = new CustomItemStack(displayName, cb.getDisplayMaterial(), cb.getDisplayCustomModelData()).GetMyItemStack();
									break;
								}
							}
						}
					}
				}
				
				if(w.GetType() == WorkbenchType.REDSTONE_TRIGGER_WORKBENCH)
				{
					Material mat = location.getBlock().getRelative(BlockFace.UP).getType();
					if(mat != Material.AIR && mat != Material.CAVE_AIR && mat != Material.VOID_AIR)
					{
						boolean found = false;
						for(CustomBlock cb : Main.CustomBlockRegistry)
						{
							if(cb.CheckForCustomBlock(location.getBlock().getRelative(BlockFace.UP)))
							{
								found = true;
								displayName = ChatColor.RED + "" + cb.getRootItem().getName().replace(ChatColor.WHITE + "", "") + " Redstone Activator";
								displayType = cb.getRootItem().GetMyItemStack().clone();
								break;
							}
						}
						
						if(!found)
						{
							displayName = ChatColor.RED + "" + toTitleCase(mat.toString().toLowerCase().replace("_", " ")) + " Redstone Activator";
							displayType = new CustomItemStack(displayName, mat, 0).GetMyItemStack();
						}
					}
				}
				
				CustomItemStack cis = new CustomItemStack(displayName, displayType.getType(), (displayType.hasItemMeta() && displayType.getItemMeta().hasCustomModelData()) ? displayType.getItemMeta().getCustomModelData() : w.GetType().DisplayItem.getCustomModelData());
				cis.AddLoreLine(ChatColor.DARK_GRAY + "" + location.getWorld().getName() + " " + location.getBlockX() + " " + location.getBlockY() + " " + location.getBlockZ());
				cis.AddLoreLine(ChatColor.DARK_GRAY + w.GetType().toString());
				
				displayMenu.addItem(cis.GetMyItemStack());
			}
			
			
			i++;
		}
	}
	
	
	private static String toTitleCase(String givenString) {
	    String[] arr = givenString.split(" ");
	    StringBuffer sb = new StringBuffer();

	    for (int i = 0; i < arr.length; i++) {
	        sb.append(Character.toUpperCase(arr[i].charAt(0)))
	            .append(arr[i].substring(1)).append(" ");
	    }          
	    return sb.toString().trim();
	}  
	
	public static void PopulateBench(HashMap<Block, Workbench> benches, Block b)
	{
		if(benches.containsKey(b)) {return;}
		for(Workbench w : BlockEvents.Workbenches)
		{
			if(w.CheckForCustomBlock(b))
			{
				benches.put(b, w);
				PopulateBench(benches, b.getRelative(BlockFace.EAST));
				PopulateBench(benches, b.getRelative(BlockFace.WEST));
				PopulateBench(benches, b.getRelative(BlockFace.NORTH));
				PopulateBench(benches, b.getRelative(BlockFace.SOUTH));
				break;
			}
		}
	}

	@Override
	public Inventory getInventory() {
		return displayMenu;
	}
}
