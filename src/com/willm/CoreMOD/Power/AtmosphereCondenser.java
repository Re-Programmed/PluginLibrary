package com.willm.CoreMOD.Power;


import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.willm.CoreMOD.MyItems;
import com.willm.CoreMOD.ElementalItems.Nonmetals;
import com.willm.CoreMOD.ElementalItems.RegisterElementalItems;
import com.willm.ModAPI.Blocks.Machine;
import com.willm.ModAPI.Blocks.MachineConversion;
import com.willm.ModAPI.Items.BlockCreator;
import com.willm.ModAPI.Items.CustomItemStack;
import com.willm.ModAPI.Items.ItemCreator;
import com.willm.ModAPI.Voltage.Blocks.EnergyCompatible;

public class AtmosphereCondenser extends EnergyCompatible {

	public AtmosphereCondenser()
	{
		CustomItemStack cis = ItemCreator.RegisterNewItem(new CustomItemStack("Atmosphere Condenser", Material.POLISHED_ANDESITE, 31062));
		
		cis.AddLoreLine(ChatColor.RED + "Needs Power!");
		
		blockRef = BlockCreator.RegisterNewBlock(cis, "core_mod.polymer_chamber", 150, 9, "Atmosphere Condenser"
				, new MachineConversion(new ItemStack(Material.GLASS_BOTTLE), MyItems.o2_bottle.GetAmountClone(2))
				, new MachineConversion(RegisterElementalItems.GasCanister.GetMyItemStack(), Nonmetals.GetNonmetal(com.willm.CoreMOD.ElementalItems.ELEnum.Nonmetals.NITROGEN).GetAmountClone(4))
				);


	}
	
	@Override
	public void Tick(Location loc) {
		if(!sources.containsKey(loc)) {sources.put(loc, 0);}
		if(sources.get(loc) == null) {sources.put(loc, 0);}
		
		for(Player p : Bukkit.getOnlinePlayers())
		{
			if(p.getLocation().distance(loc) < 2f)
			{
				if(p.isSneaking())
				{
					p.sendMessage(ChatColor.RED + "[ATMOSPHERE CONDENSER POWER]: " + sources.get(loc) + " / 1500");
				}
			}
		}
		
		if(sources.get(loc) > 1500)
		{
			for(Machine m : blockRef.m)
			{
				if(m.location.distance(loc) < 0.1f)
				{
					m.productSpeed = blockRef.getMachineTemplate().productSpeed;

					RemoveEnergy(1500, loc);
				}
			}
		}else {
			for(Machine m : blockRef.m)
			{
				if(m.location.distance(loc) < 0.1f)
				{
					m.productSpeed = 0.0000005f;
					
				}
			}
		}
		
	
	}

}
