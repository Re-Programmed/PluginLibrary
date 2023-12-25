package com.willm.ModAPI.Entities;

import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;

public class BossFightEventHandler implements Listener {

	private final BossEntity myBoss;
	public LivingEntity myBossLE;
	
	//Should be created every time a boss fight starts to track boss events.
	public BossFightEventHandler(BossEntity be, LivingEntity le)
	{
		myBoss = be;
		myBossLE = le;
	}
	
	@EventHandler
	public void EntityDamaged(EntityDamageEvent event)
	{
		if(event.getEntity() == myBossLE)
		{
			BossFightEvent.BOSS_DAMAGED.Event = event;
			myBoss.BossFightEventHandler(BossFightEvent.BOSS_DAMAGED);
		}
	}

	@EventHandler
	public void EntityKilled(EntityDeathEvent event)
	{
		if(event.getEntity() == myBossLE)
		{
			BossFightEvent.BOSS_KILLED.Event = event;
			myBoss.BossFightEventHandler(BossFightEvent.BOSS_KILLED);
		}
	}
	
}
