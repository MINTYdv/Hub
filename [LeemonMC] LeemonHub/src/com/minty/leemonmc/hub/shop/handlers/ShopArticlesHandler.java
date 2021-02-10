package com.minty.leemonmc.hub.shop.handlers;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Sound;
import org.bukkit.entity.Player;

import com.minty.leemonmc.basics.core.cache.Account;
import com.minty.leemonmc.core.util.GuiBuilder;
import com.minty.leemonmc.hub.LeemonHub;
import com.minty.leemonmc.hub.shop.articles.ARankCustom;
import com.minty.leemonmc.hub.shop.articles.ARankLemon;
import com.minty.leemonmc.hub.shop.articles.ARankVIP;
import com.minty.leemonmc.hub.shop.articles.ARankVIPPlus;
import com.minty.leemonmc.hub.shop.core.ShopArticle;
import com.minty.leemonmc.hub.shop.gui.ShopConfirmationMenu;

public class ShopArticlesHandler {

	private LeemonHub main = LeemonHub.getInstance();
	private Map<Class<? extends ShopArticle>, ShopArticle> shopArticles = new HashMap<>();
	
	public void setup()
	{
		register(new ARankVIP());
		register(new ARankVIPPlus());
		register(new ARankLemon());
		register(new ARankCustom());
	}
	
	private void register(ShopArticle article)
	{
		if(!getShopArticles().containsKey(article.getClass()))
		{
			getShopArticles().put(article.getClass(), article);
		}
	}
	
	public void openConfirmationMenu(Player player, GuiBuilder _precedent, ShopArticle article)
	{
		if(article.isPurchased(player))
		{
			player.sendMessage("§6§lBoutique §f» §cVous possédez déjà ce grade/cet article !");
			return;
		}

		String UUID = player.getUniqueId().toString();
		Account account = main.getApi().getAccountManager().getAccount(UUID);
		
		if(article.getLemonsPrice(player) > account.getLemons())
		{
			player.playSound(player.getLocation(), Sound.ENTITY_BLAZE_DEATH, 1f, 1f);
			
			player.sendMessage("§c§m---------------------------------------");
			player.sendMessage("");
			player.sendMessage("§f§l➜  §eVous n'avez pas assez de citrons");
			player.sendMessage("      §epour cet article !");
			player.sendMessage("");
			player.sendMessage("§f§l➜  §ePlus de citrons ?");
			player.sendMessage("      §6https://store.leemonmc.fr");
			player.sendMessage("");
			player.sendMessage("§c§m---------------------------------------");
			return;
		}
		
		if(article.getPulpePrice(player) > account.getLemons())
		{
			player.playSound(player.getLocation(), Sound.ENTITY_BLAZE_DEATH, 1f, 1f);
			
			player.sendMessage("§c§m---------------------------------------");
			player.sendMessage("");
			player.sendMessage("§f§l➜  §eVous n'avez pas assez de pulpe");
			player.sendMessage("      §epour cet article !");
			player.sendMessage("");
			player.sendMessage("§f§l➜  §eComment gagner de la pulpe ?");
			player.sendMessage("      §6Pour gagner des pulpes, jouer");
			player.sendMessage("      §6effectuez des kills et gagnez");
			player.sendMessage("      §6des parties !");
			player.sendMessage("");
			player.sendMessage("§c§m---------------------------------------");
			return;
		}
		
		ShopConfirmationMenu menu = new ShopConfirmationMenu(main.getApi().getGuiUtils(), _precedent, article);
		main.getApi().getGuiManager().addMenu(menu, player);
		main.getApi().getGuiManager().open(player, menu.getClass());
	}
	
	public ShopArticle getArticle(Class<? extends ShopArticle> _class)
	{
		return getShopArticles().get(_class);
	}
	
	/* 
	 * Getters & Setters
	 * */
	
	public Map<Class<? extends ShopArticle>, ShopArticle> getShopArticles() {
		return shopArticles;
	}
	
}
