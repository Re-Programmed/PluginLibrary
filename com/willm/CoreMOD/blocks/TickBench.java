package com.willm.CoreMOD.blocks;

import com.willm.ModAPI.Blocks.CustomStates.TickBlock;
import com.willm.ModAPI.Items.CustomItemStack;

public abstract class TickBench extends Workbench implements TickBlock {

	public TickBench(CustomItemStack rootItem) {
		super(rootItem);
		
		this.RegisterAsTickBlock();
	}

}
