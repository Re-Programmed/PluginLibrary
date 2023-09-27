package com.willm.ModAPI.WorldCraft;

import com.willm.ModAPI.Terrain.CustomPopulator;

public class Main {

	public static boolean Enabled = false;
	
	public static void LaunchWorldCraft()
	{
		Enabled = true;
		
		CustomPopulator.EnableWC(new PopulationManager());
	}
	
}
