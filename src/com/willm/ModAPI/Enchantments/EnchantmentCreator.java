package com.willm.ModAPI.Enchantments;

import java.util.ArrayList;

public class EnchantmentCreator {
	public static ArrayList<CustomEnchantment> EnchantmentRegistry = new ArrayList<CustomEnchantment>();
	
	public static CustomEnchantment RegisterEnchantment(CustomEnchantment e)
	{
		EnchantmentRegistry.add(e);
		return e;
	}
}
