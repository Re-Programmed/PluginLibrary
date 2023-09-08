package com.willm.ModAPI.Terrain;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.generator.BlockPopulator;

import com.willm.ModAPI.Items.CustomItemStack;
import com.willm.ModAPI.WorldCraft.PopulationManager;

public class CustomPopulator extends BlockPopulator {

	public static ArrayList<Ore> ores = new ArrayList<Ore>();

	public void RegisterOre(Ore ore) {getOres().add(ore);}
	
	private static PopulationManager pm = null;
	public static void EnableWC(PopulationManager pm) {CustomPopulator.pm = pm;}
	public static PopulationManager GetPM() {return pm;}
	
	@Override
	public void populate(World world, Random random, Chunk chunk) {
		
		if(com.willm.ModAPI.WorldCraft.Main.Enabled && pm != null)
		{

			pm.Populate(world, random, chunk);
		}
		
		for(Ore ore : getOres())
		{
			GenerateOre(world, random, chunk, ore.drop.GetMyItemStack().getType(), ore.rarity, ore.yMinSpawn, ore.ySpawnCap, ore.veins, Material.STONE, ore.drop, ore.placeCustom, ore.mustHaveAir);
		}
	}
	
	
	private static final List<Material> airBlocks = List.of(Material.AIR, Material.CAVE_AIR, Material.WATER, Material.LAVA);
	
	public static void GenerateOre(World world, Random random, Chunk chunk, Material mat, int tries, int heightMin, int heightMax, boolean veinChance, Material replace, CustomItemStack cmd, boolean placeCustom, boolean mustHaveAir)
	{
		int X, Y, Z;
		boolean isStone;
		for (int i = 1; i < tries; i++) {  
		    if (random.nextInt(100) < 60) { 
			X = random.nextInt(15);
			Z = random.nextInt(15);
			Y = random.nextInt(heightMax - heightMin)+heightMin;  
			if (chunk.getBlock(X, Y, Z).getType() == replace) {
				isStone = true;
				while (isStone) {
					if(X > 0 && Y > 0 && Z > 0 && X < 15 && Z < 15)
					{
						Block b = chunk.getBlock(X, Y, Z);
						boolean canPlace = !mustHaveAir;
						if(mustHaveAir)
						{
							canPlace = airBlocks.contains(b.getRelative(BlockFace.EAST).getType())
									|| airBlocks.contains(b.getRelative(BlockFace.WEST).getType())
									|| airBlocks.contains(b.getRelative(BlockFace.SOUTH).getType())
									|| airBlocks.contains(b.getRelative(BlockFace.NORTH).getType())
									|| airBlocks.contains(b.getRelative(BlockFace.UP).getType())
									|| airBlocks.contains(b.getRelative(BlockFace.DOWN).getType());
						}
						
						if(canPlace)
						{
							if(!placeCustom)
							{
								b.setType(mat);
								b.setBiome(Biome.THE_END);
							}else {
								cmd.getRelatedBlock().Place(b.getLocation());
							}
						}

						

					}else {
						break;
					}
					if (veinChance)  {  
						switch (random.nextInt(6)) {  
						case 0: X++; break;
						case 1: Y++; break;
						case 2: Z++; break;
						case 3: X--; break;
						case 4: Y--; break;
						case 5: Z--; break;
						}
						isStone = (chunk.getBlock(X, Y, Z).getType() == replace) && (chunk.getBlock(X, Y, Z).getType() != mat);
					} else isStone = false;
				}
			}
		    }
		}
	}
	

	public ArrayList<Ore> getOres() {
		return ores;
	}
	
}
