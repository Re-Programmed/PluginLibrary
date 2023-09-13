package com.willm.ModAPI.Commands;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.BlockFace;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;

import com.willm.ModAPI.Main;
import com.willm.ModAPI.Utils;
import com.willm.ModAPI.Blocks.BlockDirectionData;
import com.willm.ModAPI.Blocks.CustomBlock;
import com.willm.ModAPI.Items.CustomItemStack;

public class SetCustomBlockCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String title, String[] args) {

		if(title.equalsIgnoreCase("setcustomblock"))
		{
			if(sender instanceof Player)
			{
				Player p = (Player)sender;
				
				if(args.length > 0)
				{
					if(args[0].equalsIgnoreCase("minecraft:air"))
					{
						Location loc;
						if(args.length > 1)
						{
							if(args.length < 4)
							{
								p.sendMessage(ChatColor.RED + "Missing coordinates to set block.");
								return false;
							}
							loc = decodeLocation(p, args[1], args[2], args[3]);
						}else {
							loc = p.getLocation().getBlock().getLocation();
						}
						
						for(CustomBlock cb2 : Main.CustomBlockRegistry)
						{
							if(cb2.CheckForCustomBlock(loc.getBlock()))
							{
								cb2.Remove(loc);
							}
						}
						
						sender.sendMessage(ChatColor.WHITE + "Set the block at " + loc.getBlockX() + " " + loc.getBlockY() + " " + loc.getBlockZ());
						return true;
					}
				}else {
					sender.sendMessage(ChatColor.RED + "Missing block type");
					return false;
				}
				
				for(CustomBlock cb : Main.CustomBlockRegistry)
				{					
					if(cb.getTag() == "") {continue;}
					if(cb.getTag().replace(" ", "_").equalsIgnoreCase(args[0].toLowerCase()))
					{
						Location loc;
						if(args.length > 1)
						{
							if(args.length < 4)
							{
								p.sendMessage(ChatColor.RED + "Missing coordinates to set block.");
								return false;
							}
							loc = decodeLocation(p, args[1], args[2], args[3]);
						}else {
							loc = p.getLocation().getBlock().getLocation();
						}
						
						for(CustomBlock cb2 : Main.CustomBlockRegistry)
						{
							if(cb2.CheckForCustomBlock(loc.getBlock()))
							{
								cb2.Remove(loc);
							}
						}
						
						if(args.length > 4)
						{
							BlockFace face = BlockFace.valueOf(args[4]);

							if(cb.Directional == BlockDirectionData.FACE_RELATIVE)
							{
								if(face == BlockFace.EAST || face == BlockFace.WEST || face == BlockFace.NORTH || face == BlockFace.SOUTH)
								{
									
									
									cb.sidewaysBlockData.Place(loc, face);
									sender.sendMessage(ChatColor.WHITE + "Set the block at " + loc.getBlockX() + " " + loc.getBlockY() + " " + loc.getBlockZ() + " [Facing=" + args[4] + "]");
									return true; 
								}
							}
							
							cb.Place(loc, BlockFace.valueOf(args[4]));
							sender.sendMessage(ChatColor.WHITE + "Set the block at " + loc.getBlockX() + " " + loc.getBlockY() + " " + loc.getBlockZ() + " [Facing=" + args[4] + "]");
						}else {
							cb.Place(loc);
							sender.sendMessage(ChatColor.WHITE + "Set the block at " + loc.getBlockX() + " " + loc.getBlockY() + " " + loc.getBlockZ());
						}
								
						return true;
					}
				}
			}
		}
		
		sender.sendMessage(ChatColor.RED + "Block [" + args[0].replace(Main.PluginName + ":", "") + "] does not exist.");
		return false;
	}

	private Location decodeLocation(Player player, String x, String y, String z)
	{
		if(x == "~" || x == null)
		{
			x = Integer.toString(player.getLocation().getBlockX());
		}
		
		if(y == "~" || y == null)
		{
			y = Integer.toString(player.getLocation().getBlockY());
		}
		
		if(z == "~" || z == null)
		{
			z = Integer.toString(player.getLocation().getBlockZ());
		}
		
		return new Location(player.getWorld(), Integer.parseInt(x), Integer.parseInt(y), Integer.parseInt(z));
	}

}
