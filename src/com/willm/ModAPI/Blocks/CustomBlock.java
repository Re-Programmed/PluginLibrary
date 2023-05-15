package com.willm.ModAPI.Blocks;

import java.util.ArrayList;

import org.apache.commons.lang.WordUtils;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Dispenser;
import org.bukkit.block.data.Directional;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import com.willm.ModAPI.Main;
import com.willm.ModAPI.Utils;
import com.willm.ModAPI.Items.CustomItemStack;
import com.willm.ModAPI.Items.ItemCreator;

public class CustomBlock {
	private CustomItemStack rootItem;
	public CustomItemStack getRootItem() {return rootItem;}
	
	private Material displayMaterial;
	public Material getDisplayMaterial() {return displayMaterial;}
	
	private int displayCustomModelData;
	public int getDisplayCustomModelData()  {return displayCustomModelData;}
	
	private String name;
	public String getName()  {return name;}
	
	public ArrayList<Machine> m = new ArrayList<Machine>();
	private Machine machineTemplate;
	public Machine getMachineTemplate() {return machineTemplate;}
	
	boolean drops = true;
	public CustomBlock SetDrops(boolean drops) {this.drops = drops; return this;}
	
	public CustomBlock(CustomItemStack rootItem)
	{
		this.rootItem = rootItem;
		displayMaterial = rootItem.GetMyItemStack().getType();
		displayCustomModelData = rootItem.getCustomModelData();
		this.name = rootItem.getName();
	}
	
	public CustomBlock(CustomItemStack rootItem, ArrayList<MachineConversion> mt, int m_size, int m_speed, String m_name)
	{
		this.rootItem = rootItem;
		displayMaterial = rootItem.GetMyItemStack().getType();
		displayCustomModelData = rootItem.getCustomModelData();
		this.name = rootItem.getName();
	
		machineTemplate = new Machine(m_size, m_name, m_speed, mt);
				
		rootItem.AddLoreLine(ChatColor.WHITE + "Converts: ");
		
		for(MachineConversion mc : mt)
		{
			rootItem.AddLoreLine(ChatColor.WHITE + (mc.i1.getItemMeta().hasDisplayName() ? mc.i1.getItemMeta().getDisplayName() : WordUtils.capitalize(mc.i1.getType().toString().toLowerCase().replace('_', ' '))) + " -> " + (mc.i2[0].getItemMeta().hasDisplayName() ? mc.i2[0].getItemMeta().getDisplayName() : WordUtils.capitalize(mc.i2[0].getType().toString().toLowerCase().replace('_', ' '))));
		}
		
		
	}
	
	public CustomBlock(CustomItemStack rootItem, Machine m)
	{
		this.rootItem = rootItem;
		displayMaterial = rootItem.GetMyItemStack().getType();
		displayCustomModelData = rootItem.getCustomModelData();
		this.name = rootItem.getName();
	
		machineTemplate = m;
				
		if(m.conversions != null)
		{
			rootItem.AddLoreLine(ChatColor.WHITE + "Converts: ");
			for(MachineConversion mc : m.conversions)
			{
				rootItem.AddLoreLine(ChatColor.WHITE + (mc.i1.getItemMeta().hasDisplayName() ? mc.i1.getItemMeta().getDisplayName() : WordUtils.capitalize(mc.i1.getType().toString().toLowerCase().replace('_', ' '))) + " -> " + (mc.i2[0].getItemMeta().hasDisplayName() ? mc.i2[0].getItemMeta().getDisplayName() : WordUtils.capitalize(mc.i2[0].getType().toString().toLowerCase().replace('_', ' '))));
			}
			
		}
		
		
	}
	
	public CustomBlock(CustomItemStack rootItem, ArrayList<MachineConversion> mt, int m_size, int m_speed, String m_name, String sound)
	{
		this.rootItem = rootItem;
		displayMaterial = rootItem.GetMyItemStack().getType();
		displayCustomModelData = rootItem.getCustomModelData();
		this.name = rootItem.getName();
	
		machineTemplate = new Machine(m_size, m_name, m_speed, mt, sound);
				
		rootItem.AddLoreLine(ChatColor.WHITE + "Converts: ");
		
		for(MachineConversion mc : mt)
		{
			rootItem.AddLoreLine(ChatColor.WHITE + (mc.i1.getItemMeta().hasDisplayName() ? mc.i1.getItemMeta().getDisplayName() : WordUtils.capitalize(mc.i1.getType().toString().toLowerCase().replace('_', ' '))) + " -> " + (mc.i2[0].getItemMeta().hasDisplayName() ? mc.i2[0].getItemMeta().getDisplayName() : WordUtils.capitalize(mc.i2[0].getType().toString().toLowerCase().replace('_', ' '))));
		}
		
	}
	
	public String getTag()
	{
		return Main.PluginName + ":" + getName().toLowerCase().replace(' ', '_') + "_" + rootItem.getCustomModelData();
	}
	
