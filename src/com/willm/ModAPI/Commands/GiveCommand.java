package com.willm.ModAPI.Commands;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.willm.ModAPI.Main;
import com.willm.ModAPI.Items.CustomItemStack;

public class GiveCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String title, String[] args) {
		if(!(sender instanceof Player)) {return false;}
		if(title.equalsIgnoreCase("givecustom") || title.equalsIgnoreCase("gc"))
		{
			if(sender instanceof Player)
			{
				if(((Player)sender).isOp())
				{
					if(args[0] != null)
					{
						for(CustomItemStack item : Main.CustomItemRegistry)
						{
							if(item.getName() == "") {continue;}
							if(item.getName().replace(" ", "_").equalsIgnoreCase(args[0].replace(Main.PluginName + ":", "").toLowerCase()))
							{
								Player p = ((Player)sender);
								
								ItemStack drp;
								
								if(args.length > 1)
								{
									drp = item.GetAmountClone(Integer.parseInt(args[1]));
								}else {
									drp = item.GetMyItemStack();
								}
								
								Item i = p.getWorld().dropItemNaturally(p.getLocation(), drp);
								i.setPickupDelay(0);
								i.setThrower(p.getUniqueId());
								sender.sendMessage(ChatColor.WHITE + "Gave " + drp.getAmount() + " [" + item.getName() + "] to " + sender.getName());
								return true;
							}
						}
					}
				}else {
					sender.sendMessage(ChatColor.RED + "You do not have permission to use this command!");
					return false;
				}
			}
		}
		
		sender.sendMessage(ChatColor.RED + "Item [" + args[0].replace(Main.PluginName + ":", "") + "] does not exist.");
		return false;
	}


}
