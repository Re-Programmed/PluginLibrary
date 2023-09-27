package com.willm.ModAPI;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Dispenser;
import org.bukkit.block.data.type.TrapDoor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;

import com.willm.ModAPI.Blocks.BlockEvents;
import com.willm.ModAPI.Blocks.CustomBlock;
import com.willm.ModAPI.Blocks.LiquidBlock;
import com.willm.ModAPI.Blocks.Machine;
import com.willm.ModAPI.Blocks.MachineConversion;
import com.willm.ModAPI.Blocks.CustomStates.CustomTrapdoorBlock;
import com.willm.ModAPI.Blocks.CustomStates.TickBlock;
import com.willm.ModAPI.Commands.AddEnchant;
import com.willm.ModAPI.Commands.AddEnchantTabCompleter;
import com.willm.ModAPI.Commands.ClearBrokenArmorstand;
import com.willm.ModAPI.Commands.CompareVersionsCommand;
import com.willm.ModAPI.Commands.CreativeMenu;
import com.willm.ModAPI.Commands.FillCustomBlockCommand;
import com.willm.ModAPI.Commands.FillCustomBlockTabCompleter;
import com.willm.ModAPI.Commands.GiveCommand;
import com.willm.ModAPI.Commands.GiveTabCompleter;
import com.willm.ModAPI.Commands.RepopMachinesCommand;
import com.willm.ModAPI.Commands.SetCustomBlockCommand;
import com.willm.ModAPI.Commands.SetCustomBlockTabCompleter;
import com.willm.ModAPI.Enchantments.EnchantAnvils;
import com.willm.ModAPI.Items.BlockCreator;
import com.willm.ModAPI.Items.CustomItemStack;
import com.willm.ModAPI.Items.ItemCreator;
import com.willm.ModAPI.Items.Plant;
import com.willm.ModAPI.RecipeDisplay.RecipeDisplayCommand;
import com.willm.ModAPI.RecipeDisplay.RecipeDisplayEvents;
import com.willm.ModAPI.RecipeDisplay.RecipeDisplayTabCompleter;
import com.willm.ModAPI.Terrain.CustomPopulator;
import com.willm.ModAPI.Terrain.OreEvents;

public class Main {
	public static float UpdateRadius = 64.f;

	public static String PluginName, Version;
	public static ArrayList<CustomItemStack> CustomItemRegistry = new ArrayList<CustomItemStack>();
	public static ArrayList<Plant> PlantRegistry = new ArrayList<Plant>();
	public static ArrayList<CustomBlock> CustomBlockRegistry = new ArrayList<CustomBlock>();
	public static ArrayList<Machine> MachineRegistry = new ArrayList<Machine>();
	public static ArrayList<CustomItemStack> ConsumableRegistry = new ArrayList<CustomItemStack>();
	public static HashMap<Location, LiquidBlock> Liquids = new HashMap<Location, LiquidBlock>();
	
	public static ArrayList<TickBlock> TickBlocks = new ArrayList<TickBlock>();
	
	public static CustomPopulator Populator, NetherPopulator;
    static CreativeMenu CreativeMenuCommand = new CreativeMenu();
    
	private final static Random random_plant = new Random();
    
	public static HashMap<MobDrop, ItemStack> customMobDrops = new HashMap<MobDrop, ItemStack>();
	
    //Use this variable to create a recipe for a MachineExtractor -- or don't if you don't want one. Machine extractors pull items from nearby machines and drop them on the ground or in nearby chests. Inserters pull from nearby chests.
	public static CustomItemStack MExtractor, MInserter;
	
	public static int MachineRepopTick = 0;

	public static int LiquidTick = 0;
	
	public static void Launch(String pluginName, JavaPlugin jp)
	{
		Launch(pluginName, jp, "v1.0.0");
	}
	
