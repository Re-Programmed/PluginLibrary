package com.willm.ModAPI.Blocks;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Chest;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import com.willm.ModAPI.Main;
import com.willm.ModAPI.Utils;

public class Machine implements InventoryHolder {
	protected static final BlockFace[] checkFaces = new BlockFace[] { BlockFace.DOWN, BlockFace.UP, BlockFace.EAST, BlockFace.WEST, BlockFace.SOUTH, BlockFace.NORTH };

	public ArrayList<MachineConversion> conversions = new ArrayList<MachineConversion>();
	public Location location;
	public int size;
	public String name;
	
	public float productSpeed;
	
	String sound_clip;
	
	protected Inventory myInventory;
	
	public Machine SetLocation(Location location)
	{
		this.location = location;
		return this;
	}
	
	public Machine Clone()
	{
		return new Machine(this);
	}
	
	public Machine(Machine m)
	{
		size = m.size;
		name = m.name;
		productSpeed = m.productSpeed;
		sound_clip = m.sound_clip;
		myInventory = Bukkit.createInventory(this, size, name);

		Main.MachineRegistry.add(this);
	}
	
	public Machine(int size, String name, int productSpeed, ArrayList<MachineConversion> conversions)
	{
		this.size = size;
		this.name = name;
		this.productSpeed = ((float)productSpeed)/100f;
		myInventory = Bukkit.createInventory(this, size, name);
		
		this.conversions = conversions;
		
		Main.MachineRegistry.add(this);
	}
	
	public Machine(int size, String name, int productSpeed, ArrayList<MachineConversion> conversions, String sound)
	{
		this.size = size;
		this.name = name;
		this.productSpeed = ((float)productSpeed)/100f;
		myInventory = Bukkit.createInventory(this, size, name);
		
		this.conversions = conversions;
		
		Main.MachineRegistry.add(this);
		sound_clip = sound;
	}

	public void MachineTick()
	{
		if(location != null)
		{
			for(BlockFace bf : checkFaces)
			{
				Block b = location.getBlock().getRelative(bf);
				if(b.getType() == Material.DISPENSER)
				{
					if(Main.MInserter.getRelatedBlock().CheckForCustomBlock(b))
					{
						for(BlockFace bfs : checkFaces)
						{
							Block b2 = b.getRelative(bfs);
							if(b2.getType() == Material.CHEST)
							{
								Chest c = (Chest)b2.getState();
								for(MachineConversion co : conversions)
								{
									if(c.getBlockInventory().containsAtLeast(co.i1, 1))
									{
										c.getBlockInventory().removeItem(co.i1);
										myInventory.addItem(co.i1);
									}
								}
							}
						}
					}
				}
			}
		}
		
		for(ItemStack i : myInventory.getStorageContents())
		{
			if(i == null) {continue;}
			for(MachineConversion c : conversions)
			{
				if(i.getType() == c.i1.getType())
				{
					if(i.hasItemMeta())
					{
						if(c.i1.getItemMeta().hasCustomModelData())
						{
							if(i.getItemMeta().hasCustomModelData())
							{
								if(i.getItemMeta().getCustomModelData() == c.i1.getItemMeta().getCustomModelData())
								{
									if(c.IsDone())
									{
										//Finished
										Finish(c, i);
										return;
									}else {
										c.IncreaseProgress(productSpeed);
									}
									return;
								}else {
									continue;
								}
							}else {
								continue;
							}
						}else {
							if(c.IsDone())
							{
								//Finished
								Finish(c, i);
								return;
							}else {
								c.IncreaseProgress(productSpeed);
							}
						}
					}else {
						if(c.IsDone())
						{
							//Finished
							Finish(c, i);
							return;
						}else {
							c.IncreaseProgress(productSpeed);
						}
					}
				}
			}
		}
	}
	
	private void Finish(MachineConversion c, ItemStack i)
	{
		BlockFace bfs = null;
		for(BlockFace bf : checkFaces)
		{
			Block b = location.getBlock().getRelative(bf);
			if(b.getType() == Material.DISPENSER)
			{
				if(Main.MExtractor.getRelatedBlock().CheckForCustomBlock(b))
				{
					bfs = bf;
					break;
				}
			}
		}
		
		c.ResetProgress();
		ItemStack rem = i.clone();
		rem.setAmount(1);
		myInventory.removeItem(rem);	
		if(bfs == null)
		{
			for(ItemStack is : c.i2)
			{
				myInventory.addItem(is.clone());
			}											
		}else {
			boolean chest = false;
			for(BlockFace bf : checkFaces)
			{
				Block b = location.getBlock().getRelative(bfs).getRelative(bf);
				if(b.getType() == Material.CHEST)
				{
					Chest ch = (Chest)b.getState();
					for(ItemStack is : c.i2)
					{
						ch.getBlockInventory().addItem(is);	
					}
					
					
					chest = true;
				}
			}
			
			if(!chest)
			{
				for(ItemStack is : c.i2)
				{
					location.getWorld().dropItemNaturally(location.getBlock().getRelative(bfs).getLocation(), is);
				}
			}
		}
		if(sound_clip != null && sound_clip != "")
		{
			Utils.PlayCustomSound(sound_clip, location);
		}
		
	}
	
	@Override
	public Inventory getInventory() {
		return myInventory;
	}
	
	public void Interaction(PlayerInteractEvent p)
	{
		p.setCancelled(true);
		p.getPlayer().openInventory(myInventory);
		
	}
	
	public void Destroy(Location location)
	{
		for(ItemStack i : myInventory.getStorageContents())
		{
			if(i == null) {continue;}
			location.getWorld().dropItemNaturally(location, i);
		}
		Main.MachineRegistry.remove(this);
	}
}