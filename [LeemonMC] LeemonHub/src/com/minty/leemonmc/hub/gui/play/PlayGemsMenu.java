package com.minty.leemonmc.hub.gui.play;

import com.minty.leemonmc.basics.core.ServerGroup;
import com.minty.leemonmc.core.CoreMain;
import com.minty.leemonmc.core.util.GuiBuilder;
import com.minty.leemonmc.core.util.GuiUtils;
import com.minty.leemonmc.hub.LeemonHub;
import com.minty.leemonmc.hub.handlers.MinigamesIconsHandler;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlayGemsMenu implements GuiBuilder
{

	private GuiUtils utils;
	private Map<Integer, ServerGroup> serversGroups = new HashMap<>();
	
	public PlayGemsMenu(GuiUtils utils) {
		this.utils = utils;
	}
	
	@Override
	public void contents(Player player, Inventory inv) {
		
		/* 
		 * MENU CONTENT (GAMEMODES)
		 * */
		
		List<String> twolore = new ArrayList<>();
		twolore.add("");
		twolore.add("§8- §7Serveurs disponibles : §e" + CoreMain.getInstance().getServerManager().getServersOfGroup(ServerGroup.gems2x4).size());
		twolore.add("§8- §7Joueurs en jeu : §a" + CoreMain.getInstance().getServerManager().getPlayingPlayers(ServerGroup.gems2x4));
		twolore.add("");
		twolore.add("§6» §eClic-Gauche : §7Rejoindre le jeu");
		twolore.add("§6» §eClic-Droit : §7Afficher les serveurs");
		
		ItemStack it3 = utils.createItem(Material.MAP, "§6GemsDefender §8- §e§l4vs4", (byte) 0, twolore);
		it3.setAmount(4);
		inv.setItem(22, it3);
		serversGroups.put(22, ServerGroup.gems2x4);
		
		/* 
		 * MENU CONTENT (OTHER THAN GAMES)
		 * */
		
		utils.fillWithItem(pane(), 0, 3, inv);
		utils.fillWithItem(pane(), 6, 9, inv);
		
		inv.setItem(9, pane());
		inv.setItem(9 * 2, pane());
		inv.setItem(9 * 3, pane());
		inv.setItem(9 * 4, pane());
		inv.setItem(9 * 5, pane());
		inv.setItem(51, pane());
		inv.setItem(52, pane());
		inv.setItem(53, pane());
		
		inv.setItem(46, pane());
		inv.setItem(47, pane());
		
		inv.setItem(48, utils.backItem());
		inv.setItem(49, utils.cancelItem());
		
		List<String> hostLore = new ArrayList<>();
		hostLore.add("§7Lance ton §ehost §7et accède à ton");
		hostLore.add("§eserveur privé §7personnel à la demande !");
		hostLore.add("");
		hostLore.add("§4» §c§oCette option n'est pas encore disponible !...");
		
		inv.setItem(50, utils.createItem(Material.COMMAND_MINECART, "§6Hosts", (byte) 0, hostLore)); // Host item
		
		inv.setItem(8 * 2 + 1, pane());
		
		inv.setItem(8 * 3 + 2, pane());
		inv.setItem(8 * 4 + 3, pane());
		
		inv.setItem(8 * 5 + 4, pane());
		inv.setItem(4, MinigamesIconsHandler.getGemsDefenderIcon(false));
		//test
	}

	@Override
	public int getSize() {
		return 54;
	}

	@Override
	public String name() {
		return "§6Jouer » GemsDefender";
	}

	@Override
	public void onClick(Player player, Inventory inv, ItemStack it, int slot) {
		
		switch(it.getType())
		{
			case BARRIER:
				if(it.getItemMeta().getDisplayName() == "§cFermer") {
					player.closeInventory();
				}

				break;
			case ARROW:
				LeemonHub.getInstance().getApi().getGuiManager().open(player, PlayMenu.class);
				break;
			case COMMAND_MINECART:
				LeemonHub.getInstance().getApi().getGuiUtils().notImplemented(player);
				break;
			default:
				break;
		}
		
		if(serversGroups.containsKey(slot))
		{
			player.closeInventory();
			CoreMain.getInstance().getQueueManager().queue(player, serversGroups.get(slot));
		}
		
	}
	
	private ItemStack pane() {
		return LeemonHub.getInstance().getApi().getGuiUtils().pane();
	}

	public void openServersMenu(Player player, ServerGroup group)
	{
		PlayServersMenu gui = new PlayServersMenu(this, LeemonHub.getInstance(), group);
		LeemonHub.getInstance().getApi().getGuiManager().addMenu(gui);
		LeemonHub.getInstance().getApi().getGuiManager().open(player, gui.getClass());
	}
	
	@Override
	public void onRightClick(Player player, Inventory arg1, ItemStack inv, int slot) {
		if(serversGroups.containsKey(slot))
		{
			openServersMenu(player, serversGroups.get(slot));
		}
		
	}
	
}
