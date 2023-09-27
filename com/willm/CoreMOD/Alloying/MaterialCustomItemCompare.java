package com.willm.CoreMOD.Alloying;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import com.willm.ModAPI.Items.CustomItemStack;

public class MaterialCustomItemCompare {

	private final Material mat;
	private final CustomItemStack cis;
	
	public MaterialCustomItemCompare(Material mat)
	{
		this.mat = mat;
		this.cis = null;
	}
	
	public MaterialCustomItemCompare(CustomItemStack cis)
	{
		this.cis = cis;
		this.mat = null;
	}
	
	public MaterialCustomItemCompare()
	{
		this.cis = null;
		this.mat = null;
	}
	
	public boolean Compare(ItemStack item)
	{
		if(cis != null)
		{
			return cis.CheckForCustomItem(item);
		}
		
		if(mat != null)
		{
			return item.getType() == mat;
		}
		
		return false;
	}
}
