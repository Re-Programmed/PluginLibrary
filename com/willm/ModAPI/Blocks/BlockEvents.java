package com.willm.ModAPI.Blocks;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Dispenser;
import org.bukkit.block.Sign;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.willm.CoreMOD.ItemEvents;
import com.willm.ModAPI.Main;
import com.willm.ModAPI.MobDrop;
import com.willm.ModAPI.Utils;
import com.willm.ModAPI.Commands.CreativeMenu;
import com.willm.ModAPI.Commands.CreativeMenuInventory;
import com.willm.ModAPI.Items.CustomItemStack;
import com.willm.ModAPI.Items.Plant;

public class BlockEvents implements Listener {

	static HashMap<CustomItemStack, LiquidBlock> buckets = new HashMap<CustomItemStack, LiquidBlock>();
	
	public static void RegisterBucket(CustomItemStack cis, LiquidBlock lb)
	{
		buckets.put(cis, lb);
	}
	
	@EventHandler
	public void SearchSignPreventBreak(BlockBreakEvent event)
	{
		for(CreativeMenuInventory myInventory : CreativeMenu.myInventories)
	    {
	    	Sign s = myInventory.MySign;
			if(s != null && s.getLocation().distance(event.getBlock().getLocation()) < 0.75)
			{
				event.setCancelled(true);
			}
	    }
	}
	
	//Called after a sign is used to search in a creative menu.
	@EventHandler
	public void SearchSign(SignChangeEvent e) {
	    for(CreativeMenuInventory myInventory : CreativeMenu.myInventories)
	    {
	    	Sign s = myInventory.MySign;
			if(s != null && s.getLocation().distance(e.getBlock().getLocation()) < 0.75)
			{
				if(!e.getLine(0).isEmpty()) {
					CreativeMenu.OpenSearch(e.getLine(0), e.getPlayer());
				}
				
				e.getBlock().setType(Material.AIR);
				myInventory.MySign = null;
				e.setCancelled(true);
				return;
			}
			
	    }
	}
	
	@EventHandler
	public void ClickCancel(InventoryClickEvent event)
	{
		ArrayList<Integer> rem = new ArrayList<Integer>();
		int i = 0;
		for(CreativeMenuInventory myInventory : CreativeMenu.myInventories)
		{
			if(myInventory != null)
			{
				if(myInventory.getInventory().getViewers().size() > 0)
				{
					if(event.getClickedInventory().getViewers().get(0).getName().equalsIgnoreCase(myInventory.getInventory().getViewers().get(0).getName()))
					{
						if(event.getClickedInventory().getViewers().size() > 0)
						{
							if(event.getSlot() < 40)
							{
								if(!myInventory.Player.isOp())
								{
									Bukkit.dispatchCommand(event.getWhoClicked(), "recipes item:" + event.getInventory().getItem(event.getSlot()).getItemMeta().getDisplayName().replace(ChatColor.WHITE + "", "").replace(' ', '_').toLowerCase());
									event.setCancelled(true);
								}
							}
						}
					}
				}else if(myInventory.MySign == null) {
					rem.add(i);
				}
			}else {
				rem.add(i);
			}
			
			i++;
		}
		
		for(Integer r : rem)
		{
			CreativeMenu.myInventories.remove(r.intValue());
		}
	}
	
