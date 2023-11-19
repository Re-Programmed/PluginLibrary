package com.willm.CoreMOD;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.willm.ModAPI.MobDrop;
import com.willm.ModAPI.Blocks.LiquidBlock;
import com.willm.ModAPI.Items.BlockCreator;
import com.willm.ModAPI.Items.CustomItemStack;
import com.willm.ModAPI.Items.ItemCreator;
import com.willm.ModAPI.Items.Recipes.RecipeBuilder;
import com.willm.ModAPI.RecipeDisplay.CustomRecipeType;
import com.willm.ModAPI.RecipeDisplay.RecipeDisplay;

public class Cosmetics {

	public static CustomItemStack bark;
	public static CustomItemStack tannin;
	public static CustomItemStack animal_hide, soaked_animal_hide;
		
	public static CustomItemStack red_top_hat;
	
	public static void RegisterItems()
	{
		bark = ItemCreator.RegisterNewItem(new CustomItemStack("Bark", Material.BROWN_DYE, 20001));
		
		bark.getRecipe("bark_flip_1", 4, " P ", "LLL").AddMaterial('P', RecipeBuilder.ItemStackInput(MyItems.sand_paper_item)).AddMaterial('L', RecipeBuilder.MultiMaterialInput(Material.OAK_LOG, Material.SPRUCE_LOG, Material.DARK_OAK_LOG, Material.ACACIA_LOG, Material.JUNGLE_LOG, Material.CHERRY_LOG, Material.BIRCH_LOG, Material.MANGROVE_LOG)).Finalize();
		bark.getRecipe("bark_flip_2", 4, "LLL", " P ").AddMaterial('P', RecipeBuilder.ItemStackInput(MyItems.sand_paper_item)).AddMaterial('L', RecipeBuilder.MultiMaterialInput(Material.OAK_LOG, Material.SPRUCE_LOG, Material.DARK_OAK_LOG, Material.ACACIA_LOG, Material.JUNGLE_LOG, Material.CHERRY_LOG, Material.BIRCH_LOG, Material.MANGROVE_LOG)).Finalize();
	
		tannin = ItemCreator.RegisterNewItem(new CustomItemStack("Bucket Of Tannin", Material.BROWN_DYE, 20002));
		
		//Tannin Recipe Display
			ItemStack mixingItemDisplay = MyItems.glass_jar.GetMyItemStack().clone(); 
			ItemMeta meta = mixingItemDisplay.getItemMeta();
			meta.setDisplayName(ChatColor.GOLD + "Mixing");
			mixingItemDisplay.setItemMeta(meta);
			
			RecipeDisplay.CUSTOM_RECIPES.add(new CustomRecipeType("Mixing", mixingItemDisplay, "mix_tannin", Main.INSTANCE, 
					
					new CustomItemStack(ChatColor.RED + "-->", MyItems.glass_jar_tannin.getRootItem().getType(), MyItems.glass_jar_tannin.getDisplayCustomModelData()).AddLoreLine(ChatColor.GREEN + "Fluid: Water").AddEnchant(Enchantment.LUCK, 1, true).AddFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS).GetMyItemStack(),
					
					tannin.GetAmountClone(3), MyItems.glass_jar_water.getRootItem().GetMyItemStack(), bark.GetAmountClone(4)));
			
		
		
		CustomItemStack tannin_source = ItemCreator.RegisterNewItem(new CustomItemStack("Tannin Soruce", Material.GOLD_BLOCK, 70006));
		LiquidBlock tannin_source_b = BlockCreator.RegisterNewLiquid(tannin_source);
				
		com.willm.ModAPI.Blocks.BlockEvents.RegisterBucket(tannin, tannin_source_b);
		
		animal_hide = ItemCreator.RegisterNewItem(new CustomItemStack("Animal Hide", Material.LEATHER, 10001));
		
