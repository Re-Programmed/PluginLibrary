package com.willm.CoreMOD.CustomCommands;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CoordCommand implements CommandExecutor {

	ArrayList<String> hiddenPlayers = new ArrayList<String>();
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		if(label.equalsIgnoreCase("coordinates"))
		{
			if(args.length > 0)
			{
				if(args[0].equalsIgnoreCase("hide"))
				{
					if(sender instanceof Player)
					{
						Player p2 = (Player)sender;
						if(hiddenPlayers.contains(p2.getName().toLowerCase()))
						{
							hiddenPlayers.remove(p2.getName().toLowerCase());
							p2.sendMessage(ChatColor.GREEN + "Set your coordinates to be available.");
						}else {
							hiddenPlayers.add(p2.getName().toLowerCase());
							p2.sendMessage(ChatColor.GREEN + "Set your coordinates to be hidden.");
						}
						
						return true;
					}
				}
				
				if(sender instanceof Player)
				{
					Player p2 = (Player)sender;
					if(p2.getName().equalsIgnoreCase(args[0]))
					{
						for(Player p : Bukkit.getOnlinePlayers())
						{
							if(p == p2) {continue;}
							p.playSound(p.getLocation(), Sound.ENTITY_ARROW_HIT_PLAYER, 1, 1);
							p.sendMessage(ChatColor.GREEN + p2.getName() + "'s Coordinates: " + p2.getLocation().getBlockX() + " " + p.getLocation().getBlockY() + " " + p.getLocation().getBlockZ());
						}
						
						p2.sendMessage(ChatColor.GREEN + "Broadcast Coordinates");
						return true;
					}
				}
				
				for(Player p : Bukkit.getOnlinePlayers())
				{
					if(p.getName().equalsIgnoreCase(args[0]))
					{
						if(hiddenPlayers.contains(args[0].toLowerCase()))
						{
							sender.sendMessage(ChatColor.RED + "That player's coordinates are hidden.");
							return true;
						}
						
						sender.sendMessage(ChatColor.GREEN + args[0] + "'s Coordinates: " + p.getLocation().getBlockX() + " " + p.getLocation().getBlockY() + " " + p.getLocation().getBlockZ());
						p.sendMessage(ChatColor.GRAY + sender.getName() + " is viewing your coordinates.");
						return true;
					}
				}
			}
		}
		
		return false;
	}

	
	
}
