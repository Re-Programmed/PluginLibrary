package com.willm.ModAPI.Blocks;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Dispenser;
import org.bukkit.block.data.Directional;
import org.bukkit.enchantments.Enchantment;
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
	
	boolean constBlock = true;
	public CustomBlock SetConstBlock(boolean constBlock) {this.constBlock = constBlock; return this;}

	public Material mineAs = null;
	public CustomBlock SetMineAs(Material mineAs) {this.mineAs = mineAs; return this;}
	
	public BlockDirectionData Directional = BlockDirectionData.NONE;
	public CustomBlock SetDirectional(BlockDirectionData directional) {Directional = directional; return this;}
	
	public CustomBlock sidewaysBlockData;
	public CustomBlock SetSidewaysBlock(CustomBlock block) {sidewaysBlockData = block; return this;}
	
	public CustomItemStack[] customDrops = null;
	public CustomBlock SetCustomDrops(CustomItemStack... item) {customDrops = item; return this;}
	
	private boolean useSilkTouch;
	public CustomBlock UseSilkTouch(boolean use) {useSilkTouch = use;return this;}
	
	private String[] requireTool = null;
	public CustomBlock SetRequiredTool(String... tools) {requireTool = tools;return this;}
	
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
			rootItem.AddLoreLine(ChatColor.WHITE + (mc.i1.getItemMeta().hasDisplayName() ? mc.i1.getItemMeta().getDisplayName() : mc.i1.getType().toString().toLowerCase().replace('_', ' ').toUpperCase()) + " -> " + (mc.i2[0].getItemMeta().hasDisplayName() ? mc.i2[0].getItemMeta().getDisplayName() : mc.i2[0].getType().toString().toLowerCase().replace('_', ' ').toUpperCase()));
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
				rootItem.AddLoreLine(ChatColor.WHITE + (mc.i1.getItemMeta().hasDisplayName() ? mc.i1.getItemMeta().getDisplayName() : mc.i1.getType().toString().toLowerCase().replace('_', ' ').toUpperCase()) + " -> " + (mc.i2[0].getItemMeta().hasDisplayName() ? mc.i2[0].getItemMeta().getDisplayName() : mc.i2[0].getType().toString().toLowerCase().replace('_', ' ').toUpperCase()));
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
			rootItem.AddLoreLine(ChatColor.WHITE + (mc.i1.getItemMeta().hasDisplayName() ? mc.i1.getItemMeta().getDisplayName() : mc.i1.getType().toString().toLowerCase().replace('_', ' ').toUpperCase()) + " -> " + (mc.i2[0].getItemMeta().hasDisplayName() ? mc.i2[0].getItemMeta().getDisplayName() : mc.i2[0].getType().toString().toLowerCase().replace('_', ' ').toUpperCase()));
		}
		
	}
	
	public String getTag()
	{
		return Main.PluginName + ":" + getName().toLowerCase().replace(' ', '_') + "_" + rootItem.getCustomModelData();
	}
	
	@SuppressWarnings("unchecked")
	public ArmorStand Place(Location location)
	{		
		if(constBlock) {
			location.getBlock().setType(Material.DISPENSER);
		
			Dispenser d = (Dispenser)location.getBlock().getState();
			
			d.setLock(getTag());
			
			d.update(true);
		}else {
			location.getBlock().setType(Material.GLASS);
		}
		
		ArmorStand displayStand = (ArmorStand)location.getWorld().spawnEntity(new Location(location.getWorld(), location.getX() + 0.5f, location.getY(), location.getZ() + 0.5f), EntityType.ARMOR_STAND);
		
		displayStand.setInvisible(true);
		displayStand.setMarker(true);
		if(constBlock) {displayStand.setFireTicks(999999999);}
		displayStand.setSmall(true);
		
		AddToArmorStand(displayStand);
		
		ItemStack disp = ItemCreator.CreateNamedItemStack(displayMaterial, name, 1);
	
		ItemCreator.SetItemCustomModelData(disp, getDisplayCustomModelData());
		
		if(!constBlock)
		{
			disp.setAmount(2);
		}
		
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
	
	public void Place(Location location, BlockFace facing)
	{
		Place(location, facing, 0.f);
	}
	
	@SuppressWarnings("unchecked")
	public void Place(Location location, BlockFace facing, float addRot)
	{	
		if(constBlock) {
			location.getBlock().setType(Material.DISPENSER);
			
			Directional dir = (Directional) location.getBlock().getBlockData();
			
			dir.setFacing(facing);
			
			location.getBlock().setBlockData(dir);
			
			Dispenser d = (Dispenser)location.getBlock().getState();
			
			d.setLock(getTag());
			
			d.update(true);
		}else {
			location.getBlock().setType(Material.GLASS);
		}
		
		ArmorStand displayStand = (ArmorStand)location.getWorld().spawnEntity(new Location(location.getWorld(), location.getX() + 0.5f, location.getY(), location.getZ() + 0.5f), EntityType.ARMOR_STAND);
		
		displayStand.setRotation(facing.getModX() * 90f + addRot, 0f);
		
		displayStand.setInvisible(true);
		displayStand.setMarker(true);
		if(constBlock) {displayStand.setFireTicks(999999999);}
		displayStand.setSmall(true);
		
		AddToArmorStand(displayStand);
		
		ItemStack disp = ItemCreator.CreateNamedItemStack(displayMaterial, name, 1);
	
		ItemCreator.SetItemCustomModelData(disp, getDisplayCustomModelData());
		
		if(!constBlock)
		{
			disp.setAmount(2);
		}
		
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
		}else if(block.getType() == Material.GLASS || block.getType() == mineAs)
		{
			for(Entity e : block.getWorld().getNearbyEntities(Utils.AddToLocationAsNew(block.getLocation(), 0.5f, 0, 0.5f), 0.2f, 0.2f, 0.2f))
			{
				if(e instanceof ArmorStand)
				{
					ArmorStand as = (ArmorStand)e;
					if(as.getEquipment().getHelmet() != null)
					{
						if(as.getEquipment().getHelmet().getAmount() > 1)
						{
							if(as.getEquipment().getHelmet().getItemMeta().getDisplayName().replace(ChatColor.WHITE + "", "").equalsIgnoreCase(name.toLowerCase()))
							{
								return true;
							}
						}
					}
				}
			}
		}
		
		return false;
	}
	
	public void InitMineAs(Block b)
	{
		if(mineAs != null)
		{
			b.setType(mineAs);
			
			getMyStand(b).setFireTicks(20 * 8);
		}
	}
	
	private ArmorStand getMyStand(Block b)
	{
		for(Entity e : b.getWorld().getNearbyEntities(Utils.AddToLocationAsNew(b.getLocation(), 0.5f, 0, 0.5f), 0.2f, 0.2f, 0.2f))
		{
			if(e instanceof ArmorStand)
			{
				ArmorStand as = (ArmorStand)e;
				if(as.getEquipment().getHelmet() != null)
				{
					if(as.getEquipment().getHelmet().getAmount() > 1)
					{
						if(as.getEquipment().getHelmet().getItemMeta().getDisplayName().replace(ChatColor.WHITE + "", "").equalsIgnoreCase(name.toLowerCase()))
						{
							return as;
						}
					}
				}
			}
		}
		
		return null;
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
		
		if(drops && this.drops) {
			if(customDrops != null)
			{
				for(CustomItemStack cis : customDrops) {
					cis.DropNaturally(blockCenter);
				}
				
			}else {
				rootItem.DropNaturally(blockCenter);
			}
		}
		
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
		
		if(drops) {
			ItemStack handItem = event.getPlayer().getEquipment().getItemInMainHand();
			boolean doDrops = true;
			if(requireTool != null)
			{
				if(handItem != null)
				{
					doDrops = false;
					for(String m : requireTool)
					{
						if(handItem.getType().toString().contains(m))
						{
							doDrops = true;
							break;
						}
					}
				}
			}
			
			if(doDrops)
			{
				if(customDrops != null)
				{
					if(useSilkTouch)
					{
						if(handItem != null)
						{
							if(handItem.getItemMeta().hasEnchant(Enchantment.SILK_TOUCH))
							{
								rootItem.DropNaturally(blockCenter);
							}else {
								for(CustomItemStack cis : customDrops) {
									cis.DropNaturally(blockCenter);
								}
							}
						}else {
							for(CustomItemStack cis : customDrops) {
								cis.DropNaturally(blockCenter);
							}
						}
						
					}else {
						for(CustomItemStack cis : customDrops) {
							cis.DropNaturally(blockCenter);
						}
					}
				}else {
					rootItem.DropNaturally(blockCenter);
				}
			}
			

		}
		
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
