package com.willm.CoreMOD.blocks;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Dispenser;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.willm.ModAPI.Blocks.CustomBlock;
import com.willm.ModAPI.Items.BlockCreator;
import com.willm.ModAPI.Items.CustomItemStack;
import com.willm.ModAPI.Items.ItemCreator;

public class ChestLock extends CustomBlock {
	
	public static CustomItemStack CHEST_LOCK;
	public static ChestLock CHEST_LOCK_BLOCK;
	
	public static void RegisterItems()
	{
		CHEST_LOCK = ItemCreator.RegisterNewItem(new CustomItemStack("Chest Lock", Material.POLISHED_GRANITE, 15230))
				.AddLoreLine(ChatColor.BLUE + "Place below a chest and right click to lock.")
				.AddLoreLine(ChatColor.GRAY + "Prevents other players from opening a chest.")
				.AddLoreLine(ChatColor.GRAY + "Use 2 to lock a double chest.");
		CHEST_LOCK_BLOCK = (ChestLock) BlockCreator.RegisterNewBlock(CHEST_LOCK, new ChestLock(CHEST_LOCK));
	}
	public ChestLock(CustomItemStack rootItem) {
		super(rootItem);
		
		SetDrops(false);
	}
	
	@Override
	public void Interact(PlayerInteractEvent event) {
		
		if(event.getPlayer().isSneaking()) {return;}
		
		Dispenser d = (Dispenser)event.getClickedBlock().getState();
		if(d.getInventory().getItem(0) == null)
		{
			
			event.getPlayer().sendMessage(ChatColor.GREEN + "Claimed chest lock.");
			
			ItemStack playerTracker = new ItemStack(Material.DIRT, 1);
			ItemMeta m = playerTracker.getItemMeta();
			m.setDisplayName(event.getPlayer().getName());
			playerTracker.setItemMeta(m);
			
			d.getInventory().setItem(0, playerTracker);
						
		}else {
			if(!d.getInventory().getItem(0).getItemMeta().getDisplayName().equals(event.getPlayer().getName()))
			{
				event.getPlayer().sendMessage(ChatColor.BLUE + d.getInventory().getItem(0).getItemMeta().getDisplayName() + "'s Chest Lock.");
			}
		}
		
		event.setCancelled(true);
	}
	

}
