package com.willm.ModAPI;

import java.util.HashMap;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class CustomDeathMessages implements Listener {
	static java.util.HashMap<String, String> customDeathMessage = new HashMap<String, String>();
	
	public static void SetCustomDeathMessage(String playerName, String message)
	{
		customDeathMessage.put(playerName, message);	
	}
	
	@EventHandler
	public void Die(PlayerDeathEvent event)
	{
		if(customDeathMessage.containsKey(event.getEntity().getName()))
		{
			event.setDeathMessage(customDeathMessage.get(event.getEntity().getName()));
			customDeathMessage.clear();
		}
	}
	
}
