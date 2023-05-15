package com.willm.CoreMOD.AssortedTools;

import java.util.Random;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

public class AssortedToolEvent implements Listener {

	@EventHandler
	public void MineBlock(BlockBreakEvent event)
	{
		ItemStack mh = event.getPlayer().getEquipment().getItemInMainHand();
		if(mh != null)
		{
			if(mh.getItemMeta().hasCustomModelData())
			{
				if(mh.getItemMeta().getCustomModelData() == CustomTool.CMD_DATA)
				{
					boolean first = true;
					for(String s : mh.getItemMeta().getLore())
					{
						if(first) {first = false;continue;}
						ToolAbilities ta = ToolAbilities.valueOf(s.substring(2));
						
						switch(ta)
						{
						case Diamond:
							if(event.getBlock().getType().toString().toLowerCase().contains("ore"))
							{
								if((new Random()).nextInt(101) < 50)
								{
									for(ItemStack is : event.getBlock().getDrops(mh))
									{
										is.setAmount(2);
										event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(), is);
										
									}
								}
							}
							break;
						case Gold:
							event.setExpToDrop(event.getExpToDrop() + 1);
							break;
						case Iron:
							
							break;
						case Netherite:
							break;
						case Stone:
							break;
						case Wooden:
							break;
						default:
							break;
						}
					}
				}
			}
		}
	}
	
}
