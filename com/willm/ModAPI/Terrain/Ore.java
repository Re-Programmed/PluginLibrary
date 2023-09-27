package com.willm.ModAPI.Terrain;

import com.willm.ModAPI.Main;
import com.willm.ModAPI.Items.CustomItemStack;

public class Ore {

	public final int rarity;
	public final boolean veins;
	public final CustomItemStack drop;
	public final int xp;
	public final int ySpawnCap;
	
	public final int yMinSpawn;
	
	public final boolean placeCustom;
	public final boolean mustHaveAir;
	
	public final boolean netherOre;
	
	public Ore(CustomItemStack drop, int rarity, boolean veins, int xp, int ySpawnCap)
	{
		this.drop = drop;
		this.rarity = rarity;
		this.veins = veins;
		this.xp = xp;
		this.ySpawnCap = ySpawnCap;
		this.placeCustom = false;
		this.mustHaveAir = false;
		
		this.yMinSpawn = 0;
		
		netherOre = false;
		
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
		
		this.yMinSpawn = 0;
		
		netherOre = false;
		
		Main.Populator.RegisterOre(this);
	}
	
	public Ore(CustomItemStack drop, int rarity, boolean veins, int xp, int ySpawnCap, int yMinSpawn, boolean placeCustom, boolean mustHaveAir)
	{
		this.drop = drop;
		this.rarity = rarity;
		this.veins = veins;
		this.xp = xp;
		this.ySpawnCap = ySpawnCap;
		this.placeCustom = placeCustom;
		this.mustHaveAir = mustHaveAir;
		
		this.yMinSpawn = yMinSpawn;
		
		netherOre = false;
		
		Main.Populator.RegisterOre(this);
	}
	
	public Ore(boolean nether, CustomItemStack drop, int rarity, boolean veins, int xp, int ySpawnCap, int yMinSpawn, boolean placeCustom, boolean mustHaveAir)
	{
		this.drop = drop;
		this.rarity = rarity;
		this.veins = veins;
		this.xp = xp;
		this.ySpawnCap = ySpawnCap;
		this.placeCustom = placeCustom;
		this.mustHaveAir = mustHaveAir;
		
		this.yMinSpawn = yMinSpawn;
		
		netherOre = nether;
		
		if(nether)
		{
			Main.NetherPopulator.RegisterOre(this);
		}else {
			Main.Populator.RegisterOre(this);
		}
	}
}
