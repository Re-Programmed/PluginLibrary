package com.willm.ModAPI.Commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class ClearBrokenArmorstand implements CommandExecutor {


	@Override
	public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
		if(arg2.equalsIgnoreCase("removebrokenstand") || arg2.equalsIgnoreCase("modapi:removebrokenstand"))
		{			
			if(arg0 instanceof Player)
			{
				Player player = (Player)arg0;
				
				Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "execute at " + player.getName() + " run kill @e[type=armor_stand, distance=0..2, limit=1]");
			}
		}
		
		return false;
	}	

}
