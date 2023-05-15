package com.willm.CoreMOD;

import org.bukkit.Material;

import com.willm.ModAPI.Terrain.CustomPopulator;
import com.willm.ModAPI.WorldCraft.Features.Biome;
import com.willm.ModAPI.WorldCraft.Features.CustomFeature;
import com.willm.ModAPI.WorldCraft.Features.Feature;
import com.willm.ModAPI.WorldCraft.Features.RelativeLocation;

public class MyBiomes {

	public static void InitBiomes()
	{
		CustomPopulator.GetPM().AddBiome(new Biome(1000, org.bukkit.block.Biome.FOREST, org.bukkit.block.Biome.BIRCH_FOREST).AddFeature(new Feature(1000, Material.GRASS_BLOCK)
				.AddElement(new CustomFeature(RelativeLocation.Zero, MyItems.tomato_plant.getRelatedBlock()))
				));
		
		CustomPopulator.GetPM().AddBiome(new Biome(150, org.bukkit.block.Biome.FOREST, org.bukkit.block.Biome.PLAINS, org.bukkit.block.Biome.BIRCH_FOREST).AddFeature(new Feature(250, Material.GRASS_BLOCK)
				.AddElement(new CustomFeature(RelativeLocation.Zero, MyItems.spring_water_source.getRelatedBlock()))
				));
		
		CustomPopulator.GetPM().AddBiome(new Biome(250, org.bukkit.block.Biome.DESERT)
				.AddFeature(new Feature(800, Material.SAND).AddElement(new CustomFeature(new RelativeLocation(0, -1, 0), MyItems.titanium_ore.getRelatedBlock())))
				.AddFeature(new Feature(200, Material.SAND).AddElement(new CustomFeature(RelativeLocation.Zero, MyItems.titanium_ore.getRelatedBlock())))
				);
		
		CustomPopulator.GetPM().AddBiome(new Biome(250, org.bukkit.block.Biome.TAIGA, org.bukkit.block.Biome.SNOWY_TAIGA).AddFeature(new Feature(1000, Material.GRASS_BLOCK).AddElement(new CustomFeature(RelativeLocation.Zero, MyItems.pepper_plant.getRelatedBlock()))));
	}
	
}
