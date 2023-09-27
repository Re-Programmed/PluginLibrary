package com.willm.CoreMOD.AssortedTools;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

public class CreateAssortedToolCommandTabCompleter implements TabCompleter {

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String title, String[] args) {
		
		if(title.equalsIgnoreCase("assortedtool") || title.equalsIgnoreCase("modapi:assortedtool"))
		{
			if(args.length == 1)
			{
				List<String> ret = new ArrayList<String>();
				
				for(ToolType tt : ToolType.values())
				{
					if(tt.toString().startsWith(args[0]))
					{
						ret.add(tt.toString());
					}
				}
				
				return ret;
			}else {
				List<String> ret = new ArrayList<String>();
				
				for(ToolAbilities ta : ToolAbilities.values())
				{
					boolean cont = true;
					
					if(!ta.toString().startsWith(args[args.length - 1])) {continue;}
					
					for(String s : args)
					{
						if(s.equalsIgnoreCase(ta.toString().toLowerCase()))
						{
							cont = false;
						}
					}
					
					if(cont) { ret.add(ta.toString()); }
				}
				
				return ret;
			}
		}
		
		return null;
	}

}
