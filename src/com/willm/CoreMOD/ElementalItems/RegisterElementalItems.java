package com.willm.CoreMOD.ElementalItems;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.Material;

import com.willm.CoreMOD.MyItems;
import com.willm.CoreMOD.Power.AtmosphereCondenser;
import com.willm.ModAPI.Blocks.MachineConversion;
import com.willm.ModAPI.Items.BlockCreator;
import com.willm.ModAPI.Items.CustomItemStack;
import com.willm.ModAPI.Items.ItemCreator;
import com.willm.ModAPI.Items.Recipes.RecipeBuilder;

public class RegisterElementalItems {

	public static final Material Gases = Material.WOODEN_SWORD;
	public static final Material Solids = Material.LIGHT_BLUE_DYE;
	public static final Material Liquids = Material.GREEN_DYE;
	public static CustomItemStack GasCanister;
	public static CustomItemStack PolymerizationChamber;
	
	public static ArrayList<MachineConversion> Polymers = new ArrayList<MachineConversion>();
	
	public static ArrayList<CustomItemStack> GasList = new ArrayList<CustomItemStack>();
	
	public static void Register()
	{
		CustomItemStack enforced_glass = ItemCreator.RegisterNewItem(new CustomItemStack("Reinforced Glass", Material.YELLOW_STAINED_GLASS, 10001));
		BlockCreator.RegisterNewBlock(enforced_glass).SetConstBlock(false);

		GasCanister = ItemCreator.RegisterNewItem(new CustomItemStack("Gas Canister", Material.WOODEN_SWORD, 10001));
		GasCanister.AddLoreLine(ChatColor.GRAY + "Contents: ");
		GasCanister.getRecipe(1, "GGG", "GBG", "GGG", "craft_gas_canister").AddMaterial('G', RecipeBuilder.ItemStackInput(enforced_glass)).AddMaterial('B', Material.BUCKET).Finalize();
		
		Nonmetals.RegisterNonmetals();
		
		enforced_glass.getRecipe(24, "CCC", "CGC", "CCC", "craft_enforce_glass").AddMaterial('C', RecipeBuilder.ItemStackInput(Nonmetals.GetNonmetal(com.willm.CoreMOD.ElementalItems.ELEnum.Nonmetals.CARBON))).AddMaterial('G', Material.GLASS).Finalize();
		
		PolymerizationChamber = ItemCreator.RegisterNewItem(new CustomItemStack("Polymerization Chamber", Material.EMERALD_BLOCK, 12001));
		BlockCreator.RegisterNewBlock(PolymerizationChamber, "core_mod.polymer_chamber", 125, 18, "Polymerization Chamber", Polymers.toArray(new MachineConversion[Polymers.size()]));
		
		PolymerizationChamber.getRecipe(1, "GPG", "FCF", "GPG", "craft_polymer_chamber").AddMaterial('G', RecipeBuilder.ItemStackInput(GasCanister)).AddMaterial('P', Material.PISTON).AddMaterial('F', Material.BLAST_FURNACE).AddMaterial('C', RecipeBuilder.ItemStackInput(MyItems.Casing)).Finalize();
		
		AtmosphereCondenser ac = new AtmosphereCondenser();
		com.willm.ModAPI.Voltage.Main.energyUsers.add(ac);
		com.willm.ModAPI.Voltage.Main.energyRecievers.add(ac);
	}
	
}
