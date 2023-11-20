package com.willm.CoreMOD;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.willm.CoreMOD.Alloying.Crucibles.CrucibleItemRegistry;
import com.willm.CoreMOD.Alloying.Molten.ForgeRegistry;
import com.willm.CoreMOD.Power.CreeperTurret;
import com.willm.CoreMOD.Power.GasBurningGenerator;
import com.willm.CoreMOD.Shopping.Currency;
import com.willm.CoreMOD.Shopping.PlotProtector;
import com.willm.CoreMOD.Shopping.ShopBlock;
import com.willm.CoreMOD.blocks.ChestLock;
import com.willm.CoreMOD.blocks.WorkbenchItemRegistry;
import com.willm.ModAPI.MobDrop;
import com.willm.ModAPI.Blocks.BlockDirectionData;
import com.willm.ModAPI.Blocks.CustomBlock;
import com.willm.ModAPI.Blocks.LiquidBlock;
import com.willm.ModAPI.Blocks.MachineConversion;
import com.willm.ModAPI.Blocks.CustomStates.CustomBaseMaterialRetainingBlock;
import com.willm.ModAPI.Blocks.CustomStates.CustomFenceBlock;
import com.willm.ModAPI.Blocks.CustomStates.CustomTrapdoorBlock;
import com.willm.ModAPI.Items.BlockCreator;
import com.willm.ModAPI.Items.CustomItemStack;
import com.willm.ModAPI.Items.ItemCreator;
import com.willm.ModAPI.Items.Recipes.MaterialRecipeTemplate;
import com.willm.ModAPI.Items.Recipes.RecipeBuilder;
import com.willm.ModAPI.Items.Recipes.RecipeTemplates;
import com.willm.ModAPI.RecipeDisplay.CustomRecipeType;
import com.willm.ModAPI.RecipeDisplay.RecipeDisplay;
import com.willm.ModAPI.Terrain.Ore;

public class MyItems {
	
	public static Map<Material, Integer> parishables = new HashMap<Material, Integer>();
	
	public static void InitParishables()
	{
		parishables.put(Material.BEEF,  (20 * 30));
		parishables.put(Material.COOKED_BEEF,  (20 * 30) + 350);
		parishables.put(Material.CHICKEN,  (20 * 30) - 150);
		parishables.put(Material.COOKED_CHICKEN,  (20 * 30) + 200);
		parishables.put(Material.PORKCHOP,  (20 * 30));
		parishables.put(Material.COOKED_PORKCHOP,  (20 * 30) + 350);
		parishables.put(Material.APPLE,  20 * 60 * 4);
		parishables.put(Material.GOLDEN_APPLE,  20 * 60 * 8);
		parishables.put(Material.MELON_SLICE,  20 * 30);
		parishables.put(Material.SWEET_BERRIES,  20 * 45);
		parishables.put(Material.GLOW_BERRIES,  20 * 45);
		parishables.put(Material.CARROT,  20 * 60 * 4);
		parishables.put(Material.GOLDEN_CARROT,  20 * 60 * 4);
		parishables.put(Material.POTATO,  20 * 60 * 12);
		parishables.put(Material.BAKED_POTATO,  20 * 60 * 4);
		parishables.put(Material.BEETROOT,  20 * 60 * 4);
		parishables.put(Material.MUTTON,  (20 * 30) - 150);
		parishables.put(Material.COOKED_MUTTON,  (20 * 30) + 200);
		parishables.put(Material.RABBIT,  (20 * 30) - 150);
		parishables.put(Material.COOKED_RABBIT,  (20 * 30) + 200);
		parishables.put(Material.COD, (20 * 20) * 2);
		parishables.put(Material.COOKED_COD, (20 * 40) * 2);
		parishables.put(Material.SALMON, (20 * 20) * 2);
		parishables.put(Material.COOKED_SALMON, (20 * 40) * 2);
		parishables.put(Material.TROPICAL_FISH, (20 * 20) * 2);
		parishables.put(Material.PUFFERFISH, (20 * 20) * 2);
		parishables.put(Material.BREAD, (20 * 60) * 4 * 2);
		parishables.put(Material.COOKIE, (20 * 60) * 12 * 2);
		parishables.put(Material.PUMPKIN_PIE, (20 * 60) * 12 * 2);
		parishables.put(Material.ROTTEN_FLESH, 20);
		parishables.put(Material.MUSHROOM_STEW, (20 * 60) * 3 * 2);
		parishables.put(Material.RABBIT_STEW, (20 * 60) * 3 * 2);
		parishables.put(Material.SUSPICIOUS_STEW, (20 * 60) * 3 * 2);
		parishables.put(Material.MILK_BUCKET, (20 * 30) * 2);
	}
	
	//Global Items
	public static CustomItemStack platinum_ingot, platinum_block;
	public static CustomItemStack tungsten_ingot, tungsten_block, tungsten_chestplate;
	public static CustomItemStack titanium_ingot, titanium_block;
	
	public static CustomItemStack wolframite;
	public static CustomItemStack platinum_ore;
	public static CustomItemStack rutile, titanium_ore;

	public static CustomItemStack taser;
	
	public static CustomItemStack compressor, electrolyzer;
	
	static HashMap<CustomItemStack, CustomItemStack> doors = new HashMap<CustomItemStack, CustomItemStack>(); 
	
	public static CustomItemStack elevator, elevator_shaft;
	
	public static CustomItemStack iron_rod, nozzle, steel_rod;
	
	public static CustomItemStack capacitor, resistor;
	
	public static CustomItemStack crude_oil_deposit, oil_barrel, diesel, kerosene, butane, res_fuel, gasoline;
	
	public static CustomItemStack stripmine_drill, drill, enforced_drill;
	
	public static CustomItemStack trumpet;
	
	public static CustomItemStack zinc_ore, zinc;
	
	public static CustomItemStack steel_ingot;
	
	public static CustomItemStack wireless_redstone_activator;
	
	public static CustomItemStack o2_bottle, hydrogen, Casing;
	
	public static CustomItemStack jetpack;
	
	public static CustomItemStack oil_gun;
	
	public static CustomItemStack silicon;
	
	public static CustomItemStack bauxite, aluminum, aluminum_alloy;
	
	public static CustomItemStack tree_tap, sap, syrup;
	
	public static CustomItemStack player_detector;
	
	public static CustomItemStack gift;
	
	public static CustomItemStack spring_water, spring_water_source;
	
	public static CustomItemStack limestone_powder, limestone_block, rotary_kiln, generator_core, netherite_core, steel_enforced_gray_concrete, concrete_powder;
	public static CustomItemStack salt_block, salt_item;
	
	public static CustomItemStack gear, gearshift, electronic_gearshift, engine;
	
	public static CustomItemStack industrial_fridge, fridge, cooling_unit, brine_cauldron, brine, salt_water, sludge;
	
	public static CustomItemStack glass_jar;
	public static CustomBlock glass_jar_water, glass_jar_lava, glass_jar_brine, glass_jar_cookies, glass_jar_salt_water, glass_jar_tannin;
	
	public static CustomItemStack manual_feeding_trough, enforced_strut;
	
	public static CustomItemStack high_carbon_steel;
	
	public static CustomItemStack lens, combustion_acceleration_chamber;
	
	public static CustomItemStack cobalt_ingot;
	
	public static CustomItemStack lead_ore, lead_ingot, lead_powder, bismuth, lead_bullion, zinc_lead_alloy, pure_lead_powder;
	
	public static HashMap<Integer, ItemStack> GlassJarConversions = new HashMap<Integer, ItemStack>();
	public static HashMap<ItemStack, CustomBlock> GlassJarInserts = new HashMap<ItemStack, CustomBlock>();
	public static ArrayList<Sound> GlassINJarSounds = new ArrayList<Sound>();
	public static ArrayList<Sound> GlassOUTJarSounds = new ArrayList<Sound>();

	public static CustomItemStack iron_dish, tubing, tubing_cap, tubing_pump, tubing_component;
		
	public static void RegisterMyItems() {
		
		lead_powder = ItemCreator.RegisterNewItem(new CustomItemStack("Impure Lead Powder", Material.GUNPOWDER, 15023));
		
		lead_ore = ItemCreator.RegisterNewItem(new CustomItemStack("Lead Ore", Material.GLASS, 15023));
		BlockCreator.RegisterNewBlock(lead_ore).SetConstBlock(false).SetCustomDrops(lead_powder,lead_powder,lead_powder,lead_powder).UseSilkTouch(true).SetMineAs(Material.ANCIENT_DEBRIS).SetRequiredTool("DIAMOND_PICKAXE", "NETHERITE_PICKAXE");
		
		new Ore(true, lead_ore, 8, true, 12, 80, 4, true, false);
		
		lead_ore.getRecipe(4, "LL", "LL").AddMaterial('L', RecipeBuilder.ItemStackInput(lead_powder)).Finalize();
		lead_powder.createSingleItemRecipe("craft_lead_powder", 4, RecipeBuilder.ItemStackInput(lead_ore));
		
		lead_bullion = ItemCreator.RegisterNewItem(new CustomItemStack("Lead Bullion", Material.GUNPOWDER, 15025));
		lead_bullion.getFurnaceRecipe(RecipeBuilder.ItemStackInput(lead_powder), 5, 20 * 10, "smelt_lead_bullion");
		
		pure_lead_powder = ItemCreator.RegisterNewItem(new CustomItemStack("Pure Lead Powder", Material.GUNPOWDER, 15026));
		pure_lead_powder.AddLoreLine(ChatColor.GRAY + "Zinc-Lead Bullion Mixture -> Electric Furnace");
				
		zinc_lead_alloy = ItemCreator.RegisterNewItem(new CustomItemStack("Zinc-Lead Bullion Mixture", Material.GUNPOWDER, 15024));
				
		lead_ingot = ItemCreator.RegisterNewItem(new CustomItemStack("Lead Ingot", Material.GUNPOWDER, 15027));
		
		iron_dish = ItemCreator.RegisterNewItem(new CustomItemStack("Iron Dish", Material.IRON_INGOT, 20002));
		iron_dish.getRecipe(2, " I ", "I I", " I ").AddMaterial('I', Material.IRON_INGOT).Finalize();
		
		bismuth = ItemCreator.RegisterNewItem(new CustomItemStack("Bismuth", Material.GUNPOWDER, 15028));
		bismuth.AddLoreLine(ChatColor.GRAY + "Zinc-Lead Bullion Mixture -> Electric Furnace");
		
		//BISMUTH RECPIE ADD
		{
			CustomRecipeType crt_bismuth = new CustomRecipeType("Electric Smelting", new CustomItemStack("Electric Furnace", Material.POLISHED_ANDESITE, 21006).GetMyItemStack(), "electric_furnace_bismuth_byproduct", Main.INSTANCE, 
					
					new CustomItemStack(ChatColor.RED + "-->", Material.POLISHED_ANDESITE, 21006).AddLoreLine(ChatColor.GREEN + "Speed: 250mt").AddLoreLine(ChatColor.RED + "Requires Power").AddEnchant(Enchantment.LUCK, 1, true).AddFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS).GetMyItemStack(),
					
					bismuth.GetAmountClone(2), zinc_lead_alloy.GetMyItemStack()).AddByproduct(pure_lead_powder.GetMyItemStack());
		
			RecipeDisplay.CUSTOM_RECIPES.add(crt_bismuth);
		}
		
		sludge = ItemCreator.RegisterNewItem(new CustomItemStack("Sludge", Material.BONE_MEAL, 12005));
		sludge.AddLoreLine(ChatColor.DARK_PURPLE + "Can be used as fertilizer.");
		
		salt_water = ItemCreator.RegisterNewItem(new CustomItemStack("Salt Water Bucket", Material.WOODEN_SWORD, 1).AddFlags(ItemFlag.HIDE_ATTRIBUTES));
				
		InitParishables();
		//CEILING TORCH/CHANDALIER
		//Conveyor Belts / Machines
		
		iron_rod = ItemCreator.RegisterNewItem(new CustomItemStack("Iron Rod", Material.ORANGE_DYE, 10001));
		iron_rod.getRecipe(6, " I ", " I ", " I ").AddMaterial('I', Material.IRON_INGOT).Finalize();
		
		WoodRegistry();
		
		TungstenRegistry();
		PlatinumRegistry();
		OilRegistry();
		
		brine = ItemCreator.RegisterNewItem(new CustomItemStack("Bottle Of Brine", Material.HONEY_BOTTLE, 60012));
		brine.AddLoreLine(ChatColor.GRAY + "12 x Salt + Water -> Cauldron (x2)").AddLoreLine(ChatColor.GRAY + "16 x Salt + 3 x Water -> Glass Jar (x3)").AddLoreLine(ChatColor.GRAY + "16 x Salt + 3 x Salt Water -> Glass Jar (x3)").AddLoreLine(ChatColor.GREEN + "Click meat or fish in your inventory to cure it.");
		
		Farming();

		limestone_powder = ItemCreator.RegisterNewItem(new CustomItemStack("Limestone Powder", Material.ORANGE_DYE, 40001));
		limestone_block = ItemCreator.RegisterNewItem(new CustomItemStack("Limestone", Material.ORANGE_CONCRETE, 40001));
		BlockCreator.RegisterNewBlock(limestone_block).SetConstBlock(false).SetMineAs(Material.ANDESITE).SetCustomDrops(limestone_powder, limestone_powder).UseSilkTouch(true).SetRequiredTool("PICKAXE");
		
		limestone_block.getRecipe(1, "LL", "LL").AddMaterial('L', RecipeBuilder.ItemStackInput(limestone_powder)).Finalize();
		
		new Ore(limestone_block, 25, true, 0, 128, 12, true, true);
		
		
		salt_block = ItemCreator.RegisterNewItem(new CustomItemStack("Salt Block", Material.ORANGE_CONCRETE, 70001));
		salt_item = ItemCreator.RegisterNewItem(new CustomItemStack("Salt", Material.ORANGE_DYE, 70001));
		
		//Salt Gathering from BLOCK
		RecipeDisplay.CUSTOM_RECIPES.add(new CustomRecipeType("Gathering", Material.DIAMOND_SHOVEL, "gather_salt", Main.INSTANCE, null,
				salt_item.GetAmountClone(4), salt_block.GetMyItemStack()));
		
		
		BlockCreator.RegisterNewBlock(salt_block).SetConstBlock(false).SetMineAs(Material.DIRT).SetCustomDrops(salt_item, salt_item, salt_item, salt_item);
		
		new Ore(salt_block, 10, true, 6, 128, 45, true, true);
		

		brine_cauldron = ItemCreator.RegisterNewItem(new CustomItemStack("Cauldron (Brine)", Material.CAULDRON, 10001));
		BlockCreator.RegisterNewBlock(brine_cauldron).SetMineAs(Material.CAULDRON).SetConstBlock(false).SetCustomDrops(new CustomItemStack("Cauldron", Material.CAULDRON, 0));
		
		CustomItemStack liquid_cement = ItemCreator.RegisterNewItem(new CustomItemStack("Bucket Of Liquid Cement", Material.BROWN_DYE, 10232));
		liquid_cement.AddLoreLine(ChatColor.GRAY + "Made in the Rotary Kiln.");

		CustomItemStack liquid_cement_source = ItemCreator.RegisterNewItem(new CustomItemStack("Liquid Cement Soruce", Material.GOLD_BLOCK, 70001));
		LiquidBlock liquid_cement_source_b = BlockCreator.RegisterNewLiquid(liquid_cement_source);
		
		liquid_cement_source_b.AddLiquidEffect(new PotionEffect(PotionEffectType.SLOW, 20, 4, true, true));
		
		com.willm.ModAPI.Blocks.BlockEvents.RegisterBucket(liquid_cement, liquid_cement_source_b);
		
		CustomItemStack unfinished_concrete_powder = ItemCreator.RegisterNewItem(new CustomItemStack("Unfinished Concrete Powder", Material.BROWN_CONCRETE, 12341));
		BlockCreator.RegisterNewBlock(unfinished_concrete_powder).SetConstBlock(false).SetMineAs(Material.SOUL_SAND);
		
		
//REPLACE NORMAL RECIPES WITH CUSTOM RECPIES		
		Iterator<Recipe> it = Bukkit.getServer().recipeIterator();
		Recipe recipe;
		while(it.hasNext())
		{
		recipe = it.next();
		if (recipe != null && recipe.getResult().getType().toString().contains("CONCRETE_POWDER"))
		{
		it.remove();
		}
		
		
		}
		
		registerConcreteRecipe(unfinished_concrete_powder, Material.WHITE_CONCRETE_POWDER, Material.WHITE_DYE);
		registerConcreteRecipe(unfinished_concrete_powder, Material.YELLOW_CONCRETE_POWDER, Material.YELLOW_DYE);
		registerConcreteRecipe(unfinished_concrete_powder, Material.BLUE_CONCRETE_POWDER, Material.WHITE_DYE);
		registerConcreteRecipe(unfinished_concrete_powder, Material.LIGHT_BLUE_CONCRETE_POWDER, Material.LIGHT_BLUE_DYE);
		registerConcreteRecipe(unfinished_concrete_powder, Material.CYAN_CONCRETE_POWDER, Material.CYAN_DYE);
		registerConcreteRecipe(unfinished_concrete_powder, Material.PURPLE_CONCRETE_POWDER, Material.PURPLE_DYE);
		registerConcreteRecipe(unfinished_concrete_powder, Material.MAGENTA_CONCRETE_POWDER, Material.MAGENTA_DYE);
		registerConcreteRecipe(unfinished_concrete_powder, Material.GRAY_CONCRETE_POWDER, Material.GRAY_DYE);
		registerConcreteRecipe(unfinished_concrete_powder, Material.BROWN_CONCRETE_POWDER, Material.BROWN_DYE);
		registerConcreteRecipe(unfinished_concrete_powder, Material.LIME_CONCRETE_POWDER, Material.LIME_DYE);
		registerConcreteRecipe(unfinished_concrete_powder, Material.GREEN_CONCRETE_POWDER, Material.GREEN_DYE);
		registerConcreteRecipe(unfinished_concrete_powder, Material.ORANGE_CONCRETE_POWDER, Material.ORANGE_DYE);
		registerConcreteRecipe(unfinished_concrete_powder, Material.RED_CONCRETE_POWDER, Material.RED_DYE);
		registerConcreteRecipe(unfinished_concrete_powder, Material.PINK_CONCRETE_POWDER, Material.PINK_DYE);
		registerConcreteRecipe(unfinished_concrete_powder, Material.LIGHT_GRAY_CONCRETE_POWDER, Material.LIGHT_GRAY_DYE);
		registerConcreteRecipe(unfinished_concrete_powder, Material.BLACK_CONCRETE_POWDER, Material.BLACK_DYE);

		registerStrippingRecipe(Material.OAK_LOG, Material.STRIPPED_OAK_LOG);
		registerStrippingRecipe(Material.BIRCH_LOG, Material.STRIPPED_BIRCH_LOG);
		registerStrippingRecipe(Material.ACACIA_LOG, Material.STRIPPED_ACACIA_LOG);
		registerStrippingRecipe(Material.SPRUCE_LOG, Material.STRIPPED_SPRUCE_LOG);
		registerStrippingRecipe(Material.DARK_OAK_LOG, Material.STRIPPED_DARK_OAK_LOG);
		registerStrippingRecipe(Material.CHERRY_LOG, Material.STRIPPED_CHERRY_LOG);
		registerStrippingRecipe(Material.WARPED_STEM, Material.STRIPPED_WARPED_STEM);
		registerStrippingRecipe(Material.CRIMSON_STEM, Material.STRIPPED_CRIMSON_STEM);
		registerStrippingRecipe(Material.JUNGLE_LOG, Material.STRIPPED_JUNGLE_LOG);
		registerStrippingRecipe(Material.BAMBOO_BLOCK, Material.STRIPPED_BAMBOO_BLOCK);


		//Electronics

		CustomItemStack cathode = ItemCreator.RegisterNewItem(new CustomItemStack("Cathode", Material.YELLOW_DYE, 10001));
		cathode.getRecipe(12, "III", "rIr", "III").AddMaterial('I', RecipeBuilder.ItemStackInput(tungsten_ingot)).AddMaterial('r', Material.REDSTONE).Finalize();
		
