package com.willm.CoreMOD.PokimaneBoss;

import java.util.Map;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Directional;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wolf;
import org.bukkit.entity.Zombie;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import com.willm.CoreMOD.ItemEvents;
import com.willm.ModAPI.Main;
import com.willm.ModAPI.Utils;
import com.willm.ModAPI.Entities.BossEntity;
import com.willm.ModAPI.Entities.BossFightEvent;
import com.willm.ModAPI.Items.CustomItemStack;

public class PokimaneBoss extends BossEntity {

	private static final int PK_BOSS_HEALTH = 1000;
	
	private int currentBossPhase = 0;
		
	public PokimaneBoss(String name, EntityType type, int maxHealth) {
		super(name, type, PK_BOSS_HEALTH);
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public Entity Summon(Location location) {
		Entity e = super.Summon(location);
		e.setSilent(true);
		e.setPersistent(true);
		Zombie z = (Zombie)e;
		z.setBaby(false);
		BeginBossFight((LivingEntity)e);
		return e;
	}

	@SuppressWarnings("deprecation")
	public Entity Summon(Location location, boolean noBegin) {
		Entity e = super.Summon(location);
		e.setPersistent(true);
		Zombie z = (Zombie)e;
		z.setBaby(false);
		e.setSilent(true);
		return e;
	}
	
	@Override
	public void BeginBossFight(LivingEntity entity) {
		currentBossPhase = 0;
		super.BeginBossFight(entity);
		Utils.PlayCustomSound("core_mod.pk.pokimane_begin", entity.getLocation());
		DoPokimaneSpawnSequence();
	}

	@Override
	protected BossBar createBossBar() {
		return Bukkit.createBossBar(ChatColor.DARK_PURPLE + "" + ChatColor.UNDERLINE + "" + ChatColor.BOLD + "Pokimane", BarColor.PINK, BarStyle.SEGMENTED_6, BarFlag.CREATE_FOG, BarFlag.DARKEN_SKY, BarFlag.PLAY_BOSS_MUSIC);
	}
	
	private boolean timeBombActive = false;

	private int lastAtk = 0;
	private Random bossRNG = new Random();
	@Override
	public void BossFightTick()
	{
		currentFight.ActiveBossBar.setProgress(currentFight.ActiveBossFightEntity.getHealth() / (double)PK_BOSS_HEALTH);
		
		currentFight.ActiveBossFightEntity.setCustomName(currentBossPhase == 1 ? "Stripper Pokimane" : currentBossPhase == 2 ? "Furry Pokimane" : this.GetName());
		currentFight.ActiveBossFightEntity.setFireTicks(0);
		if(currentBossPhase == 0 || currentBossPhase == 3)
		{
			lastAtk++;
			if(bossRNG.nextInt((int)(6001.0 * ((double)currentFight.ActiveBossFightEntity.getHealth() / PK_BOSS_HEALTH))) < lastAtk + ((((double)currentFight.ActiveBossFightEntity.getHealth() / PK_BOSS_HEALTH)) < 0.5 ? 10 : 0))
			{				
				for(int i = 0; i < bossRNG.nextInt(4) + 1; i++)
				{
					if(i == 0)
					{
						LaunchFoot(i);
					}else if(i < 2) {
						LaunchFoot((i) * 20);
					}else {
						LaunchFoot(-20);
					}
				}
				
				lastAtk = 0;
				
				return;
			}
		}
			
		if(currentBossPhase == 1 || currentBossPhase == 3)
		{
			
			if(currentFight.ActiveBossFightEntity.getEquipment().getItemInMainHand() != null && currentFight.ActiveBossFightEntity.getEquipment().getItemInMainHand().getType() != Material.AIR)
			{
				

			
				if(bossRNG.nextInt(1000) < 12 - (((double)currentFight.ActiveBossFightEntity.getHealth() / PK_BOSS_HEALTH) * 10) && !timeBombActive)
				{
					timeBombActive = true;
					
					Utils.StopCustomSound("core_mod.pk.st_music");
					Utils.PlayCustomSound("core_mod.pk.st_music", currentFight.ActiveBossFightEntity.getLocation());

					Location randLoc = currentFight.ActiveBossFightEntity.getLocation();
					
					randLoc.setX(randLoc.getX() + bossRNG.nextInt(10) - 5);
					randLoc.setZ(randLoc.getZ() + bossRNG.nextInt(10) - 5);
					randLoc.setY(randLoc.getWorld().getHighestBlockAt(randLoc).getY());
					
					randLoc.getBlock().getRelative(BlockFace.UP).setType(Material.STONE);
					randLoc.getBlock().getRelative(BlockFace.UP).getRelative(BlockFace.UP).setType(Material.STONE);
					
					randLoc.getBlock().getRelative(BlockFace.UP).setType(Material.END_ROD);
					randLoc.getBlock().getRelative(BlockFace.UP).getRelative(BlockFace.UP).setType(Material.END_ROD);
					Block tRod = randLoc.getBlock().getRelative(BlockFace.UP).getRelative(BlockFace.UP);
					Directional tRodData = (Directional)tRod.getBlockData();
					tRodData.setFacing(BlockFace.DOWN);
					tRod.setBlockData(tRodData);
					
					for(Player p : Bukkit.getOnlinePlayers())
					{
						if(p.getLocation().distance(tRod.getLocation()) < 60f)
						{
							double power = p.getLocation().distance(tRod.getLocation());
							
							Vector v = p.getLocation().getDirection();
							v.rotateAroundY(90);
							Vector pull = new Vector(tRod.getX() - v.getX(), tRod.getY() - v.getY(), tRod.getZ() - v.getZ()).normalize().multiply(power/5);
							pull.setY(1);

							p.setVelocity(pull);
						}
					}
					
					randLoc.getWorld().strikeLightning(randLoc);
					randLoc.getWorld().strikeLightning(randLoc);
					randLoc.getWorld().strikeLightning(randLoc);
					randLoc.getWorld().strikeLightning(randLoc);

					Utils.PlayCustomSound("core_mod.pk.ticking", randLoc);
					Utils.PlayCustomSound("core_mod.pk.ticking", randLoc);

					new BukkitRunnable() {
						int i = 0;
						
						@Override
						public void run() {
							randLoc.getWorld().strikeLightning(randLoc);
							
							randLoc.getWorld().spawnParticle(Particle.FLASH, randLoc, 60, 0.4, 5, 0.4);
							randLoc.getWorld().spawnParticle(Particle.SONIC_BOOM, randLoc, 60, 0.4, 5, 0.4);

							if(i > 40)
							{
								randLoc.getWorld().strikeLightning(randLoc);
								Utils.PlayCustomSound("core_mod.pk.splat", randLoc);
								Utils.PlayCustomSound("core_mod.pk.splat", randLoc);
								Utils.PlayCustomSound("core_mod.pk.splat", randLoc);
								Utils.PlayCustomSound("core_mod.pk.splat", randLoc);
								Utils.PlayCustomSound("core_mod.pk.splat", randLoc);
								Utils.PlayCustomSound("core_mod.pk.splat", randLoc);

								randLoc.getWorld().createExplosion(randLoc, 6f, true, true);
								
								for(Player p : Bukkit.getOnlinePlayers())
								{
									if(p.getLocation().distance(randLoc) < 9f)
									{
										p.damage(9f - p.getLocation().distance(randLoc) + 2f);
									}
								}
								randLoc.getWorld().spawnParticle(Particle.SONIC_BOOM, randLoc, 220, 6, 5, 6);
								randLoc.getWorld().spawnParticle(Particle.FLASH, randLoc, 200, 6, 5, 6);
								randLoc.getWorld().spawnParticle(Particle.CAMPFIRE_COSY_SMOKE, randLoc, 60, 6, 5, 6);
								randLoc.getWorld().spawnParticle(Particle.CAMPFIRE_COSY_SMOKE, randLoc, 120, 6, 3, 6, 0);

								new BukkitRunnable() {
									
									@Override
									public void run() {									
										for(int x = -4; x <= 4; x++)
										{
											for(int z = -4; z <= 4; z++)
											{
												Location loc = randLoc.clone();
												loc.setX(loc.getX() + x);
												loc.setZ(loc.getZ() + z);
												
												loc.setY(loc.getWorld().getHighestBlockYAt(loc));
												
												loc.getBlock().setType(Material.SNOW);
											}
										}
									}
								}.runTaskLater(Main.PLUGIN, 5);
								
								timeBombActive = false;
								
								this.cancel();
							}
							
							i++;
						}
					}.runTaskTimer(Main.PLUGIN, 3, 3);
				}
				
				lastAtk++;
				if(bossRNG.nextInt((int)(6101.0 * ((double)currentFight.ActiveBossFightEntity.getHealth() / PK_BOSS_HEALTH))) < lastAtk + ((((double)currentFight.ActiveBossFightEntity.getHealth() / PK_BOSS_HEALTH)) < 0.5 ? 10 : 0))
				{			
					Utils.PlayCustomSound("core_mod.pk.siren", currentFight.ActiveBossFightEntity.getLocation());
					//AIR STRIKE CUM BOMBS
					new BukkitRunnable()
					{
						Location myTarget;
						int i2 = 0;
						public void run() {
							
							if(i2 == 0)
							{
								double leastDist = 500;
								Player leastDistP = null;
								for(Player p : Bukkit.getOnlinePlayers())
								{
									if(p.getLocation().distance(currentFight.ActiveBossFightEntity.getLocation()) < leastDist)
									{
										leastDist = p.getLocation().distance(currentFight.ActiveBossFightEntity.getLocation());
										leastDistP = p;
									}
								}
								
								if(leastDistP == null)
								{
									this.cancel();
									return;
								}
								
								float addon = 5 + (12 * ((float)currentFight.ActiveBossFightEntity.getHealth() / (float)PK_BOSS_HEALTH));
								myTarget = Utils.AddToLocationAsNew(leastDistP.getLocation(), new Random().nextFloat() * addon - (addon/2), 0, new Random().nextFloat() * addon - (addon/2));
								float leastY = myTarget.getWorld().getHighestBlockYAt(myTarget);
								myTarget.setY(leastY);
							}
							
							if(i2 < 30)
							{
								myTarget.getWorld().spawnParticle(Particle.VILLAGER_ANGRY, myTarget, 2, 0.5, 0.5, 0.5);
								myTarget.getWorld().spawnParticle(Particle.GLOW, myTarget, 20, 0.5, 0.5, 0.5);
							}
							
							if(i2 > 30)
							{
								myTarget.getWorld().strikeLightning(myTarget);
								Utils.StopCustomSound("core_mod.pk.siren");
								
								for(Player p : Bukkit.getOnlinePlayers())
								{
									if(p.getLocation().distance(myTarget) < 5)
									{
										p.damage((5 - p.getLocation().distance(myTarget)) * 2, currentFight.ActiveBossFightEntity);
									}
								}
								
								if(new Random().nextBoolean())
								{
									myTarget.getWorld().createExplosion(myTarget, 3, true, true);
									Utils.PlayCustomSound("core_mod.pk.splat", myTarget);
								}else {
									myTarget.getBlock().setType(Material.SNOW);
									
									myTarget.getBlock().getRelative(BlockFace.EAST).setType(Material.SNOW);
									myTarget.getBlock().getRelative(BlockFace.WEST).setType(Material.SNOW);
									myTarget.getBlock().getRelative(BlockFace.NORTH).setType(Material.SNOW);
									myTarget.getBlock().getRelative(BlockFace.SOUTH).setType(Material.SNOW);
	
									Utils.PlayCustomSound("core_mod.pk.splat", myTarget);
								}
								
								this.cancel();
							}
							
							i2++;
						};
					}.runTaskTimer(Main.PLUGIN, 1, 1);
					lastAtk = 0;
					
					return;
				}
			
			}
		}
			
		if(currentBossPhase == 3)
		{
			Utils.StopCustomSound("core_mod.pk.st_music");
		}
		
		if(currentBossPhase == 2 || currentBossPhase == 3)
		{
			if(currentFight.ActiveBossFightEntity.getEquipment().getItemInMainHand() != null && currentFight.ActiveBossFightEntity.getEquipment().getItemInMainHand().getType() != Material.AIR)
			{
				if(bossRNG.nextInt((int)(8501.0 * ((double)currentFight.ActiveBossFightEntity.getHealth() / PK_BOSS_HEALTH))) < lastAtk + ((((double)currentFight.ActiveBossFightEntity.getHealth() / PK_BOSS_HEALTH)) < 0.5 ? 10 : 0))
				{
					Utils.PlayCustomSound("core_mod.pk.tick_brain", currentFight.ActiveBossFightEntity.getLocation());
	
					new BukkitRunnable() {
						
						@Override
						public void run() {
							Utils.PlayCustomSound("core_mod.pk.splat", currentFight.ActiveBossFightEntity.getLocation());
							for(int x = -5; x < 5; x++)
							{
								for(int z = -5; z < 5; z++)
								{
									Location loc = currentFight.ActiveBossFightEntity.getLocation().add(x, 0, z);
									loc.setY(loc.getWorld().getHighestBlockYAt(loc));
									
									
									loc.getWorld().spawnParticle(Particle.SPIT, loc.getBlock().getRelative(BlockFace.UP).getLocation(), 60, 0.5, 6.5, 0.5, 0);
									if(bossRNG.nextBoolean())
									{
										loc.getBlock().getRelative(BlockFace.UP).setType(Material.SNOW);
									}else {
										loc.getBlock().getRelative(BlockFace.UP).setType(Material.FIRE);
									}
								}
							}
						}
					}.runTaskLater(Main.PLUGIN, 40);
					
					lastAtk = 0;							
					
					
				}
			}
		}
		
		if(currentBossPhase == 2)
		{
			if(currentFight.ActiveBossFightEntity.getEquipment().getItemInMainHand() != null && currentFight.ActiveBossFightEntity.getEquipment().getItemInMainHand().getType() != Material.AIR)
			{
				//DROP RANDOM MAPS?
				
				lastAtk++;
				if(bossRNG.nextInt((int)(5101.0 * ((double)currentFight.ActiveBossFightEntity.getHealth() / PK_BOSS_HEALTH))) < lastAtk + ((((double)currentFight.ActiveBossFightEntity.getHealth() / PK_BOSS_HEALTH)) < 0.5 ? 10 : 0))
				{	
					Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "summon item " + currentFight.ActiveBossFightEntity.getLocation().getBlockX() + " " + currentFight.ActiveBossFightEntity.getLocation().getBlockY() + " " + currentFight.ActiveBossFightEntity.getLocation().getBlockZ() + " {Item:{id:\"minecraft:filled_map\",Count:1b,tag:{map:" + bossRNG.nextInt(35) + "}}}");
					
					Wolf w = (Wolf)currentFight.ActiveBossFightEntity.getWorld().spawnEntity(currentFight.ActiveBossFightEntity.getLocation(), EntityType.WOLF);
					w.setAngry(true);
					
					for(Player p : Bukkit.getOnlinePlayers())
					{
						if(p.getLocation().distance(currentFight.ActiveBossFightEntity.getLocation()) < 50f)
						{
							w.setTarget(p);
							lastAtk = 0;
							return;
						}
					}
					
				}
				
				
				
				if(bossRNG.nextInt((int)(12501.0 * ((double)currentFight.ActiveBossFightEntity.getHealth() / PK_BOSS_HEALTH))) < lastAtk + ((((double)currentFight.ActiveBossFightEntity.getHealth() / PK_BOSS_HEALTH)) < 0.5 ? 10 : 0))
				{
					Utils.PlayCustomSound("core_mod.pk.tick_brain", currentFight.ActiveBossFightEntity.getLocation());

					new BukkitRunnable() {
						
						@Override
						public void run() {
							Utils.PlayCustomSound("core_mod.pk.splat", currentFight.ActiveBossFightEntity.getLocation());
							for(int x = -5; x < 5; x++)
							{
								for(int z = -5; z < 5; z++)
								{
									Location loc = currentFight.ActiveBossFightEntity.getLocation().add(x, 0, z);
									loc.setY(loc.getWorld().getHighestBlockYAt(loc));
									
									
									loc.getWorld().spawnParticle(Particle.SPIT, loc.getBlock().getRelative(BlockFace.UP).getLocation(), 60, 0.5, 6.5, 0.5, 0);
									if(bossRNG.nextBoolean())
									{
										loc.getBlock().getRelative(BlockFace.UP).setType(Material.SNOW);
									}else {
										loc.getBlock().getRelative(BlockFace.UP).setType(Material.FIRE);
									}
								}
							}
						}
					}.runTaskLater(Main.PLUGIN, 40);
					
					lastAtk = 0;							
					
					
				}
				
				if(bossRNG.nextInt((int)(12501.0 * ((double)currentFight.ActiveBossFightEntity.getHealth() / PK_BOSS_HEALTH))) < lastAtk + ((((double)currentFight.ActiveBossFightEntity.getHealth() / PK_BOSS_HEALTH)) < 0.5 ? 10 : 0))
				{
					Utils.PlayCustomSound("core_mod.pk.fr_music", currentFight.ActiveBossFightEntity.getLocation());

					currentFight.ActiveBossFightEntity.getWorld().spawnParticle(Particle.NOTE, currentFight.ActiveBossFightEntity.getLocation(), 12, 1f, 1f, 1f);
					
					for(int x = 0; x < 2; x++)
					{
						Entity e = currentFight.ActiveBossFightEntity.getWorld().spawnEntity(currentFight.ActiveBossFightEntity.getLocation(), EntityType.ZOMBIE);
						
						e.setCustomName("Furry Pokimane");
					}
					
					lastAtk = 0;							
					
					
				}
				
				if(bossRNG.nextInt((int)(20001.0 * ((double)currentFight.ActiveBossFightEntity.getHealth() / PK_BOSS_HEALTH))) < lastAtk + ((((double)currentFight.ActiveBossFightEntity.getHealth() / PK_BOSS_HEALTH)) < 0.5 ? 10 : 0))
				{
					Utils.PlayCustomSound("core_mod.pk.music_box", currentFight.ActiveBossFightEntity.getLocation());
					int len = 40 + bossRNG.nextInt(40);
					
					for(Player p : Bukkit.getOnlinePlayers())
					{
						if(p.getLocation().distance(currentFight.ActiveBossFightEntity.getLocation()) < 50f)
						{
							p.addPotionEffect(new PotionEffect(PotionEffectType.DARKNESS, len + 60, 15));
						}
					}
					
					new BukkitRunnable() {
						
						@Override
						public void run()
						{
							for(Player p : Bukkit.getOnlinePlayers())
							{
								if(p.getLocation().distance(currentFight.ActiveBossFightEntity.getLocation()) < 50f)
								{
									Utils.PlayCustomSound("core_mod.pk.fn_scream", currentFight.ActiveBossFightEntity.getLocation());

									Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "execute at " + p.getName() + " run spreadplayers ~ ~ 10 10 false " + p.getName());
									
									currentFight.ActiveBossFightEntity.teleport(p.getLocation());
									
									currentFight.ActiveBossFightEntity.getWorld().strikeLightning(currentFight.ActiveBossFightEntity.getLocation().add(1, 0, 1));
									currentFight.ActiveBossFightEntity.getWorld().createExplosion(currentFight.ActiveBossFightEntity.getLocation().add(2, 0, 2), 5f, true, true);
									
									p.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 100, 2));
									
								}
							}
							
						}
					}.runTaskLater(Main.PLUGIN, len);
					
					currentFight.ActiveBossFightEntity.getWorld().spawnParticle(Particle.NOTE, currentFight.ActiveBossFightEntity.getLocation(), 64, 1f, 1f, 1f);
					
					for(int x = 0; x < 2; x++)
					{
						Entity e = currentFight.ActiveBossFightEntity.getWorld().spawnEntity(currentFight.ActiveBossFightEntity.getLocation(), EntityType.ZOMBIE);
						
						e.setCustomName("Furry Pokimane");
					}
					
					lastAtk = 0;							
					
					
				}
				
			}
		
		}
	}

	@Override
	public void BossFightEventHandler(BossFightEvent event)
	{
		if(currentBossPhase == 2)
		{
			if(event == BossFightEvent.BOSS_DAMAGED)
			{
				EntityDamageEvent eventd = (EntityDamageEvent)event.Event;
				
				if(eventd.getDamage() * 6 < ((LivingEntity)eventd.getEntity()).getHealth())
				{
					eventd.setDamage(eventd.getDamage() * 6);
				}
				
				Utils.PlayCustomSound("core_mod.pk.wrong_buzz", currentFight.ActiveBossFightEntity.getLocation());
			}
		}
		
		super.BossFightEventHandler(event);
		
		
	}
	
	@Override
	public void EndBossFight() {
		currentBossPhase++;
		
		if(currentBossPhase == 1)
		{
			//8 sec heart beat sound
			
			final Location loc = currentFight.ActiveBossFightEntity.getLocation();
			
			new BukkitRunnable() {
				
				@Override
				public void run()
				{
					loc.getBlock().setType(Material.STONE);
					loc.getBlock().setType(Material.END_ROD);
					loc.getWorld().playSound(loc, Sound.BLOCK_STONE_PLACE, 1, 1);
				}
			}.runTaskLater(Main.PLUGIN, 20 * 8);
			
			new BukkitRunnable() {
				
				@Override
				public void run()
				{
					Utils.AddToLocationAsNew(loc, 0, 1, 0).getBlock().setType(Material.STONE);
					Utils.AddToLocationAsNew(loc, 0, 1, 0).getBlock().setType(Material.END_ROD);
					Directional d = (Directional)Utils.AddToLocationAsNew(loc, 0, 1, 0).getBlock().getBlockData();
					d.setFacing(BlockFace.DOWN);
					Utils.AddToLocationAsNew(loc, 0, 1, 0).getBlock().setBlockData(d);
					loc.getWorld().playSound(loc, Sound.BLOCK_STONE_PLACE, 1, 1);
					
					
				}
			}.runTaskLater(Main.PLUGIN, 20 * 8 + 12);
			
			new BukkitRunnable() {
				
				final Material[] randFloor = new Material[] {Material.BLUE_CONCRETE, Material.ORANGE_CONCRETE, Material.YELLOW_CONCRETE, Material.RED_CONCRETE, Material.MAGENTA_CONCRETE};
				int i = 0;
				
				@Override
				public void run()
				{
					if(i == 0)
					{
						for(Player p : Bukkit.getOnlinePlayers())
						{
							if(p.getLocation().distance(loc) < 100f)
							{
								Utils.PlayCustomSound("core_mod.pk.st_music", p.getLocation());
							}
						}
					}
					
					loc.getWorld().spawnParticle(Particle.ELECTRIC_SPARK, loc, 15, 0.5, 0.5, 0.5);
					
					Random rand = new Random();
					Utils.AddToLocationAsNew(loc, 0, -1, 0).getBlock().setType(randFloor[rand.nextInt(randFloor.length)]);
					Utils.AddToLocationAsNew(loc, 1, -1, 0).getBlock().setType(randFloor[rand.nextInt(randFloor.length)]);
					Utils.AddToLocationAsNew(loc, -1, -1, 0).getBlock().setType(randFloor[rand.nextInt(randFloor.length)]);
					Utils.AddToLocationAsNew(loc, 0, -1, 1).getBlock().setType(randFloor[rand.nextInt(randFloor.length)]);
					Utils.AddToLocationAsNew(loc, 0, -1, -1).getBlock().setType(randFloor[rand.nextInt(randFloor.length)]);
					Utils.AddToLocationAsNew(loc, 1, -1, 1).getBlock().setType(randFloor[rand.nextInt(randFloor.length)]);
					Utils.AddToLocationAsNew(loc, -1, -1, 1).getBlock().setType(randFloor[rand.nextInt(randFloor.length)]);
					Utils.AddToLocationAsNew(loc, 1, -1, -1).getBlock().setType(randFloor[rand.nextInt(randFloor.length)]);
					Utils.AddToLocationAsNew(loc, -1, -1, -1).getBlock().setType(randFloor[rand.nextInt(randFloor.length)]);
					i++;
					
					if(i > 100)
					{
						this.cancel();
					}
				}
			}.runTaskTimer(Main.PLUGIN, 20 * 8 + 35, 6);
			
			final PokimaneBoss pb = this;
			new BukkitRunnable() {
				
				int i = 0;
				Entity newPokimane;

				@Override
				public void run()
				{
					
					Location loc2 = Utils.AddToLocationAsNew(loc, -0.5f, -1f, -0.5f);
					
					if(i == 0)
					{
						newPokimane = pb.Summon(Utils.AddToLocationAsNew(loc2, 0.4f, 0, 0), true);
						newPokimane.setCustomName("Stripper Pokimane");
						
						currentFight.ActiveBossHandler.myBossLE = (LivingEntity) newPokimane;
						
						currentFight.ActiveBossFightEntity = (LivingEntity)newPokimane;
						
						currentFight.ActiveBossBar.setTitle(ChatColor.GOLD + "" + ChatColor.BOLD + "Stripper Pokimane");
					}else {
						Location locnew = ItemEvents.RotateAboutAPoint(loc2, newPokimane.getLocation(), 5 + (i/10));
						
						float yaw = (float) Math.toDegrees(Math.atan2(
								loc2.getZ() - newPokimane.getLocation().getZ(), loc2.getX() - newPokimane.getLocation().getX())) - 90;
						
						locnew.setYaw(yaw);
						
						locnew.add(0, (1f/64f) * (float)i, 0);
						
						newPokimane.teleport(locnew);
						
						if(i == 31)
						{
							Utils.PlayCustomSound("core_mod.pk.siren", locnew);
						}
						
						if(i > 30 && i % 5 == 0)
						{
							//AIR STRIKE CUM BOMBS
							new BukkitRunnable()
							{
								Location myTarget;
								int i2 = 0;
								public void run() {
									
									if(i2 == 0)
									{
										double leastDist = 500;
										Player leastDistP = null;
										for(Player p : Bukkit.getOnlinePlayers())
										{
											if(p.getLocation().distance(loc2) < leastDist)
											{
												leastDist = p.getLocation().distance(loc2);
												leastDistP = p;
											}
										}
										
										if(leastDistP == null)
										{
											this.cancel();
											return;
										}
										
										myTarget = Utils.AddToLocationAsNew(leastDistP.getLocation(), new Random().nextFloat() * 16f - 8f, 0, new Random().nextFloat() * 16f - 8f);
										float leastY = myTarget.getWorld().getHighestBlockYAt(myTarget);
										myTarget.setY(leastY);
									}
									
									if(i2 < 30)
									{
										myTarget.getWorld().spawnParticle(Particle.VILLAGER_ANGRY, myTarget, 2, 0.5, 0.5, 0.5);
										myTarget.getWorld().spawnParticle(Particle.GLOW, myTarget, 20, 0.5, 0.5, 0.5);
									}
									
									if(i2 > 30)
									{
										myTarget.getWorld().strikeLightning(myTarget);
										
										if(new Random().nextBoolean())
										{
											myTarget.getWorld().createExplosion(myTarget, 3, true, true);
											Utils.PlayCustomSound("core_mod.pk.splat", myTarget);
										}else {
											myTarget.getBlock().setType(Material.SNOW);
											
											myTarget.getBlock().getRelative(BlockFace.EAST).setType(Material.SNOW);
											myTarget.getBlock().getRelative(BlockFace.WEST).setType(Material.SNOW);
											myTarget.getBlock().getRelative(BlockFace.NORTH).setType(Material.SNOW);
											myTarget.getBlock().getRelative(BlockFace.SOUTH).setType(Material.SNOW);

											Utils.PlayCustomSound("core_mod.pk.splat", myTarget);
										}
										
										this.cancel();
									}
									
									i2++;
								};
							}.runTaskTimer(Main.PLUGIN, 1, 1);
						}
						
						if(i > 180)
						{
							locnew.getWorld().spawnParticle(Particle.SPIT, locnew, 12, 0.2, 0.2, 0.2);
							Utils.PlayCustomSound("core_mod.pk.splat", locnew);
							this.cancel();
							
							new BukkitRunnable() {
								
								int iv = 0;
								
								@Override
								public void run() {

									if(iv < 30)
									{
										if(iv % 2 == 0)
										{
											currentFight.ActiveBossFightEntity.getEquipment().setItemInMainHand(new ItemStack(Material.DIAMOND_SWORD));
										}else {
											currentFight.ActiveBossFightEntity.getEquipment().setItemInMainHand(new ItemStack(Material.NETHERITE_SWORD));
										}
									}
									
									if(iv == 30)
									{
										ItemStack pkSword = new CustomItemStack(ChatColor.BLUE + "" + ChatColor.UNDERLINE + "Pokimane's Blade", Material.DIAMOND_SWORD, 0).AddEnchant(Enchantment.DAMAGE_ALL, 6, true).AddEnchant(Enchantment.FIRE_ASPECT, 2, true).GetMyItemStack();
										
										currentFight.ActiveBossFightEntity.getEquipment().setItemInMainHand(pkSword);
									}
									
									if(iv > 30)
									{
										this.cancel();
									}
									
									iv++;
								}
							}.runTaskTimer(Main.PLUGIN, 1, 1);
						}
					}
					
					i++;
				}
			}.runTaskTimer(Main.PLUGIN, 20 * 8 + 35 + 6*12, 3);
			return;
		}
		
		
		
		if(currentBossPhase == 2)
		{
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "stopsound @a master");
			
			new BukkitRunnable() {
				int i = 0;
				
				@Override
				public void run() {
					currentFight.ActiveBossFightEntity.getLocation().getWorld().playSound(currentFight.ActiveBossFightEntity.getLocation(), Sound.ENTITY_WOLF_WHINE, 1f, 1f);
					
					if(i > 7)
					{
						this.cancel();
					}
					i++;
				}
			}.runTaskTimer(Main.PLUGIN, 80, 15);
			
			new BukkitRunnable() {
				int i = 0;
				
				@Override
				public void run() {
					currentFight.ActiveBossFightEntity.getLocation().getWorld().playSound(currentFight.ActiveBossFightEntity.getLocation(), Sound.ENTITY_WOLF_HURT, 1f, 1f);
					
					if(i > 7)
					{
						this.cancel();
					}
					i++;
				}
			}.runTaskTimer(Main.PLUGIN, 80 + 15*7, 15);
			
			
			final PokimaneBoss pb = this;
			new BukkitRunnable() {
				int i = 0;
				
				@Override
				public void run() {
					Wolf wolf = (Wolf)currentFight.ActiveBossFightEntity.getWorld().spawnEntity(currentFight.ActiveBossFightEntity.getLocation(), EntityType.WOLF);
					
					wolf.setRotation(90f, 0);
					wolf.setAI(false);
					
					LivingEntity newPokimane = (LivingEntity) pb.Summon(Utils.AddToLocationAsNew(currentFight.ActiveBossFightEntity.getLocation(), 0.4f, 0, 0), true);
					
					currentFight.ActiveBossHandler.myBossLE = newPokimane;
					
					newPokimane.setCustomName("Pokimane");
					newPokimane.setInvulnerable(true);
					
					newPokimane.setRotation(90f, 0f);
					newPokimane.setAI(false);
					
					new BukkitRunnable() {
						int iq = 0;
						
						@Override
						public void run() {
							currentFight.ActiveBossFightEntity.getLocation().getWorld().playSound(currentFight.ActiveBossFightEntity.getLocation(), Sound.ENTITY_WOLF_HURT, 1f, 1f);
							wolf.damage(0.01);
							
							if(iq % 2 == 0)
							{
								newPokimane.teleport(newPokimane.getLocation().add(0.3, 0, 0));
							}else {
								newPokimane.teleport(newPokimane.getLocation().add(-0.3, 0, 0));
							}
							
							iq++;
							if(iq > 12)
							{
								wolf.damage(10000);
								currentFight.ActiveBossFightEntity.getLocation().getWorld().playSound(currentFight.ActiveBossFightEntity.getLocation(), Sound.ENTITY_WOLF_DEATH, 1f, 1f);

								this.cancel();
								
								new BukkitRunnable() {
									int i = 0;
									
									@Override
									public void run() {
										currentFight.ActiveBossFightEntity.getLocation().getWorld().playSound(currentFight.ActiveBossFightEntity.getLocation(), Sound.BLOCK_SCULK_BREAK, 1f, 1f);
										currentFight.ActiveBossFightEntity.getLocation().getWorld().spawnParticle(Particle.VILLAGER_ANGRY, currentFight.ActiveBossFightEntity.getLocation(), 60, 0.5, 2, 0.5, 0);

										if(i > 12)
										{
											this.cancel();
											
											new BukkitRunnable() {
												int i = 0;
												
												@Override
												public void run() {
													currentFight.ActiveBossFightEntity.getLocation().getWorld().spawnParticle(Particle.CAMPFIRE_COSY_SMOKE, currentFight.ActiveBossFightEntity.getLocation(), 60, 0.5, 2, 0.5, 0);
													currentFight.ActiveBossFightEntity.getLocation().getWorld().playSound(currentFight.ActiveBossFightEntity.getLocation(), Sound.BLOCK_AZALEA_LEAVES_BREAK, 1f, 1f);
													
													if(i == 7)
													{
														newPokimane.setCustomName("Furry Pokimane");
														
														currentFight.ActiveBossFightEntity = (LivingEntity)newPokimane;
													}
													
													if(i > 12)
													{
														this.cancel();
														
														new BukkitRunnable() {
															int i = 0;
															
															@Override
															public void run() {
																
																currentFight.ActiveBossBar.setTitle(ChatColor.GREEN + "Furry Pokimane");
																
																ItemStack sword = new CustomItemStack(ChatColor.GREEN + "Long, Hard, Plastic Wolf Cock", Material.NETHERITE_SWORD, 0).AddEnchant(Enchantment.DAMAGE_ALL, 7, true).AddEnchant(Enchantment.KNOCKBACK, 2, true).GetMyItemStack();
															
																ItemStack calfSockThings = new CustomItemStack(ChatColor.GRAY + "Goofy Ahh Sock Things", Material.NETHERITE_LEGGINGS, 0).AddEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 10, true).AddLoreLine("*Goofy Ahh*").AddEnchant(Enchantment.THORNS, 6, true).GetMyItemStack();
																
																newPokimane.getEquipment().setItemInMainHand(sword);
																newPokimane.getEquipment().setLeggings(calfSockThings);
																
																newPokimane.setInvulnerable(false);
																newPokimane.setAI(true);
																
																Utils.PlayCustomSound("core_mod.pk.fr_music", currentFight.ActiveBossFightEntity.getLocation());
															}
														}.runTaskLater(Main.PLUGIN, 15);
														

													}
													i++;
												}
											}.runTaskTimer(Main.PLUGIN, 20, 5);
										}
										i++;
									}
								}.runTaskTimer(Main.PLUGIN, 20, 5);
							}
						}
					}.runTaskTimer(Main.PLUGIN, 10, 10);
				}
			}.runTaskLater(Main.PLUGIN, 80 + 15*14);
			
			return;
		}
		
		if(currentBossPhase == 3)
		{
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "stopsound @a master");
			
			Location loccheck = currentFight.ActiveBossFightEntity.getLocation();
			loccheck.setY(loccheck.getWorld().getHighestBlockYAt(loccheck));
			currentFight.ActiveBossFightEntity.teleport(loccheck);
			
			ItemStack calfSockThings = new CustomItemStack(ChatColor.GRAY + "Silly Socks", Material.NETHERITE_LEGGINGS, 0).AddLoreLine("*Goofy Ahh*").AddEnchant(Enchantment.THORNS, 3, true).GetMyItemStack();
			currentFight.ActiveBossFightEntity.getWorld().dropItem(currentFight.ActiveBossFightEntity.getLocation(), calfSockThings);
			
			final PokimaneBoss pb = this;
			new BukkitRunnable()
			{

				@Override
				public void run()
				{
					
					for(Player p : Bukkit.getOnlinePlayers())
					{
						if(p.getLocation().distance(currentFight.ActiveBossFightEntity.getLocation()) < 50f)
						{
							Utils.PlayCustomSound("core_mod.pk.skibidi", p.getLocation());
						}
					}
					
					new BukkitRunnable()
					{

						@Override
						public void run()
						{
							
							LivingEntity newPokimane = (LivingEntity) pb.Summon(Utils.AddToLocationAsNew(currentFight.ActiveBossFightEntity.getLocation(), 0.4f, 0, 0), true);
							currentFight.ActiveBossHandler.myBossLE = newPokimane;
							
							currentFight.ActiveBossFightEntity = newPokimane;

							newPokimane.setCustomName("Giga Pokimane");
							newPokimane.setInvulnerable(true);

							newPokimane.setAI(false);
							
							new BukkitRunnable()
							{
								int i = 0;
								@Override
								public void run()
								{
									newPokimane.teleport(newPokimane.getLocation().add(0, 0.075, 0));
									
									newPokimane.getWorld().spawnParticle(Particle.FLASH, newPokimane.getLocation(), 30, 0.1, 0.1, 0.1, 0);
									
									if(i % 14 == 0)
									{
										newPokimane.getWorld().strikeLightning(newPokimane.getLocation().add(2, 0, 0));
										newPokimane.getWorld().strikeLightning(newPokimane.getLocation().add(0, 0, 2));
										newPokimane.getWorld().strikeLightning(newPokimane.getLocation().add(-2, 0, 0));
										newPokimane.getWorld().strikeLightning(newPokimane.getLocation().add(0, 0, -2));
									}
									
									if(i > 20 * 8)
									{
										this.cancel();
									
										for(Player p : Bukkit.getOnlinePlayers())
										{
											if(p.getLocation().distance(currentFight.ActiveBossFightEntity.getLocation()) < 50f)
											{
												Utils.PlayCustomSound("core_mod.pk.gyatt", p.getLocation());
											}
										}
										
										new BukkitRunnable()
										{
											int i = 0;
											@Override
											public void run()
											{
												newPokimane.getWorld().spawnParticle(Particle.FLASH, newPokimane.getLocation(), 10, 0.1, 0.1, 0.1, 0);

												newPokimane.getWorld().spawnParticle(Particle.SPIT, newPokimane.getLocation(), 40, 0.1, 0.1, 0.1, 0.4);
												
												newPokimane.setRotation(newPokimane.getLocation().getYaw() + 5, 0);
												
												if(i > 20 * 15)
												{
													currentFight.ActiveBossBar.setTitle(ChatColor.GOLD + "" + ChatColor.UNDERLINE + "" + ChatColor.BOLD + "Giga Pokimane (Lvl. 9999 Gyatt)");
													this.cancel();
													
													Utils.StopCustomSound("core_mod.pk.gyatt");
													
													for(Player p : Bukkit.getOnlinePlayers())
													{
														if(p.getLocation().distance(currentFight.ActiveBossFightEntity.getLocation()) < 60f)
														{
															Utils.PlayCustomSound("core_mod.pk.final_form", p.getLocation());
														}
													}
													
													newPokimane.getEquipment().setHelmet(new CustomItemStack("Pokimane Cap", Material.NETHERITE_HELMET, 0).AddEnchant(Enchantment.THORNS, 2, true).AddEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 6, true).GetMyItemStack());
													newPokimane.getEquipment().setItemInMainHand(new CustomItemStack("Poki Penetrator", Material.NETHERITE_SWORD, 0).Attribute(Attribute.GENERIC_ATTACK_DAMAGE, 16).AddEnchant(Enchantment.DAMAGE_ALL, 12, true).GetMyItemStack());
												
													newPokimane.setAI(true);
													newPokimane.setInvulnerable(false);
												}
												
												i++;
											}
											
										}.runTaskTimer(Main.PLUGIN, 1, 1);
									}
									
									i++;
								}
								
							}.runTaskTimer(Main.PLUGIN, 1, 1);
						}
						
					}.runTaskLater(Main.PLUGIN, 50);
				}
				
			}.runTaskLater(Main.PLUGIN, 160);
			
			return;
		}
		
		Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "stopsound @a master");
		
		

		for(Player p : Bukkit.getOnlinePlayers())
		{
			if(p.getLocation().distance(currentFight.ActiveBossFightEntity.getLocation()) < 60f)
			{
				Utils.PlayCustomSound("core_mod.pk.final_form_end", p.getLocation());
			}
		}
		
		final Location abfe = currentFight.ActiveBossFightEntity.getLocation().clone();
		new BukkitRunnable() {
			
			@Override
			public void run() {
				for(int i = 0; i < 15; i++)
				{
					int x = abfe.getBlockX() + bossRNG.nextInt(30) - 15;
					int z = abfe.getBlockZ() + bossRNG.nextInt(30) - 15;
					
					Location loc = new Location(abfe.getWorld(), x, 0, z);
					loc.setY(loc.getWorld().getHighestBlockYAt(loc) + 1);
					
					loc.getWorld().strikeLightning(loc);
					loc.getWorld().createExplosion(loc, 1, false, false);
					
					Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "summon firework_rocket " + loc.getX() + " " + loc.getY() + " " + loc.getZ() + " {LifeTime:22,FireworksItem:{id:\"firework_rocket\",Count:1,tag:{Fireworks:{Explosions:[{Type:1,Flicker:1b,Trail:1b,Colors:[I;16711680],FadeColors:[I;0]}]}}}}");
				}
				
				for(int i = 0; i < 15; i++)
				{
					int x = abfe.getBlockX() + bossRNG.nextInt(30) - 15;
					int z = abfe.getBlockZ() + bossRNG.nextInt(30) - 15;
					
					Location loc = new Location(abfe.getWorld(), x, 0, z);
					loc.setY(loc.getWorld().getHighestBlockYAt(loc) + 1);
					
					loc.getWorld().strikeLightning(loc);
					loc.getWorld().createExplosion(loc, 1, false, false);
					
					Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "summon firework_rocket " + loc.getX() + " " + loc.getY() + " " + loc.getZ() + " {LifeTime:22,FireworksItem:{id:\"firework_rocket\",Count:1,tag:{Fireworks:{Explosions:[{Type:1,Flicker:1b,Trail:1b,Colors:[I;3407616],FadeColors:[I;0]}]}}}}");
				}
				
				for(int i = 0; i < 15; i++)
				{
					int x = abfe.getBlockX() + bossRNG.nextInt(30) - 15;
					int z = abfe.getBlockZ() + bossRNG.nextInt(30) - 15;
					
					Location loc = new Location(abfe.getWorld(), x, 0, z);
					loc.setY(loc.getWorld().getHighestBlockYAt(loc) + 1);
					
					loc.getWorld().strikeLightning(loc);
					loc.getWorld().createExplosion(loc, 1, false, false);
					
					Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "summon firework_rocket " + loc.getX() + " " + loc.getY() + " " + loc.getZ() + " {LifeTime:22,FireworksItem:{id:\"firework_rocket\",Count:1,tag:{Fireworks:{Explosions:[{Type:1,Flicker:1b,Trail:1b,Colors:[I;255],FadeColors:[I;0]}]}}}}");
				}				
			}
		}.runTaskLater(Main.PLUGIN, 8 + 9 * 20);
		
		new BukkitRunnable() {
			
			@Override
			public void run() {
				
				String pNames = "";
				int multiple = 0;
				
				for(Player p : Bukkit.getOnlinePlayers())
				{
					if(p.getLocation().distance(abfe) < 80f)
					{
						multiple++;
						pNames += p.getName() + ", ";
					}
				}
				
				pNames = pNames.substring(0, pNames.length() - 2);
				
				if(multiple > 0)
				{
					Utils.BroadcastMessage(ChatColor.GOLD + pNames + (multiple == 1 ? " has " : " have ") + "beaten Pokimane...");
				}
				
				for(int i = 0; i < 15; i++)
				{
					int x = abfe.getBlockX() + bossRNG.nextInt(30) - 15;
					int z = abfe.getBlockZ() + bossRNG.nextInt(30) - 15;
					
					Location loc = new Location(abfe.getWorld(), x, 0, z);
					loc.setY(loc.getWorld().getHighestBlockYAt(loc) + 1);
					
					Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "summon firework_rocket " + loc.getX() + " " + loc.getY() + " " + loc.getZ() + " {LifeTime:22,FireworksItem:{id:\"firework_rocket\",Count:1,tag:{Fireworks:{Explosions:[{Type:1,Flicker:1b,Trail:1b,Colors:[I;16711680],FadeColors:[I;0]}]}}}}");
				}
				
				for(int i = 0; i < 15; i++)
				{
					int x = abfe.getBlockX() + bossRNG.nextInt(30) - 15;
					int z = abfe.getBlockZ() + bossRNG.nextInt(30) - 15;
					
					Location loc = new Location(abfe.getWorld(), x, 0, z);
					loc.setY(loc.getWorld().getHighestBlockYAt(loc) + 1);
					
					Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "summon firework_rocket " + loc.getX() + " " + loc.getY() + " " + loc.getZ() + " {LifeTime:22,FireworksItem:{id:\"firework_rocket\",Count:1,tag:{Fireworks:{Explosions:[{Type:1,Flicker:1b,Trail:1b,Colors:[I;3407616],FadeColors:[I;0]}]}}}}");
				}
				
				for(int i = 0; i < 15; i++)
				{
					int x = abfe.getBlockX() + bossRNG.nextInt(30) - 15;
					int z = abfe.getBlockZ() + bossRNG.nextInt(30) - 15;
					
					Location loc = new Location(abfe.getWorld(), x, 0, z);
					loc.setY(loc.getWorld().getHighestBlockYAt(loc) + 1);
					
					Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "summon firework_rocket " + loc.getX() + " " + loc.getY() + " " + loc.getZ() + " {LifeTime:22,FireworksItem:{id:\"firework_rocket\",Count:1,tag:{Fireworks:{Explosions:[{Type:1,Flicker:1b,Trail:1b,Colors:[I;255],FadeColors:[I;0]}]}}}}");
				}				
			}
		}.runTaskLater(Main.PLUGIN, 8 + 15 * 20);
		
		super.EndBossFight();
	}
	
	private void DoPokimaneSpawnSequence()
	{
		LivingEntity le = currentFight.ActiveBossFightEntity;
		
		le.teleport(le.getLocation().add(0, 5, 0));
		le.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_FALLING, 20 * 4, 1));
		
		new BukkitRunnable() {
			
			int iterCount = 0;
			
			@Override
			public void run() {
				Location loc = le.getLocation();
				loc.setY(le.getWorld().getHighestBlockYAt(le.getLocation()));
				le.getWorld().strikeLightning(loc.add(new Random().nextDouble() * 2 - 1, 0, new Random().nextDouble() * 2 - 1));
				
				le.getWorld().spawnParticle(Particle.CAMPFIRE_COSY_SMOKE, 0.2, 0.2, 0.2, 4);
				
				iterCount++;
				
				if(iterCount == 15)
				{
					this.cancel();
				}
			}
		}.runTaskTimer(Main.PLUGIN, 20, 2);
	}
	
	private void LaunchFoot(float varyRot)
	{
		Location origin = currentFight.ActiveBossFightEntity.getLocation();
		final ArmorStand as = (ArmorStand)currentFight.ActiveBossFightEntity.getWorld().spawnEntity(Utils.AddToLocationAsNew(origin, 1.f, 0.f, 0.f), EntityType.ARMOR_STAND);
		
		as.setInvulnerable(true);
		as.setGravity(false);
		as.setInvisible(true);
		as.setSmall(true);
		as.setMarker(true);
		
		as.getEquipment().setHelmet(new CustomItemStack("Pokimane's Feet", Material.INK_SAC, 10001).GetMyItemStack());
		
		as.teleport(ItemEvents.RotateAboutAPoint(origin, as.getLocation(), currentFight.ActiveBossFightEntity.getLocation().getYaw()+ 90 + varyRot));
		 
		float yaw = (float) Math.toDegrees(Math.atan2(
                origin.getZ() - as.getLocation().getZ(), origin.getX() - as.getLocation().getX())) - 90;
		
		Location loc = as.getLocation();
		loc.setYaw(yaw + 180);
		as.teleport(loc);
		
		as.setVelocity(as.getLocation().toVector().normalize().multiply(2).setY(2));
		
		new BukkitRunnable() {
			
			int maxFlight = 0;
			Vector direction = as.getLocation().getDirection().normalize();

			public void run() {
				
				Location loc = as.getLocation();
				

				for(Entity e : loc.getWorld().getNearbyEntities(loc, 0.5, 0.5, 0.5))
				{
					if(e instanceof LivingEntity)
					{
						if(!(e.getType() == EntityType.ZOMBIE) && !(e.getType() == EntityType.ARMOR_STAND))
						{
							((LivingEntity) e).damage(4, currentFight.ActiveBossFightEntity);
							loc.getWorld().createExplosion(e.getLocation(), 1, true, true);
							as.remove();
							this.cancel();
							return;
						}
					}
				}
				
				if(loc.getBlock().getType() != Material.AIR && loc.getBlock().getType() != Material.CAVE_AIR && loc.getBlock().getType() != Material.VOID_AIR)
				{
					loc.getWorld().createExplosion(loc, 1);
					as.remove();
					this.cancel();
					return;
				}
				
				loc.add(new Vector(direction.getX()*0.5, 0, direction.getZ()*0.5));
				
				loc.getWorld().spawnParticle(Particle.SPIT, loc, 13, 0.2, 0.2, 0.2);
				
				if(maxFlight % 60 == 0)
				{
					Utils.PlayCustomSound("core_mod.pk.pokimane_ft_sniff", loc);
				}
				
				as.teleport(loc);
				
				if(maxFlight > 100)
				{
					this.cancel();
					as.remove();
					return;
				}
				
				maxFlight++;
				
			}
		}.runTaskTimer(Main.PLUGIN, 1, 1);
	}
	

}
