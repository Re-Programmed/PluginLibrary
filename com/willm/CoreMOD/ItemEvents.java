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
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.inventory.InventoryType.SlotType;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import com.willm.CoreMOD.ElementalItems.Nonmetals;
import com.willm.CoreMOD.ElementalItems.RegisterElementalItems;
import com.willm.CoreMOD.Power.Injector;
import com.willm.CoreMOD.Shopping.Currency;
import com.willm.ModAPI.CustomDeathMessages;
import com.willm.ModAPI.Utils;
import com.willm.ModAPI.Blocks.CustomBlock;
import com.willm.ModAPI.Items.CustomItemStack;
import com.willm.ModAPI.Terrain.CustomPopulator;
import com.willm.ModAPI.Terrain.Ore;

public class ItemEvents implements Listener {

	@EventHandler
	public void Playerpack(PlayerJoinEvent event)
	{
		event.getPlayer().setResourcePack("https://drive.google.com/uc?export=download&id=1ecEkSCyXQxyMRS6yeUoB2W9Cn_5P3qln");
	}
	
	private static String toTitleCase(String givenString) {
	    String[] arr = givenString.split(" ");
	    StringBuffer sb = new StringBuffer();

	    for (int i = 0; i < arr.length; i++) {
	        sb.append(Character.toUpperCase(arr[i].charAt(0)))
	            .append(arr[i].substring(1)).append(" ");
	    }          
	    return sb.toString().trim();
	}  
	
	public static ArrayList<Location> wireRedstoneActiveLinks = new ArrayList<Location>();
	
	//List of all open IceBox Inventories.
	private static HashMap<Inventory, ItemStack> iceBoxInventories = new HashMap<Inventory, ItemStack>();
	
	//When the player opens an ice box inventory.
	@EventHandler
	public void IceBoxHandling(PlayerInteractEvent event)
	{
		if(event.getItem() != null)
		{
			if(MyItems.ice_box.CheckForCustomItem(event.getItem()))
			{
				Inventory iceBoxInventory = Bukkit.createInventory(event.getPlayer(), 9, ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "Ice Box");
				event.getPlayer().openInventory(iceBoxInventory);
				
				if(event.getItem().hasItemMeta())
				{
					if(event.getItem().getItemMeta().hasLore())
					{
					outerloop:
						for(String s : event.getItem().getItemMeta().getLore())
						{
							boolean cured = false;
							if(s.startsWith(ChatColor.WHITE + "Cured "))
							{
								cured = true;
								s = s.substring(new String(ChatColor.WHITE + "Cured ").length());
							}
							
							boolean sv_splitVal = s.split(" x ")[1].contains(" (");
							int amount = Integer.parseInt(sv_splitVal ? s.split(" x ")[1].substring(0, s.split(" x ")[1].indexOf(" (")) : s.split(" x ")[1]);
							String nameType = s.split(" x ")[0].replace(ChatColor.WHITE + "", "");
							
							int age = 0;
							
							if(sv_splitVal)
							{
								String parseAge = s.substring(s.indexOf(" ("));
								parseAge = parseAge.substring(0, parseAge.indexOf("/"));
								parseAge = parseAge.substring(2, parseAge.length());
								
								age = Integer.parseInt(parseAge);
							}
							
							for(CustomItemStack cis : com.willm.ModAPI.Main.CustomItemRegistry)
							{
								if(cis.getName().equalsIgnoreCase(nameType.toLowerCase()))
								{
									ItemStack is = cis.GetAmountClone(amount);
									if(cured)
									{
										ItemMeta im = is.getItemMeta();
										List<String> lore = im.hasLore() ? im.getLore() : new ArrayList<String>();
										lore.add(CURED_TEXT);
										im.setLore(lore);
										is.setItemMeta(im);
									}
									
									Main.RotItem(is, age);
									Main.RotItem(is, age);
									iceBoxInventory.addItem(is);
									continue outerloop;
								}
							}
							
							ItemStack is = new ItemStack(Material.valueOf(nameType.toUpperCase().replace(' ', '_')), amount);
							if(cured)
							{
								ItemMeta im = is.getItemMeta();
								List<String> lore = im.hasLore() ? im.getLore() : new ArrayList<String>();
								lore.add(CURED_TEXT);
								im.setLore(lore);
								is.setItemMeta(im);
							}
							
							Main.RotItem(is, age);
							Main.RotItem(is, age);
							iceBoxInventory.addItem(is);
						}
					}
				}
				
				iceBoxInventories.put(iceBoxInventory, event.getPlayer().getEquipment().getItemInMainHand());
			}
		}
	}
	
