package com.willm.ModAPI.Voltage.Blocks;

import java.util.HashMap;

import org.bukkit.Location;
import org.bukkit.block.BlockFace;

import com.willm.ModAPI.Blocks.CustomBlock;

public abstract class EnergyCompatible {

	protected static final BlockFace[] checkFaces = new BlockFace[] { BlockFace.DOWN, BlockFace.UP, BlockFace.EAST, BlockFace.WEST, BlockFace.SOUTH, BlockFace.NORTH };

	static final int EnergyCap = 1000000;
	
	protected HashMap<Location, Integer> sources = new HashMap<Location, Integer>();
	
	protected CustomBlock blockRef; 
	public CustomBlock GetBlockRef() { return blockRef; }
	
	public void AddEnergy(int i, Location source) { sources.put(source, i + (sources.containsKey(source) ? (sources.get(source) < EnergyCap ? sources.get(source) : EnergyCap) : 0)); }
	protected int RemoveEnergy(int i, Location source) 
	{ 
		int storedEnergy = sources.get(source);
		int ret = i;
		storedEnergy -= i;
		
		if(storedEnergy < 0)
		{
			ret += storedEnergy;
			storedEnergy = 0;
		}
		
		sources.put(source, storedEnergy);
		return ret;
	}
	
	
	public abstract void Tick(Location loc);
}

