package com.willm.CoreMOD.Alloying;

public enum AlloyMaterial {
	WOOD(1, "Wooden"),
	STONE(2, "Stone"),
	IRON(3, "Iron"),
	GOLD(4, "Golden"),
	DIAMOND(5, "Diamond"),
	NETHERITE(6, "Netherite"),
	TUNGSTEN(7, "Tungsten"),
	PLATINUM(8, "Platinum");
	
	public final int Index;
	public final String NameModifer;
	AlloyMaterial(int index, String name)
	{
		Index = index;
		this.NameModifer = name;
	}
}
