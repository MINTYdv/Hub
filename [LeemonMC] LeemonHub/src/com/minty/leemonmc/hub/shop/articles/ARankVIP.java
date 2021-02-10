package com.minty.leemonmc.hub.shop.articles;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.minty.leemonmc.basics.core.Rank;
import com.minty.leemonmc.basics.core.cache.Account;
import com.minty.leemonmc.core.CoreMain;
import com.minty.leemonmc.hub.LeemonHub;
import com.minty.leemonmc.hub.shop.core.ShopArticle;

public class ARankVIP extends ShopArticle {

	@SuppressWarnings("unused")
	private LeemonHub main = LeemonHub.getInstance();
	
	@Override
	public String getName(Player player)
	{
		return "§cError";
	}
	
	@Override
	public ItemStack getIcon(Player player)
	{
		return getVIPItem(player);
	}
	
	@Override
	public int getPulpePrice(Player player)
	{
		return 0;
	}
	
	@Override
	public int getLemonsPrice(Player player)
	{
		return 1150;
	}
	
	@Override
	public boolean isPurchased(Player player)
	{
		String UUID = player.getUniqueId().toString();
		Account account = main.getApi().getAccountManager().getAccount(UUID);
		
		return account.getRank().getPower() >= Rank.VIP.getPower();
	}
	
	@Override
	public void purchase(Player player)
	{
		super.purchase(player);
		
		String UUID = player.getUniqueId().toString();
		Account account = main.getApi().getAccountManager().getAccount(UUID);
		
		account.setRank(Rank.VIP.getPower());
	}
	
	private ItemStack getVIPItem(Player player)
	{
		List<String> VIPlore = new ArrayList<>();
		VIPlore.add("");
		VIPlore.add("§7Lors de §dl'achat §7de ce grade");
		VIPlore.add("§7vous obtiendrez les §eavantages suivants §7:");
		VIPlore.add("");
		VIPlore.add("§6§nAvantages génériques");
		VIPlore.add("§e➠ §7Préfixe dans le chat §8» §aVIP");
		VIPlore.add("§e➠ §7Préfixe personnalisable §8» §c✘");
		VIPlore.add("§e➠ §7Accès au /nick §8» §c✘");
		VIPlore.add("§e➠ §7Booster de pulpes §8» §6+20%");
		VIPlore.add("§e➠ §7Booster de citrons §8» §c✘");
		VIPlore.add("§e➠ §7Priorité dans la file d'attente §8» §6Elevée");
		VIPlore.add("§e➠ §7Emplacements de groupe §8» §65 membres");
		VIPlore.add("§e➠ §7Emplacements d'amis §8» §650 amis");
		VIPlore.add("§e➠ §7Réinitialisation des statistiques §8» §c✘");
		VIPlore.add("§e➠ §7Pouvoir directement rejoindre les serveurs §8» §a✔");
		VIPlore.add("");
		VIPlore.add("§6§nAvantages dans le hub");
		VIPlore.add("§e➠ §7Déplacement dans le hub §8» §6Double Jump");
		VIPlore.add("§e➠ §7Effet lors de la connexion dans le hub §8» §c✘");
		VIPlore.add("§e➠ §7Message lors de la connexion §8» §c✘");
		VIPlore.add("§e➠ §7Message lors de la connexion personnalisable §8» §c✘");
		VIPlore.add("");
		String UUID = player.getUniqueId().toString();
		
		if(!isPurchased(player))
		{
			VIPlore.add("§6» §eCliquez pour acheter §6(" + getLemonsPrice(player) + " Citrons)");
		} else {
			VIPlore.add("§4» §cVous possédez déjà ce grade ou un grade supérieur !");
		}
		
		ItemStack it = LeemonHub.getInstance().getApi().getLeemonUtils().getSkull("http://textures.minecraft.net/texture/ab9a36c5589f3a2e59c1caa9b3b88fada76732bdb4a7926388a8c088bbbcb");
		ItemMeta meta = it.getItemMeta();
		
		meta.setDisplayName("§7Grade §a§lVIP");
		meta.setLore(VIPlore);
		
		it.setItemMeta(meta);
		return it;
	}
	
}
