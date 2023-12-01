package com.willm.ModAPI.Voltage.Blocks;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

import com.willm.ModAPI.Items.BlockCreator;
import com.willm.ModAPI.Items.CustomItemStack;
import com.willm.ModAPI.Items.ItemCreator;
import com.willm.ModAPI.Voltage.Main;

public class CreativeEnergySource extends EnergyCompatible {
	
	public CreativeEnergySource()
	{
		CustomItemStack cis = ItemCreator.RegisterNewItem(new CustomItemStack("Creative Energy Source", Material.POLISHED_ANDESITE, 21001));
		blockRef = BlockCreator.RegisterNewBlock(cis);
	}
	

	@Override
	public void Tick(Location loc) {

	//	AddEnergy(60, loc);
		
		for(BlockFace bf : checkFaces)
		{
			Block b = loc.getBlock().getRelative(bf);
			for(EnergyCompatible ec : Main.energyRecievers)
			{
				if(ec.GetBlockRef().CheckForCustomBlock(b))
				{
				//	ec.AddEnergy(RemoveEnergy(10, loc), b.getLocation());
				}
			}
		}
	
	}
	
	
}
