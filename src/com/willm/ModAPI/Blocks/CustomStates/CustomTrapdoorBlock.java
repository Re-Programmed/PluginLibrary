package com.willm.ModAPI.Blocks.CustomStates;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Dispenser;
import org.bukkit.block.data.Bisected;
import org.bukkit.block.data.Bisected.Half;
import org.bukkit.block.data.Directional;
import org.bukkit.block.data.type.TrapDoor;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;

import com.willm.ModAPI.Utils;
import com.willm.ModAPI.Blocks.BlockDirectionData;
import com.willm.ModAPI.Blocks.CustomBlock;
import com.willm.ModAPI.Items.CustomItemStack;
import com.willm.ModAPI.Items.ItemCreator;

public class CustomTrapdoorBlock extends CustomBlock {

	private CustomBlock openModel;
	public int GetOpenCMD()
	{
		return openModel.getDisplayCustomModelData();
	}
	
	private CustomBlock topModel;
	
	public static ArrayList<CustomTrapdoorBlock> Trapdoors = new ArrayList<CustomTrapdoorBlock>();
	
	public CustomTrapdoorBlock(CustomItemStack rootItem, CustomBlock openModel, CustomBlock topModel) {
		super(rootItem);
		
		this.openModel = openModel;
		this.topModel = topModel;
		
		this.mineAs = Material.WARPED_TRAPDOOR;
		this.constBlock = false;
		this.Directional = BlockDirectionData.PLAYER_RELATIVE;
		
		Trapdoors.add(this);
	}

	
	@Override
	public void Place(Location location, BlockFace facing, float addRot)
	{

		ArmorStand displayStand = (ArmorStand)location.getWorld().spawnEntity(new Location(location.getWorld(), location.getX() + 0.5f, location.getY(), location.getZ() + 0.5f), EntityType.ARMOR_STAND);
		
		displayStand.setInvisible(true);
		displayStand.setMarker(true);
		displayStand.setSmall(true);
		
		Directional d = (Directional)location.getBlock().getBlockData();
		d.setFacing(facing.getOppositeFace());
		location.getBlock().setBlockData(d);
		
		displayStand.setRotation(facing.getModX() * 90f + addRot, 0f);
		
		AddToArmorStand(displayStand);
		
		ItemStack disp = ItemCreator.CreateNamedItemStack(displayMaterial, name, 1);
		ItemCreator.SetItemCustomModelData(disp, getDisplayCustomModelData());
		displayStand.getEquipment().setHelmet(disp);

		UpdateState(location.getBlock(), true);	}
	
	@Override
	public ArmorStand Place(Location location) {
		
		
		

		ArmorStand displayStand = (ArmorStand)location.getWorld().spawnEntity(new Location(location.getWorld(), location.getX() + 0.5f, location.getY(), location.getZ() + 0.5f), EntityType.ARMOR_STAND);
		
		displayStand.setInvisible(true);
		displayStand.setMarker(true);
		displayStand.setSmall(true);
		
		AddToArmorStand(displayStand);
		
		ItemStack disp = ItemCreator.CreateNamedItemStack(displayMaterial, name, 1);
		ItemCreator.SetItemCustomModelData(disp, getDisplayCustomModelData());
		displayStand.getEquipment().setHelmet(disp);
				
		UpdateState(location.getBlock(), true);		
		return displayStand;
	}
	
	public void UpdateState(Block block, boolean open)
	{
		ArmorStand as = getMyStand(block);
		
		ItemStack disp = ItemCreator.CreateNamedItemStack(open ? openModel.getDisplayMaterial() : displayMaterial, name, 1);
		
		if(!open)
		{
			TrapDoor t = (TrapDoor)block.getBlockData();
			if(t.getHalf() == Half.TOP)
			{
				ItemCreator.SetItemCustomModelData(disp, topModel.getDisplayCustomModelData());
			}else {
				ItemCreator.SetItemCustomModelData(disp, getDisplayCustomModelData());
			}
		}else {
			ItemCreator.SetItemCustomModelData(disp, openModel.getDisplayCustomModelData());
		}
		
		as.getEquipment().setHelmet(disp);
	}
	
}
