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

import net.md_5.bungee.api.ChatColor;

public class GeothermalGenerator extends EnergyCompatible {
	
	public GeothermalGenerator()
	{
		CustomItemStack cis = ItemCreator.RegisterNewItem(new CustomItemStack("Geothermal Generator", Material.POLISHED_ANDESITE, 22051));
		cis.AddLoreLine(ChatColor.GRAY + "Place near lava to generate power.").AddLoreLine(ChatColor.GRAY + "Will also generate power based on biome.");
		blockRef = BlockCreator.RegisterNewBlock(cis);
	}
	

	@Override
	public void Tick(Location loc) {
		
		int lavaBoost = 3;
		
		switch(loc.getBlock().getBiome())
		{
		case BEACH:
			lavaBoost += 1;
			break;
		case COLD_OCEAN:
			lavaBoost -= 2;
			break;
		case DEEP_COLD_OCEAN:
			lavaBoost -= 2;
			break;
		case DEEP_FROZEN_OCEAN:
			lavaBoost -= 3;
			break;
		case DESERT:
			lavaBoost += 3;
			break;
		case DESERT_HILLS:
			lavaBoost += 3;
			break;
		case DESERT_LAKES:
			lavaBoost += 2;
			break;
		case FROZEN_OCEAN:
			lavaBoost -= 3;
			break;
		case FROZEN_RIVER:
			lavaBoost -= 3;
			break;
		case ICE_SPIKES:
			lavaBoost -= 3;
			break;
		case MOUNTAINS:
			lavaBoost -= 1;
			break;
		case MOUNTAIN_EDGE:
			lavaBoost -= 1;
			break;
		case NETHER_WASTES:
			lavaBoost += 3;
			break;
		case SNOWY_BEACH:
			lavaBoost -= 2;
			break;
		case SNOWY_MOUNTAINS:
			lavaBoost -= 3;
			break;
		case SNOWY_TAIGA:
			lavaBoost -= 2;
			break;
		case SNOWY_TAIGA_HILLS:
			lavaBoost -= 2;
			break;
		case SNOWY_TAIGA_MOUNTAINS:
			lavaBoost -= 2;
			break;
		case SNOWY_TUNDRA:
			lavaBoost -= 3;
			break;
		case SWAMP:
			lavaBoost -= 1;
			break;
		case SWAMP_HILLS:
			lavaBoost -= 1;
			break;
		default:
			break;
		
		}
		
		byte lavaUp = 0;
		
		for(BlockFace bf : checkFaces)
		{
			if(loc.getBlock().getRelative(bf).getType() == Material.LAVA)
			{
				lavaBoost += 3;
				lavaUp++;
			}
		}
		
		AddEnergy(lavaBoost * 2, loc);
		
		for(BlockFace bf : checkFaces)
		{
			Block b = loc.getBlock().getRelative(bf);
			for(EnergyCompatible ec : Main.energyRecievers)
			{
				if(ec.GetBlockRef().CheckForCustomBlock(b))
				{
					ec.AddEnergy(RemoveEnergy(lavaBoost, loc), b.getLocation());
				}
			}
		}
		
		for(Player p : Bukkit.getOnlinePlayers())
		{
			if(p.getLocation().distance(loc) < 2f)
			{
				if(p.isSneaking())
				{
					p.sendMessage(ChatColor.GOLD + "[GEOTHERMAL GENERATOR OUTPUT]: " + lavaBoost + " (" + loc.getBlock().getBiome().toString().replace("_", " ") + ", " + lavaUp + "x LAVA)");
				}
			}
		}
		
		
	
	}
	
	
}