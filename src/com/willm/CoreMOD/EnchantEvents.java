package com.willm.CoreMOD;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Random;

import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

import com.willm.ModAPI.Enchantments.CustomEnchantment;
import com.willm.ModAPI.Terrain.CustomPopulator;
import com.willm.ModAPI.Terrain.Ore;

public class EnchantEvents implements Listener {

	private static HashMap<ItemStack, ItemStack> smelting_touch_smeltables = new HashMap<ItemStack, ItemStack>();
	
	public static void SmeltingTouch_RegisterSmeltables()
	{
		smelting_touch_smeltables.put(new ItemStack(Material.IRON_ORE), new ItemStack(Material.IRON_INGOT));
		smelting_touch_smeltables.put(new ItemStack(Material.GOLD_ORE), new ItemStack(Material.GOLD_INGOT));
		smelting_touch_smeltables.put(MyItems.wolframite.GetMyItemStack(), MyItems.tungsten_ingot.GetMyItemStack());
		smelting_touch_smeltables.put(MyItems.platinum_ore.GetMyItemStack(), MyItems.platinum_ingot.GetMyItemStack());
		smelting_touch_smeltables.put(MyItems.crude_oil_deposit.GetMyItemStack(), MyItems.oil_barrel.GetMyItemStack());
		smelting_touch_smeltables.put(MyItems.rutile.GetMyItemStack(), MyItems.titanium_ingot.GetMyItemStack());
		smelting_touch_smeltables.put(new ItemStack(Material.ANCIENT_DEBRIS), new ItemStack(Material.NETHERITE_SCRAP));
	}
	
	@EventHandler
	public void SmeltingTouch_BlockBreak(BlockBreakEvent event)
	{
		ItemStack mh = event.getPlayer().getEquipment().getItemInMainHand();
		if(mh != null)
		{
			int i = CustomEnchantment.hasEnchant(mh, "Smelting Touch I") ? 1 : (CustomEnchantment.hasEnchant(mh, "Smelting Touch II") ? 2 : CustomEnchantment.hasEnchant(mh, "Smelting Touch III") ? 3 : 0);
			if(i != 0)
			{
				//Scope for random
				{
					Random r = new Random();

					switch(i)
					{
					case 1:
						if(r.nextInt(101) < 60)
						{
							return;
						}
						break;
					case 2:
						if(r.nextInt(101) < 30)
						{
							return;
						}
						break;
					case 3:
						break;
					}
				}
				
				Collection<ItemStack> minedItem = event.getBlock().getDrops(event.getPlayer().getEquipment().getItemInMainHand());
								
				boolean dropped = false;
				for(Entry<ItemStack, ItemStack> e : smelting_touch_smeltables.entrySet())
				{
					if(e.getKey().getType() == event.getBlock().getType())
					{
						dropped = true;
						event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(), e.getValue());
						continue;
					}
					
					for(ItemStack drop : minedItem)
					{
						if(drop.getType() == e.getKey().getType())
						{
							if(e.getKey().getItemMeta().hasCustomModelData())
							{
								if(drop.getItemMeta().hasCustomModelData())
								{
									if(e.getKey().getItemMeta().getCustomModelData() != drop.getItemMeta().getCustomModelData())
									{
										continue;
									}
								}else {
									continue;
								}
							}
							
							dropped = true;
							event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(), e.getValue());
						}
					}
				}
				
				if(dropped) { event.setDropItems(false); }
			}
		}
	}
	
	@EventHandler
	public void XPCollector_AttackEntity(EntityDamageByEntityEvent event)
	{
		if(event.getDamager() instanceof LivingEntity)
		{
			LivingEntity le_d = (LivingEntity)event.getDamager();
			if(le_d.getEquipment().getItemInMainHand() != null)
			{
				if(CustomEnchantment.hasEnchant(le_d.getEquipment().getItemInMainHand(), "XP Collector"))
				{
					ExperienceOrb orb = (ExperienceOrb)event.getEntity().getWorld().spawnEntity(event.getEntity().getLocation(), EntityType.EXPERIENCE_ORB);
					orb.setExperience(Math.round((float)event.getDamage()/4f));
				}
			}
		}
	}
	
	@EventHandler
	public void XPCollector_BreakBlock(BlockBreakEvent event)
	{

		if(event.getPlayer().getEquipment().getItemInMainHand() != null)
		{
			if(CustomEnchantment.hasEnchant(event.getPlayer().getEquipment().getItemInMainHand(), "XP Collector"))
			{
				if((new Random()).nextInt(101) < 15)
				{
					ExperienceOrb orb = (ExperienceOrb)event.getBlock().getWorld().spawnEntity(event.getBlock().getLocation(), EntityType.EXPERIENCE_ORB);
					orb.setExperience(1);
				}
			}
		}
	}
	
	static final Material[] oreMine = new Material[] { Material.IRON_ORE, Material.GOLD_ORE, Material.NETHER_GOLD_ORE, Material.LAPIS_ORE, Material.DIAMOND_ORE, Material.REDSTONE_ORE, Material.COAL_ORE, Material.EMERALD_ORE, Material.OAK_LOG, Material.SPRUCE_LOG, Material.BIRCH_LOG, Material.DARK_OAK_LOG, Material.ACACIA_LOG, Material.JUNGLE_LOG };
	
	@EventHandler
	public void MineOre_OreMiner(BlockBreakEvent event)
	{
		if(event.getPlayer().getEquipment().getItemInMainHand() != null)
		{
			if(CustomEnchantment.hasEnchant(event.getPlayer().getEquipment().getItemInMainHand(), "Vein Miner"))
			{
				OreMineBlock(event.getBlock());
			}
		}
	}
	
	public void OreMineBlock(Block b) {OreMineBlock(b, false);}
	public void OreMineBlock(Block b, boolean ignoreBreak)
	{
		boolean ore = ignoreBreak;
		
		if(!ore)
		{
			for(Ore o : CustomPopulator.ores)
			{
				if(b.getType() == o.drop.GetMyItemStack().getType())
			
				{
					b.getWorld().dropItemNaturally(b.getLocation(), o.drop.GetMyItemStack());
					b.getWorld().playSound(b.getLocation(), Sound.BLOCK_STONE_BREAK, 1f, 1f);
					b.getWorld().spawnParticle(Particle.BLOCK_DUST, b.getLocation(), 60, b.getBlockData());
					
					b.setType(Material.AIR);
					ore = true;
				}
			}
		}
		
		
		if(!ore)
		{
		
			for(Material m : oreMine)
			{
				if(b.getType() == m)
				{
					b.getWorld().playSound(b.getLocation(), Sound.BLOCK_STONE_BREAK, 1f, 1f);
					b.getWorld().spawnParticle(Particle.BLOCK_DUST, b.getLocation(), 60, b.getBlockData());

					b.breakNaturally();
					ore = true;
				}
			}
		
		}
		
		if(ore)
		{
			OreMineBlock(b.getRelative(BlockFace.DOWN));
			OreMineBlock(b.getRelative(BlockFace.UP));
			OreMineBlock(b.getRelative(BlockFace.EAST));
			OreMineBlock(b.getRelative(BlockFace.WEST));
			OreMineBlock(b.getRelative(BlockFace.SOUTH));
			OreMineBlock(b.getRelative(BlockFace.NORTH));
		}
	}
}
