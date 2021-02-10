package com.minty.leemonmc.hub.leaderboards;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.minty.leemonmc.hub.LeemonHub;

public class LeaderboardsHandler {

	private static final int hologramsCooldowns = 5;
	
	private static List<LeaderboardHologram> leaderboardHolograms = new ArrayList<>();
	private static LeemonHub main = LeemonHub.getInstance();
	private static Map<Player, Long> playersCooldowns = new HashMap<>();
	
	public static void createLeaderboard(Location loc, String displayname, String minigame, ItemStack icon, List<LeaderboardStat> stats)
	{
		System.out.println("Creating leaderboard " + displayname + "...");
		System.out.println("LOCATION, X : " + loc.getX() + " Y : " + loc.getY() + " Z : " + loc.getZ());
		LeaderboardHologram leaderboardHologram = new LeaderboardHologram(loc, icon, displayname, minigame, stats);
		getLeaderboardHolograms().add(leaderboardHologram);
		main.getServer().getPluginManager().registerEvents(leaderboardHologram, main);
	}
	
	public static Map<Player, Long> getPlayersCooldowns() {
		return playersCooldowns;
	}
	
	public static int getHologramscooldowns() {
		return hologramsCooldowns;
	}
	
	public static List<LeaderboardHologram> getLeaderboardHolograms() {
		return leaderboardHolograms;
	}
	
}
