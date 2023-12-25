package com.willm.ModAPI.Entities;

import org.bukkit.boss.BossBar;
import org.bukkit.entity.LivingEntity;

public class BossFightData {
	public LivingEntity ActiveBossFightEntity;
	public final BossBar ActiveBossBar;
	public final BossFightEventHandler ActiveBossHandler;
	
	public final int BossTickID;
	
	public BossFightData(LivingEntity entity, BossBar bb, BossFightEventHandler events, int bt)
	{
		this.ActiveBossFightEntity = entity;
		this.ActiveBossBar = bb;
		this.ActiveBossHandler = events;
		
		this.BossTickID = bt;
	}
}
