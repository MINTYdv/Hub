package com.minty.leemonmc.hub.shop.gui;

import com.minty.leemonmc.core.util.GuiBuilder;
import com.minty.leemonmc.core.util.GuiUtils;
import com.minty.leemonmc.hub.LeemonHub;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ShopMenu implements GuiBuilder {

	private GuiUtils utils;
	
	public ShopMenu(GuiUtils _utils) {
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
		
		/* 
		 * CONTENT
		 * */
		
		inv.setItem(21, getHostItem());
		inv.setItem(22, getRanksItem());
		inv.setItem(23, getBoostersItem());
		
		inv.setItem(31, getMedalsItem());
	}
	
	private ItemStack getBoostersItem()
	{
		List<String> lore = new ArrayList<>();
		lore.add("§7Achète un §ebooster §7et §daugmente");
		lore.add("§7ton gain de §epulpe §7et §6citrons");
		lore.add("§7lors de §etes parties §7!");
		lore.add("");
		lore.add(utils.getAccess());
		
		ItemStack it = utils.createItem(Material.EXP_BOTTLE, "§6Boosters", (byte) 0, lore);
		return it;
	}
	
	private ItemStack getMedalsItem()
	{
		List<String> lore = new ArrayList<>();
		lore.add("§7Les §emédailles §7sont des caractères personnalisés");
		lore.add("§7affichés §eà côté de ton pseudo §7! Ce sont des");
		lore.add("§7récompenses §5rares §7qui sont pour la plupart");
		lore.add("§dexlusifs §7aux §eévènements §7!");
		lore.add("");
		lore.add(utils.getAccess());
		
		ItemStack it = utils.createItem(Material.MAGMA_CREAM, "§6Médailles", (byte) 0, lore);
		return it;
	}
	
	private ItemStack getHostItem()
	{
		List<String> lore = new ArrayList<>();
		lore.add("§7Grace à un §dabonnement host §7tu");
		lore.add("§7peux héberger tes §eparties personnalisées");
		lore.add("§7entièrement à ta guise ! Ta seule limite est");
		lore.add("§7ton imagination !");
		lore.add("");
		lore.add(utils.getNotImplementedLore());
		
		ItemStack it = LeemonHub.getInstance().getApi().getLeemonUtils().getSkull("http://textures.minecraft.net/texture/ba9053d2163d0f561145d33a513145d4ac1f8a458baa796be383e7525a05f45");
		ItemMeta meta = it.getItemMeta();
		
		meta.setDisplayName("§6Abonnement §5§lHOST");
		meta.setLore(lore);
		
		it.setItemMeta(meta);
		return it;
	}
	
	private ItemStack getRanksItem()
	{
		List<String> lore = new ArrayList<>();
		lore.add("§7Achète un §egrade §7et débloque");
		lore.add("§7des avantages §dexclusifs sur");
		lore.add("§7l'ensemble §edes jeux et serveurs §7!");
		lore.add("");
		lore.add(utils.getAccess());
		
		ItemStack it = utils.createItem(Material.NAME_TAG, "§6Grades", (byte) 0, lore);
		return it;
	}
	
	@Override
	public int getSize()
	{
		return 54;
	}

	@Override
	public String name()
	{
		return "§6Boutique";
	}

	@Override
	public void onClick(Player player, Inventory inv, ItemStack it, int slot)
	{
		
		switch(it.getType())
		{
			case BARRIER:
				player.closeInventory();
				break;
			case SKULL_ITEM:
				player.closeInventory();
				utils.notImplemented(player);
				break;
			case NAME_TAG:
				LeemonHub.getInstance().leeCore.getGuiManager().open(player, ShopRanksMenu.class);
				break;
			case EXP_BOTTLE:
				LeemonHub.getInstance().leeCore.getGuiManager().open(player, ShopBoostersMenu.class);
				break;
			default:
				break;
		}
	}

	@Override
	public void onRightClick(Player arg0, Inventory arg1, ItemStack arg2, int arg3) {
		// TODO Auto-generated method stub
		
	}

}
