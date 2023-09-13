package com.willm.CoreMOD.AssortedTools;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;

import com.willm.ModAPI.Items.CustomItemStack;

public class CustomTool extends CustomItemStack {

	public static final int CMD_DATA = 16232;
	
	List<ToolAbilities> abilities = new ArrayList<ToolAbilities>();
	
	public CustomTool(String name, Material mat, int cmd, ToolAbilities... abilities) {
		super(name, mat, cmd);
		
		this.AddLoreLine(org.bukkit.ChatColor.GRAY + "Alloy:");
		for(ToolAbilities ta : abilities)
		{
			this.abilities.add(ta);
			this.AddLoreLine(ta.Color + ta.toString());
		}
	}

	public static CustomTool CreateCustomTool(ToolType type, ToolAbilities... abilities)
	{
		return new CustomTool("Alloyed " + type.toString(), type.Material, CMD_DATA, abilities);
	}
	
}
