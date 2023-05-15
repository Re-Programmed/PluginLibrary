package com.willm.ModAPI;

import org.bukkit.entity.EntityType;

public class MobDrop {

	public final EntityType Type;
	
	//Out of 1001
	public final int Chance;
	
	public MobDrop(EntityType type, int chance)
	{
		this.Type = type;
		this.Chance = chance;
	}
	
}
