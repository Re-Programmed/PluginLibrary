package com.willm.ModAPI.Commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.willm.ModAPI.Main;
import com.willm.ModAPI.Items.CustomItemStack;

public class CreativeMenu implements CommandExecutor {
	
	public static ArrayList<CreativeMenuInventory> myInventories = new ArrayList<CreativeMenuInventory>();

	@Override
	public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
		if(arg2.equalsIgnoreCase("itemmenu") || arg2.equalsIgnoreCase("modapi:itemmenu") || arg2.equalsIgnoreCase("im") || arg2.equalsIgnoreCase("creativemenu"))
		{			
			if(arg0 instanceof Player)
			{
				if(arg3 != null && arg3.length > 0)			
				{
					String search = "";
					for(String s : arg3)
					{
						search += s + " ";
					}
					OpenSearch(search, (Player)arg0);
				}else {
					myInventories.add(LoadInventory((Player)arg0, 0));
				}
				return true;
			}
		}
		
		return false;
	}
	
	final int pageSize = 36;
	
	public CreativeMenuInventory LoadInventory(Player player, int page)
	{
		CreativeMenuInventory myInventory = new CreativeMenuInventory(player, null, page);

		player.openInventory(myInventory.getInventory());
		
		int i = 1;
		int offset = (pageSize * page);
		for(CustomItemStack ci : Main.CustomItemRegistry)
		{
			if(i == pageSize + 1) {break;}
			if(offset >= Main.CustomItemRegistry.size())
			{
				if(i == 1)
				{
					return LoadInventory(player, 0);
				}
			}else {
				ItemStack is = Main.CustomItemRegistry.get(offset).GetMyItemStack().clone();
				
				if(!player.isOp())
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
				
				myInventory.getInventory().addItem(is);
			}
			i++;
			offset++;
		}
		
		ItemStack nextArrow = new CustomItemStack("Next", Material.ARROW, 1).AddEnchant(Enchantment.LUCK, 1, true).AddFlags(ItemFlag.HIDE_ENCHANTS).GetMyItemStack(), backArrow = new CustomItemStack("Back", Material.ARROW, 1).AddEnchant(Enchantment.LUCK, 1, true).AddFlags(ItemFlag.HIDE_ENCHANTS).GetMyItemStack();
		
		ItemStack search = new CustomItemStack(ChatColor.RED + "" + ChatColor.BOLD + "Search", Material.OAK_SIGN, 1).AddEnchant(Enchantment.LUCK, 1, true).AddFlags(ItemFlag.HIDE_ENCHANTS).GetMyItemStack();
		
		myInventory.getInventory().setItem(53, nextArrow);
		myInventory.getInventory().setItem(45, backArrow);
		myInventory.getInventory().setItem(49, search);

		myInventory.Content = myInventory.getInventory().getContents().clone();
		
		return myInventory;
		
	}
	
	public void Tick()
	{
		
		ArrayList<CreativeMenuInventory> modInv = new ArrayList<CreativeMenuInventory>();
		for(CreativeMenuInventory myInventory : myInventories)
		{
			if(myInventory != null)
			{
				
				if(myInventory.Content != null)
				{
					if(!myInventory.IsSearch)
					{
						if(myInventory.getInventory().getItem(53) == null)
						{
							myInventory.Page++;
							myInventory.Player.setItemOnCursor(null);
							modInv.add(myInventory);
							continue;
						}
						
						if(myInventory.getInventory().getItem(45) == null)
						{
							if(myInventory.Page != 0) {myInventory.Page--;}
							myInventory.Player.setItemOnCursor(null);
							modInv.add(myInventory);
							continue;
						}
						
						if(myInventory.getInventory().getItem(49) == null)
						{
							myInventory.Player.setItemOnCursor(null);
							if(myInventory.Player.getLocation().getBlock().getType() == Material.AIR || myInventory.Player.getLocation().getBlock().getType() == Material.GRASS || myInventory.Player.getLocation().getBlock().getType() == Material.TALL_GRASS)
							{
								myInventory.Player.getLocation().getBlock().setType(Material.OAK_SIGN);
								myInventory.MySign = (Sign)myInventory.Player.getLocation().getBlock().getState();
								myInventory.Player.openSign(myInventory.MySign);
							}
						}
					}
					
					myInventory.getInventory().setContents(myInventory.Content);
				}
			}
		}
		
		for(CreativeMenuInventory myInventory : modInv)
		{
			myInventories.add(LoadInventory(myInventory.Player, myInventory.Page));
			myInventories.remove(myInventory);
		}
	}

	//Opens a creative menu with all items matching a string.
	public static void OpenSearch(String query, Player p)
	{
		CreativeMenuInventory myInventory = new CreativeMenuInventory(p, null, 0, true);
		for(CustomItemStack ci : Main.CustomItemRegistry)
		{
			if(ci.getName().toLowerCase().replace(" ", "").contains(query.toLowerCase().replace(" ", "")))
			{
				ItemStack is = ci.GetMyItemStack().clone();
				if(!p.isOp())
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
				
				myInventory.getInventory().addItem(is);
			}
		}
		
		myInventory.Content = myInventory.getInventory().getContents();
		p.openInventory(myInventory.getInventory());
		
		myInventories.add(myInventory);
	}

}
