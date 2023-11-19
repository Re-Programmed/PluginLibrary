package com.willm.ModAPI.Items.MusicDisc;

import java.util.HashMap;

import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.block.Jukebox;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import com.willm.ModAPI.Utils;

public class MusicDiscEvents implements Listener {

	static HashMap<Jukebox, MusicDisc> playingJukeboxes = new HashMap<Jukebox, MusicDisc>();
	
	int rcDelay = 0;
	
	public static void ShowMusicNotes()
	{
		for(Jukebox jb : playingJukeboxes.keySet())
		{
			playingJukeboxes.get(jb).ProduceNote++;
			if(playingJukeboxes.get(jb).ProduceNote >= playingJukeboxes.get(jb).NoteSpeed)
			{
				jb.getWorld().spawnParticle(Particle.NOTE, Utils.AddToLocationAsNew(jb.getLocation(), 0.5f, 1.1f, 0.5f), 1);
				playingJukeboxes.get(jb).ProduceNote = 0;
			}
		}
	}
	
	@EventHandler
	public void RightClickJukebox(PlayerInteractEvent event)
	{
		if(event.getAction() != Action.RIGHT_CLICK_BLOCK) {return;}
	
		
		if(event.getClickedBlock() != null && event.getClickedBlock().getType() == Material.JUKEBOX)
		{
			final Jukebox j = (Jukebox)event.getClickedBlock().getState();

			if(playingJukeboxes.keySet().contains(j))
			{
				if(rcDelay == 1) {rcDelay = 0; return;}

				Utils.StopCustomSound(playingJukeboxes.get(j).GetSong());
				
				j.getWorld().dropItemNaturally(Utils.AddToLocationAsNew(j.getLocation(), 0, 0.5f, 0), playingJukeboxes.get(j).GetMyItemStack());
				
				playingJukeboxes.remove(j);
				return;
			}
			
			if(event.getItem() != null)
			{
				if(event.getItem().hasItemMeta() && event.getItem().getItemMeta().hasCustomModelData())
				{
					for(final MusicDisc disc : MusicDisc.MusicDiscRegistry)
					{
						if(disc.CheckForCustomItem(event.getItem()))
						{
							final String s = disc.GetSong();
							
							if(j.isPlaying())
							{
								j.eject();
								j.stopPlaying();
								j.setRecord(null);
								j.setPlaying(null);
								j.update(true);
								event.setCancelled(true);
								return;
							}
							
							j.setRecord(event.getItem());
							j.setPlaying(Material.AIR);
							j.startPlaying();
							j.update(true);
							rcDelay++;

							playingJukeboxes.put(j, disc);
							
							event.getPlayer().getEquipment().setItemInMainHand(null);
							
							Utils.PlayCustomSound(s, j.getLocation());
						}
					}
				}
			}
		}
	}
	
}
