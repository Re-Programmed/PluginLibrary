package com.willm.CoreMOD.CustomCommands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class LukeModeCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		if(label.equalsIgnoreCase("lukemode"))
		{
			if(sender instanceof Player)
			{
				Player p = (Player)sender;
				if(p.getName().equalsIgnoreCase("lxh1"))
				{
					p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 1000000, 100));
					p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 1000000, 100));
					p.addPotionEffect(new PotionEffect(PotionEffectType.DOLPHINS_GRACE, 1000000, 100));
					p.setMaxHealth(60);
					p.setHealth(60);
					return true;
				}else {
					p.sendMessage(ChatColor.RED + "You are not Luke Hollister.");
				}
			}
		}
		
		return false;
	}

}
