package com.willm.CoreMOD.Power;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;

import com.willm.ModAPI.Items.BlockCreator;
import com.willm.ModAPI.Items.CustomItemStack;
import com.willm.ModAPI.Items.ItemCreator;
import com.willm.ModAPI.Voltage.Main;
import com.willm.ModAPI.Voltage.Blocks.EnergyCompatible;

import org.bukkit.ChatColor;

public class WindGenerator extends EnergyCompatible {
	
	public WindGenerator()
	{
		CustomItemStack cis = ItemCreator.RegisterNewItem(new CustomItemStack("Wind Generator", Material.POLISHED_ANDESITE, 26102));
		cis.AddLoreLine(ChatColor.GRAY + "Generates power based on height.");
		blockRef = BlockCreator.RegisterNewBlock(cis);
	}
	

	@Override
	public void Tick(Location loc) {
		
		int power = Math.floorDiv(loc.getBlockY(), 20);
		
		for(Player p : Bukkit.getOnlinePlayers())
		{
			if(p.getLocation().distance(loc) < 2f)
			{
				if(p.isSneaking())
				{
					p.sendMessage(ChatColor.BLUE + "[WIND GENERATOR OUTPUT]: " + power + " (Y: " + loc.getBlockY() + ")");
				}
			}
		}
		
		AddEnergy(power * 2, loc);
		
		for(BlockFace bf : checkFaces)
		{
			Block b = loc.getBlock().getRelative(bf);
			for(EnergyCompatible ec : Main.energyRecievers)
			{
				if(ec.GetBlockRef().CheckForCustomBlock(b))
				{
					ec.AddEnergy(RemoveEnergy(power, loc), b.getLocation());
				}
			}
		}
	
	}
	
	
}