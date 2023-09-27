package com.willm.ModAPI.Voltage.Blocks;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import com.willm.ModAPI.Items.BlockCreator;
import com.willm.ModAPI.Items.CustomItemStack;
import com.willm.ModAPI.Items.ItemCreator;

public class CreativeEnergyAcceptor extends EnergyCompatible {
	
	public CreativeEnergyAcceptor()
	{
		CustomItemStack cis = ItemCreator.RegisterNewItem(new CustomItemStack("Creative Energy Acceptor", Material.POLISHED_ANDESITE, 21003));
		blockRef = BlockCreator.RegisterNewBlock(cis);
	}

	@Override
	public void Tick(Location loc) {
		if(!sources.containsKey(loc)) {sources.put(loc, 0);}
		if(sources.get(loc) == null) {sources.put(loc, 0);}
		
		for(Player p : Bukkit.getOnlinePlayers())
		{
			if(sources.get(loc) < 1)
			{
				p.sendMessage(ChatColor.RED + "" + sources.get(loc) + " [CAA]");
			}else {
				if(sources.get(loc) > 10000)
				{
					p.sendMessage(ChatColor.GREEN + "" + sources.get(loc) + " [CAA]");
				}else {
					p.sendMessage(ChatColor.GRAY + "" + sources.get(loc) + " [CAA]");
				}
			}
		}
		
	}
	
	
}
