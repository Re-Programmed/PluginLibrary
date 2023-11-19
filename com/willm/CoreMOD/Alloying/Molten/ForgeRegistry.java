package com.willm.CoreMOD.Alloying.Molten;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import com.willm.CoreMOD.MyItems;
import com.willm.CoreMOD.Alloying.AlloyMaterial;
import com.willm.ModAPI.Blocks.MachineConversion;
import com.willm.ModAPI.Items.BlockCreator;
import com.willm.ModAPI.Items.CustomItemStack;
import com.willm.ModAPI.Items.ItemCreator;
import com.willm.ModAPI.Items.Recipes.RecipeBuilder;

public class ForgeRegistry {

	public static final String STEEL_BUCKET_LORE = "Steel Bucket";
	
	//The forge is used for melting ingots down. You must put coal or lava in it for it to work. You also cannot take the molten metal out of it, you must place a crucible next to it where it will connect (texture too) to flow out.
	public static CustomItemStack Forge;
	
	public static CustomItemStack steel_enforced_bucket;
	
	public static void RegisterForge()
	{
		MoltenItems.RegisterMolten();

		Forge = ItemCreator.RegisterNewItem(new CustomItemStack("Forge", Material.POLISHED_DIORITE, 15231));
		
		MachineConversion[] mt = new MachineConversion[] {
			new MachineConversion(new ItemStack(Material.IRON_INGOT, 1), MoltenItems.MoltenVariants.get(AlloyMaterial.IRON).GetMyItemStack())
		};
		
		BlockCreator.RegisterNewBlock(Forge, "", 5, 18, "Forge", mt);
		
	}
 
	public static void RegsiterMetalItems()
	{
		steel_enforced_bucket = ItemCreator.RegisterNewItem(new CustomItemStack("Steel Bucket", Material.BUCKET, 12011)).AddLoreLine(ChatColor.DARK_GRAY + STEEL_BUCKET_LORE);
		steel_enforced_bucket.getRecipe(1, "S S", " S ").AddMaterial('S', RecipeBuilder.ItemStackInput(MyItems.high_carbon_steel)).Finalize();
	}

	
	
}
