package com.willm.CoreMOD.blocks;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Chest;
import org.bukkit.block.DoubleChest;
import org.bukkit.block.Hopper;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.meta.ItemMeta;

import com.willm.CoreMOD.ItemEvents;
import com.willm.CoreMOD.MyItems;
import com.willm.ModAPI.Main;
import com.willm.ModAPI.Blocks.CustomBlock;
import com.willm.ModAPI.Blocks.Machine;
import com.willm.ModAPI.Items.BlockCreator;
import com.willm.ModAPI.Items.CustomItemStack;
import com.willm.ModAPI.Items.ItemCreator;
import com.willm.ModAPI.Items.Recipes.RecipeBuilder;

public class WorkbenchItemRegistry {

	public static CustomItemStack wooden_top_workbench;
	public static CustomItemStack crafting_workbench, storage_workbench, workbench_input, machine_workbench, redstone_trigger_workbench, storage_terminal, item_terminal, workbench_network_connector;
	
	public static CustomItemStack storage_key;
	
	public static void Register()
	{
		storage_key = ItemCreator.RegisterNewItem(new CustomItemStack("Storage Access Key", Material.ORANGE_DYE, 15023)).AddLoreLine(ChatColor.GREEN + "Used to access nearby storage terminals remotely.").AddLoreLine(ChatColor.GREEN + "Use the /sk command while holding this key.").AddLoreLine(ChatColor.GREEN + "Append any text at the end of the command to search for that item. (ex. /sk iron)");
		storage_key.getRecipe(1, "BLB", "BNR", "CR ").AddMaterial('B', RecipeBuilder.ItemStackInput(MyItems.brass)).AddMaterial('L', RecipeBuilder.ItemStackInput(MyItems.lead_padlock)).AddMaterial('N', Material.NETHERITE_INGOT).AddMaterial('R', RecipeBuilder.ItemStackInput(MyItems.capacitor)).AddMaterial('C', RecipeBuilder.ItemStackInput(MyItems.brass)).Finalize();
		
		RecipeChoice.MaterialChoice allWoods = RecipeBuilder.MultiMaterialInput(Material.OAK_PLANKS, Material.DARK_OAK_PLANKS, Material.SPRUCE_PLANKS, Material.BIRCH_PLANKS, Material.ACACIA_PLANKS, Material.CHERRY_PLANKS, Material.BAMBOO_PLANKS, Material.WARPED_PLANKS, Material.JUNGLE_PLANKS, Material.CRIMSON_PLANKS);
		wooden_top_workbench = ItemCreator.RegisterNewItem(new CustomItemStack("Wooden Workbench", Material.WARPED_TRAPDOOR, 60001));
		wooden_top_workbench.getRecipe(2, "PPP", "S S", "L L").AddMaterial('P', allWoods).AddMaterial('S', Material.STICK).AddMaterial('L', RecipeBuilder.ItemStackInput(MyItems.enforced_strut)).Finalize();
		
		WorkbenchType.NONE.RelativeBench = (Workbench)BlockCreator.RegisterNewBlock(wooden_top_workbench, new Workbench(wooden_top_workbench) {

			@Override
			public void OnInteract(PlayerInteractEvent event) {
				WorkbenchSystem ws = new WorkbenchSystem(event.getClickedBlock());
				
				ws.GenerateInventory();
				event.getPlayer().openInventory(ws.getInventory());
			}

			@Override
			public WorkbenchType GetType() {
				return WorkbenchType.NONE;
			}

			@Override
			public void OnInteract(Block b, Player p) {
				
			}
			
		});
		
		workbench_network_connector = ItemCreator.RegisterNewItem(new CustomItemStack("Network Connector Workbench", Material.WARPED_TRAPDOOR, 61005));
		workbench_network_connector.getRecipe(1, "RWR", "CLC", "S S").AddMaterial('R', RecipeBuilder.ItemStackInput(MyItems.resistor)).AddMaterial('W', RecipeBuilder.ItemStackInput(MyItems.wireless_redstone_activator)).AddMaterial('C', RecipeBuilder.ItemStackInput(MyItems.capacitor)).AddMaterial('L', Material.DIAMOND).AddMaterial('S', RecipeBuilder.ItemStackInput(MyItems.enforced_strut)).Finalize();
		
		WorkbenchType.WORKBENCH_NETWORK_CONNECTOR.RelativeBench = (Workbench)BlockCreator.RegisterNewBlock(workbench_network_connector, new Workbench(workbench_network_connector) {

			@Override
			public WorkbenchType GetType() {
				return WorkbenchType.WORKBENCH_NETWORK_CONNECTOR;
			}

			@Override
			public void OnInteract(PlayerInteractEvent event) {
				OnInteract(event.getClickedBlock(), event.getPlayer());
			}

			@Override
			public void OnInteract(Block b, Player p) {
				Material m = b.getRelative(BlockFace.UP).getType();
				if(m == Material.AIR || m == Material.CAVE_AIR || m == Material.VOID_AIR)
				{
					p.sendMessage(ChatColor.RED + "This connector has no network identifier. Place a block above it to set the corresponding network.");
				}else {
					p.sendMessage(ChatColor.GREEN + "This connector is on the " + m.toString() + " network.");
				}
			}
			
		});
		
		crafting_workbench = ItemCreator.RegisterNewItem(new CustomItemStack("Crafting Workbench", Material.WARPED_TRAPDOOR, 60002));
		crafting_workbench.getRecipe(1, "PCP", "STS", "L L", "crafting_bench_from_wood").AddMaterial('T', RecipeBuilder.ItemStackInput(MyItems.netherite_core)).AddMaterial('P', Material.DIAMOND).AddMaterial('S', RecipeBuilder.ItemStackInput(MyItems.lead_ingot)).AddMaterial('L', RecipeBuilder.ItemStackInput(MyItems.enforced_strut)).AddMaterial('C', Material.CRAFTING_TABLE).Finalize();

		WorkbenchType.CRAFTING_BENCH.RelativeBench = (Workbench)BlockCreator.RegisterNewBlock(crafting_workbench, new Workbench(crafting_workbench) {

			@Override
			public void OnInteract(PlayerInteractEvent event)
			{
				OnInteract(event.getClickedBlock(), event.getPlayer());
			}

			@Override
			public WorkbenchType GetType() {
				return WorkbenchType.CRAFTING_BENCH;
			}

			@Override
			public void OnInteract(Block b, Player p)
			{
				if(p.getEquipment().getItemInMainHand() != null && p.getEquipment().getItemInMainHand().getType() != Material.AIR)
				{
					ItemStack cursor = p.getEquipment().getItemInMainHand();
					String query = (cursor.hasItemMeta() && cursor.getItemMeta().hasDisplayName()) ? cursor.getItemMeta().getDisplayName().replace(" ", "").replace(ChatColor.WHITE + "", "").toLowerCase() : cursor.getType().toString().toLowerCase().replace("_", "");
					StorageKeyCommand.CreateCraftingSearchResultInventory(p, b, query);
				}else {
					StorageKeyCommand.CreateCraftingSearchResultInventory(p, b, Material.values()[new Random().nextInt(Material.values().length)].toString().toLowerCase().replace("_", ""));
				}
				
				//StorageKeyCommand.AttemptRecipe("minecraft:iron_leggings", p, b);
				
				/*				
				Inventory craftingInventory = Bukkit.createInventory(p, 54, "Recipe Listing");
				
				int i = 0;
				
				for(String rname : RecipeUtils.GetRecipeNames())
				{
					Recipe r = RecipeUtils.GetRecipeFromKey(rname);
					
					ItemStack is = r.getResult();
					ItemMeta im = is.getItemMeta();
					im.setDisplayName(ChatColor.GOLD + im.getDisplayName());
					
					List<String> lore = im.hasLore() ? im.getLore() : new ArrayList<String>();
					
					if(r instanceof FurnaceRecipe)
					{
						FurnaceRecipe fr = (FurnaceRecipe)r;
						ItemStack isc = fr.getInput();
						lore.add(ChatColor.GREEN + "" + ((isc.hasItemMeta() && isc.getItemMeta().hasDisplayName()) ? isc.getItemMeta().getDisplayName().replace(ChatColor.WHITE + "", "") : RecipeUtils.upperCaseAllFirstCharacters(isc.getType().toString().toLowerCase().replace("_", " "))));
					}
					
					if(r instanceof ShapedRecipe)
					{
						ShapedRecipe sr = (ShapedRecipe)r;
						for(ItemStack isc : sr.getIngredientMap().values())
						{
							lore.add(ChatColor.GREEN + "" + ((isc.hasItemMeta() && isc.getItemMeta().hasDisplayName()) ? isc.getItemMeta().getDisplayName().replace(ChatColor.WHITE + "", "") : RecipeUtils.upperCaseAllFirstCharacters(isc.getType().toString().toLowerCase().replace("_", " "))));
						}
					}
					
					im.setLore(lore);
					is.setItemMeta(im);
					
					craftingInventory.setItem(i++, is);
					if(i > 44)
					{
						break;
					}
				}
				
				craftingInventory.setItem(49, new CustomItemStack("Search", Material.HOPPER, 0).AddEnchant(Enchantment.LUCK, 1, true).AddFlags(ItemFlag.HIDE_ENCHANTS).GetMyItemStack());
				craftingInventory.setItem(53, new CustomItemStack(ChatColor.RED + "Crafting Workbench", Material.WARPED_TRAPDOOR, 60002).AddEnchant(Enchantment.LUCK, 1, true).AddFlags(ItemFlag.HIDE_ENCHANTS).GetMyItemStack());

				p.openInventory(craftingInventory);
				*/
			}

			
		
			
		});
		
		item_terminal = ItemCreator.RegisterNewItem(new CustomItemStack("Item Terminal Workbench", Material.WARPED_TRAPDOOR, 61004));
		item_terminal.getRecipe(1, "CDC", "TLT", "S S").AddMaterial('C', Material.CHEST).AddMaterial('D', RecipeBuilder.ItemStackInput(MyItems.capacitor)).AddMaterial('T', RecipeBuilder.ItemStackInput(MyItems.tubing_pump)).AddMaterial('L', RecipeBuilder.ItemStackInput(MyItems.lead_ingot)).AddMaterial('S', RecipeBuilder.ItemStackInput(MyItems.enforced_strut)).Finalize();
		
		WorkbenchType.ITEM_TERMINAL.RelativeBench = (Workbench)BlockCreator.RegisterNewBlock(item_terminal, new Workbench(item_terminal) {

			@Override
			public WorkbenchType GetType() {
				return WorkbenchType.ITEM_TERMINAL;
			}

			@Override
			public void OnInteract(PlayerInteractEvent event) {
				OnInteract(event.getClickedBlock(), event.getPlayer());
			}

			@Override
			public void OnInteract(Block b, Player p) {
				for(Entity e : b.getWorld().getNearbyEntities(b.getLocation(), 1.0f, 1.5f, 1.0f))
				{
					if(e instanceof ItemFrame)
					{
						ItemFrame iFrame = (ItemFrame)e;
						if(iFrame.getItem() != null)
						{
							if(iFrame.getItem().hasItemMeta() && iFrame.getItem().getItemMeta().hasDisplayName())
							{
								StorageKeyCommand.CreateSearchResultInventory(p, this.GetMyStand(b), iFrame.getItem().getItemMeta().getDisplayName().toLowerCase().replace(" ", "").replace(ChatColor.WHITE + "", ""), false);
								return;
							}else {
								StorageKeyCommand.CreateSearchResultInventory(p, this.GetMyStand(b), iFrame.getItem().getType().toString().toLowerCase().replace("_", ""), false);
								return;
							}
						}
					}
				}
				
				p.sendMessage(ChatColor.RED + "Please place an item frame above this terminal to query for an item.");
				
			}
			
		});
		
		storage_terminal = ItemCreator.RegisterNewItem(new CustomItemStack("Storage Terminal Workbench", Material.WARPED_TRAPDOOR, 61003));
		storage_terminal.getRecipe(1, "CHC", "TLT", "S S").AddMaterial('C', Material.CHEST).AddMaterial('H', Material.HOPPER).AddMaterial('T', RecipeBuilder.ItemStackInput(MyItems.tubing_pump)).AddMaterial('L', RecipeBuilder.ItemStackInput(MyItems.lead_ingot)).AddMaterial('S', RecipeBuilder.ItemStackInput(MyItems.enforced_strut)).Finalize();
		
		WorkbenchType.STORAGE_TERMINAL.RelativeBench = (Workbench)BlockCreator.RegisterNewBlock(storage_terminal, new Workbench(storage_terminal) {

			private CustomItemStack storageTerminalIndicator = new CustomItemStack(ChatColor.GOLD + "Storage Terminal", Material.CHEST, 11);
			
			@Override
			public WorkbenchType GetType() {
				return WorkbenchType.STORAGE_TERMINAL;
			}

			@Override
			public void OnInteract(PlayerInteractEvent event)
			{
				OnInteract(event.getClickedBlock(), event.getPlayer());
			}

			@Override
			public void OnInteract(Block b, Player p)
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
				
				Inventory myInventory = Bukkit.createInventory(p, 54);
				
				for(int i = 0; i < myInventory.getSize() - 9; i++)
				{
					if(i >= allItems.size()) {break;}
					myInventory.setItem(i, allItems.get(i));
				}

				storageTerminalIndicator.SetLore(new ArrayList<String>());
				storageTerminalIndicator.AddLoreLine(b.getLocation().getBlockX() + ", " + b.getLocation().getBlockY() + ", " + b.getLocation().getBlockZ());
				
				myInventory.setItem(49, new CustomItemStack("Search", Material.HOPPER, 0).AddLoreLine(ChatColor.BLUE + "Click on this item with the item to search for.").AddEnchant(Enchantment.LUCK, 1, true).AddFlags(ItemFlag.HIDE_ENCHANTS).GetMyItemStack());
				
				myInventory.setItem(53, storageTerminalIndicator.GetMyItemStack());
				
				p.openInventory(myInventory);
			}
			
		});
		
