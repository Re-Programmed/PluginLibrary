package com.willm.CoreMOD.Power;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;

import com.willm.CoreMOD.ItemEvents;
import com.willm.CoreMOD.MyItems;
import com.willm.ModAPI.Blocks.CustomBlock;
import com.willm.ModAPI.Items.CustomItemStack;
import com.willm.ModAPI.Voltage.Blocks.EnergyCompatible;

public class RotaryEngine extends EnergyCompatible {

	public final float rotationForce;
	
	public RotaryEngine(float rotationForce, CustomBlock cb)
	{
		this.blockRef = cb;
		this.rotationForce = rotationForce;
	}
	
	@Override
	public void Tick(Location loc) {
		if(!sources.containsKey(loc)) {sources.put(loc, 0);}
		if(sources.get(loc) == null) {sources.put(loc, 0);}
		
		for(Player p : Bukkit.getOnlinePlayers())
		{
			if(p.getLocation().distance(loc) < 2f)
			{
				if(p.isSneaking())
				{
					p.sendMessage(ChatColor.AQUA + "[ROTARY ENGINE POWER]: " + sources.get(loc));
				}
			}
		}
	
		
		if(sources.get(loc) > 700)
		{
			int i = 1;
			for(CustomItemStack cis : MyItems.centrifuge_tops)
			{
				if(cis.getRelatedBlock().CheckForCustomBlock(loc.getBlock().getRelative(BlockFace.UP)))
				{
					ItemEvents.triggerCentrifuge(loc.getBlock().getRelative(BlockFace.UP), cis, rotationForce, i);
					break;
				}
				i++;

			}
			
			RemoveEnergy(425, loc);
		}
	}

}
