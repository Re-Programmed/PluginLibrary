package com.willm.CoreMOD.blocks;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Chest;
import org.bukkit.block.DoubleChest;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.ItemFrame;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.willm.CoreMOD.BlockEvents;
import com.willm.CoreMOD.ItemEvents;
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
						
						Inventory cbinv = c.getBlockInventory();
						if(c.getInventory().getHolder() instanceof DoubleChest)
						{
							cbinv = ((DoubleChest)c.getInventory().getHolder()).getInventory();
						}
						
						if(cbinv.getStorageContents().length > 0)
						{
							for(ItemStack is : cbinv.getStorageContents())
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
				
				if(w.GetType() == WorkbenchType.ITEM_TERMINAL)
				{
					for(Entity e : location.getWorld().getNearbyEntities(location, 1.0f, 1.5f, 1.0f))
					{
						if(e instanceof ItemFrame)
						{
							ItemFrame iFrame = (ItemFrame)e;
							
							if(iFrame.getItem() != null)
							{								
								ItemStack nIf = iFrame.getItem().clone();
								
								displayName = displayName + " - " + ((nIf.hasItemMeta() && nIf.getItemMeta().hasDisplayName()) ? nIf.getItemMeta().getDisplayName().replace(ChatColor.WHITE + "", "") : ItemEvents.toTitleCase(nIf.getType().toString().toLowerCase().replace('_', ' ')));
								
								ItemMeta im = nIf.getItemMeta();
								im.setDisplayName(displayName);
								nIf.setItemMeta(im);
								displayType = nIf;
								
								break;
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
				//Check for wireless connections.
				if(w.GetType() == WorkbenchType.WORKBENCH_NETWORK_CONNECTOR)
				{
					//Determine if benches are on same network.
					Material checkMat = b.getRelative(BlockFace.UP).getType();
					if(!(checkMat == Material.AIR || checkMat == Material.CAVE_AIR || checkMat == Material.VOID_AIR))
					{
						for(Entity e : b.getWorld().getNearbyEntities(b.getLocation(), 30f, 30f, 30f))
						{
							if(e instanceof ArmorStand)
							{
								ArmorStand as = (ArmorStand)e;
								
								Block wConnection = as.getLocation().getBlock();
								if(w.CheckForCustomBlock(wConnection))
								{
									if(wConnection.getRelative(BlockFace.UP).getType() == checkMat)
									{
										benches.put(wConnection, w);
										PopulateBench(benches, wConnection.getRelative(BlockFace.EAST));
										PopulateBench(benches, wConnection.getRelative(BlockFace.WEST));
										PopulateBench(benches, wConnection.getRelative(BlockFace.NORTH));
										PopulateBench(benches, wConnection.getRelative(BlockFace.SOUTH));
										PopulateBench(benches, wConnection.getRelative(BlockFace.UP));
										PopulateBench(benches, wConnection.getRelative(BlockFace.DOWN));	
									}
								}
							}
						}
					}
					

				}
				
				benches.put(b, w);
				PopulateBench(benches, b.getRelative(BlockFace.EAST));
				PopulateBench(benches, b.getRelative(BlockFace.WEST));
				PopulateBench(benches, b.getRelative(BlockFace.NORTH));
				PopulateBench(benches, b.getRelative(BlockFace.SOUTH));
				PopulateBench(benches, b.getRelative(BlockFace.UP));
				PopulateBench(benches, b.getRelative(BlockFace.DOWN));
				break;
			}
		}
	}

	@Override
	public Inventory getInventory() {
		return displayMenu;
	}
}
