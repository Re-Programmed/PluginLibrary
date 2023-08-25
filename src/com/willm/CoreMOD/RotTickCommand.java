package com.willm.CoreMOD;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class RotTickCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
		if(arg2.equalsIgnoreCase("rottick"))
		{
			if(arg3.length > 0)
			{
				int i = Integer.parseInt(arg3[0]);
				Main.RotTick(i);
			}else {
				Main.RotTick();
			}
			arg0.sendMessage("Ticked Rotting Items");
			return true;
		}
		
		return false;
	}

}