		ItemCreator.RegisterMobDrop(new MobDrop(EntityType.COW, 600), animal_hide, 1);
		ItemCreator.RegisterMobDrop(new MobDrop(EntityType.COW, 200), animal_hide, 2);
		ItemCreator.RegisterMobDrop(new MobDrop(EntityType.SHEEP, 600), animal_hide, 1);
		ItemCreator.RegisterMobDrop(new MobDrop(EntityType.SHEEP, 200), animal_hide, 2);
		ItemCreator.RegisterMobDrop(new MobDrop(EntityType.HORSE, 1000), animal_hide, 1);
		ItemCreator.RegisterMobDrop(new MobDrop(EntityType.HORSE, 200), animal_hide, 2);
		ItemCreator.RegisterMobDrop(new MobDrop(EntityType.CAMEL, 1000), animal_hide, 1);
		ItemCreator.RegisterMobDrop(new MobDrop(EntityType.CAMEL, 200), animal_hide, 2);
		ItemCreator.RegisterMobDrop(new MobDrop(EntityType.LLAMA, 1000), animal_hide, 1);
		ItemCreator.RegisterMobDrop(new MobDrop(EntityType.LLAMA, 200), animal_hide, 2);
		ItemCreator.RegisterMobDrop(new MobDrop(EntityType.DONKEY, 200), animal_hide, 2);
		ItemCreator.RegisterMobDrop(new MobDrop(EntityType.DONKEY, 1000), animal_hide, 1);
		ItemCreator.RegisterMobDrop(new MobDrop(EntityType.MULE, 200), animal_hide, 1);
		ItemCreator.RegisterMobDrop(new MobDrop(EntityType.MULE, 1000), animal_hide, 1);
		ItemCreator.RegisterMobDrop(new MobDrop(EntityType.POLAR_BEAR, 1000), animal_hide, 3);

		ItemCreator.RegisterMobDrop(new MobDrop(EntityType.RAVAGER, 800), animal_hide, 2);
		ItemCreator.RegisterMobDrop(new MobDrop(EntityType.PANDA, 700), animal_hide, 2);
		ItemCreator.RegisterMobDrop(new MobDrop(EntityType.HOGLIN, 400), animal_hide, 2);

