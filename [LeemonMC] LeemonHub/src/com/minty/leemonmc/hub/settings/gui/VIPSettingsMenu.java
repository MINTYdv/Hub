package com.minty.leemonmc.hub.settings.gui;

import com.minty.leemonmc.basics.core.Rank;
import com.minty.leemonmc.basics.core.cache.Account;
import com.minty.leemonmc.core.util.GuiBuilder;
import com.minty.leemonmc.core.util.GuiUtils;
import com.minty.leemonmc.hub.LeemonHub;
import com.minty.leemonmc.hub.gui.play.PlayMenu;
import com.minty.leemonmc.hub.settings.vip.HubJoinEffectSetting;
import com.minty.leemonmc.hub.settings.vip.HubMovementSetting;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class VIPSettingsMenu implements GuiBuilder {

	private GuiUtils utils;
	private LeemonHub main;
	
	public VIPSettingsMenu(GuiUtils utils, LeemonHub _main) {
		this.utils = utils;
		this.main = _main;
	}
	
	@Override
	public void contents(Player player, Inventory inv) {
		
		/* 
		 * REAL CONTENT (SETTINGS)
		 * */

		inv.setItem(22, main.getSettingsHandler().getSetting(new HubMovementSetting(main, main.getSettingsHandler()), player));
		inv.setItem(21, main.getSettingsHandler().getSetting(new HubJoinEffectSetting(main, main.getSettingsHandler()), player));
		
		
		List<String> lore = new ArrayList<>();
		lore.add("§7Paramètre le §emessage§7 affiché §eglobalement");
		lore.add("§7lorsque tu te connectes à un §elobby §7!");
		lore.add("");
		lore.add("§4⚠ §cTout abus sera sanctionné §4⚠");
		lore.add("");
		lore.add("§6» §eClic-Gauche : §7Modifier le message de connexion");
		lore.add("§6» §eClic-Droit : §7Réintialiser le message de connexion");
		ItemStack it = main.getApi().getGuiUtils().createItem(Material.SIGN, "§6Message de connexion au lobby", (byte) 0, lore);
		
		inv.setItem(23, it);
		
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
		

		inv.setItem(49, utils.cancelItem());

		inv.setItem(48, utils.backItem());
		
		inv.setItem(4, utils.getProfileItem(player.getUniqueId().toString()));
	
	}

	@Override
	public int getSize() {
		return 54;
	}

	@Override
	public String name() {
		return "§6Profil » Options VIP";
	}

	@Override
	public void onClick(Player player, Inventory inv, ItemStack it, int slot)
	{
		String UUID = player.getUniqueId().toString();
		Account account = main.getApi().getAccountManager().getAccount(UUID);
		
		switch(it.getType()) {
			case BARRIER:
				player.closeInventory();
				break;
			case ARROW:
				player.closeInventory();
				LeemonHub.getInstance().leeCore.getGuiManager().open(player, PlayMenu.class);
				break;
			default:
				break;
		}
		
		if(slot == 23)
		{
			if(account.getRank().getPower() < Rank.CUSTOM.getPower())
			{
				player.playSound(player.getLocation(), Sound.ENTITY_BLAZE_DEATH, 1f, 1f);
				
				player.sendMessage("§c§m---------------------------------------");
				player.sendMessage("");
				player.sendMessage("§f§l➜  §eTu n'as pas le grade nécessaire pour");
				player.sendMessage("      §emodifier cette option !");
				player.sendMessage("");
				player.sendMessage("§f§l➜  §eUn meilleur grade ?");
				player.sendMessage("      §6https://store.leemonmc.fr");
				player.sendMessage("");
				player.sendMessage("§c§m---------------------------------------");
				return;
			}
			
			player.sendMessage("§c§m---------------------------------------");
			player.sendMessage("");
			player.sendMessage("§f§l➜  §ePour modifier cette option, entre la commande");
			player.sendMessage("      §6§l/joinmsg <message>");
			player.sendMessage("");
			player.sendMessage("§c§m---------------------------------------");
			return;
			
		}
		
	}

	@Override
	public void onRightClick(Player player, Inventory inv, ItemStack it, int slot)
	{

		String UUID = player.getUniqueId().toString();
		Account account = main.getApi().getAccountManager().getAccount(UUID);
		
		if(slot == 23)
		{
			if(account.getRank().getPower() < Rank.CUSTOM.getPower())
			{
				player.playSound(player.getLocation(), Sound.ENTITY_BLAZE_DEATH, 1f, 1f);
				
				player.sendMessage("§c§m---------------------------------------");
				player.sendMessage("");
				player.sendMessage("§f§l➜  §eTu n'as pas le grade nécessaire pour");
				player.sendMessage("      §emodifier cette option !");
				player.sendMessage("");
				player.sendMessage("§f§l➜  §eUn meilleur grade ?");
				player.sendMessage("      §6https://store.leemonmc.fr");
				player.sendMessage("");
				player.sendMessage("§c§m---------------------------------------");
				return;
			}
			
			player.sendMessage("§c§m---------------------------------------");
			player.sendMessage("");
			player.sendMessage("§f§l➜  §eCette option a bien été réinitialisée");
			player.sendMessage("      §eà sa valeur par défaut !");
			player.sendMessage("");
			player.sendMessage("§c§m---------------------------------------");
			
			account.setSetting("join_message", "§6§oa rejoint le hub !");
			return;
			
		}
		
	}

}
