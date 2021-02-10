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

public class ARankCustom extends ShopArticle {

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
		return 2900;
	}
	
	@Override
	public boolean isPurchased(Player player)
	{
		String UUID = player.getUniqueId().toString();
		Account account = main.getApi().getAccountManager().getAccount(UUID);
		
		return account.getRank().getPower() >= Rank.CUSTOM.getPower();
	}
	
	@Override
	public void purchase(Player player)
	{
		super.purchase(player);
		
		String UUID = player.getUniqueId().toString();
		Account account = main.getApi().getAccountManager().getAccount(UUID);
		
		account.setRank(Rank.CUSTOM.getPower());
	}
	
	private ItemStack getVIPItem(Player player)
	{
		List<String> VIPlore = new ArrayList<>();
		VIPlore.add("");
		VIPlore.add("§7Lors de §dl'achat §7de ce grade");
		VIPlore.add("§7vous obtiendrez les §eavantages suivants §7:");
		VIPlore.add("");
		VIPlore.add("§6§nAvantages génériques");
		VIPlore.add("§e➠ §7Préfixe dans le chat §8» §dPersonnalisé");
		VIPlore.add("§e➠ §7Préfixe personnalisable §8» §a✔");
		VIPlore.add("§e➠ §7Accès au /nick §8» §a✔");
		VIPlore.add("§e➠ §7Booster de pulpes §8» §6+150%");
		VIPlore.add("§e➠ §7Booster de citrons §8» §c+50%");
		VIPlore.add("§e➠ §7Priorité dans la file d'attente §8» §6Ultra-Haute");
		VIPlore.add("§e➠ §7Emplacements de groupe §8» §6Illimité");
		VIPlore.add("§e➠ §7Emplacements d'amis §8» §6500 amis");
		VIPlore.add("§e➠ §7Réinitialisation des statistiques §8» §a✔");
		VIPlore.add("§e➠ §7Pouvoir directement rejoindre les serveurs §8» §a✔");
		VIPlore.add("");
		VIPlore.add("§6§nAvantages dans le hub");
		VIPlore.add("§e➠ §7Déplacement dans le hub §8» §6Fly");
		VIPlore.add("§e➠ §7Effet lors de la connexion dans le hub §8» §a✔");
		VIPlore.add("§e➠ §7Message lors de la connexion §8» §a✔");
		VIPlore.add("§e➠ §7Message lors de la connexion personnalisable §8» §a✔");
		VIPlore.add("");
		VIPlore.add("§6§l+ §eLes avantages du grade précédent");
		VIPlore.add("");
		String UUID = player.getUniqueId().toString();
		
		if(!isPurchased(player))
		{
			VIPlore.add("§6» §eCliquez pour acheter §6(" + getLemonsPrice(player) + " Citrons)");
		} else {
			VIPlore.add("§4» §cVous possédez déjà ce grade ou un grade supérieur !");
		}
		
		ItemStack it = LeemonHub.getInstance().getApi().getLeemonUtils().addGlow(main.getApi().getGuiUtils().createItem(Material.NAME_TAG, "§7Grade §d§lCustom", (byte) 0, VIPlore));
		return it;
	}
	
}
