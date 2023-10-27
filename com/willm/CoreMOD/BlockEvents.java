package com.willm.CoreMOD;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Chest;
import org.bukkit.block.Dispenser;
import org.bukkit.block.data.type.TrapDoor;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.WanderingTrader;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.MerchantRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import com.willm.CoreMOD.Shopping.Currency;
import com.willm.CoreMOD.Shopping.PlotProtector;
import com.willm.CoreMOD.Shopping.ShopBlock;
import com.willm.CoreMOD.blocks.ChestLock;
import com.willm.CoreMOD.blocks.FeedingTrough;
import com.willm.CoreMOD.blocks.Workbench;
import com.willm.CoreMOD.blocks.WorkbenchCraftingInventory;
import com.willm.CoreMOD.blocks.WorkbenchSystem;
import com.willm.CoreMOD.blocks.WorkbenchType;
import com.willm.ModAPI.Utils;
import com.willm.ModAPI.Blocks.CustomBlock;
import com.willm.ModAPI.Enchantments.CustomEnchantment;
import com.willm.ModAPI.Items.CustomItemStack;

public class BlockEvents implements Listener {

	public static HashMap<ItemStack, ItemStack> CuttingBoardRecipes = new HashMap<ItemStack, ItemStack>();
	private static Random randomInteractables = new Random();
	
	public static ArrayList<Workbench> Workbenches = new ArrayList<Workbench>();
	
	public BlockEvents()
	{
		InitMerchantRecipes();
	}
	
	@EventHandler
	public void ImportPack(PlayerJoinEvent event)
	{
		event.getPlayer().setResourcePack("https://drive.google.com/uc?export=download&id=1XgWVqLBBOBcxbI0-LTy92Cuh0HlU11Tb");
	}
	
	@EventHandler
	public void ChestLock(PlayerInteractEvent event)
	{
		if(event.getAction() == Action.RIGHT_CLICK_BLOCK)
		{
			if(event.getClickedBlock().getType() == Material.CHEST)
			{
				if(com.willm.CoreMOD.blocks.ChestLock.CHEST_LOCK_BLOCK.CheckForCustomBlock(event.getClickedBlock().getRelative(BlockFace.DOWN)))
				{
					Dispenser d = (Dispenser)event.getClickedBlock().getRelative(BlockFace.DOWN).getState();
					if(d.getInventory().getItem(0) == null || d.getInventory().getItem(0).getItemMeta().getDisplayName().equals(event.getPlayer().getName()))
					{
						return;
					}
					
					event.setCancelled(true);
					event.getPlayer().sendMessage(ChatColor.RED + "This chest is locked by " + d.getInventory().getItem(0).getItemMeta().getDisplayName() + ".");
				}
			}
		}
	}
	
