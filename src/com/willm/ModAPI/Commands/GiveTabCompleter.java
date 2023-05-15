package com.willm.ModAPI.Commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import com.willm.ModAPI.Main;
import com.willm.ModAPI.Items.CustomItemStack;

public class GiveTabCompleter implements TabCompleter {

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
    	
    	if(args.length == 1)
    	{
    		ArrayList<String> str = new ArrayList<String>();
        	
        	for(CustomItemStack i : Main.CustomItemRegistry)
        	{
        		if(i.getName() == "") {continue;}
        		String namewithval = Main.PluginName + ":" + i.getName().replace(' ', '_');
        		if(args[0].equalsIgnoreCase(""))
        		{
        			str.add(Main.PluginName + ":" + i.getName().toLowerCase().replace(" ", "_"));
        		}else {
        			if(namewithval.toLowerCase().startsWith(args[0].toLowerCase()) || namewithval.replace(Main.PluginName + ":", "").toLowerCase().startsWith(args[0].toLowerCase()))
        			{
        				str.add(Main.PluginName + ":" + i.getName().toLowerCase().replace(" ", "_"));
        			}
        		}
        	}
        
        	return str;
    	}
    	
    	return new ArrayList<String>();
    	
    }
}
