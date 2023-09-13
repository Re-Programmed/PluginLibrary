package api.example;

import org.bukkit.plugin.java.JavaPlugin;

/*
 All the files in api.example are here as an example of how to use the Mod system! Replace the main file in plugin.yml with your plugin Main and delete the api.example package.
 */

public class Main extends JavaPlugin {

		
	@Override
	public void onEnable()
	{
		//PUT ModAPI.Main.Launch() here!
		
		com.willm.ModAPI.Main.Launch("TestPlugin", this);
		
		MyItems.RegisterMyItems();
	}
	
	@Override
	public void onDisable()
	{
		com.willm.ModAPI.Main.Disable();
	}
}
