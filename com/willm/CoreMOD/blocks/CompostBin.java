package com.willm.CoreMOD.blocks;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Hopper;
import org.bukkit.block.data.Directional;
import org.bukkit.entity.ArmorStand;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.willm.CoreMOD.MyItems;
import com.willm.ModAPI.Blocks.BlockDirectionData;
import com.willm.ModAPI.Blocks.CustomStates.CustomBaseMaterialRetainingBlock;
import com.willm.ModAPI.Blocks.CustomStates.TickBlock;
import com.willm.ModAPI.Items.BlockCreator;
import com.willm.ModAPI.Items.CustomItemStack;
import com.willm.ModAPI.Items.ItemCreator;
import com.willm.ModAPI.Items.Recipes.RecipeBuilder;

public class CompostBin extends CustomBaseMaterialRetainingBlock implements TickBlock {
	
	private static int DEFAULT_FILL = 12661;
	public static CompostBin BASE_BIN;
	
	public static void RegisterItem()
	{
		CustomItemStack item = ItemCreator.RegisterNewItem(new CustomItemStack("Compost Bin", Material.WARPED_TRAPDOOR, DEFAULT_FILL));
		
		item.getRecipe(2, "PCP", "PPP", "L L").AddMaterial('C', Material.COMPOSTER).AddMaterial('L', RecipeBuilder.ItemStackInput(MyItems.enforced_strut)).AddMaterial('P', RecipeBuilder.MultiMaterialInput(Material.OAK_SLAB, Material.BIRCH_SLAB, Material.SPRUCE_SLAB, Material.JUNGLE_SLAB, Material.DARK_OAK_SLAB, Material.BAMBOO_SLAB, Material.MANGROVE_SLAB, Material.CHERRY_SLAB, Material.WARPED_SLAB, Material.CRIMSON_SLAB, Material.ACACIA_SLAB)).Finalize();
		
		BASE_BIN = new CompostBin(item);
		BlockCreator.RegisterNewBlock(item, BASE_BIN);
	}
	
	public CompostBin(CustomItemStack rootItem)
	{
		super(rootItem);
		this.Directional = BlockDirectionData.PLAYER_RELATIVE;
		this.SetDrops(false);

		RegisterAsTickBlock();
	}
	
	@Override
	public void Interact(PlayerInteractEvent event) {
		super.Interact(event);
		
		if(event.getItem() != null)
		{
			if(MyItems.parishables.containsKey(event.getItem().getType()))
			{				
				if(event.getItem().getAmount() == 1)
				{
					event.getPlayer().getEquipment().setItemInMainHand(null);
				}else {
					ItemStack less1 = event.getItem();
					less1.setAmount(less1.getAmount() - 1);
					event.getPlayer().getEquipment().setItemInMainHand(less1);
				}
				
				addCompostLevel(event.getClickedBlock());
			}
		}
				
		event.setCancelled(true);
	}
	
	public void OnPlace(BlockPlaceEvent event)
	{
		ItemStack item = event.getPlayer().getEquipment().getItemInMainHand();
		if(item.getItemMeta().hasLore())
		{
			String fLore = item.getItemMeta().getLore().get(item.getItemMeta().getLore().size() - 1);
			if(fLore.contains(FILL_LORE_PREFIX))
			{
				int fillLevel = Integer.parseInt(fLore.replace(FILL_LORE_PREFIX, ""));
				
				addCompostLevel(event.getBlock(), fillLevel);
			}
		}
	}
	
	private static String FILL_LORE_PREFIX = ChatColor.GREEN + "Level: ";
	
	@Override
	public void Remove(BlockBreakEvent event) {
		doDrop(event.getBlock());
		super.Remove(event);
	}
	
	@Override
	public void Remove(Location location)
	{
		doDrop(location.getBlock());
		super.Remove(location);
	}
	
	@Override
	public void Remove(Location location, boolean drops) {
		if(drops)
		{
			doDrop(location.getBlock());
		}
		super.Remove(location, false);
	}
	
	private void doDrop(Block b)
	{
		ItemStack drop = this.getRootItem().GetAmountClone(1);
		List<String> lore = drop.getItemMeta().hasLore() ? drop.getItemMeta().getLore() : new ArrayList<String>();
		int FillLevelSet = getFillLevel(b)-1;
		lore.add(FILL_LORE_PREFIX + (getFillLevel(b)-1));
		ItemMeta im = drop.getItemMeta();
		im.setLore(lore);
		
		//im.setCustomModelData(DEFAULT_FILL + FillLevelSet);
		
		drop.setItemMeta(im);
		
		b.getWorld().dropItemNaturally(b.getLocation(), drop);
	}
	
	@Override
	public void Tick(Block b)
	{
		int rand = new Random().nextInt(10001);
		
		if(rand <= 65)
		{
			if(b.getRelative(BlockFace.UP).getType() == Material.HOPPER)
			{
				Hopper h = (Hopper)b.getRelative(BlockFace.UP).getState();
				Directional d = (Directional)h.getBlock().getBlockData();
				if(d.getFacing() == BlockFace.DOWN)
				{
					for(Material c : MyItems.parishables.keySet())
					{
						if(h.getInventory().contains(c, 1))
						{
							if(addCompostLevel(b))
							{
								for(ItemStack is : h.getInventory())
								{
									if(is.getType() == c)
									{
										if(is.getAmount() == 1)
										{
											h.getInventory().remove(is);
										}else {
											is.setAmount(is.getAmount() - 1);
										}
										break;
									}
								}
							}
							
							break;
						}
					}
				}
			}
		}
		
		if(rand <= 14)
		{			
			if(!(getFillLevel(b) == 1))
			{
				addCompostLevel(b, -1);

				ArmorStand as = this.getMyStand(b);
				BlockFace facing = as.getFacing();
				
				Block compostPile = b.getRelative(facing);
				
				if(b.getRelative(BlockFace.DOWN).getType() == Material.HOPPER)
				{
					Hopper h = (Hopper)b.getRelative(BlockFace.DOWN).getState();
					h.getInventory().addItem(MyItems.sludge.GetAmountClone(1));
					return;
				}
				
				if(compostPile.getType() == Material.AIR)
				{
					CompostPile.INSTANCE.Place(compostPile.getLocation());
					return;
				}
				
				if(CompostPile.INSTANCE.CheckForCustomBlock(compostPile))
				{
					if(CompostPile.INSTANCE.AddToPile(compostPile))
					{
						return;
					}
				}
				
				b.getWorld().dropItemNaturally(b.getLocation(), MyItems.sludge.GetAmountClone(1));
			}
			
		}
	}
	
	private int getFillLevel(Block b)
	{
		ArmorStand as = this.getMyStand(b);
		return as.getEquipment().getHelmet().getItemMeta().getCustomModelData() - DEFAULT_FILL + 1;
	}
	
	private boolean addCompostLevel(Block b) {return addCompostLevel(b, 1);}
	private boolean addCompostLevel(Block b, int amnt)
	{
		ArmorStand as = this.getMyStand(b);
		int cmd = as.getEquipment().getHelmet().getItemMeta().getCustomModelData();
		int fillLevel = cmd - DEFAULT_FILL + 1;
		
		if(fillLevel < 10 || amnt < 0)
		{
			ItemStack is = as.getEquipment().getHelmet();
			ItemMeta im = is.getItemMeta();
			im.setCustomModelData(cmd + amnt);
			is.setItemMeta(im);
			as.getEquipment().setHelmet(is);
			return true;
		}
		
		return false;
	}
}
