package com.willm.CoreMOD.Power;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.willm.CoreMOD.MyItems;
import com.willm.ModAPI.Blocks.Machine;
import com.willm.ModAPI.Blocks.MachineConversion;
import com.willm.ModAPI.Items.BlockCreator;
import com.willm.ModAPI.Items.CustomItemStack;
import com.willm.ModAPI.Items.ItemCreator;
import com.willm.ModAPI.Voltage.Blocks.EnergyCompatible;

public class Crusher extends EnergyCompatible {
	
	public static CustomItemStack iron_dust, gold_dust, emerald_dust, diamond_dust, lapis_dust, obsidian_dust, wolframite_dust, platinum_dust, titanium_dust, netherite_dust;
	
	public Crusher()
	{
		CustomItemStack cis = ItemCreator.RegisterNewItem(new CustomItemStack("Electric Crusher", Material.POLISHED_ANDESITE, 22006));
		
		cis.AddLoreLine(ChatColor.RED + "Needs Power!");
		
		iron_dust = ItemCreator.RegisterNewItem(new CustomItemStack("Pulverized Iron", Material.GUNPOWDER, 30001)).AddLoreLine(ChatColor.GRAY + "Raw Iron -> From Electric Crusher");
		gold_dust = ItemCreator.RegisterNewItem(new CustomItemStack("Gold Dust", Material.GUNPOWDER, 30002)).AddLoreLine(ChatColor.GRAY + "Raw Gold -> From Electric Crusher");
		emerald_dust = ItemCreator.RegisterNewItem(new CustomItemStack("Emerald Dust", Material.GUNPOWDER, 30003)).AddLoreLine(ChatColor.GRAY + "Emerald Ore -> From Electric Crusher");
		diamond_dust = ItemCreator.RegisterNewItem(new CustomItemStack("Diamond Dust", Material.GUNPOWDER, 30004)).AddLoreLine(ChatColor.GRAY + "Diamond Ore -> From Electric Crusher");
		lapis_dust = ItemCreator.RegisterNewItem(new CustomItemStack("Lapis Dust", Material.GUNPOWDER, 30005)).AddLoreLine(ChatColor.GRAY + "Lapis Ore -> From Electric Crusher");
		obsidian_dust = ItemCreator.RegisterNewItem(new CustomItemStack("Obsidian Dust", Material.GUNPOWDER, 30006)).AddLoreLine(ChatColor.GRAY + "Obsidian -> From Electric Crusher");
		wolframite_dust = ItemCreator.RegisterNewItem(new CustomItemStack("Wolframite Dust", Material.GUNPOWDER, 30007)).AddLoreLine(ChatColor.GRAY + "Wolframite -> From Electric Crusher");
		platinum_dust = ItemCreator.RegisterNewItem(new CustomItemStack("Platinum Dust", Material.GUNPOWDER, 30008)).AddLoreLine(ChatColor.GRAY + "Platinum Ore -> From Electric Crusher");
		titanium_dust = ItemCreator.RegisterNewItem(new CustomItemStack("Titanium Dust", Material.GUNPOWDER, 30009)).AddLoreLine(ChatColor.GRAY + "Titanium Ore -> From Electric Crusher");
		netherite_dust = ItemCreator.RegisterNewItem(new CustomItemStack("Netherite Dust", Material.GUNPOWDER, 30010)).AddLoreLine(ChatColor.GRAY + "Ancient Debris -> From Electric Crusher");

		blockRef = BlockCreator.RegisterNewBlock(cis, "core_mod.steam", 150, 18, "Electric Crusher"
				, new MachineConversion(MyItems.wolframite.GetMyItemStack(), wolframite_dust.GetAmountClone(3))
				, new MachineConversion(MyItems.platinum_ore.GetMyItemStack(), platinum_dust.GetAmountClone(3))
				, new MachineConversion(MyItems.titanium_ore.GetMyItemStack(), titanium_dust.GetAmountClone(3))
				, new MachineConversion(new ItemStack(Material.RAW_IRON), iron_dust.GetAmountClone(3))
				, new MachineConversion(new ItemStack(Material.RAW_GOLD), gold_dust.GetAmountClone(3))
				, new MachineConversion(new ItemStack(Material.EMERALD_ORE), emerald_dust.GetAmountClone(3))
				, new MachineConversion(new ItemStack(Material.DIAMOND_ORE), diamond_dust.GetAmountClone(3))
				, new MachineConversion(new ItemStack(Material.LAPIS_ORE), lapis_dust.GetAmountClone(30))
				, new MachineConversion(new ItemStack(Material.OBSIDIAN), obsidian_dust.GetAmountClone(2))
				, new MachineConversion(new ItemStack(Material.ANCIENT_DEBRIS), netherite_dust.GetAmountClone(2))
				, new MachineConversion(MyItems.limestone_block.GetMyItemStack(), MyItems.limestone_powder.GetAmountClone(4))
				);


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
					p.sendMessage(ChatColor.RED + "[ELECTRIC CRUSHER POWER]: " + sources.get(loc));
				}
			}
		}
		if(sources.get(loc) > 700)
		{
			for(Machine m : blockRef.m)
			{
				if(m.location.distance(loc) < 0.1f)
				{
					m.productSpeed = blockRef.getMachineTemplate().productSpeed;

					RemoveEnergy(70, loc);
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
