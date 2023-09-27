package com.willm.CoreMOD.AssortedTools;

import org.bukkit.ChatColor;

public enum ToolAbilities {
	Wooden(ChatColor.DARK_RED),
	Stone(ChatColor.GRAY),
	Iron(ChatColor.WHITE),
	Gold(ChatColor.GOLD),
	Diamond(ChatColor.AQUA),
	Netherite(ChatColor.DARK_PURPLE);
	
	public final ChatColor Color;
	
	ToolAbilities(ChatColor color)
	{
		Color = color;
	}
}
