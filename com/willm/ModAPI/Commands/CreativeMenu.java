package com.willm.ModAPI.Commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.willm.ModAPI.Main;
import com.willm.ModAPI.Items.CustomItemStack;

public class CreativeMenu implements InventoryHolder, CommandExecutor {

	public static boolean Allowed = false;
	
	public static Inventory myInventory;
	ItemStack[] content;
	
	Player lastPlayer;
	
	int page = 0;
	
	@Override
	public Inventory getInventory() {
		return myInventory;
	}

	@Override
	public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
		if(arg2.equalsIgnoreCase("itemmenu") || arg2.equalsIgnoreCase("modapi:itemmenu") || arg2.equalsIgnoreCase("im") || arg2.equalsIgnoreCase("creativemenu"))
		{			
			if(arg0 instanceof Player)
			{
				lastPlayer = (Player)arg0;
				
				Allowed = lastPlayer.getGameMode() == GameMode.CREATIVE;
				
				LoadInventory(lastPlayer);
				
				return true;
			}
		}
		
		return false;
	}
	
	final int pageSize = 36;
	
	public void LoadInventory(Player player)
	{
		myInventory = Bukkit.createInventory(this, 54, "Creative Menu");
		player.openInventory(getInventory());
		
		int i = 1;
		int offset = (pageSize * page);
		for(CustomItemStack ci : Main.CustomItemRegistry)
		{
			if(i == pageSize + 1) {break;}
			if(offset >= Main.CustomItemRegistry.size())
			{
				if(i == 1)
				{
					page = 0;
					LoadInventory(player);
					return;
				}
			}else {
				ItemStack is = Main.CustomItemRegistry.get(offset).GetMyItemStack().clone();
				
				if(!Allowed)
				{
					ItemMeta m = is.getItemMeta();
					List<String> lore = new ArrayList<String>();
					if(m.hasLore())
					{
						lore = m.getLore();
					}
					
					lore.add(ChatColor.DARK_GRAY + "RMB/LMB - View recipes");
					m.setLore(lore);
					is.setItemMeta(m);
				}
				
				myInventory.addItem(is);
			}
			i++;
			offset++;
		}
		
		ItemStack nextArrow = new CustomItemStack("Next", Material.ARROW, 1).GetMyItemStack();
		
		myInventory.setItem(53, nextArrow);
		
		content = myInventory.getContents().clone();
		
	}
	
	public void Tick()
	{
		if(myInventory != null)
		{
			if(content != null)
			{
				if(myInventory.getItem(53) == null)
				{
					page++;
					lastPlayer.setItemOnCursor(null);
					LoadInventory(lastPlayer);
					return;
				}
				
				myInventory.setContents(content);
			}
		}
	}

}
