package com.willm.ModAPI.Commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.block.BlockFace;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import com.willm.ModAPI.Main;
import com.willm.ModAPI.Blocks.CustomBlock;

public class FillCustomBlockTabCompleter implements TabCompleter {

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
    	
		ArrayList<String> str = new ArrayList<String>();

    	if(args.length == 1)
    	{
    		str.add("minecraft:air");
        	for(CustomBlock cb : Main.CustomBlockRegistry)
        	{
        		str.add(cb.getTag());
        	}
        
    	} else if(args.length < 8 && args.length > 1)
    	{
    		str.add("~");
    		if(sender instanceof Player)
    		{
    			Player p = (Player)sender;
    			switch(args.length)
    			{
    			case 2:
    				str.add(Integer.toString(p.getTargetBlockExact(8).getLocation().getBlockX()));
    				break;
    			case 3:
    				str.add(Integer.toString(p.getTargetBlockExact(8).getLocation().getBlockY()));
    				break;
    			case 4:
    				str.add(Integer.toString(p.getTargetBlockExact(8).getLocation().getBlockZ()));
    				break;
    			case 5:
    				str.add(Integer.toString(p.getTargetBlockExact(8).getLocation().getBlockX()));
    				break;
    			case 6:
    				str.add(Integer.toString(p.getTargetBlockExact(8).getLocation().getBlockY()));
    				break;
    			case 7:
    				str.add(Integer.toString(p.getTargetBlockExact(8).getLocation().getBlockZ()));
    				break;
    			}
    			
    		}
    	} else if (args.length == 8)
    	{
    		for(BlockFace bf : BlockFace.values())
    		{
    			str.add(bf.toString());
    		}
    	}
    	
    	return str;
    	
    }
}
