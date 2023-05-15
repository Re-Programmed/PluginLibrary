package com.willm.CoreMOD;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import com.willm.CoreMOD.ElementalItems.Nonmetals;
import com.willm.CoreMOD.ElementalItems.RegisterElementalItems;
import com.willm.CoreMOD.Power.Injector;
import com.willm.ModAPI.CustomDeathMessages;
import com.willm.ModAPI.Utils;
import com.willm.ModAPI.Blocks.CustomBlock;
import com.willm.ModAPI.Items.CustomItemStack;
import com.willm.ModAPI.Terrain.CustomPopulator;
import com.willm.ModAPI.Terrain.Ore;

import net.md_5.bungee.api.ChatColor;

public class ItemEvents implements Listener {

	private ArrayList<Location> wireRedstoneActiveLinks = new ArrayList<Location>();
	
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
					b.getRelative(bf).breakNaturally(silkPick);
				}
			}
		}

		
	}
}