	@SuppressWarnings("unchecked")
	public ArmorStand Place(Location location)
	{		
		location.getBlock().setType(Material.DISPENSER);
		Dispenser d = (Dispenser)location.getBlock().getState();
		
		d.setLock(getTag());
		
		d.update(true);
		
		ArmorStand displayStand = (ArmorStand)location.getWorld().spawnEntity(new Location(location.getWorld(), location.getX() + 0.5f, location.getY(), location.getZ() + 0.5f), EntityType.ARMOR_STAND);
		
		displayStand.setInvisible(true);
		displayStand.setMarker(true);
		displayStand.setFireTicks(999999999);
		displayStand.setSmall(true);
		
		AddToArmorStand(displayStand);
		
		ItemStack disp = ItemCreator.CreateNamedItemStack(displayMaterial, name, 1);
	
		ItemCreator.SetItemCustomModelData(disp, getDisplayCustomModelData());
		
		displayStand.getEquipment().setHelmet(disp);
		
		if(machineTemplate != null)
		{
			if(machineTemplate.conversions == null)
			{
				m.add(machineTemplate.Clone().SetLocation(location));
			}else {
				m.add(new Machine(machineTemplate.size, machineTemplate.name, (int) Math.floor(machineTemplate.productSpeed * 100f), (ArrayList<MachineConversion>)machineTemplate.conversions.clone(), machineTemplate.sound_clip).SetLocation(location));
			}
		}
		
		return displayStand;
	}
	
	@SuppressWarnings("unchecked")
	public void Place(Location location, BlockFace facing)
	{		
		location.getBlock().setType(Material.DISPENSER);
		
		Directional dir = (Directional) location.getBlock().getBlockData();
		
		dir.setFacing(facing);
		
		location.getBlock().setBlockData(dir);
		
		Dispenser d = (Dispenser)location.getBlock().getState();
		
		d.setLock(getTag());
		
		d.update(true);
		
		ArmorStand displayStand = (ArmorStand)location.getWorld().spawnEntity(new Location(location.getWorld(), location.getX() + 0.5f, location.getY(), location.getZ() + 0.5f), EntityType.ARMOR_STAND);
		
		displayStand.setRotation(facing.getModX(), 0f);
		
		displayStand.setInvisible(true);
		displayStand.setMarker(true);
		displayStand.setFireTicks(999999999);
		displayStand.setSmall(true);
		
		AddToArmorStand(displayStand);
		
		ItemStack disp = ItemCreator.CreateNamedItemStack(displayMaterial, name, 1);
	
		ItemCreator.SetItemCustomModelData(disp, getDisplayCustomModelData());
		
		displayStand.getEquipment().setHelmet(disp);
		
		if(machineTemplate != null)
		{
			if(machineTemplate.conversions == null)
			{
				m.add(machineTemplate.Clone().SetLocation(location));
			}else {
				m.add(new Machine(machineTemplate.size, machineTemplate.name, (int) Math.floor(machineTemplate.productSpeed * 100f), (ArrayList<MachineConversion>)machineTemplate.conversions.clone(), machineTemplate.sound_clip).SetLocation(location));
			}
		}
	}
	
	public void AddToArmorStand(ArmorStand stand)
	{
		
	}
	
	public boolean CheckForCustomBlock(Block block)
	{
		if(block.getState() instanceof Dispenser)
		{
			Dispenser d = (Dispenser)block.getState();
			
			if(d.isLocked())
			{
				if(d.getLock().equalsIgnoreCase(getTag().toLowerCase()))
				{
					return true;
				}
			}
		}
		
		return false;
	}
	
	public void Interact(PlayerInteractEvent event)
	{
		if(!event.getPlayer().isSneaking())
		{
			if(machineTemplate != null)
			{
				for(Machine ma : m)
				{
					if(ma.location.distance(event.getClickedBlock().getLocation()) < 0.3f)
					{
						ma.Interaction(event);
						break;
					}
				}
			}else{
				event.setCancelled(true);
			}
		}
	}
	
	public void Remove(Location location) {Remove(location, false);}
	public void Remove(Location location, boolean drops)
	{
		Location blockCenter = Utils.AddToLocationAsNew(location, 0.5f, 0, 0.5f);
		ArmorStand rem = null;
		double dist = 1;
		for(Entity e : location.getWorld().getNearbyEntities(blockCenter, 0.2f, 0.2f, 0.2f))
		{
			if(e.getType() == EntityType.ARMOR_STAND)
			{
				if(e.getLocation().distance(blockCenter) < dist)
				{
					dist = e.getLocation().distance(blockCenter);
					rem = (ArmorStand)e;
				}
			}
		}
		
		if(rem != null)
		{
			rem.remove();
		}
		
		if(drops && this.drops) {rootItem.DropNaturally(blockCenter);}
		
		location.getBlock().setType(Material.AIR);
		
		for(Machine ma : m)
		{
			if(ma.location.distance(location) < 0.1f)
			{
				ma.Destroy(location);
				m.remove(ma);
				break;
			}
		}
	}
	
	public void Remove(BlockBreakEvent event)
	{	
		Location blockCenter = Utils.AddToLocationAsNew(event.getBlock().getLocation(), 0.5f, 0, 0.5f);
		ArmorStand rem = null;
		double dist = 1;
		for(Entity e : event.getBlock().getLocation().getWorld().getNearbyEntities(blockCenter, 0.2f, 0.2f, 0.2f))
		{
			if(e.getType() == EntityType.ARMOR_STAND)
			{
				if(e.getLocation().distance(blockCenter) < dist)
				{
					dist = e.getLocation().distance(blockCenter);
					rem = (ArmorStand)e;
				}
			}
		}
		
		if(rem != null)
		{
			rem.remove();
		}
		
		event.setDropItems(false);
		
		if(drops) {rootItem.DropNaturally(blockCenter);}
		
		for(Machine ma : m)
		{
			if(ma.location.distance(event.getBlock().getLocation()) < 0.1f)
			{
				ma.Destroy(blockCenter);
				m.remove(ma);
				break;
			}
		}
	}

}
