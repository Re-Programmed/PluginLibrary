package com.willm.ModAPI.Items.MusicDisc;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.Material;

import com.willm.ModAPI.Items.CustomItemStack;

public class MusicDisc extends CustomItemStack {
	
	static ArrayList<MusicDisc> MusicDiscRegistry = new ArrayList<MusicDisc>();
	
	private final String songId;
	
	public int ProduceNote;
	public final float NoteSpeed;
	
	public MusicDisc(Material mat, int cmd, String songName, String songId, float noteSpeed) {
		super(ChatColor.AQUA + "Music Disc", mat, cmd);
		
		this.AddLoreLine(ChatColor.GRAY + songName);
		
		this.songId = songId;
		
		this.NoteSpeed = noteSpeed;
		
		MusicDiscRegistry.add(this);
	}

	
	@Override
	protected void finalize() throws Throwable {
		MusicDiscRegistry.remove(this);
	}
	
	//Returns the songId.
	public String GetSong()
	{
		return songId;
	}
	
	
}
