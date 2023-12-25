package com.willm.CoreMOD.CustomCommands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

public class CMapCompleter implements TabCompleter {

	@Override
	public List<String> onTabComplete(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
		List<String> ret = new ArrayList<String>();
		 
		if(arg2.equalsIgnoreCase("cmap"))
		{
			if(arg3.length > 0)
			{
				if(arg3[0].equalsIgnoreCase(""))
				{
					ret.add("0");
					ret.add("1");
					ret.add("2");
					ret.add("3");
					ret.add("4");
					ret.add("5");
				}
				
				for(String s : CMapCommand.mapIndexes)
				{
					if(s.toLowerCase().contains(arg3[0].toLowerCase()) || arg3[0].equals(""))
					{
						ret.add(s);
					}
				}
				
				
			}
		}
		
		return ret;
	}

}
