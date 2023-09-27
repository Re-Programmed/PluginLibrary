package com.willm.CoreMOD.blocks;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;

import com.willm.CoreMOD.BlockEvents;
import com.willm.ModAPI.Blocks.CustomStates.CustomBaseMaterialRetainingBlock;
import com.willm.ModAPI.Items.CustomItemStack;

public abstract class Workbench extends CustomBaseMaterialRetainingBlock {

	public Workbench(CustomItemStack rootItem) {
		super(rootItem);
		
		BlockEvents.Workbenches.add(this);
	}
	
	public abstract WorkbenchType GetType();
	//Called when player right clicks bench.
	public abstract void OnInteract(PlayerInteractEvent event);
	//Called when bench is accessed from external menu.
	public abstract void OnInteract(Block b, Player p);

	
}
