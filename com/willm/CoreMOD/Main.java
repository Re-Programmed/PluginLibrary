package com.willm.CoreMOD;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.BlockState;
import org.bukkit.block.Chest;
import org.bukkit.block.Dispenser;
import org.bukkit.block.ShulkerBox;
import org.bukkit.entity.ChestBoat;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.minecart.HopperMinecart;
import org.bukkit.entity.minecart.StorageMinecart;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import com.willm.CoreMOD.AdvancedCrafter.AdvancedCrafter;
import com.willm.CoreMOD.Alloying.CreateAlloyPickCommand;
import com.willm.CoreMOD.Alloying.CreateAlloyPickTabCompleter;
import com.willm.CoreMOD.Alloying.Crucibles.CrucibleEvents;
import com.willm.CoreMOD.BackSlot.BackSlotCommand;
import com.willm.CoreMOD.BackSlot.BackSlotEvents;
import com.willm.CoreMOD.CustomCommands.CMapCommand;
import com.willm.CoreMOD.CustomCommands.CMapCompleter;
import com.willm.CoreMOD.CustomCommands.CoordCommand;
import com.willm.CoreMOD.CustomCommands.CoordsCommandCompleter;
import com.willm.CoreMOD.CustomCommands.LukeModeCommand;
import com.willm.CoreMOD.CustomCommands.ViewInventoryCommand;
import com.willm.CoreMOD.DifficultyExtension.DifficultyEvents;
import com.willm.CoreMOD.DifficultyExtension.SetDifficultyCommand;
import com.willm.CoreMOD.ElementalItems.RegisterElementalItems;
import com.willm.CoreMOD.blocks.CraftingWorkbenchEvents;
import com.willm.CoreMOD.blocks.StorageKeyCommand;
import com.willm.CoreMOD.blocks.StorageTerminalEvents;

public class Main extends JavaPlugin {

	public static Main INSTANCE;
		
