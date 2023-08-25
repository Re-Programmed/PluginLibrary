package com.willm.CoreMOD.ElementalItems;

import java.util.HashMap;

import org.bukkit.Material;

import com.willm.CoreMOD.MyItems;
import com.willm.ModAPI.Blocks.BlockEvents;
import com.willm.ModAPI.Blocks.LiquidBlock;
import com.willm.ModAPI.Blocks.MachineConversion;
import com.willm.ModAPI.Items.BlockCreator;
import com.willm.ModAPI.Items.CustomItemStack;
import com.willm.ModAPI.Items.ItemCreator;
import com.willm.ModAPI.Items.Recipes.RecipeBuilder;
import org.bukkit.ChatColor;

public class Nonmetals {

	static HashMap<com.willm.CoreMOD.ElementalItems.ELEnum.Nonmetals, CustomItemStack> Nonmetals = new HashMap<com.willm.CoreMOD.ElementalItems.ELEnum.Nonmetals, CustomItemStack>();
	
	public static CustomItemStack ammonia, nitrous_oxide, sleeping_bag;
	
	public static CustomItemStack GetNonmetal(com.willm.CoreMOD.ElementalItems.ELEnum.Nonmetals nonmetal)
	{
		return Nonmetals.get(nonmetal);
	}
	
