package com.willm.ModAPI.WorldCraft.Features;

import java.util.ArrayList;
import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Material;

public class Feature {

	private ArrayList<FeatureElement> elements = new ArrayList<FeatureElement>();
	
	private final int spawnChance;
		
	private final FeatureType type = FeatureType.SURFACE;
	
	private final Material[] materialCheck;
	
	public FeatureType GetFeatureType() {return type;}
	
	public Feature(int spawnChance, Material... materialCheck)
	{
		this.spawnChance = spawnChance;
		this.materialCheck = materialCheck;
	}
	
	public Feature AddElement(FeatureElement fe)
	{
		elements.add(fe);
		return this;
	}
	
	//Returns true based on spawnChance.
	public boolean GetChance(Random random)
	{
		return random.nextInt(10001) < spawnChance;
	}
	
	//Place this feature at the input location.
	public void Place(Location root_location)
	{
		boolean ret = true;
		for(Material m : materialCheck)
		{
			if(root_location.getBlock().getType() == m)
			{
				ret = false;
				break;
			}
		}
		if(ret) {return;}
		for(FeatureElement fe : elements)
		{
			fe.Place(root_location);
		}
	}
	
	public void PlaceWithChance(Location root_location, Random random)
	{
		if(GetChance(random)) {Place(root_location);}
	}
	
}
