package com.willm.CoreMOD.Alloying;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

public class CreateAlloyPickTabCompleter implements TabCompleter {

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
		
		if(!label.equalsIgnoreCase("alloypick")) {
			return null;
		}
		
		List<String> reccommends = new ArrayList<String>();
		
		if(args.length < 3)
		{
			for(AlloyMaterial am : AlloyMaterial.values())
			{
				reccommends.add(am.toString());
			}
		}
		
		return reccommends;
	}

}
