package com.willm.ModAPI.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.willm.ModAPI.Main;

public class RepopMachinesCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
		
		if(arg2.equalsIgnoreCase("repopulatemachines"))
		{
			Main.MachineRepopTick = 0;
			arg0.sendMessage("Repopulating machines...");
			return true;
		}
		
		return false;
	}

	
	
}
