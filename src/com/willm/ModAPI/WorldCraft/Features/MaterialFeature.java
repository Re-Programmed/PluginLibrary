package com.willm.ModAPI.WorldCraft.Features;

import org.bukkit.Location;
import org.bukkit.Material;

public class MaterialFeature extends FeatureElement {

	private final Material material;
	
	public MaterialFeature(RelativeLocation location, Material material) {
		super(location);
		this.material = material;
	}

	@Override
	public void Place(Location location) {
		Location l = this.location.Apply(location);
		l.getBlock().setType(material);
	}

}