	@Override
	public void onEnable()
	{		

		INSTANCE = this;

		for(Player p : Bukkit.getOnlinePlayers())
		{
			p.sendMessage(ChatColor.YELLOW + "The server was reloaded!");
		}
		
		com.willm.ModAPI.Main.Launch("core_smp", this);
		
		com.willm.ModAPI.Voltage.Main.UseVoltage();

		MyItems.RegisterMyItems();
		//MyEnchants.RegisterEnchants();
		
		com.willm.CoreMOD.Holiday.WinterStuff.RegisterWinterStuff();
	
		com.willm.ModAPI.WorldCraft.Main.LaunchWorldCraft();
		
		MyBiomes.InitBiomes();
		
		getServer().getPluginManager().registerEvents(new ItemEvents(), this);
		getServer().getPluginManager().registerEvents(new BlockEvents(), this);
		getServer().getPluginManager().registerEvents(new EnchantEvents(), this);
		
		getServer().getPluginManager().registerEvents(new CustomItemEffectsEvents(), this);
		
		//getServer().getPluginManager().registerEvents(new MilkFeatures(), this);
		
		//getServer().getPluginManager().registerEvents(new AssortedToolEvent(), this);
		
		getServer().getPluginManager().registerEvents(new CrucibleEvents(), this);
		
		getCommand("storagekey").setExecutor(new StorageKeyCommand());
		
		getCommand("rottick").setExecutor(new RotTickCommand());
		getCommand("viewinventory").setExecutor(new ViewInventoryCommand());

		getCommand("alloypick").setExecutor(new CreateAlloyPickCommand());
		getCommand("alloypick").setTabCompleter(new CreateAlloyPickTabCompleter());
		
		getCommand("coordinates").setExecutor(new CoordCommand());
		getCommand("coordinates").setTabCompleter(new CoordsCommandCompleter());
		
		getCommand("cmap").setExecutor(new CMapCommand());
		getCommand("cmap").setTabCompleter(new CMapCompleter());

		getCommand("lukemode").setExecutor(new LukeModeCommand());
		
		getCommand("setdifficulty").setExecutor(new SetDifficultyCommand());
		
		getCommand("backslot").setExecutor(new BackSlotCommand());
		getServer().getPluginManager().registerEvents(new BackSlotEvents(), this);

		getServer().getPluginManager().registerEvents(new DifficultyEvents(), this);
		
		getServer().getPluginManager().registerEvents(new MessageEvents(), this);
		
		getServer().getPluginManager().registerEvents(new StorageTerminalEvents(), this);
		getServer().getPluginManager().registerEvents(new CraftingWorkbenchEvents(), this);

		
		RegisterElementalItems.Register();

		MyEntities.RegisterEntities();
		
		//Elevator Runnable
		Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
			
			public void run() { 
				try
				{
					if(BlockEvents.elevator_cooldown > 0f) {BlockEvents.elevator_cooldown --;}
					
					for(Entry<Inventory, ItemStack> shulkerOpen : ItemEvents.shulkerPouches.entrySet())
					{
						Inventory inv = shulkerOpen.getKey();
						
						if(inv.getViewers().size() < 1) {ItemEvents.shulkerPouches.remove(inv);return;}
						
						ItemStack item = shulkerOpen.getValue();
						BlockStateMeta im = (BlockStateMeta)item.getItemMeta();
						ShulkerBox shulker = (ShulkerBox) im.getBlockState();
						
						shulker.getInventory().setContents(inv.getContents());
						im.setBlockState(shulker);
						item.setItemMeta(im);

					}
				}catch(Exception e)
				{
					e.printStackTrace();
				}
			}
		}, 1, 1);
		
		//Ice Box Open Inventory Updates Runnable
		Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
			
			public void run() { 
				ItemEvents.IceBoxInventoryHandling();
				
				AdvancedCrafter.Tick();
				
				ViewInventoryCommand.Tick();
			}
		}, 3, 3);
		
		//Rotting Food Runnable
Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
			
			public void run() { 
				try
				{
					RotTick(100);
				}catch(Exception e)
				{
					e.printStackTrace();
				}
			}
		}, 20 * 100, 20 * 100);
		
	}
	
	public static void RotTick()
	{
		RotTick(30);
	}
	
	public static void RotTick(int count)
	{
		for(Player p : Bukkit.getOnlinePlayers())
		{
			for(Entity e : p.getWorld().getNearbyEntities(p.getLocation(), 25f, 25f, 25f))
			{
				if(e instanceof ChestBoat)
				{
					ChestBoat cb = (ChestBoat)e;
					for(ItemStack i : cb.getInventory())
					{
						if(i == null) {continue;}
						RotItem(i, count);
					}
				}
				
				if(e instanceof StorageMinecart)
				{
					StorageMinecart cb = (StorageMinecart)e;
					for(ItemStack i : cb.getInventory())
					{
						if(i == null) {continue;}
						RotItem(i, count);
					}
				}
				
				if(e instanceof HopperMinecart)
				{
					HopperMinecart cb = (HopperMinecart)e;
					for(ItemStack i : cb.getInventory())
					{
						if(i == null) {continue;}
						RotItem(i, count);
					}
				}
			}
			
			for (BlockState state: p.getLocation().getChunk().getTileEntities()) {
				
				
				if (state instanceof Chest) {
					Chest c = (Chest)state;
					
					if(c.getLock().contains("industrial_fridge"))
					{
						for(ItemStack i : c.getInventory())
						{
							if(i == null) {continue;}
							RotItem(i, -count * 6);
						}
					}else {
						for(ItemStack i : c.getInventory())
						{
							if(i == null) {continue;}
							RotItem(i, count);
						}
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
			
			for(ItemStack i : p.getEnderChest())
			{
				if(i == null) {continue;}
				RotItem(i, count);
			}
		}
	}

	
	public static void RotItem(ItemStack i, int amnt)
	{
		if(MyItems.ice_box.CheckForCustomItem(i))
		{
			if(i.getItemMeta().hasLore())
			{
				List<String> newLore = new ArrayList<String>();
				for(String s : i.getItemMeta().getLore())
				{
					if(s.split(" x ")[1].contains(" ("))
					{
						String ageText = s.substring(s.indexOf(" (") + 2);
						ageText = ageText.split("/")[0];
						
						int age = Integer.parseInt(ageText);
						age -= amnt * 2.5;
						
						if(age < 0) {age = 0;}
						
						String newS = new String(s);
						newS = newS.substring(0, s.indexOf(" (") + 2) + age + s.substring(s.indexOf("/"));
						newLore.add(newS);
						
					}
				}
				
				ItemMeta m = i.getItemMeta();
				m.setLore(newLore);
				i.setItemMeta(m);
			}
			
			return;
		}
		
		if(MyItems.parishables.containsKey(i.getType()))
		{
			int parishMax = MyItems.parishables.get(i.getType());
			
			if(i.getItemMeta().hasLore())
			{
				ItemMeta meta = i.getItemMeta();
				List<String> lore = meta.getLore();
				
				if(lore.contains(ItemEvents.CURED_TEXT))
				{
					parishMax *= 3.35;
				}
				
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
