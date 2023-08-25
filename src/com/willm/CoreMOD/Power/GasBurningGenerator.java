package com.willm.CoreMOD.Power;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.willm.CoreMOD.MyItems;
import com.willm.ModAPI.Utils;
import com.willm.ModAPI.Blocks.Machine;
import com.willm.ModAPI.Blocks.MachineConversion;
import com.willm.ModAPI.Items.BlockCreator;
import com.willm.ModAPI.Items.CustomItemStack;
import com.willm.ModAPI.Items.ItemCreator;
import com.willm.ModAPI.Items.Recipes.RecipeBuilder;
import com.willm.ModAPI.Voltage.Main;
import com.willm.ModAPI.Voltage.Blocks.EnergyCompatible;

public class GasBurningGenerator extends EnergyCompatible {

	public int cooldown = 0;
	
	public GasBurningGenerator()
	{
		CustomItemStack cis = ItemCreator.RegisterNewItem(new CustomItemStack("Gas Burning Generator", Material.POLISHED_ANDESITE, 61025));
				
		blockRef = BlockCreator.RegisterNewBlock(cis, "core_mod.drill_idle", 0, 27, "Gas Burning Generator", new MachineConversion(MyItems.gasoline.GetMyItemStack(), new CustomItemStack("Power", Material.COAL, 105).GetMyItemStack()));
	
		cis.getRecipe(1, "CCC", "cOc", "GEG").AddMaterial('C', Material.GRAY_CONCRETE).AddMaterial('c', RecipeBuilder.ItemStackInput(MyItems.Casing)).AddMaterial('O', RecipeBuilder.ItemStackInput(MyItems.generator_core)).AddMaterial('G', RecipeBuilder.ItemStackInput(MyItems.electronic_gearshift)).AddMaterial('E', RecipeBuilder.ItemStackInput(MyItems.engine)).Finalize();
	}
	

	@Override
	public void Tick(Location loc) {

		cooldown--;
		
		
		boolean output = false, outIdle = false;
		

			
			for(Machine m : blockRef.m)
			{
			
				if(m.location.distance(loc) < 0.1f)
				{
					if(m.getInventory().containsAtLeast(MyItems.gasoline.GetMyItemStack(), 1))
					{
						Utils.PlayCustomSound("core_mod.kiln_spin", loc);
						
						
						if(cooldown < 0)
						{
							cooldown = 75;
							output = true;
							AddEnergy(1000, loc);
							
						
							for(BlockFace bf : checkFaces)
							{
								Block b = loc.getBlock().getRelative(bf);
								for(EnergyCompatible ec : Main.energyRecievers)
								{
									if(ec.GetBlockRef().CheckForCustomBlock(b))
									{
										ec.AddEnergy(RemoveEnergy(575, loc), b.getLocation());
									}
								}
							}
							
							m.getInventory().removeItem(MyItems.gasoline.GetMyItemStack());
						}else {
							outIdle = true;
							AddEnergy(55, loc);
							for(BlockFace bf : checkFaces)
							{
								Block b = loc.getBlock().getRelative(bf);
								for(EnergyCompatible ec : Main.energyRecievers)
								{
									if(ec.GetBlockRef().CheckForCustomBlock(b))
									{
										ec.AddEnergy(RemoveEnergy(30, loc), b.getLocation());
									}
								}
							}
						}
					}
	
					
				}
		}
		
		for(Player p : Bukkit.getOnlinePlayers())
		{
			if(p.getLocation().distance(loc) < 2f)
			{
				if(p.isSneaking())
				{
					p.sendMessage(ChatColor.DARK_PURPLE + "[GAS BURNING GENERATOR OUTPUT]: " + (output ? "575" : (outIdle ? "30" : "0")));
				}
			}
		}
	
	}
	
	
}