		storage_workbench = ItemCreator.RegisterNewItem(new CustomItemStack("Storage Workbench", Material.WARPED_TRAPDOOR, 60003));
		storage_workbench.getRecipe(1, "PHP", "StS", "L L", "storage_bench_from_wood").AddMaterial('P', allWoods).AddMaterial('t', RecipeBuilder.ItemStackInput(MyItems.tubing_component)).AddMaterial('H', Material.HOPPER).AddMaterial('S', Material.STICK).AddMaterial('L', RecipeBuilder.ItemStackInput(MyItems.enforced_strut)).Finalize();
		storage_workbench.getRecipe(1, "PHP", "WtW").AddMaterial('P', allWoods).AddMaterial('t', RecipeBuilder.ItemStackInput(MyItems.tubing_component)).AddMaterial('H', Material.HOPPER).AddMaterial('W', RecipeBuilder.ItemStackInput(wooden_top_workbench)).Finalize();

		WorkbenchType.STORAGE_BENCH.RelativeBench = (Workbench)BlockCreator.RegisterNewBlock(storage_workbench, new Workbench(storage_workbench) {

			@Override
			public WorkbenchType GetType() {
				return WorkbenchType.STORAGE_BENCH;
			}

			@Override
			public void OnInteract(PlayerInteractEvent event) {
				OnInteract(event.getClickedBlock(), event.getPlayer());
			}

			@Override
			public void OnInteract(Block b, Player p) {
				if(b.getRelative(BlockFace.UP).getState() instanceof Chest)
				{
					Chest c = (Chest)b.getRelative(BlockFace.UP).getState();
					
					if(c.getInventory().getHolder() instanceof DoubleChest)
					{
						DoubleChest dc = (DoubleChest)c.getInventory().getHolder();
						p.openInventory(dc.getInventory());
					}else {
						p.openInventory(c.getBlockInventory());
					}
					
					c.open();
					c.update(true);
					c.close();
				}
			}
			
		
		});
		
