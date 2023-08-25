package com.willm.CoreMOD.Power;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.willm.ModAPI.Blocks.Machine;
import com.willm.ModAPI.Blocks.MachineConversion;
import com.willm.ModAPI.Items.BlockCreator;
import com.willm.ModAPI.Items.CustomItemStack;
import com.willm.ModAPI.Items.ItemCreator;
import com.willm.ModAPI.Voltage.Main;
import com.willm.ModAPI.Voltage.Blocks.EnergyCompatible;

public class CoalGenerator extends EnergyCompatible {

	public int cooldown = 0;
	
	public CoalGenerator()
	{
		CustomItemStack cis = ItemCreator.RegisterNewItem(new CustomItemStack("Coal Generator", Material.POLISHED_ANDESITE, 21025));
				
		blockRef = BlockCreator.RegisterNewBlock(cis, "core_mod.drill_idle", 0, 27, "Coal Generator", new MachineConversion(new ItemStack(Material.COAL, 1), new CustomItemStack("Power", Material.COAL, 105).GetMyItemStack()));
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
					if(m.getInventory().contains(Material.COAL))
					{
						cooldown = 75;
						output = true;
						AddEnergy(1000, loc);
						
						for(BlockFace bf : checkFaces)
						{
							Block b = loc.getBlock().getRelative(bf);
							for(EnergyCompatible ec : Main.energyRecievers)
							{
								if(ec.GetBlockRef().CheckForCustomBlock(b))
								{
									ec.AddEnergy(RemoveEnergy(575, loc), b.getLocation());
								}
							}
						}
						
						m.getInventory().removeItem(new ItemStack(Material.COAL, 1));
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
					p.sendMessage(ChatColor.GREEN + "[COAL GENERATOR OUTPUT]: " + (output ? "575" : "0"));
				}
			}
		}
	
	}
	
	
}