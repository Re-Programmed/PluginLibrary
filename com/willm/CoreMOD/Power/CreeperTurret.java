package com.willm.CoreMOD.Power;

import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.Dispenser;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Entity;
import org.bukkit.event.player.PlayerInteractEvent;

import com.willm.ModAPI.Blocks.CustomBlock;
import com.willm.ModAPI.Blocks.CustomStates.TickBlock;
import com.willm.ModAPI.Items.BlockCreator;
import com.willm.ModAPI.Items.CustomItemStack;
import com.willm.ModAPI.Items.ItemCreator;

public class CreeperTurret extends CustomBlock implements TickBlock {
	
	public CreeperTurret(CustomItemStack rootItem) {
		super(rootItem);
		
		RegisterAsTickBlock();
	}

	public static CustomItemStack CREEPER_TURRET;
	public static CreeperTurret CREEPER_TURRET_BLOCK;
	public static CustomItemStack CREEPER_TURRET_AMMO;
	
	public static void RegisterItems()
	{
		CREEPER_TURRET = ItemCreator.RegisterNewItem(new CustomItemStack("Creeper Turret", Material.IRON_BLOCK, 69201));
		CREEPER_TURRET_BLOCK = (CreeperTurret) BlockCreator.RegisterNewBlock(CREEPER_TURRET, new CreeperTurret(CREEPER_TURRET));
		CREEPER_TURRET_AMMO = ItemCreator.RegisterNewItem(new CustomItemStack("Turret Ammo", Material.IRON_NUGGET, 15002));
	}
	
	@Override
	public void Interact(PlayerInteractEvent event) {
		Dispenser d = (Dispenser)event.getClickedBlock().getState();
		d.setCustomName("Turret");
		
		String lock = d.getLock();
		d.setLock("");
		d.update(true);
		event.getPlayer().openInventory(d.getInventory());
		d.setLock(lock);
		d.update(true);
		
		event.setCancelled(true);
	}

	@Override
	public void Tick(Block b)
	{
		Dispenser d = (Dispenser)b.getState();
		
		if(!d.getInventory().containsAtLeast(CREEPER_TURRET_AMMO.GetMyItemStack(), 1))
		{
			return;
		}
		
		for(Entity e : b.getWorld().getNearbyEntities(b.getLocation(), 10f, 10f, 10f))
		{
			if(e instanceof Creeper)
			{
				if(Math.random() * 50 < 5)
				{
					b.getWorld().spawnParticle(Particle.CRIT, b.getLocation(), 30, 0.6, 0.6, 0.6);
					b.getWorld().playSound(b.getLocation(), Sound.BLOCK_CHAIN_BREAK, 1, 1);
					
					((Creeper) e).damage(8);
					d.getInventory().removeItem(CREEPER_TURRET_AMMO.GetAmountClone(1));
				}
			}
		}
	}
	
}