	boolean cooldown = false;
	@EventHandler
	public void PlayerInteract_Bucket(PlayerInteractEvent event)
	{
		if(event.getAction() == Action.RIGHT_CLICK_BLOCK)
		{
			if(event.getItem() != null)
			{
				if(event.getClickedBlock().getType() == Material.WARPED_TRAPDOOR) {return;}
				
				if(cooldown) {cooldown = false; return;}
				cooldown = true;
				if(event.getItem().getType() == Material.BUCKET)
				{
					if(LiquidBlock.noDrop.contains(event.getClickedBlock().getLocation())) {return;}
					for(Entry<CustomItemStack, LiquidBlock> e : buckets.entrySet())
					{
						if(e.getValue().CheckForCustomBlock(event.getClickedBlock()))
						{
							e.getValue().Remove(event.getClickedBlock().getLocation());
							
							ItemStack mh = event.getPlayer().getEquipment().getItemInMainHand();
							if(mh.getAmount() > 1)
							{
								mh.setAmount(mh.getAmount() - 1);
								event.getPlayer().getEquipment().setItemInMainHand(mh);
								
								event.getPlayer().getWorld().dropItem(event.getPlayer().getLocation(), e.getKey().GetMyItemStack()).setPickupDelay(0);
							}else {
								event.getPlayer().getEquipment().setItemInMainHand(e.getKey().GetMyItemStack());
							}
						}
					}
					return;
				}
				
				for(Entry<CustomItemStack, LiquidBlock> e : buckets.entrySet())
				{
					if(e.getKey().CheckForCustomItem(event.getItem()))
					{
						for(Material m : Main.replaceable_liquid)
						{
							if(m == event.getClickedBlock().getRelative(event.getBlockFace()).getType())
							{
								e.getValue().Place(event.getClickedBlock().getRelative(event.getBlockFace()).getLocation());
								
								ItemStack mh = event.getPlayer().getEquipment().getItemInMainHand();
								if(mh.getAmount() > 1)
								{
									mh.setAmount(mh.getAmount() - 1);
									event.getPlayer().getEquipment().setItemInMainHand(mh);
									
									event.getPlayer().getWorld().dropItem(event.getPlayer().getLocation(), new ItemStack(Material.BUCKET)).setPickupDelay(0);
								}else {
									event.getPlayer().getEquipment().setItemInMainHand(new ItemStack(Material.BUCKET));
								}
								return;
							}
						}
					}
				}
			}
		}
	}
	
	@EventHandler
	public void ItemDrop(ItemSpawnEvent event)
	{
		if(event.getEntity().getItemStack().getType() == Material.LIGHT_WEIGHTED_PRESSURE_PLATE)
		{
			event.setCancelled(true);
		}
	}
	
	@EventHandler
	public void NoWeigtedPlates(BlockBreakEvent event)
	{
		if(event.getBlock().getType() == Material.LIGHT_WEIGHTED_PRESSURE_PLATE)
		{
			event.setCancelled(true);
		}
	}
	
	@EventHandler
	public void BlockExplode(BlockExplodeEvent event)
	{
		final List<Block> blocks = event.blockList();
		int i = 0;
		for(Block b : blocks)
		{
			if(b.getType() == Material.DISPENSER)
			{
				event.blockList().remove(i);
			}
			i++;
		}
	}
	
	@EventHandler
	public void EntityExplode(EntityExplodeEvent event)
	{
		final List<Block> blocks = event.blockList();
		int i = 0;
		for(Block b : blocks)
		{
			if(b.getType() == Material.DISPENSER)
			{
				event.blockList().remove(i);
			}
			i++;
		}
	}
	
