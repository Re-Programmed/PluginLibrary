package com.willm.CoreMOD;

import org.bukkit.Material;
import org.bukkit.entity.Villager.Profession;
import org.bukkit.inventory.ItemStack;

import com.willm.CoreMOD.Alloying.Crucibles.CrucibleItemRegistry;
import com.willm.ModAPI.Entities.CustomTrade;
import com.willm.ModAPI.Entities.CustomVillager;
import com.willm.ModAPI.Entities.EntityCreator;

public class MyEntities {

	public static CustomVillager MetallurgyVillager;
	
	public static void RegisterEntities()
	{
		MetallurgyVillager = (CustomVillager)EntityCreator.RegisterNewEntity(new CustomVillager("Metallurgy", Profession.MASON, 20, CrucibleItemRegistry.basic_crucible.getRelatedBlock()
				, new CustomTrade(MyItems.aluminum.GetMyItemStack(), 3, true, 3, 1).AddIngrident(MyItems.salt_item.GetAmountClone(32))
				, new CustomTrade(MyItems.tungsten_ingot.GetMyItemStack(), 3, true, 3, 2).AddIngrident(new ItemStack(Material.IRON_INGOT, 5))

				));
	}
	
}
