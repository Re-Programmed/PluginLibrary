package com.willm.ModAPI.WorldCraft;

import java.util.ArrayList;
import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.World;

import com.willm.ModAPI.WorldCraft.Features.Biome;

public class PopulationManager {

	private ArrayList<Biome> b = new ArrayList<Biome>();
	
	public void AddBiome(Biome b)
	{
		this.b.add(b);
	}
	
	public void Populate(World world, Random random, Chunk chunk)
	{
		for(Biome bi : b)
		{

			bi.GenerateWithChance(world, random, chunk);
		}
	}
	
}
