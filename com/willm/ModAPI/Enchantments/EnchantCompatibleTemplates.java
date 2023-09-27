package com.willm.ModAPI.Enchantments;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;

public enum EnchantCompatibleTemplates {

	Swords(Material.WOODEN_SWORD, Material.STONE_SWORD, Material.GOLDEN_SWORD, Material.IRON_SWORD, Material.DIAMOND_SWORD, Material.NETHERITE_SWORD),
	Axes(Material.WOODEN_AXE, Material.STONE_AXE, Material.GOLDEN_AXE, Material.IRON_AXE, Material.DIAMOND_AXE, Material.NETHERITE_AXE),
	Shovels(Material.WOODEN_SHOVEL, Material.STONE_SHOVEL, Material.GOLDEN_SHOVEL, Material.IRON_SHOVEL, Material.DIAMOND_SHOVEL, Material.NETHERITE_SHOVEL),
	Hoes(Material.WOODEN_HOE, Material.STONE_HOE, Material.GOLDEN_HOE, Material.IRON_HOE, Material.DIAMOND_HOE, Material.NETHERITE_HOE),
	Pickaxes(Material.WOODEN_PICKAXE, Material.STONE_PICKAXE, Material.GOLDEN_PICKAXE, Material.IRON_PICKAXE, Material.DIAMOND_PICKAXE, Material.NETHERITE_PICKAXE),
	
	Bow(Material.BOW),
	
	Chestplates(Material.IRON_CHESTPLATE, Material.GOLDEN_CHESTPLATE, Material.CHAINMAIL_CHESTPLATE, Material.DIAMOND_CHESTPLATE, Material.NETHERITE_CHESTPLATE),
	Helmets(Material.IRON_HELMET, Material.GOLDEN_HELMET, Material.CHAINMAIL_HELMET, Material.DIAMOND_HELMET, Material.NETHERITE_HELMET),
	Leggings(Material.IRON_LEGGINGS, Material.GOLDEN_LEGGINGS, Material.CHAINMAIL_LEGGINGS, Material.DIAMOND_LEGGINGS, Material.NETHERITE_LEGGINGS),
	Boots(Material.IRON_BOOTS, Material.GOLDEN_BOOTS, Material.CHAINMAIL_BOOTS, Material.DIAMOND_BOOTS, Material.NETHERITE_BOOTS),
	
	Tools(EnchantCompatibleTemplates.Axes, EnchantCompatibleTemplates.Shovels, EnchantCompatibleTemplates.Hoes, EnchantCompatibleTemplates.Pickaxes),
	Weapons(EnchantCompatibleTemplates.Swords, EnchantCompatibleTemplates.Axes),
	Weapons_PlusBow(EnchantCompatibleTemplates.Swords, EnchantCompatibleTemplates.Axes, EnchantCompatibleTemplates.Bow),
	Armor(EnchantCompatibleTemplates.Chestplates, EnchantCompatibleTemplates.Helmets, EnchantCompatibleTemplates.Leggings, EnchantCompatibleTemplates.Boots),
	Handhelds(EnchantCompatibleTemplates.Swords, EnchantCompatibleTemplates.Axes, EnchantCompatibleTemplates.Shovels, EnchantCompatibleTemplates.Hoes, EnchantCompatibleTemplates.Pickaxes),
	Handhelds_PlusBow(EnchantCompatibleTemplates.Swords, EnchantCompatibleTemplates.Axes, EnchantCompatibleTemplates.Shovels, EnchantCompatibleTemplates.Hoes, EnchantCompatibleTemplates.Pickaxes, EnchantCompatibleTemplates.Bow);


	public final List<Material> Materials = new ArrayList<Material>();
	
	EnchantCompatibleTemplates(Material... materials)
	{
		for(Material m : materials)
		{
			Materials.add(m);
		}
	}
	
	EnchantCompatibleTemplates(EnchantCompatibleTemplates... ects)
	{
		for(EnchantCompatibleTemplates ect : ects)
		{
			for(Material m : ect.Materials)
			{
				Materials.add(m);
			}
		}
	}
	
}
