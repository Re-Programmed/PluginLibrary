package com.willm.ModAPI.Blocks;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.ArmorStand;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.potion.PotionEffect;

import com.willm.ModAPI.Main;
import com.willm.ModAPI.Items.CustomItemStack;

public class LiquidBlock extends CustomBlock {
	
	public int FlowTime = 20;
	public int FlowSpan = 3;
	
	public HashMap<ArmorStand, Location> flowLocations = new HashMap<ArmorStand, Location>();
	
	List<PotionEffect> effects = new ArrayList<PotionEffect>();
	public double LiquidDamage = 0;
	
	public static List<Location> noDrop = new ArrayList<Location>();
	
	public List<PotionEffect> GetLiquidEffects()
	{
		return effects;
	}
	
	public void AddLiquidEffect(PotionEffect effect)
	{
		effects.add(effect);
	}
	
	public LiquidBlock(CustomItemStack rootItem)
	{
		super(rootItem);
		drops = false;
	}
	
	public LiquidBlock(CustomItemStack rootItem, ArrayList<MachineConversion> mt, int m_size, int m_speed, String m_name)
	{
		super(rootItem, mt, m_size, m_speed, m_name);
		drops = false;
	}
	
	public LiquidBlock(CustomItemStack rootItem, Machine m)
	{
		super(rootItem, m);
		drops = false;
	}
	
	public LiquidBlock(CustomItemStack rootItem, ArrayList<MachineConversion> mt, int m_size, int m_speed, String m_name, String sound)
	{
		super(rootItem, mt, m_size, m_speed, m_name, sound);
		drops = false;
	}
	
	@Override
	public ArmorStand Place(Location location)
	{
		Main.Liquids.put(location, this);
		return super.Place(location);
	}
	
	@Override
	public void Place(Location location, BlockFace facing)
	{
		Main.Liquids.put(location, this);
		super.Place(location, facing);
	}
	
	@Override
	public void Remove(Location location, boolean drops)
	{
		for(Entry<ArmorStand, Location> e : flowLocations.entrySet())
		{
			if(e.getValue().distance(location) < 0.6f)
			{
				if(e.getKey().getLocation().getBlock().getType() == Material.LIGHT_WEIGHTED_PRESSURE_PLATE) {e.getKey().getLocation().getBlock().setType(Material.AIR);}
				e.getKey().getLocation().getBlock().getRelative(BlockFace.UP).setType(Material.AIR);
				e.getKey().remove();
			}
		}
		
		for(int y = 1; y < 300; y++)
		{
			Location loc = new Location(location.getWorld(), location.getBlockX(), location.getBlockY() - y, location.getBlockZ());
			
			if(CheckForCustomBlock(loc.getBlock()))
			{
				Remove(loc);
			}else {
				break;
			}
		}
		
		Main.Liquids.remove(location);
		super.Remove(location, drops);
	}
	
	@Override
	public void Remove(BlockBreakEvent event)
	{
		for(Entry<ArmorStand, Location> e : flowLocations.entrySet())
		{
			if(e.getValue().distance(event.getBlock().getLocation()) < 0.6f)
			{
				if(e.getKey().getLocation().getBlock().getType() == Material.LIGHT_WEIGHTED_PRESSURE_PLATE) {e.getKey().getLocation().getBlock().setType(Material.AIR);}
				e.getKey().getLocation().getBlock().getRelative(BlockFace.UP).setType(Material.AIR);
				e.getKey().remove();
			}
		}
		
		for(int y = 1; y < 300; y++)
		{
			Location loc = new Location(event.getBlock().getWorld(), event.getBlock().getX(), event.getBlock().getY() - y, event.getBlock().getZ());
			
			if(CheckForCustomBlock(loc.getBlock()))
			{
				Remove(loc);
			}else {
				break;
			}
		}
		
		Main.Liquids.remove(event.getBlock().getLocation());
		super.Remove(event);
	}

	public void Place(Location location, boolean temp_source)
	{
		Main.Liquids.put(location, this);
		super.Place(location);
		if(temp_source) {noDrop.add(location);}
	}
	
}
