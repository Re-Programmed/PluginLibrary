package com.willm.CoreMOD.blocks;

import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.EntityEffect;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Animals;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Cow;
import org.bukkit.entity.Entity;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.willm.ModAPI.Blocks.BlockDirectionData;
import com.willm.ModAPI.Blocks.CustomStates.CustomBaseMaterialRetainingBlock;
import com.willm.ModAPI.Blocks.CustomStates.TickBlock;
import com.willm.ModAPI.Items.CustomItemStack;

public class FeedingTrough extends CustomBaseMaterialRetainingBlock implements TickBlock {

	private static final Random feedingRand = new Random();
	
	public final int troughMid, troughEnd, troughEnd180;
	
	public FeedingTrough(CustomItemStack rootItem, int trough_mid, int trough_end, int trough_end_180) {
		super(rootItem);
		this.Directional = BlockDirectionData.PLAYER_RELATIVE;
		
		troughMid = trough_mid;
		troughEnd = trough_end;
		troughEnd180 = trough_end_180;
		
		this.getRootItem().AddLoreLine(ChatColor.GRAY + "Place and fill with food to");
		this.getRootItem().AddLoreLine(ChatColor.GRAY + "feed nearby animals. ").AddLoreLine(ChatColor.GRAY + "Can also connect to other troughs in a line.");
		this.getRootItem().AddLoreLine(ChatColor.DARK_GREEN + "Uses: Carrot x 24, Potato x 24").AddLoreLine(ChatColor.DARK_GREEN + "Wheat x 24, Beetroot x 24.");

		RegisterAsTickBlock();
	}
	
	@Override
	public ArmorStand Place(Location location) {
		ArmorStand as = super.Place(location);
		
		UpdateTrough(location.getBlock(), true);
		
		return as;
	}
	
	@Override
	public void Place(Location location, BlockFace facing, float addRot) {
		super.Place(location, facing, addRot);
		
		UpdateTrough(location.getBlock(), true);
	}
	
	@Override
	public void Remove(BlockBreakEvent event) {
		ArmorStand as = getMyStand(event.getBlock());
		BlockFace facing = as.getFacing();
		super.Remove(event);
		UpdateTrough(event.getBlock(), true, false, facing, -1);
	}
	
	@Override
	public void Remove(Location location, boolean drops) {
		ArmorStand as = getMyStand(location.getBlock());
		BlockFace facing = as.getFacing();
		super.Remove(location, drops);
		UpdateTrough(location.getBlock(), true, false, facing, -1);
	}
	
	@Override
	public void Remove(Location location) {
		ArmorStand as = getMyStand(location.getBlock());
		BlockFace facing = as.getFacing();
		super.Remove(location);
		UpdateTrough(location.getBlock(), true, false, facing, -1);
	}
	
