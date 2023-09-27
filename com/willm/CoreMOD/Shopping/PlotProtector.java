package com.willm.CoreMOD.Shopping;

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

public class PlotProtector extends CustomBlock {

	public static CustomItemStack PLOT_PROTECTOR;
	public static PlotProtector PLOT_PROTECTOR_BLOCK;
	
	public static void RegisterItems()
	{
		 PLOT_PROTECTOR = ItemCreator.RegisterNewItem(new CustomItemStack("Block Protector", Material.GOLD_BLOCK, 51023)).AddLoreLine(ChatColor.GOLD + "Prevents players from breaking blocks in a 3x3 area.").AddLoreLine(ChatColor.BLUE + "Right Click to claim.");
		 PLOT_PROTECTOR_BLOCK = (PlotProtector) BlockCreator.RegisterNewBlock(PLOT_PROTECTOR, new PlotProtector(PLOT_PROTECTOR));
	}
	
	public PlotProtector(CustomItemStack rootItem) {
		super(rootItem);
		
		SetDrops(false);
	}
	
	@Override
	public void Interact(PlayerInteractEvent event) {
		
		if(event.getPlayer().isSneaking()) {return;}
		
		Dispenser d = (Dispenser)event.getClickedBlock().getState();
		if(d.getInventory().getItem(0) == null)
		{
			event.getPlayer().sendMessage(ChatColor.GREEN + "Claimed block protector.");
			
			ItemStack playerTracker = new ItemStack(Material.DIRT, 1);
			ItemMeta m = playerTracker.getItemMeta();
			m.setDisplayName(event.getPlayer().getName());
			playerTracker.setItemMeta(m);
			
			d.getInventory().setItem(0, playerTracker);
						
		}else {
			if(!d.getInventory().getItem(0).getItemMeta().getDisplayName().equals(event.getPlayer().getName()))
			{
				event.getPlayer().sendMessage(ChatColor.BLUE + d.getInventory().getItem(0).getItemMeta().getDisplayName() + "'s Block Protector.");
			}
		}
		
		event.setCancelled(true);
	}

}
