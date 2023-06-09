package com.willm.CoreMOD;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Dispenser;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;

import com.willm.ModAPI.Utils;
import com.willm.ModAPI.Blocks.CustomBlock;
import com.willm.ModAPI.Items.CustomItemStack;

public class BlockEvents implements Listener {

	public static HashMap<ItemStack, ItemStack> CuttingBoardRecipes = new HashMap<ItemStack, ItemStack>();
	private static Random randomInteractables = new Random();
	
	@EventHandler
	public void ImportPack(PlayerJoinEvent event)
	{
		event.getPlayer().setResourcePack("https://drive.google.com/uc?export=download&id=1XgWVqLBBOBcxbI0-LTy92Cuh0HlU11Tb");
	}
	
	@EventHandler
	public void UseInteractableMachine(PlayerInteractEvent event)
	{
		if(event.getAction() == Action.RIGHT_CLICK_BLOCK)
		{
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
								boolean check_b = true;
								
								if(or.usePlate != isPlate) {check_b = false;}
								
								for(ItemStack check : or.inputs)
								{
									if(!d.getInventory().containsAtLeast(check, check.getAmount()))
									{
										check_b = false;
										break;
									}
								}
								
								if(check_b)
								{
									event.getClickedBlock().getWorld().dropItemNaturally(event.getClickedBlock().getLocation(), or.out);
									RemoveOrSubtractFromPlayersHand(event.getPlayer(), 1);
									d.getInventory().removeItem(or.inputs);
									
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
							if((hi.getType() == Material.COOKED_BEEF && !hi.getItemMeta().hasCustomModelData()) || MyItems.tomato.CheckForCustomItem(hi) || MyItems.bacon.CheckForCustomItem(hi) || MyItems.tomato_sauce.CheckForCustomItem(hi) || MyItems.spare_rib.CheckForCustomItem(hi) || MyItems.rib_eye.CheckForCustomItem(hi) || MyItems.bbq_sauce_smokey.CheckForCustomItem(hi) || MyItems.beef_broth.CheckForCustomItem(hi))
							{
								SetOven(event, hi, MyItems.oven_red.getRelatedBlock());
							}
							
							//ITEMS THAT MAKE OVEN TURN BLUE
							if((hi.getType() == Material.WATER_BUCKET && !hi.getItemMeta().hasCustomModelData()) || MyItems.syrup.CheckForCustomItem(hi) || MyItems.sap.CheckForCustomItem(hi) || MyItems.cider_vinegar.CheckForCustomItem(hi) || MyItems.chopped_carrots.CheckForCustomItem(hi) || MyItems.pepper_sack.CheckForCustomItem(hi))
							{
								SetOven(event, hi, MyItems.oven_blue.getRelatedBlock());
							}
							
							//ITEMS THAT MAKE OVEN TURN TAN
							if(MyItems.spaghetti.CheckForCustomItem(hi) || MyItems.lamb.CheckForCustomItem(hi) || MyItems.ham.CheckForCustomItem(hi))
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
