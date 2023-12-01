package com.willm.CoreMOD.Power;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;

import com.willm.CoreMOD.MyItems;
import com.willm.ModAPI.Blocks.Machine;
import com.willm.ModAPI.Blocks.MachineConversion;
import com.willm.ModAPI.Items.BlockCreator;
import com.willm.ModAPI.Items.CustomItemStack;
import com.willm.ModAPI.Items.ItemCreator;
import com.willm.ModAPI.Voltage.Main;
import com.willm.ModAPI.Voltage.Blocks.EnergyCompatible;

public class HydrogenGenerator extends EnergyCompatible {
	
	public int cooldown = 0;
	
	public HydrogenGenerator()
	{
		CustomItemStack cis = ItemCreator.RegisterNewItem(new CustomItemStack("Hydrogen Generator", Material.POLISHED_ANDESITE, 21027));
				
		blockRef = BlockCreator.RegisterNewBlock(cis, "core_mod.drill_idle", 0, 27, "Hydrogen Generator", new MachineConversion(MyItems.hydrogen.GetMyItemStack(), new CustomItemStack("Power", Material.COAL, 105).GetMyItemStack()));
	}
	

	@Override
	public void Tick(Location loc) {

		cooldown--;
		boolean output = false;
		
		if(cooldown < 0)
		{
			for(Machine m : blockRef.m)
			{
				if(m.location.distance(loc) < 0.1f)
				{
					if(m.getInventory().containsAtLeast(MyItems.hydrogen.GetMyItemStack(), 1))
					{
						cooldown = 13;
						output = true;
						AddEnergy(1200, loc);
						
						for(BlockFace bf : checkFaces)
						{
							Block b = loc.getBlock().getRelative(bf);
							for(EnergyCompatible ec : Main.energyRecievers)
							{
								if(ec.GetBlockRef().CheckForCustomBlock(b))
								{
									ec.AddEnergy(RemoveEnergy(700, loc), b.getLocation());
								}
							}
						}
						
						if(new Random().nextInt(4) == 1)
						{
							m.getInventory().removeItem(MyItems.hydrogen.GetMyItemStack());
						}
					}
	
					
				}
			}
		}
		
		for(Player p : Bukkit.getOnlinePlayers())
		{
			if(p.getLocation().distance(loc) < 2f)
			{
				if(p.isSneaking())
				{
					p.sendMessage(ChatColor.GRAY + "[HYDROGEN GENERATOR OUTPUT]: " + (output ? "700" : "0"));
				}
			}
		}
	
	}
}