package com.willm.ModAPI.Commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.willm.ModAPI.Main;

public class CompareVersionsCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		if(label.equalsIgnoreCase("pversion"))
		{
			sender.sendMessage(ChatColor.BLUE + "" + ChatColor.BOLD + "-------------------" + ChatColor.YELLOW + "VERSION" + ChatColor.BLUE + "-------------------");
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "tellraw " + sender.getName() + " [{\"text\":\"Client version: \"}, {\"translate\":\"core.current_version\",\"fallback\":\"Unknown\",\"color\":\"green\"}]");
			sender.sendMessage("Server version: " + ChatColor.RED + Main.Version);
			
			sender.sendMessage(ChatColor.RED + "If these versions do not match watch this video: ");
			sender.sendMessage(ChatColor.RED + "https://www.youtube.com/watch?v=XCQvcONWm-Y");
			
			sender.sendMessage(ChatColor.BLUE + "" + ChatColor.BOLD + "-----------------------------------------");
		}
		
		return false;
	}

	
	
}
