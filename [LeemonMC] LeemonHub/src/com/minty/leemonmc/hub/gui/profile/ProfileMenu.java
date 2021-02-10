package com.minty.leemonmc.hub.gui.profile;

import com.minty.leemonmc.core.util.GuiBuilder;
import com.minty.leemonmc.core.util.GuiUtils;
import com.minty.leemonmc.hub.LeemonHub;
import com.minty.leemonmc.hub.gui.social.GroupMenu;
import com.minty.leemonmc.hub.settings.gui.PlayerSettingsMenu;
import com.minty.leemonmc.hub.settings.gui.VIPSettingsMenu;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ProfileMenu implements GuiBuilder {

	private GuiUtils utils;
	
	public ProfileMenu(GuiUtils utils) {
		this.utils = utils;
	}
	
	@Override
	public void contents(Player player, Inventory inv) {
		/* 
		 * LORES
		 * */
		
		List<String> statsLore = new ArrayList<>();
		statsLore.add("§7L'ensemble de vos §estatistiques");
		statsLore.add("§7sur les différents §emodes de jeux §7!");
		statsLore.add("");
		statsLore.add(utils.getAccess());
		
		
		/* 
		 * DECORATION
		 * */
		
		utils.fillWithItem(utils.pane(), 0, 3, inv);
		utils.fillWithItem(utils.pane(), 6, 9, inv);
		
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
		
		/* 
		 * CONTENT
		 * */
		

		inv.setItem(4, utils.getProfileItem(player.getUniqueId().toString())); // Haut milieu
		
		inv.setItem(21, getFriendsItem());
		inv.setItem(22, getPartyItem());
		inv.setItem(23, getSoonItem());
		
		inv.setItem(49, utils.cancelItem());
		
		inv.setItem(30, utils.getVipSettingsItem());
		ItemStack it = utils.createItem(Material.IRON_SWORD, "§6Statistiques", (byte) 0, statsLore);
		ItemMeta meta = it.getItemMeta();
		meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		it.setItemMeta(meta);
		inv.setItem(31, it);
		inv.setItem(32, utils.getSettingsItem());
	}

	@Override
	public int getSize() {
		return 54;
	}

	@Override
	public String name() {
		return "§6Profil";
	}

	private ItemStack getSoonItem() {
		ItemStack it = LeemonHub.getInstance().getApi().getLeemonUtils().getSkull("http://textures.minecraft.net/texture/83e279656bd74b681597fb443d19d4a220f350d66ba04f826dcc89ae125cd8");
		ItemMeta meta = it.getItemMeta();
		meta.setDisplayName("§7Prochainement...");
		
		List<String> lore = new ArrayList<>();
		lore.add("§7Hmmm... Que se cache t-il derrière cet objet?");
		lore.add("");
		lore.add(LeemonHub.getInstance().getApi().getGuiUtils().getNotImplementedLore());
		
		meta.setLore(lore);
		it.setItemMeta(meta);
		return it;
	}
	
	private ItemStack getPartyItem() {
		ItemStack it = LeemonHub.getInstance().getApi().getLeemonUtils().getSkull("http://textures.minecraft.net/texture/a7ca8b9c7169a743f82e8e9fd68f11c0f31070a53c92863884e673137fbaf1");
		ItemMeta meta = it.getItemMeta();
		meta.setDisplayName("§6Groupe");
		
		List<String> lore = new ArrayList<>();
		lore.add("§7Accéder au menu de §agroupe§7.");
		lore.add("");
		lore.add(LeemonHub.getInstance().getApi().getGuiUtils().getAccess());
		
		meta.setLore(lore);
		it.setItemMeta(meta);
		return it;
	}
	
	private ItemStack getFriendsItem() {
		ItemStack it = LeemonHub.getInstance().getApi().getLeemonUtils().getSkull("http://textures.minecraft.net/texture/17ef28de8e2accdcb23b0942f5624f4149a5aa490ec19f7dc4991a850b65f40");
		ItemMeta meta = it.getItemMeta();
		meta.setDisplayName("§6Amis");
		
		List<String> lore = new ArrayList<>();
		lore.add("§7Accéder au menu des §eamis§7.");
		lore.add("");
		lore.add(LeemonHub.getInstance().getApi().getGuiUtils().getAccess());
		
		meta.setLore(lore);
		it.setItemMeta(meta);
		return it;
	}
	
	@Override
	public void onClick(Player player, Inventory inv, ItemStack it, int slot)
	{
		switch(it.getType()) {
			case BARRIER:
				player.closeInventory();
				break;
			case DIODE:
				LeemonHub.getInstance().getApi().getGuiManager().open(player, PlayerSettingsMenu.class);
				break;
			case BLAZE_POWDER:
				LeemonHub.getInstance().getApi().getGuiManager().open(player, VIPSettingsMenu.class);
				break;
			case IRON_SWORD:
				LeemonHub.getInstance().getApi().getGuiManager().open(player, ProfileStatsMenu.class);
				break;
			default:
				break;
		}
		
		if(it.hasItemMeta() && it.getItemMeta().getDisplayName().equalsIgnoreCase(getPartyItem().getItemMeta().getDisplayName()))
		{
			LeemonHub.getInstance().getApi().getGuiManager().open(player, GroupMenu.class);
		}
		
		if(it.hasItemMeta() && it.getItemMeta().getDisplayName().equalsIgnoreCase(getFriendsItem().getItemMeta().getDisplayName()))
		{
			// Menu des amis
		}
	}

	@Override
	public void onRightClick(Player arg0, Inventory arg1, ItemStack arg2, int arg3) {
		// TODO Auto-generated method stub
		
	}

}