		workbench_input = ItemCreator.RegisterNewItem(new CustomItemStack("Input Workbench", Material.WARPED_TRAPDOOR, 60004));
		workbench_input.getRecipe(1, "HHH", "CtR", "L L", "input_bench_from_wood").AddMaterial('C', RecipeBuilder.ItemStackInput(MyItems.capacitor)).AddMaterial('R', RecipeBuilder.ItemStackInput(MyItems.resistor)).AddMaterial('t', RecipeBuilder.ItemStackInput(MyItems.tubing)).AddMaterial('H', Material.HOPPER).AddMaterial('L', RecipeBuilder.ItemStackInput(MyItems.enforced_strut)).Finalize();
		workbench_input.getRecipe(1, "HHH", "CtR", " W ").AddMaterial('C', RecipeBuilder.ItemStackInput(MyItems.capacitor)).AddMaterial('R', RecipeBuilder.ItemStackInput(MyItems.resistor)).AddMaterial('t', RecipeBuilder.ItemStackInput(MyItems.tubing)).AddMaterial('H', Material.HOPPER).AddMaterial('W', RecipeBuilder.ItemStackInput(wooden_top_workbench)).Finalize();

		WorkbenchType.WORKBENCH_INPUT.RelativeBench = (Workbench)BlockCreator.RegisterNewBlock(workbench_input, new TickBench(workbench_input) {

			@Override
			public WorkbenchType GetType() {
				return WorkbenchType.WORKBENCH_INPUT;
			}

			@Override
			public void OnInteract(PlayerInteractEvent event)
			{
				
			}

			@Override
			public void OnInteract(Block b, Player p)
			{
				
			}

			@Override
			public void Tick(Block b)
			{
				if(b.getRelative(BlockFace.UP).getType() == Material.HOPPER)
				{

					Hopper h = (Hopper)b.getRelative(BlockFace.UP).getState();
					if(((org.bukkit.block.data.type.Hopper)h.getBlockData()).getFacing() == BlockFace.DOWN)
					{

						
						List<ItemStack> add = new ArrayList<ItemStack>();
						for(ItemStack is : h.getInventory().getContents())
						{
							if(is != null)
							{
								add.add(is);
							}
						}
						
						if(add.size() > 0)
						{
							WorkbenchSystem system = new WorkbenchSystem(b);
							
							if(AddItemsToWorkbenchSystem(system, add))
							{
								h.getInventory().clear();
							}
							
							//h.update(true);
						}
					}
				}
			}
			
		});
	
