package com.willm.CoreMOD.CustomCommands;

import java.util.HashMap;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.willm.ModAPI.Items.CustomItemStack;

public class ViewInventoryCommand implements CommandExecutor {

	public static HashMap<Player, Inventory> openInventories = new HashMap<Player, Inventory>();
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		if(label.equalsIgnoreCase("viewinventory"))
		{
			if(args.length > 0)
			{
				if(sender.isOp())
				{
					if(sender instanceof Player)
					{
						Player psend = (Player)sender;
						for(Player p : Bukkit.getOnlinePlayers())
						{
							if(p.getName().equalsIgnoreCase(args[0]))
							{
								Inventory DispInv = Bukkit.createInventory(p, 5 * 9, p.getName() + "'s Inventory");
								int i = 0;
								for(ItemStack is : p.getInventory())
								{
									DispInv.setItem(i++, is == null ? null : is.clone());
								}

								psend.openInventory(DispInv);
								
								openInventories.put(p, DispInv);
							}
						}
					}

				}
			}
		}
		
		return false;
	}
	
	public static void Tick()
	{
		for(Entry<Player, Inventory> e : openInventories.entrySet())
		{
			if(e.getValue().getViewers().size() == 0) {openInventories.remove(e.getKey());return;}
			
			int i = 0;
			for(ItemStack is : e.getKey().getInventory())
			{
				e.getValue().setItem(i, is);
				i++;
			}
			
			
			e.getValue().setItem(e.getValue().getSize() - 1, new CustomItemStack("Viewing Inventory: " + e.getKey().getName(), Material.CHEST, 1502).GetMyItemStack());
		}
	}
	
}
