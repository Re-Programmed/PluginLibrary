package com.willm.CoreMOD.DifficultyExtension;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class SetDifficultyCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(label.equalsIgnoreCase("setdifficulty"))
		{
			if(!sender.isOp())
			{
				sender.sendMessage(ChatColor.GREEN + "The difficulty is: " + DifficultyEvents.CURRENT_ADDED_DIFFICULTY);
				return false;
			}
			
			if(args.length > 0)
			{
				DifficultyEvents.CURRENT_ADDED_DIFFICULTY = Integer.parseInt(args[0]);
				sender.sendMessage(ChatColor.WHITE + "Set the difficulty to: " + DifficultyEvents.CURRENT_ADDED_DIFFICULTY);
				return true;
			}
		}
		
		return false;
	}

	
	
}