	public static void RegisterNonmetals()
	{
		
		CustomItemStack HydrogenGas = new CustomItemStack("Hydrogen Gas", RegisterElementalItems.Gases, 12001).AddLoreLine("1000 mB");
		ItemCreator.RegisterNewItem(HydrogenGas);
		
		RegisterElementalItems.GasList.add(HydrogenGas);
		
		Nonmetals.put(com.willm.CoreMOD.ElementalItems.ELEnum.Nonmetals.HYDROGEN, HydrogenGas);
		
		HydrogenGas.getRecipe(4, "XXX", "X X", "XXX", "hydrogen_by_bottle").AddMaterial('X', RecipeBuilder.ItemStackInput(MyItems.hydrogen)).Finalize();
		
		MyItems.gasoline.getRecipe(6, "BBB", "HHH", "BBB", "catalyst_gasoline_from_oil").AddMaterial('B', RecipeBuilder.ItemStackInput(HydrogenGas)).AddMaterial('H', RecipeBuilder.ItemStackInput(MyItems.oil_barrel)).Finalize();
		
		
		CustomItemStack PureCarbon = ItemCreator.RegisterNewItem(new CustomItemStack("Pure Carbon", RegisterElementalItems.Solids, 12001)).AddLoreLine("4 cbM");
		
		Nonmetals.put(com.willm.CoreMOD.ElementalItems.ELEnum.Nonmetals.CARBON, PureCarbon);
		
		
		
		CustomItemStack NitrogenGas = new CustomItemStack("Nitrogen Gas", RegisterElementalItems.Gases, 12002).AddLoreLine("1000 mB");
		NitrogenGas.AddLoreLine(ChatColor.GRAY + "Gas Canister -> From Atmosphere Condenser (x4)");
		ItemCreator.RegisterNewItem(NitrogenGas);
		
		RegisterElementalItems.GasList.add(NitrogenGas);
		
		Nonmetals.put(com.willm.CoreMOD.ElementalItems.ELEnum.Nonmetals.NITROGEN, NitrogenGas);
		
		
		ammonia = ItemCreator.RegisterNewItem(new CustomItemStack("Ammonia", RegisterElementalItems.Liquids, 20001)).AddLoreLine("1000 mB");
		
		ammonia.getRecipe(1, "HNH", "NHN", "HNH", "ammonia_from_n_and_h").AddMaterial('N', RecipeBuilder.ItemStackInput(HydrogenGas)).AddMaterial('H', RecipeBuilder.ItemStackInput(NitrogenGas)).Finalize();
		
		CustomItemStack ammoniaLiquidBlock = ItemCreator.RegisterNewItem(new CustomItemStack("Ammonia Liquid", Material.DIAMOND_BLOCK, 10001));
		
		LiquidBlock ammoniaLiquidBlock_v = BlockCreator.RegisterNewLiquid(ammoniaLiquidBlock);
		ammoniaLiquidBlock_v.LiquidDamage = 2;
		
		BlockEvents.RegisterBucket(ammonia, ammoniaLiquidBlock_v);
		
		
		CustomItemStack OxygenGas = new CustomItemStack("Oxygen Canister", RegisterElementalItems.Gases, 12003).AddLoreLine("1000 mB");
		ItemCreator.RegisterNewItem(OxygenGas);
		
		RegisterElementalItems.GasList.add(OxygenGas);
		
		Nonmetals.put(com.willm.CoreMOD.ElementalItems.ELEnum.Nonmetals.OXYGEN, OxygenGas);
		
		
		nitrous_oxide = ItemCreator.RegisterNewItem(new CustomItemStack("Nitrous Oxide", RegisterElementalItems.Gases, 12004)).AddLoreLine("3000 mB").AddLoreLine("Laughing Gas. Right-click to use.");
		RegisterElementalItems.GasList.add(nitrous_oxide);
		
		nitrous_oxide.getRecipe(1, "NOO", " C ", "   ", "nitrous_oxide_craft").AddMaterial('C', RecipeBuilder.ItemStackInput(RegisterElementalItems.GasCanister)).AddMaterial('O', RecipeBuilder.ItemStackInput(OxygenGas)).AddMaterial('N', RecipeBuilder.ItemStackInput(NitrogenGas)).Finalize();
		
		CustomItemStack nitric_acid = ItemCreator.RegisterNewItem(new CustomItemStack("Nitric Acid", RegisterElementalItems.Liquids, 12003)).AddLoreLine("1000 mB");
		nitric_acid.getRecipe(3, " H ", "OOO", " N ", "craft_nitric_acid").AddMaterial('H', RecipeBuilder.ItemStackInput(HydrogenGas)).AddMaterial('O', RecipeBuilder.ItemStackInput(OxygenGas)).AddMaterial('N', RecipeBuilder.ItemStackInput(NitrogenGas)).Finalize();
		
		CustomItemStack nitric_acid_block = ItemCreator.RegisterNewItem(new CustomItemStack("Nitric Acid Source", Material.DIAMOND_BLOCK, 10002));
		
		LiquidBlock nitric_acid_block_v = BlockCreator.RegisterNewLiquid(nitric_acid_block);
		nitric_acid_block_v.LiquidDamage = 4;
		
		BlockEvents.RegisterBucket(nitric_acid, nitric_acid_block_v);
		
		CustomItemStack nylon_monomer = ItemCreator.RegisterNewItem(new CustomItemStack("Nylon Monomer", Material.GREEN_DYE, 12002));
		nylon_monomer.getRecipe(10, "NNN", "NON", "NNN", "nylon_monomer_craft").AddMaterial('N', RecipeBuilder.ItemStackInput(NitrogenGas)).AddMaterial('O', RecipeBuilder.ItemStackInput(MyItems.oil_barrel)).Finalize();
		
		CustomItemStack nylon = ItemCreator.RegisterNewItem(new CustomItemStack("Nylon", Material.GREEN_DYE, 12001)).AddLoreLine(ChatColor.GRAY + "(Nylon Monomer) Polymer");
		
		RegisterElementalItems.Polymers.add(new MachineConversion(nylon_monomer.GetAmountClone(3), nylon.GetAmountClone(4)));
		
		//NYLON CREATION
		
		CustomItemStack pillow = ItemCreator.RegisterNewItem(new CustomItemStack("Pillow", Material.ORANGE_DYE, 12121));
		pillow.getRecipe(1, "NNN", "NFN", "NNN", "craft_pillow").AddMaterial('N', RecipeBuilder.ItemStackInput(nylon)).AddMaterial('F', Material.FEATHER).Finalize();
		
		sleeping_bag = ItemCreator.RegisterNewItem(new CustomItemStack("Sleeping Bag", Material.YELLOW_STAINED_GLASS, 10002)).AddLoreLine(ChatColor.RED + "Set your spawn at any location. Cannot skip night.");
		sleeping_bag.getRecipe(1, "PNN", "OOO", "   ", "craft_sleeping_bag").AddMaterial('P', RecipeBuilder.ItemStackInput(pillow)).AddMaterial('O', RecipeBuilder.MultiMaterialInput(Material.OAK_PLANKS, Material.JUNGLE_PLANKS, Material.SPRUCE_PLANKS, Material.DARK_OAK_PLANKS, Material.ACACIA_PLANKS, Material.BIRCH_PLANKS)).AddMaterial('N', RecipeBuilder.ItemStackInput(nylon)).Finalize();
		
		BlockCreator.RegisterNewBlock(sleeping_bag);
		
		//END NYLON
		
		CustomItemStack PurePhosphorus = new CustomItemStack("Phosphorus", RegisterElementalItems.Solids, 12002).AddLoreLine("4 cbM");
		ItemCreator.RegisterNewItem(PurePhosphorus);
		
		CustomItemStack PhosphorusMineral = ItemCreator.RegisterNewItem(new CustomItemStack("Phosphorus Mineral", RegisterElementalItems.Solids, 12005));
		PurePhosphorus.getRecipe(6, "MMM", "MNM", "MMM", "craft_phosphorus").AddMaterial('M', RecipeBuilder.ItemStackInput(PhosphorusMineral)).AddMaterial('N', RecipeBuilder.ItemStackInput(nitric_acid)).Finalize();
				
		Nonmetals.put(com.willm.CoreMOD.ElementalItems.ELEnum.Nonmetals.PHOSPHORUS, PurePhosphorus);
		
		
		
		CustomItemStack PureSulfur = new CustomItemStack("Sulfur", RegisterElementalItems.Solids, 12003).AddLoreLine("4 cbM");
		ItemCreator.RegisterNewItem(PureSulfur);
				
		Nonmetals.put(com.willm.CoreMOD.ElementalItems.ELEnum.Nonmetals.SULFUR, PureSulfur);
		
		
		
		CustomItemStack PureSelenium = new CustomItemStack("Selenium", RegisterElementalItems.Solids, 12004).AddLoreLine("4 cbM");
		ItemCreator.RegisterNewItem(PureSelenium);
				
		Nonmetals.put(com.willm.CoreMOD.ElementalItems.ELEnum.Nonmetals.SELENIUM, PureSelenium);
		 
	}
	
}
