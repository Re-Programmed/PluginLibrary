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

public class SolarPanel extends EnergyCompatible {
	
	public SolarPanel()
	{
		CustomItemStack cis = ItemCreator.RegisterNewItem(new CustomItemStack("Solar Panel", Material.POLISHED_ANDESITE, 21051));
		blockRef = BlockCreator.RegisterNewBlock(cis);
	}
	

	@Override
	public void Tick(Location loc) {
		for(Player p : Bukkit.getOnlinePlayers())
		{
			if(p.getLocation().distance(loc) < 2f)
			{
				if(p.isSneaking())
				{
					p.sendMessage(ChatColor.BLUE + "[SOLAR PANEL OUTPUT]: 1");
				}
			}
		}
		
		AddEnergy(5, loc);
		
		for(BlockFace bf : checkFaces)
		{
			Block b = loc.getBlock().getRelative(bf);
			for(EnergyCompatible ec : Main.energyRecievers)
			{
				if(ec.GetBlockRef().CheckForCustomBlock(b))
				{
					ec.AddEnergy(RemoveEnergy(2, loc), b.getLocation());
				}
			}
		}
	
	}
	
	
}