		soaked_animal_hide = ItemCreator.RegisterNewItem(new CustomItemStack("Soaked Animal Hide", Material.LEATHER, 10002));
		//Soaked Hide Recipe Display
			ItemStack soakingItemDisplay = MyItems.glass_jar.GetMyItemStack().clone(); 
			ItemMeta smeta = mixingItemDisplay.getItemMeta();
			smeta.setDisplayName(ChatColor.GOLD + "Soaking");
			soakingItemDisplay.setItemMeta(meta);
			RecipeDisplay.CUSTOM_RECIPES.add(new CustomRecipeType("Soaking", soakingItemDisplay, "soak_animal_hide", Main.INSTANCE, 
					
					new CustomItemStack(ChatColor.RED + "-->", MyItems.glass_jar_tannin.getRootItem().getType(), MyItems.glass_jar_tannin.getDisplayCustomModelData()).AddLoreLine(ChatColor.GREEN + "Fluid: /rTannin").AddEnchant(Enchantment.LUCK, 1, true).AddFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS).GetMyItemStack(),
					
					soaked_animal_hide.GetMyItemStack(), MyItems.glass_jar_tannin.getRootItem().GetMyItemStack(), animal_hide.GetMyItemStack()));

			
		CustomItemStack red_leather = ItemCreator.RegisterNewItem(new CustomItemStack("Red Leather", Material.LEATHER, 20016));
		red_leather.getRecipe(3, "HH", "HS").AddMaterial('H', RecipeBuilder.ItemStackInput(soaked_animal_hide)).AddMaterial('S', Material.WOODEN_SWORD).Finalize();
		
		AddAllLeathers(new String[] {"Black", "White", "Light Gray", "Gray", "Brown", "Orange", "Yellow", "Lime", "Green", "Cyan", "Light Blue", "Blue", "Magenta", "Pink", "Purple"}, red_leather);
		
		leatherColorMap.put(Material.RED_DYE, red_leather);
		
		AddColoredCosmetic("Top Hat", 12005, 12020, new char[] {'D'}, new CustomItemStack[] {leatherColorMap.get(Material.BLACK_DYE)}, "DDD", "C C", "D D");
		AddColoredCosmetic("Cap", 12105, 12114, new char[0], new CustomItemStack[0], "CCC", "C C", "   ");
		AddColoredCosmetic("Sun Glasses", 12205, 12205, new char[] {'L', 'I'}, new CustomItemStack[] {MyItems.lens, MyItems.iron_rod}, "ICI", "L L", "   "); 
		AddColoredCosmetic("Goggles", 12305, 12315, new char[] {'L', 'S'}, new CustomItemStack[] {MyItems.lens}, new Material[] {Material.STRING}, "SSS", "C C", "LCL");
		AddColoredCosmetic("Crown", 12405, 12416, new char[] {'G'}, new CustomItemStack[0], new Material[] {Material.GOLD_INGOT}, "   ", "GCG", "GGG");
		AddColoredCosmetic("Mustache Glasses", 12505, 12505, new char[] {'R', 'L'}, new CustomItemStack[] {MyItems.iron_rod, leatherColorMap.get(Material.BLACK_DYE)}, new Material[0], "   ", "RLR", "CCC");

		//red_top_hat = ItemCreator.RegisterNewItem(new CustomItemStack("Top Hat", Material.WOODEN_SWORD, 12005)).AddFlags(ItemFlag.HIDE_ATTRIBUTES);
		//red_top_hat.getRecipe(1, "B B", "R R", "BBB").AddMaterial('B', RecipeBuilder.ItemStackInput(leatherColorMap.get(Material.BLACK_DYE))).AddMaterial('R', RecipeBuilder.ItemStackInput(red_leather)).Finalize();
	}
	
	static HashMap<Material, CustomItemStack> leatherColorMap = new HashMap<Material, CustomItemStack>();
	
	public static void AddAllLeathers(String[] colors, CustomItemStack rl /*Red Leather*/)
	{
		int i = 20001;
		for(String s : colors)
		{
			CustomItemStack new_leather = ItemCreator.RegisterNewItem(new CustomItemStack(s + " Leather", Material.LEATHER, i));
			
			new_leather.getRecipe(1, "DLD", "   ").AddMaterial('D', Material.valueOf(s.toUpperCase().replace(' ', '_') + "_DYE")).AddMaterial('L', RecipeBuilder.ItemStackInput(rl)).Finalize();
			
			leatherColorMap.put(Material.valueOf(s.toUpperCase().replace(' ', '_') + "_DYE"), new_leather);
			
			i++;
		}
	}
	
	public static void AddColoredCosmetic(String name, int basecmd, int defaultColor, char[] inMats, CustomItemStack[] otherIn, String... recipe) {AddColoredCosmetic(name, basecmd, defaultColor, inMats, otherIn, null, recipe);}
	public static void AddColoredCosmetic(String name, int basecmd, int defaultColor/*Default CMD of item to show in recipe menu*/, char[] inMats, CustomItemStack[] otherIn, Material[] oin, String... recipe)
	{
		ItemCreator.RegisterNewItem(new CustomItemStack(name, Material.WOODEN_SWORD, defaultColor)).AddLoreLine(ChatColor.YELLOW + "Colorable");

		for(Entry<Material, CustomItemStack> e : leatherColorMap.entrySet())
		{
			int cmd = e.getValue().getCustomModelData() - 20001 + basecmd;
			System.out.println(e.getValue().getName().toLowerCase().replace(" ", "_") + name.toLowerCase().replace(' ', '_') + "  CUSTOMCMD: " + cmd);
			
			CustomItemStack cm;
			cm = new CustomItemStack(e.getValue().getName().substring(0, 1) + e.getValue().getName().toLowerCase().replace("leather", "").substring(1) + name, Material.WOODEN_SWORD, cmd);
			
			RecipeBuilder rb = cm.getRecipe(1, recipe[0], recipe[1], recipe[2], e.getValue().getName().toLowerCase().replace(" ", "_") + name.toLowerCase().replace(' ', '_')).AddMaterial('C', RecipeBuilder.ItemStackInput(e.getValue()));
			
			int i = 0;
			for(char c : inMats)
			{
				if(i >= otherIn.length)
				{
					rb.AddMaterial(c, oin[i - otherIn.length]);
				}else {
					rb.AddMaterial(c, RecipeBuilder.ItemStackInput(otherIn[i]));
				}
				i++;
			}
			
			rb.Finalize();
		}
	}
}
