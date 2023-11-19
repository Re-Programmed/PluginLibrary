package com.willm.ModAPI.Entities;

import com.willm.ModAPI.Main;

public class EntityCreator {

	public static CustomEntity RegisterNewEntity(CustomEntity entity)
	{
		Main.CustomEntityRegistry.add(entity);
		return entity;
	}
	
}