	public void UpdateTrough(Block b, boolean updateOthers, BlockFace dCheck) {UpdateTrough(b, updateOthers, true, dCheck, 0);}
	public void UpdateTrough(Block b, boolean updateOthers) {UpdateTrough(b, updateOthers, true, null, 0);}
	public boolean UpdateTrough(Block b, boolean updateOthers, boolean updateSelf, BlockFace dCheck, int fillLevel)
	{	
		
		if(!updateSelf)
		{
			if(CheckForCustomBlock(b.getRelative(BlockFace.EAST)))
			{
				UpdateTrough(b.getRelative(BlockFace.EAST), false, true, dCheck, fillLevel);
			}
			
			if(CheckForCustomBlock(b.getRelative(BlockFace.WEST)))
			{
				UpdateTrough(b.getRelative(BlockFace.WEST), false, true, dCheck, fillLevel);
			}
			
			if(CheckForCustomBlock(b.getRelative(BlockFace.SOUTH)))
			{
				UpdateTrough(b.getRelative(BlockFace.SOUTH), false, true, dCheck, fillLevel);
			}
			
			if(CheckForCustomBlock(b.getRelative(BlockFace.NORTH)))
			{
				UpdateTrough(b.getRelative(BlockFace.NORTH), false, true, dCheck, fillLevel);
			}
			
		}
		
		ArmorStand as = getMyStand(b);
		
		if(fillLevel != -1)
		{
			fillLevel = GetFillLevel(as);
		}
		
		BlockFace eastnorth = null;
		BlockFace westsouth = null;
		
		if(dCheck != null && as != null)
		{
			if(as.getFacing() != dCheck)
			{
				return false;
			}
		}

		if(as != null && updateSelf)
		{
			if(as.getFacing() == BlockFace.NORTH)
			{
				eastnorth = (CheckForCustomBlock(b.getRelative(BlockFace.EAST)) && getMyStand(b.getRelative(BlockFace.EAST)).getFacing() == BlockFace.NORTH) ? BlockFace.EAST : null;
				westsouth = (CheckForCustomBlock(b.getRelative(BlockFace.WEST)) && getMyStand(b.getRelative(BlockFace.WEST)).getFacing() == BlockFace.NORTH) ? BlockFace.WEST : null;
			}else if(as.getFacing() == BlockFace.SOUTH)
			{
				eastnorth = (CheckForCustomBlock(b.getRelative(BlockFace.WEST)) && getMyStand(b.getRelative(BlockFace.WEST)).getFacing() == BlockFace.SOUTH) ? BlockFace.WEST : null;
				westsouth = (CheckForCustomBlock(b.getRelative(BlockFace.EAST)) && getMyStand(b.getRelative(BlockFace.EAST)).getFacing() == BlockFace.SOUTH) ? BlockFace.EAST : null;
			}else if(as.getFacing() == BlockFace.EAST)
			{
				eastnorth = (CheckForCustomBlock(b.getRelative(BlockFace.SOUTH)) && getMyStand(b.getRelative(BlockFace.SOUTH)).getFacing() == BlockFace.EAST) ? BlockFace.SOUTH : null;
				westsouth = (CheckForCustomBlock(b.getRelative(BlockFace.NORTH)) && getMyStand(b.getRelative(BlockFace.NORTH)).getFacing() == BlockFace.EAST) ? BlockFace.NORTH : null;
			}else if(as.getFacing() == BlockFace.WEST)
			{
				eastnorth = (CheckForCustomBlock(b.getRelative(BlockFace.NORTH)) && getMyStand(b.getRelative(BlockFace.NORTH)).getFacing() == BlockFace.WEST) ? BlockFace.NORTH : null;
				westsouth = (CheckForCustomBlock(b.getRelative(BlockFace.SOUTH)) && getMyStand(b.getRelative(BlockFace.SOUTH)).getFacing() == BlockFace.WEST) ? BlockFace.SOUTH : null;
			}
		}
		

		boolean updateModel = updateSelf;
		
		
		if(updateOthers)
		{
			if(eastnorth != null)
			{
				if(updateModel)
				{
					updateModel = UpdateTrough(b.getRelative(eastnorth), false, true, as.getFacing(), fillLevel);
				}else {
					UpdateTrough(b.getRelative(westsouth), false, true, as.getFacing(), fillLevel);
				}
			}
			
			if(westsouth != null)
			{
				if(updateModel)
				{
					updateModel = UpdateTrough(b.getRelative(westsouth), false, true, as.getFacing(), fillLevel);
				}else {
					UpdateTrough(b.getRelative(westsouth), false, true, as.getFacing(), fillLevel);
				}
			}
		}
		
		if(updateModel)
		{
			if(eastnorth != null && westsouth != null)
			{
				setModel(b, troughMid);
			}else if(eastnorth != null)
			{			
				setModel(b, troughEnd);
			}else if(westsouth != null)
			{
				setModel(b, troughEnd180);
			}else
			{
				setModel(b, getDisplayCustomModelData());
			}
			
			if(fillLevel != -1)
			{
				SetFillLevel(as, fillLevel);
			}
		}
		
		return true;
	}
	
	public int GetFillLevel(ArmorStand as)
	{
		return (as.getEquipment().getHelmet().getItemMeta().getCustomModelData() % 100) / 10;
	}
	
	public void SetFillLevel(ArmorStand as, int level)
	{
		int cmd = as.getEquipment().getHelmet().getItemMeta().getCustomModelData();
		setModel(as.getLocation().getBlock(), (cmd / 100) * 100 + (cmd % 10) + (level * 10), 0, false);
	}
	
	private void setModel(Block b, int model) {setModel(b, model, 0, true);}
	private void setModel(Block b, int model, float addRotation) {setModel(b, model, addRotation, true);}
	private void setModel(Block b, int model, float addRotation, boolean keepFill)
	{
		ArmorStand myStand = this.getMyStand(b);
		
		myStand.setRotation(myStand.getLocation().getYaw() + addRotation, 0);
		
		int lvl = keepFill ? GetFillLevel(myStand) : 0; //Retain fill level after update.
		
		ItemStack displayItem = this.getRootItem().GetMyItemStack().clone();
		ItemMeta meta = displayItem.getItemMeta();
		meta.setCustomModelData(model);
		displayItem.setItemMeta(meta);
		
		myStand.getEquipment().setHelmet(displayItem);
		
		if(lvl != 0) //Retain fill level after update. Don't set fill if keepFill is false or if the fill level is already 0.
		{
			SetFillLevel(myStand, lvl);
		}
	}

	@Override
	public void Tick(Block b)
	{
		if(feedingRand.nextInt(10001) < 80)
		{
			ArmorStand as = getMyStand(b);
			int fl = GetFillLevel(as);
			if(fl < 1) {return;}
			int i = 0;
			for(Entity e : b.getWorld().getNearbyEntities(b.getLocation(), 8f, 2f, 8f))
			{
				if(fl < 1) {return;}
				if(i > 3) {return;}
				if(e instanceof Animals)
				{
					Animals c = (Animals)e;
					if(c.canBreed() == false || c.isLoveMode() == true) {continue;}
					
					c.playEffect(EntityEffect.LOVE_HEARTS);

					c.setLoveModeTicks(3000);
					
					if(feedingRand.nextInt(101) < 5)
					{
						SetFillLevel(as, (fl--) - 1);
					}
					
					i++;
				}
				
			}
		}
	}

	
}
