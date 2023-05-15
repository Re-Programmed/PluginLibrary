package com.willm.ModAPI.Blocks;

import org.bukkit.inventory.ItemStack;

public class MachineConversion {

	public final ItemStack i1;
	public final ItemStack[] i2;
	
	float progress = 0;
	float maxProgress = 20;
	
	public void IncreaseProgress(float amount)
	{
		progress += amount;
	}
	
	public void ResetProgress()
	{
		progress = 0;
	}
	
	public MachineConversion(ItemStack i1, ItemStack... i2)
	{
		this.i1 = i1;
		this.i2 = i2;
	}
	
	public boolean IsDone()
	{
		return progress >= maxProgress;
	}
}
