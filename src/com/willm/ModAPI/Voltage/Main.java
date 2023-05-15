package com.willm.ModAPI.Voltage;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;

import com.willm.ModAPI.Items.CustomItemStack;
import com.willm.ModAPI.Voltage.Blocks.CreativeEnergyAcceptor;
import com.willm.ModAPI.Voltage.Blocks.CreativeEnergySource;
import com.willm.ModAPI.Voltage.Blocks.EnergyCompatible;
import com.willm.ModAPI.Voltage.Blocks.EnergyReceiver;
import com.willm.ModAPI.Voltage.Blocks.EnergyReceiver2;
import com.willm.ModAPI.Voltage.Blocks.EnergyReceiver3;
import com.willm.ModAPI.Voltage.Blocks.EnergyReceiver4;
import com.willm.ModAPI.Voltage.Blocks.EnergyReceiver5;

public class Main {

	public static boolean Enabled = false;
	
	public static ArrayList<EnergyCompatible> energyUsers = new ArrayList<EnergyCompatible>();
	public static ArrayList<EnergyCompatible> energyRecievers = new ArrayList<EnergyCompatible>();
	
	//Use these for creating crafting recipes.
	public static CustomItemStack er1, er2, er3, er4, er5;
	
	//Run this function on plugin enable to enable voltage.
	public static void UseVoltage()
	{
		Enabled = true;
		InitEnergyBlocks();
	}
	
	private static void InitEnergyBlocks()
	{
		energyUsers.add(new CreativeEnergySource());
		
		EnergyReceiver er = new EnergyReceiver();
		energyUsers.add(er);
		energyRecievers.add(er);
		
		Main.er1 = er.GetBlockRef().getRootItem();
		
		EnergyReceiver2 er2 = new EnergyReceiver2();
		energyUsers.add(er2);
		energyRecievers.add(er2);
		
		Main.er2 = er2.GetBlockRef().getRootItem();
		
		EnergyReceiver3 er3 = new EnergyReceiver3();
		energyUsers.add(er3);
		energyRecievers.add(er3);
		
		Main.er3 = er3.GetBlockRef().getRootItem();
		
		EnergyReceiver4 er4 = new EnergyReceiver4();
		energyUsers.add(er4);
		energyRecievers.add(er4);
		
		Main.er4 = er4.GetBlockRef().getRootItem();
		
		EnergyReceiver5 er5 = new EnergyReceiver5();
		energyUsers.add(er5);
		energyRecievers.add(er5);
		
		Main.er5 = er5.GetBlockRef().getRootItem();
		
		CreativeEnergyAcceptor cea = new CreativeEnergyAcceptor();
		energyUsers.add(cea);
		energyRecievers.add(cea);
		
	}
	
	public static void CheckStand(ArmorStand as)
	{		
		if(as.getEquipment().getHelmet().getType() == Material.POLISHED_ANDESITE)
		{
			for(EnergyCompatible ec : energyUsers)
			{
				if(ec.GetBlockRef().CheckForCustomBlock(as.getLocation().getBlock()))
				{
					ec.Tick(as.getLocation().getBlock().getLocation());
					
				}
			}
		}
	}
}
