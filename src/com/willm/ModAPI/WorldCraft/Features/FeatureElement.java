package com.willm.ModAPI.WorldCraft.Features;

import org.bukkit.Location;

//A block in a feature.
public abstract class FeatureElement {

	protected final RelativeLocation location;

	public abstract void Place(Location location);
	
	public FeatureElement(RelativeLocation location)
	{
		this.location = location;
	}
}
