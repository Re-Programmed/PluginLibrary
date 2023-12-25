package com.willm.ModAPI.Entities;

import org.bukkit.Bukkit;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.entity.EntityDamageEvent;

import com.willm.CoreMOD.Main;
import com.willm.ModAPI.Utils;

public abstract class BossEntity extends CustomEntity {
	
	//Store the current boss fight data.
	protected BossFightData currentFight = null;

	public BossEntity(String name, EntityType type, int maxHealth)
	{
		super(name, type, maxHealth);
	}
	
	protected abstract BossBar createBossBar();
	
	//Called when the boss fight begins. Override to add effects.
	public void BeginBossFight(LivingEntity entity)
	{
		BossFightEventHandler events = new BossFightEventHandler(this, entity);
		Bukkit.getServer().getPluginManager().registerEvents(events, Main.INSTANCE);
		
		Runnable bossTick = new Runnable() {
			public void run() {
				BossFightTick();
			}
		};
				
		currentFight = new BossFightData(entity, createBossBar(), events, Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.INSTANCE, bossTick, 1, 1));
		
		for(Player p : Bukkit.getOnlinePlayers())
		{
			if(p.getLocation().distance(entity.getLocation()) < 100f)
			{
				currentFight.ActiveBossBar.addPlayer(p);
			}
		}
	}
	
	//Called when the boss fight should end. Override to add effects.
	public void EndBossFight()
	{
		Utils.BroadcastMessage("BOSS OVER");
		Bukkit.getScheduler().cancelTask(currentFight.BossTickID);
		HandlerList.unregisterAll(currentFight.ActiveBossHandler);
		currentFight.ActiveBossFightEntity.remove();
		currentFight.ActiveBossBar.removeAll();
		currentFight = null;
	}
	
	//Called each frame, allowing the boss to perform some attacks.
	public abstract void BossFightTick();
	
	public boolean GetBossFightActive()
	{
		return currentFight != null;
	}
	
	public void BossFightEventHandler(BossFightEvent event)
	{
		/*if(event == BossFightEvent.BOSS_DAMAGED)
		{
			if(currentFight.ActiveBossFightEntity.getHealth() - ((EntityDamageEvent)event.Event).getDamage() <= 0)
			{
				EndBossFight();
			}
		}*/
		
		if(event == BossFightEvent.BOSS_KILLED)
		{
			EndBossFight();
		}
	}

	
}
