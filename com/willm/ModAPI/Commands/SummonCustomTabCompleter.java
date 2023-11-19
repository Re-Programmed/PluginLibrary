package com.willm.ModAPI.Commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.EntityType;

import com.willm.ModAPI.Main;
import com.willm.ModAPI.Entities.CustomEntity;
import com.willm.ModAPI.Entities.CustomVillager;

public class SummonCustomTabCompleter implements TabCompleter {

	@Override
	public List<String> onTabComplete(CommandSender arg0, Command arg1, String arg2, String[] args) {
		
		if(!arg2.equalsIgnoreCase("summoncustom") && !arg2.equalsIgnoreCase("modapi:summoncustom")) {return null;}
		
		ArrayList<String> tList = new ArrayList<String>();
		
		if(args.length == 1)
		{
			tList.add("modapi:villager");
			for(CustomEntity entity : Main.CustomEntityRegistry)
			{
				if(entity.GetType() != EntityType.VILLAGER)
				{
					tList.add(Main.PluginName + ":" + entity.GetName().toLowerCase().replace(" ", "_"));
				}
			}
		}else if(args.length < 6)
		{
			if(args[0].equalsIgnoreCase("villager") || args[0].equalsIgnoreCase("modapi:villager") && args.length == 2)
			{
				for(CustomEntity entity : Main.CustomEntityRegistry)
				{
					if(entity instanceof CustomVillager)
					{
						CustomVillager villager = (CustomVillager)entity;
						tList.add("profession:" + villager.GetName().toLowerCase().replace(" villager", "").replace("villager ", "").replace("villager", "").replace(" ", "_"));
					}
				}
			}else if(args.length < 5)
			{
				tList.add("0");
				tList.add("1");
				tList.add("5");
				tList.add("10");
				tList.add("100");
			}
		}
		
		return tList;
	}

}
