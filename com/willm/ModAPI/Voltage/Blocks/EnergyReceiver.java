package com.willm.ModAPI.Voltage.Blocks;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

import com.willm.ModAPI.Items.BlockCreator;
import com.willm.ModAPI.Items.CustomItemStack;
import com.willm.ModAPI.Items.ItemCreator;
import com.willm.ModAPI.Voltage.Main;

public class EnergyReceiver extends EnergyCompatible {
	
	public EnergyReceiver()
	{
		CustomItemStack cis = ItemCreator.RegisterNewItem(new CustomItemStack("Energy Receiver MK. I", Material.POLISHED_ANDESITE, 21002));
		blockRef = BlockCreator.RegisterNewBlock(cis);
	}

	@Override
	public void Tick(Location loc) {

		if(!sources.containsKey(loc)) {sources.put(loc, 0);}
		if(sources.get(loc) == null) {sources.put(loc, 0);}
		
		for(BlockFace bf : checkFaces)
		{
			Block b = loc.getBlock().getRelative(bf);
			for(EnergyCompatible ec : Main.energyRecievers)
			{
				if(ec.GetBlockRef().CheckForCustomBlock(b))
				{
					ec.AddEnergy(RemoveEnergy(10, loc), b.getLocation());

				}
			}
		}
	}
	
	
}
