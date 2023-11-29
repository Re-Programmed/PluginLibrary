package com.willm.CoreMOD;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import com.willm.ModAPI.Items.CustomItemStack;

public class MessageEvents implements Listener {

	@EventHandler
	public void MessageSendEvent(AsyncPlayerChatEvent event)
	{				
		ArrayList<String> messageComponents = new ArrayList<String>();

		String message = event.getMessage();
		
		int lastSplitPoint = 0;
		
		//Highlight and ping players.
		for(Player p : Bukkit.getOnlinePlayers())
		{
			if(message.contains(p.getName()))
			{
				messageComponents.add("{\"text\":\"" + message.substring(lastSplitPoint, message.indexOf(p.getName())) + "\"}");
				messageComponents.add("{\"selector\":\"" + p.getName() + "\",\"color\":\"gold\"}");
				lastSplitPoint = message.indexOf(p.getName()) + p.getName().length();
				
				p.playSound(p.getLocation(), Sound.BLOCK_BELL_USE, 100, 1);
			}
		}		
		
		//Add items as clickables.
		for(CustomItemStack cis : com.willm.ModAPI.Main.CustomItemRegistry)
		{
			if(message.toLowerCase().contains(cis.getName().toLowerCase()))
			{
				int index = message.toLowerCase().indexOf(cis.getName().toLowerCase());
				messageComponents.add("{\"text\":\"" + message.substring(lastSplitPoint, index) + "\"}");

				String text1 = "{\"text\":\"" + message.substring(index, index + cis.getName().length()) + "\",\"color\":\"dark_aqua\",\"hoverEvent\":{\"action\":\"show_text\",\"value\":\"" + cis.getName();
				
				text1 += "\\n" + ChatColor.DARK_GRAY + "item:" + cis.getName().toLowerCase().replace(' ', '_');
				
				if(cis.GetMyItemStack().getItemMeta().hasLore())
				{
					for(String s : cis.GetMyItemStack().getItemMeta().getLore())
					{
						text1 += "\\n" + ChatColor.GRAY + s;
					}
				}
				
				text1 += "\\n" + ChatColor.DARK_GRAY + "Click to view recipes.";
				
				text1 += "\"}";
				
				text1 += ",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/recipes item:" + cis.getName().toLowerCase().replace(' ', '_') + "\"}}";
				
				messageComponents.add(text1);
				lastSplitPoint = index + cis.getName().length();
			}
		}
		
		messageComponents.add("{\"text\":\"" + message.substring(lastSplitPoint, message.length()) + "\"}");		
		
		String jsonString = "[{\"text\":\"<\"},{\"selector\":\"" + event.getPlayer().getName() + "\"},{\"text\":\"> \"}";
		
		for(String s : messageComponents)
		{
			jsonString += "," + s;
		}
		
		jsonString += "]";
				
		event.setCancelled(true);
				
		setDispatch("tellraw @a " + jsonString);
	}
	
	private static void setDispatch(final String command)
	{
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.INSTANCE, new Runnable() {
			
			@Override
			public void run() {
				Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command);
			}
		}, 0);
	}
	
}