	public static void Launch(String pluginName, JavaPlugin jp, String version)
	{
		Main.PluginName = pluginName;
		Main.Version = version;
		
		jp.getCommand("givecustom").setExecutor(new GiveCommand());
		jp.getCommand("givecustom").setTabCompleter(new GiveTabCompleter());
		
		jp.getCommand("enchantcustom").setExecutor(new AddEnchant());
		jp.getCommand("enchantcustom").setTabCompleter(new AddEnchantTabCompleter());
		
		jp.getCommand("removebrokenstand").setExecutor(new ClearBrokenArmorstand());
		
		jp.getCommand("setcustomblock").setExecutor(new SetCustomBlockCommand());
		jp.getCommand("setcustomblock").setTabCompleter(new SetCustomBlockTabCompleter());
		
		jp.getCommand("fillcustomblock").setExecutor(new FillCustomBlockCommand());
		jp.getCommand("fillcustomblock").setTabCompleter(new FillCustomBlockTabCompleter());
		
		jp.getCommand("recipes").setExecutor(new RecipeDisplayCommand());
		jp.getCommand("recipes").setTabCompleter(new RecipeDisplayTabCompleter());
		
		jp.getCommand("pversion").setExecutor(new CompareVersionsCommand());
		
		jp.getCommand("repopulatemachines").setExecutor(new RepopMachinesCommand());
		
		jp.getCommand("setupdateradius").setExecutor(new CommandExecutor() {

			@Override
			public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
				if(label.equalsIgnoreCase("setupdateradius"))
				{
					if(args.length > 0)
					{
						try
						{				
							Integer i = Integer.parseUnsignedInt(args[0]);
							UpdateRadius = Integer.parseUnsignedInt(args[0]);
							sender.sendMessage(ChatColor.WHITE + "Block update radius has been updated to " + args[0] + " blocks.");
							return true;
						}
						catch(NumberFormatException e)
						{
							sender.sendMessage(ChatColor.RED + "Enter a valid update radius.");
							return false;
						}
					}
					
					sender.sendMessage(ChatColor.WHITE + "The update radius is currently: " + UpdateRadius);
					return true;
				}
				return false;
			}
			
		});
		
		jp.getServer().getPluginManager().registerEvents(new RecipeDisplayEvents(), jp);
		
		jp.getServer().getPluginManager().registerEvents(new OreEvents(), jp);
		jp.getServer().getPluginManager().registerEvents(new BlockEvents(), jp);
		jp.getServer().getPluginManager().registerEvents(new EnchantAnvils(), jp);
		
		jp.getServer().getPluginManager().registerEvents(new CustomDeathMessages(), jp);

		MExtractor = ItemCreator.RegisterNewItem(new CustomItemStack("Machine Extractor", Material.GOLD_BLOCK, 21003));
		MExtractor.AddLoreLine(ChatColor.GREEN + "Pulls items out of machines.");
		BlockCreator.RegisterNewBlock(MExtractor);

		MInserter = ItemCreator.RegisterNewItem(new CustomItemStack("Machine Inserter", Material.GOLD_BLOCK, 21004));
		MInserter.AddLoreLine(ChatColor.GREEN + "Pulls items from nearby chests into nearby machines.");
		BlockCreator.RegisterNewBlock(MInserter);

		jp.getCommand("itemmenu").setExecutor(CreativeMenuCommand);
		ArrayList<String> cm_alias = new ArrayList<String>();
		cm_alias.add("im");
		cm_alias.add("creativemenu");
		jp.getCommand("itemmenu").setAliases(cm_alias);
		
