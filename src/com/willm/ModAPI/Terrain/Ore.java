package com.willm.ModAPI.Terrain;

import com.willm.ModAPI.Main;
import com.willm.ModAPI.Items.CustomItemStack;

public class Ore {

	public final int rarity;
	public final boolean veins;
	public final CustomItemStack drop;
	public final int xp;
	public final int ySpawnCap;
	
	public final boolean placeCustom;
	public final boolean mustHaveAir;
	
	public Ore(CustomItemStack drop, int rarity, boolean veins, int xp, int ySpawnCap)
	{
		this.drop = drop;
		this.rarity = rarity;
		this.veins = veins;
		this.xp = xp;
		this.ySpawnCap = ySpawnCap;
		this.placeCustom = false;
		this.mustHaveAir = false;
		
		Main.Populator.RegisterOre(this);
	}
	
	public Ore(CustomItemStack drop, int rarity, boolean veins, int xp, int ySpawnCap, boolean placeCustom, boolean mustHaveAir)
	{
		this.drop = drop;
		this.rarity = rarity;
		this.veins = veins;
		this.xp = xp;
		this.ySpawnCap = ySpawnCap;
		this.placeCustom = placeCustom;
		this.mustHaveAir = mustHaveAir;
		
		Main.Populator.RegisterOre(this);
	}
}
