package api.example;

import org.bukkit.Material;

import com.willm.ModAPI.Items.CustomItemStack;
import com.willm.ModAPI.Items.ItemCreator;

public class MyItems {

	public static void RegisterMyItems()
	{
		ItemCreator.RegisterNewItem(new CustomItemStack("Test Item", Material.IRON_INGOT, 10001));
	}

}