		for(World world : Bukkit.getWorlds())
		{
			if(world.getEnvironment() == Environment.NORMAL)
			{
				Populator = new CustomPopulator();
				world.getPopulators().add(Populator);
			}
			
			if(world.getEnvironment() == Environment.NETHER)
			{
				NetherPopulator = new CustomPopulator();
				world.getPopulators().add(NetherPopulator);
			}
		}
		

		Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(jp, new Runnable() {
			
			@SuppressWarnings("unchecked")
			public void run() { 
				try
				{
					MachineRepopTick--;
					CreativeMenuCommand.Tick();
					for(Machine m : MachineRegistry)
					{
						m.MachineTick();
					}
					
					
					for(Player p : Bukkit.getOnlinePlayers())
					{
						for(Entity e : p.getWorld().getNearbyEntities(p.getLocation(), UpdateRadius, UpdateRadius, UpdateRadius))
						{
							if(e.getType() == EntityType.ARMOR_STAND)
							{
								ArmorStand as = (ArmorStand)e;
								
								if(as.getEquipment().getHelmet() != null)
								{
									if(as.getEquipment().getHelmet().hasItemMeta())
									{
										if(as.getEquipment().getHelmet().getItemMeta().hasCustomModelData())
										{
											if(as.getLocation().getBlock().getState() instanceof Dispenser)
											{
												as.setFireTicks(999999999);
												
												if(MachineRepopTick < 0)
												{													
													for(CustomBlock cbr : Main.CustomBlockRegistry)
													{
														if(cbr.getMachineTemplate() == null)
														{
															continue;
														}
														
														if(cbr.CheckForCustomBlock(as.getLocation().getBlock()))
														{															
															boolean found = false;
															for(Machine m : MachineRegistry)
															{
																if(m.location == null) {continue;}
																if(m.location.distance(as.getLocation()) < 0.67f)
																{
																	found = true;
																	break;
																}
															}
															
															if(!found)
															{
																if(cbr.getMachineTemplate().conversions == null)
																{
																	cbr.m.add(cbr.getMachineTemplate().Clone().SetLocation(new Location(as.getLocation().getWorld(), as.getLocation().getX() - 0.5, as.getLocation().getY(), as.getLocation().getZ() - 0.5)));
																}else {
																	cbr.m.add(new Machine(cbr.getMachineTemplate().size, cbr.getMachineTemplate().name, (int) Math.floor(cbr.getMachineTemplate().productSpeed * 100f), (ArrayList<MachineConversion>)cbr.getMachineTemplate().conversions.clone(), cbr.getMachineTemplate().sound_clip).SetLocation(new Location(as.getLocation().getWorld(), as.getLocation().getX() - 0.5, as.getLocation().getY(), as.getLocation().getZ() - 0.5)));
																}
															}
															
															break;
														}
													}
													
												}
												
											}else if(as.getFireTicks() < 5 && as.getEquipment().getHelmet().getAmount() > 1)
											{
												as.getLocation().getBlock().setType(Material.GLASS);
											}
											
											if(com.willm.ModAPI.Voltage.Main.Enabled)
											{
												com.willm.ModAPI.Voltage.Main.CheckStand(as);
											}
											
											for(Plant pl : PlantRegistry)
											{
												pl.CheckStand(as, random_plant);
											}
											
											if(as.getLocation().getBlock().getType() == Material.WARPED_TRAPDOOR)
											{
												Block b = as.getLocation().getBlock();
												for(CustomTrapdoorBlock ctb : CustomTrapdoorBlock.Trapdoors)
												{
													if(ctb.CheckForCustomBlock(b))
													{
														TrapDoor t = (TrapDoor)b.getBlockData();
														
														if(t.isOpen() != (as.getEquipment().getHelmet().getItemMeta().getCustomModelData() == ctb.GetOpenCMD()))
														{
															ctb.UpdateState(b, t.isOpen());
														}
													}
												}
												
												
											}
											
											for(TickBlock tb : TickBlocks)
											{
												CustomBlock cb = (CustomBlock)tb;
												if(cb.CheckForCustomBlock(as.getLocation().getBlock()))
												{
													tb.Tick(as.getLocation().getBlock());
												}
												
											}
											
											for(Entry<Location, LiquidBlock> el : Main.Liquids.entrySet())
											{
												if(Main.LiquidTick % el.getValue().FlowTime == 0)
												{
													if(el.getValue().CheckForCustomBlock(e.getLocation().getBlock()))
													{
														if(p.getLocation().getBlock().getType() == Material.LIGHT_WEIGHTED_PRESSURE_PLATE)
														{
															int p_x = p.getLocation().getBlockX();
															int p_z = p.getLocation().getBlockZ();
															int b_x = e.getLocation().getBlockX();
															int b_z = e.getLocation().getBlockZ();
															int flowDist = el.getValue().FlowSpan + 1;
															if(p_x < b_x + flowDist && p_x > b_x - flowDist && p_z < b_z + flowDist && p_z > b_z - flowDist && e.getLocation().getBlockY() == p.getLocation().getBlockY())
															{
																if(el.getValue().LiquidDamage > 0) {p.damage(el.getValue().LiquidDamage);}
																if(el.getValue().LiquidDamage < 0) {double i = p.getHealth() - el.getValue().LiquidDamage; if(i < 20) {p.setHealth(p.getHealth() - el.getValue().LiquidDamage);}}
																if(el.getValue().GetLiquidEffects().size() != 0)
																{
																	for(PotionEffect pe : el.getValue().GetLiquidEffects())
																	{
																		p.addPotionEffect(pe);
																	}
																}
															}
														}
														
														Location el_key_loc = el.getKey().getBlock().getLocation();
														
														for(Entry<ArmorStand, Location> es : el.getValue().flowLocations.entrySet())
														{
															if(es.getKey().getLocation().getBlock().getType() == Material.AIR) {el.getValue().flowLocations.remove(es.getKey());es.getKey().remove();continue;}
															
															if(es.getValue().distance(el_key_loc) > 0.6f) {continue;}
															
															if(es.getKey().getLocation().getBlock().getRelative(BlockFace.UP).getLocation().distance(el_key_loc) > el.getValue().FlowSpan) {continue;}
															
															CheckLiquidFlow(es.getKey().getLocation().getBlock().getRelative(BlockFace.UP).getRelative(BlockFace.EAST), el.getValue(), el_key_loc, false);
															CheckLiquidFlow(es.getKey().getLocation().getBlock().getRelative(BlockFace.UP).getRelative(BlockFace.WEST), el.getValue(), el_key_loc, false);
															CheckLiquidFlow(es.getKey().getLocation().getBlock().getRelative(BlockFace.UP).getRelative(BlockFace.SOUTH), el.getValue(), el_key_loc, false);
															CheckLiquidFlow(es.getKey().getLocation().getBlock().getRelative(BlockFace.UP).getRelative(BlockFace.NORTH), el.getValue(), el_key_loc, false);
															//CheckLiquidFlow(es.getKey().getLocation().getBlock().getRelative(BlockFace.UP).getRelative(BlockFace.DOWN), el.getValue(), el_key_loc, true);
														}
														
														CheckLiquidFlow(el.getKey().getBlock().getRelative(BlockFace.EAST), el.getValue(), el_key_loc, false);
														CheckLiquidFlow(el.getKey().getBlock().getRelative(BlockFace.WEST), el.getValue(), el_key_loc, false);
														CheckLiquidFlow(el.getKey().getBlock().getRelative(BlockFace.SOUTH), el.getValue(), el_key_loc, false);
														CheckLiquidFlow(el.getKey().getBlock().getRelative(BlockFace.NORTH), el.getValue(), el_key_loc, false);
														CheckLiquidFlow(el.getKey().getBlock().getRelative(BlockFace.DOWN), el.getValue(), el_key_loc, true);
													}
												}
											}
										}
									}
								}
							}
						}
					}
					
					if(MachineRepopTick < 0)
					{
						MachineRepopTick = 20 * 45;
					}
					
					Main.LiquidTick++;
					
					if(Main.LiquidTick == 1000)
					{
						Main.LiquidTick = 0;
					}
					
				}catch(Exception e)
				{
					e.printStackTrace();
				}
			}
		}, 1, 1);
		
		
	}
	
	public static final Material[] replaceable_liquid = new Material[] {Material.AIR, Material.CAVE_AIR, Material.VOID_AIR, Material.GRASS, Material.TALL_GRASS, Material.VINE};
	
	public static void CheckLiquidFlow(Block b, LiquidBlock lb, Location source, boolean down)
	{
		boolean cont = false;
		for(Material check : replaceable_liquid)
		{
			if(check == b.getType()) {cont = true;}
			if((b.getRelative(BlockFace.DOWN).getType() == check && !down) || b.getRelative(BlockFace.DOWN).getType() == Material.LIGHT_WEIGHTED_PRESSURE_PLATE)
			{
				return;
			}
		}
		
		if(!cont) {return;}
		
		if(!down)
		{
			b.setType(Material.LIGHT_WEIGHTED_PRESSURE_PLATE);
			
			ArmorStand as = (ArmorStand)b.getWorld().spawnEntity(new Location(b.getLocation().getWorld(), b.getLocation().getX() + 0.5f, 0.1f + b.getLocation().getY() - (b.getLocation().distance(source)/(lb.FlowSpan + 1)) + (down ? 0.15 : 0), b.getLocation().getZ() + 0.5f), EntityType.ARMOR_STAND);
			
			as.setInvisible(true);
			as.setSmall(true);
			as.setMarker(true);
			as.setInvulnerable(true);
			as.getEquipment().setHelmet(lb.getRootItem().GetMyItemStack());
			
			lb.flowLocations.put(as, source);
		}else {
			lb.Place(b.getLocation(), true);
		}
	}
	
	public static void Disable()
	{
		for(LiquidBlock lb : Liquids.values())
		{
			for(ArmorStand as : lb.flowLocations.keySet())
			{
				as.getLocation().getBlock().getRelative(BlockFace.UP).setType(Material.AIR);
				as.remove();
			}
		}
		
		for(World world : Bukkit.getWorlds())
		{
			if(world.getPopulators().contains(Populator))
			{
				world.getPopulators().remove(Populator);
			}
		}
	}
	
	public static void Log(String log)
	{
		for(Player p : Bukkit.getOnlinePlayers())
		{
			p.sendMessage(ChatColor.WHITE + log);
		}
		
		System.out.println("[" + PluginName + "] " + log);
	}
	
}
