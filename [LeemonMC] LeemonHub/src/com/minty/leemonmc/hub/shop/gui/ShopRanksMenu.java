package com.minty.leemonmc.hub.shop.gui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.minty.leemonmc.basics.core.Rank;
import com.minty.leemonmc.core.CoreMain;
import com.minty.leemonmc.core.util.GuiBuilder;
import com.minty.leemonmc.core.util.GuiUtils;
import com.minty.leemonmc.hub.LeemonHub;
import com.minty.leemonmc.hub.shop.articles.ARankCustom;
import com.minty.leemonmc.hub.shop.articles.ARankLemon;
import com.minty.leemonmc.hub.shop.articles.ARankVIP;
import com.minty.leemonmc.hub.shop.articles.ARankVIPPlus;
import com.minty.leemonmc.hub.shop.core.ShopArticle;

public class ShopRanksMenu implements GuiBuilder {

	private GuiUtils utils;
	private LeemonHub main = LeemonHub.getInstance();
	private Map<Integer, ShopArticle> articlesSlots = new HashMap<>();
	
	public ShopRanksMenu(GuiUtils _utils) {
		this.utils = _utils;
	}
	
	@Override
	public void contents(Player player, Inventory inv) {

		/* 
		 * LORES
		 * */
		
		
		/* 
		 * DECORATION
		 * */
		
		utils.fillWithItem(utils.pane(), 0, 9, inv);
		
		inv.setItem(9, utils.pane());
		inv.setItem(9 * 2, utils.pane());
		inv.setItem(9 * 3, utils.pane());
		inv.setItem(9 * 4, utils.pane());
		inv.setItem(9 * 5, utils.pane());
		
		inv.setItem(8 * 2 + 1, utils.pane());
		
		inv.setItem(8 * 3 + 2, utils.pane());
		inv.setItem(8 * 4 + 3, utils.pane());
		
		inv.setItem(8 * 5 + 4, utils.pane());
		
		utils.fillWithItem(utils.pane(), 45, 48, inv);
		utils.fillWithItem(utils.pane(), 51, 54, inv);
		
		inv.setItem(49, utils.cancelItem());
		inv.setItem(48, utils.backItem());
		
		/* 
		 * CONTENT
		 * */
		
		ShopArticle article = main.getShopArticlesHandler().getArticle(ARankVIP.class);
		inv.setItem(21, article.getIcon(player));
		articlesSlots.put(21, article);
		
		ShopArticle vipplus = main.getShopArticlesHandler().getArticle(ARankVIPPlus.class);
		inv.setItem(22, vipplus.getIcon(player));
		articlesSlots.put(22, vipplus);
		
		ShopArticle lemon = main.getShopArticlesHandler().getArticle(ARankLemon.class);
		inv.setItem(23, lemon.getIcon(player));
		articlesSlots.put(23, lemon);
		
		ShopArticle custom = main.getShopArticlesHandler().getArticle(ARankCustom.class);
		inv.setItem(31, custom.getIcon(player));
		articlesSlots.put(31, custom);
	}

	@Override
	public int getSize()
	{
		return 54;
	}

	@Override
	public String name()
	{
		return "§6Boutique » Grades";
	}

	@Override
	public void onClick(Player player, Inventory inv, ItemStack it, int slot)
	{
		switch(it.getType())
		{
			case BARRIER:
				player.closeInventory();
				break;
			case ARROW:
				LeemonHub.getInstance().getApi().getGuiManager().open(player, ShopMenu.class);
				break;
			default:
				break;
		}
		
		if(articlesSlots.containsKey(slot))
		{
			LeemonHub.getInstance().getShopArticlesHandler().openConfirmationMenu(player, this, articlesSlots.get(slot));
		}
		
	}

	@Override
	public void onRightClick(Player arg0, Inventory arg1, ItemStack arg2, int arg3) {
		
		
	}

}
