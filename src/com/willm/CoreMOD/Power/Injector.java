package com.willm.CoreMOD.Power;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.willm.CoreMOD.MyItems;
import com.willm.ModAPI.Blocks.Machine;
import com.willm.ModAPI.Blocks.MachineConversion;
import com.willm.ModAPI.Items.BlockCreator;
import com.willm.ModAPI.Items.CustomItemStack;
import com.willm.ModAPI.Items.ItemCreator;
import com.willm.ModAPI.Voltage.Blocks.EnergyCompatible;

import net.md_5.bungee.api.ChatColor;

public class Injector extends EnergyCompatible {
	
	public Injector()
	{
		CustomItemStack cis = ItemCreator.RegisterNewItem(new CustomItemStack("Injector", Material.POLISHED_ANDESITE, 26216));
		
		cis.AddLoreLine(ChatColor.RED + "Needs Power!");
		
		blockRef = BlockCreator.RegisterNewBlock(cis, "core_mod.refiner_sound", 0, 9, "Injector"
				, new MachineConversion(MyItems.jetpack.GetMyItemStack(), new CustomItemStack("Jetpack (1000 mB)", Material.IRON_INGOT, 1).GetMyItemStack())
				, new MachineConversion(MyItems.oil_gun.GetMyItemStack(), new CustomItemStack("Oil Gun (1000 mB)", Material.IRON_INGOT, 1).GetMyItemStack()));


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
					p.sendMessage(ChatColor.RED + "[INJECTOR POWER]: " + sources.get(loc));
				}
			}
		}
		if(sources.get(loc) > 250)
		{
			for(Machine m : blockRef.m)
			{
				if(m.location.distance(loc) < 0.1f)
				{

							for(ItemStack i : m.getInventory().getContents())
							{
								if(i == null) {continue;}
								if(m.getInventory().containsAtLeast(MyItems.kerosene.GetMyItemStack(), 1))
								{
									if(MyItems.jetpack.CheckForCustomItem(i))
									{
										ItemMeta im = i.getItemMeta();
										List<String> lore = im.getLore();
										
										int currFuel = GetJetpackFuel(lore.get(0));
										currFuel += 1000;
										
										if(currFuel > 10000) {continue;}
										
										lore.set(0, SetJetpackFuel(currFuel));
										
										im.setLore(lore);
										i.setItemMeta(im);
										
										m.getInventory().removeItem(MyItems.kerosene.GetMyItemStack());
									}
								}
								
								if(m.getInventory().containsAtLeast(MyItems.butane.GetMyItemStack(), 1))
								{
									if(MyItems.oil_gun.CheckForCustomItem(i))
									{
										ItemMeta im = i.getItemMeta();
										List<String> lore = im.getLore();
										
										int currFuel = GetJetpackFuel(lore.get(0));
										currFuel += 1000;
										
										if(currFuel > 10000) {continue;}
										
										lore.set(0, SetJetpackFuel(currFuel));
										
										im.setLore(lore);
										i.setItemMeta(im);
										
										m.getInventory().removeItem(MyItems.butane.GetMyItemStack());
									}
								}
							}
						
					

					RemoveEnergy(25, loc);
				}
			}
		}else {
			for(Machine m : blockRef.m)
			{
				if(m.location.distance(loc) < 0.1f)
				{
					
					
				}
			}
		}
		
	
	}
	
	public static String SetJetpackFuel(int currFuel)
	{
		if(currFuel < 2000)
		{
			return ChatColor.RED + "Fuel: " + currFuel + " mB";
		}else if(currFuel < 5000)
		{
			return ChatColor.GRAY + "Fuel: " + currFuel + " mB";
		}else {
			return ChatColor.GREEN + "Fuel: " + currFuel + " mB";
		}
		
	}
	
	public static int GetJetpackFuel(String lore)
	{
		return Integer.parseInt(lore.replace(ChatColor.GRAY + "Fuel: ", "").replace(ChatColor.GREEN + "Fuel: ", "").replace(ChatColor.RED + "Fuel: ", "").replace(" mB", ""));
	}
	
	
}
