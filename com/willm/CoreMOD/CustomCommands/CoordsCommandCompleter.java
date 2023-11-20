package com.willm.CoreMOD.CustomCommands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

public class CoordsCommandCompleter implements TabCompleter {

	@Override
	public List<String> onTabComplete(CommandSender arg0, Command command, String label, String[] args) {
		List<String> s = new ArrayList<String>();
		
		s.add("hide");
		
		if(label.equalsIgnoreCase("coordinates"))
		{
			if(args.length == 1)
			{
				for(Player p : Bukkit.getOnlinePlayers())
				{
					if(p.getName().startsWith(args[0]))
					{
						s.add(p.getName());
					}
				}
			}
		}
		
		return s;
	}

}
