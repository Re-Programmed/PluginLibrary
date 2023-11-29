package com.willm.CoreMOD.blocks;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.ArmorStand;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.willm.CoreMOD.MyItems;
import com.willm.ModAPI.Blocks.CustomStates.CustomBaseMaterialRetainingBlock;
import com.willm.ModAPI.Items.BlockCreator;
import com.willm.ModAPI.Items.CustomItemStack;
import com.willm.ModAPI.Items.ItemCreator;

public class CompostPile extends CustomBaseMaterialRetainingBlock {
	
	public static CompostPile INSTANCE;
	
	public int BASE_CMD = 15231;
		
	protected CustomItemStack GetDropItem() {return MyItems.sludge;}
	
	protected int GetMaxFill() {return 12;}
	
	public static void RegisterItem()
	{
		CustomItemStack item = ItemCreator.RegisterNewItem(new CustomItemStack("Compost Pile", Material.WARPED_TRAPDOOR, 15231));
		INSTANCE = new CompostPile(item);
		BlockCreator.RegisterNewBlock(item, INSTANCE);
	}
	
	@Override
	public void Interact(PlayerInteractEvent event) {
		event.setCancelled(true);
		
		ItemStack item = event.getPlayer().getEquipment().getItemInMainHand();
		if(item != null)
		{
			if(GetDropItem().CheckForCustomItem(item))
			{
				if(AddToPile(event.getClickedBlock()))
				{
					if(event.getItem().getAmount() == 1)
					{
						event.getPlayer().getEquipment().setItemInMainHand(null);
					}else {
						ItemStack less1 = event.getItem();
						less1.setAmount(less1.getAmount() - 1);
						event.getPlayer().getEquipment().setItemInMainHand(less1);
					}
				}
				return;
			}
		}
		
		BreakPile(event.getClickedBlock());
	}
	
	@Override
	public void Remove(BlockBreakEvent event) {
		BreakPile(event.getBlock());
		event.setCancelled(true);
	}
	
	public CompostPile(CustomItemStack rootItem)
	{
		super(rootItem);
		
		this.SetDrops(false);
	}
	
	public int GetPileLevel(Block b)
	{
		ArmorStand as = this.GetMyStand(b);
		
		ItemStack helmet = as.getEquipment().getHelmet();
		ItemMeta im = helmet.getItemMeta();
		
		int pileLevel = im.getCustomModelData() - BASE_CMD;
		
		return pileLevel;
	}
	
	public void BreakPile(Block b)
	{
		if(GetPileLevel(b) == 0)
		{
			super.Remove(b.getLocation(), false);
			ItemStack drop = GetDropItem().GetAmountClone(1);
			b.getWorld().dropItem(b.getLocation(), drop);
			return;
		}
		
		AddToPile(b, -1);
		
		ItemStack drop = GetDropItem().GetAmountClone(1);
		b.getWorld().dropItem(b.getLocation(), drop);
	}
	
	public boolean AddToPile(Block b) {return AddToPile(b, 1);}
	public boolean AddToPile(Block b, int amnt)
	{
		ArmorStand as = this.GetMyStand(b);
		
		ItemStack helmet = as.getEquipment().getHelmet();
		ItemMeta im = helmet.getItemMeta();
		
		int pileLevel = im.getCustomModelData() - BASE_CMD;
		
		pileLevel += amnt;

		if(pileLevel >= GetMaxFill())
		{
			return false;
		}
				
		im.setCustomModelData(BASE_CMD + pileLevel);
		helmet.setItemMeta(im);
		as.getEquipment().setHelmet(helmet);
		return true;
	}
}
