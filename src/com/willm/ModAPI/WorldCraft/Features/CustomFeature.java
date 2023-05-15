package com.willm.ModAPI.WorldCraft.Features;

import org.bukkit.Location;
import org.bukkit.Material;

import com.willm.ModAPI.Blocks.CustomBlock;

public class CustomFeature extends FeatureElement {

	private final CustomBlock b;
	
 	public CustomFeature(RelativeLocation location, CustomBlock b) {
		super(location);
		this.b = b;
	}

	@Override
	public void Place(Location location) {
		Location l = this.location.Apply(location);
		b.Place(location);
	}

}
