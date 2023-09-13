package com.willm.ModAPI.Items;

import java.util.ArrayList;

import com.willm.ModAPI.Main;
import com.willm.ModAPI.Blocks.CustomBlock;
import com.willm.ModAPI.Blocks.LiquidBlock;
import com.willm.ModAPI.Blocks.Machine;
import com.willm.ModAPI.Blocks.MachineConversion;

public class BlockCreator {
	
	public static CustomBlock RegisterNewBlock(CustomItemStack rootItem)
	{
		CustomBlock b = new CustomBlock(rootItem);
		
		Main.CustomBlockRegistry.add(b);
		
		rootItem.setRelatedBlock(b);
		
		return b;
	}
	
	public static CustomBlock RegisterNewBlock(CustomItemStack rootItem, CustomBlock block)
	{
		Main.CustomBlockRegistry.add(block);
		rootItem.setRelatedBlock(block);
		return block;
	}

	public static LiquidBlock RegisterNewLiquid(CustomItemStack rootItem) {return RegisterNewLiquid(rootItem, 3, 20);}
	public static LiquidBlock RegisterNewLiquid(CustomItemStack rootItem, int flow_span, int flow_time)
	{
		LiquidBlock b = new LiquidBlock(rootItem);
		b.FlowSpan = flow_span;
		b.FlowTime = flow_time;
		
		Main.CustomBlockRegistry.add(b);
		
		rootItem.setRelatedBlock(b);
		
		return b;
	}
	
	public static CustomBlock RegisterNewBlock(CustomItemStack rootItem, Machine machine)
	{		
		CustomBlock b = new CustomBlock(rootItem, machine);
		
		Main.CustomBlockRegistry.add(b);
		
		rootItem.setRelatedBlock(b);
		
		return b;
	}
	
	
	public static CustomBlock RegisterNewBlock(CustomItemStack rootItem, int m_speed, int m_size, String m_name, MachineConversion... m_conversions)
	{
		ArrayList<MachineConversion> mcs = new ArrayList<MachineConversion>();
		
		for(MachineConversion mc : m_conversions)
		{
			mcs.add(mc);
		}
		
		CustomBlock b = new CustomBlock(rootItem, mcs, m_size, m_speed, m_name);
		
		Main.CustomBlockRegistry.add(b);
		
		rootItem.setRelatedBlock(b);
		
		return b;
	}
	
	public static CustomBlock RegisterNewBlock(CustomItemStack rootItem, String sound, int m_speed, int m_size, String m_name, MachineConversion... m_conversions)
	{
		ArrayList<MachineConversion> mcs = new ArrayList<MachineConversion>();
		
		for(MachineConversion mc : m_conversions)
		{
			mcs.add(mc);
		}
		
		CustomBlock b = new CustomBlock(rootItem, mcs, m_size, m_speed, m_name, sound);
		
		Main.CustomBlockRegistry.add(b);
		
		rootItem.setRelatedBlock(b);
		
		return b;
	}
	
}
