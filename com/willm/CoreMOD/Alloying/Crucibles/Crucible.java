package com.willm.CoreMOD.Alloying.Crucibles;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.willm.CoreMOD.Alloying.AlloyMaterial;
import com.willm.ModAPI.Blocks.CustomStates.CustomBaseMaterialRetainingBlock;
import com.willm.ModAPI.Items.CustomItemStack;

public abstract class Crucible extends CustomBaseMaterialRetainingBlock {

	public Crucible(CustomItemStack rootItem) {
		super(rootItem);
	}
	
	@Override
	public void Interact(PlayerInteractEvent event) {
		if(event.getItem() != null)
		{
			for(AlloyMaterial am : AlloyMaterial.values())
			{
				if(am.Compare.Compare(event.getItem()))
				{
					FillCrucible(event.getClickedBlock(), am);
					break;
				}
			}
		}
	}

	public static final String FILL_LEVEL_PREFIX = "CRUCIBLE_FILL_LEVEL_";
	
	public void FillCrucible(Block b, AlloyMaterial am)
	{
		for(Entity e : b.getWorld().getNearbyEntities(b.getLocation(), 0.5f, 0.5f, 0.5f))
		{
			if(e instanceof ArmorStand)
			{
				ArmorStand as = ((ArmorStand) e);
				if(as.getEquipment().getHelmet() != null)
				{
					ItemStack helm = as.getEquipment().getHelmet();
					if(helm.hasItemMeta())
					{
						if(helm.getItemMeta().hasDisplayName() && helm.getItemMeta().getDisplayName().contains(FILL_LEVEL_PREFIX)) {
							int fillLevel = Integer.parseInt(helm.getItemMeta().getDisplayName().replace(FILL_LEVEL_PREFIX, ""));
							
							as.teleport(new Location(as.getWorld(), as.getLocation().getX(), as.getLocation().getY() + 0.2, as.getLocation().getZ()));
							helm.getItemMeta().setDisplayName(FILL_LEVEL_PREFIX + (fillLevel + 1));
							as.getEquipment().setHelmet(helm);
							return;
						}
					}
				}
			}
		}
		
		for(Player p : Bukkit.getOnlinePlayers())
		{
			p.sendMessage("FOUND");
		}
		ArmorStand as = (ArmorStand)b.getWorld().spawnEntity(b.getLocation(), EntityType.ARMOR_STAND);
		
		ItemStack fillItem = new ItemStack(Material.IRON_INGOT, 1);
		ItemMeta m = fillItem.getItemMeta();
		m.setDisplayName(FILL_LEVEL_PREFIX + "1");
		fillItem.setItemMeta(m);
		
		as.getEquipment().setHelmet(fillItem);
	}
	
}
