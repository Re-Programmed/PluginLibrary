package com.willm.ModAPI.Players;

public class Player {

	final org.bukkit.entity.Player relPlayer;
	
	public Player(org.bukkit.entity.Player player)
	{
		relPlayer = player;
	}
	
	public PlayerInventory GetInventory()
	{
		return new PlayerInventory(relPlayer.getInventory());
	}
}
