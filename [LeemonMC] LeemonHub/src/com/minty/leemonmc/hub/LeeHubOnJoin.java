package com.minty.leemonmc.hub;

import com.minty.leemonmc.basics.core.Rank;
import com.minty.leemonmc.basics.core.ServerType;
import com.minty.leemonmc.basics.core.cache.Account;
import com.minty.leemonmc.core.CoreMain;
import com.minty.leemonmc.core.events.dataLoadedEvent;
import com.minty.leemonmc.core.util.GuiUtils;
import com.minty.leemonmc.hub.settings.core.LeemonSetting;

import net.md_5.bungee.api.chat.TextComponent;
import net.minecraft.server.v1_9_R2.IChatBaseComponent;
import net.minecraft.server.v1_9_R2.IChatBaseComponent.ChatSerializer;

import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import us.myles.ViaVersion.api.Via;

public class LeeHubOnJoin implements Listener {

	private LeemonHub main;
	
	private GuiUtils utils;
	
	public LeeHubOnJoin(LeemonHub _main, GuiUtils utils) {
		main = _main;
		this.utils = utils;
	}
	
	@EventHandler
	public void OnJoin(PlayerJoinEvent e) {
		
		Player player = e.getPlayer();
		if(player == null) return;
		
		e.setJoinMessage("");
		// Teleport players to spawn point
		
		int x = main.getConfig().getInt("spawn.x");
		int y = main.getConfig().getInt("spawn.y");
		int z = main.getConfig().getInt("spawn.z");
		int pitch = main.getConfig().getInt("spawn.pitch");
		int yaw = main.getConfig().getInt("spawn.yaw");
		
		Location loc = new Location(player.getWorld(), x, y, z, yaw, pitch);
		
		player.teleport(loc);
		
		// Set player's gamemode
		player.setGameMode(GameMode.ADVENTURE);
		
		// Regenerate player's health and set max health
		player.setMaxHealth(2);
		player.setHealth(player.getMaxHealth());
		
		//Reset player's exp
		player.setExp(0);
		player.setLevel(0);
		
		// Regenerate player's hunger bar
		player.setFoodLevel(20);
		
		// Set player's hotbar
		PlayerInventory inv = player.getInventory();
		
		inv.clear();
		
		inv.setItem(0, main.getApi().getGuiUtils().createItem(Material.COMPASS, main.getConfigItemName("play"), (byte) 0));
		inv.setItem(4, main.getApi().getGuiUtils().createItem(Material.GOLD_INGOT, main.getConfigItemName("shop"), (byte) 0));
		inv.setItem(8, main.getApi().getGuiUtils().createItem(Material.STORAGE_MINECART, main.getConfigItemName("selector"), (byte) 0));
		
		@SuppressWarnings("static-access")
		ItemStack it = utils.getHead(player);
		ItemMeta meta = it.getItemMeta();
		meta.setDisplayName(main.getConfigItemName("profile"));
		it.setItemMeta(meta);
		
		inv.setItem(1, it);
		
		// Show a title on join
		main.title.sendTitle(player, 20, 20, 20, "§e✦ §6§lLeemonMC §e✦", "§8• §fPasse un bon moment ! §8•");
		
		//Set scoreboard
		
		sendHubMessage(player);
		main.getSettingsHandler().updateSettings(player);
		
	}
	
	private void sendHubMessage(Player player)
	{
		int version = Via.getAPI().getPlayerVersion(player.getUniqueId());
		
		
		String ver = "§7▌ §eVersion utilisée : §6" + protocolToString(version) + " " + getIcon(version);

		player.sendMessage("§d");
		player.sendMessage("§7▌ §eBienvenue sur §6LeemonMC §e! §7(BETA)");
		player.sendMessage("§7");
		player.sendMessage(ver);
		if(version <= 106)
		{
			player.sendMessage("§c⚠ Version recommandée : 1.9.4 ! ⚠");
		}
		player.sendMessage("§e");
	}

	private String protocolToString(int version)
	{
		String result = "";

		if(version <= 46) return null;
		
		if(version > 46 && version <= 106)
		{
			result = "1.8";
		}
		
		if(version > 106 && version < 201)
		{
			result = "1.9";
		}
		
		if(version >= 201 && version < 315)
		{
			result = "1.10";
		}
		
		if(version >= 315 && version < 335)
		{
			result = "1.11";
		}
		
		if(version >= 335 && version < 341)
		{
			result = "1.12";
		}
		
		if(version >= 341 && version < 404)
		{
			result = "1.13";
		}
		
		if(version >= 404 && version < 550)
		{
			result = "1.14";
		}
		
		if(version >= 550 && version < 701)
		{
			result = "1.15";
		}
		
		if(version >= 701 && version <= 738)
		{
			result = "1.16";
		}
		
		return result;
	}
	
	public String getIcon(int version)
	{
		if(version >= 106)
		{
			return "§a✔";
		} else {
			return "§c✖";
		}
	}
	
	@EventHandler
	public void onDataLoaded(dataLoadedEvent e)
	{
		Player player = e.getPlayer();
		String UUID = player.getUniqueId().toString();
		
		main.getApi().init(ServerType.LOBBY);

		createHubScoreboard(player);

		PlayerScoreboard.scoreGame(player);
		
		for(LeemonSetting setting : main.getSettingsHandler().getSettings())
		{
			setting.setSelectedOption(player, setting.stringToOption(CoreMain.getInstance().getAccountManager().getAccount(UUID).getSetting(setting.getNameID())));
		}
		main.getSettingsHandler().updateSettings(player);

		Account account = CoreMain.getInstance().getAccountManager().getAccount(UUID);
		if(account.getNickedRank().getPower() >= Rank.VIP_PLUS.getPower())
		{
			if(account.isModEnabled() == false)
			{
				if(account.getNickedRank().getPower() >= Rank.CUSTOM.getPower() && account.getNickedRank().getPower() <= Rank.STAFF.getPower())
				{
					if(account.getSetting("join_message").equalsIgnoreCase("NULL")) {
						account.setSetting("join_message", "&c&oa rejoint le hub !");
					}
					Bukkit.broadcastMessage(main.getApi().getPlayerDisplayNameChat(player) + ChatColor.GOLD + " " + account.getSetting("join_message").replace("&", "§"));
				} else
				{
					Bukkit.broadcastMessage(main.getApi().getPlayerDisplayNameChat(player) + ChatColor.GOLD + "§o a rejoint le hub !");
				}
			} else
			{
				for(Player players : Bukkit.getOnlinePlayers())
				{
					players.hidePlayer(player);
				}
			}
		}
		
		if(account.getSetting("join_effect").equalsIgnoreCase("STORM") && !account.isModEnabled())
		{
			player.getWorld().strikeLightning(player.getLocation());
		}
		
		if(account.getSetting("join_effect").equalsIgnoreCase("WOLF") && !account.isModEnabled())
		{
			for(Player player2 : Bukkit.getOnlinePlayers())
			{
				player2.playSound(player2.getLocation(), Sound.ENTITY_WOLF_HOWL, 1f, 1f);
			}
		}
	}
	
	public void createHubScoreboard(Player player) {
		
		for(Player online : Bukkit.getOnlinePlayers())
		{
			PlayerScoreboard.scoreGame(online);
		}
	}

}
