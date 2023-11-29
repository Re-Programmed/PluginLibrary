package com.willm.CoreMOD;

import org.bukkit.Material;

import com.willm.ModAPI.Terrain.CustomPopulator;
import com.willm.ModAPI.WorldCraft.Features.Biome;
import com.willm.ModAPI.WorldCraft.Features.CustomFeature;
import com.willm.ModAPI.WorldCraft.Features.Feature;
import com.willm.ModAPI.WorldCraft.Features.MaterialFeature;
import com.willm.ModAPI.WorldCraft.Features.RelativeLocation;

public class MyBiomes {

	public static void InitBiomes()
	{
		CustomPopulator.GetPM().AddBiome(new Biome(150, org.bukkit.block.Biome.FOREST, org.bukkit.block.Biome.BIRCH_FOREST).AddFeature(new Feature(500, Material.GRASS_BLOCK)
				.AddElement(new CustomFeature(RelativeLocation.Zero, MyItems.tomato_plant.getRelatedBlock()))
				));
		
		CustomPopulator.GetPM().AddBiome(new Biome(150, org.bukkit.block.Biome.FOREST, org.bukkit.block.Biome.PLAINS, org.bukkit.block.Biome.BIRCH_FOREST).AddFeature(new Feature(70, Material.GRASS_BLOCK)
				.AddElement(new CustomFeature(RelativeLocation.Zero, MyItems.spring_water_source.getRelatedBlock()))
				));
		
		CustomPopulator.GetPM().AddBiome(new Biome(250, org.bukkit.block.Biome.DESERT)
				.AddFeature(new Feature(800, Material.SAND).AddElement(new CustomFeature(new RelativeLocation(0, -1, 0), MyItems.titanium_ore.getRelatedBlock())))
				.AddFeature(new Feature(200, Material.SAND).AddElement(new CustomFeature(RelativeLocation.Zero, MyItems.titanium_ore.getRelatedBlock())))
				);
		
		CustomPopulator.GetPM().AddBiome(new Biome(300, org.bukkit.block.Biome.DESERT)
				.AddFeature(new Feature(1500, Material.SAND).AddElement(new CustomFeature(new RelativeLocation(0, -1, 0), MyItems.limestone_block.getRelatedBlock())))
				.AddFeature(new Feature(700, Material.SAND).AddElement(new CustomFeature(RelativeLocation.Zero, MyItems.limestone_block.getRelatedBlock())))
				);
		
		CustomPopulator.GetPM().AddBiome(new Biome(1800, org.bukkit.block.Biome.BEACH)
				.AddFeature(new Feature(125, Material.SAND).AddElement(new CustomFeature(RelativeLocation.Zero, MyItems.salt_block.getRelatedBlock()))));
		
		CustomPopulator.GetPM().AddBiome(new Biome(1750, org.bukkit.block.Biome.STONY_PEAKS, org.bukkit.block.Biome.WINDSWEPT_HILLS, org.bukkit.block.Biome.WINDSWEPT_GRAVELLY_HILLS, org.bukkit.block.Biome.FLOWER_FOREST, org.bukkit.block.Biome.SUNFLOWER_PLAINS)
				.AddFeature(new Feature(150, Material.GRASS_BLOCK, Material.GRAVEL, Material.COARSE_DIRT, Material.STONE)
						.AddElement(new CustomFeature(new RelativeLocation(0, 1, 0), MyItems.pine_log.getRelatedBlock()))
						.AddElement(new CustomFeature(new RelativeLocation(0, 2, 0), MyItems.pine_log.getRelatedBlock()))
						.AddElement(new CustomFeature(new RelativeLocation(0, 3, 0), MyItems.pine_log.getRelatedBlock()))
						.AddElement(new CustomFeature(new RelativeLocation(0, 4, 0), MyItems.pine_log.getRelatedBlock()))
						.AddElement(new CustomFeature(new RelativeLocation(0, 5, 0), MyItems.pine_log.getRelatedBlock()))
						.AddElement(new MaterialFeature(new RelativeLocation(0, 6, 0), Material.SPRUCE_LOG))
						
						.AddElement(new MaterialFeature(new RelativeLocation(0, 7, 0), Material.SPRUCE_LEAVES))
						
						.AddElement(new MaterialFeature(new RelativeLocation(1, 6, 0), Material.SPRUCE_LEAVES))
						.AddElement(new MaterialFeature(new RelativeLocation(-1, 6, 0), Material.SPRUCE_LEAVES))
						.AddElement(new MaterialFeature(new RelativeLocation(0, 6, 1), Material.SPRUCE_LEAVES))
						.AddElement(new MaterialFeature(new RelativeLocation(0, 6, -1), Material.SPRUCE_LEAVES))

						.AddElement(new MaterialFeature(new RelativeLocation(1, 5, 0), Material.SPRUCE_LEAVES))
						.AddElement(new MaterialFeature(new RelativeLocation(-1, 5, 0), Material.SPRUCE_LEAVES))
						.AddElement(new MaterialFeature(new RelativeLocation(0, 5, 1), Material.SPRUCE_LEAVES))
						.AddElement(new MaterialFeature(new RelativeLocation(0, 5, -1), Material.SPRUCE_LEAVES))
						.AddElement(new MaterialFeature(new RelativeLocation(2, 5, 0), Material.SPRUCE_LEAVES))
						.AddElement(new MaterialFeature(new RelativeLocation(-2, 5, 0), Material.SPRUCE_LEAVES))
						.AddElement(new MaterialFeature(new RelativeLocation(0, 5, 2), Material.SPRUCE_LEAVES))
						.AddElement(new MaterialFeature(new RelativeLocation(0, 5, -2), Material.SPRUCE_LEAVES))
						.AddElement(new MaterialFeature(new RelativeLocation(1, 5, 1), Material.SPRUCE_LEAVES))
						.AddElement(new MaterialFeature(new RelativeLocation(1, 5, -1), Material.SPRUCE_LEAVES))
						.AddElement(new MaterialFeature(new RelativeLocation(-1, 5, -1), Material.SPRUCE_LEAVES))
						.AddElement(new MaterialFeature(new RelativeLocation(-1, 5, 1), Material.SPRUCE_LEAVES))
						
						.AddElement(new MaterialFeature(new RelativeLocation(1, 4, 0), Material.SPRUCE_LEAVES))
						.AddElement(new MaterialFeature(new RelativeLocation(-1, 4, 0), Material.SPRUCE_LEAVES))
						.AddElement(new MaterialFeature(new RelativeLocation(0, 4, 1), Material.SPRUCE_LEAVES))
						.AddElement(new MaterialFeature(new RelativeLocation(0, 4, -1), Material.SPRUCE_LEAVES))

						.AddElement(new MaterialFeature(new RelativeLocation(1, 3, 0), Material.SPRUCE_LEAVES))
						.AddElement(new MaterialFeature(new RelativeLocation(-1, 3, 0), Material.SPRUCE_LEAVES))
						.AddElement(new MaterialFeature(new RelativeLocation(0, 3, 1), Material.SPRUCE_LEAVES))
						.AddElement(new MaterialFeature(new RelativeLocation(0, 3, -1), Material.SPRUCE_LEAVES))
						.AddElement(new MaterialFeature(new RelativeLocation(2, 3, 0), Material.SPRUCE_LEAVES))
						.AddElement(new MaterialFeature(new RelativeLocation(-2, 3, 0), Material.SPRUCE_LEAVES))
						.AddElement(new MaterialFeature(new RelativeLocation(0, 3, 2), Material.SPRUCE_LEAVES))
						.AddElement(new MaterialFeature(new RelativeLocation(0, 3, -2), Material.SPRUCE_LEAVES))
						.AddElement(new MaterialFeature(new RelativeLocation(1, 3, 1), Material.SPRUCE_LEAVES))
						.AddElement(new MaterialFeature(new RelativeLocation(1, 3, -1), Material.SPRUCE_LEAVES))
						.AddElement(new MaterialFeature(new RelativeLocation(-1, 3, -1), Material.SPRUCE_LEAVES))
						.AddElement(new MaterialFeature(new RelativeLocation(-1, 3, 1), Material.SPRUCE_LEAVES))
						
						.AddElement(new MaterialFeature(new RelativeLocation(2, 3, 1), Material.SPRUCE_LEAVES))
						.AddElement(new MaterialFeature(new RelativeLocation(1, 3, 2), Material.SPRUCE_LEAVES))
						.AddElement(new MaterialFeature(new RelativeLocation(-2, 3, 1), Material.SPRUCE_LEAVES))
						.AddElement(new MaterialFeature(new RelativeLocation(-1, 3, 2), Material.SPRUCE_LEAVES))
						.AddElement(new MaterialFeature(new RelativeLocation(2, 3, -1), Material.SPRUCE_LEAVES))
						.AddElement(new MaterialFeature(new RelativeLocation(1, 3, -2), Material.SPRUCE_LEAVES))
						.AddElement(new MaterialFeature(new RelativeLocation(-2, 3, -1), Material.SPRUCE_LEAVES))
						.AddElement(new MaterialFeature(new RelativeLocation(-1, 3, -2), Material.SPRUCE_LEAVES))
						
						.AddElement(new MaterialFeature(new RelativeLocation(3, 3, 0), Material.SPRUCE_LEAVES))
						.AddElement(new MaterialFeature(new RelativeLocation(-3, 3, 0), Material.SPRUCE_LEAVES))
						.AddElement(new MaterialFeature(new RelativeLocation(0, 3, 3), Material.SPRUCE_LEAVES))
						.AddElement(new MaterialFeature(new RelativeLocation(0, 3, -3), Material.SPRUCE_LEAVES))

						));
		
		CustomPopulator.GetPM().AddBiome(new Biome(250, org.bukkit.block.Biome.TAIGA, org.bukkit.block.Biome.SNOWY_TAIGA).AddFeature(new Feature(500, Material.GRASS_BLOCK).AddElement(new CustomFeature(RelativeLocation.Zero, MyItems.pepper_plant.getRelatedBlock()))));
	
		CustomPopulator.GetPM().AddBiome(new Biome(250, org.bukkit.block.Biome.RIVER).AddFeature(new Feature(600, Material.GRASS_BLOCK, Material.DIRT).AddElement(new CustomFeature(RelativeLocation.Zero, MyItems.onion_plant.getRelatedBlock()))));

	}
	
}