		machine_workbench = ItemCreator.RegisterNewItem(new CustomItemStack("Machine Access Workbench", Material.WARPED_TRAPDOOR, 60005));
		machine_workbench.getRecipe(1, "CTR", "S S", "L L", "machine_bench_from_wood").AddMaterial('C', RecipeBuilder.ItemStackInput(MyItems.capacitor)).AddMaterial('T', RecipeBuilder.ItemStackInput(MyItems.tubing_cap)).AddMaterial('R', RecipeBuilder.ItemStackInput(MyItems.resistor)).AddMaterial('S', RecipeBuilder.ItemStackInput(MyItems.steel_rod)).AddMaterial('L', RecipeBuilder.ItemStackInput(MyItems.enforced_strut)).Finalize();
		machine_workbench.getRecipe(1, "CTR", "W W").AddMaterial('C', RecipeBuilder.ItemStackInput(MyItems.capacitor)).AddMaterial('T', RecipeBuilder.ItemStackInput(MyItems.tubing_cap)).AddMaterial('R', RecipeBuilder.ItemStackInput(MyItems.resistor)).AddMaterial('W', RecipeBuilder.ItemStackInput(wooden_top_workbench)).Finalize();

		WorkbenchType.MACHINE_WORKBENCH.RelativeBench = (Workbench)BlockCreator.RegisterNewBlock(machine_workbench, new Workbench(machine_workbench) {

			@Override
			public WorkbenchType GetType() {
				return WorkbenchType.MACHINE_WORKBENCH;
			}

			@Override
			public void OnInteract(PlayerInteractEvent event) {
				OnInteract(event.getClickedBlock(), event.getPlayer());
			}

			@Override
			public void OnInteract(Block b, Player p) {
				if(b.getRelative(BlockFace.UP).getType() != Material.DISPENSER) {return;}
				
				
				
				
				for(CustomBlock cb : Main.CustomBlockRegistry)
				{
					if(cb.getMachineTemplate() != null)
					{
						if(cb.CheckForCustomBlock(b.getRelative(BlockFace.UP)))
						{
							for(Machine m : cb.m)
							{
								if(m.location.distance(b.getLocation()) < 7f)
								{
									m.Interaction(p);
									return;
								}
							}
						}
					}
				}
			}
			
		
		});
	
