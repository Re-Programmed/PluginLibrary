package com.willm.CoreMOD.Alloying;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.willm.ModAPI.Items.CustomItemStack;

public class CreateAlloyPickCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(label.equalsIgnoreCase("alloypick"))
		{
			if(!sender.isOp()) {
				sender.sendMessage(ChatColor.RED + "You do not have permission to use this command!");
				return false;
			}
			AlloyMaterial mat1 = AlloyMaterial.valueOf(args[0]);
			AlloyMaterial mat2 = AlloyMaterial.valueOf(args[1]);
			
			CustomItemStack giveItem = new CustomItemStack(AlloyUtils.GetPickaxeName(mat1, mat2), Material.NETHERITE_PICKAXE, AlloyUtils.GetPickaxeCMD(mat1, mat2));
			if(sender instanceof Player)
			{
				((Player)sender).getInventory().addItem(giveItem.GetMyItemStack());
				return true;
			}
		}
		return false;
	}

}
