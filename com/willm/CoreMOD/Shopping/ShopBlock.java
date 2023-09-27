package com.willm.CoreMOD.Shopping;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Chest;
import org.bukkit.event.player.PlayerInteractEvent;

import com.willm.CoreMOD.BlockEvents;
import com.willm.ModAPI.Blocks.CustomBlock;
import com.willm.ModAPI.Items.CustomItemStack;
import com.willm.ModAPI.Items.ItemCreator;

public class ShopBlock extends CustomBlock {
	
	public static final CustomItemStack SHOP_BLOCK_ITEM = ItemCreator.RegisterNewItem(new CustomItemStack("Shop", Material.DIAMOND_BLOCK, 16023)).AddLoreLine(ChatColor.GREEN + "Place below a chest to create a shop.");
	public static final ShopBlock SHOP_BLOCK = new ShopBlock(SHOP_BLOCK_ITEM);

	public ShopBlock(CustomItemStack rootItem) {
		super(rootItem);
		
		SetDrops(false);
		
	}

	public void OnInteract(PlayerInteractEvent event)
	{
		if(event.getClickedBlock().getRelative(BlockFace.UP).getType() != Material.CHEST) {
			event.getPlayer().sendMessage(ChatColor.RED + "There must be a chest placed above this store.");
			return;
			}
		
		//Check for owner of store.
		Chest c = (Chest)event.getClickedBlock().getRelative(BlockFace.UP).getState();
				
		if(c.getInventory().getItem(26) != null) {
			String owner = c.getInventory().getItem(26).getItemMeta().getLore().get(c.getInventory().getItem(26).getItemMeta().getLore().size() - 1).replace(BlockEvents.STORE_OWNER_PREFIX, "");
			
			if(!event.getPlayer().getName().equals(owner))
			{
				event.setCancelled(true);
				event.getPlayer().sendMessage(ChatColor.RED + "You do not own this store.");
				return;
			}
		}
		
		
		
		ShopBlockUI ui = new ShopBlockUI();
		ui.MenuSelection(event.getClickedBlock().getRelative(BlockFace.UP).getLocation());
		
		event.getPlayer().openInventory(ui.getInventory());
	}
	
}
