package com.willm.ModAPI.Enchantments;

import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.inventory.ItemStack;

public class EnchantmentWrapper extends Enchantment {

	private final String name;
	private final int maxLvl;
	
	public EnchantmentWrapper(String namespace, String name, int lvl) {
		super(NamespacedKey.minecraft(namespace));
		this.name = name;
		this.maxLvl = lvl;
	}

	@Override
	public boolean canEnchantItem(ItemStack arg0) {
		
		return false;
	}

	@Override
	public boolean conflictsWith(Enchantment arg0) {
		
		return false;
	}

	@Override
	public EnchantmentTarget getItemTarget() {
		
		return null;
	}

	@Override
	public int getMaxLevel() {	
		return maxLvl;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public int getStartLevel() {
		
		return 0;
	}

	@Override
	public boolean isCursed() {
		
		return false;
	}

	@Override
	public boolean isTreasure() {
		
		return false;
	}
}
