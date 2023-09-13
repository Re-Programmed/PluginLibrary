package com.willm.CoreMOD.blocks;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.RecipeChoice;

import com.willm.CoreMOD.MyItems;
import com.willm.ModAPI.Items.BlockCreator;
import com.willm.ModAPI.Items.CustomItemStack;
import com.willm.ModAPI.Items.ItemCreator;
import com.willm.ModAPI.Items.Recipes.RecipeBuilder;

public class WorkbenchItemRegistry {

	public static CustomItemStack wooden_top_workbench;
	public static CustomItemStack crafting_workbench, storage_workbench;
	
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
				WorkbenchCraftingInventory inventory = new WorkbenchCraftingInventory();
				inventory.PopulateItems();
				inventory.PopulateStorageSources(b);
				
				p.openInventory(inventory.getInventory());				
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

	}
	
}
