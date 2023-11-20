package com.willm.CoreMOD.Shopping;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;

import com.willm.ModAPI.MobDrop;
import com.willm.ModAPI.Items.CustomItemStack;
import com.willm.ModAPI.Items.ItemCreator;
import com.willm.ModAPI.Items.Recipes.RecipeBuilder;
import com.willm.ModAPI.Items.Recipes.RecipeTemplates;

public class Currency {
	
	public static CustomItemStack coin, coin_stack, coin_pouch, bill, bill_pile;
	
	public static void RegisterCoins()
	{
		coin = ItemCreator.RegisterNewItem(new CustomItemStack("Coin", Material.GOLD_NUGGET, 10001));
		
		ItemCreator.RegisterMobDrop(new MobDrop(EntityType.HUSK, 500), coin, 1);
		ItemCreator.RegisterMobDrop(new MobDrop(EntityType.HUSK, 500), coin, 2);
		ItemCreator.RegisterMobDrop(new MobDrop(EntityType.HUSK, 500), coin, 3);

		ItemCreator.RegisterMobDrop(new MobDrop(EntityType.STRAY, 500), coin, 1);
		ItemCreator.RegisterMobDrop(new MobDrop(EntityType.STRAY, 500), coin, 2);
		ItemCreator.RegisterMobDrop(new MobDrop(EntityType.STRAY, 500), coin, 3);

		coin_stack = ItemCreator.RegisterNewItem(new CustomItemStack("Coin Stack", Material.GOLD_NUGGET, 10002));
		coin_stack.getRecipe(1, RecipeTemplates.Ingot_Block, "coin_stack").AddMaterial('I', RecipeBuilder.ItemStackInput(coin)).Finalize();
		coin.createSingleItemRecipe("coin_from_stack", 9, RecipeBuilder.ItemStackInput(coin_stack));
				
		//coin_stack.createSingleItemRecipe("coin_from_emerald", 3, Material.EMERALD);

		coin_pouch = ItemCreator.RegisterNewItem(new CustomItemStack("Coin Pouch", Material.GOLD_NUGGET, 10003));
		coin_pouch.getRecipe(1, RecipeTemplates.Ingot_Block, "coin_pouch").AddMaterial('I', RecipeBuilder.ItemStackInput(coin_stack)).Finalize();
		coin_stack.createSingleItemRecipe("coin_stack_from_pouch", 9, RecipeBuilder.ItemStackInput(coin_pouch));
		
		coin_pouch.createSingleItemRecipe("coin_pouch_from_diamond", 1, Material.DIAMOND);

		ItemCreator.RegisterMobDrop(new MobDrop(EntityType.PIGLIN_BRUTE, 800), coin_pouch, 2);

		bill = ItemCreator.RegisterNewItem(new CustomItemStack("Bill", Material.GOLD_NUGGET, 10004));
		bill.getRecipe(1, RecipeTemplates.Ingot_Block, "bill").AddMaterial('I', RecipeBuilder.ItemStackInput(coin_pouch)).Finalize();
		coin_pouch.createSingleItemRecipe("coin_pouch_from_bill", 9, RecipeBuilder.ItemStackInput(bill));
		
		bill.getRecipe(1, RecipeTemplates.Ingot_Block, "craft_bill_from_diamond").AddMaterial('I', Material.DIAMOND).Finalize();
				
		bill_pile = ItemCreator.RegisterNewItem(new CustomItemStack("Bill Pile", Material.GOLD_NUGGET, 10005));
		bill_pile.getRecipe(1, RecipeTemplates.Ingot_Block, "bill_pile").AddMaterial('I', RecipeBuilder.ItemStackInput(bill)).Finalize();
		bill.createSingleItemRecipe("bill_from_bill_pile", 9, RecipeBuilder.ItemStackInput(bill_pile));
		
		bill_pile.getRecipe(1, RecipeTemplates.Ingot_Block, "bill_pile_from_diamond_block").AddMaterial('I', Material.DIAMOND_BLOCK).Finalize();
	}
	
}
