package com.willm.CoreMOD.blocks;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Chest;
import org.bukkit.block.Hopper;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.RecipeChoice;

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
	public static CustomItemStack crafting_workbench, storage_workbench, workbench_input, machine_workbench, redstone_trigger_workbench;
	
	public static void Register()
	{
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
		
		crafting_workbench = ItemCreator.RegisterNewItem(new CustomItemStack("Crafting Workbench", Material.WARPED_TRAPDOOR, 60002));
		crafting_workbench.getRecipe(1, "PCP", "S S", "L L", "crafting_bench_from_wood").AddMaterial('P', allWoods).AddMaterial('S', Material.STICK).AddMaterial('L', RecipeBuilder.ItemStackInput(MyItems.enforced_strut)).AddMaterial('C', Material.CRAFTING_TABLE).Finalize();
		crafting_workbench.getRecipe(1, "PCP", "W W").AddMaterial('P', allWoods).AddMaterial('W', RecipeBuilder.ItemStackInput(wooden_top_workbench)).AddMaterial('C', Material.CRAFTING_TABLE).Finalize();

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
			public void OnInteract(Block b, Player p) {
				p.openWorkbench(p.getLocation(), true);
				
				/*
				WorkbenchCraftingInventory inventory = new WorkbenchCraftingInventory();
				inventory.PopulateItems();
				inventory.PopulateStorageSources(b);
				
				p.openInventory(inventory.getInventory());			
				*/	
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
					p.openInventory(c.getBlockInventory());
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
							for(ItemStack is : add)
							{
								Chest airChest = null;
								boolean found = false;
								for(Entry<Block, Workbench> entry : system.Benches.entrySet())
								{

									if(entry.getValue().GetType() == WorkbenchType.STORAGE_BENCH) {
										if(entry.getKey().getRelative(BlockFace.UP).getState() instanceof Chest)
										{

											Chest c = (Chest)entry.getKey().getRelative(BlockFace.UP).getState();
											boolean has_air = false;
											for(ItemStack isc : c.getBlockInventory().getStorageContents())
											{
												
												if(isc == null) {
													has_air = true;
													continue;
												}
												
												if(isc.isSimilar(is))
												{
													if(isc.getAmount() <= 64 - is.getAmount())
													{
														c.getBlockInventory().addItem(is.clone());
														h.getInventory().clear();
														found = true;
														break;
													}
												}
											}
											
											if(found)
											{
												break;
											}else if(has_air)
											{
												
												airChest = c;
											}
										}
									}
								}
								
								if(!found && airChest != null)
								{
									airChest.getInventory().addItem(is.clone());
									h.getInventory().clear();
									//airChest.update(true);
								}
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
	
}