	@EventHandler
	public void PlayerConsumeBaguette(PlayerInteractEvent event)
	{
		if(event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK)
		{
			if(event.getItem() != null)
			{
				if(MyItems.baguette_b2.CheckForCustomItem(event.getItem()))
				{
					event.getPlayer().getEquipment().setItemInMainHand(null);
					
					event.getPlayer().playSound(event.getPlayer().getLocation(), Sound.ENTITY_PLAYER_BURP, 1, 1);
					Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "particle item bread " + event.getPlayer().getEyeLocation().getX() + " " + (event.getPlayer().getEyeLocation().getY() - 0.6) + " " + event.getPlayer().getEyeLocation().getZ() + " 0 -1 0 0.2 45 force");
					event.getPlayer().setFoodLevel(event.getPlayer().getFoodLevel() + 3 < 20 ? event.getPlayer().getFoodLevel() + 3 : 20);
					event.getPlayer().setSaturation(event.getPlayer().getSaturation() + 3.2f);
					return;
				}
				
				if(MyItems.baguette_b1.CheckForCustomItem(event.getItem()))
				{
					event.getPlayer().getEquipment().setItemInMainHand(MyItems.baguette_b2.GetMyItemStack());
					
					event.getPlayer().playSound(event.getPlayer().getLocation(), Sound.ENTITY_PLAYER_BURP, 1, 1);
					Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "particle item bread " + event.getPlayer().getEyeLocation().getX() + " " + (event.getPlayer().getEyeLocation().getY() - 0.6) + " " + event.getPlayer().getEyeLocation().getZ() + " 0 -1 0 0.2 45 force");
					event.getPlayer().setFoodLevel(event.getPlayer().getFoodLevel() + 3 < 20 ? event.getPlayer().getFoodLevel() + 3 : 20);
					event.getPlayer().setSaturation(event.getPlayer().getSaturation() + 3.2f);
					return;
				}
				
				if(MyItems.baguette.CheckForCustomItem(event.getItem()))
				{
					event.getPlayer().getEquipment().setItemInMainHand(MyItems.baguette_b1.GetMyItemStack());
					
					event.getPlayer().playSound(event.getPlayer().getLocation(), Sound.ENTITY_PLAYER_BURP, 1, 1);
					Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "particle item bread " + event.getPlayer().getEyeLocation().getX() + " " + (event.getPlayer().getEyeLocation().getY() - 0.6) + " " + event.getPlayer().getEyeLocation().getZ() + " 0 -1 0 0.2 45 force");
					event.getPlayer().setFoodLevel(event.getPlayer().getFoodLevel() + 3 < 20 ? event.getPlayer().getFoodLevel() + 3 : 20);
					event.getPlayer().setSaturation(event.getPlayer().getSaturation() + 3.2f);
					return;
					
				}
			}
		}
	}
	
	//Called every 3 ticks for updating ice box inventories.
	public static void IceBoxInventoryHandling()
	{
		for(Inventory inventory : iceBoxInventories.keySet())
		{	
			if(inventory.getViewers().size() < 1) {iceBoxInventories.remove(inventory);break;}
			
			if(!MyItems.ice_box.CheckForCustomItem(inventory.getViewers().get(0).getEquipment().getItemInMainHand()))
			{
				inventory.getViewers().get(0).closeInventory();
				iceBoxInventories.remove(inventory);
				break;
			}
			
			List<String> newLore = new ArrayList<String>();
			
			for(ItemStack is : inventory)
			{				
				if(is == null) {continue;}
				
				if(!MyItems.parishables.containsKey(is.getType()))
				{
					Item i = inventory.getViewers().get(0).getWorld().dropItem(inventory.getViewers().get(0).getLocation(), is);
					i.setPickupDelay(0);
					i.setOwner(inventory.getViewers().get(0).getUniqueId());
					
					inventory.getViewers().get(0).sendMessage(ChatColor.RED + "That is not a parishable item.");
					
					inventory.remove(is);
					continue;
				}
				
				if(is.getItemMeta().hasCustomModelData())
				{	
					newLore.add(ChatColor.WHITE + toTitleCase(is.getItemMeta().getDisplayName()) + " x " + is.getAmount());
				}else {
					newLore.add(ChatColor.WHITE + toTitleCase(is.getType().toString().replace('_', ' ').toLowerCase()) + " x " + is.getAmount());
				}
				
				if(is.getItemMeta().hasLore())
				{
					if(is.getItemMeta().getLore().get(is.getItemMeta().getLore().size() - 1).contains("Age: "))
					{
						String ageText = is.getItemMeta().getLore().get(is.getItemMeta().getLore().size() - 1).replace(ChatColor.RED + "Age: ", "").replace(ChatColor.YELLOW + "Age: ", "").replace(ChatColor.GREEN + "Age: ", "");
						
						
						newLore.set(newLore.size() - 1, newLore.get(newLore.size() - 1) + " (" + ageText + ")");
						
					}
					
					if(is.getItemMeta().getLore().contains(CURED_TEXT))
					{
						newLore.set(newLore.size() - 1, ChatColor.WHITE + "Cured " + newLore.get(newLore.size() - 1));
					}
				}
			}
						
			ItemMeta meta = iceBoxInventories.get(inventory).getItemMeta();
			meta.setLore(newLore);
			iceBoxInventories.get(inventory).setItemMeta(meta);
		}
	}
	
	@EventHandler
	public void PlayerJoinRootAdvancement(PlayerJoinEvent event)
	{
		Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "advancement grant @a only core:root");
		Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "advancement grant @a only core:root_farming");
		
		//event.getPlayer().setResourcePack("https://download.mc-packs.net/pack/07a33ce1651ce3eac393a39dfb2e2ac60e4a2d75.zip");
	}
	
	
	@EventHandler
	public void NoLeatherDrop(EntityDeathEvent event)
	{
		List<ItemStack> rem = new ArrayList<ItemStack>();
		for(ItemStack i : event.getDrops())
		{
			if(i.getType() == Material.LEATHER)
			{
				rem.add(i);
			}
		}
		
		for(ItemStack i : rem)
		{
			event.getDrops().remove(i);
		}
	}
	
	private static final List<Material> ALLOWED_CURES = List.of(Material.BEEF, Material.COOKED_BEEF, Material.CHICKEN, Material.COOKED_CHICKEN, Material.PORKCHOP, Material.COOKED_PORKCHOP, Material.MUTTON, Material.COOKED_MUTTON, Material.RABBIT, Material.COOKED_RABBIT, Material.SALMON, Material.COOKED_SALMON, Material.COD, Material.COOKED_COD);
	public static final String CURED_TEXT = ChatColor.GREEN + "Cured";
	
	@EventHandler
	public void CureMeat(InventoryClickEvent event)
	{
		ItemStack cItem = event.getClickedInventory().getItem(event.getSlot());
		if(cItem != null)
		{
			if(event.getWhoClicked().getItemOnCursor() != null)
			{
				if(MyItems.brine.CheckForCustomItem(event.getWhoClicked().getItemOnCursor()))
				{					
					if(ALLOWED_CURES.contains(cItem.getType()))
					{
						if(cItem.hasItemMeta())
						{
							if(cItem.getItemMeta().hasLore())
							{
								event.setCancelled(true);
								
								ItemMeta meta = cItem.getItemMeta();
								List<String> lore = meta.getLore();
								
								if(lore.get(lore.size() - 1).contains("Age: "))
								{
									if(!lore.contains(CURED_TEXT))
									{
										lore.add(lore.size() - 1, CURED_TEXT);
										
										if(meta.hasDisplayName())
										{
											meta.setDisplayName(ChatColor.WHITE + "Cured " + meta.getDisplayName());
										}else {
											meta.setDisplayName(ChatColor.WHITE + "Cured " + toTitleCase(cItem.getType().toString().toLowerCase().replace("_", " ")));
										}
										
										lore.remove(lore.size() - 1);
										meta.setLore(lore);
										
										if(cItem.getAmount() == 1)
										{
											cItem.setItemMeta(meta);
										}else {
											ItemStack produce = cItem.clone();
											produce.setItemMeta(meta);
											produce.setAmount(1);
											
											cItem.setAmount(cItem.getAmount() - 1);
											event.getWhoClicked().getWorld().dropItem(event.getWhoClicked().getLocation(), produce).setPickupDelay(0);
										}
										
										
										if(Math.random() < 0.5)
										{
											if(event.getWhoClicked() instanceof Player)
											{
												Player p = (Player)event.getWhoClicked();
												p.playSound(p, Sound.BLOCK_BREWING_STAND_BREW, 1.f, 1.f);
											}
											
											if(event.getWhoClicked().getItemOnCursor().getAmount() == 1)
											{
												event.getWhoClicked().setItemOnCursor(null);
											}else {
												ItemStack newCursor = event.getWhoClicked().getItemOnCursor().clone();
												newCursor.setAmount(newCursor.getAmount() - 1);
												event.getWhoClicked().setItemOnCursor(newCursor);
											}
										}else {
											if(event.getWhoClicked() instanceof Player)
											{
												Player p = (Player)event.getWhoClicked();
												p.playSound(p, Sound.ENTITY_WANDERING_TRADER_DRINK_POTION, 1.f, 1.f);
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
	}
	
	@EventHandler
	public void CoinCondensing(EntityPickupItemEvent event)
	{
		if(!(event.getEntity() instanceof Player)) {return;}
		if(event.getItem().getItemStack().getType() == Material.GOLD_NUGGET)
		{
			if(Currency.coin.CheckForCustomItem(event.getItem().getItemStack()))
			{
				checkCondenseItem(event, Currency.coin_stack);
			}
			
			if(Currency.coin_stack.CheckForCustomItem(event.getItem().getItemStack()))
			{
				checkCondenseItem(event, Currency.coin_pouch);
			}
			
			if(Currency.coin_pouch.CheckForCustomItem(event.getItem().getItemStack()))
			{
				checkCondenseItem(event, Currency.bill);
			}
			
			if(Currency.bill.CheckForCustomItem(event.getItem().getItemStack()))
			{
				checkCondenseItem(event, Currency.bill_pile);
			}
		}
	}
	
	private static void checkCondenseItem(EntityPickupItemEvent event, CustomItemStack item)
	{
		Player p = (Player)event.getEntity();
		int amnt = (9 - event.getItem().getItemStack().getAmount());
		
		if(amnt <= 0)
		{
			ItemStack is = event.getItem().getItemStack();
			int stacks = is.getAmount()/9;
			
			if(is.getAmount() % 9 == 0)
			{
				event.setCancelled(true);
				ItemStack stacksI = item.GetAmountClone(stacks);
				p.getWorld().dropItem(p.getLocation(), stacksI).setPickupDelay(0);
				event.getItem().remove();
				return;
			}
			
			is.setAmount(is.getAmount() % 9);
			
			event.getItem().setItemStack(is);
			
			ItemStack stacksI = item.GetAmountClone(stacks);
			
			p.getWorld().dropItem(p.getLocation(), stacksI).setPickupDelay(0);
			
			return;
		}
		
		
	}
	
	@EventHandler
	public void AnyHeadItem(InventoryClickEvent event)
	{
		if(event.getClickedInventory().getType() == InventoryType.PLAYER)
		{
			if(event.getSlotType() == SlotType.ARMOR)
			{
				if(event.getSlot() == 39)
				{
					if(event.getCursor() != null)
					{
						if(event.getCursor().getType() != Material.AIR)
						{
							if(event.getClickedInventory().getItem(event.getSlot()) != null && event.getClickedInventory().getItem(event.getSlot()).getType() != Material.AIR)
							{
								return;
							}
							
							event.getClickedInventory().setItem(event.getSlot(), event.getCursor());
							event.getWhoClicked().setItemOnCursor(null);
							event.setCancelled(true);
							return;
						}
					}
					
					if(event.getClickedInventory().getItem(event.getSlot()) != null)
					{
						event.getWhoClicked().setItemOnCursor(event.getClickedInventory().getItem(event.getSlot()));
						event.getClickedInventory().setItem(event.getSlot(), null);
						event.setCancelled(true);
						return;
					}
					
				}
			}
			
		}
	}
	
	@EventHandler
	public void UseNitrous(PlayerInteractEntityEvent event)
	{
		ItemStack mh = event.getPlayer().getEquipment().getItemInMainHand();
		if(mh != null)
		{
			if(Nonmetals.nitrous_oxide.CheckForCustomItem(mh))
			{
				if(event.getRightClicked() instanceof LivingEntity)
				{
					
					event.getPlayer().getWorld().playSound(event.getPlayer().getLocation(), Sound.BLOCK_FIRE_EXTINGUISH, 1, 1);
					
					LivingEntity lv = (LivingEntity)event.getRightClicked();
					lv.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20 * 15, 1));
					lv.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 20 * 20, 1));
					if(mh.getAmount() > 1)
					{
						mh.setAmount(mh.getAmount() - 1);
						event.getPlayer().getEquipment().setItemInMainHand(mh);
					}else {
						event.getPlayer().getEquipment().setItemInMainHand(null);
					}
				}
			}
		}
	}
	

	
	@EventHandler
	public void CheckForBrineMaking(PlayerInteractEvent event)
	{
		if(event.getAction() != Action.RIGHT_CLICK_BLOCK) {return;}
		
		
		if(event.getItem() != null)
		{
			if(MyItems.brine_cauldron.getRelatedBlock().CheckForCustomBlock(event.getClickedBlock()))
			{
				if(event.getItem().getType() == Material.GLASS_BOTTLE)
				{
					int c = event.getItem().getAmount();
					
					if(c == 1)
					{
						event.getPlayer().getEquipment().setItemInMainHand(MyItems.brine.GetAmountClone(2));
					}else {
						ItemStack newItem = event.getItem().clone();
						newItem.setAmount(c - 1);
						
						event.getPlayer().getEquipment().setItemInMainHand(newItem);
						
						ItemStack brine = MyItems.brine.GetAmountClone(2);
						Item i = event.getPlayer().getWorld().dropItem(event.getPlayer().getLocation(), brine);
						i.setPickupDelay(0);
						
					}
					
					MyItems.brine_cauldron.getRelatedBlock().Remove(event.getClickedBlock().getLocation(), false);
					event.getClickedBlock().setType(Material.CAULDRON);
				}
			}
		}
		
		if(event.getClickedBlock().getType() == Material.WATER_CAULDRON)
		{
			for(Entity e : event.getClickedBlock().getWorld().getNearbyEntities(event.getClickedBlock().getLocation(), 0.8f, 1f, 0.8f))
			{
				if(e instanceof Item)
				{
					Item i = (Item)e;
					ItemStack is = i.getItemStack();
					if(MyItems.salt_item.CheckForCustomItem(is))
					{
						if(is.getAmount() > 12)
						{
							MyItems.brine_cauldron.getRelatedBlock().Place(event.getClickedBlock().getLocation());
							
							is.setAmount(is.getAmount() - 12);
							i.setItemStack(is);
							return;
						}
						
						if(is.getAmount() == 12)
						{
							MyItems.brine_cauldron.getRelatedBlock().Place(event.getClickedBlock().getLocation());
							
							i.remove();
							return;
						}
					}
				}
			}
		}
	}
	
	@EventHandler
	public void SaltWaterFromOcean(org.bukkit.event.player.PlayerBucketFillEvent event)
	{		
		if(event.getItemStack().getType() == Material.WATER_BUCKET)
		{
			if(event.getBlockClicked().getBiome() == Biome.OCEAN || event.getBlockClicked().getBiome() == Biome.COLD_OCEAN || event.getBlockClicked().getBiome() == Biome.DEEP_COLD_OCEAN || event.getBlockClicked().getBiome() == Biome.DEEP_FROZEN_OCEAN || event.getBlockClicked().getBiome() == Biome.DEEP_LUKEWARM_OCEAN || event.getBlockClicked().getBiome() == Biome.DEEP_OCEAN || event.getBlockClicked().getBiome() == Biome.FROZEN_OCEAN || event.getBlockClicked().getBiome() == Biome.LUKEWARM_OCEAN || event.getBlockClicked().getBiome() == Biome.WARM_OCEAN || event.getBlockClicked().getBiome() == Biome.BEACH || event.getBlockClicked().getBiome() == Biome.SNOWY_BEACH)
			{
				event.setItemStack(MyItems.salt_water.GetMyItemStack());
			}
		}
	}
	
	@EventHandler
	public void CentrifugeHandling(PlayerInteractEvent event)
	{
		if(event.getAction() != Action.RIGHT_CLICK_BLOCK || event.getPlayer().isSneaking())
		{
			return;
		}
		
			//ENGINES
		if(MyItems.centrifuge_engines[0].getRelatedBlock().CheckForCustomBlock(event.getClickedBlock()))
		{
			event.setCancelled(true);
			int mult = 1;
			for(CustomItemStack cis : MyItems.centrifuge_tops)
			{
				if(cis.getRelatedBlock().CheckForCustomBlock(event.getClickedBlock().getRelative(BlockFace.UP)))
				{
					triggerCentrifuge(event.getClickedBlock().getRelative(BlockFace.UP), cis, 10f + (20f * mult), 1);
					return;
				}
				
				mult++;
			}
			
			
			return;
		}
		
			
			//TOPS
		int level = 1;
		for(CustomItemStack cis : MyItems.centrifuge_tops)
		{
			if(cis.getRelatedBlock().CheckForCustomBlock(event.getClickedBlock()))
			{
				event.setCancelled(true);
				if(event.getItem() != null)
				{
					if(placeItemOnCentrifuge(event.getItem(), event.getClickedBlock(), cis, level))
					{
						if(event.getItem().getAmount() > 1)
						{
							event.getItem().setAmount(event.getItem().getAmount() - 1);
							event.getPlayer().getEquipment().setItemInMainHand(event.getItem());
						}else {
							event.getPlayer().getEquipment().setItemInMainHand(null);
						}
					}
					
				}
				break;
			}
			
			level++;
		}

	}
	
	@EventHandler
	public void MineCentrifuge(BlockBreakEvent event)
	{
		if(event.getBlock().getType() != Material.WARPED_TRAPDOOR) {return;}
		for(CustomItemStack cis : MyItems.centrifuge_tops)
		{
			if(cis.getRelatedBlock().CheckForCustomBlock(event.getBlock()))
			{
				System.out.println("MINED STAND");

				List<ArmorStand> asl = GetCentrifugeStands(event.getBlock());
				System.out.println(asl.size());

				for(ArmorStand as : asl)
				{
					as.getWorld().dropItemNaturally(as.getLocation(), as.getEquipment().getItemInMainHand());
					
					as.remove();
				}
				break;
			}
		}
	}
	
	public static final String CENTRIFUGE_AS_PREFIX = "centrifuge_item";
	
	private static boolean placeItemOnCentrifuge(ItemStack item, Block b, CustomItemStack cis, int level) //Item to put, Block to put it on, Type of centrifuge, Centrifuge level. Returns if placing successful.
	{
		ArmorStand check = cis.getRelatedBlock().GetMyStand(b);
		if(check.getCustomName() != null)
		{
			if(Float.parseFloat(check.getCustomName()) > 0)
			{
				return false;
			}
		}
		
		ItemStack put = item.clone();
		put.setAmount(1);
		
		ArmorStand as = (ArmorStand)b.getWorld().spawnEntity(b.getLocation(), EntityType.ARMOR_STAND);
		
		
		as.setMarker(true);
		as.setInvisible(true);
		as.setInvulnerable(true);
		as.setCustomName(CENTRIFUGE_AS_PREFIX + ":");
		
		as.getEquipment().setItemInMainHand(put);
		
		List<ArmorStand> stands = GetCentrifugeStands(b);
		
		
		switch(level < 4 && stands.size() > 4 ? -1 : stands.size() - 1)
		{
		case 0:
			as.teleport(as.getLocation().add(1f, -0.35f, -1f));
			return true;
		case 1:
			as.teleport(as.getLocation().add(2f, -0.35f, 0f));
			return true;
		case 2:
			as.teleport(as.getLocation().add(1f, -0.35f, 1f));
			return true;
		case 3:
			as.teleport(as.getLocation().add(0f, -0.35f, 0f));
			return true;
		case 4:
			as.teleport(as.getLocation().add(0.2f, -0.35f, -0.7f));
			return true;
		case 5:
			as.teleport(as.getLocation().add(1.8f, -0.35f, 0.7f));
			return true;
		default:
			as.remove();
			return false;
		}
	}
	
	private static List<ArmorStand> GetCentrifugeStands(Block b)
	{
		List<ArmorStand> stands = new ArrayList<ArmorStand>();
		for(Entity e : b.getWorld().getNearbyEntities(Utils.AddToLocationAsNew(b.getLocation(), 0.5f, 0.5f, 0.5f), 2f, 2f, 2f))
		{
			if(e.getType() == EntityType.ARMOR_STAND)
			{
				ArmorStand cas = (ArmorStand)e;
				if(cas.getCustomName() == null) {continue;}
				if(cas.getCustomName().startsWith(CENTRIFUGE_AS_PREFIX))
				{
					stands.add(cas);
				}
			}
		}
		
		return stands;
	}
	
	public static void triggerCentrifuge(Block b, CustomItemStack cis, float deg, int level) {triggerCentrifuge(b, cis, deg, level, false);}
	public static void triggerCentrifuge(Block b, CustomItemStack cis, float deg, int level, boolean require4)
	{
		List<ArmorStand> stands = GetCentrifugeStands(b);
		
		if(stands.size() == 0)
		{
			return;
		}
		
		ArmorStand as = cis.getRelatedBlock().GetMyStand(b);
		
		float prevDeg = 0;
		prevDeg += deg;
		
		if(cis.getName().toLowerCase().equalsIgnoreCase(MyItems.centrifuge_tops[3].getName().toLowerCase()))
		{
			if(require4) {if(stands.size() < 6 && as.getLocation().getYaw() == 0) {return;}}
		}else {
			if(require4) {if(stands.size() < 4 && as.getLocation().getYaw() == 0) {return;}}
		}
		
		if(as.getCustomName() == null)
		{
			as.setCustomName("0");
		}
		
		prevDeg += Float.parseFloat(as.getCustomName());
		
		as.setCustomName("" + prevDeg);
		
		as.setRotation(as.getLocation().getYaw() + deg, 0);
		
		
		for(ArmorStand stand : stands)
		{
			Location newloc = RotateAboutAPoint(as.getLocation(), stand.getLocation(), deg);
			stand.teleport(newloc);
		}
		
		if(prevDeg > 1000)
		{
			if(stands.size() == 1)
			{
				as.setRotation(0, 0);
				as.setCustomName("0");
			}else {
				as.setCustomName("1");
			}
			
			ItemStack is = stands.get(0).getEquipment().getItemInMainHand();
			
			boolean foundRecipe = false;
			
			for(CentrifugeRecipe cr : MyItems.centrifugeRecipes)
			{
				if(cr.ContainsRecipe(is, level))
				{
					foundRecipe = true;
					
					if(cr.CheckForRecipe(is, level)) {
						Vector v = stands.get(0).getLocation().toVector().subtract(as.getLocation().toVector()).normalize().multiply(-deg/250);
						
						Item i = as.getWorld().dropItem(stands.get(0).getLocation(), cr.Result());
						i.setVelocity(v);
					}
				}
			}
			
			if(!foundRecipe)
			{
				Vector v = stands.get(0).getLocation().toVector().subtract(as.getLocation().toVector()).normalize().multiply(-deg/250);
				Item i = as.getWorld().dropItem(stands.get(0).getLocation(), is.clone());
				i.setVelocity(v);
			}
			
			stands.get(0).remove();
		}
	}
	
	public static Location RotateAboutAPoint(Location middle, Location point, double degrees)
	{
        middle.setY(point.getY());
       
        float oldyaw = point.getYaw()%360.0F;
       
        double d = middle.distance(point);
        double angle = Math.acos((point.getX()-middle.getX())/d);
       
        if(point.getZ() < middle.getZ()){
            double newangle = 2*Math.PI-angle + (degrees * Math.PI / 180);
            double newx = Math.cos(newangle) * d;
            double newz = Math.sin(newangle) * d;
            point = middle.clone().add(newx, 0, newz);
            point.setYaw((float) ((oldyaw+degrees)%360.0F));
            return point;
        }else {
            double newangle = angle + (degrees * Math.PI / 180);

            double newx = Math.cos(newangle) * d;
            double newz = Math.sin(newangle) * d;
            point = middle.clone().add(newx, 0, newz);
            point.setYaw((float) ((oldyaw+degrees)%360.0F));
            return point;
        }
	}

	
	@EventHandler
	public void JarHandling(PlayerInteractEvent event)
	{
		if(event.getAction() != Action.RIGHT_CLICK_BLOCK)
		{
			return;
		}
		
		
		if(MyItems.glass_jar.getRelatedBlock().CheckForCustomBlock(event.getClickedBlock()))
		{
			event.setCancelled(true);
			if(event.getItem() != null)
			{
				int standCmd = MyItems.glass_jar.getRelatedBlock().GetMyStand(event.getClickedBlock()).getEquipment().getHelmet().getItemMeta().getCustomModelData();
				int fillLevel = standCmd == MyItems.glass_jar.getCustomModelData() ? -1 : standCmd % 10;
				
				/*
				 * Create Salt Water.
				 */
				if(MyItems.salt_item.CheckForCustomItem(event.getItem()))
				{
					if(event.getItem().getAmount() >= 16)
					{
						if(standCmd == MyItems.glass_jar_water.getDisplayCustomModelData() + 2 || standCmd == MyItems.glass_jar_salt_water.getDisplayCustomModelData() + 2)
						{
							MyItems.glass_jar.getRelatedBlock().UpdateArmorStandTexture(new CustomBlock(new CustomItemStack("Glass Jar", Material.WARPED_TRAPDOOR, MyItems.glass_jar_brine.getDisplayCustomModelData() + 2)), event.getClickedBlock());
							ItemStack i = event.getItem();
							if(i.getAmount() == 16)
							{
								event.getPlayer().getEquipment().setItemInMainHand(null);
							}else {
								i.setAmount(i.getAmount() - 16);
								event.getPlayer().getEquipment().setItemInMainHand(i);
							}
						}
						
						
					}
					return;
				}
				
				/*
				 * Create Tannin.
				 */
				if(Cosmetics.bark.CheckForCustomItem(event.getItem()))
				{
					if(event.getItem().getAmount() >= 4)
					{
						if(standCmd == MyItems.glass_jar_water.getDisplayCustomModelData() + 2)
						{
							MyItems.glass_jar.getRelatedBlock().UpdateArmorStandTexture(new CustomBlock(new CustomItemStack("Glass Jar", Material.WARPED_TRAPDOOR, MyItems.glass_jar_tannin.getDisplayCustomModelData() + 2)), event.getClickedBlock());
							ItemStack i = event.getItem();
							if(i.getAmount() == 4)
							{
								event.getPlayer().getEquipment().setItemInMainHand(null);
							}else {
								i.setAmount(i.getAmount() - 4);
								event.getPlayer().getEquipment().setItemInMainHand(i);
							}
						}
					}
					
					return;
				}
				
				if(Cosmetics.animal_hide.CheckForCustomItem(event.getItem()))
				{
					if(standCmd >= MyItems.glass_jar_tannin.getDisplayCustomModelData() && standCmd <= MyItems.glass_jar_tannin.getDisplayCustomModelData() + 2)
					{
						if(standCmd == MyItems.glass_jar_tannin.getDisplayCustomModelData())
						{
							MyItems.glass_jar.getRelatedBlock().UpdateArmorStandTexture(new CustomBlock(new CustomItemStack("Glass Jar", Material.WARPED_TRAPDOOR, MyItems.glass_jar.getCustomModelData())), event.getClickedBlock());
						}else {
							MyItems.glass_jar.getRelatedBlock().UpdateArmorStandTexture(new CustomBlock(new CustomItemStack("Glass Jar", Material.WARPED_TRAPDOOR, standCmd - 1)), event.getClickedBlock());
						}
						
						ItemStack i = event.getItem();
						if(i.getAmount() == 1)
						{
							event.getPlayer().getEquipment().setItemInMainHand(Cosmetics.soaked_animal_hide.GetMyItemStack());
						}else {
							i.setAmount(i.getAmount() - 1);
							event.getPlayer().getWorld().dropItem(event.getPlayer().getLocation(), Cosmetics.soaked_animal_hide.GetMyItemStack()).setPickupDelay(0);
						}
					}
					
					return;
				}
				
				if(event.getItem().getType() == Material.BUCKET || event.getItem().getType() == Material.GLASS_BOTTLE)
				{
					if(event.getItem().hasItemMeta())
					{
						if(event.getItem().getItemMeta().hasCustomModelData())
						{
							return;
						}
					}
					
					int soundId = -1;
					for(Entry<Integer, ItemStack> conversion : MyItems.GlassJarConversions.entrySet())
					{
						soundId++;

						if(conversion.getValue().getType() == Material.COOKIE)
						{
							continue;
						}
						
						if(conversion.getValue().getType() == Material.HONEY_BOTTLE && event.getItem().getType() != Material.GLASS_BOTTLE) { continue; }
						if(conversion.getValue().getType() != Material.HONEY_BOTTLE && event.getItem().getType() != Material.BUCKET) { continue; }
						if((standCmd - fillLevel) + 1 == conversion.getKey())
						{
							if(fillLevel == 1)
							{
								MyItems.glass_jar.getRelatedBlock().UpdateArmorStandTexture(MyItems.glass_jar.getRelatedBlock(), event.getClickedBlock());
							}else {
								MyItems.glass_jar.getRelatedBlock().UpdateArmorStandTexture(new CustomBlock(new CustomItemStack("Glass Jar", Material.WARPED_TRAPDOOR, standCmd - 1)), event.getClickedBlock());
							}
							
							//event.getPlayer().getWorld().playSound(event.getClickedBlock().getLocation(), MyItems.GlassOUTJarSounds.get(soundId), 1.0f, 1.0f);
							
							if(event.getItem().getType().toString().contains("BUCKET") || event.getItem().getType().toString().contains("BROWN_DYE"))
							{
								if(conversion.getValue().getType() == Material.LAVA_BUCKET)
								{
									event.getPlayer().getWorld().playSound(event.getClickedBlock().getLocation(), Sound.ITEM_BUCKET_FILL_LAVA, 1.0f, 1.0f);
								}else {
									event.getPlayer().getWorld().playSound(event.getClickedBlock().getLocation(), Sound.ITEM_BUCKET_FILL, 1.0f, 1.0f);
								}
							}else if(event.getItem().getType().toString().contains("BOTTLE"))
							{
								event.getPlayer().getWorld().playSound(event.getClickedBlock().getLocation(), Sound.ITEM_BOTTLE_FILL, 1.0f, 1.0f);
							}else if(conversion.getValue().getType() == Material.COOKIE)
							{
								event.getPlayer().getWorld().playSound(event.getClickedBlock().getLocation(), Sound.ENTITY_ITEM_PICKUP, 1.0f, 1.0f);
							}
							
							if(event.getPlayer().getEquipment().getItemInMainHand().getAmount() == 1)
							{
								event.getPlayer().getEquipment().setItemInMainHand(conversion.getValue().clone());
							}else {
								ItemStack i = event.getPlayer().getEquipment().getItemInMainHand();
								i.setAmount(i.getAmount() - 1);
								event.getPlayer().getEquipment().setItemInMainHand(i);
								
								Item id = event.getPlayer().getWorld().dropItem(event.getPlayer().getLocation(), conversion.getValue().clone());
								id.setPickupDelay(0);
							}
							
							return;
						}
					}
				}
				
				
				if(fillLevel > 2) {return;}
				
				if(event.getItem().getType() == Material.COOKIE)
				{
					if(event.getItem().getAmount() < 3)
					{
						return;
					}
				}
				
				
				int soundId = -1;
				for(Entry<ItemStack, CustomBlock> insert : MyItems.GlassJarInserts.entrySet())
				{
					soundId++;
					if(event.getItem().getType() == insert.getKey().getType())
					{
						if(insert.getKey().hasItemMeta())
						{
							if(insert.getKey().getItemMeta().hasCustomModelData())
							{
								if(event.getItem().hasItemMeta())
								{
									if(event.getItem().getItemMeta().hasCustomModelData())
									{
										if(insert.getKey().getItemMeta().getCustomModelData() == event.getItem().getItemMeta().getCustomModelData())
										{

											
											if(fillLevel == -1)
											{
												MyItems.glass_jar.getRelatedBlock().UpdateArmorStandTexture(insert.getValue(), event.getClickedBlock());
											}else {
												if(standCmd - fillLevel + 1 == insert.getValue().getDisplayCustomModelData())
												{
													MyItems.glass_jar.getRelatedBlock().UpdateArmorStandTexture(new CustomBlock(new CustomItemStack("Glass Jar", Material.WARPED_TRAPDOOR, standCmd + 1)), event.getClickedBlock());
												}else {
													return;
												}
											}
											//event.getPlayer().getWorld().playSound(event.getClickedBlock().getLocation(), MyItems.GlassINJarSounds.get(soundId), 1.0f, 1.0f);
											
											if(event.getItem().getType() == Material.COOKIE)
											{
												event.getPlayer().getWorld().playSound(event.getClickedBlock().getLocation(), Sound.ENTITY_ITEM_PICKUP, 1.0f, 1.0f);
												if(event.getItem().getAmount() == 4)
												{
													event.getPlayer().getEquipment().setItemInMainHand(null);
												}else {
													ItemStack is = event.getItem();
													is.setAmount(is.getAmount() - 4);
													event.getPlayer().getEquipment().setItemInMainHand(is);
												}
											}
											
											if(insert.getKey().getItemMeta().getDisplayName().toLowerCase().contains("bucket"))
											{
												if(event.getItem().getType() == Material.LAVA_BUCKET)
												{
													event.getPlayer().getWorld().playSound(event.getClickedBlock().getLocation(), Sound.ITEM_BUCKET_EMPTY_LAVA, 1.0f, 1.0f);
												}else {
													event.getPlayer().getWorld().playSound(event.getClickedBlock().getLocation(), Sound.ITEM_BUCKET_EMPTY, 1.0f, 1.0f);
												}
												
												
												if(event.getItem().getAmount() == 1)
												{
													event.getPlayer().getEquipment().setItemInMainHand(new ItemStack(Material.BUCKET));
												}else {
													Item i = event.getPlayer().getWorld().dropItem(event.getPlayer().getLocation(), new ItemStack(Material.BUCKET));
													i.setPickupDelay(0);
													
													ItemStack is = event.getItem();
													is.setAmount(is.getAmount() -1 );
													event.getPlayer().getEquipment().setItemInMainHand(is);
												}
											}
											
											if(insert.getKey().getType() == Material.HONEY_BOTTLE)
											{
												event.getPlayer().getWorld().playSound(event.getClickedBlock().getLocation(), Sound.ITEM_BOTTLE_EMPTY, 1.0f, 1.0f);
												if(event.getItem().getAmount() == 1)
												{
													event.getPlayer().getEquipment().setItemInMainHand(new ItemStack(Material.GLASS_BOTTLE));
												}else {
													Item i = event.getPlayer().getWorld().dropItem(event.getPlayer().getLocation(), new ItemStack(Material.GLASS_BOTTLE));
													i.setPickupDelay(0);

													ItemStack is = event.getItem();
													is.setAmount(is.getAmount() -1 );
													event.getPlayer().getEquipment().setItemInMainHand(is);
												}
											}

								
											
											return;
										}
									}
								}
								
								continue;
							}
						}else if(event.getItem().hasItemMeta())
						{
							if(event.getItem().getItemMeta().hasCustomModelData())
							{
								return;
							}
						}
						
						
						
						if(fillLevel == -1)
						{
							MyItems.glass_jar.getRelatedBlock().UpdateArmorStandTexture(insert.getValue(), event.getClickedBlock());
						}else {
							if(standCmd - fillLevel + 1 == insert.getValue().getDisplayCustomModelData())
							{
								MyItems.glass_jar.getRelatedBlock().UpdateArmorStandTexture(new CustomBlock(new CustomItemStack("Glass Jar", Material.WARPED_TRAPDOOR, standCmd + 1)), event.getClickedBlock());
							}else {
								return;
							}
						}
						
						
						//event.getPlayer().getWorld().playSound(event.getClickedBlock().getLocation(), MyItems.GlassINJarSounds.get(soundId), 1.0f, 1.0f);
						
						if(event.getItem().getType() == Material.COOKIE)
						{
							event.getPlayer().getWorld().playSound(event.getClickedBlock().getLocation(), Sound.ENTITY_ITEM_PICKUP, 1.0f, 1.0f);
							if(event.getItem().getAmount() == 4)
							{
								event.getPlayer().getEquipment().setItemInMainHand(null);
							}else {
								ItemStack is = event.getItem();
								is.setAmount(is.getAmount() - 4);
								event.getPlayer().getEquipment().setItemInMainHand(is);
							}
							return;
						}
						
						if(event.getItem().getType().toString().contains("BUCKET"))
						{
							if(event.getItem().getType() == Material.LAVA_BUCKET)
							{
								event.getPlayer().getWorld().playSound(event.getClickedBlock().getLocation(), Sound.ITEM_BUCKET_EMPTY_LAVA, 1.0f, 1.0f);
							}else {
								event.getPlayer().getWorld().playSound(event.getClickedBlock().getLocation(), Sound.ITEM_BUCKET_EMPTY, 1.0f, 1.0f);
							}
							event.getPlayer().getEquipment().setItemInMainHand(new ItemStack(Material.BUCKET));
						}else if(event.getItem().getType().toString().contains("BOTTLE"))
						{
							event.getPlayer().getWorld().playSound(event.getClickedBlock().getLocation(), Sound.ITEM_BOTTLE_EMPTY, 1.0f, 1.0f);
							if(event.getItem().getAmount() == 1)
							{
								event.getPlayer().getEquipment().setItemInMainHand(new ItemStack(Material.GLASS_BOTTLE));
							}else {
								Item i = event.getPlayer().getWorld().dropItem(event.getPlayer().getLocation(), new ItemStack(Material.GLASS_BOTTLE));
								i.setPickupDelay(0);
							}
						}
						return;
					}
				}
			}else {
				ArmorStand as = MyItems.glass_jar.getRelatedBlock().GetMyStand(event.getClickedBlock());
				int cmd = as.getEquipment().getHelmet().getItemMeta().getCustomModelData();
				int fillLevel = cmd == MyItems.glass_jar.getRelatedBlock().getDisplayCustomModelData() ? -1 : cmd % 10;
				
				
				
				if(cmd - fillLevel + 1 != MyItems.glass_jar_cookies.getDisplayCustomModelData()) {return;}
				
				if(fillLevel == -1) {return;}
				
				if(fillLevel == 1)
				{
					MyItems.glass_jar.getRelatedBlock().UpdateArmorStandTexture(MyItems.glass_jar.getRelatedBlock(), event.getClickedBlock());
				}else {
					ItemStack disp = as.getEquipment().getHelmet();
					ItemMeta dispMeta = disp.getItemMeta();
					dispMeta.setCustomModelData(cmd - 1);
					disp.setItemMeta(dispMeta);
					
					as.getEquipment().setHelmet(disp);
				}
				
				Item i = event.getPlayer().getWorld().dropItem(event.getPlayer().getLocation(), new ItemStack(Material.COOKIE, 4));
				i.setPickupDelay(0);
			}
			
		}
	}
	
	@EventHandler
	public void CheckForRottenFood(PlayerItemConsumeEvent event)
	{
		if(event.getItem().hasItemMeta())
		{
			if(event.getItem().getItemMeta().hasLore())
			{
				List<String> lore = event.getItem().getItemMeta().getLore();
				if(lore.get(lore.size() - 1).contains("ROTTEN"))
				{
					if(event.getItem().getType() == Material.MILK_BUCKET)
					{
						event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 20 * (9 * 2 + 15), 5));
						event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20 * (9 * 2 + 15), 1));

						event.getPlayer().setSaturation(0);
						
						event.setCancelled(true);
						
						event.getPlayer().getEquipment().setItemInMainHand(new ItemStack(Material.BUCKET));
						return;
					}
					
					Random foodRand = new Random();
					if(foodRand.nextInt(101) < 36)
					{
						event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 20 * (foodRand.nextInt(10) * 2 + 15), foodRand.nextInt(5) + 1));
					}
					
					event.getPlayer().setFoodLevel(event.getPlayer().getFoodLevel() - (foodRand.nextInt(8) + 3));
					event.getPlayer().setSaturation(event.getPlayer().getSaturation() - (foodRand.nextInt(8) + 3));
					event.getPlayer().setAbsorptionAmount(0);
				}
			}
		}
	}
	
	@EventHandler
	public void InventoryClick(InventoryClickEvent event)
	{
		if(event.getClickedInventory().getType() == InventoryType.PLAYER)
		{
			ItemStack canister = event.getClickedInventory().getItem(event.getSlot());
			if(canister != null)
			{
				if(RegisterElementalItems.GasCanister.CheckForCustomItem(canister))
				{
					if(event.getCursor() != null && event.getCursor().getType() != Material.AIR) 
					{
						if(event.getCursor().getType() == Material.WOODEN_SWORD)
						{
							if(!event.getCursor().getItemMeta().hasCustomModelData()) {return;}
							if(event.getCursor().getItemMeta().getCustomModelData() == RegisterElementalItems.GasCanister.getCustomModelData()) {return;}
							event.setCancelled(true);
							ItemMeta im = canister.getItemMeta();
							
							List<String> l = im.getLore();
							
							if(l.size() > 12) {return;}
							
							l.add(ChatColor.GRAY + event.getCursor().getItemMeta().getDisplayName() + " (" + event.getCursor().getItemMeta().getLore().get(0) + ")" + ChatColor.GRAY + " " + event.getCursor().getItemMeta().getCustomModelData());
														
							im.setLore(l);
							
							canister.setItemMeta(im);
							event.getClickedInventory().setItem(event.getSlot(), canister);
							event.getWhoClicked().setItemOnCursor(null);
						}
					}else {
						if(event.getAction() == InventoryAction.PICKUP_ALL)
						{
							return;
						}
						
						if(canister.getItemMeta().getLore().size() > 1)
						{
							for(CustomItemStack cis : RegisterElementalItems.GasList)
							{
								String[] arr = canister.getItemMeta().getLore().get(1).split(" ");
								if(cis.getCustomModelData() == Integer.parseInt(arr[arr.length - 1]))
								{
									ItemMeta im = canister.getItemMeta();
									
									List<String> lore = im.getLore();
									
									lore.remove(1);
									
									im.setLore(lore);
									
									canister.setItemMeta(im);
									event.getClickedInventory().setItem(event.getSlot(), canister);
									
									event.getWhoClicked().setItemOnCursor(cis.GetMyItemStack());
									break;
								}
							}
							event.setCancelled(true);
						}
					}
				}
			}
		}
	}

	
	@EventHandler
	public void Join(PlayerJoinEvent event)
	{
		//event.getPlayer().setResourcePack("https://drive.google.com/uc?export=download&id=1ecEkSCyXQxyMRS6yeUoB2W9Cn_5P3qln");
	}
	
	@EventHandler
	public void UseJetpack(PlayerMoveEvent event)
	{
		if(event.getPlayer().isSneaking())
		{
			if(event.getPlayer().isGliding())
			{
				ItemStack chestplate = event.getPlayer().getEquipment().getChestplate();
				
				if(chestplate != null)
				{
					if(MyItems.jetpack.CheckForCustomItem(chestplate))
					{
						ItemMeta im = chestplate.getItemMeta();
						List<String> lore = im.getLore();
						int currFuel = Injector.GetJetpackFuel(lore.get(0));
						
						if(currFuel > 0)
						{
							event.getPlayer().getWorld().spawnParticle(Particle.FLAME, AddToLocation(event.getPlayer().getLocation(), 0, 0.5f, 0), 1,0,0,0,0);
							
							event.getPlayer().getWorld().playSound(event.getPlayer().getLocation(), Sound.BLOCK_FIRE_EXTINGUISH, 1, 1);
							
							if(event.getPlayer().getVelocity().getY() < 1f)
							{
								event.getPlayer().setVelocity(event.getPlayer().getVelocity().add(new Vector(0, 0.2f, 0)));
								
							}
							
							lore.set(0, Injector.SetJetpackFuel(currFuel - 1));
							im.setLore(lore);
							chestplate.setItemMeta(im);
						}else {
							event.getPlayer().getWorld().spawnParticle(Particle.SMOKE_NORMAL, AddToLocation(event.getPlayer().getLocation(), 0, 0.5f, 0), 1,0,0,0,0);
							event.getPlayer().getWorld().playSound(event.getPlayer().getLocation(), Sound.BLOCK_CHAIN_BREAK, 1, 1);

						}
						
						
					}
				}
			}
		}
	}
	
	private static Location AddToLocation(Location l, float x, float y, float z)
	{
		return new Location(l.getWorld(), l.getX() + x, l.getY() + y, l.getZ() + z);
	}
	
	@EventHandler
	public void InteractTrumpetGunEvent(PlayerInteractEvent event)
	{
		if(event.getAction() == Action.RIGHT_CLICK_AIR)
		{
			if(MyItems.trumpet.CheckForCustomItem(event.getPlayer().getEquipment().getItemInMainHand()))
			{
				Utils.PlayCustomSound("core_mod.trumpet_note", event.getPlayer().getLocation());
				ShootProjectile(event.getPlayer(), 0.25f, 5f, Particle.NOTE, Particle.VILLAGER_HAPPY, 0, false, "{player} was blown to death by {player2} using [Trumpet]");
			}
			
			if(MyItems.oil_gun.CheckForCustomItem(event.getPlayer().getEquipment().getItemInMainHand()))
			{
				ItemMeta im = event.getPlayer().getEquipment().getItemInMainHand().getItemMeta();
				List<String> lore = im.getLore();
				int currFuel = Injector.GetJetpackFuel(lore.get(0));
				
				if(currFuel > 49)
				{
					event.getPlayer().getWorld().playSound(event.getPlayer().getLocation(), Sound.BLOCK_FIRE_EXTINGUISH, 1, 1);
					ShootProjectile(event.getPlayer(), 4.5f, 30000f, Particle.DRIP_LAVA, Particle.LAVA, 0.2f, true, "{player} was incinerated by {player2} using [Oil Gun]");
					
					lore.set(0, Injector.SetJetpackFuel(currFuel - 50));
					im.setLore(lore);
					event.getPlayer().getEquipment().getItemInMainHand().setItemMeta(im);
				}else {
					event.getPlayer().getWorld().playSound(event.getPlayer().getLocation(), Sound.BLOCK_CHAIN_BREAK, 1, 1);
				}
			}
		}
	}
	
	@EventHandler
	public void InteractWireRedstoneAndCustom(PlayerInteractEvent event)
	{
		if(event.getAction() == Action.RIGHT_CLICK_BLOCK)
		{
			if(event.getClickedBlock().getType() == Material.REDSTONE_WIRE)
			{
				if(MyItems.wireless_redstone_activator.CheckForCustomItem(event.getPlayer().getEquipment().getItemInMainHand()))
				{
					ItemMeta m = event.getPlayer().getEquipment().getItemInMainHand().getItemMeta();
					
					List<String> l = new ArrayList<String>();
					Location loc = event.getClickedBlock().getLocation();
					l.add(ChatColor.GREEN + "Link: " + loc.getBlockX() + ", " + loc.getBlockY() + ", " + loc.getBlockZ() + ", " + loc.getWorld().getName());
					
					wireRedstoneActiveLinks.add(loc);
					
					m.setLore(l);
					event.getPlayer().getEquipment().getItemInMainHand().setItemMeta(m);
					event.setCancelled(true);
				}
			}
			
			if(event.getClickedBlock().getType() == Material.DISPENSER)
			{
				if(Nonmetals.sleeping_bag.getRelatedBlock().CheckForCustomBlock(event.getClickedBlock()))
				{
					Location loc = event.getClickedBlock().getLocation();
					event.getPlayer().teleport(loc.add(0.5, 0, 1.5));
					
					event.getPlayer().setBedSpawnLocation(loc, true);
					
					event.getPlayer().sendMessage("Spawn point set");
				}
			}
		}
	}
	
	@EventHandler
	public void BreakWireLink(BlockBreakEvent event)
	{
		if(wireRedstoneActiveLinks.contains(event.getBlock().getLocation()))
		{
			if(event.getBlock().getType() == Material.REDSTONE_TORCH)
			{
				event.setDropItems(false);
				event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(), new ItemStack(Material.REDSTONE));
			}
			
			wireRedstoneActiveLinks.remove(event.getBlock().getLocation());
		}
	}
	
	@EventHandler
	public void ActiavteWireRedstone(BlockPlaceEvent event)
	{
		ItemStack i = event.getPlayer().getEquipment().getItemInMainHand();
		if(MyItems.wireless_redstone_activator.CheckForCustomItem(i))
		{
			Location loc = DecodeLocFromText(i.getItemMeta().getLore().get(0), ChatColor.GREEN + "Link: ");
			if(loc.getBlock().getType() == Material.REDSTONE_TORCH)
			{
				loc.getBlock().setType(Material.REDSTONE_WIRE);
			}else if(loc.getBlock().getType() == Material.REDSTONE_WIRE) {
				loc.getBlock().setType(Material.REDSTONE_TORCH);
			}
			
			event.setCancelled(true);
		}
	}
	
	private Location DecodeLocFromText(String text, String prefix)
	{
		String out = text.replace(prefix, "").replace(" ", "");
		String[] outs = out.split(",");
		
		if(outs.length < 4) {return null;}
		
		return new Location(Bukkit.getWorld(outs[3]), Integer.parseInt(outs[0]), Integer.parseInt(outs[1]), Integer.parseInt(outs[2]));
	}
	
	@EventHandler
	public void ClayDropZinc(BlockBreakEvent event)
	{
		if(event.getBlock().getType() == Material.CLAY)
		{
			if((new Random()).nextInt(101) < 30)
			{
				event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(), MyItems.zinc_ore.GetMyItemStack().clone());
				event.setDropItems(false);
			}
		}
		
		if(event.getBlock().getType() == Material.GRAVEL)
		{
			if((new Random()).nextInt(101) < 15)
			{
				event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(), MyItems.bauxite.GetMyItemStack().clone());
				event.setDropItems(false);
			}
		}
	}
	
	void ShootProjectile(final Player from, final float damage, final float range, final Particle particle, final Particle particle2, final float drop, final boolean fire)
	{
		ShootProjectile(from, damage, range, particle, particle2, drop, fire, " was killed by ");
	}
	
	void ShootProjectile(final Player from, final float damage, final float range, final Particle particle, final Particle particle2, final float drop, final boolean fire, final String message)
	{
		new BukkitRunnable()
		{
			double time = 0;
			Location loc = from.getLocation();
			Vector direction = loc.getDirection().normalize();
			@Override
			public void run()
			{
				time++;
				
                loc.add(new Vector(direction.getX()*2, direction.getY()*2, direction.getZ()*2));
				loc.add(0, 1.5, 0);
				
		    	loc.getWorld().spawnParticle(particle, loc, 5, 0.2f, 0.2f, 0.2f);
		    	loc.getWorld().spawnParticle(particle2, loc, 5, 0f, 0f, 0f);
		    	
				for(Entity e : loc.getWorld().getNearbyEntities(loc, 1f, 1f, 1f))
		    	{
		    		if(e == from) {continue;}
		    		if(!e.isDead())
		    		{ 
		    			((LivingEntity)e).damage(damage, from);
		    			
		    			if(e instanceof Player)
		    			{
		    				if(((LivingEntity)e).getHealth() <= 3f)
			    			{
			    				CustomDeathMessages.SetCustomDeathMessage(e.getName(), message.replace("{player}", e.getName()).replace("{player2}", from.getName()));
			    				((LivingEntity)e).damage(1000);
			    			}
		    			}
		    			
		    			((LivingEntity)e).setVelocity(from.getEyeLocation().getDirection().multiply(0.5f));
		    			
		    			if(fire) {((LivingEntity)e).setFireTicks(60);}
		    		}
		    	}
				
				loc.subtract(0, 1.5 + ((drop/2) * time), 0);
	               
                if (time == range){
                    this.cancel();
                }
               
			}
			
		}.runTaskTimer(com.willm.CoreMOD.Main.INSTANCE, 0, 1);
		
	}
	

	
	@EventHandler
	public void TaserEvent(EntityDamageByEntityEvent event)
	{
		if(event.getDamager() instanceof LivingEntity)
		{
			LivingEntity le = (LivingEntity)event.getDamager();
			if(MyItems.taser.CheckForCustomItem(le.getEquipment().getItemInMainHand()))
			{
				if(event.getEntity() instanceof LivingEntity)
				{
					Utils.PlayCustomSound("core_mod.taserelec", event.getEntity().getLocation());
					((LivingEntity)event.getEntity()).addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 40, 1));
					
					if(((LivingEntity)event.getEntity()).getHealth() < 5f)
					{
						((LivingEntity)event.getEntity()).damage(1000);
						CustomDeathMessages.SetCustomDeathMessage(event.getEntity().getName(), event.getEntity().getName() + " was shocked to death by " + event.getDamager().getName());
					}
				}
			}
		}
	}
	
	@EventHandler
	public void MineBlockDrills(BlockBreakEvent event)
	{
		ItemStack is = event.getPlayer().getEquipment().getItemInMainHand();
		if(is != null)
		{
			if(MyItems.stripmine_drill.CheckForCustomItem(is))
			{
				Utils.PlayCustomSound("core_mod.drill_idle", event.getPlayer().getLocation());
				event.getBlock().getWorld().playSound(event.getBlock().getLocation(), Sound.BLOCK_STONE_BREAK, 1f, 1f);
				event.getBlock().getWorld().playSound(event.getBlock().getLocation(), Sound.BLOCK_STONE_BREAK, 1f, 1f);
				event.getBlock().getWorld().playSound(event.getBlock().getLocation(), Sound.BLOCK_STONE_BREAK, 1f, 1f);
				event.getBlock().getWorld().playSound(event.getBlock().getLocation(), Sound.BLOCK_STONE_BREAK, 1f, 1f);
				event.getBlock().getWorld().playSound(event.getBlock().getLocation(), Sound.BLOCK_STONE_BREAK, 1f, 1f);
				event.getBlock().getWorld().playSound(event.getBlock().getLocation(), Sound.BLOCK_STONE_BREAK, 1f, 1f);
				event.getBlock().getWorld().playSound(event.getBlock().getLocation(), Sound.BLOCK_STONE_BREAK, 1f, 1f);
				event.getBlock().getWorld().playSound(event.getBlock().getLocation(), Sound.BLOCK_STONE_BREAK, 1f, 1f);


				if(event.getPlayer().getLocation().getBlockY() < event.getBlock().getLocation().getBlockY())
				{
					BreakRelative(event.getBlock(), BlockFace.DOWN);
				}else {
					BreakRelative(event.getBlock(), BlockFace.UP);
				}
			}
			
			if(MyItems.drill.CheckForCustomItem(is))
			{
				Utils.PlayCustomSound("core_mod.drill_idle", event.getPlayer().getLocation());
				event.getBlock().getWorld().playSound(event.getBlock().getLocation(), Sound.BLOCK_STONE_BREAK, 1f, 1f);
				event.getBlock().getWorld().playSound(event.getBlock().getLocation(), Sound.BLOCK_STONE_BREAK, 1f, 1f);
				event.getBlock().getWorld().playSound(event.getBlock().getLocation(), Sound.BLOCK_STONE_BREAK, 1f, 1f);
				event.getBlock().getWorld().playSound(event.getBlock().getLocation(), Sound.BLOCK_STONE_BREAK, 1f, 1f);
				event.getBlock().getWorld().playSound(event.getBlock().getLocation(), Sound.BLOCK_STONE_BREAK, 1f, 1f);
				event.getBlock().getWorld().playSound(event.getBlock().getLocation(), Sound.BLOCK_STONE_BREAK, 1f, 1f);
				event.getBlock().getWorld().playSound(event.getBlock().getLocation(), Sound.BLOCK_STONE_BREAK, 1f, 1f);
				event.getBlock().getWorld().playSound(event.getBlock().getLocation(), Sound.BLOCK_STONE_BREAK, 1f, 1f);

				BreakRelative(event.getBlock(), BlockFace.UP);
				BreakRelative(event.getBlock(), BlockFace.DOWN);
				BreakRelative(event.getBlock(), BlockFace.EAST);
				BreakRelative(event.getBlock(), BlockFace.WEST);
				BreakRelative(event.getBlock(), BlockFace.SOUTH);
				BreakRelative(event.getBlock(), BlockFace.NORTH);
			}
			
			if(MyItems.enforced_drill.CheckForCustomItem(is))
			{
				Utils.PlayCustomSound("core_mod.drill_idle", event.getPlayer().getLocation());

				BreakRelative(event.getBlock(), BlockFace.UP);
				BreakRelative(event.getBlock(), BlockFace.DOWN);
				BreakRelative(event.getBlock(), BlockFace.EAST);
				BreakRelative(event.getBlock(), BlockFace.WEST);
				BreakRelative(event.getBlock(), BlockFace.SOUTH);
				BreakRelative(event.getBlock(), BlockFace.NORTH);
			
				
				BreakRelative(event.getBlock(), BlockFace.NORTH_EAST);
				BreakRelative(event.getBlock(), BlockFace.NORTH_WEST);
				
				BreakRelative(event.getBlock(), BlockFace.SOUTH_EAST);
				BreakRelative(event.getBlock(), BlockFace.SOUTH_WEST);
				
				BreakRelative(event.getBlock().getRelative(BlockFace.UP), BlockFace.NORTH_EAST);
				BreakRelative(event.getBlock().getRelative(BlockFace.UP), BlockFace.NORTH_WEST);
				BreakRelative(event.getBlock().getRelative(BlockFace.UP), BlockFace.SOUTH_EAST);
				BreakRelative(event.getBlock().getRelative(BlockFace.UP), BlockFace.SOUTH_WEST);
				BreakRelative(event.getBlock().getRelative(BlockFace.UP), BlockFace.EAST);
				BreakRelative(event.getBlock().getRelative(BlockFace.UP), BlockFace.WEST);
				BreakRelative(event.getBlock().getRelative(BlockFace.UP), BlockFace.SOUTH);
				BreakRelative(event.getBlock().getRelative(BlockFace.UP), BlockFace.NORTH);
				
				BreakRelative(event.getBlock().getRelative(BlockFace.DOWN), BlockFace.NORTH_EAST);
				BreakRelative(event.getBlock().getRelative(BlockFace.DOWN), BlockFace.NORTH_WEST);
				BreakRelative(event.getBlock().getRelative(BlockFace.DOWN), BlockFace.SOUTH_EAST);
				BreakRelative(event.getBlock().getRelative(BlockFace.DOWN), BlockFace.SOUTH_WEST);
				BreakRelative(event.getBlock().getRelative(BlockFace.DOWN), BlockFace.EAST);
				BreakRelative(event.getBlock().getRelative(BlockFace.DOWN), BlockFace.WEST);
				BreakRelative(event.getBlock().getRelative(BlockFace.DOWN), BlockFace.SOUTH);
				BreakRelative(event.getBlock().getRelative(BlockFace.DOWN), BlockFace.NORTH);
				
			}
		}
	}
	
	public ItemEvents()
	{
		CreateSilkPick();
	}
	
	static ItemStack silkPick;
	public static void CreateSilkPick()
	{
		silkPick = new ItemStack(Material.DIAMOND_PICKAXE, 1);
		ItemMeta im = silkPick.getItemMeta();
		im.addEnchant(Enchantment.SILK_TOUCH, 1, false);
		silkPick.setItemMeta(im);
	}
	
	void BreakRelative(Block b, BlockFace bf)
	{
		boolean ore = false;
		for(Ore o : CustomPopulator.ores)
		{
			if(b.getRelative(bf).getType() == o.drop.GetMyItemStack().getType())
			{
				b.getWorld().dropItemNaturally(b.getRelative(bf).getLocation(), o.drop.GetMyItemStack());
				b.getWorld().playSound(b.getRelative(bf).getLocation(), Sound.BLOCK_STONE_BREAK, 1f, 1f);
				b.getWorld().spawnParticle(Particle.BLOCK_DUST, b.getRelative(bf).getLocation(), 60, b.getRelative(bf).getBlockData());
				
				b.getRelative(bf).setType(Material.AIR);
				ore = true;
			}
		}
		
		if(!ore)
		{
			if(b.getRelative(bf).getType() == Material.DISPENSER)
			{
				for(CustomBlock cb : com.willm.ModAPI.Main.CustomBlockRegistry)
				{
					if(cb.CheckForCustomBlock(b.getRelative(bf)))
					{
						b.getWorld().playSound(b.getRelative(bf).getLocation(), Sound.BLOCK_STONE_BREAK, 1f, 1f);
						b.getWorld().spawnParticle(Particle.BLOCK_DUST, b.getRelative(bf).getLocation(), 60, b.getRelative(bf).getBlockData());
						cb.Remove(b.getRelative(bf).getLocation(), true);
					}
				}
			}
			else {
				if(b.getRelative(bf).getType() != Material.DISPENSER && !ore)
				{
					/*
					for(ItemStack is : b.getDrops())
					{
						b.getWorld().dropItemNaturally(b.getRelative(bf).getLocation(), is);
					}
					*/
					b.getWorld().playSound(b.getRelative(bf).getLocation(), Sound.BLOCK_STONE_BREAK, 1f, 1f);
					b.getWorld().spawnParticle(Particle.BLOCK_DUST, b.getRelative(bf).getLocation(), 60, b.getRelative(bf).getBlockData());
					b.getRelative(bf).breakNaturally();
				}
			}
		}

		
	}
}
