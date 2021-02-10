package com.minty.leemonmc.hub.leaderboards;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import com.gmail.filoghost.holographicdisplays.api.handler.TouchHandler;
import com.gmail.filoghost.holographicdisplays.api.line.TouchableLine;
import com.minty.leemonmc.core.stats.LeaderboardHandler;
import com.minty.leemonmc.hub.LeemonHub;

public class LeaderboardHologram implements Listener {

	private String minigame;
	private List<LeaderboardStat> stats;
	private ItemStack icon;
	private Location location;
	private String displayName;
	
	private Map<Player, Hologram> playersHolograms = new HashMap<>();
	private Map<Player, Integer> playersPages = new HashMap<>();
	
	private LeemonHub main = LeemonHub.getInstance();
	
	public LeaderboardHologram(Location location, ItemStack icon, String _displayName, String minigame, List<LeaderboardStat> stats) {
		super();
		this.location = location;
		this.icon = icon;
		this.minigame = minigame;
		this.stats = stats;
		this.displayName = _displayName;
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e)
	{
		Player player = e.getPlayer();
		if(getPlayersHolograms().containsKey(player)) {
			getPlayersHolograms().remove(player);
		}
		
		createHologram(player);
	}
	
	@EventHandler
	public void onLeave(PlayerQuitEvent e)
	{
		Player player = e.getPlayer();
		if(getPlayersHolograms().containsKey(player)) {
			Hologram hologram = getPlayersHolograms().get(player);
			hologram.delete();
			getPlayersHolograms().remove(player);
		}
	}
	
	private void createHologram(Player player)
	{
		System.out.println("Creating hologram...");
		Hologram hologram = HologramsAPI.createHologram(main, getLocation());
		hologram.getVisibilityManager().setVisibleByDefault(false);
		hologram.getVisibilityManager().showTo(player);

		getPlayersHolograms().put(player, hologram);
		updateHologram(player);
	}
	
	@SuppressWarnings("unchecked")
	private void updateHologram(Player player)
	{
		if(!getPlayersHolograms().containsKey(player)) {
			createHologram(player);
		}
		
		if(!getPlayersPages().containsKey(player)) 
		{
			getPlayersPages().put(player, 0);
		}
		
		Hologram hologram = getPlayersHolograms().get(player);
		int page = getPlayersPages().get(player);
		LeaderboardStat stat = getStats().get(page);
		hologram.clearLines();
		hologram.appendItemLine(getIcon());
		hologram.appendTextLine("§6§l" + getDisplayName());
		hologram.appendTextLine("§7§o" + stat.getDisplayName());
		hologram.appendTextLine("");
		
		Map<String, Integer> leaderboard = LeaderboardHandler.getLeaderboard(getMinigame(), stat.getStatsHandlerName());
		
		for(int i = 0; i < 10; i++)
		{
			int ranking = i + 1;
			
			if(i >= leaderboard.values().size())
			{
				hologram.appendTextLine("§6§l#" + ranking + " §eNon attribué");
				continue;
			}
			
			Entry<String, Integer> entry = (Entry<String, Integer>) leaderboard.entrySet().toArray()[i];
			if(entry == null)
			{
				hologram.appendTextLine("§6§l#" + ranking + " §eNon attribué");
				continue;
			}
			
			hologram.appendTextLine("§6§l#" + ranking + " §e" + main.getApi().getAccountManager().UUIDtoUsername(entry.getKey()) + " §7(" + entry.getValue() + ")");
		}
		hologram.appendTextLine(" ");
		String result = "";
		for(LeaderboardStat statPage : stats)
		{
			if(statPage.getStatsHandlerName().equalsIgnoreCase(stat.getStatsHandlerName()))
			{
				result += "§c§l" + ChatColor.stripColor(statPage.getDisplayName()) + " ";
			} else {
				result += "§7" + ChatColor.stripColor(statPage.getDisplayName()) + " ";
			}
		}
		
		hologram.appendTextLine(result);
		TouchableLine touchableLine = hologram.appendTextLine("§6» §eCliquez ici pour changer §6«");
		touchableLine.setTouchHandler(new TouchHandler() {
			@Override
			public void onTouch(Player player) {
				click(player);
			}
		});
	}
	
	private void click(Player player)
	{
		if(!LeaderboardsHandler.getPlayersCooldowns().containsKey(player))
		{
			LeaderboardsHandler.getPlayersCooldowns().put(player, System.currentTimeMillis() - LeaderboardsHandler.getHologramscooldowns() * 1000);
		}
		
		long lastTime = LeaderboardsHandler.getPlayersCooldowns().get(player);
		long elapsed = System.currentTimeMillis() - lastTime;
		
		if(elapsed >= LeaderboardsHandler.getHologramscooldowns() * 1000)
		{
			// Cooldown if off
			int page = getPlayersPages().get(player);
			getPlayersPages().remove(player);
			page++;
			if(page >= getStats().size()) {
				page = 0;
			}
			
			getPlayersPages().put(player, page);
			updateHologram(player);
			
			LeaderboardsHandler.getPlayersCooldowns().remove(player);
			LeaderboardsHandler.getPlayersCooldowns().put(player, System.currentTimeMillis());
			player.playSound(player.getLocation(), Sound.BLOCK_LEVER_CLICK, 1f, 1f);
		}
	}
	
	public Hologram getPlayerHologram(Player player)
	{
		return getPlayersHolograms().get(player);
	}
	
	public int getPlayerPage(Player player)
	{
		if(!getPlayersPages().containsKey(player))
		{
			getPlayersPages().put(player, 0);
		}
		return getPlayersPages().get(player);
	}
	
	public ItemStack getIcon() {
		return icon;
	}
	
	public String getDisplayName() {
		return displayName;
	}

	public Location getLocation() {
		return location;
	}
	
	public Map<Player, Hologram> getPlayersHolograms() {
		return playersHolograms;
	}
	
	public Map<Player, Integer> getPlayersPages() {
		return playersPages;
	}
	
	public String getMinigame() {
		return minigame;
	}
	
	public List<LeaderboardStat> getStats() {
		return stats;
	}
	
}
