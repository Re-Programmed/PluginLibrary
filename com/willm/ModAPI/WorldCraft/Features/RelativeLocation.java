package com.willm.ModAPI.WorldCraft.Features;

import org.bukkit.Location;

public class RelativeLocation {

	public final int X, Y, Z;
	
	public final static RelativeLocation Zero = new RelativeLocation(0,0,0);
	
	public RelativeLocation(int x, int y, int z)
	{
		this.X = x;
		this.Y = y;
		this.Z = z;
	}

	public Location Apply(Location location) {
		return new Location(location.getWorld(), location.getBlockX() + X, location.getBlockY() + Y, location.getBlockZ() + Z);
	}
	
	
}
