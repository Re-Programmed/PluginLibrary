package com.willm.CoreMOD;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import com.willm.CoreMOD.AssortedTools.AssortedToolEvent;
import com.willm.CoreMOD.AssortedTools.CreateAssortedToolCommand;
import com.willm.CoreMOD.AssortedTools.CreateAssortedToolCommandTabCompleter;
import com.willm.CoreMOD.ElementalItems.RegisterElementalItems;
import com.willm.ModAPI.Commands.GiveCommand;
import com.willm.ModAPI.Commands.GiveTabCompleter;

public class Main extends JavaPlugin {

	public static Main INSTANCE;
		
	@Override
	public void onEnable()
	{		
		INSTANCE = this;

		com.willm.ModAPI.Main.Launch("core_smp", this);
		
		com.willm.ModAPI.Voltage.Main.UseVoltage();

		MyItems.RegisterMyItems();
		MyEnchants.RegisterEnchants();
	
		com.willm.ModAPI.WorldCraft.Main.LaunchWorldCraft();
		
		MyBiomes.InitBiomes();
		
		getServer().getPluginManager().registerEvents(new ItemEvents(), this);
		getServer().getPluginManager().registerEvents(new BlockEvents(), this);
		getServer().getPluginManager().registerEvents(new EnchantEvents(), this);
		
		getServer().getPluginManager().registerEvents(new MilkFeatures(), this);
		
		getServer().getPluginManager().registerEvents(new AssortedToolEvent(), this);
		
		getCommand("assortedtool").setExecutor(new CreateAssortedToolCommand());
		getCommand("assortedtool").setTabCompleter(new CreateAssortedToolCommandTabCompleter());
		
		RegisterElementalItems.Register();

		Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
			
			public void run() { 
				try
				{
					if(BlockEvents.elevator_cooldown > 0f) {BlockEvents.elevator_cooldown --;}
				}catch(Exception e)
				{
					e.printStackTrace();
				}
			}
		}, 1, 1);
		
	}

	
	@Override
	public void onDisable()
	{
		com.willm.ModAPI.Main.Disable();
	}
}
