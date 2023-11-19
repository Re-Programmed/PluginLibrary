package com.willm.ModAPI.Commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.willm.ModAPI.Main;
import com.willm.ModAPI.Entities.CustomEntity;
import com.willm.ModAPI.Entities.CustomVillager;

public class SummonCustom implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(label.equalsIgnoreCase("summoncustom") || label.equalsIgnoreCase("modapi:summoncustom"))
		{
			if(args.length > 0)
			{
				if(args[0].equalsIgnoreCase("villager") || args[0].equalsIgnoreCase("modapi:villager"))
				{
					if(args.length < 2)
					{
						sender.sendMessage(ChatColor.RED + "Expected a profession.");
						return false;
					}
					
					for(CustomEntity entity : Main.CustomEntityRegistry)
					{
						if(entity instanceof CustomVillager)
						{
							CustomVillager villager = (CustomVillager)entity;
							if(villager.GetName().replace("Villager ", "").replace(" Villager", "").replace("Villager ", "").toLowerCase().equalsIgnoreCase(args[1].replace("profession:", "").toLowerCase()))
							{
								if(args.length == 5)
								{
									Location location = null;
									if(sender instanceof Player)
									{
										location = new Location(((Player) sender).getWorld(), Double.parseDouble(args[2]), Double.parseDouble(args[3]), Double.parseDouble(args[4]));
									}else {
										location = new Location(Bukkit.getWorlds().get(0), Double.parseDouble(args[2]), Double.parseDouble(args[3]), Double.parseDouble(args[4]));
									}
									
									villager.Summon(location);
									sender.sendMessage("Summoned new Villager{Profession:" + villager.GetName().toLowerCase().replace(" villager", "") + "} at " + location.getX() + " " + location.getY() + " " + location.getZ());
									return true;
								}else if(sender instanceof Player)
								{
									villager.Summon(((Player)sender).getLocation());
									sender.sendMessage("Summoned new Villager{Profession:" + villager.GetName().toLowerCase().replace(" villager", "") + "}");
									return true;
								}
							}
						}
					}
				}
				
				for(CustomEntity entity : Main.CustomEntityRegistry)
				{
					if(entity.GetName().replace(" ", "_").equalsIgnoreCase(args[0].replace(Main.PluginName + ":", "").toLowerCase()))
					{
						if(args.length == 4)
						{
							Location location = null;
							if(sender instanceof Player)
							{
								location = new Location(((Player) sender).getWorld(), Double.parseDouble(args[1]), Double.parseDouble(args[2]), Double.parseDouble(args[3]));
							}else {
								location = new Location(Bukkit.getWorlds().get(0), Double.parseDouble(args[1]), Double.parseDouble(args[2]), Double.parseDouble(args[3]));
							}
							
							entity.Summon(location);
							sender.sendMessage("Summoned new " + entity.GetName() + " at " + location.getX() + " " + location.getY() + " " + location.getZ());
							return true;
						}else if(sender instanceof Player)
						{
							entity.Summon(((Player)sender).getLocation());
							sender.sendMessage("Summoned new " + entity.GetName());
							return true;
						}
						
						sender.sendMessage(ChatColor.RED + "Please specify a location for the entity to spawn at.");
						
					}
				}
			}else {
				sender.sendMessage(ChatColor.RED + "Please specify an entity to spawn.");
			}
		}
		
		return false;
	}

}
