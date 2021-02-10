package com.minty.leemonmc.hub.gui.profile;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.minty.leemonmc.core.stats.LeaderboardHandler;
import com.minty.leemonmc.core.util.GuiBuilder;
import com.minty.leemonmc.core.util.GuiUtils;
import com.minty.leemonmc.hub.LeemonHub;

public class ProfileStatsMenu implements GuiBuilder {

	private GuiUtils utils;
	private LeemonHub main;
	
	public ProfileStatsMenu(GuiUtils utils, LeemonHub _main) {
		this.utils = utils;
		this.main = _main;
	}
	
	@Override
	public void contents(Player player, Inventory inv) {
		
		/* 
		 * REAL CONTENT (STATS)
		 * */

		String UUID = player.getUniqueId().toString();
		
		List<String> ffaskyLore = new ArrayList<>();
		ffaskyLore.add("");
		ffaskyLore.add("§8- §7Position dans le classement : §6#1");
		ffaskyLore.add("");
		ffaskyLore.add("§8- §7Niveau : §e1 §7(0/50)");
		ffaskyLore.add("");
		ffaskyLore.add("§8- §7Kills : §e" + main.getApi().getStatsHandler().getStat(UUID, "ffaskywars", "kills") + " §6(#" + LeaderboardHandler.getLeaderboardPosition(UUID, "ffaskywars", "kills") + ")");
		ffaskyLore.add("§8- §7Morts : §e" + main.getApi().getStatsHandler().getStat(UUID, "ffaskywars", "deaths") + " §6(#" + LeaderboardHandler.getLeaderboardPosition(UUID, "ffaskywars", "deaths") + ")");
		ffaskyLore.add("§8- §7Best Killstreak : §e" + main.getApi().getStatsHandler().getStat(UUID, "ffaskywars", "ksrecord") + " §6(#" + LeaderboardHandler.getLeaderboardPosition(UUID, "ffaskywars", "ksrecord") + ")");
		inv.setItem(21, utils.createItem(Material.DIAMOND_AXE, "§6FFASkywars", (byte) 0, ffaskyLore));
		
		List<String> skyrushLore = new ArrayList<>();
		skyrushLore.add("");
		skyrushLore.add("§8- §7Position dans le classement : §6#1");
		skyrushLore.add("");
		skyrushLore.add("§8- §7Niveau : §e1 §7(0/50)");
		skyrushLore.add("");
		skyrushLore.add("§8- §7Temps de jeu : §e" + formatPlayTime(main.getApi().getStatsHandler().getStat(UUID, "skyrush", "playtime")) + " §6(#" + LeaderboardHandler.getLeaderboardPosition(UUID, "skyrush", "playtime") + ")");
		skyrushLore.add("§8- §7Parties jouées : §e" + main.getApi().getStatsHandler().getStat(UUID, "skyrush", "games") + " §6(#" + LeaderboardHandler.getLeaderboardPosition(UUID, "skyrush", "games") + ")");
		skyrushLore.add("§8- §7Points marqués : §e" + main.getApi().getStatsHandler().getStat(UUID, "skyrush", "points") + " §6(#" + LeaderboardHandler.getLeaderboardPosition(UUID, "skyrush", "games") + ")");
		skyrushLore.add("");
		skyrushLore.add("§8- §7Victoires : §e" + main.getApi().getStatsHandler().getStat(UUID, "skyrush", "victories") + " §6(#" + LeaderboardHandler.getLeaderboardPosition(UUID, "skyrush", "victories") + ")");
		skyrushLore.add("§8- §7Défaites : §e" + main.getApi().getStatsHandler().getStat(UUID, "skyrush", "defeats") + " §6(#" + LeaderboardHandler.getLeaderboardPosition(UUID, "skyrush", "defeats") + ")");
		skyrushLore.add("§8- §7Kills : §e" + main.getApi().getStatsHandler().getStat(UUID, "skyrush", "kills") + " §6(#" + LeaderboardHandler.getLeaderboardPosition(UUID, "skyrush", "kills") + ")");
		skyrushLore.add("§8- §7Morts : §e" + main.getApi().getStatsHandler().getStat(UUID, "skyrush", "deaths") + " §6(#" + LeaderboardHandler.getLeaderboardPosition(UUID, "skyrush", "deaths") + ")");
		inv.setItem(22, utils.createItem(Material.ENDER_PEARL, "§6SkyRush", (byte) 0, skyrushLore));
		
		List<String> undergroundLore = new ArrayList<>();
		undergroundLore.add("");
		undergroundLore.add("§8- §7Position dans le classement : §6#1");
		undergroundLore.add("");
		undergroundLore.add("§8- §7Niveau : §e1 §7(0/50)");
		undergroundLore.add("");
		undergroundLore.add("§8- §7Temps de jeu : §e" + formatPlayTime(main.getApi().getStatsHandler().getStat(UUID, "underground", "playtime")) + " §6(#" + LeaderboardHandler.getLeaderboardPosition(UUID, "underground", "playtime") + ")");
		undergroundLore.add("§8- §7Parties jouées : §e" + main.getApi().getStatsHandler().getStat(UUID, "underground", "games") + " §6(#" + LeaderboardHandler.getLeaderboardPosition(UUID, "underground", "games") + ")");
		undergroundLore.add("§8- §7Points marqués : §e" + main.getApi().getStatsHandler().getStat(UUID, "underground", "points") + " §6(#" + LeaderboardHandler.getLeaderboardPosition(UUID, "underground", "points") + ")");
		undergroundLore.add("");
		undergroundLore.add("§8- §7Victoires : §e" + main.getApi().getStatsHandler().getStat(UUID, "underground", "victories") + " §6(#" + LeaderboardHandler.getLeaderboardPosition(UUID, "underground", "victories") + ")");
		undergroundLore.add("§8- §7Défaites : §e" + main.getApi().getStatsHandler().getStat(UUID, "underground", "defeats") + " §6(#" + LeaderboardHandler.getLeaderboardPosition(UUID, "underground", "defeats") + ")");
		undergroundLore.add("§8- §7Kills : §e" + main.getApi().getStatsHandler().getStat(UUID, "underground", "kills") + " §6(#" + LeaderboardHandler.getLeaderboardPosition(UUID, "underground", "kills") + ")");
		undergroundLore.add("§8- §7Morts : §e" + main.getApi().getStatsHandler().getStat(UUID, "underground", "deaths") + " §6(#" + LeaderboardHandler.getLeaderboardPosition(UUID, "underground", "deaths") + ")");
		inv.setItem(23, utils.createItem(Material.COBBLESTONE, "§6Underground", (byte) 0, undergroundLore));
		
		List<String> gemsLore = new ArrayList<>();
		gemsLore.add("");
		gemsLore.add("§8- §7Position dans le classement : §6#1");
		gemsLore.add("");
		gemsLore.add("§8- §7Niveau : §e1 §7(0/50)");
		gemsLore.add("");
		gemsLore.add("§8- §7Victoires : §e" + main.getApi().getStatsHandler().getStat(UUID, "gemsdefender", "victories") + " §6(#" + LeaderboardHandler.getLeaderboardPosition(UUID, "gemsdefender", "victories") + ")");
		gemsLore.add("§8- §7Défaites : §e" + main.getApi().getStatsHandler().getStat(UUID, "gemsdefender", "defeats") + " §6(#" + LeaderboardHandler.getLeaderboardPosition(UUID, "gemsdefender", "defeats") + ")");
		gemsLore.add("§8- §7Kills : §e" + main.getApi().getStatsHandler().getStat(UUID, "gemsdefender", "kills") + " §6(#" + LeaderboardHandler.getLeaderboardPosition(UUID, "gemsdefender", "kills") + ")");
		gemsLore.add("§8- §7Morts : §e" + main.getApi().getStatsHandler().getStat(UUID, "gemsdefender", "deaths") + " §6(#" + LeaderboardHandler.getLeaderboardPosition(UUID, "gemsdefender", "deaths") + ")");
		inv.setItem(31, utils.createItem(Material.EMERALD_ORE, "§6GemsDefender", (byte) 0, gemsLore));
		
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

	public static String formatPlayTime(int playtime)
	{
	       int days = (int) TimeUnit.MINUTES.toDays(playtime);
	       int hours = (int) (TimeUnit.MINUTES.toHours(playtime) - TimeUnit.DAYS.toHours(days));
	       int minutes = (int) (TimeUnit.MINUTES.toMinutes(playtime) - TimeUnit.HOURS.toMinutes(hours) - TimeUnit.DAYS.toMinutes(days));
	       int seconds = (int) (TimeUnit.MINUTES.toSeconds(playtime) - TimeUnit.MINUTES.toSeconds(minutes) - TimeUnit.HOURS.toSeconds(hours) - TimeUnit.DAYS.toSeconds(days));

	       if (days != 0) {
	           return days + "d, " + hours + "h, " + minutes + "m, " + seconds + "s";
	       } else {
	           if (hours != 0) {
	               return hours + "h, " + minutes + "m, " + seconds + "s";
	           } else {
	               if (minutes != 0) {
	                   return minutes + "m, " + seconds + "s";
	               } else {
	                   return seconds + "s";
	               }
	           }
	       }
	}
	
	@Override
	public int getSize() {
		return 54;
	}

	@Override
	public String name() {
		return "§6Profil » Statistiques";
	}

	@Override
	public void onClick(Player player, Inventory inv, ItemStack it, int slot)
	{
		switch(it.getType()) {
			case BARRIER:
				player.closeInventory();
				break;
			case ARROW:
				player.closeInventory();
				LeemonHub.getInstance().getApi().getGuiManager().open(player, ProfileMenu.class);
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
