package com.minty.leemonmc.hub.gui.play;

import com.minty.leemonmc.basics.core.*;
import com.minty.leemonmc.basics.core.cache.Account;
import com.minty.leemonmc.core.util.GuiBuilder;
import com.minty.leemonmc.core.util.GuiUtils;
import com.minty.leemonmc.hub.LeemonHub;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlayServersMenu implements GuiBuilder {

	private ServerGroup group;
	private LeemonHub main;
	private GuiUtils utils;
	private Map<Integer, Server> serversSlots = new HashMap<Integer, Server>();
	private GuiBuilder lastMenu;
	
	public PlayServersMenu(GuiBuilder _lastMenu, LeemonHub _main, ServerGroup _group)
	{
		lastMenu = _lastMenu;
		main = _main;
		group = _group;
		utils = main.getApi().getGuiUtils();
	}
	
	@Override
	public void contents(Player player, Inventory inv)
	{
		utils.fillWithItem(utils.pane(), 0, 3, inv);
		utils.fillWithItem(utils.pane(), 6, 9, inv);
		
		inv.setItem(9, utils.pane());
		inv.setItem(9 * 2, utils.pane());
		inv.setItem(9 * 3, utils.pane());
		inv.setItem(9 * 4, utils.pane());
		inv.setItem(9 * 5, utils.pane());
		inv.setItem(51, utils.pane());
		inv.setItem(52, utils.pane());
		inv.setItem(53, utils.pane());
		
		inv.setItem(46, utils.pane());
		inv.setItem(47, utils.pane());
		
		inv.setItem(8 * 2 + 1, utils.pane());
		
		inv.setItem(8 * 3 + 2, utils.pane());
		inv.setItem(8 * 4 + 3, utils.pane());
		
		inv.setItem(8 * 5 + 4, utils.pane());
		
		if(lastMenu != null) {
			inv.setItem(48, utils.backItem());
		}
		inv.setItem(49, utils.cancelItem());
		
		List<Integer> slots = new ArrayList<>();
		slots.add(20); slots.add(21); slots.add(22); slots.add(23); slots.add(24);
		
		List<Server> servers = main.getApi().getServerManager().getServersOfGroup(group);
		for(int i = 0; i < servers.size(); i++)
		{
			Server server = servers.get(i);
			if(server.getPlayingPlayers() + server.getMaxPlayers() <= 0) {
				servers.remove(server);
				continue;
			}
			
			inv.setItem(slots.get(i), getServerItem(server));
			
			if(!getServersSlots().containsKey(slots.get(i)))
			{
				getServersSlots().put(slots.get(i), server);
			}
		}
		
		if(servers.size() == 0 || servers == null)
		{
			inv.setItem(22, getNoServersItem());
		}
		//test
	}

	private ItemStack getNoServersItem()
	{
		List<String> lore = new ArrayList<>();
		lore.add("§7Il n'y a §caucun serveur §7disponible actuellement");
		lore.add("§7pour ce §cmode de jeu§7.");
		lore.add("");
		lore.add("§7Si vous pensez qu'il s'agit d'une erreur, contactez");
		lore.add("§7un §cAdministrateur §7!");
		ItemStack it = main.getApi().getGuiUtils().createItem(Material.BARRIER, "§cAucun serveurs disponibles", (byte) 0, lore);	
		return it;
	}

	private String getPresence(Server server)
	{
		if(server.getPlayingPlayers() < 20)
		{
			return "§aFaible";
		} else if(server.getPlayingPlayers() < 50)
		{
			return "§eMoyenne";
		} else {
			return "§cÉlevée";
		}
	}
	
	private String getState(Server server) {
		if(!server.isFull()) {
			return "§aOuvert";
		} else {
			if(server.getServerType() == ServerType.LOBBY)
			{
				return "§cFermé §7(Complet)";
			} else
			{
				return "§cFermé §7(§7Spectateurs §eVIP§7)";
			}
		}
	}
	
	private String getGameState(Server server)
	{
		if(server.getGameState() == GameState.WAITING)
		{
			return "§6En attente...";
		}
		if(server.getGameState() == GameState.PLAYING)
		{
			return "§cEn jeu";
		}
		if(server.getGameState() == GameState.FINISH)
		{
			return "§2Redémarrage...";
		}
		return "";
	}
	
	private ItemStack getServerItem(Server server)
	{
		ItemStack it = new ItemStack(Material.STONE, server.getPlayingPlayers(), (byte) 0);
		List<String> lore = new ArrayList<>();
		lore.add("");
		
		lore.add("§8- §7État : " + getState(server));
		if(server.getServerGroup() == ServerGroup.lobby)
		{
			lore.add("§8- §7Présence : " + getPresence(server));
		}
		lore.add("§8- §7Joueurs : §e" + server.getPlayingPlayers() + "/" + server.getMaxPlayers());
		
		if(server.getServerType() == ServerType.MINIGAME)
		{
			lore.add("§8- §7Statut : " + getGameState(server));
		}
		
		lore.add("");
		if(!Bukkit.getServerName().equalsIgnoreCase(server.getName()))
		{
			it = main.getApi().getLeemonUtils().getSkull("http://textures.minecraft.net/texture/27712ca655128701ea3e5f28ddd69e6a8e63adf28052c51b2fd5adb538e1");
			it.setAmount(server.getPlayingPlayers());
			lore.add("§6» §eCliquez pour rejoindre le serveur");
		} else
		{
			it = main.getApi().getLeemonUtils().getSkull("http://textures.minecraft.net/texture/76bcb59ad4b26113b1a200a903a531adc32522c1be1757a566db8c8b");
			it.setAmount(server.getPlayingPlayers());
			lore.add("§4» §cVous êtes déjà connecté sur ce serveur !");
		}
		ItemMeta meta = it.getItemMeta();
		
		
		if(group == ServerGroup.lobby)
		{
			meta.setDisplayName("§6Lobby #" + server.getID());
		} else
		{
			meta.setDisplayName("§6" + server.getName());
		}
		
		meta.setLore(lore);
		it.setItemMeta(meta);
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
		if(group == ServerGroup.lobby) {
			return "§6Sélection de Lobby";
		} else
		{
			return "§6" + group.toString() + " » Serveurs";
		}

	}

	@Override
	public void onClick(Player player, Inventory inv, ItemStack it, int slot)
	{
		if(it.getType() == Material.BARRIER && it.getItemMeta().getDisplayName().equalsIgnoreCase(main.getApi().getGuiUtils().cancelItem().getItemMeta().getDisplayName()))
		{
			player.closeInventory();
			return;
		}
		
		if(it.getType() == Material.ARROW)
		{
			main.getApi().getGuiManager().open(player, lastMenu.getClass());
			return;
		}
		
		if(getServersSlots().containsKey(slot))
		{
			Server target = getServersSlots().get(slot);
			if(target.getName() != Bukkit.getServerName())
			{
				connect(player, target);
			}
		}
		
	}
	
	private void connect(Player player, Server server)
	{
		// Send target player to a server
		
		Account account = main.getApi().getAccountManager().getAccount(player.getUniqueId().toString());
		if(server.getServerType() != ServerType.LOBBY && account.getRank().getPower() < Rank.VIP.getPower())
		{
			player.sendMessage("§c§m---------------------------------------");
			player.sendMessage("");
			player.sendMessage("§f§l➜  §eTu n'as pas le grade nécessaire pour");
			player.sendMessage("      §erejoindre les serveurs directement !");
			player.sendMessage("");
			player.sendMessage("§c§m---------------------------------------");
			return;
		}
		
		main.getApi().getQueueManager().connect(player, server);

	}
	
	public Map<Integer, Server> getServersSlots() {
		return serversSlots;
	}

	@Override
	public void onRightClick(Player arg0, Inventory arg1, ItemStack arg2, int arg3) {
		// TODO Auto-generated method stub
		
	}

	
	
}
