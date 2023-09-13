package com.willm.CoreMOD.CMachines;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import com.willm.ModAPI.Blocks.Machine;
import com.willm.ModAPI.Blocks.MachineConversion;

public class EnchantingStation extends Machine {

	public EnchantingStation() {
		super(9, "Enchanting Station", 0, null);
	}
	
	public EnchantingStation(Machine m)
	{
		super(m);
	}
	
	public EnchantingStation(int size, String name, int productSpeed, ArrayList<MachineConversion> conversions)
	{
		super(size, name, productSpeed, conversions);
	}
	
	public EnchantingStation(int size, String name, int productSpeed, ArrayList<MachineConversion> conversions, String sound)
	{
		super(size, name, productSpeed, conversions, sound);
	}
	
	@Override
	public void MachineTick()
	{
		if(location == null) {return;}
		for(ItemStack is : myInventory.getContents())
		{
			if(is != null)
			{
				if(is.getType() != Material.AIR && is.getType() != Material.ENCHANTED_BOOK) { location.getWorld().dropItemNaturally(location, is).setPickupDelay(0); myInventory.remove(is); }
			}
		}
		
		if(myInventory.getItem(0) == null)
		{
			myInventory.setItem(0, new ItemStack(Material.ENCHANTED_BOOK));
		}
	}
	
	@Override
	public Machine Clone()
	{
		return new EnchantingStation();
	}

}
