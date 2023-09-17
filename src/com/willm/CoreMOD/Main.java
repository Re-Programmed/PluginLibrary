package com.willm.CoreMOD;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.BlockState;
import org.bukkit.block.Chest;
import org.bukkit.block.Dispenser;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import com.willm.CoreMOD.Alloying.CreateAlloyPickCommand;
import com.willm.CoreMOD.Alloying.CreateAlloyPickTabCompleter;
import com.willm.CoreMOD.AssortedTools.AssortedToolEvent;
import com.willm.CoreMOD.ElementalItems.RegisterElementalItems;

public class Main extends JavaPlugin {

	public static Main INSTANCE;
		
	@Override
	public void onEnable()
	{		
		INSTANCE = this;

		com.willm.ModAPI.Main.Launch("core_smp", this);
		
		com.willm.ModAPI.Voltage.Main.UseVoltage();

		MyItems.RegisterMyItems();
		MyEnchants.RegisterEnchants();
	
		com.willm.ModAPI.WorldCraft.Main.LaunchWorldCraft();
		
		MyBiomes.InitBiomes();
		
		getServer().getPluginManager().registerEvents(new ItemEvents(), this);
		getServer().getPluginManager().registerEvents(new BlockEvents(), this);
		getServer().getPluginManager().registerEvents(new EnchantEvents(), this);
		
		getServer().getPluginManager().registerEvents(new MilkFeatures(), this);
		
		getServer().getPluginManager().registerEvents(new AssortedToolEvent(), this);
		
		getCommand("rottick").setExecutor(new RotTickCommand());
		
		getCommand("alloypick").setExecutor(new CreateAlloyPickCommand());
		getCommand("alloypick").setTabCompleter(new CreateAlloyPickTabCompleter());

		RegisterElementalItems.Register();

		Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
			
			public void run() { 
				try
				{
					if(BlockEvents.elevator_cooldown > 0f) {BlockEvents.elevator_cooldown --;}
				}catch(Exception e)
				{
					e.printStackTrace();
				}
			}
		}, 1, 1);
		
Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
			
			public void run() { 
				try
				{
					RotTick();
				}catch(Exception e)
				{
					e.printStackTrace();
				}
			}
		}, 20 * 30, 20 * 30);
		
	}
	
	public static void RotTick()
	{
		RotTick(30);
	}
	
	public static void RotTick(int count)
	{
		for(Player p : Bukkit.getOnlinePlayers())
		{
			for (BlockState state: p.getLocation().getChunk().getTileEntities()) {
				if (state instanceof Chest) {
					Chest c = (Chest)state;
					for(ItemStack i : c.getInventory())
					{
						if(i == null) {continue;}
						RotItem(i, count);
					}
				}
				
				if(state instanceof Dispenser)
				{
					Dispenser d = (Dispenser)state;
					if(d.getLock().contains("fridge"))
					{
						for(ItemStack i : d.getInventory())
						{
							if(i == null) {continue;}
							RotItem(i, -count * 3);
						}
					}else {
						for(ItemStack i : d.getInventory())
						{
							if(i == null) {continue;}
							RotItem(i, count);
						}
					}
				}
			}
			
			for(ItemStack i : p.getInventory())
			{
				if(i == null) {continue;}
				RotItem(i, count);

			}
		}
	}

	
	public static void RotItem(ItemStack i, int amnt)
	{
		if(MyItems.parishables.containsKey(i.getType()))
		{
			int parishMax = MyItems.parishables.get(i.getType());
			
			if(i.getItemMeta().hasLore())
			{
				ItemMeta meta = i.getItemMeta();
				List<String> lore = meta.getLore();
				
				String curr = lore.get(lore.size() - 1);
				
				if(curr.contains("ROTTEN")) {return;}
				
				if(!curr.contains("Age:"))
				{
					lore.add(ChatColor.GREEN + "Age: " + 0 + "/" + parishMax);
					curr = lore.get(lore.size() - 1);
				}
				
				int val = Integer.parseInt(curr.replace("Age: ", "").replace(ChatColor.GREEN + "", "").replace(ChatColor.RED + "", "").replace(ChatColor.YELLOW + "", "").replace("/" + parishMax, ""));
								
				val += amnt;
				
				if(val < 0) {val = 0;}
				
				if(val > parishMax)
				{
					lore.set(lore.size() - 1, ChatColor.RED + "ROTTEN");
					
					if(i.getType() == Material.MILK_BUCKET)
					{
						meta.setCustomModelData(MyItems.curdled_milk.getCustomModelData());
						meta.setDisplayName(ChatColor.WHITE + MyItems.curdled_milk.getName());
					}
					
				}else if(val >= parishMax / 3 * 2)
				{
					lore.set(lore.size() - 1, ChatColor.RED + "Age: " + (val) + "/" + parishMax);
				}else if(val >= parishMax / 3)
				{
					lore.set(lore.size() - 1, ChatColor.YELLOW + "Age: " + (val) + "/" + parishMax);
				}else {
					lore.set(lore.size() - 1, ChatColor.GREEN + "Age: " + (val) + "/" + parishMax);
				}
				
				meta.setLore(lore);
				i.setItemMeta(meta);
			}else {
				List<String> lore = new ArrayList<String>();
				
				lore.add(ChatColor.GREEN + "Age: " + 0 + "/" + parishMax);
				
				ItemMeta m = i.getItemMeta();
				m.setLore(lore);
				i.setItemMeta(m);
			}
		}
	}
	
	@Override
	public void onDisable()
	{
		com.willm.ModAPI.Main.Disable();
	}
}