		redstone_trigger_workbench = ItemCreator.RegisterNewItem(new CustomItemStack("Redstone Trigger Workbench", Material.WARPED_TRAPDOOR, 60006));
		redstone_trigger_workbench.getRecipe(1, "PWP", "S S", "L L", "redstone_trigger_bench_from_wood").AddMaterial('P', Material.OAK_PLANKS).AddMaterial('W', RecipeBuilder.ItemStackInput(MyItems.wireless_redstone_activator)).AddMaterial('S', RecipeBuilder.ItemStackInput(MyItems.steel_rod)).AddMaterial('L', RecipeBuilder.ItemStackInput(MyItems.enforced_strut)).Finalize();
		redstone_trigger_workbench.getRecipe(1, "PWP", "w w").AddMaterial('P', Material.OAK_PLANKS).AddMaterial('W', RecipeBuilder.ItemStackInput(MyItems.wireless_redstone_activator)).AddMaterial('w', RecipeBuilder.ItemStackInput(wooden_top_workbench)).Finalize();
		
		WorkbenchType.REDSTONE_TRIGGER_WORKBENCH.RelativeBench = (Workbench)BlockCreator.RegisterNewBlock(redstone_trigger_workbench, new Workbench(redstone_trigger_workbench) {

			@Override
			public WorkbenchType GetType() {
				return WorkbenchType.REDSTONE_TRIGGER_WORKBENCH;
			}

			@Override
			public void OnInteract(PlayerInteractEvent event) {
				OnInteract(event.getClickedBlock(), event.getPlayer());
			}

			@Override
			public void OnInteract(Block b, Player p) 
			{
				Block[] relatives = new Block[] {b.getRelative(BlockFace.EAST), b.getRelative(BlockFace.SOUTH), b.getRelative(BlockFace.NORTH), b.getRelative(BlockFace.WEST), b.getRelative(BlockFace.UP), b.getRelative(BlockFace.DOWN)};
				for(Block rel : relatives)
				{
					if(rel.getType() == Material.REDSTONE_WIRE)
					{
						final Block b1 = rel;
						b1.setType(Material.REDSTONE_TORCH);
						
						ItemEvents.wireRedstoneActiveLinks.add(b1.getLocation());
						
						Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(com.willm.CoreMOD.Main.INSTANCE, new Runnable() {

							@Override
							public void run() {
						
								ItemEvents.wireRedstoneActiveLinks.remove(b1.getLocation());
								b1.setType(Material.REDSTONE_WIRE);
							}
							
						}, 3);
					}
				}
			}
			
		});
	}
	
	public static boolean AddItemsToWorkbenchSystem(WorkbenchSystem system, List<ItemStack> add)
	{
		for(ItemStack is : add)
		{
			Inventory airChest = null;
			boolean found = false;
			
			for(Entry<Block, Workbench> entry : system.Benches.entrySet())
			{

				if(entry.getValue().GetType() == WorkbenchType.STORAGE_BENCH) {
					if(entry.getKey().getRelative(BlockFace.UP).getState() instanceof Chest)
					{

						Chest c = (Chest)entry.getKey().getRelative(BlockFace.UP).getState();
						boolean has_air = false;
						
						Inventory inv_check = c.getBlockInventory();
						
						if(c.getInventory().getHolder() instanceof DoubleChest)
						{
							inv_check = ((DoubleChest)c.getInventory().getHolder()).getInventory();
						}
						
						for(ItemStack isc : inv_check)
						{
							
							if(isc == null) {
								has_air = true;
								continue;
							}
							
							boolean validCMD = true;
							
							if(is.hasItemMeta() && is.getItemMeta().hasCustomModelData())
							{
								validCMD = false;
								
								if(isc.hasItemMeta() && isc.getItemMeta().hasCustomModelData())
								{
									if(is.getItemMeta().getCustomModelData() == isc.getItemMeta().getCustomModelData())
									{
										validCMD = true;
									}
								}
							}
							
							if(validCMD && isc.getType() == is.getType())
							{
								if(isc.getAmount() <= 64 - is.getAmount())
								{
									inv_check.addItem(is.clone());
									found = true;
									break;
								}else {
									if(has_air)
									{
										inv_check.addItem(is.clone());
										found = true;
										break;
									}
								}
							}
						}
						
						if(found)
						{
							break;
						}else if(has_air)
						{
							airChest = inv_check;
						}
					}
				}
			}
			
			if(found)
			{
				return true;
			}
			
			if(!found && airChest != null)
			{
				airChest.addItem(is.clone());
				return true;
				//airChest.update(true);
			}
			
		}
		
		return false;
	}
	
}
