package com.minty.leemonmc.hub.gui.play;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.minty.leemonmc.basics.core.Rank;
import com.minty.leemonmc.basics.core.ServerGroup;
import com.minty.leemonmc.basics.core.cache.Account;
import com.minty.leemonmc.core.CoreMain;
import com.minty.leemonmc.core.util.GuiBuilder;
import com.minty.leemonmc.core.util.GuiUtils;
import com.minty.leemonmc.hub.LeemonHub;
import com.minty.leemonmc.hub.gui.profile.ProfileMenu;
import com.minty.leemonmc.hub.handlers.MinigamesIconsHandler;
import com.minty.leemonmc.hub.settings.gui.VIPSettingsMenu;

public class PlayMenu implements GuiBuilder
{

	private GuiUtils utils;
	
	public PlayMenu(GuiUtils utils) {
		this.utils = utils;
	}
	
	@Override
	public void contents(Player player, Inventory inv) {
		
		/* 
		 * MENU CONTENT (GAMES)
		 * */
		
		inv.setItem(21, MinigamesIconsHandler.getGemsDefenderIcon(true));
		inv.setItem(22, MinigamesIconsHandler.getSkyRushIcon(true));
		inv.setItem(23, MinigamesIconsHandler.getFFASkywarsIcon(true));
		inv.setItem(31, MinigamesIconsHandler.getUndergroundIcon(true));
		
		/* 
		 * MENU CONTENT (OTHER THAN GAMES)
		 * */
		
		utils.fillWithItem(pane(), 0, 9, inv);
		inv.setItem(0, pane());
		inv.setItem(1, pane());
		inv.setItem(2, pane());
		inv.setItem(3, pane());
		Account account = CoreMain.getInstance().getAccountManager().getAccount(player.getUniqueId().toString());
		if(account.getRank().getPower() >= Rank.BUILDER.getPower())
		{
			List<String> lore = new ArrayList<>();
			lore.add("§fServeur réservé aux membres du staff, ici sont");
			lore.add("§feffectués les tets, constructions et prototypes de");
			lore.add("§fmini-jeux !");
			lore.add("");
			lore.add("§6» §eVisibilité : §8Staff uniquement");
			lore.add("");
			lore.add("§6» §eClic-Gauche : §7Rejoindre le serveur");
			inv.setItem(4, utils.createItem(Material.GRASS, "§6§lServeur Prototype", (byte) 0, lore));
			//test
		} else {
			inv.setItem(4, pane());
		}
		inv.setItem(5, pane());
		inv.setItem(6, pane());
		inv.setItem(7, pane());
		inv.setItem(8, pane());
		
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
		
		List<String> lobbyLore = new ArrayList<>();
		lobbyLore.add("§7Le §6sélecteur de lobby §7vous permet");
		lobbyLore.add("§7de changer de lobby à votre guise.");
		lobbyLore.add("");
		lobbyLore.add("§6» §eCliquez ici pour sélectionner un lobby");
		
		ItemStack lobby = utils.createItem(Material.ENDER_PORTAL_FRAME, "§6§lSélecteur de Lobby", (byte) 4, lobbyLore);
		lobby.setAmount(CoreMain.getInstance().getServerManager().getPlayingPlayers(ServerGroup.lobby));
		inv.setItem(48, lobby);
		inv.setItem(49, utils.cancelItem());
		
		List<String> hostLore = new ArrayList<>();
		hostLore.add("§7Lance ton §ehost §7et accède à ton");
		hostLore.add("§eserveur privé §7personnel à la demande !");
		hostLore.add("");
		hostLore.add(utils.getNotImplementedLore());
		
		inv.setItem(50, utils.createItem(Material.COMMAND_MINECART, "§6§lHosts", (byte) 0, hostLore)); // Host item
		
		inv.setItem(8 * 2 + 1, pane());
		
		inv.setItem(8 * 3 + 2, utils.getProfileItem(player.getUniqueId().toString()));
		inv.setItem(8 * 4 + 3, utils.getVipSettingsItem());
		
		inv.setItem(8 * 5 + 4, pane());
		
	}

	@Override
	public int getSize() {
		return 54;
	}

	@Override
	public String name() {
		return "§6§lMenu Principal";
	}

	public void openServersMenu(Player player, ServerGroup group)
	{
		PlayServersMenu gui = new PlayServersMenu(this, LeemonHub.getInstance(), group);
		LeemonHub.getInstance().getApi().getGuiManager().addMenu(gui);
		LeemonHub.getInstance().getApi().getGuiManager().open(player, gui.getClass());
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
			case COMMAND_MINECART:
				LeemonHub.getInstance().getApi().getGuiUtils().notImplemented(player);
				break;
			case SKULL_ITEM:
				LeemonHub.getInstance().getApi().getGuiManager().open(player, ProfileMenu.class);
				break;
			case ENDER_PEARL:
				LeemonHub.getInstance().getApi().getGuiManager().open(player, PlaySkyRushMenu.class);
				break;
			case DIAMOND_AXE:
				LeemonHub.getInstance().getApi().getGuiManager().open(player, PlayFFASkyMenu.class);
				break;
			case EMERALD_ORE:
				LeemonHub.getInstance().getApi().getGuiManager().open(player, PlayGemsMenu.class);
				break;
			case DIAMOND_PICKAXE:
				LeemonHub.getInstance().getApi().getGuiManager().open(player, PlayUndergroundMenu.class);
				break;
			case BLAZE_POWDER:
				LeemonHub.getInstance().getApi().getGuiManager().open(player, VIPSettingsMenu.class);
				break;
			case ENDER_PORTAL_FRAME:
				PlayServersMenu gui = new PlayServersMenu(this, LeemonHub.getInstance(), ServerGroup.lobby);
				LeemonHub.getInstance().getApi().getGuiManager().addMenu(gui);
				LeemonHub.getInstance().getApi().getGuiManager().open(player, gui.getClass());
			default:
				break;
		}
		
		if(slot == 4 && CoreMain.getInstance().getAccountManager().getAccount(player.getUniqueId().toString()).getRank().getPower() >= Rank.BUILDER.getPower()) {
			ByteArrayOutputStream b = new ByteArrayOutputStream();
			DataOutputStream out = new DataOutputStream(b);
			
			try {
				out.writeUTF("Connect");
				out.writeUTF("prototype");
			} catch(IOException e) {
				e.printStackTrace();
			}
			
			player.sendPluginMessage(LeemonHub.getInstance(), "BungeeCord", b.toByteArray());
		}
		
	}
	
	private ItemStack pane() {
		return LeemonHub.getInstance().getApi().getGuiUtils().pane();
	}

	@Override
	public void onRightClick(Player arg0, Inventory arg1, ItemStack arg2, int arg3) {
		
		
	}
	
}
