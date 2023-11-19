package com.willm.ModAPI;

import org.bukkit.Bukkit;
import org.bukkit.Location;

public class Utils {

	public static Location AddToLocationAsNew(Location loc, float x, float y, float z)
	{
		return new Location(loc.getWorld(), loc.getX() + x, loc.getY() + y, loc.getZ() + z);
	}
	
	public static String LocationToString(Location loc)
	{
		return loc.getX() + " " + loc.getY() + " " + loc.getZ();
 	}
	
	public static void PlayCustomSound(String sound, Location location)
	{
		Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "playsound minecraft:" + sound + " master @a " + LocationToString(location) + " 1 1");
	}
	
	public static void StopCustomSound(String sound)
	{
		Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "stopsound @a * minecraft:" + sound);
	}
}
