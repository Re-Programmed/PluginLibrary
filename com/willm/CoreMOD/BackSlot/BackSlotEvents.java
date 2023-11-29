package com.willm.CoreMOD.BackSlot;

import java.util.HashMap;

import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.EulerAngle;

public class BackSlotEvents implements Listener {

	public static HashMap<Player, ArmorStand> BackSlotDisplays = new HashMap<Player, ArmorStand>();
	public static HashMap<Player, Location> LastPositions = new HashMap<Player, Location>();
	
	@EventHandler
	public void UpdateBackSlotPosition(PlayerMoveEvent event)
	{
		if(BackSlotDisplays.containsKey(event.getPlayer()))
		{
			//Create the armor stand.
			ArmorStand as = BackSlotDisplays.get(event.getPlayer());
			
			//Create loc as the players position and rotation.
			Location loc = new Location(event.getPlayer().getWorld(), event.getPlayer().getLocation().getX(), event.getPlayer().getLocation().getY(), event.getPlayer().getLocation().getZ(), event.getPlayer().getLocation().getYaw(), event.getPlayer().getLocation().getPitch());
			
			//Move the armor stand forward.
			
			double playerVelChange = LastPositions.containsKey(event.getPlayer()) ? LastPositions.get(event.getPlayer()).distance(event.getPlayer().getLocation()) : 0;
						
			
			
			loc.add(loc.getDirection().normalize().multiply(0.1).multiply(1 + ((playerVelChange > 0.14) ? (playerVelChange * 37.68) : 0)).setY(0));

			//Rotate the stand 90 degrees.
			loc.setYaw(loc.getYaw() + 90f);
			
			//Set the arm to point up.
			as.setRightArmPose(new EulerAngle(45, 0, 0));
			
			//Move the stand to the right.
			loc.add(loc.getDirection().normalize().multiply(0.45).setY(0));

			as.teleport(loc);
			
			LastPositions.put(event.getPlayer(), new Location(event.getPlayer().getLocation().getWorld(), event.getPlayer().getLocation().getX(), event.getPlayer().getLocation().getY(), event.getPlayer().getLocation().getZ()));
		}
	}
	
}
