package com.minty.leemonmc.hub.handlers;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import com.minty.leemonmc.basics.core.ServerGroup;
import com.minty.leemonmc.hub.LeemonHub;
import com.minty.leemonmc.hub.leaderboards.LeaderboardStat;
import com.minty.leemonmc.hub.leaderboards.LeaderboardsHandler;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class HologramsHandler {

	private LeemonHub main;
	private World world;
	
	private Hologram welcome;
	private Hologram skyrush;
	private Hologram gemsDefender;
	private Hologram underground;
	private Hologram ffaskywars;
	
	private boolean yes;
	
	public HologramsHandler(LeemonHub _main)
	{
		main = _main;
		world = Bukkit.getWorld("world");
	}
	
	/* Function called at the start of the server */
	public void setup()
	{
		welcomeHologram();
		
		skyrushHologram();
		undergroundHologram();
		gemsHologram();
		ffaskywarsHologram();
		
		lbFFASkywars();
		lbUnderground();
		lbSkyrush();
		lbGemsDefender();
		
		yes = true;
		new BukkitRunnable() {
			
			@Override
			public void run()
			{
				updateHolograms();
			}
		}.runTaskTimer(main, 40, 40);
	}
	
	private void lbUnderground()
	{
		// Underground
		double x = main.getConfig().getDouble("holograms.ranking_underground.x");
		double y = main.getConfig().getDouble("holograms.ranking_underground.y");
		double z = main.getConfig().getDouble("holograms.ranking_underground.z");
		
		Location loc = new Location(Bukkit.getWorld("world"), x, y, z);
		
		List<LeaderboardStat> list = new ArrayList<>();
		list.add(new LeaderboardStat("kills", "§6Kills"));
		list.add(new LeaderboardStat("deaths", "§6Morts"));
		list.add(new LeaderboardStat("victories", "§6Victoires"));
		list.add(new LeaderboardStat("defeats", "§6Défaites"));
		list.add(new LeaderboardStat("playtime", "§6Temps de jeu"));
		LeaderboardsHandler.createLeaderboard(loc, "Classement Underground", "underground", new ItemStack(Material.DIAMOND_PICKAXE), list);
	}
	
	private void lbGemsDefender()
	{
		// GemsDefender
		double x = main.getConfig().getDouble("holograms.ranking_gemsdefender.x");
		double y = main.getConfig().getDouble("holograms.ranking_gemsdefender.y");
		double z = main.getConfig().getDouble("holograms.ranking_gemsdefender.z");
		
		Location loc = new Location(Bukkit.getWorld("world"), x, y, z);
		
		List<LeaderboardStat> list = new ArrayList<>();
		list.add(new LeaderboardStat("kills", "§6Kills"));
		list.add(new LeaderboardStat("deaths", "§6Morts"));
		list.add(new LeaderboardStat("victories", "§6Victoires"));
		list.add(new LeaderboardStat("defeats", "§6Défaites"));
		list.add(new LeaderboardStat("playtime", "§6Temps de jeu"));
		LeaderboardsHandler.createLeaderboard(loc, "Classement GemsDefender", "gemsdefender", new ItemStack(Material.EMERALD_ORE), list);
	}
	
	private void lbFFASkywars()
	{
		// FFASkywars
		double x = main.getConfig().getDouble("holograms.ranking_ffaskywars.x");
		double y = main.getConfig().getDouble("holograms.ranking_ffaskywars.y");
		double z = main.getConfig().getDouble("holograms.ranking_ffaskywars.z");
		
		Location loc = new Location(Bukkit.getWorld("world"), x, y, z);
		
		List<LeaderboardStat> list = new ArrayList<>();
		list.add(new LeaderboardStat("kills", "§6Kills"));
		list.add(new LeaderboardStat("deaths", "§6Morts"));
		list.add(new LeaderboardStat("ksrecord", "§6Record d'éliminations en série"));
		list.add(new LeaderboardStat("playtime", "§6Temps de jeu"));
		LeaderboardsHandler.createLeaderboard(loc, "Classement FFASkywars", "ffaskywars", new ItemStack(Material.GRASS), list);
	}
	
	private void lbSkyrush()
	{
		// SkyRush
		double x = main.getConfig().getDouble("holograms.ranking_skyrush.x");
		double y = main.getConfig().getDouble("holograms.ranking_skyrush.y");
		double z = main.getConfig().getDouble("holograms.ranking_skyrush.z");
		
		Location loc = new Location(Bukkit.getWorld("world"), x, y, z);
		
		List<LeaderboardStat> list = new ArrayList<>();
		list.add(new LeaderboardStat("kills", "§6Kills"));
		list.add(new LeaderboardStat("deaths", "§6Morts"));
		list.add(new LeaderboardStat("victories", "§6Victoires"));
		list.add(new LeaderboardStat("defeats", "§6Défaites"));
		list.add(new LeaderboardStat("playtime", "§6Temps de jeu"));
		LeaderboardsHandler.createLeaderboard(loc, "Classement SkyRush", "skyrush", new ItemStack(Material.ENDER_PEARL), list);
	}
	
	private void updateHolograms()
	{
		updateSkyrush();
		updateFFAskywars();
		updateGemsDefender();
		updateUnderground();
		
		yes = !yes;
	}
	
	private void updateSkyrush()
	{
		if(skyrush.getHeight() > 0) {
			skyrush.clearLines();
		}
		
		skyrush.appendItemLine(new ItemStack(Material.ENDER_PEARL));
		skyrush.appendTextLine("§6✪ §eSkyRush §6✪");
		skyrush.appendTextLine("");
		skyrush.appendTextLine("§e" + playingSkyrush() + " §7joueurs en jeu !");
		skyrush.appendTextLine("");
		if(yes) {
			skyrush.appendTextLine("§f» §6Jouer §f«");
		} else {
			skyrush.appendTextLine("§f» §eJouer §f«");
		}
	}
	
	private void updateFFAskywars()
	{
		if(ffaskywars.getHeight() > 0) {
			ffaskywars.clearLines();
		}
		
		ffaskywars.appendItemLine(new ItemStack(Material.DIAMOND_AXE));
		ffaskywars.appendTextLine("§6✪ §eFFASkywars §6✪");
		ffaskywars.appendTextLine("");
		ffaskywars.appendTextLine("§e" + playingFfaskywars() + " §7joueurs en jeu !");
		ffaskywars.appendTextLine("");
		if(yes) {
			ffaskywars.appendTextLine("§f» §6Jouer §f«");
		} else {
			ffaskywars.appendTextLine("§f» §eJouer §f«");
		}
	}
	
	private void updateGemsDefender()
	{
		if(gemsDefender.getHeight() > 0) {
			gemsDefender.clearLines();
		}
		
		gemsDefender.appendItemLine(new ItemStack(Material.EMERALD_ORE));
		gemsDefender.appendTextLine("§6✪ §eGemsDefender §6✪");
		gemsDefender.appendTextLine("");
		gemsDefender.appendTextLine("§e" + playingGems() + " §7joueurs en jeu !");
		gemsDefender.appendTextLine("");
		if(yes) {
			gemsDefender.appendTextLine("§f» §6Jouer §f«");
		} else {
			gemsDefender.appendTextLine("§f» §eJouer §f«");
		}
	}
	
	private void updateUnderground()
	{
		if(underground.getHeight() > 0) {
			underground.clearLines();
		}
		
		underground.appendItemLine(new ItemStack(Material.COBBLESTONE));
		underground.appendTextLine("§6✪ §eUnderground §6✪");
		underground.appendTextLine("");
		underground.appendTextLine("§e" + playingUnderground() + " §7joueurs en jeu !");
		underground.appendTextLine("");
		if(yes) {
			underground.appendTextLine("§f» §6Jouer §f«");
		} else {
			underground.appendTextLine("§f» §eJouer §f«");
		}
	}
	
	private Integer playingSkyrush() {
		return main.getApi().getServerManager().getPlayingPlayers(ServerGroup.skyrush2x1) + main.getApi().getServerManager().getPlayingPlayers(ServerGroup.skyrush2x2) + main.getApi().getServerManager().getPlayingPlayers(ServerGroup.skyrush2x4);
	}
	
	private Integer playingFfaskywars() {
		return main.getApi().getServerManager().getPlayingPlayers(ServerGroup.ffaskywars);
	}
	
	private Integer playingGems() {
		return main.getApi().getServerManager().getPlayingPlayers(ServerGroup.gems2x4);
	}
	
	private Integer playingUnderground() {
		return main.getApi().getServerManager().getPlayingPlayers(ServerGroup.underground2x4);
	}
	
	private void skyrushHologram()
	{
		double x = main.getConfig().getDouble("holograms.skyrush.x");
		double y = main.getConfig().getDouble("holograms.skyrush.y");
		double z = main.getConfig().getDouble("holograms.skyrush.z");
		
		Location location = new Location(world, x, y, z);
		skyrush = HologramsAPI.createHologram(main, location);
	}
	
	private void undergroundHologram()
	{
		double x = main.getConfig().getDouble("holograms.underground.x");
		double y = main.getConfig().getDouble("holograms.underground.y");
		double z = main.getConfig().getDouble("holograms.underground.z");
		
		Location location = new Location(world, x, y, z);
		underground = HologramsAPI.createHologram(main, location);
	}
	
	private void gemsHologram()
	{
		double x = main.getConfig().getDouble("holograms.gemsdefender.x");
		double y = main.getConfig().getDouble("holograms.gemsdefender.y");
		double z = main.getConfig().getDouble("holograms.gemsdefender.z");
		
		Location location = new Location(world, x, y, z);
		gemsDefender = HologramsAPI.createHologram(main, location);
	}
	
	private void ffaskywarsHologram()
	{
		double x = main.getConfig().getDouble("holograms.ffaskywars.x");
		double y = main.getConfig().getDouble("holograms.ffaskywars.y");
		double z = main.getConfig().getDouble("holograms.ffaskywars.z");
		
		Location location = new Location(world, x, y, z);
		ffaskywars = HologramsAPI.createHologram(main, location);
	}
	
	private void welcomeHologram()
	{
		double x = main.getConfig().getDouble("holograms.welcome.x");
		double y = main.getConfig().getDouble("holograms.welcome.y");
		double z = main.getConfig().getDouble("holograms.welcome.z");
		
		Location location = new Location(world, x, y, z);
		welcome = HologramsAPI.createHologram(main, location);
		
		welcome.appendItemLine(new ItemStack(Material.COMMAND, 1));
		welcome.appendTextLine("§6§m------§e§m------§r §6§lLeemonMC§r §e§m------§6§m------");
		welcome.appendTextLine("§d");
		welcome.appendTextLine("§e§l➜ §7Bienvenue sur §eLeemonMC §7!");
		welcome.appendTextLine("");
		welcome.appendTextLine("§e§l➜ §7Durant cette §ebêta fermée");
		welcome.appendTextLine("§7certaines §efonctitonnalités/jeux §7pourraient ne pas fonctionner !");
		welcome.appendTextLine("");
		welcome.appendTextLine("§e§l➜ §7Merci de signaler §etout bug §7ou §etoute suggestion");
		welcome.appendTextLine("§7sur notre §eserveur Discord §7!");
		welcome.appendTextLine("");
		welcome.appendTextLine("§6§m------§e§m------§6§m------§e§m------§6§m------§e§m------");
	}
	
}
