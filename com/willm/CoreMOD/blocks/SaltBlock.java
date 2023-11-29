package com.willm.CoreMOD.blocks;

import java.util.Random;

import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.event.block.BlockBreakEvent;

import com.willm.CoreMOD.MyItems;
import com.willm.CoreMOD.blocks.piles.SaltPile;
import com.willm.ModAPI.Blocks.CustomBlock;
import com.willm.ModAPI.Items.CustomItemStack;

public class SaltBlock extends CustomBlock {

	public SaltBlock(CustomItemStack rootItem) {
		super(rootItem);
	}
	
	@Override
	public void Remove(BlockBreakEvent event) {
		super.Remove(event);
		
		
		int randPile = new Random().nextInt(4);

		if(randPile != 0)
		{
			
			if(event.getBlock().getRelative(BlockFace.DOWN).getType() == Material.AIR || event.getBlock().getRelative(BlockFace.DOWN).getType() == Material.CAVE_AIR) {
				
				event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(), MyItems.salt_item.GetAmountClone(randPile));
				
				return;
			}
		
			SaltPile.SALT_PILE.Place(event.getBlock().getLocation());
			if(randPile > 1)
			{
				SaltPile.SALT_PILE.AddToPile(event.getBlock(), randPile - 1);
			}
			event.setCancelled(true);

		}
		
	}

}
