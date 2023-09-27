package com.willm.ModAPI.WorldCraft.Features;

import java.util.ArrayList;
import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;

public class Biome {

	private ArrayList<Feature> features = new ArrayList<Feature>();
	
	private final int chance;
	private final org.bukkit.block.Biome[] biome_spawn;
	
	public Biome(int chance, org.bukkit.block.Biome... biome_spawn)
	{
		this.biome_spawn = biome_spawn;
		this.chance = chance;
	}
	
	public Biome AddFeature(Feature f)
	{
		features.add(f);
		return this;
	}
	
	public boolean GetChance(Random random)
	{
		return random.nextInt(10001) < chance;
	}
	
	public void GenerateWithChance(World world, Random random, Chunk chunk)
	{
		if(GetChance(random)) {GenerateBiome(world, random, chunk);}
	}
	
	public void GenerateBiome(World world, Random random, Chunk chunk)
	{
		if(!CheckBiome(chunk.getBlock(0, 16, 0).getBiome())) {return;}
		for(Feature f : features)
		{
			if(f.GetFeatureType() == FeatureType.SURFACE)
			{
				for(int x = 0; x < 15; x++)
				{
					for(int z = 0; z < 15; z++)
					{
						Location check = chunk.getBlock(x, 0, z).getLocation();
						check.setY(world.getHighestBlockYAt(check));
						
						f.PlaceWithChance(check, random);
					}
				}
			}
		}
	}
	
	private boolean CheckBiome(org.bukkit.block.Biome b)
	{		
		for(org.bukkit.block.Biome bi : biome_spawn)
		{
			if(bi == b)
			{
				return true;
			}
		}
		
		return false;
	}
	
}
