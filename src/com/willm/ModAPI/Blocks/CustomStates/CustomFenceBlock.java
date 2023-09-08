package com.willm.ModAPI.Blocks.CustomStates;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.type.Fence;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import com.willm.ModAPI.Utils;
import com.willm.ModAPI.Blocks.BlockDirectionData;
import com.willm.ModAPI.Blocks.CustomBlock;
import com.willm.ModAPI.Items.CustomItemStack;
import com.willm.ModAPI.Items.ItemCreator;

public class CustomFenceBlock extends CustomBlock {
	
	private CustomBlock fencePost, sideFence;
	
	private static ArrayList<CustomFenceBlock> customFences = new ArrayList<CustomFenceBlock>();
	
	//ENSURE THAT FENCE POST NAMES EQUAL THE NAME OF THE ROOT BLOCK.
	public CustomFenceBlock(CustomItemStack rootItem, CustomBlock fencePost, CustomBlock sideFence)
	{
		super(rootItem);
		this.mineAs = Material.OAK_FENCE;
		this.Directional = BlockDirectionData.PLAYER_RELATIVE;
		
		this.fencePost = fencePost;
		this.sideFence = sideFence;
		
		customFences.add(this);
	}
	
	@Override
	public ArmorStand Place(Location location)
	{		
		location.getBlock().setType(Material.OAK_FENCE);
		
		return UpdateFence(location, true);
	}
	
	private ArmorStand spawnFencePost(Location location)
	{
		ArmorStand displayStand = (ArmorStand)location.getWorld().spawnEntity(new Location(location.getWorld(), location.getX() + 0.5f, location.getY(), location.getZ() + 0.5f), EntityType.ARMOR_STAND);
		
		displayStand.setInvisible(true);
		displayStand.setMarker(true);
		//sdisplayStand.setFireTicks(999999999);
		displayStand.setSmall(true);
		
		AddToArmorStand(displayStand);
		
		ItemStack disp = ItemCreator.CreateNamedItemStack(fencePost.getDisplayMaterial(), fencePost.getName(), 1);
		ItemCreator.SetItemCustomModelData(disp, fencePost.getDisplayCustomModelData());
				
		displayStand.getEquipment().setHelmet(disp);
		
		return displayStand;
	}
	
	BlockFace[] destroyChecks = new BlockFace[] {BlockFace.EAST, BlockFace.WEST, BlockFace.SOUTH, BlockFace.NORTH};

	public void UpdateFencesOnBreak(Location location)
	{
		
		for(BlockFace face : destroyChecks)
		{
			for(CustomFenceBlock cfb : customFences)
			{
				if(cfb.CheckForCustomBlock(location.getBlock().getRelative(face)))
				{
					cfb.UpdateFence(location.getBlock().getRelative(face).getLocation(), false);
				}
			}
		}
	}
	
	public ArmorStand UpdateFence(Location location, boolean updateAdj)
	{
		Block b = location.getBlock();
		Fence f = (Fence)b.getBlockData();
		
		for(Entity e : b.getWorld().getNearbyEntities(Utils.AddToLocationAsNew(b.getLocation(), 0.5f, 0, 0.5f), 0.2f, 0.2f, 0.2f))
		{
			if(e instanceof ArmorStand)
			{
				ArmorStand as = (ArmorStand)e;
				if(as.getEquipment().getHelmet() != null)
				{
					if(as.getEquipment().getHelmet().getItemMeta().getDisplayName().replace(ChatColor.WHITE + "", "").equalsIgnoreCase(name.toLowerCase()))
					{
						e.remove();
					}
				}
			}
		}
				
		for(BlockFace face : f.getFaces())
		{
			ArmorStand displayStand = (ArmorStand)location.getWorld().spawnEntity(new Location(location.getWorld(), location.getX() + 0.5f, location.getY(), location.getZ() + 0.5f), EntityType.ARMOR_STAND);
			displayStand.setInvisible(true);
			displayStand.setMarker(true);
			//displayStand.setFireTicks(999999999);
			displayStand.setSmall(true);
			
			if(face == BlockFace.NORTH)
			{
				displayStand.setRotation(180f, 0f);
			}else {
				displayStand.setRotation(face.getModX() * -90f, 0f);
			}
			
			ItemStack disp = ItemCreator.CreateNamedItemStack(sideFence.getDisplayMaterial(), sideFence.getName(), 1);
			
			ItemCreator.SetItemCustomModelData(disp, sideFence.getDisplayCustomModelData());
			
			displayStand.getEquipment().setHelmet(disp);
			
			if(updateAdj)
			{
				for(CustomFenceBlock cfb : customFences)
				{
					if(cfb.CheckForCustomBlock(b.getRelative(face)))
					{
						cfb.UpdateFence(b.getRelative(face).getLocation(), false);
					}
				}
			}
		}
		
		return spawnFencePost(b.getLocation());
		
	}
	
	
	@Override
	public void Remove(BlockBreakEvent event) {
				
		boolean first = true;
		for(Entity e : event.getBlock().getWorld().getNearbyEntities(Utils.AddToLocationAsNew(event.getBlock().getLocation(), 0.5f, 0, 0.5f), 0.2f, 0.2f, 0.2f))
		{
			if(first)
			{
				first = false;
				continue;
			}
			if(e instanceof ArmorStand)
			{
				ArmorStand as = (ArmorStand)e;
				if(as.getEquipment().getHelmet() != null)
				{
					if(as.getEquipment().getHelmet().getItemMeta().getDisplayName().replace(ChatColor.WHITE + "", "").equalsIgnoreCase(name.toLowerCase()))
					{
						e.remove();
					}
				}
			}
		}
		
		super.Remove(event);
		
		event.getBlock().setType(Material.AIR);
		
		UpdateFencesOnBreak(event.getBlock().getLocation());
	}
	
	@Override
	public void Remove(Location location) {
		
		
		boolean first = true;
		for(Entity e : location.getBlock().getWorld().getNearbyEntities(Utils.AddToLocationAsNew(location, 0.5f, 0, 0.5f), 0.2f, 0.2f, 0.2f))
		{
			if(first)
			{
				first = false;
				continue;
			}
			if(e instanceof ArmorStand)
			{
				ArmorStand as = (ArmorStand)e;
				if(as.getEquipment().getHelmet() != null)
				{
					if(as.getEquipment().getHelmet().getItemMeta().getDisplayName().replace(ChatColor.WHITE + "", "").equalsIgnoreCase(name.toLowerCase()))
					{
						e.remove();
					}
				}
			}
		}
		
		super.Remove(location);
		
		location.getBlock().setType(Material.AIR);
		
		UpdateFencesOnBreak(location);
	}
	
	@Override
	public boolean CheckForCustomBlock(Block block) {
		return super.CheckForCustomBlock(block);
	}
	
	@Override
	public void Place(Location location, BlockFace facing, float addRot)
	{	
		location.getBlock().setType(Material.OAK_FENCE);
		
		
		UpdateFence(location, true);
	}
	

	
	
}
