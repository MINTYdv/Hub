package com.minty.leemonmc.hub.shop.gui;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.minty.leemonmc.basics.core.cache.Account;
import com.minty.leemonmc.core.util.GuiBuilder;
import com.minty.leemonmc.core.util.GuiUtils;
import com.minty.leemonmc.hub.LeemonHub;
import com.minty.leemonmc.hub.shop.core.ShopArticle;

public class ShopConfirmationMenu implements GuiBuilder {

	private GuiUtils utils;
	private LeemonHub main = LeemonHub.getInstance();
	private ShopArticle article;
	private GuiBuilder precedent;
	
	public ShopConfirmationMenu(GuiUtils _utils, GuiBuilder _precedent, ShopArticle _article) {
		this.utils = _utils;
		article = _article;
		precedent = _precedent;
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
		
		utils.fillWithItem(utils.pane(), 45, 54, inv);
		
		/* 
		 * CONTENT
		 * */
		
		ItemStack itemStack = article.getIcon(player);
		ItemMeta meta = itemStack.getItemMeta();
		
		List<String> lore = new ArrayList<>();
		lore.add("");
		if(article.getLemonsPrice(player) > 0)
		{
			lore.add("§6» §ePrix §7(Citrons) §f: §6" + article.getLemonsPrice(player) + " citrons");
		}
		if(article.getPulpePrice(player) > 0)
		{
			lore.add("§6» §ePrix §7(Pulpes) §f: §e" + article.getPulpePrice(player) + " pulpes");
		}
		lore.add("");
		lore.add("§7§oSouhaitez-vous confirmer la transaction ?");
		
		meta.setLore(lore);
		itemStack.setItemMeta(meta);
		inv.setItem(22, itemStack);
		
		ItemStack confirm_pulpes = main.getApi().getGuiUtils().createItem(Material.INK_SACK, "§eConfirmer §7(Pulpes)", (byte) 11);
		if(article.getPulpePrice(player) > 0)
		{
			inv.setItem(30, confirm_pulpes);
		} else {
			inv.setItem(30, main.getApi().getGuiUtils().createItem(Material.BARRIER, "§cPaiement en Pulpes impossible", (byte) 0));
		}
		
		ItemStack confirm_lemons = main.getApi().getGuiUtils().createItem(Material.INK_SACK, "§6Confirmer §7(Citrons)", (byte) 14);
		if(article.getLemonsPrice(player) > 0)
		{
			inv.setItem(32, confirm_lemons);
		} else {
			inv.setItem(32, main.getApi().getGuiUtils().createItem(Material.BARRIER, "§cPaiement en Citrons impossible", (byte) 0));
		}
		
		ItemStack annulation = main.getApi().getGuiUtils().createItem(Material.INK_SACK, "§cAnnuler", (byte) 1);
		inv.setItem(40, annulation);
	}
	
	@Override
	public int getSize()
	{
		return 54;
	}

	@Override
	public String name()
	{
		return "§6Boutique » Confirmation";
	}

	@SuppressWarnings("deprecation")
	@Override
	public void onClick(Player player, Inventory inv, ItemStack it, int slot)
	{
		if(it == null) return;
		
		switch(it.getType())
		{
			default:
				break;
		}
		
		if(!it.hasItemMeta()) return;
		
		if(it.getData().getData() == 1) // Annuler
		{
			main.getApi().getGuiManager().open(player, precedent.getClass());
		}
		
		String UUID = player.getUniqueId().toString();
		Account account = main.getApi().getAccountManager().getAccount(UUID);
		
		if(it.getData().getData() == 11) // Confirmer (pulpes)
		{
			player.closeInventory();
			
			account.removePulpe(article.getPulpePrice(player));
			article.purchase(player);
		}
		
		if(it.getData().getData() == 14) // Confirmer (pulpes)
		{
			player.closeInventory();
			
			account.removeLemons(article.getLemonsPrice(player));
			article.purchase(player);
		}
	}

	@Override
	public void onRightClick(Player arg0, Inventory arg1, ItemStack arg2, int arg3) {
		
		
	}

}
