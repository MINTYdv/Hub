package com.minty.leemonmc.hub.shop.core;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ShopArticle {

	public String getName(Player player)
	{
		return "§cError";
	}
	
	public ItemStack getIcon(Player player)
	{
		return new ItemStack(Material.STONE);
	}
	
	public int getPulpePrice(Player player)
	{
		return 404;
	}
	
	public int getLemonsPrice(Player player)
	{
		return 404;
	}
	
	public boolean isPurchased(Player player)
	{
		return false;
	}
	
	public void purchase(Player player)
	{
		player.sendMessage("§6§lBoutique §f» §7Achat effectué avec succès !");
	}
	
}
