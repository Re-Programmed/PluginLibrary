package com.willm.ModAPI.Terrain;

import com.willm.ModAPI.Main;
import com.willm.ModAPI.Items.CustomItemStack;

public class Ore {

	public final int rarity;
	public final boolean veins;
	public final CustomItemStack drop;
	public final int xp;
	public final int ySpawnCap;
	
	public Ore(CustomItemStack drop, int rarity, boolean veins, int xp, int ySpawnCap)
	{
		this.drop = drop;
		this.rarity = rarity;
		this.veins = veins;
		this.xp = xp;
		this.ySpawnCap = ySpawnCap;
		
		Main.Populator.RegisterOre(this);
	}
}