		CustomItemStack anode = ItemCreator.RegisterNewItem(new CustomItemStack("Anode", Material.YELLOW_DYE, 10002));
		anode.getRecipe(12, "III", "rIr", "III").AddMaterial('I', RecipeBuilder.ItemStackInput(platinum_ingot)).AddMaterial('r', Material.REDSTONE).Finalize();
		
		
		capacitor = ItemCreator.RegisterNewItem(new CustomItemStack("Capacitor", Material.YELLOW_DYE, 10003));
		capacitor.getRecipe(2, " G ", "CGA", " G ").AddMaterial('G', Material.GOLD_INGOT).AddMaterial('C', RecipeBuilder.ItemStackInput(cathode)).AddMaterial('A', RecipeBuilder.ItemStackInput(anode)).Finalize();
		
		resistor = ItemCreator.RegisterNewItem(new CustomItemStack("Resistor", Material.YELLOW_DYE, 10004));
		resistor.getRecipe(2, " G ", "CGA", " G ").AddMaterial('G', Material.IRON_INGOT).AddMaterial('C', RecipeBuilder.ItemStackInput(cathode)).AddMaterial('A', RecipeBuilder.ItemStackInput(anode)).Finalize();
	
		taser = ItemCreator.RegisterNewItem(new CustomItemStack("Taser", Material.IRON_SWORD, 10002));
		taser.getRecipe(1, "i i", "r r", "RiC").AddMaterial('i', Material.IRON_INGOT).AddMaterial('r', Material.REDSTONE).AddMaterial('R', RecipeBuilder.ItemStackInput(resistor)).AddMaterial('C', RecipeBuilder.ItemStackInput(capacitor)).Finalize();
		
		//Titanium
		
		titanium_ore = ItemCreator.RegisterNewItem(new CustomItemStack("Titanium Ore", Material.DEAD_BRAIN_CORAL_BLOCK, 10001));
		titanium_ore.AddLoreLine(ChatColor.GRAY + "Rutile -> From Compressor");
		BlockCreator.RegisterNewBlock(titanium_ore).SetConstBlock(false).SetMineAs(Material.IRON_ORE).SetRequiredTool("PICKAXE");
		
						
		titanium_ingot = ItemCreator.RegisterNewItem(new CustomItemStack("Titanium Ingot", Material.GREEN_DYE, 10001));
		titanium_ingot.getFurnaceRecipe(RecipeBuilder.ItemStackInput(titanium_ore), 5, 150, "titanium_from_ore");
		titanium_ingot.getBlastingRecipe(RecipeBuilder.ItemStackInput(titanium_ore), 5, 120, "titanium_from_ore_blasting");

		titanium_block = ItemCreator.RegisterNewItem(new CustomItemStack("Block Of Titanium", Material.GREEN_TERRACOTTA, 10002));
		BlockCreator.RegisterNewBlock(titanium_block).SetConstBlock(false).SetMineAs(Material.IRON_BLOCK).SetRequiredTool("PICKAXE");
				
		titanium_block.getRecipe(1, RecipeTemplates.Ingot_Block, "titanium_block").AddMaterial('I', RecipeBuilder.ItemStackInput(titanium_ingot)).Finalize();
		
		titanium_ingot.getRecipe(9, RecipeTemplates.Ingot_Middle, "titanium_ingot_from_block").AddMaterial('I', RecipeBuilder.ItemStackInput(titanium_block)).Finalize();
		
		
		//Other
		o2_bottle = ItemCreator.RegisterNewItem(new CustomItemStack("Bottle Of Oxygen", Material.GLASS_BOTTLE, 10001));
		o2_bottle.AddLoreLine(ChatColor.GRAY + "Glass Bottle -> From Compressor");
		o2_bottle.AddLoreLine(ChatColor.GRAY + "Water Bucket -> From Electrolyzer");
		
			
			
		rutile = ItemCreator.RegisterNewItem(new CustomItemStack("Rutile", Material.DEAD_BUBBLE_CORAL_BLOCK, 10003));
		rutile.AddLoreLine(ChatColor.GRAY + "Used in the compressor.");
		
		BlockCreator.RegisterNewBlock(rutile).SetConstBlock(false).SetMineAs(Material.COAL_ORE).SetRequiredTool("PICKAXE");
		
		rutile.getRecipe(6, "TTT", "OOO", "TTT").AddMaterial('T', RecipeBuilder.ItemStackInput(titanium_ore)).AddMaterial('O', RecipeBuilder.ItemStackInput(o2_bottle)).Finalize();
		
		new Ore(rutile, 27, true, 5, 44);
		
		silicon = ItemCreator.RegisterNewItem(new CustomItemStack("Silicon", Material.COAL, 20001));
		
		compressor = ItemCreator.RegisterNewItem(new CustomItemStack("Compressor", Material.IRON_BLOCK, 10001));
		BlockCreator.RegisterNewBlock(compressor, "core_mod.steam", 40, 9, "Compressor", new MachineConversion(new ItemStack(Material.COAL), silicon.GetMyItemStack()), new MachineConversion(rutile.GetMyItemStack(), titanium_ore.GetMyItemStack()), new MachineConversion(new ItemStack(Material.GLASS_BOTTLE, 1), o2_bottle.GetMyItemStack()), new MachineConversion(new ItemStack(Material.APPLE, 1), cider_vinegar.GetMyItemStack()), new MachineConversion(new ItemStack(Material.PUMPKIN_SEEDS, 1), cooking_oil.GetMyItemStack()), new MachineConversion(new ItemStack(Material.BEETROOT_SEEDS, 1), cooking_oil.GetMyItemStack()));
		
