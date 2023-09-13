package com.willm.ModAPI.RecipeDisplay;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import com.willm.ModAPI.Main;
import com.willm.ModAPI.Items.CustomItemStack;

public class RecipeDisplayTabCompleter implements TabCompleter {
	
	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
		List<String> tComplete = new ArrayList<String>();
		
		if(args.length == 1)
		{
			
			if(!args[0].startsWith("item:"))
			{
				for(String s : RecipeUtils.GetRecipeNames())
				{
					if(s.startsWith(args[0]))
					{
						tComplete.add(s);
					}
				}
				
				for(CustomRecipeType crt : RecipeDisplay.CUSTOM_RECIPES)
				{
					if(crt.GetKey().startsWith(args[0]))
					{
						tComplete.add(crt.GetKey());
					}
				}
			}
			
			for(CustomItemStack cis : Main.CustomItemRegistry)
			{
				if(("item:" + cis.getName()).replace(ChatColor.WHITE + "", "").toLowerCase().replace(' ', '_').startsWith(args[0]))
				{
					tComplete.add("item:" + cis.getName().replace(ChatColor.WHITE + "", "").toLowerCase().replace(' ', '_'));
				}
			}
		}
		
		return tComplete;
	}

}
