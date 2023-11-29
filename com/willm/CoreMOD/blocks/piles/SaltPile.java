package com.willm.CoreMOD.blocks.piles;

import org.bukkit.Material;

import com.willm.CoreMOD.MyItems;
import com.willm.CoreMOD.blocks.CompostPile;
import com.willm.ModAPI.Items.BlockCreator;
import com.willm.ModAPI.Items.CustomItemStack;
import com.willm.ModAPI.Items.ItemCreator;

public class SaltPile extends CompostPile {

	public static SaltPile SALT_PILE;
	
	SaltPile(CustomItemStack rootItem)
	{
		super(rootItem);
	}
	
	@Override
	protected CustomItemStack GetDropItem()
	{
		return MyItems.salt_item;
	}
	
	public static void RegisterItem()
	{
		CustomItemStack item = ItemCreator.RegisterNewItem(new CustomItemStack("Salt Pile", Material.WARPED_TRAPDOOR, 16231));
		
		SALT_PILE = new SaltPile(item);
		SALT_PILE.BASE_CMD = 16231;

		BlockCreator.RegisterNewBlock(item, SALT_PILE);
	}
	
}
