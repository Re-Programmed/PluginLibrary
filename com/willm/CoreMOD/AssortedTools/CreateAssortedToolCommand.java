package com.willm.CoreMOD.AssortedTools;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;

public class CreateAssortedToolCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String title, String[] args) {
		
		if(title.equalsIgnoreCase("assortedtool") || title.equalsIgnoreCase("modapi:assortedtool"))
		{
			if(sender instanceof Player)
			{
				Player p = (Player)sender;
				
				List<ToolAbilities> ta_list = new ArrayList<ToolAbilities>();
				
				boolean first = true;
				for(String ta : args)
				{
					if(!first)
					{
						ta_list.add(ToolAbilities.valueOf(ta));
					}
					
					first = false;
				}
				
				Item i = p.getWorld().dropItem(p.getLocation(), CustomTool.CreateCustomTool(ToolType.valueOf(args[0]), ta_list.toArray(new ToolAbilities[ta_list.size()])).GetMyItemStack());
				i.setPickupDelay(0);
				i.setOwner(p.getUniqueId());
				return true;
			}
		}
		
		return false;
	}

	
}