	@EventHandler
	public void BreakChestLock(BlockBreakEvent event)
	{
		if(event.getBlock().getType() == Material.CHEST)
		{
			if(com.willm.CoreMOD.blocks.ChestLock.CHEST_LOCK_BLOCK.CheckForCustomBlock(event.getBlock().getRelative(BlockFace.DOWN)))
			{
				Dispenser d = (Dispenser)event.getBlock().getRelative(BlockFace.DOWN).getState();
				if(d.getInventory().getItem(0) == null || d.getInventory().getItem(0).getItemMeta().getDisplayName().equals(event.getPlayer().getName()))
				{
					return;
				}
				
				event.setCancelled(true);
				event.getPlayer().sendMessage(ChatColor.RED + "This chest is locked by " + d.getInventory().getItem(0).getItemMeta().getDisplayName() + ".");
			}
			
			return;
		}
		
		if(com.willm.CoreMOD.blocks.ChestLock.CHEST_LOCK_BLOCK.CheckForCustomBlock(event.getBlock()))
		{
			Dispenser d = (Dispenser)event.getBlock().getState();
			if(d.getInventory().getItem(0) == null || d.getInventory().getItem(0).getItemMeta().getDisplayName().equals(event.getPlayer().getName()))
			{
				event.setCancelled(true);
				ChestLock.CHEST_LOCK_BLOCK.Remove(event.getBlock().getLocation());

				event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(), ChestLock.CHEST_LOCK.GetMyItemStack());
				return;
			}
			
			event.setCancelled(true);
			event.getPlayer().sendMessage(ChatColor.RED + "This chest lock is owned by " + d.getInventory().getItem(0).getItemMeta().getDisplayName() + ".");
			ChestLock.CHEST_LOCK_BLOCK.Place(event.getBlock().getLocation());
		}
	}
	
	BlockFace[] blockProtectionLocations = new BlockFace[] { BlockFace.UP, BlockFace.DOWN, BlockFace.EAST, BlockFace.WEST, BlockFace.NORTH, BlockFace.SOUTH, BlockFace.NORTH_EAST, BlockFace.NORTH_WEST, BlockFace.SOUTH_EAST, BlockFace.SOUTH_WEST};
	@EventHandler
	public void PlotProtector(BlockBreakEvent event)
	{
		if(PlotProtector.PLOT_PROTECTOR_BLOCK.CheckForCustomBlock(event.getBlock()))
		{
			Dispenser d = (Dispenser)event.getBlock().getState();
			
			if(d.getInventory().getItem(0) == null || d.getInventory().getItem(0).getItemMeta().getDisplayName().equals(event.getPlayer().getName()))
			{
				event.setCancelled(true);
				PlotProtector.PLOT_PROTECTOR_BLOCK.Remove(event.getBlock().getLocation());
				event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(), PlotProtector.PLOT_PROTECTOR.GetMyItemStack()).setPickupDelay(0);
				
				return;
			}
			
			event.setCancelled(true);
			event.getPlayer().sendMessage(ChatColor.RED + "That block is protected by " + d.getInventory().getItem(0).getItemMeta().getDisplayName() + ".");
			
			PlotProtector.PLOT_PROTECTOR_BLOCK.Place(event.getBlock().getLocation());
			return;
		}
		
		for(BlockFace bf : blockProtectionLocations)
		{

			if(event.getBlock().getRelative(bf).getType() == Material.DISPENSER && com.willm.CoreMOD.Shopping.PlotProtector.PLOT_PROTECTOR_BLOCK.CheckForCustomBlock(event.getBlock().getRelative(bf)))
			{
				Dispenser d = (Dispenser)event.getBlock().getRelative(bf).getState();
				if(d.getInventory().getItem(0).getItemMeta().getDisplayName().equals(event.getPlayer().getName()))
				{
					continue;
				}
				
				event.setCancelled(true);
				event.getPlayer().sendMessage(ChatColor.RED + "That block is protected by " + d.getInventory().getItem(0).getItemMeta().getDisplayName() + ".");
				return;
			} else if(event.getBlock().getRelative(BlockFace.UP).getRelative(bf).getType() == Material.DISPENSER && com.willm.CoreMOD.Shopping.PlotProtector.PLOT_PROTECTOR_BLOCK.CheckForCustomBlock(event.getBlock().getRelative(BlockFace.UP).getRelative(bf)))
			{
				Dispenser d = (Dispenser)event.getBlock().getRelative(BlockFace.UP).getRelative(bf).getState();
				if(d.getInventory().getItem(0).getItemMeta().getDisplayName().equals(event.getPlayer().getName()))
				{
					continue;
				}
				
				event.setCancelled(true);
				event.getPlayer().sendMessage(ChatColor.RED + "That block is protected by " + d.getInventory().getItem(0).getItemMeta().getDisplayName() + ".");
				return;
			} else if(event.getBlock().getRelative(BlockFace.DOWN).getRelative(bf).getType() == Material.DISPENSER && com.willm.CoreMOD.Shopping.PlotProtector.PLOT_PROTECTOR_BLOCK.CheckForCustomBlock(event.getBlock().getRelative(BlockFace.DOWN).getRelative(bf)))
			{
				Dispenser d = (Dispenser)event.getBlock().getRelative(BlockFace.DOWN).getRelative(bf).getState();
				if(d.getInventory().getItem(0).getItemMeta().getDisplayName().equals(event.getPlayer().getName()))
				{
					continue;
				}
				
				event.setCancelled(true);
				event.getPlayer().sendMessage(ChatColor.RED + "That block is protected by " + d.getInventory().getItem(0).getItemMeta().getDisplayName() + ".");
				return;
			}
		}
	}
	
	@EventHandler
	public void WorkbenchInventoryHandling(InventoryClickEvent event)
	{
		if(!(event.getWhoClicked() instanceof Player)) {return;}
		
		if(event.getClickedInventory() == null) {return;}
		
		if(event.getClickedInventory().getSize() == 54)
		{
			ItemStack i = event.getClickedInventory().getItem(53);
			if(i != null)
			{
				if(i.hasItemMeta())
				{
					if(i.getItemMeta().hasDisplayName())
					{
						boolean craftingBench = i.getItemMeta().getDisplayName().equals(WorkbenchCraftingInventory.GetName());
						if(craftingBench || i.getItemMeta().getDisplayName().equals(WorkbenchSystem.GetName()))
						{
							event.setCancelled(true);
							
							if(craftingBench)
							{
								if(event.getSlot() < 57-21) {return;}
							}
							
							ItemStack cli = event.getClickedInventory().getItem(event.getSlot());
							if(cli == null || !cli.hasItemMeta() || !cli.getItemMeta().hasLore()) {return;}
							
							List<String> lore = cli.getItemMeta().getLore();
							
							String[] locDecode = lore.get(0).replace(ChatColor.DARK_GRAY + "", "").split(" ");
							Location location = new Location(Bukkit.getWorld(locDecode[0]), Integer.parseInt(locDecode[1]), Integer.parseInt(locDecode[2]), Integer.parseInt(locDecode[3]));
						
							WorkbenchType typeDecode = WorkbenchType.valueOf(lore.get(1).replace(ChatColor.DARK_GRAY + "", ""));
							typeDecode.RelativeBench.OnInteract(location.getBlock(), (Player)event.getWhoClicked());
						}
					}
				}
			}
		}
	}

	static List<MerchantRecipe> customMerchantRecipes = new ArrayList<MerchantRecipe>();
	
	public static void InitMerchantRecipes()
	{
		MerchantRecipe mr = new MerchantRecipe(Currency.coin.GetAmountClone(3), 5);
		mr.addIngredient(new ItemStack(Material.CARROT, 28));
		customMerchantRecipes.add(mr);
		
		mr = new MerchantRecipe(Currency.coin.GetAmountClone(3), 5);
		mr.addIngredient(new ItemStack(Material.POTATO, 28));
		customMerchantRecipes.add(mr);
		
		mr = new MerchantRecipe(Currency.coin.GetAmountClone(3), 5);
		mr.addIngredient(new ItemStack(Material.WHEAT, 28));
		customMerchantRecipes.add(mr);
		
		mr = new MerchantRecipe(Currency.coin_stack.GetAmountClone(3), 2);
		mr.addIngredient(new ItemStack(Material.EMERALD, 12));
		customMerchantRecipes.add(mr);
		
		mr = new MerchantRecipe(Currency.bill.GetAmountClone(3), 1);
		mr.addIngredient(new ItemStack(Material.NETHERITE_INGOT, 1));
		customMerchantRecipes.add(mr);
		
		mr = new MerchantRecipe(Currency.coin.GetAmountClone(16), 2);
		mr.addIngredient(MyItems.iron_dish.GetAmountClone(3));
		customMerchantRecipes.add(mr);
		
		mr = new MerchantRecipe(Currency.coin.GetAmountClone(32), 2);
		mr.addIngredient(MyItems.tungsten_block.GetAmountClone(2));
		customMerchantRecipes.add(mr);
		
		mr = new MerchantRecipe(Currency.coin.GetAmountClone(32), 2);
		mr.addIngredient(MyItems.platinum_block.GetAmountClone(2));
		customMerchantRecipes.add(mr);
		
		mr = new MerchantRecipe(Currency.coin_stack.GetAmountClone(20), 2);
		mr.addIngredient(MyItems.res_fuel.GetAmountClone(1));
		customMerchantRecipes.add(mr);
		
		mr = new MerchantRecipe(Currency.coin.GetAmountClone(6), 2);
		mr.addIngredient(MyItems.sludge.GetAmountClone(10));
		customMerchantRecipes.add(mr);

		mr = new MerchantRecipe(Currency.coin_stack.GetAmountClone(6), 1);
		mr.addIngredient(MyItems.steel_enforced_gray_concrete.GetAmountClone(6));
		customMerchantRecipes.add(mr);
	}
	
	public static MerchantRecipe GetRandomMerchantRecipe()
	{
		return customMerchantRecipes.get((int)(Math.random() * customMerchantRecipes.size()));
	}
	
	@EventHandler
	public void AddWanderingTraderFeatures(EntitySpawnEvent event)
	{
		if(event.getEntityType() == EntityType.WANDERING_TRADER)
		{
			WanderingTrader wt = (WanderingTrader)event.getEntity();
			wt.setRemoveWhenFarAway(false);
			int recipes = 0;
		
			
			while(recipes < wt.getRecipeCount()) {
				MerchantRecipe mr = GetRandomMerchantRecipe();
				wt.setRecipe(recipes++, mr);
			}
		}
	}
	
	@EventHandler
	public void BreakStore(BlockBreakEvent event)
	{
		if(event.getBlock().getType() == Material.CHEST) {
			if(ShopBlock.SHOP_BLOCK.CheckForCustomBlock(event.getBlock().getRelative(BlockFace.DOWN)))
			{
				Chest c = (Chest)event.getBlock().getState();
				
				if(c.getInventory().getItem(26) != null)
				{
					String owner = c.getInventory().getItem(26).getItemMeta().getLore().get(c.getInventory().getItem(26).getItemMeta().getLore().size() - 1).replace(STORE_OWNER_PREFIX, "");
					
					if(!event.getPlayer().getName().equals(owner))
					{
						event.setCancelled(true);
						event.getPlayer().sendMessage(ChatColor.RED + "You do not own this store.");
						return;
					}
				}
				
				c.getInventory().setItem(26, null);
			}
		}else if(event.getBlock().getType() == Material.DISPENSER)
		{
			if(ShopBlock.SHOP_BLOCK.CheckForCustomBlock(event.getBlock()))
			{
				if(event.getBlock().getRelative(BlockFace.UP).getType() == Material.CHEST)
				{
					Chest c = (Chest)event.getBlock().getRelative(BlockFace.UP).getState();
					
					if(c.getInventory().getItem(26) != null)
					{
						String owner = c.getInventory().getItem(26).getItemMeta().getLore().get(c.getInventory().getItem(26).getItemMeta().getLore().size() - 1).replace(STORE_OWNER_PREFIX, "");
						
						if(!event.getPlayer().getName().equals(owner))
						{
							event.setCancelled(true);
							event.getPlayer().sendMessage(ChatColor.RED + "You do not own this store.");
							
							ShopBlock.SHOP_BLOCK.Place(event.getBlock().getLocation());
							
							return;
						}
					}
					
					c.getInventory().setItem(26, null);
					event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(), ShopBlock.SHOP_BLOCK_ITEM.GetMyItemStack());
				}else {
					event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(), ShopBlock.SHOP_BLOCK_ITEM.GetMyItemStack());
				}
			}
		}
	}
	
	public static final String STORE_OWNER_PREFIX = ChatColor.BLUE + "Store Owner: ";
	
	@EventHandler
	public void ShopInventoryHandling(InventoryClickEvent event)
	{
		if(event.getClickedInventory() == null) {return;}
		
		if(event.getClickedInventory().getType() == InventoryType.CHEST)
		{
			
			if(event.getClickedInventory().getLocation() != null)
			{
				if(ShopBlock.SHOP_BLOCK.CheckForCustomBlock(event.getClickedInventory().getLocation().getBlock().getRelative(BlockFace.DOWN)))
				{
					
					if(event.getSlot() == 26) {
						
						event.setCancelled(true);return;
						}
					ItemStack storePrice = event.getClickedInventory().getItem(26);
					
					if(storePrice != null)
					{
						String owner = storePrice.getItemMeta().getLore().get(storePrice.getItemMeta().getLore().size() - 1).replace(STORE_OWNER_PREFIX, "");
						
						if(!event.getWhoClicked().getName().equals(owner))
						{
							if(event.getClick() != ClickType.LEFT) {event.setCancelled(true);return;}

							ItemStack clickedItem = event.getClickedInventory().getItem(event.getSlot());
							if(clickedItem != null)
							{
								CustomItemStack storePriceIsCustom = null;
								for(CustomItemStack cis : com.willm.ModAPI.Main.CustomItemRegistry)
								{
									if(cis.CheckForCustomItem(storePrice))
									{
										storePriceIsCustom = cis;
										if(cis.CheckForCustomItem(clickedItem))
										{
											event.setCancelled(true);
											return;
										}
										
										break;
									}
								}
								
								if(storePriceIsCustom == null)
								{
									if(clickedItem.getType() == storePrice.getType())
									{
										event.setCancelled(true);
										return;
									}
								}
								
								ItemStack cursor = event.getWhoClicked().getItemOnCursor();
								if(cursor != null)
								{
									if(storePriceIsCustom != null)
									{
										if(storePriceIsCustom.CheckForCustomItem(cursor))
										{
											if(storePrice.getAmount() == cursor.getAmount())
											{
												return;
											}
										}
									}else {
										if(storePrice.getType() == cursor.getType())
										{
											if(storePrice.getAmount() == cursor.getAmount())
											{
												return;
											}
										}
									}
									
								}
								
								
							}
							event.setCancelled(true);

						}
					}else {
						event.getWhoClicked().sendMessage(ChatColor.RED + "This store has no price set.");
						event.setCancelled(true);
						return;
					}
				}
			}
			
		}
		
		if(event.getClickedInventory().getSize() == 27)
		{
			if(event.getClickedInventory().getItem(26) != null)
			{
				if(ShopBlock.SHOP_BLOCK_ITEM.CheckForCustomItem(event.getClickedInventory().getItem(26)))
				{					
					if(event.getClick() != ClickType.LEFT) {event.setCancelled(true);return;}
					ItemStack clicked = event.getClickedInventory().getItem(event.getSlot());
					if(clicked != null)
					{
						List<String> lore = event.getClickedInventory().getItem(26).getItemMeta().getLore();
						
						String[] locDecode = lore.get(0).replace(ChatColor.DARK_GRAY + "Linked Store: ", "").split(" ");
						Location location = new Location(Bukkit.getWorld(locDecode[0]), Integer.parseInt(locDecode[1]), Integer.parseInt(locDecode[2]), Integer.parseInt(locDecode[3]));
						
						if(location.getBlock().getRelative(BlockFace.UP).getType() == Material.CHEST)
						{
							Chest c = (Chest)location.getBlock().getRelative(BlockFace.UP).getState();
							
							if(c.getInventory().getItem(26) != null)
							{
								String owner = c.getInventory().getItem(26).getItemMeta().getLore().get(c.getInventory().getItem(26).getItemMeta().getLore().size() - 1).replace(STORE_OWNER_PREFIX, "");
								
								if(!event.getWhoClicked().getName().equals(owner))
								{
									event.setCancelled(true);
									event.getWhoClicked().sendMessage(ChatColor.RED + "You do not own this store.");
									return;
								}
							}
						}
						
						if(clicked.getItemMeta().getDisplayName().contains("Set Price"))
						{
							event.setCancelled(true);
							if(event.getWhoClicked().getItemOnCursor() != null)
							{
								
							
								if(location.getBlock().getState() instanceof Chest)
								{
									Chest c = (Chest)location.getBlock().getState();
									
									ItemStack price = event.getWhoClicked().getItemOnCursor().clone();
									ItemMeta meta = price.getItemMeta();
									List<String> lorelist = meta.hasLore() ? meta.getLore() : new ArrayList<String>();
									lorelist.add(STORE_OWNER_PREFIX + event.getWhoClicked().getName());
									meta.setLore(lorelist);
									meta.setDisplayName(ChatColor.GREEN + "Price");
									price.setItemMeta(meta);
									
									c.getInventory().setItem(26, price);
									
									event.getWhoClicked().sendMessage(ChatColor.GREEN + "Updated the price of this store.");
								}
							}
						}else if(clicked.getItemMeta().getDisplayName().contains("Retrieve Profits"))
						{
							event.setCancelled(true);
							if(location.getBlock().getState() instanceof Chest)
							{
								Chest c = (Chest)location.getBlock().getState();
							
								ItemStack price = c.getInventory().getItem(26);
								if(price != null)
								{
									CustomItemStack storePriceIsCustom = null;
									for(CustomItemStack cis : com.willm.ModAPI.Main.CustomItemRegistry)
									{
										if(cis.CheckForCustomItem(price))
										{
											storePriceIsCustom = cis;
											break;
										}
									}
									
									List<ItemStack> removes = new ArrayList<ItemStack>();
									for(ItemStack is : c.getInventory().getContents())
									{
										if(is == null) {continue;}

										if(is.hasItemMeta())
										{
											if(is.getItemMeta().hasDisplayName())
											{
												if(is.getItemMeta().getDisplayName().contains(ChatColor.GREEN + "Price"))
												{
													break;
												}
											}
										}
										
										if(storePriceIsCustom != null)
										{
											if(storePriceIsCustom.CheckForCustomItem(is))
											{
												removes.add(is);
												event.getWhoClicked().getWorld().dropItem(event.getWhoClicked().getLocation(), is.clone()).setPickupDelay(0);
											}
										}else if(price.getType() == is.getType()) {
											removes.add(is);
											event.getWhoClicked().getWorld().dropItem(event.getWhoClicked().getLocation(), is.clone()).setPickupDelay(0);
										}
										
									}
									
									c.getInventory().removeItem(removes.toArray(new ItemStack[0]));
								}
							}
						}
						
						if(event.getSlot() == 26) {event.setCancelled(true);}
					}
				
				}
			}
		}
	}
	
	@EventHandler
	public void WorkbenchAndShopHandling(PlayerInteractEvent event)
	{
		if(event.getAction() != Action.RIGHT_CLICK_BLOCK) {return;}
		for(Workbench w : Workbenches)
		{
			if(w.CheckForCustomBlock(event.getClickedBlock()))
			{
				if(event.getPlayer().isSneaking()) {
					TrapDoor trapDoor = (TrapDoor)event.getClickedBlock().getBlockData();
					trapDoor.setOpen(false);
					event.getClickedBlock().setBlockData(trapDoor);
					return;
				}
				event.setCancelled(true);
				w.OnInteract(event);
				return;
			}
		}
		
		if(ShopBlock.SHOP_BLOCK.CheckForCustomBlock(event.getClickedBlock()))
		{
			if(event.getPlayer().isSneaking()) {
					return;
			}
			event.setCancelled(true);
			ShopBlock.SHOP_BLOCK.OnInteract(event);
		}
	}
	
	
	static final List<Material> troughMats = List.of(Material.CARROT, Material.POTATO, Material.WHEAT, Material.BEETROOT);
	@EventHandler
	public void TroughHandling(PlayerInteractEvent event)
	{
		if(event.getAction() == Action.RIGHT_CLICK_BLOCK)
		{
			if(MyItems.manual_feeding_trough.getRelatedBlock().CheckForCustomBlock(event.getClickedBlock()))
			{
				event.setCancelled(true);
				
				if(event.getItem() != null) {
					if(troughMats.contains(event.getItem().getType()))
					{
						if(event.getItem().getAmount() >= 32)
						{
							FeedingTrough ft = (FeedingTrough)MyItems.manual_feeding_trough.getRelatedBlock();
							ArmorStand as = ft.GetMyStand(event.getClickedBlock());
							int lvl = ft.GetFillLevel(as);
							if(lvl < 2)
							{
								ft.SetFillLevel(as, lvl + 1);
								
								RemoveOrSubtractFromPlayersHand(event.getPlayer(), 32);
							}
						}
					}
				}
				
				
			}
		}
	}
	
	//DISABLE LOG STRIPPING
	@EventHandler
	public void PlayerStripEvent(PlayerInteractEvent event)
	{
		if(event.getAction() == Action.RIGHT_CLICK_BLOCK)
		{
			if(event.getItem() == null) {return;}
			if(event.getItem().getType().toString().contains("AXE") && !event.getItem().getType().toString().contains("PICKAXE"))
			{
				if(event.getClickedBlock().getType().toString().contains("LOG") || event.getClickedBlock().getType().toString().contains("WOOD") || event.getClickedBlock().getType().toString().contains("STEM") || event.getClickedBlock().getType().toString().contains("HYPHAE"))
				{
					event.setCancelled(true);
				}
			}
		}
	}
	
	@EventHandler
	public void UseInteractableMachine(PlayerInteractEvent event)
	{	
		if(event.getAction() == Action.RIGHT_CLICK_BLOCK)
		{
			if(MyItems.fridge.getRelatedBlock().CheckForCustomBlock(event.getClickedBlock()))
			{
				Dispenser d = (Dispenser)event.getClickedBlock().getState();
				String save = d.getLock();
				
				d.setCustomName("Fridge");
				
				d.setLock("");
				d.update(true);
				event.getPlayer().openInventory(d.getInventory());
				d.setLock(save);
				d.update(true);
			}
			
			if(MyItems.cooling_unit.getRelatedBlock().CheckForCustomBlock(event.getClickedBlock()))
			{
				for(Entity e : event.getClickedBlock().getWorld().getNearbyEntities(event.getClickedBlock().getLocation(), 5f, 5f, 5f))
				{
					if(e instanceof LivingEntity)
					{
						LivingEntity le = (LivingEntity)e;
						le.setFreezeTicks(8 * 20);
					}
				}
				
				Utils.PlayCustomSound("core_mod.steam", event.getClickedBlock().getLocation());
				event.getClickedBlock().getWorld().playSound(event.getClickedBlock().getLocation(), Sound.ENTITY_PLAYER_SPLASH, 1.0f, 1.0f);
				event.getClickedBlock().getWorld().spawnParticle(Particle.WATER_SPLASH, event.getClickedBlock().getLocation().getX(), event.getClickedBlock().getLocation().getY() + 1, event.getClickedBlock().getLocation().getZ(), 50, 1, 0.5d, 0.5d, 0.5d);

				for(int x = -2; x < 2; x++)
				{
					for(int y = -2; y < 2; y++)
					{
						for(int z = -2; z < 2; z++)
						{
							Location loc = new Location(event.getClickedBlock().getWorld(), x, y, z);
							loc.add(event.getClickedBlock().getLocation());
							
							if(loc.getBlock().getType() == Material.WATER)
							{
								loc.getBlock().setType(Material.ICE);
								loc.getWorld().spawnParticle(Particle.WATER_SPLASH, loc.getX(), loc.getY() + 1, loc.getZ(), 50, 1, 0.5d, 0.5d, 0.5d);
								loc.getWorld().playSound(loc, Sound.ENTITY_PLAYER_SPLASH, 1.0f, 1.0f);
							}
						}
					}
				}
			}
			
			if(MyItems.tree_tap.getRelatedBlock().CheckForCustomBlock(event.getClickedBlock()))
			{
				if(event.getClickedBlock().getRelative(BlockFace.DOWN).getType().toString().contains("LOG"))
				{
					event.getClickedBlock().getWorld().dropItemNaturally(event.getClickedBlock().getLocation(), MyItems.sap.GetAmountClone(randomInteractables.nextInt(10) + 5));
					event.getClickedBlock().getRelative(BlockFace.DOWN).setType(Material.AIR);
					event.getClickedBlock().getWorld().spawnParticle(Particle.DRIPPING_HONEY, event.getClickedBlock().getRelative(BlockFace.DOWN).getLocation(), 40, 1d, 1d, 1d);
					MyItems.tree_tap.getRelatedBlock().Remove(event.getClickedBlock().getLocation());
				}
			}
			
			ItemStack hi = event.getPlayer().getEquipment().getItemInMainHand();
			if(hi != null && hi.getType() != Material.AIR)
			{
				
				
				if(MyItems.cutting_board.getRelatedBlock().CheckForCustomBlock(event.getClickedBlock()))
				{
					for(Entry<ItemStack, ItemStack> entry : CuttingBoardRecipes.entrySet())
					{
						if(entry.getKey().getType() == hi.getType())
						{
							if(entry.getKey().getItemMeta().hasCustomModelData())
							{
								if(hi.getItemMeta().hasCustomModelData())
								{
									if(entry.getKey().getItemMeta().getCustomModelData() == hi.getItemMeta().getCustomModelData())
									{
										RemoveOrSubtractFromPlayersHand(event.getPlayer(), 1);
										event.getClickedBlock().getWorld().dropItemNaturally(event.getClickedBlock().getLocation(), entry.getValue());
										return;
									}
								}
							}else {
								if(!hi.getItemMeta().hasCustomModelData())
								{
									RemoveOrSubtractFromPlayersHand(event.getPlayer(), 1);
									event.getClickedBlock().getWorld().dropItemNaturally(event.getClickedBlock().getLocation(), entry.getValue());
									return;
								}
							}
						}
					}
					event.setCancelled(true);
				}
			}
		}
	}
	
	@EventHandler
	public void BreakPanOven(BlockBreakEvent event)
	{
		if(MyItems.pan_oven.getRelatedBlock().CheckForCustomBlock(event.getBlock()) || MyItems.oven_red.getRelatedBlock().CheckForCustomBlock(event.getBlock()) || MyItems.oven_blue.getRelatedBlock().CheckForCustomBlock(event.getBlock()) || MyItems.oven_tan.getRelatedBlock().CheckForCustomBlock(event.getBlock()))
		{
			event.setDropItems(false);
			event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(), MyItems.oven.GetMyItemStack());
			event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(), MyItems.pan.GetMyItemStack());
		}
	}
	
	@EventHandler
	public void PlaceOnOven(PlayerInteractEvent event)
	{
		
		
		if(event.getAction() == Action.RIGHT_CLICK_BLOCK)
		{
			ItemStack hi = event.getPlayer().getEquipment().getItemInMainHand();
			if(hi != null && hi.getType() != Material.AIR)
			{
				
				if(hi.getType() == Material.BOWL)
				{
					if(MyItems.oven_red.getRelatedBlock().CheckForCustomBlock(event.getClickedBlock()) || MyItems.oven_blue.getRelatedBlock().CheckForCustomBlock(event.getClickedBlock()) || MyItems.oven_tan.getRelatedBlock().CheckForCustomBlock(event.getClickedBlock()))
					{
						event.getClickedBlock().getWorld().playSound(event.getClickedBlock().getLocation(), Sound.ENTITY_ITEM_PICKUP, 1, 1);

							boolean isPlate = MyItems.wooden_plate.CheckForCustomItem(hi);
							Dispenser d = (Dispenser)event.getClickedBlock().getState();

							for(OvenRecipe or : ovenRecipes)
							{
								List<ItemStack> customRemoves = new ArrayList<ItemStack>();
								boolean check_b = true;
								
								if(or.usePlate != isPlate) {check_b = false;}
								
								for(ItemStack check : or.inputs)
								{
									
									if(check.hasItemMeta() && check.getItemMeta().hasCustomModelData())
									{
										boolean found = false;
										for(ItemStack is : d.getInventory().getContents())
										{
											if(is == null) {continue;}
											
							
											
											if(is.getType() == check.getType() && is.hasItemMeta())
											{
												if(is.getItemMeta().hasCustomModelData())
												{
													if(is.getItemMeta().getCustomModelData() == check.getItemMeta().getCustomModelData())
													{
														customRemoves.add(is);
														found = true;
														break;
													}
												}
											}
										}
										
										if(!found)
										{
											check_b = false;
											break;
										}
										
										
									}else {
										if(!d.getInventory().containsAtLeast(check, check.getAmount()))
										{
											check_b = false;
											break;
										}
									}
									
									
								}
								
								if(check_b)
								{
									event.getClickedBlock().getWorld().dropItemNaturally(event.getClickedBlock().getLocation(), or.out);
									RemoveOrSubtractFromPlayersHand(event.getPlayer(), 1);
									d.getInventory().removeItem(or.inputs);
									
									if(customRemoves.size() > 0) {d.getInventory().removeItem(customRemoves.toArray(new ItemStack[0]));}
								 	
									if(d.getInventory().isEmpty())
									{
										MyItems.oven_red.getRelatedBlock().Remove(event.getClickedBlock().getLocation());
										MyItems.pan_oven.getRelatedBlock().Place(event.getClickedBlock().getLocation());
									}
								}
							}
						
					}
				}else {
					

						if(MyItems.oven.getRelatedBlock().CheckForCustomBlock(event.getClickedBlock()))
						{
							event.getClickedBlock().getWorld().playSound(event.getClickedBlock().getLocation(), Sound.BLOCK_FIRE_EXTINGUISH, 1, 1);

							if(MyItems.pan.CheckForCustomItem(hi))
							{
								event.getPlayer().getEquipment().setItemInMainHand(null);
								
								MyItems.oven.getRelatedBlock().Remove(event.getClickedBlock().getLocation());
								MyItems.pan_oven.getRelatedBlock().Place(event.getClickedBlock().getLocation());		
							}
						}
						
						if(MyItems.pan_oven.getRelatedBlock().CheckForCustomBlock(event.getClickedBlock()) || MyItems.oven_red.getRelatedBlock().CheckForCustomBlock(event.getClickedBlock()) || MyItems.oven_blue.getRelatedBlock().CheckForCustomBlock(event.getClickedBlock()) || MyItems.oven_tan.getRelatedBlock().CheckForCustomBlock(event.getClickedBlock()))
						{
							event.getClickedBlock().getWorld().playSound(event.getClickedBlock().getLocation(), Sound.BLOCK_FIRE_EXTINGUISH, 1, 1);

							//ITEMS THAT MAKE OVEN TURN RED
							if((hi.getType() == Material.COOKED_BEEF && !hi.getItemMeta().hasCustomModelData()) || MyItems.tomato.CheckForCustomItem(hi) || MyItems.bacon.CheckForCustomItem(hi) || MyItems.tomato_sauce.CheckForCustomItem(hi) || MyItems.spare_rib.CheckForCustomItem(hi) || MyItems.rib_eye.CheckForCustomItem(hi) || MyItems.bbq_sauce_smoky.CheckForCustomItem(hi) || MyItems.beef_broth.CheckForCustomItem(hi))
							{
								SetOven(event, hi, MyItems.oven_red.getRelatedBlock());
							}
							
							//ITEMS THAT MAKE OVEN TURN BLUE
							if((hi.getType() == Material.WATER_BUCKET && !hi.getItemMeta().hasCustomModelData()) || MyItems.syrup.CheckForCustomItem(hi) || MyItems.sap.CheckForCustomItem(hi) || MyItems.cider_vinegar.CheckForCustomItem(hi) || MyItems.chopped_carrots.CheckForCustomItem(hi) || MyItems.pepper_sack.CheckForCustomItem(hi))
							{
								SetOven(event, hi, MyItems.oven_blue.getRelatedBlock());
							}
							
							//ITEMS THAT MAKE OVEN TURN TAN
							if(MyItems.spaghetti.CheckForCustomItem(hi) || MyItems.lamb.CheckForCustomItem(hi) || MyItems.ham.CheckForCustomItem(hi) || MyItems.potato_slices.CheckForCustomItem(hi) || MyItems.cooking_oil.CheckForCustomItem(hi))
							{
								SetOven(event, hi, MyItems.oven_tan.getRelatedBlock());
							}
						}
					}
				
			
			}
				
			
		}
	}
	
	private void SetOven(PlayerInteractEvent event, ItemStack hi, CustomBlock cb)
	{
		RemoveOrSubtractFromPlayersHand(event.getPlayer(), 1);
		
		Dispenser d1 = (Dispenser)event.getClickedBlock().getState();
		
		ItemStack[] c1 = d1.getInventory().getContents();
		
		MyItems.pan_oven.getRelatedBlock().Remove(event.getClickedBlock().getLocation());	
		cb.Place(event.getClickedBlock().getLocation());
		
		Dispenser d = (Dispenser)event.getClickedBlock().getState();
		
		d.getInventory().setContents(c1);
		
		ItemStack add = hi.clone();
		add.setAmount(1);
		d.getInventory().addItem(add);
	}
	
	public static ArrayList<OvenRecipe> ovenRecipes = new ArrayList<OvenRecipe>();
	
	private void RemoveOrSubtractFromPlayersHand(Player p, int amount)
	{
		ItemStack i = p.getEquipment().getItemInMainHand();
		if(i != null)
		{
			if(i.getAmount() > amount)
			{
				i.setAmount(i.getAmount() - amount);
			}else {
				p.getEquipment().setItemInMainHand(null);
			}
		}
	}
	
	@EventHandler
	public void PlaceDoor(BlockPlaceEvent event)
	{
		for(CustomItemStack cis : MyItems.doors.keySet())
		{
			if(cis.CheckForCustomItem(event.getItemInHand()))
			{
				MyItems.doors.get(cis).getRelatedBlock().Place(Utils.AddToLocationAsNew(event.getBlock().getLocation(), 0, 1, 0));
				return;
			}
		}
	}
	
	@EventHandler
	public void BreakDoor(BlockBreakEvent event)
	{
		for(CustomItemStack cis : MyItems.doors.keySet())
		{
			if(cis.getRelatedBlock().CheckForCustomBlock(event.getBlock()))
			{
				cis.getRelatedBlock().Remove(Utils.AddToLocationAsNew(event.getBlock().getLocation(), 0.5f, 1.5f, 0.5f).getBlock().getLocation());
				return;
			}
			
			if(MyItems.doors.get(cis).getRelatedBlock().CheckForCustomBlock(event.getBlock()))
			{
				Location loc = Utils.AddToLocationAsNew(event.getBlock().getLocation(), 0.5f, -1f, 0.5f).getBlock().getLocation();
				MyItems.doors.get(cis).getRelatedBlock().Remove(loc);
				
				cis.DropNaturally(loc);
				
				return;
			}
		}
		
		
	}
	
	public static float elevator_cooldown = 0f;
	ArrayList<Location> moved_elvs = new ArrayList<Location>();
	
	@EventHandler
	public void CheckElevator(PlayerInteractEvent event)
	{		
		if(event.getPlayer().isSneaking())
		{
			if(event.getClickedBlock() != null)
			{
				if(MyItems.elevator.getRelatedBlock().CheckForCustomBlock(event.getClickedBlock()))
				{
					if(event.getPlayer().getEquipment().getItemInMainHand().getType().isBlock())
					{
						for(Entity e : event.getClickedBlock().getWorld().getNearbyEntities(event.getClickedBlock().getLocation(), 2f, 1f, 2f))
						{
							if(e.getType() == EntityType.ARMOR_STAND)
							{
								ArmorStand as = (ArmorStand)e;
								
								if(MyItems.elevator.CheckForCustomItem(as.getEquipment().getHelmet()))
								{
									as.getEquipment().setHelmet(event.getPlayer().getEquipment().getItemInMainHand());
								}
							}
						}
						event.setCancelled(true);
					}
				}
			}
			return;
		}
		if(elevator_cooldown < 1f)
		{
			if(event.getAction() == Action.RIGHT_CLICK_BLOCK)
			{
				if(event.getClickedBlock() != null)
				{
					for(Entity e : event.getClickedBlock().getWorld().getNearbyEntities(Utils.AddToLocationAsNew(event.getClickedBlock().getLocation(), 0, 1, 0), 5f, 1f, 5f))
					{
						if(e.getType() == EntityType.ARMOR_STAND)
						{
							ArmorStand as = (ArmorStand)e;
							
							if(MyItems.elevator_shaft.CheckForCustomItem(as.getEquipment().getHelmet()))
							{
								moved_elvs.clear();
								CheckMotionElevator(event.getClickedBlock(), false);
							}
						}
					}
				}
			}
		}
	}
	
	@EventHandler
	public void PlayerSneakElevatorDown(PlayerMoveEvent event)
	{
		if(elevator_cooldown < 1f)
		{
			if(event.getPlayer().isSneaking())
			{
				Location loc = Utils.AddToLocationAsNew(event.getPlayer().getLocation(), 0f, -0.7f, 0f);
				if(loc.getBlock().getType() == Material.DISPENSER)
				{
					for(Entity e : loc.getWorld().getNearbyEntities(Utils.AddToLocationAsNew(loc.getBlock().getLocation(), 0, -1, 0), 5f, 1f, 5f))
					{
						if(e.getType() == EntityType.ARMOR_STAND)
						{
							ArmorStand as = (ArmorStand)e;
							
							if(MyItems.elevator_shaft.CheckForCustomItem(as.getEquipment().getHelmet()))
							{
								moved_elvs.clear();
								CheckMotionElevator(loc.getBlock(), true);
							}
						}
					}
				}
			}
		}
	}
	
	void CheckMotionElevator(Block b, boolean sneaking)
	{
		if(MyItems.elevator.getRelatedBlock().CheckForCustomBlock(b))
		{
			
			elevator_cooldown = 5f;
			
			Location mov = Utils.AddToLocationAsNew(b.getLocation(), 0, sneaking ? -1f : 1, 0);
			if(mov.getBlock().getType() != Material.AIR) {return;}
			
			ItemStack i_replace = MyItems.elevator.GetMyItemStack();
			
			CustomBlock elevatorBlock = MyItems.elevator.getRelatedBlock();
			for(Entity e : mov.getWorld().getNearbyEntities(b.getLocation(), 1f, 1f, 1f))
			{
				if(e.getType() == EntityType.ARMOR_STAND)
				{
					ArmorStand as = (ArmorStand)e;
					
					if(!MyItems.elevator_shaft.CheckForCustomItem(as.getEquipment().getHelmet()))
					{
						i_replace = as.getEquipment().getHelmet();
						break;
					}
				}
			}
			
			elevatorBlock.Place(mov.getBlock().getLocation()).getEquipment().setHelmet(i_replace);
			elevatorBlock.Remove(b.getLocation());
			
			moved_elvs.add(b.getLocation());
			
			if(!sneaking)
			{
	
				for(Entity e : mov.getWorld().getNearbyEntities(Utils.AddToLocationAsNew(b.getLocation(), 0, 1, 0), 1f, 1f, 1f))
				{
					if(e.getType() != EntityType.ARMOR_STAND)
					{
						Location loc = Utils.AddToLocationAsNew(e.getLocation(), 0, 1, 0);
						Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "tp " + e.getUniqueId() + " " + Utils.LocationToString(loc) + " " + e.getLocation().getYaw() + " " + e.getLocation().getPitch());
					}
				}
			
			}
			
			for(Location loc : sneaking ? loc_check_elv_d : loc_check_elv_u)
			{
				Location newlocation = Utils.AddToLocationAsNew(b.getLocation(), loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
				if(!moved_elvs.contains(newlocation.getBlock().getLocation()))
				{
					CheckMotionElevator(newlocation.getBlock(), sneaking);
				}
			}
		}
	}
	
	Location[] loc_check_elv_u = new Location[] {
			new Location(null, 1f, 0f, 0f),	
			new Location(null, 0f, 0f, 1f),	
			new Location(null, -1f, 0f, 0f),	
			new Location(null, 0f, 0f, -1f),
			new Location(null, 0f, -1f, 0f)
	};
	
	Location[] loc_check_elv_d = new Location[] {
			new Location(null, 1f, 0f, 0f),	
			new Location(null, 0f, 0f, 1f),	
			new Location(null, -1f, 0f, 0f),	
			new Location(null, 0f, 0f, -1f),
			new Location(null, 0f, 1f, 0f),
	};
	
	Location[] loc_check_door = new Location[] {
			new Location(null, 1f, 0f, 0f),	
			new Location(null, 0f, 0f, 1f),	
			new Location(null, -1f, 0f, 0f),	
			new Location(null, 0f, 0f, -1f)
	};
	
	@EventHandler
	public void OpenCloseDoor(PlayerInteractEvent event)
	{
		if(event.getClickedBlock() == null) {return;}
		CheckOpenCloseDoor(event.getPlayer(), event.getAction(), event.getClickedBlock());
		
		for(Location loc : loc_check_door)
		{
			Location n_loc = Utils.AddToLocationAsNew(event.getClickedBlock().getLocation(), loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
			CheckOpenCloseDoor(event.getPlayer(), event.getAction(), n_loc.getBlock());
		}
	}
	
	public void CheckOpenCloseDoor(Player player, Action action, Block clickedBlock)
	{
		if(player.isSneaking()) {return;}
		if(action == Action.RIGHT_CLICK_BLOCK)
		{
			if(clickedBlock != null)
			{
				for(CustomItemStack cis : MyItems.doors.keySet())
				{
					if(cis.getRelatedBlock().CheckForCustomBlock(clickedBlock))
					{
						if(Utils.AddToLocationAsNew(clickedBlock.getLocation(), 0.5f, -2f, 0.5f).getBlock().getType() == Material.AIR && Utils.AddToLocationAsNew(clickedBlock.getLocation(), 0.5f, -1f, 0.5f).getBlock().getType() == Material.AIR)
						{
							cis.getRelatedBlock().Remove(clickedBlock.getLocation());
							cis.getRelatedBlock().Remove(Utils.AddToLocationAsNew(clickedBlock.getLocation(), 0.5f, 1.5f, 0.5f).getBlock().getLocation());
							
							cis.getRelatedBlock().Place(Utils.AddToLocationAsNew(clickedBlock.getLocation(), 0.5f, -2f, 0.5f).getBlock().getLocation());
							MyItems.doors.get(cis).getRelatedBlock().Place(Utils.AddToLocationAsNew(clickedBlock.getLocation(), 0.5f, -1f, 0.5f).getBlock().getLocation());
							
							Utils.PlayCustomSound("core_mod.door_open", clickedBlock.getLocation());
						}
					}
					
					if(MyItems.doors.get(cis).getRelatedBlock().CheckForCustomBlock(clickedBlock))
					{
						if(Utils.AddToLocationAsNew(clickedBlock.getLocation(), 0.5f, 2f, 0.5f).getBlock().getType() == Material.AIR && Utils.AddToLocationAsNew(clickedBlock.getLocation(), 0.5f, 1f, 0.5f).getBlock().getType() == Material.AIR)
						{
							cis.getRelatedBlock().Remove(clickedBlock.getLocation());
							cis.getRelatedBlock().Remove(Utils.AddToLocationAsNew(clickedBlock.getLocation(), 0.5f, -1f, 0.5f).getBlock().getLocation());
							
							MyItems.doors.get(cis).getRelatedBlock().Place(Utils.AddToLocationAsNew(clickedBlock.getLocation(), 0.5f, 2f, 0.5f).getBlock().getLocation());
							cis.getRelatedBlock().Place(Utils.AddToLocationAsNew(clickedBlock.getLocation(), 0.5f, 1f, 0.5f).getBlock().getLocation());
							
							Utils.PlayCustomSound("core_mod.door_close", clickedBlock.getLocation());
						}
					}
				}
			}
		}
	}
}