		//LEAD CONVERSION
			compressor.getRelatedBlock().getMachineTemplate().conversions.add(new MachineConversion(pure_lead_powder.GetMyItemStack(), lead_ingot.GetMyItemStack()));
			RecipeDisplay.CUSTOM_RECIPES.add(new CustomRecipeType("Compressing", compressor.GetMyItemStack(), "compress_lead_powder", Main.INSTANCE, 
					
					new CustomItemStack(ChatColor.RED + "-->", compressor.getType(), compressor.getCustomModelData()).AddLoreLine(ChatColor.GREEN + "Speed: 40mt").AddEnchant(Enchantment.LUCK, 1, true).AddFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS).GetMyItemStack(),
					
					lead_ingot.GetMyItemStack(), pure_lead_powder.GetMyItemStack()));
			
		compressor.getRecipe(1, "iRi", "P P", "iCi").AddMaterial('i', Material.IRON_INGOT).AddMaterial('R', RecipeBuilder.ItemStackInput(resistor)).AddMaterial('C', RecipeBuilder.ItemStackInput(capacitor)).AddMaterial('P', Material.PISTON).Finalize();
		
		elevator = ItemCreator.RegisterNewItem(new CustomItemStack("Elevator", Material.BLACK_CONCRETE, 10001));
		BlockCreator.RegisterNewBlock(elevator);
				
		elevator_shaft = ItemCreator.RegisterNewItem(new CustomItemStack("Elevator Shaft", Material.BLACK_CONCRETE, 10002));
		BlockCreator.RegisterNewBlock(elevator_shaft);
		
		
		DrillsRegistry();
				
		ZincRegistry();
		
		//ZINC LEAD RECIPE
			zinc_lead_alloy.getRecipe(2, "LZL", "ZLZ").AddMaterial('L', RecipeBuilder.ItemStackInput(lead_bullion)).AddMaterial('Z', RecipeBuilder.ItemStackInput(zinc)).Finalize();
		
		CustomItemStack steel = ItemCreator.RegisterNewItem(new CustomItemStack("Steel Ingot", Material.IRON_INGOT, 15001));
		steel.getFurnaceRecipe(Material.IRON_BLOCK, 7, 300, "smelt_steel");
		steel.getBlastingRecipe(Material.IRON_BLOCK, 7, 200, "blast_steel");

		steel_ingot = steel;
		
		CustomItemStack galvanisedSteel = ItemCreator.RegisterNewItem(new CustomItemStack("Galvanised Steel", Material.IRON_INGOT, 15002));
		Bukkit.getServer().addRecipe(galvanisedSteel.GenUnshaped("galvanise_steel").addIngredient(RecipeBuilder.ItemStackInput(steel)).addIngredient(RecipeBuilder.ItemStackInput(zinc)));
		
		CustomItemStack unfinished_high_carbon_steel = ItemCreator.RegisterNewItem(new CustomItemStack("Unfinished High Carbon Steel", Material.IRON_INGOT, 15004));
		unfinished_high_carbon_steel.getRecipe(1, "CCC", "CGC", "CCC").AddMaterial('C', Material.COAL_BLOCK).AddMaterial('G', RecipeBuilder.ItemStackInput(galvanisedSteel)).Finalize();
		
		high_carbon_steel = ItemCreator.RegisterNewItem(new CustomItemStack("High Carbon Steel", Material.IRON_INGOT, 15003));
		high_carbon_steel.getFurnaceRecipe(RecipeBuilder.ItemStackInput(unfinished_high_carbon_steel), 8, 400, "create_high_carbon_steel");
		high_carbon_steel.getBlastingRecipe(RecipeBuilder.ItemStackInput(unfinished_high_carbon_steel), 8, 400, "create_high_carbon_steel_blasting");
		
		CustomItemStack galvanisedNetheriteSword = ItemCreator.RegisterNewItem(new CustomItemStack("Galvanised Netherite Sword", Material.NETHERITE_SWORD, 10001).Attribute(Attribute.GENERIC_ATTACK_SPEED, -1.3f, EquipmentSlot.HAND));
		galvanisedNetheriteSword.getRecipe(1, "GGG", "GSG", "GGG", "craft_gal_n_sword").AddMaterial('G', RecipeBuilder.ItemStackInput(galvanisedSteel)).AddMaterial('S', Material.NETHERITE_SWORD).Finalize();
		
		
		CustomItemStack brass = ItemCreator.RegisterNewItem(new CustomItemStack("Brass Ingot", Material.IRON_INGOT, 11001));
		Bukkit.getServer().addRecipe(brass.GenUnshaped("brass_ingot_craft").addIngredient(RecipeBuilder.ItemStackInput(zinc)).addIngredient(RecipeBuilder.ItemStackInput(tungsten_ingot)));
		trumpet = ItemCreator.RegisterNewItem(new CustomItemStack("Trumpet", Material.IRON_SWORD, 60001)); 
		trumpet.getRecipe(1, "IBB", "B B", "BBB").AddMaterial('I', Material.IRON_INGOT).AddMaterial('B', RecipeBuilder.ItemStackInput(brass)).Finalize();
		
		
		ItemCreator.RegisterNewItem(new CustomItemStack("Brass Helmet", Material.IRON_HELMET, 10002).Attribute(Attribute.GENERIC_ARMOR_TOUGHNESS, 1f, EquipmentSlot.HEAD).Attribute(Attribute.GENERIC_ARMOR, 2f, EquipmentSlot.HEAD)).AddLoreLine(ChatColor.GRAY + "*Brass Armor*").getRecipe(1, "III", "I I", "   ").AddMaterial('I', RecipeBuilder.ItemStackInput(brass)).Finalize();
		tungsten_chestplate = ItemCreator.RegisterNewItem(new CustomItemStack("Brass Chestplate", Material.IRON_CHESTPLATE, 10002).Attribute(Attribute.GENERIC_ARMOR_TOUGHNESS, 1f, EquipmentSlot.CHEST).Attribute(Attribute.GENERIC_ARMOR, 7f, EquipmentSlot.CHEST)).AddLoreLine(ChatColor.GRAY + "*Brass Armor*");
		tungsten_chestplate.getRecipe(1, "I I", "III", "III").AddMaterial('I', RecipeBuilder.ItemStackInput(brass)).Finalize();
		ItemCreator.RegisterNewItem(new CustomItemStack("Brass Leggings", Material.IRON_LEGGINGS, 10002).Attribute(Attribute.GENERIC_ARMOR_TOUGHNESS, 1f, EquipmentSlot.LEGS).Attribute(Attribute.GENERIC_ARMOR, 6f, EquipmentSlot.LEGS)).AddLoreLine(ChatColor.GRAY + "*Brass Armor*").getRecipe(1, "III", "I I", "I I").AddMaterial('I', RecipeBuilder.ItemStackInput(brass)).Finalize();
		ItemCreator.RegisterNewItem(new CustomItemStack("Brass Boots", Material.IRON_BOOTS, 10002).Attribute(Attribute.GENERIC_ARMOR_TOUGHNESS, 1f, EquipmentSlot.FEET).Attribute(Attribute.GENERIC_ARMOR, 2f, EquipmentSlot.FEET)).AddLoreLine(ChatColor.GRAY + "*Brass Armor*").getRecipe(1, "   ", "I I", "I I").AddMaterial('I', RecipeBuilder.ItemStackInput(brass)).Finalize();

		CustomItemStack screw = ItemCreator.RegisterNewItem(new CustomItemStack("Screw", Material.RED_DYE, 10002));
		
		screw.getRecipe(12, "MMM", "M M", "MMM", "titanium_screw").AddMaterial('M', RecipeBuilder.ItemStackInput(titanium_ingot)).Finalize();
		screw.getRecipe(10, "MMM", "M M", "MMM", "brass_screw").AddMaterial('M', RecipeBuilder.ItemStackInput(brass)).Finalize();
		screw.getRecipe(10, "MMM", "M M", "MMM", "steel_screw").AddMaterial('M', RecipeBuilder.ItemStackInput(steel)).Finalize();

		CustomItemStack hinge = ItemCreator.RegisterNewItem(new CustomItemStack("Hinge", Material.RED_DYE, 10001));
		hinge.getRecipe(5, "SBS", " B ", "SBS").AddMaterial('S', RecipeBuilder.ItemStackInput(screw)).AddMaterial('B', RecipeBuilder.ItemStackInput(brass)).Finalize();
		
		CustomItemStack casing = ItemCreator.RegisterNewItem(new CustomItemStack("Casing", Material.GOLD_BLOCK, 10005));
		Casing = casing;
		casing.getRecipe(1, "SHS", "I I", "SHS").AddMaterial('S', RecipeBuilder.ItemStackInput(screw)).AddMaterial('H', RecipeBuilder.ItemStackInput(hinge)).AddMaterial('I', Material.IRON_INGOT).Finalize();
		
		BlockCreator.RegisterNewBlock(casing).SetConstBlock(false).SetMineAs(Material.ANDESITE);
		
		com.willm.ModAPI.Voltage.Main.er1.getRecipe(8, "SCS", "PDP", "SCS", "craft_er_one").AddMaterial('S', RecipeBuilder.ItemStackInput(screw)).AddMaterial('C', RecipeBuilder.ItemStackInput(casing)).AddMaterial('P', RecipeBuilder.ItemStackInput(galvanisedSteel)).AddMaterial('D', RecipeBuilder.ItemStackInput(capacitor)).Finalize();
		com.willm.ModAPI.Voltage.Main.er2.getRecipe(8, "SCS", "PDP", "SCS", "craft_er_two").AddMaterial('S', RecipeBuilder.ItemStackInput(screw)).AddMaterial('C', RecipeBuilder.ItemStackInput(casing)).AddMaterial('P', RecipeBuilder.ItemStackInput(resistor)).AddMaterial('D', RecipeBuilder.ItemStackInput(com.willm.ModAPI.Voltage.Main.er1)).Finalize();
		com.willm.ModAPI.Voltage.Main.er3.getRecipe(8, "SCS", "PDP", "SCS", "craft_er_three").AddMaterial('S', RecipeBuilder.ItemStackInput(hinge)).AddMaterial('C', RecipeBuilder.ItemStackInput(steel_ingot)).AddMaterial('P', RecipeBuilder.ItemStackInput(zinc)).AddMaterial('D', RecipeBuilder.ItemStackInput(com.willm.ModAPI.Voltage.Main.er2)).Finalize();
		com.willm.ModAPI.Voltage.Main.er4.getRecipe(8, "SCS", "PDP", "SCS", "craft_er_four").AddMaterial('S', Material.DIAMOND).AddMaterial('C', RecipeBuilder.ItemStackInput(high_carbon_steel)).AddMaterial('P', RecipeBuilder.ItemStackInput(zinc)).AddMaterial('D', RecipeBuilder.ItemStackInput(com.willm.ModAPI.Voltage.Main.er3)).Finalize();
		com.willm.ModAPI.Voltage.Main.er5.getRecipe(8, "SCS", "PDP", "SCS", "craft_er_five").AddMaterial('S', Material.DIAMOND).AddMaterial('C', RecipeBuilder.ItemStackInput(casing)).AddMaterial('P', RecipeBuilder.ItemStackInput(zinc)).AddMaterial('D', RecipeBuilder.ItemStackInput(com.willm.ModAPI.Voltage.Main.er4)).Finalize();

		
		CustomItemStack photovoltaic_cell = ItemCreator.RegisterNewItem(new CustomItemStack("Photovoltaic Cell", Material.COAL, 30001));
		photovoltaic_cell.getRecipe(6, "SBS", "BSB", "SBS", "pva_cell").AddMaterial('S', RecipeBuilder.ItemStackInput(silicon)).AddMaterial('B', RecipeBuilder.ItemStackInput(brass)).Finalize();
		
		generator_core = ItemCreator.RegisterNewItem(new CustomItemStack("Generator Core", Material.GOLD_BLOCK, 50001));
		generator_core.getRecipe(1, "RCR", "ZFZ", "RCR", "gc_gen").AddMaterial('R', RecipeBuilder.ItemStackInput(resistor)).AddMaterial('C', RecipeBuilder.ItemStackInput(capacitor)).AddMaterial('Z', RecipeBuilder.ItemStackInput(high_carbon_steel)).AddMaterial('F', Material.FURNACE).Finalize();
		
		BlockCreator.RegisterNewBlock(generator_core).SetConstBlock(false).SetMineAs(Material.GOLD_BLOCK);
		
		com.willm.CoreMOD.Power.SolarPanel sp = new com.willm.CoreMOD.Power.SolarPanel();
		com.willm.ModAPI.Voltage.Main.energyUsers.add(sp);
		
		sp.GetBlockRef().getRootItem().getRecipe(2, "CCC", "RcR", "SSS", "solar_panel_craft").AddMaterial('C', RecipeBuilder.ItemStackInput(photovoltaic_cell)).AddMaterial('R', RecipeBuilder.ItemStackInput(resistor)).AddMaterial('c', RecipeBuilder.ItemStackInput(generator_core)).AddMaterial('S', RecipeBuilder.ItemStackInput(steel_ingot)).Finalize();
		
		com.willm.CoreMOD.Power.GeothermalGenerator gg = new com.willm.CoreMOD.Power.GeothermalGenerator();
		com.willm.ModAPI.Voltage.Main.energyUsers.add(gg);
				
		com.willm.CoreMOD.Power.CoalGenerator cg = new com.willm.CoreMOD.Power.CoalGenerator();
		com.willm.ModAPI.Voltage.Main.energyUsers.add(cg);
		
		com.willm.CoreMOD.Power.HydrogenGenerator hg = new com.willm.CoreMOD.Power.HydrogenGenerator();
		com.willm.ModAPI.Voltage.Main.energyUsers.add(hg);
		
		cg.GetBlockRef().getRootItem().getRecipe(1, "CSC", "cFr", "CSC", "coal_generator_craft").AddMaterial('C', RecipeBuilder.ItemStackInput(casing)).AddMaterial('S', RecipeBuilder.ItemStackInput(galvanisedSteel)).AddMaterial('c', RecipeBuilder.ItemStackInput(capacitor)).AddMaterial('r', RecipeBuilder.ItemStackInput(resistor)).AddMaterial('F', RecipeBuilder.ItemStackInput(generator_core)).Finalize();
	
		netherite_core = ItemCreator.RegisterNewItem(new CustomItemStack("Netherite Casing", Material.GOLD_BLOCK, 50002));
		netherite_core.getRecipe(1, "NSN", "SCS", "NSN").AddMaterial('S', RecipeBuilder.ItemStackInput(galvanisedSteel)).AddMaterial('N', Material.NETHERITE_SCRAP).AddMaterial('C', RecipeBuilder.ItemStackInput(casing)).Finalize();
		
		BlockCreator.RegisterNewBlock(netherite_core).SetConstBlock(false).SetMineAs(Material.NETHERITE_BLOCK);
		
		gg.GetBlockRef().getRootItem().getRecipe(2, "SCS", "cfc", "SBS", "geo_gen_craft").AddMaterial('S', RecipeBuilder.ItemStackInput(galvanisedSteel)).AddMaterial('C', RecipeBuilder.ItemStackInput(netherite_core)).AddMaterial('c', RecipeBuilder.ItemStackInput(capacitor)).AddMaterial('f', RecipeBuilder.ItemStackInput(generator_core)).AddMaterial('B', Material.BUCKET).Finalize();
		
		com.willm.CoreMOD.Power.Crusher crusher = new com.willm.CoreMOD.Power.Crusher();
		com.willm.ModAPI.Voltage.Main.energyUsers.add(crusher);
		com.willm.ModAPI.Voltage.Main.energyRecievers.add(crusher);

		crusher.GetBlockRef().getRootItem().getRecipe(1, "ZCZ", "cFc", "ZCZ", "crusher_craft").AddMaterial('Z', RecipeBuilder.ItemStackInput(zinc)).AddMaterial('C', RecipeBuilder.ItemStackInput(casing)).AddMaterial('F', RecipeBuilder.ItemStackInput(netherite_core)).AddMaterial('c', Material.PISTON).Finalize();

		//HIHGH CARBON STEEL ROD
			CustomItemStack high_carbon_steel_rod = ItemCreator.RegisterNewItem(new CustomItemStack("High Carbon Steel Rod", Material.IRON_INGOT, 15005));
			high_carbon_steel_rod.getRecipe(4, "S", "I", "S").AddMaterial('S', RecipeBuilder.ItemStackInput(screw)).AddMaterial('I', RecipeBuilder.ItemStackInput(high_carbon_steel)).Finalize();
		
		//WINDOW SHADE
			CustomItemStack window_shade = ItemCreator.RegisterNewItem(new CustomItemStack("Window Shade", Material.WARPED_TRAPDOOR, 70001));
			window_shade.getRecipe(2, "RWR", "S S").AddMaterial('R', Material.RED_WOOL).AddMaterial('W', Material.WHITE_WOOL).AddMaterial('S', RecipeBuilder.ItemStackInput(high_carbon_steel_rod)).Finalize();
		
			BlockCreator.RegisterNewBlock(window_shade, new CustomBaseMaterialRetainingBlock(window_shade)).SetDirectional(BlockDirectionData.INVERSE_PLAYER_RELATIVE);
			
		hg.GetBlockRef().getRootItem().getRecipe(1, "CSC", "cFr", "CgC", "hydrogen_generator_craft").AddMaterial('g', RecipeBuilder.ItemStackInput(generator_core)).AddMaterial('C', RecipeBuilder.ItemStackInput(casing)).AddMaterial('S', RecipeBuilder.ItemStackInput(galvanisedSteel)).AddMaterial('c', RecipeBuilder.ItemStackInput(capacitor)).AddMaterial('r', RecipeBuilder.ItemStackInput(resistor)).AddMaterial('F', RecipeBuilder.ItemStackInput(netherite_core)).Finalize();

		wireless_redstone_activator = ItemCreator.RegisterNewItem(new CustomItemStack("Wireless Redstone Activator", Material.REDSTONE_TORCH, 10001));
		
		Bukkit.getServer().addRecipe(wireless_redstone_activator.GenUnshaped("craft_wireless_redstone").addIngredient(Material.REDSTONE_TORCH).addIngredient(RecipeBuilder.ItemStackInput(capacitor)).addIngredient(RecipeBuilder.ItemStackInput(resistor)).addIngredient(Material.STONE_BUTTON));
		
		CustomItemStack MachineExtractor = com.willm.ModAPI.Main.MExtractor;
		MachineExtractor.getRecipe(1, "STS", "PRp", "STS").AddMaterial('S', RecipeBuilder.ItemStackInput(galvanisedSteel)).AddMaterial('T', RecipeBuilder.ItemStackInput(titanium_ingot)).AddMaterial('P', Material.STICKY_PISTON).AddMaterial('R', Material.REDSTONE).AddMaterial('p', RecipeBuilder.ItemStackInput(platinum_block)).Finalize();
		
		com.willm.ModAPI.Main.MInserter.getRecipe(1, "STS", "PRp", "STS").AddMaterial('S', RecipeBuilder.ItemStackInput(galvanisedSteel)).AddMaterial('T', RecipeBuilder.ItemStackInput(tungsten_ingot)).AddMaterial('P', Material.HOPPER).AddMaterial('R', Material.REDSTONE).AddMaterial('p', Material.GOLD_BLOCK).Finalize();

		//Aluminum
				bauxite = ItemCreator.RegisterNewItem(new CustomItemStack("Bauxite", Material.GUNPOWDER, 21052));
				bauxite.AddLoreLine(ChatColor.GRAY + "Found in gravel deposits.");
				bauxite.AddLoreLine(ChatColor.GRAY + "Gravel -> Centrifuge");
				
				//Adds display for command.
					RecipeDisplay.CUSTOM_RECIPES.add(new CustomRecipeType("Gathering", Material.DIAMOND_SHOVEL, "gather_bauxite", Main.INSTANCE, 
							
							new CustomItemStack(ChatColor.RED + "Mine Gravel", Material.DIAMOND_SHOVEL, 0).AddFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS).AddLoreLine(ChatColor.GREEN + "15% Drop Chance").GetMyItemStack(),
							
							bauxite.GetMyItemStack(), new ItemStack(Material.GRAVEL, 7)));
					
				aluminum = ItemCreator.RegisterNewItem(new CustomItemStack("Aluminum Ingot", Material.GUNPOWDER, 21053));
				aluminum.getFurnaceRecipe(RecipeBuilder.ItemStackInput(bauxite), 3, 160, "smelt_aluminum_ingot_from_bauxite");
				aluminum.getBlastingRecipe(RecipeBuilder.ItemStackInput(bauxite), 3, 120, "blast_aluminum_ingot_from_bauxite");

				aluminum_alloy = ItemCreator.RegisterNewItem(new CustomItemStack("Aluminum Alloy", Material.GUNPOWDER, 21054));
				Bukkit.getServer().addRecipe(aluminum_alloy.GenUnshaped("aluminum_alloy_craft").addIngredient(RecipeBuilder.ItemStackInput(silicon)).addIngredient(RecipeBuilder.ItemStackInput(zinc)).addIngredient(RecipeBuilder.ItemStackInput(aluminum)).addIngredient(RecipeBuilder.ItemStackInput(aluminum)));
		
				
		//Electric Furnace
				com.willm.CoreMOD.Power.ElectricFurnace ef = new com.willm.CoreMOD.Power.ElectricFurnace();
				com.willm.ModAPI.Voltage.Main.energyUsers.add(ef);
				com.willm.ModAPI.Voltage.Main.energyRecievers.add(ef);
				ef.GetBlockRef().getRootItem().getRecipe(1, "ZCZ", "CFC", "ZCZ", "ef_craft").AddMaterial('Z', RecipeBuilder.ItemStackInput(zinc)).AddMaterial('C', RecipeBuilder.ItemStackInput(casing)).AddMaterial('F', Material.BLAST_FURNACE).Finalize();

		
		//Wind Generator
				com.willm.CoreMOD.Power.WindGenerator wg = new com.willm.CoreMOD.Power.WindGenerator();
				com.willm.ModAPI.Voltage.Main.energyUsers.add(wg);
				wg.GetBlockRef().getRootItem().getRecipe(2, "SRS", "CGC", "TCT", "wg_craft").AddMaterial('C', RecipeBuilder.ItemStackInput(casing)).AddMaterial('T', RecipeBuilder.MultiItemStackInput(titanium_block, tungsten_block, platinum_block)).AddMaterial('G', RecipeBuilder.ItemStackInput(generator_core)).AddMaterial('R', RecipeBuilder.ItemStackInput(iron_rod)).AddMaterial('S', RecipeBuilder.ItemStackInput(steel_ingot)).Finalize();
				
		engine = ItemCreator.RegisterNewItem(new CustomItemStack("Engine", Material.GOLD_BLOCK, 60001));
		BlockCreator.RegisterNewBlock(engine).SetConstBlock(false).SetMineAs(Material.DIORITE);
				
		
		
		CustomItemStack jet_booster = ItemCreator.RegisterNewItem(new CustomItemStack("Jet Booster", Material.IRON_INGOT, 20001));
		
		jet_booster.getRecipe(2, "TtT", "PKP", "EtE").AddMaterial('T', RecipeBuilder.ItemStackInput(tungsten_block)).AddMaterial('t', RecipeBuilder.ItemStackInput(titanium_block)).AddMaterial('P', RecipeBuilder.ItemStackInput(platinum_block)).AddMaterial('E', RecipeBuilder.ItemStackInput(engine)).AddMaterial('K', RecipeBuilder.ItemStackInput(kerosene)).Finalize();
		
		jetpack = ItemCreator.RegisterNewItem(new CustomItemStack("Jetpack", Material.ELYTRA, 20001)).AddLoreLine(ChatColor.RED + "Fuel: 0 mB").AddLoreLine(ChatColor.GRAY + "*Jetpack*").AddLoreLine(ChatColor.GRAY + "Uses kerosene (injector)").Attribute(Attribute.GENERIC_ARMOR_TOUGHNESS, 1f, EquipmentSlot.CHEST).Attribute(Attribute.GENERIC_ARMOR, 7, EquipmentSlot.CHEST);
		
		jetpack.getRecipe(1, "tKt", "ETE", "BBB").AddMaterial('t', RecipeBuilder.ItemStackInput(titanium_ingot)).AddMaterial('T', Material.ELYTRA).AddMaterial('K', RecipeBuilder.ItemStackInput(kerosene)).AddMaterial('E', RecipeBuilder.ItemStackInput(engine)).AddMaterial('B', RecipeBuilder.ItemStackInput(jet_booster)).Finalize();
	
		ItemCreator.RegisterNewItem(new CustomItemStack("Creative Jetpack", Material.ELYTRA, 20001)).AddLoreLine(ChatColor.GREEN + "Fuel: 1000000 mB").AddLoreLine(ChatColor.GRAY + "*Jetpack*").AddLoreLine(ChatColor.GRAY + "Uses kerosene (injector)").Attribute(Attribute.GENERIC_ARMOR_TOUGHNESS, 1f, EquipmentSlot.CHEST).Attribute(Attribute.GENERIC_ARMOR, 7, EquipmentSlot.CHEST);		
		
		nozzle = ItemCreator.RegisterNewItem(new CustomItemStack("Nozzle", Material.WOODEN_HOE, 10002));
		nozzle.getRecipe(1, "TTT", "TBP", "TTT").AddMaterial('T', RecipeBuilder.ItemStackInput(tungsten_ingot)).AddMaterial('B', Material.BUCKET).AddMaterial('P', Material.PISTON).Finalize();
		
		oil_gun = ItemCreator.RegisterNewItem(new CustomItemStack("Oil Gun", Material.WOODEN_HOE, 10001)).AddLoreLine(ChatColor.RED + "Fuel: 0 mB").AddLoreLine(ChatColor.GRAY + "Uses butane (injector)");
		oil_gun.getRecipe(1, "   ", " BN", " R ").AddMaterial('R', RecipeBuilder.ItemStackInput(iron_rod)).AddMaterial('B', Material.BUCKET).AddMaterial('N', RecipeBuilder.ItemStackInput(nozzle)).Finalize();
		
		ItemCreator.RegisterNewItem(new CustomItemStack("Creative Oil Gun", Material.WOODEN_HOE, 10001)).AddLoreLine(ChatColor.GREEN + "Fuel: 1000000 mB").AddLoreLine(ChatColor.GRAY + "Uses butane (injector)");
		
		//Injector
				com.willm.CoreMOD.Power.Injector ij = new com.willm.CoreMOD.Power.Injector();
				com.willm.ModAPI.Voltage.Main.energyUsers.add(ij);
				com.willm.ModAPI.Voltage.Main.energyRecievers.add(ij);
				ij.GetBlockRef().getRootItem().getRecipe(1, "TTT", "PBP", "CTC").AddMaterial('T', RecipeBuilder.ItemStackInput(titanium_ingot)).AddMaterial('P', RecipeBuilder.ItemStackInput(platinum_ingot)).AddMaterial('B', Material.BUCKET).AddMaterial('C', RecipeBuilder.ItemStackInput(casing)).Finalize();
		
	
				
		oven.getRecipe(1, " B ", "QTQ", "QQQ").AddMaterial('B', Material.BOWL).AddMaterial('Q', Material.QUARTZ_BRICKS).AddMaterial('T', RecipeBuilder.ItemStackInput(tungsten_block)).Finalize();
		cutting_board.getRecipe(1, "WWW", "QQQ", "QQQ").AddMaterial('Q', Material.QUARTZ_BLOCK).AddMaterial('W', RecipeBuilder.MultiMaterialInput(MaterialRecipeTemplate.PLANKS)).Finalize();
	
		tree_tap = ItemCreator.RegisterNewItem(new CustomItemStack("Tree Tap", Material.STONE, 10001));
		Bukkit.getServer().addRecipe(tree_tap.GenUnshaped("craft_tree_tap").addIngredient(Material.BUCKET).addIngredient(RecipeBuilder.ItemStackInput(nozzle)).addIngredient(RecipeBuilder.ItemStackInput(iron_rod)).addIngredient(Material.IRON_INGOT));
		
		tree_tap.AddLoreLine(ChatColor.GRAY + "Place on top of a log to harvest sap.");
		
		BlockCreator.RegisterNewBlock(tree_tap);
		
		sap = ItemCreator.RegisterNewItem(new CustomItemStack("Sap", Material.COAL, 10423));
		sap.AddLoreLine(ChatColor.GRAY + "-> From Tree Tap");
		
		CustomItemStack sap_piston = ItemCreator.RegisterNewItem(new CustomItemStack("Sappy Piston", Material.STICKY_PISTON, 10001));
		
		Bukkit.getServer().addRecipe(sap_piston.GenUnshaped("piston_with_sap", 1).addIngredient(Material.PISTON).addIngredient(RecipeBuilder.ItemStackInput(sap)));
		
		syrup = ItemCreator.RegisterNewItem(new CustomItemStack("Syrup", Material.HONEY_BOTTLE, 15023));
		syrup.AddLoreLine(ChatColor.BLUE + "2 Sap -> Oven (Bowl)");
		
		BlockEvents.ovenRecipes.add(new OvenRecipe(syrup.GetAmountClone(2), false, sap.GetAmountClone(2)));
				
		CustomItemStack glazed_ham = ItemCreator.RegisterNewItem(new CustomItemStack("Glazed Ham", Material.COOKED_PORKCHOP, 10004));
		glazed_ham.AddFoodModifier(1, 2).AddLoreLine(ChatColor.BLUE + "Syrup + Ham -> Oven (Plate)");
		
		BlockEvents.ovenRecipes.add(new OvenRecipe(glazed_ham.GetAmountClone(2), true, syrup.GetMyItemStack(), ham.GetMyItemStack()));
		
		spring_water = ItemCreator.RegisterNewItem(new CustomItemStack("Spring Water", Material.ORANGE_DYE, 30001));
		
		spring_water_source = ItemCreator.RegisterNewItem(new CustomItemStack("Spring Water Soruce", Material.DIAMOND_BLOCK, 20001));
		LiquidBlock spring_water_source_b = BlockCreator.RegisterNewLiquid(spring_water_source);
		
		spring_water_source_b.LiquidDamage = -0.5;
		
		com.willm.ModAPI.Blocks.BlockEvents.RegisterBucket(spring_water, spring_water_source_b);
				
		CustomItemStack cement_powder = ItemCreator.RegisterNewItem(new CustomItemStack("Cement Powder", Material.BROWN_CONCRETE, 51023));
		BlockCreator.RegisterNewBlock(cement_powder).SetConstBlock(false).SetMineAs(Material.CLAY);
		
		
		rotary_kiln = ItemCreator.RegisterNewItem(new CustomItemStack("Rotary Kiln", Material.BROWN_CONCRETE, 10001));
		BlockCreator.RegisterNewBlock(rotary_kiln, "core_mod.kiln_spin", 200, 9, "Rotary Kiln", new MachineConversion(limestone_block.GetMyItemStack(), liquid_cement.GetMyItemStack()),
				new MachineConversion(liquid_cement.GetMyItemStack(), cement_powder.GetAmountClone(4)));
		
		//Kiln LIQUID CEMENT
				RecipeDisplay.CUSTOM_RECIPES.add(new CustomRecipeType("Rotary Kiln", rotary_kiln.GetMyItemStack(), "kiln_limestone", Main.INSTANCE, 
						
						new CustomItemStack(ChatColor.RED + "-->", rotary_kiln.getType(), rotary_kiln.getCustomModelData()).AddLoreLine(ChatColor.GREEN + "Speed: 200mt").AddEnchant(Enchantment.LUCK, 1, true).AddFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS).GetMyItemStack(),
						
						liquid_cement.GetMyItemStack(), limestone_block.GetMyItemStack()));
				
		//Kiln CEMENT POWDER
				RecipeDisplay.CUSTOM_RECIPES.add(new CustomRecipeType("Rotary Kiln", rotary_kiln.GetMyItemStack(), "kiln_liquid_cement", Main.INSTANCE, 
						
						new CustomItemStack(ChatColor.RED + "-->", rotary_kiln.getType(), rotary_kiln.getCustomModelData()).AddLoreLine(ChatColor.GREEN + "Speed: 200mt").AddEnchant(Enchantment.LUCK, 1, true).AddFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS).GetMyItemStack(),
						
						cement_powder.GetAmountClone(4), liquid_cement.GetMyItemStack()));
		
		//COBALT
				cobalt_ingot = ItemCreator.RegisterNewItem(new CustomItemStack("Cobalt Ingot", Material.IRON_INGOT, 16230));
				
				ItemCreator.RegisterNewItem(new CustomItemStack("Cobalt Pickaxe", Material.DIAMOND_PICKAXE, 20001)).getRecipe(1, "III", " S ", " S ").AddMaterial('I', RecipeBuilder.ItemStackInput(cobalt_ingot)).AddMaterial('S', Material.STICK).Finalize();
				ItemCreator.RegisterNewItem(new CustomItemStack("Cobalt Axe", Material.DIAMOND_AXE, 20001)).getRecipe(1, "II ", "IS ", " S ").AddMaterial('I', RecipeBuilder.ItemStackInput(cobalt_ingot)).AddMaterial('S', Material.STICK).Finalize();
				ItemCreator.RegisterNewItem(new CustomItemStack("Cobalt Hoe", Material.DIAMOND_HOE, 20001)).getRecipe(1, "II ", " S ", " S ").AddMaterial('I', RecipeBuilder.ItemStackInput(cobalt_ingot)).AddMaterial('S', Material.STICK).Finalize();
				ItemCreator.RegisterNewItem(new CustomItemStack("Cobalt Shovel", Material.DIAMOND_SHOVEL, 20001)).getRecipe(1, " I ", " S ", " S ").AddMaterial('I', RecipeBuilder.ItemStackInput(cobalt_ingot)).AddMaterial('S', Material.STICK).Finalize();
				ItemCreator.RegisterNewItem(new CustomItemStack("Cobalt Sword", Material.DIAMOND_SWORD, 20001)).getRecipe(1, " I ", " I ", " S ").AddMaterial('I', RecipeBuilder.ItemStackInput(cobalt_ingot)).AddMaterial('S', Material.STICK).Finalize();
			
					//Armor
				ItemCreator.RegisterNewItem(new CustomItemStack("Cobalt Helmet", Material.DIAMOND_HELMET, 20001)).AddLoreLine(ChatColor.GRAY + "*Cobalt Armor*").getRecipe(1, "III", "I I", "   ").AddMaterial('I', RecipeBuilder.ItemStackInput(cobalt_ingot)).Finalize();
				ItemCreator.RegisterNewItem(new CustomItemStack("Cobalt Chestplate", Material.DIAMOND_CHESTPLATE, 20001)).AddLoreLine(ChatColor.GRAY + "*Cobalt Armor*")
				.getRecipe(1, "I I", "III", "III").AddMaterial('I', RecipeBuilder.ItemStackInput(cobalt_ingot)).Finalize();
				ItemCreator.RegisterNewItem(new CustomItemStack("Cobalt Leggings", Material.DIAMOND_LEGGINGS, 20001)).AddLoreLine(ChatColor.GRAY + "*Cobalt Armor*").getRecipe(1, "III", "I I", "I I").AddMaterial('I', RecipeBuilder.ItemStackInput(cobalt_ingot)).Finalize();
				ItemCreator.RegisterNewItem(new CustomItemStack("Cobalt Boots", Material.DIAMOND_BOOTS, 20001)).AddLoreLine(ChatColor.GRAY + "*Cobalt Armor*").getRecipe(1, "   ", "I I", "I I").AddMaterial('I', RecipeBuilder.ItemStackInput(cobalt_ingot)).Finalize();
				
				
		cement_powder.AddLoreLine(ChatColor.GRAY + "Made in the Rotary Kiln.");
		Bukkit.getServer().addRecipe(unfinished_concrete_powder.GenUnshaped("craft_concrete_pow_from_cement_pow", 4).addIngredient(Material.GRAVEL).addIngredient(Material.SAND).addIngredient(Material.GRAVEL).addIngredient(Material.SAND).addIngredient(RecipeBuilder.ItemStackInput(cement_powder)).addIngredient(RecipeBuilder.ItemStackInput(cement_powder)).addIngredient(RecipeBuilder.ItemStackInput(cement_powder)).addIngredient(RecipeBuilder.ItemStackInput(cement_powder)));
		
		gear = ItemCreator.RegisterNewItem(new CustomItemStack("Gear", Material.BLUE_DYE, 60001));
		gear.getRecipe(4, "SW ", "WRW", " WS").AddMaterial('W', RecipeBuilder.MultiMaterialInput(Material.OAK_PLANKS, Material.SPRUCE_PLANKS, Material.DARK_OAK_PLANKS, Material.ACACIA_PLANKS, Material.JUNGLE_PLANKS, Material.CHERRY_PLANKS, Material.BAMBOO_PLANKS, Material.BIRCH_PLANKS, Material.CRIMSON_PLANKS, Material.WARPED_PLANKS, Material.MANGROVE_PLANKS)).AddMaterial('S', RecipeBuilder.ItemStackInput(screw)).AddMaterial('R', RecipeBuilder.ItemStackInput(iron_rod)).Finalize();
		
		gearshift = ItemCreator.RegisterNewItem(new CustomItemStack("Gearshift", Material.BLUE_DYE, 60002));
		gearshift.getRecipe(1, "G  ", "SRS", "  G").AddMaterial('G', RecipeBuilder.ItemStackInput(gear)).AddMaterial('R', RecipeBuilder.ItemStackInput(iron_rod)).AddMaterial('S', RecipeBuilder.ItemStackInput(screw)).Finalize();
		
		electronic_gearshift = ItemCreator.RegisterNewItem(new CustomItemStack("Electronic Gearshift", Material.BLUE_DYE, 60003));
		electronic_gearshift.getRecipe(1, " C ", " G ", " R ").AddMaterial('C', RecipeBuilder.ItemStackInput(capacitor)).AddMaterial('R', RecipeBuilder.ItemStackInput(resistor)).AddMaterial('G', RecipeBuilder.ItemStackInput(gearshift)).Finalize();
		
		rotary_kiln.getRecipe(1, "PGP", "CBC", "cEc").AddMaterial('G', RecipeBuilder.ItemStackInput(galvanisedSteel)).AddMaterial('P', RecipeBuilder.ItemStackInput(platinum_block)).AddMaterial('C', RecipeBuilder.ItemStackInput(casing)).AddMaterial('B', Material.BUCKET).AddMaterial('E', RecipeBuilder.ItemStackInput(engine)).AddMaterial('c', RecipeBuilder.ItemStackInput(electronic_gearshift)).Finalize();
		
		GasBurningGenerator gbg = new GasBurningGenerator();
		com.willm.ModAPI.Voltage.Main.energyUsers.add(gbg);
		
		cooling_unit = ItemCreator.RegisterNewItem(new CustomItemStack("Cooling Unit", Material.IRON_BLOCK, 45202));
		BlockCreator.RegisterNewBlock(cooling_unit);
		
		cooling_unit.getRecipe(1, "SBS", "CIT", "SBS").AddMaterial('S', RecipeBuilder.ItemStackInput(screw)).AddMaterial('T', RecipeBuilder.ItemStackInput(titanium_block)).AddMaterial('C', RecipeBuilder.ItemStackInput(capacitor)).AddMaterial('B', RecipeBuilder.ItemStackInput(casing)).AddMaterial('I', RecipeBuilder.MultiMaterialInput(Material.ICE, Material.PACKED_ICE, Material.BLUE_ICE)).Finalize();
		
		fridge = ItemCreator.RegisterNewItem(new CustomItemStack("Fridge", Material.IRON_BLOCK, 45201));
		BlockCreator.RegisterNewBlock(fridge).SetDirectional(BlockDirectionData.PLAYER_RELATIVE);
		
		fridge.getRecipe(1, "QQQ", "HCQ", "GcS").AddMaterial('Q', Material.IRON_BLOCK).AddMaterial('C', Material.CHEST).AddMaterial('G', RecipeBuilder.ItemStackInput(gearshift)).AddMaterial('H', RecipeBuilder.ItemStackInput(hinge)).AddMaterial('S', RecipeBuilder.ItemStackInput(aluminum_alloy)).AddMaterial('c', RecipeBuilder.ItemStackInput(cooling_unit)).Finalize();
	
		
		//ELEVATOR RECIPE
		elevator_shaft.getRecipe(5, "GRG", "SRS", "gEg", "craft_shaft_elevator").AddMaterial('G', RecipeBuilder.ItemStackInput(gear)).AddMaterial('g', RecipeBuilder.ItemStackInput(gearshift)).AddMaterial('S', RecipeBuilder.ItemStackInput(screw)).AddMaterial('E', RecipeBuilder.ItemStackInput(engine)).AddMaterial('R', RecipeBuilder.ItemStackInput(iron_rod)).Finalize();
		elevator.getRecipe(8, "PPP", "CrR", "SGS").AddMaterial('P', Material.STICKY_PISTON).AddMaterial('C', RecipeBuilder.ItemStackInput(capacitor)).AddMaterial('R', RecipeBuilder.ItemStackInput(resistor)).AddMaterial('r', Material.REDSTONE_BLOCK).AddMaterial('S', Material.IRON_INGOT).AddMaterial('G', RecipeBuilder.ItemStackInput(electronic_gearshift)).Finalize();
	
		//JAR
			glass_jar = ItemCreator.RegisterNewItem(new CustomItemStack("Glass Jar", Material.WARPED_TRAPDOOR, 50001));
			glass_jar.AddLoreLine(ChatColor.BLUE + "Holds Fluids");
			
			glass_jar_water = new CustomBaseMaterialRetainingBlock(new CustomItemStack("Glass Jar", Material.WARPED_TRAPDOOR, 50021));
			glass_jar_lava = new CustomBaseMaterialRetainingBlock(new CustomItemStack("Glass Jar", Material.WARPED_TRAPDOOR, 50011));
			glass_jar_brine = new CustomBaseMaterialRetainingBlock(new CustomItemStack("Glass Jar", Material.WARPED_TRAPDOOR, 50031));
			glass_jar_cookies = new CustomBaseMaterialRetainingBlock(new CustomItemStack("Glass Jar", Material.WARPED_TRAPDOOR, 50041));
			glass_jar_salt_water = new CustomBaseMaterialRetainingBlock(new CustomItemStack("Glass Jar", Material.WARPED_TRAPDOOR, 50051));
			
			
			BlockCreator.RegisterNewBlock(glass_jar, new CustomBaseMaterialRetainingBlock(glass_jar));
			
			GlassJarConversions.put(50011, new ItemStack(Material.LAVA_BUCKET));
			GlassJarInserts.put(new ItemStack(Material.LAVA_BUCKET), glass_jar_lava);
			GlassINJarSounds.add(Sound.ITEM_BUCKET_EMPTY_LAVA);
			GlassOUTJarSounds.add(Sound.ITEM_BUCKET_FILL_LAVA);
			
			GlassJarConversions.put(50021, new ItemStack(Material.WATER_BUCKET));
			GlassJarInserts.put(new ItemStack(Material.WATER_BUCKET), glass_jar_water);
			GlassINJarSounds.add(Sound.ITEM_BUCKET_EMPTY);
			GlassOUTJarSounds.add(Sound.ITEM_BUCKET_FILL);

			GlassJarConversions.put(50031, brine.GetAmountClone(1));
			GlassJarInserts.put(brine.GetAmountClone(1), glass_jar_brine);
			GlassINJarSounds.add(Sound.ITEM_BOTTLE_EMPTY);
			GlassOUTJarSounds.add(Sound.ITEM_BOTTLE_FILL);
			
			GlassJarConversions.put(50041, new ItemStack(Material.COOKIE, 4));
			GlassJarInserts.put(new ItemStack(Material.COOKIE, 4), glass_jar_cookies);
			GlassINJarSounds.add(Sound.ENTITY_ITEM_PICKUP);
			GlassOUTJarSounds.add(Sound.ENTITY_ITEM_PICKUP);
			
			GlassJarConversions.put(50051, salt_water.GetMyItemStack());
			GlassJarInserts.put(salt_water.GetMyItemStack(), glass_jar_salt_water);
			
			CustomItemStack cork = ItemCreator.RegisterNewItem(new CustomItemStack("Cork", Material.PAPER, 10002));
			Bukkit.getServer().addRecipe(cork.GenUnshaped("craft_cork", 2).addIngredient(RecipeBuilder.MultiMaterialInput(Material.STRIPPED_OAK_LOG, Material.STRIPPED_DARK_OAK_LOG)).addIngredient(RecipeBuilder.MultiMaterialInput(Material.STRIPPED_OAK_LOG, Material.STRIPPED_DARK_OAK_LOG)).addIngredient(RecipeBuilder.ItemStackInput(sand_paper_item)));
	
			glass_jar.getRecipe(1, "CCC", "G G", "GGG").AddMaterial('G', Material.GLASS).AddMaterial('C', RecipeBuilder.ItemStackInput(cork)).Finalize();
	
			Bukkit.getServer().addRecipe(salt_water.GenUnshaped("craft_salt_water").addIngredient(Material.WATER_BUCKET).addIngredient(RecipeBuilder.ItemStackInput(salt_item)).addIngredient(RecipeBuilder.ItemStackInput(salt_item)));
	
			Bukkit.getServer().addRecipe(new FurnaceRecipe(NamespacedKey.fromString("desalinate_water", Main.INSTANCE), new ItemStack(Material.WATER_BUCKET, 1), RecipeBuilder.ItemStackInput(salt_water), 2f, 300));
			
			concrete_powder = ItemCreator.RegisterNewItem(new CustomItemStack("Concrete Powder", Material.BROWN_CONCRETE, 51025));
			BlockCreator.RegisterNewBlock(concrete_powder).SetConstBlock(false).SetMineAs(Material.CLAY);
			Bukkit.getServer().addRecipe(concrete_powder.GenUnshaped("concrete_powder", 32).addIngredient(RecipeBuilder.ItemStackInput(unfinished_concrete_powder)).addIngredient(RecipeBuilder.ItemStackInput(unfinished_concrete_powder)).addIngredient(RecipeBuilder.ItemStackInput(unfinished_concrete_powder)).addIngredient(RecipeBuilder.ItemStackInput(unfinished_concrete_powder)).addIngredient(Material.SAND).addIngredient(Material.SAND).addIngredient(Material.GRAVEL).addIngredient(Material.GRAVEL));
			
			steel_rod = ItemCreator.RegisterNewItem(new CustomItemStack("Steel Rod", Material.IRON_INGOT, 20003));
			steel_rod.getRecipe(3, "S", "s", "S").AddMaterial('S', RecipeBuilder.ItemStackInput(screw)).AddMaterial('s', RecipeBuilder.ItemStackInput(steel_ingot)).Finalize();
			
			steel_enforced_gray_concrete = ItemCreator.RegisterNewItem(new CustomItemStack("Steel Enforced Concrete", Material.BROWN_CONCRETE, 51024));
			BlockCreator.RegisterNewBlock(steel_enforced_gray_concrete).SetConstBlock(false).SetMineAs(Material.IRON_BLOCK).SetRequiredTool("PICKAXE");
			steel_enforced_gray_concrete.getRecipe(6, "psp", "ppp", "sps").AddMaterial('s', RecipeBuilder.ItemStackInput(steel_rod)).AddMaterial('p', RecipeBuilder.ItemStackInput(concrete_powder)).Finalize();
			
			CentrifugeRegistry();
			
			RegisterCustomRecipesMenu();
			
			CustomItemStack ingot_mold = ItemCreator.RegisterNewItem(new CustomItemStack("Ingot Mold", Material.BRICK, 10001));
			ingot_mold.getRecipe(1, "CCC", "C C", "CCC").AddMaterial('C', Material.CLAY_BALL).Finalize();
			
			CustomItemStack hardened_ingot_mold = ItemCreator.RegisterNewItem(new CustomItemStack("Hardened Ingot Mold", Material.BRICK, 10002));
			hardened_ingot_mold.getFurnaceRecipe(RecipeBuilder.ItemStackInput(ingot_mold), 8.f, 500, "fire_ingot_mold");
			hardened_ingot_mold.getBlastingRecipe(RecipeBuilder.ItemStackInput(ingot_mold), 3.f, 200, "blast_ingot_mold");
			
			enforced_strut = ItemCreator.RegisterNewItem(new CustomItemStack("Reinforced Strut", Material.STICK, 54321));
			enforced_strut.getRecipe(2, "S S", "S S", "i i", "craft_strut_with_iron").AddMaterial('S', Material.STICK).AddMaterial('i', Material.IRON_INGOT).Finalize();
			enforced_strut.getRecipe(6, "S S", "S S", "i i", "craft_strut_with_screw").AddMaterial('S', Material.STICK).AddMaterial('i', RecipeBuilder.ItemStackInput(screw)).Finalize();

			manual_feeding_trough = ItemCreator.RegisterNewItem(new CustomItemStack("Feeding Trough", Material.WARPED_TRAPDOOR, 51201));
			BlockCreator.RegisterNewBlock(manual_feeding_trough, new com.willm.CoreMOD.blocks.FeedingTrough(manual_feeding_trough, 51202, 51203, 51204));
			
			manual_feeding_trough.getRecipe(1, "P P", "PPP", "l l").AddMaterial('P', RecipeBuilder.MultiMaterialInput(Material.OAK_PLANKS, Material.DARK_OAK_PLANKS, Material.SPRUCE_PLANKS, Material.BIRCH_PLANKS, Material.ACACIA_PLANKS, Material.CHERRY_PLANKS, Material.BAMBOO_PLANKS, Material.WARPED_PLANKS, Material.JUNGLE_PLANKS, Material.CRIMSON_PLANKS)).AddMaterial('l', RecipeBuilder.ItemStackInput(enforced_strut)).Finalize();
	
			tubing = ItemCreator.RegisterNewItem(new CustomItemStack("Tubing", Material.IRON_INGOT, 61203));
			tubing.getRecipe(8, "CTC", "BSB", "CTC").AddMaterial('C', Material.COPPER_INGOT).AddMaterial('T', RecipeBuilder.ItemStackInput(tungsten_ingot)).AddMaterial('B', Material.BUCKET).AddMaterial('S', RecipeBuilder.ItemStackInput(screw)).Finalize();			
			tubing_cap = ItemCreator.RegisterNewItem(new CustomItemStack("Tubing Cap", Material.IRON_INGOT, 61204));
			tubing_cap.getRecipe(4, "CTC", "DHB", "CTC").AddMaterial('C', Material.COPPER_INGOT).AddMaterial('T', RecipeBuilder.ItemStackInput(tungsten_ingot)).AddMaterial('B', Material.BUCKET).AddMaterial('D', RecipeBuilder.ItemStackInput(iron_dish)).AddMaterial('H', RecipeBuilder.ItemStackInput(hinge)).Finalize();
			tubing_pump = ItemCreator.RegisterNewItem(new CustomItemStack("Tubing Pump", Material.IRON_INGOT, 61205));
			tubing_pump.getRecipe(2, "CGC", "BSB", "CGC").AddMaterial('C', Material.COPPER_INGOT).AddMaterial('G', RecipeBuilder.ItemStackInput(gear)).AddMaterial('B', Material.BUCKET).AddMaterial('S', RecipeBuilder.ItemStackInput(screw)).Finalize();

			tubing_component = ItemCreator.RegisterNewItem(new CustomItemStack("Tubing Component", Material.IRON_INGOT, 61206));
			tubing_component.getRecipe(2, "TC", "PT").AddMaterial('T', RecipeBuilder.ItemStackInput(tubing)).AddMaterial('C', RecipeBuilder.ItemStackInput(tubing_cap)).AddMaterial('P', RecipeBuilder.ItemStackInput(tubing_pump)).Finalize();
			
			lens = ItemCreator.RegisterNewItem(new CustomItemStack("Lens", Material.IRON_INGOT, 61207));
			lens.getRecipe(2, " G ", " CG", " G ").AddMaterial('G', Material.GLASS).AddMaterial('C', Material.AMETHYST_SHARD).Finalize();
			
			//ENGINE RECIPE
			engine.getRecipe(1, "AGA", "ACA", "ASP").AddMaterial('G', RecipeBuilder.ItemStackInput(gearshift)).AddMaterial('S', RecipeBuilder.ItemStackInput(screw)).AddMaterial('C', RecipeBuilder.ItemStackInput(casing)).AddMaterial('A', RecipeBuilder.ItemStackInput(aluminum_alloy)).AddMaterial('P', RecipeBuilder.ItemStackInput(tubing_component)).Finalize();
			
			combustion_acceleration_chamber = ItemCreator.RegisterNewItem(new CustomItemStack("Combustion Acceleration Chamber", Material.IRON_INGOT, 61208));
			combustion_acceleration_chamber.getRecipe(1, "SCS", "HBG", "SCS").AddMaterial('S', RecipeBuilder.ItemStackInput(steel_ingot)).AddMaterial('C', RecipeBuilder.ItemStackInput(high_carbon_steel)).AddMaterial('H', RecipeBuilder.ItemStackInput(hinge)).AddMaterial('B', Material.BUCKET).AddMaterial('G', RecipeBuilder.ItemStackInput(diesel)).Finalize();
			
			WorkbenchItemRegistry.Register();
			
			//Shopping addon
			Currency.RegisterCoins();
			BlockCreator.RegisterNewBlock(ShopBlock.SHOP_BLOCK_ITEM, new ShopBlock(ShopBlock.SHOP_BLOCK_ITEM));
			ShopBlock.SHOP_BLOCK_ITEM.getRecipe(4, "PTP", "CcR", "LLL", "craft_shop").AddMaterial('P', RecipeBuilder.ItemStackInput(MyItems.platinum_block)).AddMaterial('T', RecipeBuilder.ItemStackInput(MyItems.tubing_pump)).AddMaterial('c', Material.CHEST).AddMaterial('C', RecipeBuilder.ItemStackInput(MyItems.capacitor)).AddMaterial('R', RecipeBuilder.ItemStackInput(MyItems.resistor)).AddMaterial('L', RecipeBuilder.ItemStackInput(MyItems.steel_enforced_gray_concrete)).Finalize();
			
			PlotProtector.RegisterItems();
			//PlotProtector.PLOT_PROTECTOR.getRecipe(2, "GWG", "COC", "TET").AddMaterial('G', RecipeBuilder.ItemStackInput(MyItems.electronic_gearshift)).AddMaterial('W', RecipeBuilder.ItemStackInput(MyItems.wireless_redstone_activator)).AddMaterial('C', RecipeBuilder.ItemStackInput(MyItems.capacitor)).AddMaterial('O', Material.OBSIDIAN).AddMaterial('T', RecipeBuilder.ItemStackInput(tungsten_block)).AddMaterial('E', RecipeBuilder.ItemStackInput(engine)).Finalize();
	
			
			//Turret
			CreeperTurret.RegisterItems();
			CreeperTurret.CREEPER_TURRET.getRecipe(1, " L ", "TAT", "Tgt").AddMaterial('L', RecipeBuilder.ItemStackInput(lens)).AddMaterial('T', RecipeBuilder.ItemStackInput(galvanisedSteel)).AddMaterial('A', RecipeBuilder.ItemStackInput(combustion_acceleration_chamber)).AddMaterial('g', RecipeBuilder.ItemStackInput(electronic_gearshift)).AddMaterial('t', RecipeBuilder.ItemStackInput(tubing_pump)).Finalize();
			CreeperTurret.CREEPER_TURRET_AMMO.getRecipe(24, "BL", "L ").AddMaterial('B', RecipeBuilder.ItemStackInput(brass)).AddMaterial('L', RecipeBuilder.ItemStackInput(lead_bullion)).Finalize();
	
			ChestLock.RegisterItems();
			
			CustomItemStack padlock = ItemCreator.RegisterNewItem(new CustomItemStack("Lead Padlock", Material.GOLD_INGOT, 11001));
			padlock.getRecipe(2, "PPP", "S S", "HLH").AddMaterial('P', RecipeBuilder.ItemStackInput(platinum_ingot)).AddMaterial('S', RecipeBuilder.ItemStackInput(screw)).AddMaterial('H', RecipeBuilder.ItemStackInput(hinge)).AddMaterial('L', RecipeBuilder.ItemStackInput(lead_ingot)).Finalize();
			
			//ChestLock.CHEST_LOCK.getRecipe(2, "PPP", "PpP", "PPP").AddMaterial('P', RecipeBuilder.MultiMaterialInput(Material.OAK_PLANKS, Material.DARK_OAK_PLANKS, Material.SPRUCE_PLANKS, Material.BIRCH_PLANKS, Material.ACACIA_PLANKS, Material.CHERRY_PLANKS, Material.BAMBOO_PLANKS, Material.WARPED_PLANKS, Material.JUNGLE_PLANKS, Material.CRIMSON_PLANKS)).AddMaterial('p', RecipeBuilder.ItemStackInput(padlock)).Finalize();
			
			CrucibleItemRegistry.RegisterItems();
			
			glass_jar_tannin = new CustomBaseMaterialRetainingBlock(new CustomItemStack("Glass Jar", Material.WARPED_TRAPDOOR, 50061));
			
			Cosmetics.RegisterItems();
			Weapons.RegisterItems();						
			
			GlassJarConversions.put(50061, Cosmetics.tannin.GetMyItemStack());
			GlassJarInserts.put(Cosmetics.tannin.GetMyItemStack(), glass_jar_tannin);
			GlassINJarSounds.add(Sound.ITEM_BUCKET_EMPTY);
			GlassOUTJarSounds.add(Sound.ITEM_BUCKET_FILL);
			
			ForgeRegistry.RegsiterMetalItems();
			ForgeRegistry.RegisterForge();
			
			industrial_fridge = ItemCreator.RegisterNewItem(new CustomItemStack("Industrial Fridge", Material.IRON_BLOCK, 56203));
			BlockCreator.RegisterNewBlock(industrial_fridge).SetDirectional(BlockDirectionData.PLAYER_RELATIVE);
			industrial_fridge.getRecipe(1, "HQQ", "HCS", "Hcc").AddMaterial('H', RecipeBuilder.ItemStackInput(hinge)).AddMaterial('Q', RecipeBuilder.ItemStackInput(steel_enforced_gray_concrete)).AddMaterial('C', Material.CHEST).AddMaterial('S', RecipeBuilder.ItemStackInput(lead_ingot)).AddMaterial('c', RecipeBuilder.ItemStackInput(cooling_unit)).Finalize();
			
			SillyItems.RegisterSillyItems();
	}

	public static void RegisterCustomRecipesMenu()
	{
		//Brine Making
		ItemStack mixingItemDisplay = glass_jar.GetMyItemStack().clone(); 
		ItemMeta meta = mixingItemDisplay.getItemMeta();
		meta.setDisplayName(ChatColor.GOLD + "Mixing");
		mixingItemDisplay.setItemMeta(meta);
		
		//MIX BRINE
		RecipeDisplay.CUSTOM_RECIPES.add(new CustomRecipeType("Mixing", mixingItemDisplay, "mix_brine", Main.INSTANCE, 
				
				new CustomItemStack(ChatColor.RED + "-->", glass_jar_brine.getRootItem().getType(), glass_jar_brine.getDisplayCustomModelData()).AddEnchant(Enchantment.LUCK, 1, true).AddFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS).GetMyItemStack(),
				
				brine.GetAmountClone(3), glass_jar_water.getRootItem().GetMyItemStack(), salt_item.GetAmountClone(16)));
		
		RecipeDisplay.CUSTOM_RECIPES.add(new CustomRecipeType("Mixing", mixingItemDisplay, "mix_brine_saltwater", Main.INSTANCE, 
				
				new CustomItemStack(ChatColor.RED + "-->", glass_jar_brine.getRootItem().getType(), glass_jar_brine.getDisplayCustomModelData()).AddEnchant(Enchantment.LUCK, 1, true).AddFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS).GetMyItemStack(),
				
				brine.GetAmountClone(3), glass_jar_salt_water.getRootItem().GetMyItemStack(), salt_item.GetAmountClone(16)));
		
		
		//MIX BRINE
		RecipeDisplay.CUSTOM_RECIPES.add(new CustomRecipeType("Cauldron Mixing", Material.CAULDRON, "mix_brine_cauldron", Main.INSTANCE, 
				
				new CustomItemStack(ChatColor.RED + "-->", Material.CAULDRON, 0).AddLoreLine(ChatColor.GREEN + "Throw items in water").AddLoreLine(ChatColor.GREEN + "cauldron and right click.").AddEnchant(Enchantment.LUCK, 1, true).AddFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS).GetMyItemStack(),
				
				brine.GetAmountClone(2), new ItemStack(Material.WATER_BUCKET), salt_item.GetAmountClone(12)));
		
		//COMPRESS AIR
			RecipeDisplay.CUSTOM_RECIPES.add(new CustomRecipeType("Compressing", compressor.GetMyItemStack(), "compress_air", Main.INSTANCE, 
				
				new CustomItemStack(ChatColor.RED + "-->", compressor.getType(), compressor.getCustomModelData()).AddLoreLine(ChatColor.GREEN + "Speed: 40mt").AddEnchant(Enchantment.LUCK, 1, true).AddFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS).GetMyItemStack(),
				
				o2_bottle.GetMyItemStack(), new ItemStack(Material.GLASS_BOTTLE)));
			
		//ELECTROLYSIS WATER
			RecipeDisplay.CUSTOM_RECIPES.add(new CustomRecipeType("Electrolysis", electrolyzer.GetMyItemStack(), "electrolysis_water", Main.INSTANCE, 
					
					new CustomItemStack(ChatColor.RED + "-->", electrolyzer.getType(), electrolyzer.getCustomModelData()).AddLoreLine(ChatColor.GREEN + "Speed: 100mt").AddEnchant(Enchantment.LUCK, 1, true).AddFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS).GetMyItemStack(),
					
					o2_bottle.GetAmountClone(2), new ItemStack(Material.WATER_BUCKET)).AddByproduct(hydrogen.GetMyItemStack()));
			
			//COMPRESSING RECIPES
				RecipeDisplay.CUSTOM_RECIPES.add(new CustomRecipeType("Compressing", compressor.GetMyItemStack(), "compress_coal_for_silicon", Main.INSTANCE, 
						
						new CustomItemStack(ChatColor.RED + "-->", compressor.getType(), compressor.getCustomModelData()).AddLoreLine(ChatColor.GREEN + "Speed: 40mt").AddEnchant(Enchantment.LUCK, 1, true).AddFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS).GetMyItemStack(),
						
						silicon.GetMyItemStack(), new ItemStack(Material.COAL)));
				
				RecipeDisplay.CUSTOM_RECIPES.add(new CustomRecipeType("Compressing", compressor.GetMyItemStack(), "compress_rutile_for_titanium_ore", Main.INSTANCE, 
						
						new CustomItemStack(ChatColor.RED + "-->", compressor.getType(), compressor.getCustomModelData()).AddLoreLine(ChatColor.GREEN + "Speed: 40mt").AddEnchant(Enchantment.LUCK, 1, true).AddFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS).GetMyItemStack(),
						
						titanium_ore.GetMyItemStack(), rutile.GetMyItemStack()));
				
				RecipeDisplay.CUSTOM_RECIPES.add(new CustomRecipeType("Compressing", compressor.GetMyItemStack(), "compress_apple_for_cider", Main.INSTANCE, 
						
						new CustomItemStack(ChatColor.RED + "-->", compressor.getType(), compressor.getCustomModelData()).AddLoreLine(ChatColor.GREEN + "Speed: 40mt").AddEnchant(Enchantment.LUCK, 1, true).AddFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS).GetMyItemStack(),
						
						cider_vinegar.GetMyItemStack(), new ItemStack(Material.APPLE)));
				
				RecipeDisplay.CUSTOM_RECIPES.add(new CustomRecipeType("Compressing", compressor.GetMyItemStack(), "compress_pumpkin_seeds_for_oil", Main.INSTANCE, 
				
						new CustomItemStack(ChatColor.RED + "-->", compressor.getType(), compressor.getCustomModelData()).AddLoreLine(ChatColor.GREEN + "Speed: 40mt").AddEnchant(Enchantment.LUCK, 1, true).AddFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS).GetMyItemStack(),
						
						cooking_oil.GetMyItemStack(), new ItemStack(Material.PUMPKIN_SEEDS)));
				
				RecipeDisplay.CUSTOM_RECIPES.add(new CustomRecipeType("Compressing", compressor.GetMyItemStack(), "compress_beetroot_seeds_for_oil", Main.INSTANCE, 
						
						new CustomItemStack(ChatColor.RED + "-->", compressor.getType(), compressor.getCustomModelData()).AddLoreLine(ChatColor.GREEN + "Speed: 40mt").AddEnchant(Enchantment.LUCK, 1, true).AddFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS).GetMyItemStack(),
						
						cooking_oil.GetMyItemStack(), new ItemStack(Material.BEETROOT_SEEDS)));
				
				
		for(OvenRecipe or : BlockEvents.ovenRecipes)
		{
			RecipeDisplay.CUSTOM_RECIPES.add(new CustomRecipeType("Cooking (Oven)", oven.GetMyItemStack(), "cook_" + or.hashCode(), Main.INSTANCE, 
					
					new CustomItemStack(ChatColor.RED + "-->", oven.getType(), oven.getCustomModelData()).AddLoreLine(ChatColor.GREEN + (or.usePlate ? " Use Plate" : "Use Bowl")).AddEnchant(Enchantment.LUCK, 1, true).AddFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS).GetMyItemStack(),
					
					or.out, or.inputs));
		}
	}
	
	public static CustomItemStack[] centrifuge_tops = new CustomItemStack[4];
	
	public static CustomItemStack[] centrifuge_engines = new CustomItemStack[2];
	
	public static ArrayList<CentrifugeRecipe> centrifugeRecipes = new ArrayList<CentrifugeRecipe>();

	private static void CentrifugeRegistry()
	{
		CustomItemStack wooden_centrifuge_top = ItemCreator.RegisterNewItem(new CustomItemStack("Wooden Centrifuge Top", Material.WARPED_TRAPDOOR, 90011));
		BlockCreator.RegisterNewBlock(wooden_centrifuge_top, new com.willm.ModAPI.Blocks.CustomStates.CustomBaseMaterialRetainingBlock(wooden_centrifuge_top));
		
		wooden_centrifuge_top.AddLoreLine(ChatColor.GRAY + "Dirt -> Wheat Seeds (50%), Sludge X 2 (10%)").AddLoreLine(ChatColor.GRAY + "Gravel -> Bauxite (10%)").AddLoreLine(ChatColor.GRAY + "Cobblestone -> Coal (5%), Raw Iron (2%)");
		
		wooden_centrifuge_top.getRecipe(1, " D ", "DSD", " D ").AddMaterial('S', Material.STICK).AddMaterial('D', RecipeBuilder.ItemStackInput(iron_dish)).Finalize();

		centrifuge_tops[0] = wooden_centrifuge_top;
		
		CustomItemStack manual_rotary_engine = ItemCreator.RegisterNewItem(new CustomItemStack("Manual Rotary Engine", Material.ORANGE_WOOL, 10001));
		BlockCreator.RegisterNewBlock(manual_rotary_engine).SetMineAs(Material.OAK_PLANKS);
		
		centrifuge_engines[0] = manual_rotary_engine;
		
		manual_rotary_engine.getRecipe(1, "CGC", "SsC", "CGC").AddMaterial('C', RecipeBuilder.MultiMaterialInput(Material.COBBLESTONE, Material.COBBLED_DEEPSLATE)).AddMaterial('G', RecipeBuilder.ItemStackInput(gear)).AddMaterial('s', RecipeBuilder.ItemStackInput(gearshift)).AddMaterial('S', Material.STICK).Finalize();
		
		sludge.AddLoreLine(ChatColor.GRAY + "Dirt -> Centrifuge");
		
		centrifugeRecipes.add(new BasicChanceCentrifugeRecipe(Material.DIRT, new ItemStack(Material.WHEAT_SEEDS, 1), 500.0f, 1, 3));
		centrifugeRecipes.add(new BasicChanceCentrifugeRecipe(Material.DIRT, sludge.GetAmountClone(2), 100.0f, 1, 3));
		centrifugeRecipes.add(new BasicChanceCentrifugeRecipe(Material.GRAVEL, bauxite.GetAmountClone(1), 100.0f, 1, 3));
		centrifugeRecipes.add(new BasicChanceCentrifugeRecipe(Material.COBBLESTONE, new ItemStack(Material.COAL), 50.0f, 1, 3));
		centrifugeRecipes.add(new BasicChanceCentrifugeRecipe(Material.COBBLESTONE, new ItemStack(Material.RAW_IRON), 20.0f, 1, 3));
		
		//STONE TOP
		
		CustomItemStack stone_centrifuge_top = ItemCreator.RegisterNewItem(new CustomItemStack("Stone Centrifuge Top", Material.WARPED_TRAPDOOR, 90012));
		BlockCreator.RegisterNewBlock(stone_centrifuge_top, new com.willm.ModAPI.Blocks.CustomStates.CustomBaseMaterialRetainingBlock(stone_centrifuge_top));
		stone_centrifuge_top.AddLoreLine(ChatColor.GRAY + "Dirt -> Wheat Seeds (50%), Sludge X 2 (10%)").AddLoreLine(ChatColor.GRAY + "Gravel -> Bauxite (10%)").AddLoreLine(ChatColor.GRAY + "Cobblestone -> Coal (5%), Raw Iron (2%)");
		stone_centrifuge_top.getRecipe(1, "CD ", "DSD", " DC").AddMaterial('S', RecipeBuilder.ItemStackInput(wooden_centrifuge_top)).AddMaterial('D', RecipeBuilder.ItemStackInput(iron_dish)).AddMaterial('C', Material.COBBLESTONE).Finalize();

		centrifuge_tops[1] = stone_centrifuge_top;
		
		//DC BASIC ENGINE
		
		CustomItemStack basic_auto_rotary_engine = ItemCreator.RegisterNewItem(new CustomItemStack("Basic DC Rotary Engine", Material.ORANGE_WOOL, 10002));
		BlockCreator.RegisterNewBlock(basic_auto_rotary_engine).SetMineAs(Material.GOLD_BLOCK);

		centrifuge_engines[1] = basic_auto_rotary_engine;

		com.willm.CoreMOD.Power.RotaryEngine re = new com.willm.CoreMOD.Power.RotaryEngine(45.f, basic_auto_rotary_engine.getRelatedBlock());
		com.willm.ModAPI.Voltage.Main.energyUsers.add(re);
		com.willm.ModAPI.Voltage.Main.energyRecievers.add(re);
		
		basic_auto_rotary_engine.getRecipe(1, "TGT", "EeE", "SSS").AddMaterial('T', RecipeBuilder.ItemStackInput(titanium_block)).AddMaterial('G', RecipeBuilder.ItemStackInput(gearshift)).AddMaterial('E', RecipeBuilder.ItemStackInput(electronic_gearshift)).AddMaterial('e', RecipeBuilder.ItemStackInput(engine)).AddMaterial('S', RecipeBuilder.ItemStackInput(steel_enforced_gray_concrete)).Finalize();
		
		//IRON TOP
		
		centrifugeRecipes.add(new BasicChanceCentrifugeRecipe(Material.DIRT, sludge.GetAmountClone(3), 100.0f, 3, 5));	//10%
		centrifugeRecipes.add(new BasicChanceCentrifugeRecipe(Material.GRAVEL, bauxite.GetAmountClone(1), 200.0f, 3, 5));	//20%
		centrifugeRecipes.add(new BasicChanceCentrifugeRecipe(Material.COBBLESTONE, new ItemStack(Material.COAL), 250.0f, 3, 5)); //25%
		centrifugeRecipes.add(new BasicChanceCentrifugeRecipe(Material.COBBLESTONE, new ItemStack(Material.RAW_IRON), 70.0f, 3, 5)); //7%
		centrifugeRecipes.add(new BasicChanceCentrifugeRecipe(Material.COBBLESTONE, new ItemStack(Material.REDSTONE), 50.0f, 3, 5)); //5%
		centrifugeRecipes.add(new BasicChanceCentrifugeRecipe(Material.COBBLESTONE, new ItemStack(Material.RAW_GOLD), 30.0f, 3, 5)); //3%
		
		centrifugeRecipes.add(new BasicChanceCentrifugeRecipe(Material.SAND, new ItemStack(Material.GLASS), 120.0f, 3, 5)); //12%
		centrifugeRecipes.add(new BasicChanceCentrifugeRecipe(Material.SAND, MyItems.titanium_ore.GetMyItemStack(), 60.0f, 3, 5)); //6%
		
		centrifugeRecipes.add(new BasicChanceCentrifugeCustomRecipe(salt_water, salt_item.GetMyItemStack(), 750.0f, 3, 5)); //75%
		
		CustomItemStack iron_centrifuge_top = ItemCreator.RegisterNewItem(new CustomItemStack("Iron Centrifuge Top", Material.WARPED_TRAPDOOR, 90013));
		
		iron_centrifuge_top.SetLore(GenerateCentrifugeLore(3));
		
		BlockCreator.RegisterNewBlock(iron_centrifuge_top, new com.willm.ModAPI.Blocks.CustomStates.CustomBaseMaterialRetainingBlock(iron_centrifuge_top));
		iron_centrifuge_top.getRecipe(1, "DDD", "DSD", "DDD").AddMaterial('S', RecipeBuilder.ItemStackInput(stone_centrifuge_top)).AddMaterial('D', RecipeBuilder.ItemStackInput(iron_dish)).Finalize();

		
		centrifuge_tops[2] = iron_centrifuge_top;

		//GOLD TOP
		
		centrifugeRecipes.add(new BasicChanceCentrifugeRecipe(Material.COBBLESTONE, new ItemStack(Material.LAPIS_LAZULI), 70.0f, 4, 5)); //7%
		centrifugeRecipes.add(new BasicChanceCentrifugeRecipe(Material.COBBLESTONE, new ItemStack(Material.REDSTONE, 5), 50.0f, 4, 5)); //5%

		centrifugeRecipes.add(new BasicChanceCentrifugeRecipe(Material.CLAY, bauxite.GetMyItemStack(), 500.0f, 4, 5)); //50%
		centrifugeRecipes.add(new BasicChanceCentrifugeRecipe(Material.COBBLED_DEEPSLATE, new ItemStack(Material.COAL), 300.0f, 4, 5)); //30%

		CustomItemStack gold_centrifuge_top = ItemCreator.RegisterNewItem(new CustomItemStack("Gold Centrifuge Top", Material.WARPED_TRAPDOOR, 90014));
		gold_centrifuge_top.SetLore(GenerateCentrifugeLore(4));
		BlockCreator.RegisterNewBlock(gold_centrifuge_top, new com.willm.ModAPI.Blocks.CustomStates.CustomBaseMaterialRetainingBlock(gold_centrifuge_top));
		gold_centrifuge_top.getRecipe(1, "DRD", "DSD", "DRD").AddMaterial('S', RecipeBuilder.ItemStackInput(iron_centrifuge_top)).AddMaterial('D', Material.GOLD_INGOT).AddMaterial('R', RecipeBuilder.ItemStackInput(steel_rod)).Finalize();

		centrifuge_tops[3] = gold_centrifuge_top;

		int i = 0;
		for(CentrifugeRecipe cr : centrifugeRecipes)
		{
			if(cr instanceof BasicChanceCentrifugeRecipe)
			{
				BasicChanceCentrifugeRecipe bccr = (BasicChanceCentrifugeRecipe)cr;
				
				RecipeDisplay.CUSTOM_RECIPES.add(new CustomRecipeType("Centrifuging", centrifuge_tops[bccr.level - 1].GetMyItemStack(), "centrifuge_" + i, Main.INSTANCE, 
						
						new CustomItemStack(ChatColor.RED + "-->", centrifuge_tops[0].getType(), centrifuge_tops[0].getCustomModelData()).AddLoreLine(ChatColor.GREEN + "" + (bccr.chance1000 / 10) + "% chance").AddLoreLine(ChatColor.RED + "Centrifuge Level: " + bccr.level + " - " + bccr.maxLevel).AddLoreLine(ChatColor.GRAY + "Place a centrifuge top on a rotary engine.").AddEnchant(Enchantment.LUCK, 1, true).AddFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS).GetMyItemStack(),
						
						bccr.out, new ItemStack(bccr.in)));
			}
			i++;
		}
		
		

	}
	
	public static List<String> GenerateCentrifugeLore(int level)
	{
		List<String> out = new ArrayList<String>();
		for(CentrifugeRecipe cr : centrifugeRecipes)
		{
			String l = cr.GetLore(level);
			if(l != null)
			{
				out.add(l);
			}
		}
		
		return out;
	}
	
	
	public static CustomItemStack tomato_plant, oven, pan_oven, pan, oven_red, tomato, oven_blue, oven_tan, spaghetti, bacon, wooden_plate, cider_vinegar, tomato_sauce, spare_rib, rib_eye, bbq_sauce_smoky, beef_broth;
	public static CustomItemStack cutting_board, chopped_carrots, potato_slices;
	public static CustomItemStack pepper_plant, peppercorn, pepper_sack;
	public static CustomItemStack onion_plant, onion;
	
	public static CustomItemStack curdled_milk;
	
	public static CustomItemStack lamb;
	public static CustomItemStack ham;
	
	public static CustomItemStack cooking_oil, potato_chips;
	
	private static void Farming()
	{
		curdled_milk = ItemCreator.RegisterNewItem(new CustomItemStack("Curdled Milk", Material.MILK_BUCKET, 10001));
		curdled_milk.AddLoreLine(ChatColor.RED + "ROTTEN");
		
		RecipeDisplay.CUSTOM_RECIPES.add(new CustomRecipeType("Curdling", new ItemStack(Material.CLOCK), "curdle_milk", Main.INSTANCE, 
				
				new CustomItemStack(ChatColor.RED + "Age", Material.CLOCK, 0).AddLoreLine(ChatColor.GREEN + "Age: 1200/1200").AddEnchant(Enchantment.LUCK, 1, true).AddFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS).GetMyItemStack(),
				
				curdled_milk.GetMyItemStack(), new ItemStack(Material.MILK_BUCKET)));
		
		cutting_board = ItemCreator.RegisterNewItem(new CustomItemStack("Cutting Board", Material.QUARTZ_BRICKS, 10001));
		BlockCreator.RegisterNewBlock(cutting_board);
		
		cider_vinegar = ItemCreator.RegisterNewItem(new CustomItemStack("Cider Vinegar", Material.HONEY_BOTTLE, 10001)).AddLoreLine(ChatColor.GRAY + "Apple -> From Compressor");
		cooking_oil = ItemCreator.RegisterNewItem(new CustomItemStack("Cooking Oil", Material.HONEY_BOTTLE, 10004)).AddLoreLine(ChatColor.GRAY + "Beetroot Seeds -> From Compressor").AddLoreLine(ChatColor.GRAY + "Pumpkin Seeds -> From Compressor");
		
		wooden_plate = ItemCreator.RegisterNewItem(new CustomItemStack("Wooden Plate", Material.BOWL, 10001));
		wooden_plate.getRecipe(6, "   ", "BBB", "   ", "craft_wooden_plate").AddMaterial('B', Material.BOWL).Finalize();
				
		
		CustomItemStack brisket = ItemCreator.RegisterNewItem(new CustomItemStack("Brisket", Material.BEEF, 50001)); ItemCreator.RegisterMobDrop(new MobDrop(EntityType.COW, 160), brisket, 1);
		CustomItemStack chuck_steak = ItemCreator.RegisterNewItem(new CustomItemStack("Chuck Steak", Material.BEEF, 50002)); ItemCreator.RegisterMobDrop(new MobDrop(EntityType.COW, 160), chuck_steak, 1);
		CustomItemStack filet = ItemCreator.RegisterNewItem(new CustomItemStack("Filet", Material.BEEF, 50003)); ItemCreator.RegisterMobDrop(new MobDrop(EntityType.COW, 160), filet, 1);
		rib_eye = ItemCreator.RegisterMobDrop(new MobDrop(EntityType.COW, 160), ItemCreator.RegisterNewItem(new CustomItemStack("Rib-Eye", Material.BEEF, 50004)), 1);
		CustomItemStack sirloin = ItemCreator.RegisterMobDrop(new MobDrop(EntityType.COW, 160), ItemCreator.RegisterNewItem(new CustomItemStack("Sirloin", Material.BEEF, 50005)), 1);
		
		/*
		 * SMELTING RECIPES FOR CUSTOM COW MEAT
		 */
		CustomItemStack cooked1 = new CustomItemStack("Cooked Brisket", Material.COOKED_BEEF, 50001);cooked1.getFurnaceRecipe(RecipeBuilder.ItemStackInput(brisket), 2.f, 20 * 5, "cook_brisket");
		CustomItemStack cooked2 = (new CustomItemStack("Cooked Chuck Steak", Material.COOKED_BEEF, 50002));cooked2.getFurnaceRecipe(RecipeBuilder.ItemStackInput(chuck_steak), 2.f, 20 * 5, "cook_chuck_steak");
		CustomItemStack cooked3 = (new CustomItemStack("Cooked Filet", Material.COOKED_BEEF, 50003));cooked3.getFurnaceRecipe(RecipeBuilder.ItemStackInput(filet), 2.f, 20 * 5, "cook_filet");
		CustomItemStack cooked4 = (new CustomItemStack("Cooked Rib-Eye", Material.COOKED_BEEF, 50004));cooked4.getFurnaceRecipe(RecipeBuilder.ItemStackInput(rib_eye), 2.f, 20 * 5, "cook_ribeye");
		CustomItemStack cooked5 = (new CustomItemStack("Cooked Sirloin", Material.COOKED_BEEF, 50005));cooked5.getFurnaceRecipe(RecipeBuilder.ItemStackInput(sirloin), 2.f, 20 * 5, "cook_sirloin");
		
		CustomItemStack smoked1 = (new CustomItemStack("Smoked Brisket", Material.COOKED_BEEF, 50001));smoked1.getSmokingRecipe(RecipeBuilder.ItemStackInput(brisket), 2.f, 20 * 4, "smoke_brisket");
		CustomItemStack smoked2 = (new CustomItemStack("Smoked Chuck Steak", Material.COOKED_BEEF, 50002));smoked2.getSmokingRecipe(RecipeBuilder.ItemStackInput(chuck_steak), 2.f, 20 * 4, "smoke_chuck_steak");
		CustomItemStack smoked3 = (new CustomItemStack("Smoked Filet", Material.COOKED_BEEF, 50003));smoked3.getSmokingRecipe(RecipeBuilder.ItemStackInput(filet), 2.f, 20 * 4, "smoke_filet");
		CustomItemStack smoked4 = (new CustomItemStack("Smoked Rib-Eye", Material.COOKED_BEEF, 50004));smoked4.getSmokingRecipe(RecipeBuilder.ItemStackInput(rib_eye), 2.f, 20 * 4, "smoke_ribeye");
		CustomItemStack smoked5 = (new CustomItemStack("Smoked Sirloin", Material.COOKED_BEEF, 50005));smoked5.getSmokingRecipe(RecipeBuilder.ItemStackInput(sirloin), 2.f, 20 * 4, "smoke_sirloin");
		
		CustomItemStack fired1 = (new CustomItemStack("Fired Brisket", Material.COOKED_BEEF, 50001));fired1.getCampfireRecipe(RecipeBuilder.ItemStackInput(brisket), 2.f, 20 * 12, "campfire_smoke_brisket");
		CustomItemStack fired2 = (new CustomItemStack("Fired Chuck Steak", Material.COOKED_BEEF, 50002));fired2.getCampfireRecipe(RecipeBuilder.ItemStackInput(chuck_steak), 2.f, 20 * 12, "campfire_smoke_chuck_steak");
		CustomItemStack fired3 = (new CustomItemStack("Fired Filet", Material.COOKED_BEEF, 50003));fired3.getCampfireRecipe(RecipeBuilder.ItemStackInput(filet), 2.f, 20 * 5, "campfire_smoke_filet");
		CustomItemStack fired4 = (new CustomItemStack("Fired Rib-Eye", Material.COOKED_BEEF, 50004));fired4.getCampfireRecipe(RecipeBuilder.ItemStackInput(rib_eye), 2.f, 20 * 12, "campfire_smoke_ribeye");
		CustomItemStack fired5 = (new CustomItemStack("Fired Sirloin", Material.COOKED_BEEF, 50005));fired5.getCampfireRecipe(RecipeBuilder.ItemStackInput(sirloin), 2.f, 20 * 12, "campfire_smoke_sirloin");
		
		
		ham = ItemCreator.RegisterMobDrop(new MobDrop(EntityType.PIG, 160), ItemCreator.RegisterNewItem(new CustomItemStack("Ham", Material.PORKCHOP, 50001)), 1);
		spare_rib = ItemCreator.RegisterMobDrop(new MobDrop(EntityType.PIG, 160), ItemCreator.RegisterNewItem(new CustomItemStack("Spare Ribs", Material.PORKCHOP, 50002)), 1);
		CustomItemStack pork_loin = ItemCreator.RegisterMobDrop(new MobDrop(EntityType.PIG, 160), ItemCreator.RegisterNewItem(new CustomItemStack("Pork Loin", Material.PORKCHOP, 50003)), 1);
		CustomItemStack pork_butt = ItemCreator.RegisterMobDrop(new MobDrop(EntityType.PIG, 160), ItemCreator.RegisterNewItem(new CustomItemStack("Pork Butt", Material.PORKCHOP, 50004)), 1);
		bacon = ItemCreator.RegisterMobDrop(new MobDrop(EntityType.PIG, 160), ItemCreator.RegisterNewItem(new CustomItemStack("Bacon", Material.PORKCHOP, 50005)), 1);
		
		
		/*
		 * SMELTING RECIPES FOR CUSTOM PIG MEAT
		 */
		(new CustomItemStack("Cooked Ham", Material.COOKED_PORKCHOP, 50001)).getFurnaceRecipe(RecipeBuilder.ItemStackInput(ham), 2.f, 20 * 5, "cook_ham");
		(new CustomItemStack("Cooked Spare Ribs", Material.COOKED_PORKCHOP, 50002)).getFurnaceRecipe(RecipeBuilder.ItemStackInput(spare_rib), 2.f, 20 * 5, "cook_spare_ribs");
		(new CustomItemStack("Cooked Pork Loin", Material.COOKED_PORKCHOP, 50003)).getFurnaceRecipe(RecipeBuilder.ItemStackInput(pork_loin), 2.f, 20 * 5, "cook_pork_loin");
		(new CustomItemStack("Cooked Pork Butt", Material.COOKED_PORKCHOP, 50004)).getFurnaceRecipe(RecipeBuilder.ItemStackInput(pork_butt), 2.f, 20 * 5, "cook_pork_butt");
		(new CustomItemStack("Cooked Bacon", Material.COOKED_PORKCHOP, 50005)).getFurnaceRecipe(RecipeBuilder.ItemStackInput(bacon), 2.f, 20 * 5, "cook_bacon");

		(new CustomItemStack("Smoked Ham", Material.COOKED_PORKCHOP, 50001)).getSmokingRecipe(RecipeBuilder.ItemStackInput(ham), 2.f, 20 * 5, "smoke_ham");
		(new CustomItemStack("Smoked Spare Ribs", Material.COOKED_PORKCHOP, 50002)).getSmokingRecipe(RecipeBuilder.ItemStackInput(spare_rib), 2.f, 20 * 5, "smoke_spare_ribs");
		(new CustomItemStack("Smoked Pork Loin", Material.COOKED_PORKCHOP, 50003)).getSmokingRecipe(RecipeBuilder.ItemStackInput(pork_loin), 2.f, 20 * 5, "smoke_pork_loin");
		(new CustomItemStack("Smoked Pork Butt", Material.COOKED_PORKCHOP, 50004)).getSmokingRecipe(RecipeBuilder.ItemStackInput(pork_butt), 2.f, 20 * 5, "smoke_pork_butt");
		(new CustomItemStack("Smoked Bacon", Material.COOKED_PORKCHOP, 50005)).getSmokingRecipe(RecipeBuilder.ItemStackInput(bacon), 2.f, 20 * 5, "smoke_bacon");
		
		(new CustomItemStack("Fired Ham", Material.COOKED_PORKCHOP, 50001)).getCampfireRecipe(RecipeBuilder.ItemStackInput(ham), 2.f, 20 * 5, "campfire_smoke_ham");
		(new CustomItemStack("Fired Spare Ribs", Material.COOKED_PORKCHOP, 50002)).getCampfireRecipe(RecipeBuilder.ItemStackInput(spare_rib), 2.f, 20 * 5, "campfire_smoke_spare_ribs");
		(new CustomItemStack("Fired Pork Loin", Material.COOKED_PORKCHOP, 50003)).getCampfireRecipe(RecipeBuilder.ItemStackInput(pork_loin), 2.f, 20 * 5, "campfire_smoke_pork_loin");
		(new CustomItemStack("Fired Pork Butt", Material.COOKED_PORKCHOP, 50004)).getCampfireRecipe(RecipeBuilder.ItemStackInput(pork_butt), 2.f, 20 * 5, "campfire_smoke_pork_butt");
		(new CustomItemStack("Fired Bacon", Material.COOKED_PORKCHOP, 50005)).getCampfireRecipe(RecipeBuilder.ItemStackInput(bacon), 2.f, 20 * 5, "campfire_smoke_bacon");
		
		BlockEvents.ovenRecipes.add(new OvenRecipe(ItemCreator.RegisterNewItem(new CustomItemStack("Crispy Bacon", Material.COOKED_BEEF, 50006)).AddLoreLine(ChatColor.BLUE + "Bacon -> Oven (Plate)").GetMyItemStack(), true, bacon.GetMyItemStack()));
		
		
		CustomItemStack hoget = ItemCreator.RegisterMobDrop(new MobDrop(EntityType.SHEEP, 320), ItemCreator.RegisterNewItem(new CustomItemStack("Hoget", Material.MUTTON, 50001)), 1);
		lamb = ItemCreator.RegisterMobDrop(new MobDrop(EntityType.SHEEP, 320), ItemCreator.RegisterNewItem(new CustomItemStack("Lamb", Material.MUTTON, 50002)), 1);
		
		(new CustomItemStack("Cooked Hoget", Material.COOKED_MUTTON, 50001)).getFurnaceRecipe(RecipeBuilder.ItemStackInput(hoget), 2.f, 20 * 5, "cook_hoget");
		(new CustomItemStack("Cooked Lamb", Material.COOKED_MUTTON, 50002)).getFurnaceRecipe(RecipeBuilder.ItemStackInput(lamb), 2.f, 20 * 5, "cook_lamb");

		(new CustomItemStack("Smoked Hoget", Material.COOKED_MUTTON, 50001)).getSmokingRecipe(RecipeBuilder.ItemStackInput(hoget), 2.f, 20 * 5, "smoke_hoget");
		(new CustomItemStack("Smoked Lamb", Material.COOKED_MUTTON, 50002)).getSmokingRecipe(RecipeBuilder.ItemStackInput(lamb), 2.f, 20 * 5, "smoke_lamb");
		
		(new CustomItemStack("Fired Hoget", Material.COOKED_MUTTON, 50001)).getCampfireRecipe(RecipeBuilder.ItemStackInput(hoget), 2.f, 20 * 5, "campfire_cook_hoget");
		(new CustomItemStack("Fired Lamb", Material.COOKED_MUTTON, 50002)).getCampfireRecipe(RecipeBuilder.ItemStackInput(lamb), 2.f, 20 * 5, "campfire_cook_lamb");
		
		oven = ItemCreator.RegisterNewItem(new CustomItemStack("Oven", Material.SMOOTH_STONE, 10001));
		BlockCreator.RegisterNewBlock(oven);
		
		pan_oven = ItemCreator.RegisterNewItem(new CustomItemStack("Oven With Pan", Material.SMOOTH_STONE, 10002));
		BlockCreator.RegisterNewBlock(pan_oven).SetDrops(false);
		
		oven_red = ItemCreator.RegisterNewItem(new CustomItemStack("Oven With Pan (Red)", Material.SMOOTH_STONE, 10003));
		BlockCreator.RegisterNewBlock(oven_red).SetDrops(false);
		
		oven_blue = ItemCreator.RegisterNewItem(new CustomItemStack("Oven With Pan (Water)", Material.SMOOTH_STONE, 10004));
		BlockCreator.RegisterNewBlock(oven_blue).SetDrops(false);
		
		oven_tan = ItemCreator.RegisterNewItem(new CustomItemStack("Oven With Pan (Noodles)", Material.SMOOTH_STONE, 10005));
		BlockCreator.RegisterNewBlock(oven_tan).SetDrops(false);
		
		pan = ItemCreator.RegisterNewItem(new CustomItemStack("Pan", Material.STONE_HOE, 10001));
		pan.getRecipe(1, " II", "III", " II").AddMaterial('I', Material.IRON_INGOT).Finalize();
		
		tomato_plant = ItemCreator.RegisterNewItem(new CustomItemStack("Tomato Plant (Stage 3)", Material.DIRT, 10001));
		CustomItemStack tomato_plant_2 = ItemCreator.RegisterNewItem(new CustomItemStack("Tomato Plant (Stage 2)", Material.DIRT, 10002));
		CustomItemStack tomato_plant_3 = ItemCreator.RegisterNewItem(new CustomItemStack("Tomato Plant", Material.DIRT, 10003));
		
		tomato = ItemCreator.RegisterNewItem(new CustomItemStack("Tomato", Material.BEETROOT, 10001));
				
		Bukkit.getServer().addRecipe(tomato_plant_3.GenUnshaped("tomato_plant_craft").addIngredient(RecipeBuilder.ItemStackInput(tomato)).addIngredient(RecipeBuilder.ItemStackInput(tomato)).addIngredient(RecipeBuilder.ItemStackInput(tomato)).addIngredient(RecipeBuilder.ItemStackInput(tomato)));
		
		ItemCreator.RegisterPlant(tomato, 6, 50, BlockCreator.RegisterNewBlock(tomato_plant), BlockCreator.RegisterNewBlock(tomato_plant_2), BlockCreator.RegisterNewBlock(tomato_plant_3));
		
		//Pepper
			pepper_plant = ItemCreator.RegisterNewItem(new CustomItemStack("Pepper Plant (Stage 4)", Material.DIRT, 10005));
			CustomItemStack pepper_plant_2 = ItemCreator.RegisterNewItem(new CustomItemStack("Pepper Plant (Stage 3)", Material.DIRT, 10006));
			CustomItemStack pepper_plant_3 = ItemCreator.RegisterNewItem(new CustomItemStack("Pepper Plant (Stage 2)", Material.DIRT, 10007));
			CustomItemStack pepper_plant_4 = ItemCreator.RegisterNewItem(new CustomItemStack("Pepper Plant", Material.DIRT, 10008));

			peppercorn = ItemCreator.RegisterNewItem(new CustomItemStack("Peppercorn", Material.BEETROOT, 10002));
			
			Bukkit.getServer().addRecipe(pepper_plant_4.GenUnshaped("pepper_plant_craft").addIngredient(RecipeBuilder.ItemStackInput(peppercorn)).addIngredient(RecipeBuilder.ItemStackInput(peppercorn)).addIngredient(RecipeBuilder.ItemStackInput(peppercorn)));
			
			ItemCreator.RegisterPlant(peppercorn, 6, 55, BlockCreator.RegisterNewBlock(pepper_plant), BlockCreator.RegisterNewBlock(pepper_plant_2), BlockCreator.RegisterNewBlock(pepper_plant_3), BlockCreator.RegisterNewBlock(pepper_plant_4));
			
			pepper_sack = ItemCreator.RegisterNewItem(new CustomItemStack("Pepper Pouch", Material.BEETROOT, 10003));
			pepper_sack.AddLoreLine(ChatColor.BLUE + "Peppercorn -> Cutting Board");
			
			//PEPPER SACK
			RecipeDisplay.CUSTOM_RECIPES.add(new CustomRecipeType("Cutting", cutting_board.GetMyItemStack(), "chop_pepper", Main.INSTANCE, 
					
					new CustomItemStack(ChatColor.RED + "-->", cutting_board.getType(), cutting_board.getCustomModelData()).AddLoreLine(ChatColor.GREEN + "Right click the board.").AddEnchant(Enchantment.LUCK, 1, true).AddFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS).GetMyItemStack(),
					
					pepper_sack.GetMyItemStack(), peppercorn.GetMyItemStack()));

			BlockEvents.CuttingBoardRecipes.put(peppercorn.GetMyItemStack(), pepper_sack.GetMyItemStack());
			
		//Onion
			onion_plant = ItemCreator.RegisterNewItem(new CustomItemStack("Onion Plant (Stage 4)", Material.DIRT, 10012));
			CustomItemStack onion_plant_2 = ItemCreator.RegisterNewItem(new CustomItemStack("Onion Plant (Stage 3)", Material.DIRT, 10011));
			CustomItemStack onion_plant_3 = ItemCreator.RegisterNewItem(new CustomItemStack("Onion Plant (Stage 2)", Material.DIRT, 10010));
			CustomItemStack onion_plant_4 = ItemCreator.RegisterNewItem(new CustomItemStack("Onion Plant", Material.DIRT, 10009));
	
			onion = ItemCreator.RegisterNewItem(new CustomItemStack("Onion", Material.CARROT, 15001));
			
			ItemCreator.RegisterPlant(onion, 5, 15, BlockCreator.RegisterNewBlock(onion_plant), BlockCreator.RegisterNewBlock(onion_plant_2), BlockCreator.RegisterNewBlock(onion_plant_3), BlockCreator.RegisterNewBlock(onion_plant_4));
				
		CustomItemStack lambchop = ItemCreator.RegisterNewItem(new CustomItemStack("Lamb Chop", Material.COOKED_MUTTON, 10001));
		lambchop.AddFoodModifier(1, 1).AddLoreLine(ChatColor.BLUE + "Lamb + Pepper Pouch -> Oven (Plate)");
			
		BlockEvents.ovenRecipes.add(new OvenRecipe(lambchop.GetMyItemStack(), true, pepper_sack.GetMyItemStack(), lamb.GetMyItemStack()));
		
		
		tomato_sauce = ItemCreator.RegisterNewItem(new CustomItemStack("Tomato Sauce", Material.COOKED_BEEF, 10001));
		tomato_sauce.AddLoreLine(ChatColor.BLUE + "2 Tomatoes -> Oven (Bowl)");
		
		BlockEvents.ovenRecipes.add(new OvenRecipe(tomato_sauce.GetMyItemStack(), false, tomato.GetAmountClone(2)));
		
		bbq_sauce_smoky = ItemCreator.RegisterNewItem(new CustomItemStack("Smoky BBQ Sauce", Material.HONEY_BOTTLE, 10002)).AddLoreLine(ChatColor.BLUE + "Cider Vinegar + Tomato Sauce -> Oven (Bowl)");
		BlockEvents.ovenRecipes.add(new OvenRecipe(bbq_sauce_smoky.GetAmountClone(2), false, cider_vinegar.GetMyItemStack(), tomato_sauce.GetMyItemStack()));
		
		CustomItemStack smoky_ribs = ItemCreator.RegisterNewItem(new CustomItemStack("Smoky Ribs", Material.COOKED_BEEF, 10007)).AddFoodModifier(2, 3).AddLoreLine(ChatColor.BLUE + "Smoky BBQ Sauce + Rib-Eye -> Oven (Plate)").AddLoreLine(ChatColor.BLUE + "Smoky BBQ Sauce + Spare Ribs -> Oven (Plate)");
		BlockEvents.ovenRecipes.add(new OvenRecipe(smoky_ribs.GetAmountClone(4), true, bbq_sauce_smoky.GetMyItemStack(), spare_rib.GetMyItemStack()));
		BlockEvents.ovenRecipes.add(new OvenRecipe(smoky_ribs.GetAmountClone(4), true, bbq_sauce_smoky.GetMyItemStack(), rib_eye.GetMyItemStack()));

		spaghetti = ItemCreator.RegisterNewItem(new CustomItemStack("Spaghetti", Material.WHEAT, 10001));
		Bukkit.getServer().addRecipe(spaghetti.GenUnshaped("spaghetti_s").addIngredient(Material.WATER_BUCKET).addIngredient(Material.WATER_BUCKET).addIngredient(Material.WHEAT).addIngredient(Material.WHEAT).addIngredient(Material.WHEAT));
		
		CustomItemStack cooked_spaghetti = ItemCreator.RegisterNewItem(new CustomItemStack("Cooked Spaghetti", Material.WHEAT, 10002));
		cooked_spaghetti.AddLoreLine(ChatColor.BLUE + "Water + Spaghetti -> Oven (Bowl)");
		
		BlockEvents.ovenRecipes.add(new OvenRecipe(cooked_spaghetti.GetMyItemStack(), false, new ItemStack(Material.WATER_BUCKET, 1), spaghetti.GetMyItemStack()));
		
		CustomItemStack saucy_spaghetti = ItemCreator.RegisterNewItem(new CustomItemStack("Saucy Spaghetti", Material.COOKED_BEEF, 10002)).AddFoodModifier(2, 1);
		BlockEvents.ovenRecipes.add(new OvenRecipe(saucy_spaghetti.GetAmountClone(2), false, tomato_sauce.GetMyItemStack(), cooked_spaghetti.GetMyItemStack()));

		chopped_carrots = ItemCreator.RegisterNewItem(new CustomItemStack("Chopped Carrots", Material.CARROT, 10001));
		chopped_carrots.AddLoreLine(ChatColor.BLUE + "Carrot -> Cutting Board");
		
		BlockEvents.CuttingBoardRecipes.put(new ItemStack(Material.CARROT, 1), chopped_carrots.GetMyItemStack());
		
		//CHOPPED CARROTS
		RecipeDisplay.CUSTOM_RECIPES.add(new CustomRecipeType("Cutting", cutting_board.GetMyItemStack(), "chop_carrots", Main.INSTANCE, 
				
				new CustomItemStack(ChatColor.RED + "-->", cutting_board.getType(), cutting_board.getCustomModelData()).AddLoreLine(ChatColor.GREEN + "Right click the board.").AddEnchant(Enchantment.LUCK, 1, true).AddFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS).GetMyItemStack(),
				
				chopped_carrots.GetMyItemStack(), new ItemStack(Material.CARROT, 1)));
		
		
		beef_broth = ItemCreator.RegisterNewItem(new CustomItemStack("Beef Broth", Material.HONEY_BOTTLE, 10003));
		beef_broth.AddLoreLine(ChatColor.BLUE + "Cooked Beef + Water Bucket -> Oven (Bowl)");
		
		BlockEvents.ovenRecipes.add(new OvenRecipe(beef_broth.GetMyItemStack(), false, new ItemStack(Material.WATER_BUCKET), new ItemStack(Material.COOKED_BEEF)));
		
		CustomItemStack carrot_stew = ItemCreator.RegisterNewItem(new CustomItemStack("Carrot Stew", Material.COOKED_BEEF, 10008)).AddFoodModifier(1, 1).AddLoreLine(ChatColor.BLUE + "Chopped Carrots + Beef Broth -> Oven (Bowl)");
		
		BlockEvents.ovenRecipes.add(new OvenRecipe(carrot_stew.GetMyItemStack(), false, chopped_carrots.GetMyItemStack(), beef_broth.GetMyItemStack()));
		
		potato_chips = ItemCreator.RegisterNewItem(new CustomItemStack("Potato Chips", Material.BAKED_POTATO, 60001)).AddFoodModifier(1, 1).AddLoreLine(ChatColor.BLUE + "Cooking Oil + Baked Potato -> Oven (Bowl)");
		
		potato_slices = ItemCreator.RegisterNewItem(new CustomItemStack("Potato Slices", Material.BAKED_POTATO, 60002)).AddLoreLine(ChatColor.BLUE + "Baked Potato -> Cutting Board");
		BlockEvents.CuttingBoardRecipes.put(new ItemStack(Material.BAKED_POTATO, 1), potato_slices.GetMyItemStack());
		
		RecipeDisplay.CUSTOM_RECIPES.add(new CustomRecipeType("Cutting", cutting_board.GetMyItemStack(), "chop_potatoes", Main.INSTANCE, 
				
				new CustomItemStack(ChatColor.RED + "-->", cutting_board.getType(), cutting_board.getCustomModelData()).AddLoreLine(ChatColor.GREEN + "Right click the board.").AddEnchant(Enchantment.LUCK, 1, true).AddFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS).GetMyItemStack(),
				
				potato_slices.GetMyItemStack(), new ItemStack(Material.POTATO, 1)));
		
		BlockEvents.ovenRecipes.add(new OvenRecipe(potato_chips.GetMyItemStack(), false, potato_slices.GetMyItemStack(), cooking_oil.GetMyItemStack()));
	}
	
	private static void ZincRegistry()
	{
		zinc_ore = ItemCreator.RegisterNewItem(new CustomItemStack("Calamine", Material.GUNPOWDER, 10001));
		zinc_ore.AddLoreLine(ChatColor.GRAY + "Found in clay deposits.");
		
		zinc = ItemCreator.RegisterNewItem(new CustomItemStack("Zinc", Material.GUNPOWDER, 10002)).AddLoreLine(ChatColor.GRAY + "Calamine -> From Electrolyzer");
		
		hydrogen = ItemCreator.RegisterNewItem(new CustomItemStack("Bottle Of Hydrogen", Material.GLASS_BOTTLE, 20001));
		hydrogen.AddLoreLine(ChatColor.GRAY + "Water Bucket -> From Electrolyzer");
		
		electrolyzer = ItemCreator.RegisterNewItem(new CustomItemStack("Electrolyzer", Material.IRON_BLOCK, 10003));
		
		BlockCreator.RegisterNewBlock(electrolyzer, "core_mod.drill_idle", 100, 9, "Electrolyzer", new MachineConversion(zinc_ore.GetMyItemStack(), zinc.GetMyItemStack(), new ItemStack(Material.COAL, 3)), new MachineConversion(new ItemStack(Material.WATER_BUCKET), hydrogen.GetMyItemStack(), o2_bottle.GetAmountClone(2)));
	
		electrolyzer.getRecipe(1, "PPP", "PBP", "PPP").AddMaterial('P', RecipeBuilder.ItemStackInput(platinum_ingot)).AddMaterial('B', Material.BUCKET).Finalize();
		
		
		RecipeDisplay.CUSTOM_RECIPES.add(new CustomRecipeType("Electrolysis", electrolyzer.GetMyItemStack(), "electrolysis_zinc", Main.INSTANCE, 
				
				new CustomItemStack(ChatColor.RED + "-->", electrolyzer.getType(), electrolyzer.getCustomModelData()).AddLoreLine(ChatColor.GREEN + "Speed: 100mt").AddEnchant(Enchantment.LUCK, 1, true).AddFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS).GetMyItemStack(),
				
				zinc.GetMyItemStack(), zinc_ore.GetMyItemStack()).AddByproduct(new ItemStack(Material.COAL, 3)));
		
		RecipeDisplay.CUSTOM_RECIPES.add(new CustomRecipeType("Gathering", Material.DIAMOND_SHOVEL, "gather_zinc_ore", Main.INSTANCE, 
				
				new CustomItemStack(ChatColor.RED + "Mine Clay", Material.DIAMOND_SHOVEL, 0).AddFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS).AddLoreLine(ChatColor.GREEN + "30% Drop Chance").GetMyItemStack(),
				
				zinc_ore.GetMyItemStack(), new ItemStack(Material.CLAY, 4)));
	}
	
	public static CustomItemStack refiner;
	private static void OilRegistry()
	{
		crude_oil_deposit = ItemCreator.RegisterNewItem(new CustomItemStack("Crude Oil Deposit", Material.DEAD_HORN_CORAL_BLOCK, 20001));
		BlockCreator.RegisterNewBlock(crude_oil_deposit).SetConstBlock(false).SetMineAs(Material.STONE).SetRequiredTool("PICKAXE");
		
		new Ore(crude_oil_deposit, 24, true, 7, 10);
		
		oil_barrel = ItemCreator.RegisterNewItem(new CustomItemStack("Oil Barrel", Material.BLACK_DYE, 10001));
		oil_barrel.AddLoreLine(ChatColor.GRAY + "Crude Oil Deposit -> From Refiner");
		
		
		
		
		refiner = ItemCreator.RegisterNewItem(new CustomItemStack("Refiner", Material.IRON_BLOCK, 10002));
		
		refiner.getRecipe(1, "iIi", "F F", "iIi").AddMaterial('I', Material.IRON_BLOCK).AddMaterial('i', RecipeBuilder.ItemStackInput(iron_rod)).AddMaterial('F', Material.FURNACE).Finalize();
		
		//Catalysts
		//butane, gasoline, kerosene, diesel, residual fuel
		
		CustomItemStack butane_c = ItemCreator.RegisterNewItem(new CustomItemStack("Butane Catalyst", Material.BLACK_DYE, 10002));
		butane_c.getFurnaceRecipe(RecipeBuilder.ItemStackInput(oil_barrel), 1, 20, "create_butane");
		butane_c.getBlastingRecipe(RecipeBuilder.ItemStackInput(oil_barrel), 1, 15, "blast_butane");

		butane = ItemCreator.RegisterNewItem(new CustomItemStack("Butane", Material.BLACK_DYE, 20002)).AddLoreLine(ChatColor.GRAY + "Butane Catalyst -> From Refiner");
		
		CustomItemStack gasoline_c = ItemCreator.RegisterNewItem(new CustomItemStack("Gasoline Catalyst", Material.BLACK_DYE, 10003));
		gasoline_c.getFurnaceRecipe(RecipeBuilder.ItemStackInput(butane_c), 2, 40, "create_gas_oil");
		gasoline_c.getBlastingRecipe(RecipeBuilder.ItemStackInput(butane_c), 1, 35, "blast_gas_oil");
		
		gasoline = ItemCreator.RegisterNewItem(new CustomItemStack("Gasoline", Material.BLACK_DYE, 20003)).AddLoreLine(ChatColor.GRAY + "Gasoline Catalyst -> From Refiner");
		
		CustomItemStack kerosene_c = ItemCreator.RegisterNewItem(new CustomItemStack("Kerosene Catalyst", Material.BLACK_DYE, 10004));
		kerosene_c.getFurnaceRecipe(RecipeBuilder.ItemStackInput(gasoline_c), 4, 80, "create_kerosene");
		kerosene_c.getBlastingRecipe(RecipeBuilder.ItemStackInput(gasoline_c), 4, 70, "blast_kerosene");
		
		kerosene = ItemCreator.RegisterNewItem(new CustomItemStack("Kerosene", Material.BLACK_DYE, 20004)).AddLoreLine(ChatColor.GRAY + "Kerosene Catalyst -> From Refiner");
		
		CustomItemStack diesel_c = ItemCreator.RegisterNewItem(new CustomItemStack("Diesel Catalyst", Material.BLACK_DYE, 10005));
		diesel_c.getFurnaceRecipe(RecipeBuilder.ItemStackInput(kerosene_c), 8, 160, "create_diesel");
		diesel_c.getBlastingRecipe(RecipeBuilder.ItemStackInput(kerosene_c), 8, 130, "blast_diesel");
		
		diesel = ItemCreator.RegisterNewItem(new CustomItemStack("Diesel", Material.BLACK_DYE, 20005)).AddLoreLine(ChatColor.GRAY + "Diesel Catalyst -> From Refiner");

		CustomItemStack residual_fuel_c = ItemCreator.RegisterNewItem(new CustomItemStack("Residual Fuel Catalyst", Material.BLACK_DYE, 10006));
		residual_fuel_c.getFurnaceRecipe(RecipeBuilder.ItemStackInput(diesel_c), 16, 320, "create_res_fuel");
		residual_fuel_c.getBlastingRecipe(RecipeBuilder.ItemStackInput(diesel_c), 16, 280, "blast_res_fuel");

		res_fuel = ItemCreator.RegisterNewItem(new CustomItemStack("Residual Fuel", Material.BLACK_DYE, 20006)).AddLoreLine(ChatColor.GRAY + "Residual Fuel Catalyst -> From Refiner");
		
		BlockCreator.RegisterNewBlock(refiner, "core_mod.refiner_sound", 50, 9, "Refiner", new MachineConversion(crude_oil_deposit.GetMyItemStack(), oil_barrel.GetMyItemStack()),
				new MachineConversion(butane_c.GetMyItemStack(), butane.GetMyItemStack()),
				new MachineConversion(gasoline_c.GetMyItemStack(), gasoline.GetMyItemStack()),
				new MachineConversion(kerosene_c.GetMyItemStack(), kerosene.GetMyItemStack()),
				new MachineConversion(diesel_c.GetMyItemStack(), diesel.GetMyItemStack()),
				new MachineConversion(residual_fuel_c.GetMyItemStack(), res_fuel.GetMyItemStack())
				);
		
		//Refining OIL BARREL
		RecipeDisplay.CUSTOM_RECIPES.add(new CustomRecipeType("Refining", refiner.GetMyItemStack(), "refiner_for_oil_barrel", Main.INSTANCE, 
				
				new CustomItemStack(ChatColor.RED + "-->", refiner.getType(), refiner.getCustomModelData()).AddLoreLine(ChatColor.GREEN + "Speed: 50mt").AddEnchant(Enchantment.LUCK, 1, true).AddFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS).GetMyItemStack(),
				
				oil_barrel.GetMyItemStack(), crude_oil_deposit.GetMyItemStack()));
		
		//Refining BUTANE
		RecipeDisplay.CUSTOM_RECIPES.add(new CustomRecipeType("Refining", refiner.GetMyItemStack(), "refine_butane", Main.INSTANCE, 
				
				new CustomItemStack(ChatColor.RED + "-->", refiner.getType(), refiner.getCustomModelData()).AddLoreLine(ChatColor.GREEN + "Speed: 50mt").AddEnchant(Enchantment.LUCK, 1, true).AddFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS).GetMyItemStack(),
				
				butane.GetMyItemStack(), butane_c.GetMyItemStack()));
		
		//Refining GASOLINE
		RecipeDisplay.CUSTOM_RECIPES.add(new CustomRecipeType("Refining", refiner.GetMyItemStack(), "refine_gasoline", Main.INSTANCE, 
				
				new CustomItemStack(ChatColor.RED + "-->", refiner.getType(), refiner.getCustomModelData()).AddLoreLine(ChatColor.GREEN + "Speed: 50mt").AddEnchant(Enchantment.LUCK, 1, true).AddFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS).GetMyItemStack(),
				
				gasoline.GetMyItemStack(), gasoline_c.GetMyItemStack()));
		
		//Refining KEROSENE
		RecipeDisplay.CUSTOM_RECIPES.add(new CustomRecipeType("Refining", refiner.GetMyItemStack(), "refine_kerosene", Main.INSTANCE, 
				
				new CustomItemStack(ChatColor.RED + "-->", refiner.getType(), refiner.getCustomModelData()).AddLoreLine(ChatColor.GREEN + "Speed: 50mt").AddEnchant(Enchantment.LUCK, 1, true).AddFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS).GetMyItemStack(),
				
				kerosene.GetMyItemStack(), kerosene_c.GetMyItemStack()));
		
		//Refining DIESEL
		RecipeDisplay.CUSTOM_RECIPES.add(new CustomRecipeType("Refining", refiner.GetMyItemStack(), "refine_diesel", Main.INSTANCE, 
				
				new CustomItemStack(ChatColor.RED + "-->", refiner.getType(), refiner.getCustomModelData()).AddLoreLine(ChatColor.GREEN + "Speed: 50mt").AddEnchant(Enchantment.LUCK, 1, true).AddFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS).GetMyItemStack(),
				
				diesel.GetMyItemStack(), diesel_c.GetMyItemStack()));
		
		//Refining RES FUEL
		RecipeDisplay.CUSTOM_RECIPES.add(new CustomRecipeType("Refining", refiner.GetMyItemStack(), "refine_residual_fuel", Main.INSTANCE, 
				
				new CustomItemStack(ChatColor.RED + "-->", refiner.getType(), refiner.getCustomModelData()).AddLoreLine(ChatColor.GREEN + "Speed: 50mt").AddEnchant(Enchantment.LUCK, 1, true).AddFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS).GetMyItemStack(),
				
				res_fuel.GetMyItemStack(), residual_fuel_c.GetMyItemStack()));
	}


	static void DrillsRegistry()
	{
		stripmine_drill = ItemCreator.RegisterNewItem(new CustomItemStack("Strip Mine Drill", Material.IRON_PICKAXE, 60001));
		
		stripmine_drill.getRecipe(1, "   ", "CPR", " P ").AddMaterial('C', RecipeBuilder.ItemStackInput(capacitor)).AddMaterial('R', RecipeBuilder.ItemStackInput(resistor)).AddMaterial('P', Material.IRON_PICKAXE).Finalize();
		
		drill = ItemCreator.RegisterNewItem(new CustomItemStack("Drill", Material.WOODEN_PICKAXE, 60001));
		drill.getRecipe(1, "PPP", "PRP", "PDP").AddMaterial('P', Material.IRON_PICKAXE).AddMaterial('R', RecipeBuilder.ItemStackInput(resistor)).AddMaterial('D', RecipeBuilder.ItemStackInput(diesel)).Finalize();
		
		enforced_drill = ItemCreator.RegisterNewItem(new CustomItemStack("Enforced Drill", Material.STONE_PICKAXE, 60002));
		enforced_drill.getRecipe(1, "DRD", "CCC", "KKK").AddMaterial('D', RecipeBuilder.ItemStackInput(drill)).AddMaterial('R', Material.REDSTONE).AddMaterial('K', RecipeBuilder.ItemStackInput(kerosene)).AddMaterial('C', RecipeBuilder.ItemStackInput(capacitor)).Finalize();
	}
	
	static void PlatinumRegistry()
	{
		//Platinum
		
		//Ore
	platinum_ore = ItemCreator.RegisterNewItem(new CustomItemStack("Platinum Ore", Material.DEAD_TUBE_CORAL_BLOCK, 10001));
	BlockCreator.RegisterNewBlock(platinum_ore).SetConstBlock(false).SetMineAs(Material.DEEPSLATE).SetRequiredTool("PICKAXE");
	
	new Ore(platinum_ore, 32, true, 5, 24);
	
		//Ingot
	platinum_ingot = ItemCreator.RegisterNewItem(new CustomItemStack("Platinum Ingot", Material.LIGHT_BLUE_DYE, 10001));
	platinum_ingot.getFurnaceRecipe(RecipeBuilder.ItemStackInput(platinum_ore), 5, 200, "platinum_ingot_from_platinum_ore");
	platinum_ingot.getBlastingRecipe(RecipeBuilder.ItemStackInput(platinum_ore), 5, 170, "platinum_ingot_from_platinum_ore_blasting");

		//Tools
	ItemCreator.RegisterNewItem(new CustomItemStack("Platinum Sword", Material.DIAMOND_SWORD, 10001)).Attribute(Attribute.GENERIC_ATTACK_SPEED, -1.4f, EquipmentSlot.HAND).Attribute(Attribute.GENERIC_ATTACK_DAMAGE, 6.5f, EquipmentSlot.HAND).getRecipe(1, RecipeTemplates.Sword, "platinum_sword").AddMaterial('I', RecipeBuilder.ItemStackInput(platinum_ingot)).Finalize();
	ItemCreator.RegisterNewItem(new CustomItemStack("Platinum Axe", Material.DIAMOND_AXE, 10001)).Attribute(Attribute.GENERIC_ATTACK_SPEED, -2.95f, EquipmentSlot.HAND).Attribute(Attribute.GENERIC_ATTACK_DAMAGE, 8.5f, EquipmentSlot.HAND).getRecipe(1, RecipeTemplates.Axe, "platinum_axe").AddMaterial('I', RecipeBuilder.ItemStackInput(platinum_ingot)).Finalize();
	ItemCreator.RegisterNewItem(new CustomItemStack("Platinum Pickaxe", Material.DIAMOND_PICKAXE, 10001)).getRecipe(1, RecipeTemplates.Pickaxe, "platinum_pickaxe").AddMaterial('I', RecipeBuilder.ItemStackInput(platinum_ingot)).Finalize();
	ItemCreator.RegisterNewItem(new CustomItemStack("Platinum Shovel", Material.DIAMOND_SHOVEL, 10001)).getRecipe(1, RecipeTemplates.Shovel, "platinum_shovel").AddMaterial('I', RecipeBuilder.ItemStackInput(platinum_ingot)).Finalize();
	ItemCreator.RegisterNewItem(new CustomItemStack("Platinum Hoe", Material.DIAMOND_HOE, 10001)).getRecipe(1, RecipeTemplates.Hoe, "platinum_hoe").AddMaterial('I', RecipeBuilder.ItemStackInput(platinum_ingot)).Finalize();

		//Armor
	ItemCreator.RegisterNewItem(new CustomItemStack("Platinum Helmet", Material.DIAMOND_HELMET, 10001)).AddLoreLine(ChatColor.GRAY + "*Platinum Armor*").Attribute(Attribute.GENERIC_ARMOR_TOUGHNESS, 1f, EquipmentSlot.CHEST).Attribute(Attribute.GENERIC_ARMOR, 3, EquipmentSlot.CHEST).getRecipe(1, RecipeTemplates.Helmet, "platinum_helmet").AddMaterial('I', RecipeBuilder.ItemStackInput(platinum_ingot)).Finalize();
	ItemCreator.RegisterNewItem(new CustomItemStack("Platinum Chestplate", Material.DIAMOND_CHESTPLATE, 10001)).AddLoreLine(ChatColor.GRAY + "*Platinum Armor*").Attribute(Attribute.GENERIC_ARMOR_TOUGHNESS, 1f, EquipmentSlot.CHEST).Attribute(Attribute.GENERIC_ARMOR, 7, EquipmentSlot.CHEST).getRecipe(1, RecipeTemplates.Chestplate, "platinum_chestplate").AddMaterial('I', RecipeBuilder.ItemStackInput(platinum_ingot)).Finalize();
	ItemCreator.RegisterNewItem(new CustomItemStack("Platinum Leggings", Material.DIAMOND_LEGGINGS, 10001)).AddLoreLine(ChatColor.GRAY + "*Platinum Armor*").Attribute(Attribute.GENERIC_ARMOR_TOUGHNESS, 1f, EquipmentSlot.CHEST).Attribute(Attribute.GENERIC_ARMOR, 3, EquipmentSlot.CHEST).getRecipe(1, RecipeTemplates.Leggings, "platinum_leggings").AddMaterial('I', RecipeBuilder.ItemStackInput(platinum_ingot)).Finalize();
	ItemCreator.RegisterNewItem(new CustomItemStack("Platinum Boots", Material.DIAMOND_BOOTS, 10001)).AddLoreLine(ChatColor.GRAY + "*Platinum Armor*").Attribute(Attribute.GENERIC_ARMOR_TOUGHNESS, 1f, EquipmentSlot.CHEST).Attribute(Attribute.GENERIC_ARMOR, 3, EquipmentSlot.CHEST).getRecipe(1, RecipeTemplates.Boots, "platinum_boots").AddMaterial('I', RecipeBuilder.ItemStackInput(platinum_ingot)).Finalize();

		//Blocks And Decoration
	platinum_block = ItemCreator.RegisterNewItem(new CustomItemStack("Block Of Platinum", Material.LIGHT_BLUE_TERRACOTTA, 10002));
	platinum_block.getRecipe(1, RecipeTemplates.Ingot_Block, "platinum_block").AddMaterial('I', RecipeBuilder.ItemStackInput(platinum_ingot)).Finalize();
	
	BlockCreator.RegisterNewBlock(platinum_block).SetConstBlock(false).SetMineAs(Material.IRON_BLOCK).SetRequiredTool("PICKAXE");
	
	platinum_ingot.getRecipe(9, RecipeTemplates.Ingot_Middle, "platinum_ingot_from_block").AddMaterial('I', RecipeBuilder.ItemStackInput(platinum_block)).Finalize();
	
			//Door
	
	CustomItemStack d_bottom = ItemCreator.RegisterNewItem(new CustomItemStack("Platinum Door", Material.LIGHT_BLUE_TERRACOTTA, 10003));
	CustomItemStack d_top = ItemCreator.RegisterNewItem(new CustomItemStack("Platinum Door", Material.LIGHT_BLUE_TERRACOTTA, 10004));
	
	BlockCreator.RegisterNewBlock(d_bottom);
	BlockCreator.RegisterNewBlock(d_top).SetDrops(false);
	
	d_bottom.getRecipe(3, RecipeTemplates.Door, "platinum_door").AddMaterial('I', RecipeBuilder.ItemStackInput(platinum_ingot)).Finalize();
	
	doors.put(d_bottom, d_top);
	
	}

	static void TungstenRegistry()
	{
		//Tungsten
		
			//Ore
		wolframite = ItemCreator.RegisterNewItem(new CustomItemStack("Wolframite", Material.DEAD_FIRE_CORAL_BLOCK, 10001));
		BlockCreator.RegisterNewBlock(wolframite).SetConstBlock(false).SetMineAs(Material.IRON_ORE).SetRequiredTool("PICKAXE");
	
		new Ore(wolframite, 64, true, 1, 64);
				
			//Ingot
		tungsten_ingot = ItemCreator.RegisterNewItem(new CustomItemStack("Tungsten Ingot", Material.BROWN_DYE, 10002));
		tungsten_ingot.getFurnaceRecipe(RecipeBuilder.ItemStackInput(wolframite), 2, 100, "tungsten_ingot_from_wolframite");
		tungsten_ingot.getBlastingRecipe(RecipeBuilder.ItemStackInput(wolframite), 2, 70, "tungsten_ingot_from_wolframite_blasting");
		

		
			//Tools
		ItemCreator.RegisterNewItem(new CustomItemStack("Tungsten Pickaxe", Material.IRON_PICKAXE, 10001)).getRecipe(1, "III", " S ", " S ").AddMaterial('I', RecipeBuilder.ItemStackInput(tungsten_ingot)).AddMaterial('S', Material.STICK).Finalize();
		ItemCreator.RegisterNewItem(new CustomItemStack("Tungsten Axe", Material.IRON_AXE, 10001)).getRecipe(1, "II ", "IS ", " S ").AddMaterial('I', RecipeBuilder.ItemStackInput(tungsten_ingot)).AddMaterial('S', Material.STICK).Finalize();
		ItemCreator.RegisterNewItem(new CustomItemStack("Tungsten Hoe", Material.IRON_HOE, 10001)).getRecipe(1, "II ", " S ", " S ").AddMaterial('I', RecipeBuilder.ItemStackInput(tungsten_ingot)).AddMaterial('S', Material.STICK).Finalize();
		ItemCreator.RegisterNewItem(new CustomItemStack("Tungsten Shovel", Material.IRON_SHOVEL, 10001)).getRecipe(1, " I ", " S ", " S ").AddMaterial('I', RecipeBuilder.ItemStackInput(tungsten_ingot)).AddMaterial('S', Material.STICK).Finalize();
		ItemCreator.RegisterNewItem(new CustomItemStack("Tungsten Sword", Material.IRON_SWORD, 10001)).getRecipe(1, " I ", " I ", " S ").AddMaterial('I', RecipeBuilder.ItemStackInput(tungsten_ingot)).AddMaterial('S', Material.STICK).Finalize();
		ItemCreator.RegisterNewItem(new CustomItemStack("Tungsten Bow", Material.BOW, 10001)).getRecipe(1, " Ss", "I s", " Ss").AddMaterial('I', RecipeBuilder.ItemStackInput(tungsten_ingot)).AddMaterial('S', Material.STICK).AddMaterial('s', Material.STRING).Finalize();
	
			//Armor
		ItemCreator.RegisterNewItem(new CustomItemStack("Tungsten Helmet", Material.IRON_HELMET, 10001)).AddLoreLine(ChatColor.GRAY + "*Tungsten Armor*").getRecipe(1, "III", "I I", "   ").AddMaterial('I', RecipeBuilder.ItemStackInput(tungsten_ingot)).Finalize();
		tungsten_chestplate = ItemCreator.RegisterNewItem(new CustomItemStack("Tungsten Chestplate", Material.IRON_CHESTPLATE, 10001)).AddLoreLine(ChatColor.GRAY + "*Tungsten Armor*");
		tungsten_chestplate.getRecipe(1, "I I", "III", "III").AddMaterial('I', RecipeBuilder.ItemStackInput(tungsten_ingot)).Finalize();
		ItemCreator.RegisterNewItem(new CustomItemStack("Tungsten Leggings", Material.IRON_LEGGINGS, 10001)).AddLoreLine(ChatColor.GRAY + "*Tungsten Armor*").getRecipe(1, "III", "I I", "I I").AddMaterial('I', RecipeBuilder.ItemStackInput(tungsten_ingot)).Finalize();
		ItemCreator.RegisterNewItem(new CustomItemStack("Tungsten Boots", Material.IRON_BOOTS, 10001)).AddLoreLine(ChatColor.GRAY + "*Tungsten Armor*").getRecipe(1, "   ", "I I", "I I").AddMaterial('I', RecipeBuilder.ItemStackInput(tungsten_ingot)).Finalize();
	
			//Blocks And Decoration
		tungsten_block = ItemCreator.RegisterNewItem(new CustomItemStack("Block Of Tungsten", Material.ORANGE_TERRACOTTA, 10002));
		tungsten_block.getRecipe(1, "III", "III", "III").AddMaterial('I', RecipeBuilder.ItemStackInput(tungsten_ingot)).Finalize();
		BlockCreator.RegisterNewBlock(tungsten_block).SetConstBlock(false).SetMineAs(Material.GOLD_BLOCK).SetRequiredTool("PICKAXE");
		
		tungsten_ingot.getRecipe(9, "   ", " B ", "   ").AddMaterial('B', RecipeBuilder.ItemStackInput(tungsten_block)).Finalize();
		
		CustomItemStack tungsten_lamp = ItemCreator.RegisterNewItem(new CustomItemStack("Tungsten Lamp", Material.ORANGE_TERRACOTTA, 10003));
		tungsten_lamp.getRecipe(6, "TTT", "TtT", "TTT").AddMaterial('T', RecipeBuilder.ItemStackInput(tungsten_ingot)).AddMaterial('t', Material.TORCH).Finalize();
		
		BlockCreator.RegisterNewBlock(tungsten_lamp);
	}
	
	public static CustomItemStack pine_log, sand_paper_item;
	
	public static void WoodRegistry()
	{
		sand_paper_item = ItemCreator.RegisterNewItem(new CustomItemStack("Sandpaper", Material.PAPER, 20001)).AddLoreLine(ChatColor.GRAY + "Used to strip logs.");
		Bukkit.getServer().addRecipe(sand_paper_item.GenUnshaped("craft_sand_paper", 1).addIngredient(Material.PAPER).addIngredient(RecipeBuilder.MultiMaterialInput(Material.SAND, Material.RED_SAND)));

		CustomItemStack pine_log_item = ItemCreator.RegisterNewItem(new CustomItemStack("Pine Log", Material.OAK_LOG, 10001));
		BlockCreator.RegisterNewBlock(pine_log_item).SetConstBlock(false).SetDirectional(BlockDirectionData.FACE_RELATIVE).SetSidewaysBlock(BlockCreator.RegisterNewBlock(new CustomItemStack("Pine Log", Material.OAK_LOG, 10002)).SetCustomDrops(pine_log_item).SetMineAs(Material.OAK_LOG).SetConstBlock(false)).SetMineAs(Material.OAK_LOG);
	
		pine_log = pine_log_item;
		
		CustomItemStack stripped_pine_log_item = ItemCreator.RegisterNewItem(new CustomItemStack("Stripped Pine Log", Material.STRIPPED_OAK_LOG, 10001));
		BlockCreator.RegisterNewBlock(stripped_pine_log_item).SetConstBlock(false).SetDirectional(BlockDirectionData.FACE_RELATIVE).SetSidewaysBlock(BlockCreator.RegisterNewBlock(new CustomItemStack("Stripped Pine Log", Material.STRIPPED_OAK_LOG, 10002)).SetCustomDrops(stripped_pine_log_item).SetMineAs(Material.STRIPPED_OAK_LOG).SetConstBlock(false)).SetMineAs(Material.STRIPPED_OAK_LOG);
		
		Bukkit.getServer().addRecipe(stripped_pine_log_item.GenUnshaped("strip_pine_log", 2).addIngredient(RecipeBuilder.ItemStackInput(sand_paper_item)).addIngredient(RecipeBuilder.ItemStackInput(pine_log_item)).addIngredient(RecipeBuilder.ItemStackInput(pine_log_item)));
		
		CustomItemStack pine_planks = ItemCreator.RegisterNewItem(new CustomItemStack("Pine Planks", Material.OAK_PLANKS, 10001));
		BlockCreator.RegisterNewBlock(pine_planks).SetConstBlock(false).SetMineAs(Material.OAK_PLANKS);
		
		pine_planks.getRecipe(4, "P", " ").AddMaterial('P', RecipeBuilder.MultiItemStackInput(pine_log_item, stripped_pine_log_item)).Finalize();
		
		CustomItemStack pine_fence = ItemCreator.RegisterNewItem(new CustomItemStack("Pine Fence", Material.OAK_FENCE, 10001));
		
		pine_fence.getRecipe(3, "SPS", "SPS").AddMaterial('S', Material.STICK).AddMaterial('P', RecipeBuilder.ItemStackInput(pine_planks)).Finalize();

		CustomItemStack pine_fence_post = new CustomItemStack("Pine Fence", Material.OAK_FENCE, 10011);
		CustomItemStack pine_side_fence = new CustomItemStack("Pine Fence", Material.OAK_FENCE, 10021);
		
		BlockCreator.RegisterNewBlock(pine_fence, new CustomFenceBlock(pine_fence, new CustomBlock(pine_fence_post), new CustomBlock(pine_side_fence)));
		
		CustomItemStack pine_trapdoor = ItemCreator.RegisterNewItem(new CustomItemStack("Pine Trapdoor", Material.WARPED_TRAPDOOR, 10001));
		CustomItemStack pine_trapdoor_open = new CustomItemStack("Pine Trapdoor", Material.WARPED_TRAPDOOR, 10011);
		CustomItemStack pine_trapdoor_top = new CustomItemStack("Pine Trapdoor", Material.WARPED_TRAPDOOR, 10021);

		pine_trapdoor.getRecipe(8, "PPP", "PPP").AddMaterial('P', RecipeBuilder.ItemStackInput(pine_log)).Finalize();
		
		BlockCreator.RegisterNewBlock(pine_trapdoor, new CustomTrapdoorBlock(pine_trapdoor, new CustomBlock(pine_trapdoor_open), new CustomBlock(pine_trapdoor_top)));
	}
	
	private static void registerConcreteRecipe(CustomItemStack unfinished_concrete_powder, Material product, Material dye)
	{
		ShapelessRecipe craftConcreteWithUnfinished = new ShapelessRecipe(NamespacedKey.minecraft("core_" + product.toString().toLowerCase()), new ItemStack(product, 32));
		craftConcreteWithUnfinished.addIngredient(Material.GRAVEL);craftConcreteWithUnfinished.addIngredient(Material.GRAVEL);
		craftConcreteWithUnfinished.addIngredient(Material.SAND);craftConcreteWithUnfinished.addIngredient(Material.SAND);
		craftConcreteWithUnfinished.addIngredient(RecipeBuilder.ItemStackInput(unfinished_concrete_powder));craftConcreteWithUnfinished.addIngredient(RecipeBuilder.ItemStackInput(unfinished_concrete_powder));
		craftConcreteWithUnfinished.addIngredient(RecipeBuilder.ItemStackInput(unfinished_concrete_powder));craftConcreteWithUnfinished.addIngredient(RecipeBuilder.ItemStackInput(unfinished_concrete_powder));
		craftConcreteWithUnfinished.addIngredient(dye);
		Bukkit.getServer().addRecipe(craftConcreteWithUnfinished);
	}
	
	private static void registerStrippingRecipe(Material log, Material stripped)
	{
		ShapelessRecipe stripLog = new ShapelessRecipe(NamespacedKey.fromString("core_" + stripped.toString().toLowerCase(), Main.INSTANCE), new ItemStack(stripped, 2));
		stripLog.addIngredient(log);
		stripLog.addIngredient(log);
		stripLog.addIngredient(RecipeBuilder.ItemStackInput(sand_paper_item));
		
		Bukkit.getServer().addRecipe(stripLog);
	}
}
