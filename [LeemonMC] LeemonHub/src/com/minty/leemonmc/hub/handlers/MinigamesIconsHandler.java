package com.minty.leemonmc.hub.handlers;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import com.minty.leemonmc.basics.core.ServerGroup;
import com.minty.leemonmc.core.CoreMain;
import com.minty.leemonmc.core.util.GuiUtils;

public class MinigamesIconsHandler {

	private final static GuiUtils utils = CoreMain.getInstance().getGuiUtils();
	
	public static ItemStack getFFASkywarsIcon(boolean interactionpossible)
	{
		List<String> ffaskywars = new ArrayList<>();
		ffaskywars.add("§8§oMini-jeu PvP Free-For-All");
		ffaskywars.add("");
		ffaskywars.add("§fVotre objectif est de survivre le plus longtemps tout en");
		ffaskywars.add("§frécoltant de l'équipement sur ces îles volantes, mais");
		ffaskywars.add("§fattention à  ne pas tomber dans le vide...");
		ffaskywars.add("");
		ffaskywars.add("§6» §7Booster : §d+0%");
		ffaskywars.add("§6» §7Joueurs en jeu : §e" + CoreMain.getInstance().getServerManager().getPlayingPlayers(ServerGroup.ffaskywars));
		ffaskywars.add("");
		if(interactionpossible) ffaskywars.add("§6» §eClic-Gauche : §7Rejoindre le jeu");
		return utils.createItem(Material.DIAMOND_AXE, "§6§lFFASkywars", (byte) 0, ffaskywars);
	}
	
	public static ItemStack getSkyRushIcon(boolean interactionpossible)
	{
		List<String> skyRushLore = new ArrayList<>();
		skyRushLore.add("§8§oMini-jeu PvP en équipe");
		skyRushLore.add("");
		skyRushLore.add("§fUn mélange ultime entre parcour, combat");
		skyRushLore.add("§fet stratégie ! Utilisez vos atouts à votre avantage");
		skyRushLore.add("§fpour expulser vos ennemis et remporter la victoire !");
		skyRushLore.add("");
		skyRushLore.add("§6» §7Booster : §d+0%");
		int total = CoreMain.getInstance().getServerManager().getPlayingPlayers(ServerGroup.skyrush2x1) + CoreMain.getInstance().getServerManager().getPlayingPlayers(ServerGroup.skyrush2x2) + CoreMain.getInstance().getServerManager().getPlayingPlayers(ServerGroup.skyrush2x4);
		skyRushLore.add("§6» §7Joueurs en jeu : §e" + total);
		skyRushLore.add("");
		if(interactionpossible) skyRushLore.add("§6» §eClic-Gauche : §7Rejoindre le jeu");
		return utils.createItem(Material.ENDER_PEARL, "§6§lSkyRush", (byte) 0, skyRushLore);
	}
	
	public static ItemStack getGemsDefenderIcon(boolean interactionpossible)
	{
		List<String> gemLore = new ArrayList<>();
		gemLore.add("§8§oMini-jeu en équipe et de stratégie");
		gemLore.add("");
		gemLore.add("§fÉchangez vos richesses contre des émeraudes");
		gemLore.add("§fet soyez la première équipe à  atteindre l'objectif");
		gemLore.add("§fde richesse en pillant vos adversaires ou en");
		gemLore.add("§fprotégant votre richesse.");
		gemLore.add("");
		gemLore.add("§6» §7Booster : §d+0%");
		gemLore.add("§6» §7Joueurs en jeu : §e" +  CoreMain.getInstance().getServerManager().getPlayingPlayers(ServerGroup.gems2x4));
		gemLore.add("");
		if(interactionpossible) gemLore.add("§6» §eClic-Gauche : §7Rejoindre le jeu");
		return utils.createItem(Material.EMERALD_ORE, "§6§lGemsDefender", (byte) 4, gemLore);
	}
	
	public static ItemStack getUndergroundIcon(boolean interactionpossible)
	{
		List<String> undergroundLore = new ArrayList<>();
		undergroundLore.add("§8§oMini-jeu en équipe & PvP");
		undergroundLore.add("");
		undergroundLore.add("§fA la conquête des plus belles richesses sous-terraines,");
		undergroundLore.add("§fvotre objectif est d'améliorer votre équipement pour s'infiltrer");
		undergroundLore.add("§fdans la base de l'équipe adverse pour remporter la partie.");
		undergroundLore.add("");
		undergroundLore.add("§6» §7Booster : §d+0%");
		undergroundLore.add("§6» §7Joueurs en jeu : §e" + CoreMain.getInstance().getServerManager().getPlayingPlayers(ServerGroup.underground2x4));
		undergroundLore.add("");
		if(interactionpossible) undergroundLore.add("§6» §eClic-Gauche : §7Rejoindre le jeu");
		return utils.createItem(Material.DIAMOND_PICKAXE, "§6§lUnderground", (byte) 0, undergroundLore);
	}
	
}
