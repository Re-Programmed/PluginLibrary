package com.willm.CoreMOD.Power;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import com.willm.CoreMOD.Main;
import com.willm.CoreMOD.MyItems;
import com.willm.ModAPI.Blocks.Machine;
import com.willm.ModAPI.Blocks.MachineConversion;
import com.willm.ModAPI.Items.BlockCreator;
import com.willm.ModAPI.Items.CustomItemStack;
import com.willm.ModAPI.Items.ItemCreator;
import com.willm.ModAPI.RecipeDisplay.CustomRecipeType;
import com.willm.ModAPI.RecipeDisplay.RecipeDisplay;
import com.willm.ModAPI.Voltage.Blocks.EnergyCompatible;

public class ElectricFurnace extends EnergyCompatible {
	
	public ElectricFurnace()
	{
		CustomItemStack cis = ItemCreator.RegisterNewItem(new CustomItemStack("Electric Furnace", Material.POLISHED_ANDESITE, 21006));
		
		cis.AddLoreLine(ChatColor.RED + "Needs Power!");
		
		blockRef = BlockCreator.RegisterNewBlock(cis, "core_mod.drill_idle", 250, 18, "Electric Furnace"
				, new MachineConversion(Crusher.diamond_dust.GetMyItemStack(), new ItemStack(Material.DIAMOND, 1))
				, new MachineConversion(Crusher.iron_dust.GetMyItemStack(), new ItemStack(Material.IRON_INGOT, 1))
				, new MachineConversion(Crusher.gold_dust.GetMyItemStack(), new ItemStack(Material.GOLD_INGOT, 1))
				, new MachineConversion(Crusher.emerald_dust.GetMyItemStack(), new ItemStack(Material.EMERALD, 1))
				, new MachineConversion(Crusher.lapis_dust.GetMyItemStack(), new ItemStack(Material.LAPIS_LAZULI, 1))
				, new MachineConversion(Crusher.netherite_dust.GetMyItemStack(), new ItemStack(Material.NETHERITE_SCRAP, 1))
				, new MachineConversion(Crusher.wolframite_dust.GetMyItemStack(), MyItems.tungsten_ingot.GetMyItemStack())
				, new MachineConversion(Crusher.platinum_dust.GetMyItemStack(), MyItems.platinum_ingot.GetMyItemStack())
				, new MachineConversion(Crusher.titanium_dust.GetMyItemStack(), MyItems.titanium_ingot.GetMyItemStack())
				, new MachineConversion(MyItems.bauxite.GetMyItemStack(), MyItems.aluminum.GetAmountClone(2))

				, new MachineConversion(new ItemStack(Material.IRON_BLOCK), MyItems.steel_ingot.GetMyItemStack())
				, new MachineConversion(new ItemStack(Material.RAW_IRON), new ItemStack(Material.IRON_INGOT, 1))
				, new MachineConversion(new ItemStack(Material.RAW_GOLD), new ItemStack(Material.GOLD_INGOT, 1))
				, new MachineConversion(MyItems.platinum_ore.GetMyItemStack(), MyItems.platinum_ingot.GetMyItemStack())
				, new MachineConversion(MyItems.titanium_ore.GetMyItemStack(), MyItems.titanium_ingot.GetMyItemStack())
				, new MachineConversion(MyItems.wolframite.GetMyItemStack(), MyItems.tungsten_ingot.GetMyItemStack())
				, new MachineConversion(new ItemStack(Material.DIAMOND_ORE), new ItemStack(Material.DIAMOND, 1))
				, new MachineConversion(new ItemStack(Material.SAND), new ItemStack(Material.GLASS))
				, new MachineConversion(new ItemStack(Material.COBBLESTONE), new ItemStack(Material.STONE))
				, new MachineConversion(new ItemStack(Material.STONE), new ItemStack(Material.SMOOTH_STONE))
				, new MachineConversion(new ItemStack(Material.ANCIENT_DEBRIS), new ItemStack(Material.NETHERITE_SCRAP, 1)));


		int i = 0;
		for(MachineConversion m : blockRef.getMachineTemplate().conversions)
		{
			RecipeDisplay.CUSTOM_RECIPES.add(new CustomRecipeType("Electric Smelting", cis.GetMyItemStack(), "electric_furnace_" + (i++), Main.INSTANCE, 
					
					new CustomItemStack(ChatColor.RED + "-->", cis.getType(), cis.getCustomModelData()).AddLoreLine(ChatColor.GREEN + "Speed: 250mt").AddLoreLine(ChatColor.RED + "Requires Power").AddEnchant(Enchantment.LUCK, 1, true).AddFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS).GetMyItemStack(),
					
					m.i2[0], m.i1));
		}
		
	}
	

	@Override
	public void Tick(Location loc) {
		if(!sources.containsKey(loc)) {sources.put(loc, 0);}
		if(sources.get(loc) == null) {sources.put(loc, 0);}
		
		for(Player p : Bukkit.getOnlinePlayers())
		{
			if(p.getLocation().distance(loc) < 2f)
			{
				if(p.isSneaking())
				{
					p.sendMessage(ChatColor.RED + "[ELECTRIC FURNACE POWER]: " + sources.get(loc));
				}
			}
		}
		if(sources.get(loc) > 500)
		{
			for(Machine m : blockRef.m)
			{
				if(m.location.distance(loc) < 0.1f)
				{
					m.productSpeed = blockRef.getMachineTemplate().productSpeed;

					RemoveEnergy(50, loc);
				}
			}
		}else {
			for(Machine m : blockRef.m)
			{
				if(m.location.distance(loc) < 0.1f)
				{
					m.productSpeed = 0.0000005f;
					
				}
			}
		}
		
	
	}
	
	
}
