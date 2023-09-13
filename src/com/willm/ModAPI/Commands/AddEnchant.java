package com.willm.ModAPI.Commands;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.willm.ModAPI.Main;
import com.willm.ModAPI.Enchantments.CustomEnchantment;
import com.willm.ModAPI.Enchantments.EnchantmentCreator;

public class AddEnchant implements CommandExecutor{
	@Override
	public boolean onCommand(CommandSender sender, Command command, String title, String[] args) {
		if(!(sender instanceof Player)) {return false;}
		if(((Player)sender).getGameMode() != GameMode.CREATIVE){return false;}
		if(title.equalsIgnoreCase("enchantcustom"))
		{
			if(args[0] != null)
			{
				for(CustomEnchantment e : EnchantmentCreator.EnchantmentRegistry)
				{
					if(e.getName() == "") {continue;}
					if(e.getName().replace(" ", "_").equalsIgnoreCase(args[0].replace(Main.PluginName + ":", "").toLowerCase()))
					{
						Player p = ((Player)sender);
						
						ItemStack i = p.getEquipment().getItemInMainHand();
						if(i != null && i.getType() != Material.AIR)
						{
							if(!e.ApplyEnchant(i))
							{
								sender.sendMessage(ChatColor.RED + "Enchantment is not compatible.");
								return false;
							}
							sender.sendMessage(ChatColor.WHITE + "Added enchantment [" + e.getName() + "] to " + sender.getName());
							return true;				
						}else {
							sender.sendMessage(ChatColor.RED + "No item to add the enchantment to.");
							return false;
						}
						
					}
				}
			}
		}
		
		sender.sendMessage(ChatColor.RED + "Enchantment [" + args[0].replace(Main.PluginName + ":", "") + "] does not exist.");
		return false;
	}
}