	@EventHandler
	public void GivePlayersRecipes(PlayerJoinEvent event)
	{
		Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "recipe give @a *");
	}
	
	@EventHandler
	public void MineBlock(BlockBreakEvent event)
	{/*
		if(event.getBlock().getType() == Material.GLASS)
		{

				for(Entity e : event.getBlock().getWorld().getNearbyEntities(Utils.AddToLocationAsNew(event.getBlock().getLocation(), 0.5f, 0, 0.5f), 0.2f, 0.2f, 0.2f))
				{
					if(e.getType() == EntityType.ARMOR_STAND)
					{
						ArmorStand as = (ArmorStand)e;
						
						if(as.getEquipment().getHelmet() != null)
						{
							if(as.getEquipment().getHelmet().getItemMeta().hasCustomModelData())
							{
								int cmd = as.getEquipment().getHelmet().getItemMeta().getCustomModelData();
								for(CustomBlock b : Main.CustomBlockRegistry)
								{
									if(b.getDisplayCustomModelData() == cmd)
									{
										b.Remove(event);
										break;
									}

									
								}
							}
						}

					}
				}
				
				
			
		}*/
		for(Plant p : Main.PlantRegistry)
		{
			int i = 0;
			for(CustomBlock cb : p.GetCustomBlock())
			{
				if(cb.CheckForCustomBlock(event.getBlock()))
				{
					p.BreakPlant(event, i);
					cb.SetDrops(false).Remove(event);
					event.setDropItems(false);
					p.FinishBreakPlant(event, i);
					return;
				}
				i++;
			}
		}
		
		if(event.getBlock().getType() == Material.DISPENSER)
		{
			
			Dispenser d = (Dispenser)event.getBlock().getState();
		
			
			for(CustomBlock b : Main.CustomBlockRegistry)
			{
				if(d.getLock() != null && d.getLock() != "")
				{
					if(d.getLock().equalsIgnoreCase(b.getTag()))
					{
						b.Remove(event);
						break;
					}
				}
			}
			
			return;
			
		}
		
		for(Entity e : event.getBlock().getWorld().getNearbyEntities(Utils.AddToLocationAsNew(event.getBlock().getLocation(), 0.5f, 0, 0.5f), 0.2f, 0.2f, 0.2f))
		{
			if(e.getType() == EntityType.ARMOR_STAND)
			{
				ArmorStand as = (ArmorStand)e;
				
				if(as.getEquipment().getHelmet() != null)
				{
					if(as.getEquipment().getHelmet().getItemMeta().hasCustomModelData())
					{
						for(CustomBlock b : Main.CustomBlockRegistry)
						{
							if(b.CheckForCustomBlock(as.getLocation().getBlock()))
							{
								b.Remove(event);
								break;
							}

							
						}
					}
				}

			}
		}
	}
	
	@EventHandler
	public void PlayerStartMining(PlayerInteractEvent event)
	{
		if(event.getAction() != Action.LEFT_CLICK_BLOCK) {return;}
		
		if(event.getClickedBlock() != null)
		{
			if(event.getClickedBlock().getType() == Material.DISPENSER || event.getClickedBlock().getType() == Material.GLASS)
			{
				for(CustomBlock b : Main.CustomBlockRegistry)
				{
					if(b.CheckForCustomBlock(event.getClickedBlock()))
					{
						b.InitMineAs(event.getClickedBlock());
					}
				}
			}
		}
	}
	
	@EventHandler
	public void ConsumeCustomFood(PlayerItemConsumeEvent event)
	{
		for(CustomItemStack cis : Main.ConsumableRegistry)
		{
			if(cis.CheckForCustomItem(event.getItem()))
			{
				int[] f = GetFoodFromLore(event.getItem());
				double a = event.getPlayer().getAbsorptionAmount() + f[1];
				if(a < 15)
				{
					event.getPlayer().setAbsorptionAmount(a);
				}
				event.getPlayer().setFoodLevel(event.getPlayer().getFoodLevel() + f[0]);
			}
		}
	}
	
	//Returns new int[] {FOOD_LEVEL, SATURATION_LEVEL}
	private int[] GetFoodFromLore(ItemStack is)
	{
		ItemMeta im = is.getItemMeta();
		List<String> lore = im.getLore();
		
		for(String s : lore)
		{
			if(s.startsWith(ChatColor.YELLOW + ""))
			{
				if(s.contains(" Food, "))
				{
					String[] r = s.replace(ChatColor.YELLOW + "", "").replace(" Food, ", " ").replace(" Saturation", "").split(" ");
					return new int[] {Integer.parseInt(r[0]), Integer.parseInt(r[1])};
				}
			}
		}
		
		return new int[] {0, 0};
	}
	
	private static final Random random_mobs = new Random();
	
	@EventHandler
	public void DoCustomMobDrops(EntityDeathEvent event)
	{
		for(Entry<MobDrop, ItemStack> entry : Main.customMobDrops.entrySet())
		{
			if(event.getEntityType() == entry.getKey().Type)
			{
				if(random_mobs.nextInt(1001) < entry.getKey().Chance)
				{
					event.getEntity().getWorld().dropItemNaturally(event.getEntity().getLocation(), entry.getValue());
				}
			}
		}
	}
	
	@EventHandler
	public void CheckBlockInteraction(PlayerInteractEvent event)
	{
		if(event.getAction() == Action.RIGHT_CLICK_BLOCK)
		{
			if(event.getClickedBlock().getType() == Material.DISPENSER)
			{
				if(event.getPlayer().getEquipment().getItemInMainHand().getType() == Material.BONE_MEAL)
				{
					for(Plant p : Main.PlantRegistry)
					{
						for(CustomBlock cb : p.GetCustomBlock())
						{
							if(cb.CheckForCustomBlock(event.getClickedBlock()))
							{
								p.GrowPlant(event.getClickedBlock().getLocation());
								ItemStack bone = event.getPlayer().getEquipment().getItemInMainHand();
								if(bone.getAmount() == 1)
								{
									event.getPlayer().getEquipment().setItemInMainHand(null);
								}else {
									bone.setAmount(bone.getAmount() - 1);
								}
								return;
							}
						}
					}
				}
				
				
				Dispenser d = (Dispenser)event.getClickedBlock().getState();
				
				for(CustomBlock b : Main.CustomBlockRegistry)
				{
					if(d.getLock() != null && d.getLock() != "")
					{
						if(d.getLock().equalsIgnoreCase(b.getTag()))
						{
							b.Interact(event);
							break;
						}
					}
				}
			}
		}
	}
	
	@EventHandler
	public void PlaceBlock(BlockPlaceEvent event)
	{
		for(CustomBlock b : Main.CustomBlockRegistry)
		{
			if(b.getRootItem().getType() == event.getBlock().getType())
			{
				if(event.getPlayer().getEquipment().getItemInMainHand().hasItemMeta())
				{
					if(event.getPlayer().getEquipment().getItemInMainHand().getItemMeta().hasCustomModelData())
					{
						if(b.getRootItem().getCustomModelData() == event.getPlayer().getEquipment().getItemInMainHand().getItemMeta().getCustomModelData())
						{
							if(b.Directional == BlockDirectionData.FACE_RELATIVE)
							{
								if(event.getBlock().getRelative(BlockFace.EAST).equals(event.getBlockAgainst()))
								{
									b.sidewaysBlockData.Place(event.getBlock().getLocation(), BlockFace.EAST);
									return;
								}
								
								if(event.getBlock().getRelative(BlockFace.WEST).equals(event.getBlockAgainst()))
								{
									b.sidewaysBlockData.Place(event.getBlock().getLocation(), BlockFace.WEST);
									return;
								}
								
								if(event.getBlock().getRelative(BlockFace.SOUTH).equals(event.getBlockAgainst()))
								{
									b.sidewaysBlockData.Place(event.getBlock().getLocation(), BlockFace.SOUTH);
									return;
								}
								
								if(event.getBlock().getRelative(BlockFace.NORTH).equals(event.getBlockAgainst()))
								{
									b.sidewaysBlockData.Place(event.getBlock().getLocation(), BlockFace.NORTH);
									return;
								}
							}
							
							if(b.Directional == BlockDirectionData.PLAYER_RELATIVE || b.Directional == BlockDirectionData.INVERSE_PLAYER_RELATIVE)
							{
								if(event.getPlayer().getFacing() == BlockFace.SOUTH)
								{
									b.Place(event.getBlock().getLocation(), event.getPlayer().getFacing(), b.Directional == BlockDirectionData.INVERSE_PLAYER_RELATIVE ? 0.f : 180.f);
									return;
								}
								
								if(b.Directional == BlockDirectionData.INVERSE_PLAYER_RELATIVE)
								{
									if(event.getPlayer().getFacing() == BlockFace.NORTH)
									{
										b.Place(event.getBlock().getLocation(), event.getPlayer().getFacing(), 180.f);
										return;
									}
								}
								
								b.Place(event.getBlock().getLocation(), b.Directional == BlockDirectionData.INVERSE_PLAYER_RELATIVE ? event.getPlayer().getFacing().getOppositeFace() : event.getPlayer().getFacing());
								return;
							}
							
							
							b.Place(event.getBlock().getLocation());
						}	
					}
				}
			}
		}
	}
	
	@EventHandler
	public void PlayerPlantCrop(PlayerInteractEvent event)
	{
		if(event.getAction() != Action.RIGHT_CLICK_BLOCK) {return;}
		if(event.getItem() == null) {return;}
		if(event.getClickedBlock().getType() == Material.FARMLAND)
		{
			for(Plant p : Main.PlantRegistry)
			{
				if(p.GetCropSource().CheckForCustomItem(event.getItem()))
				{
					BlockFace bf = BlockFace.NORTH;
					
					int rand = new Random().nextInt(4);
					
					switch(rand)
					{
					case 0:
						bf = BlockFace.NORTH;
						break;
					case 1:
						bf = BlockFace.EAST;
						break;
					case 2:
						bf = BlockFace.SOUTH;
						break;
					case 3:
						bf = BlockFace.WEST;
						break;
					}
					
					p.GetCustomBlock()[p.GetCustomBlock().length - 1].Place(event.getClickedBlock().getLocation(), bf);
					
					ItemStack mh = event.getPlayer().getEquipment().getItemInMainHand();
					if(mh.getAmount() > 1)
					{
						mh.setAmount(mh.getAmount() - 1);
					}else {
						event.getPlayer().getEquipment().setItemInMainHand(null);
					}
				}
			}
		}
	}
	
	
}
