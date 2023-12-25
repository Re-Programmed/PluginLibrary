package com.willm.CoreMOD.CustomCommands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CMapCommand implements CommandExecutor {

	public static String[] mapIndexes = new String[] {
			"Horn Pub",
			"Cock Department",
			"Train Kid",
			"Berry",
			"Cookie Monster",
			"Dorito",
			"Lightskin Stare",
			"Hiroshima",
			"Dragon Eggos",
			":O",
			"Even at my Lois I'm still Griffin it my all",
			"Holy Bible",
			"Biggie Cheese",
			"CBT Dungeon",
			"Gushers",
			"Vaporeon",
			"Spunchbob Suck Patrick",
			"Smoker Steve",
			"Gaming With The Homies",
			"Uh Oh Sexy Time",
			"Hop On Ball Hall",
			"Meth Makers",
			"Heroin In Minecraft",
			"I Love Miners",
			"Froggy Allay Sex",
			"They are Watching Head to the DMs",
			"I'm leaving this cake out for santa this year",
			"Carmelized Onions",
			"Doritos",
			"Even at my Lois I'm still Griffin it my all",
			"L Plus Naughty List",
			"Porno",
			"Perry The Platypus",
			"Throw Me To The Wolves and I will return a father",
			"Lightning McQueen",
			"Testiculos",
			"Rest in peace Queen Elizabeth II",
			"LIE Buzzer",
			"Pregnant Pikachu",
			"Shithead",
			"They are Watching Head to the DMs",
			"Sus Nine Eleven",
			"Holy Bible",
			"Football at its peak",
			"SLAVES",
			"KLUX",
			"SIX BLACK MEN",
			"TOWERS",
			"CAUST",
			"KIM",
			"Vaporeon",
			"GASSY",
			"Smoker Ratatouille",
			"Horn Pub",
			"Alcahal",
			"Person.Java",
			"CHOCCY MILK",
			"Berry",
			"Hitler Dead My Little Pony",
			"Wonking Thy Willy",
			"Confederate Truck",
			"Foxhole",
			"Minecraft THE SEX UPDATE",
			"FNAF Lightskin Stare",
			"Testicle Minion",
			"Pipe Bomb :3",
			"Ed Sheeran But In Real Life",
			"Walter White",
			"Breezer",
			"Shotgun",
			"She can see my chamber of secrets",
			"The boy who lifted",
			"Babies first vape",
			"Dr Suess FNAF",
			"Hop On Ball Hall",
			"Furry Song",
			"Angry Birds (Hard)",
			"Dallying With Gents",
			"Pikmin",
			":O 2",
			"Trump pregnant wolf",
			"I Show Peen",
			"Whatsapp",
			"Is Sex A Phone",
			"Anus Manuver",
			"Delete that shit right now",
			"Heroin in Minecraft",
			"The Penis Monkey",
			"CP World",
			"Porn Hub Kids",
			"Porn Hub Kids 2",
			"Spunchbob Suck Patrick",
			"Average Digger",
			"Dominating",
			"Kissming",
			"Smookdhing",
			"Danting",
			"Uh Oh Sexy Time",
			"Me Inspecting Your Cock",
			"Duke Nukem",
			"Lovely Gent",
			"JEWS JEWS JEWS JEWS",
			"Make me cum with your feet",
			"YESSS ASHHHH AHHH",
			"Pillager Heil",
			"Dont google Porn",
			"When u r white and you eat hot chip",
			"Mark Zuckerberg N Word",
			"I Am in her septic tank",
			"Ion Let Shit Bother me",
			"Whos getting the most ionizing radiation",
			"I Fucked you Mom, and Dad",
			"Feet Sniffa",
			"Life Is Roblox",
			"DJ Kale Twerk",
			"Wolf Fuck",
			"Meth Makers",
			"Scary Man",
			"I beat my MEAT",
			"Ice Spice",
			"Motorist Guy",
			"Holy Moly",
			"Wolf Fuck 2",
			"Gaming With The Homies",
			"Suck Mein PP",
			"Dragon Eggos",
			"Attention Group Masterbation in 10",
			"Jimmy Nuetron Dad",
			"White Boy",
			"Ahh, How the tides have turned",
			"Coming back into my sons life prank",
			"Toyota",
			"Wenamechainthesimma Dog",
			"Minion Coffin",
			"Always Judge People By Race and Religion",
			"Cat Named Gay Porn",
			"Inflation",
			"Hitler Games",
			"Last Night I Your Sister",
			"Unpicks your pickaxe",
			"Small Penis Bonus",
			"N Word Run Gameplay",
			"Scary SCP 23",
			"Scary SCP 23",
			"Subway is better",
			"Wanna Ride Me (Yoshi)",
			"Evee O's",
			"Hotter Pockets",
			"Subway Eat Fresh",
			"There is motion at your front door",
			"Femboy Eggos",
			"Hidden Valley Ranch",
			"Hidden Valley Ranch 2",
			"Inflation", //Unused
			"Donald Trump God Damn",
			"Regular Show",
			"Would you like a sprite cranberry",
			"Inflation", //Unused
			"Thats the Subway",
			"Zootopia",
			"Uh Oh Eggos",
			"Subway Cum",
			"The Grip of 87",
			"Faznuts",
			"Oh no",
			"Oh no 2",
			"Want a Dorito",
			"FNAF Smexy",
			"Fishing Hole",
			"Inflation", //Unused
			"Ender Dragon Bedtime",
			"Average Terraria Player",
			"Inflation", //Unused
			"Pokimane",
			"Hard Pope",
			"Pokimane 2",
			"Ice Spice 2024",
			"Pokimane 3",
			"MarkerPliers",
			"Brandon Speculative",
			"Black Man Go Crazy",
			"COMMUNISM",
			"Broccliboma",
			"Many Black Men Have Died Ender Pearl",
			"Black Man Go Crazy 2",
			"Do Not Fist Animatronic Girls",
			"Porn",
			"Google Slides",
			"Im like black ops 1 guy",
			"Im like black ops 2 guy",
			"Im like black ops 3 guy",
			"Sometimes The",
			"When Jizz, I Mean Jazz",
			"Willyum"
	};
	
	@Override
	public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
		
		if(arg2.equalsIgnoreCase("cmap"))
		{
			if(arg0 instanceof Player)
			{
				Player p = (Player)arg0;
				
				if(arg3.length > 0)
				{
					String search = "";
					
					for(String s : arg3)
					{
						search += s + " ";
					}
					
					search = search.substring(0, search.length() - 1);
					
					int i = 0;
					for(String s : mapIndexes)
					{
						if(s.equalsIgnoreCase(search.toLowerCase()))
						{
							Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "give " + p.getName() + " filled_map{display:{Lore:['{\"text\":\"" + s + "\",\"color\":\"yellow\"}']},map:" + i + "} 1");
							arg0.sendMessage(ChatColor.GREEN + "Gave Map: " + s);
							return true;
						}
						
						i++;
					}
					
					int ic = Integer.parseInt(search);
					String name = "Map " + ic;
					
					if(mapIndexes.length > ic)
					{
						name = mapIndexes[ic];
					}
					
					Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "give " + p.getName() + " filled_map{display:{Lore:['{\"text\":\"" + name + "\",\"color\":\"yellow\"}']},map:" + ic + "} 1");
					arg0.sendMessage(ChatColor.GREEN + "Gave Map: " + name);
					return true;
				}else {		
					arg0.sendMessage(ChatColor.RED + "Please specify a map name or ID.");
					return false;
				}
			}
		}
		
		return false;
	}

}
