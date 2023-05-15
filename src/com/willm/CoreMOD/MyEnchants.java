package com.willm.CoreMOD;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;

import com.willm.CoreMOD.CMachines.EnchantingStation;
import com.willm.CoreMOD.Power.Crusher;
import com.willm.ModAPI.MobDrop;
import com.willm.ModAPI.Enchantments.CustomEnchantment;
import com.willm.ModAPI.Enchantments.EnchantCompatibleTemplates;
import com.willm.ModAPI.Enchantments.EnchantmentCreator;
import com.willm.ModAPI.Items.BlockCreator;
import com.willm.ModAPI.Items.CustomItemStack;
import com.willm.ModAPI.Items.ItemCreator;
import com.willm.ModAPI.Items.Recipes.RecipeBuilder;

public class MyEnchants {

	static CustomItemStack enchantment_core;
	
	static CustomItemStack enchanting_station;
	
	public static void RegisterEnchants()
	{
		CustomItemStack enchantingEssence = ItemCreator.RegisterNewItem(new CustomItemStack("Magic Essence", Material.FIREWORK_STAR, 2));
		ItemCreator.RegisterMobDrop(new MobDrop(EntityType.ZOMBIE, 100), enchantingEssence, 1);
		ItemCreator.RegisterMobDrop(new MobDrop(EntityType.CREEPER, 100), enchantingEssence, 1);
		ItemCreator.RegisterMobDrop(new MobDrop(EntityType.SKELETON, 100), enchantingEssence, 1);

		enchantment_core = ItemCreator.RegisterNewItem(new CustomItemStack("Enchantment Core", Material.FIREWORK_STAR, 1));
		enchantment_core.getRecipe(4, "bSb", "SBS", "bSb").AddMaterial('S', RecipeBuilder.ItemStackInput(Crusher.netherite_dust)).AddMaterial('B', Material.BOOK).AddMaterial('b', RecipeBuilder.ItemStackInput(MyItems.bauxite)).Finalize();
		
		CustomEnchantment xp_collector = new CustomEnchantment("XP Collector", EnchantCompatibleTemplates.Handhelds);
		EnchantmentCreator.RegisterEnchantment(xp_collector);
		
		xp_collector.getBook().getRecipe(1, "EEE", "EBE", "EEE").AddMaterial('E', Material.EXPERIENCE_BOTTLE).AddMaterial('B', Material.BOOK).Finalize();
		
		CustomEnchantment ore_miner = new CustomEnchantment("Vein Miner", EnchantCompatibleTemplates.Tools);
		EnchantmentCreator.RegisterEnchantment(ore_miner);
		
		ore_miner.getBook().getRecipe(1, "DCD", "DBD", "DCD").AddMaterial('D', RecipeBuilder.ItemStackInput(enchantingEssence)).AddMaterial('B', Material.BOOK).AddMaterial('C', Material.IRON_PICKAXE).Finalize();
	
		EnchantEvents.SmeltingTouch_RegisterSmeltables();
		
		
		CustomEnchantment smelting_touch_I = new CustomEnchantment("Smelting Touch I", EnchantCompatibleTemplates.Pickaxes);
		CustomEnchantment smelting_touch_II = new CustomEnchantment("Smelting Touch II", EnchantCompatibleTemplates.Pickaxes);
		CustomEnchantment smelting_touch_III = new CustomEnchantment("Smelting Touch III", EnchantCompatibleTemplates.Pickaxes);
		EnchantmentCreator.RegisterEnchantment(smelting_touch_I);
		EnchantmentCreator.RegisterEnchantment(smelting_touch_II);
		EnchantmentCreator.RegisterEnchantment(smelting_touch_III);

		smelting_touch_I.getBook().getRecipe(1, "DED", "EPE", "DED").AddMaterial('P', Material.DIAMOND_PICKAXE).AddMaterial('D', RecipeBuilder.ItemStackInput(MyItems.diesel)).AddMaterial('E', RecipeBuilder.ItemStackInput(enchantment_core)).Finalize();
		smelting_touch_II.getBook().getRecipe(1, "DED", "EPE", "DED").AddMaterial('P', RecipeBuilder.ItemStackInput(smelting_touch_I.getBook())).AddMaterial('D', RecipeBuilder.ItemStackInput(MyItems.res_fuel)).AddMaterial('E', RecipeBuilder.ItemStackInput(enchantment_core)).Finalize();
		smelting_touch_III.getBook().getRecipe(1, "DED", "EPE", "DED").AddMaterial('P', RecipeBuilder.ItemStackInput(smelting_touch_II.getBook())).AddMaterial('D', RecipeBuilder.ItemStackInput(MyItems.platinum_block)).AddMaterial('E', RecipeBuilder.ItemStackInput(enchantment_core)).Finalize();

		enchanting_station = ItemCreator.RegisterNewItem(new CustomItemStack("Enchanting Station", Material.OBSIDIAN, 1));
		enchanting_station.getRecipe(1, " B ", "CEC", "OOO").AddMaterial('O', Material.OBSIDIAN).AddMaterial('B', Material.BOOK).AddMaterial('C', RecipeBuilder.ItemStackInput(enchantment_core)).AddMaterial('E', Material.ENCHANTING_TABLE).Finalize();
		BlockCreator.RegisterNewBlock(enchanting_station, new EnchantingStation());
	}
}
