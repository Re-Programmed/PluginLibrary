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

public class FillCustomBlockCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String title, String[] args) {

		if(title.equalsIgnoreCase("fillcustomblock"))
		{
			if(!sender.isOp()) {
				sender.sendMessage(ChatColor.RED + "You do not have permission to use this command!");
				return false;
			}
			
			if(sender instanceof Player)
			{
				Player p = (Player)sender;
				
				if(args.length > 0)
				{
					if(args[0].equalsIgnoreCase("minecraft:air"))
					{
						
						Location loc, loc2;
						if(args.length < 7)
						{
							p.sendMessage(ChatColor.RED + "Missing coordinates to fill blocks.");
							return false;
						}
						loc = decodeLocation(p, args[1], args[2], args[3]);
						loc2 = decodeLocation(p, args[4], args[5], args[6]);
						
						if(loc.getBlockX() > loc2.getBlockX())
						{
							int save = loc.getBlockX();
							loc.setX(loc2.getX());
							loc2.setX(save);
						}
						
						if(loc.getBlockY() > loc2.getBlockY())
						{
							int save = loc.getBlockY();
							loc.setY(loc2.getY());
							loc2.setY(save);
						}
						
						if(loc.getBlockZ() > loc2.getBlockZ())
						{
							int save = loc.getBlockZ();
							loc.setZ(loc2.getZ());
							loc2.setZ(save);
						}
						
						int i = 0;
						for(int x = loc.getBlockX(); x < loc2.getBlockX() + 1; x++)
						{
							for(int y = loc.getBlockY(); y < loc2.getBlockY() + 1; y++)
							{
								for(int z = loc.getBlockZ(); z < loc2.getBlockZ() + 1; z++)
								{
									Location myLoc = new Location(loc.getWorld(), x, y, z);
									
									for(CustomBlock cb2 : Main.CustomBlockRegistry)
									{
										if(cb2.CheckForCustomBlock(myLoc.getBlock()))
										{
											cb2.Remove(myLoc);
											i++;
										}
									}
									
								}
							}
						}
						
						sender.sendMessage("Filled " + i + " blocks");
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
						Location loc, loc2;
						if(args.length < 7)
						{
							p.sendMessage(ChatColor.RED + "Missing coordinates to fill blocks.");
							return false;
						}
						loc = decodeLocation(p, args[1], args[2], args[3]);
						loc2 = decodeLocation(p, args[4], args[5], args[6]);
						
						if(loc.getBlockX() > loc2.getBlockX())
						{
							int save = loc.getBlockX();
							loc.setX(loc2.getX());
							loc2.setX(save);
						}
						
						if(loc.getBlockY() > loc2.getBlockY())
						{
							int save = loc.getBlockY();
							loc.setY(loc2.getY());
							loc2.setY(save);
						}
						
						if(loc.getBlockZ() > loc2.getBlockZ())
						{
							int save = loc.getBlockZ();
							loc.setZ(loc2.getZ());
							loc2.setZ(save);
						}

						int i = 0;
						for(int x = loc.getBlockX(); x < loc2.getBlockX() + 1; x++)
						{
							for(int y = loc.getBlockY(); y < loc2.getBlockY() + 1; y++)
							{
								for(int z = loc.getBlockZ(); z < loc2.getBlockZ() + 1; z++)
								{
									Location myLoc = new Location(loc.getWorld(), x, y, z);
									for(CustomBlock cb2 : Main.CustomBlockRegistry)
									{
										if(cb2.CheckForCustomBlock(myLoc.getBlock()))
										{
											cb2.Remove(myLoc);
										}
									}
									
									if(args.length > 7)
									{
										BlockFace face = BlockFace.valueOf(args[7]);

										if(cb.Directional == BlockDirectionData.FACE_RELATIVE)
										{
											if(face == BlockFace.EAST || face == BlockFace.WEST || face == BlockFace.NORTH || face == BlockFace.SOUTH)
											{	
												cb.sidewaysBlockData.Place(myLoc, face);
											}
										}
										
										cb.Place(myLoc, BlockFace.valueOf(args[7]));
									}else {
										cb.Place(myLoc);
									}
									
									i++;
								}
							}
						}
						
						
						sender.sendMessage("Filled " + i + " blocks");
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
