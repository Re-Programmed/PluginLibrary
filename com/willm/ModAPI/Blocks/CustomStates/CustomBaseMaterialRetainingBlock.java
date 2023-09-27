package com.willm.ModAPI.Blocks.CustomStates;

import org.bukkit.Location;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;

import com.willm.ModAPI.Blocks.CustomBlock;
import com.willm.ModAPI.Items.CustomItemStack;
import com.willm.ModAPI.Items.ItemCreator;

public class CustomBaseMaterialRetainingBlock extends CustomBlock {

	public CustomBaseMaterialRetainingBlock(CustomItemStack rootItem) {
		super(rootItem);
		
		this.mineAs = rootItem.getType();
	}
	
	@Override
	public ArmorStand Place(Location location) {
		location.getBlock().setType(this.mineAs);
		
		ArmorStand displayStand = (ArmorStand)location.getWorld().spawnEntity(new Location(location.getWorld(), location.getX() + 0.5f, location.getY(), location.getZ() + 0.5f), EntityType.ARMOR_STAND);
		
		displayStand.setInvisible(true);
		displayStand.setMarker(true);
		displayStand.setSmall(true);
		
		AddToArmorStand(displayStand);
		
		ItemStack disp = ItemCreator.CreateNamedItemStack(displayMaterial, name, 1);
		ItemCreator.SetItemCustomModelData(disp, getDisplayCustomModelData());
		
		displayStand.getEquipment().setHelmet(disp);
	
		return displayStand;
	}
	
	@Override
	public void Place(Location location, BlockFace facing, float addRot) {
		location.getBlock().setType(this.mineAs);
		
		ArmorStand displayStand = (ArmorStand)location.getWorld().spawnEntity(new Location(location.getWorld(), location.getX() + 0.5f, location.getY(), location.getZ() + 0.5f), EntityType.ARMOR_STAND);
		
		displayStand.setInvisible(true);
		displayStand.setMarker(true);
		displayStand.setSmall(true);
		
		AddToArmorStand(displayStand);
		
		displayStand.setRotation(facing.getModX() * 90f + addRot, 0f);
		
		ItemStack disp = ItemCreator.CreateNamedItemStack(displayMaterial, name, 1);
		ItemCreator.SetItemCustomModelData(disp, getDisplayCustomModelData());
		
		displayStand.getEquipment().setHelmet(disp);
	}
}
