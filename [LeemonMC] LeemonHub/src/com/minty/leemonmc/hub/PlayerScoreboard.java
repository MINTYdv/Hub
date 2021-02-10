package com.minty.leemonmc.hub;

import com.minty.leemonmc.core.CoreMain;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

public class PlayerScoreboard {

	public static ScoreboardManager m;
	public static Scoreboard b;
	public static Objective o;
	
	public static Score playerDisplayname;
	public static Score onlinePlayers;
	
	private static LeemonHub main = LeemonHub.getPlugin(LeemonHub.class);
	
	public static void scoreGame(Player p)
	{
	    // if(initialized.contains(p.getUniqueId())) return; //Remove this line if you want, I had it in place because the server I made this for had issues creating player objects quickly onPlayerJoin, caused a fat NPE to happen, just delayed this method by a second in onPlayerJoin
	    if(p.getScoreboard().equals(Bukkit.getScoreboardManager().getMainScoreboard())) p.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard()); //Per-player scoreboard, not necessary if all the same data, but we're personalizing the displayname and all
	    Scoreboard score = p.getScoreboard(); //Personalized scoreboard
	    Objective objective = score.getObjective(p.getName()) == null ? score.registerNewObjective(p.getName(), "dummy") : score.getObjective(p.getName()); //Per-player objectives, even though it doesn't matter what it's called since we're using per-player scoreboards.
	    String displayName = "§6§lLeemonMC";
	    
	    int rawPulpe = 0;
	    int rawLemons = 0;
	    
	    String pulpeAmount = "";
	    String lemonsAmount = "";
	    
	    String UUID = p.getUniqueId().toString();
	    
	    if(main.getApi() != null) {
	    	rawPulpe = CoreMain.getInstance().getAccountManager().getAccount(UUID).getPulpes();
	    	rawLemons = CoreMain.getInstance().getAccountManager().getAccount(UUID).getLemons();
	    	
	    	pulpeAmount = main.getApi().getLeemonUtils().formatNumber(rawPulpe);
	    	lemonsAmount = main.getApi().getLeemonUtils().formatNumber(rawLemons);
	   
	    	
	    } else {
	    	System.out.println("Error: leeCore is null !");
	    }
	    
	    objective.setDisplayName("\u00A7d\u00A7l" + displayName);
	    replaceScore(objective, 10, "§8");
	    replaceScore(objective, 9, "§f➠ §7Compte : §e" + p.getDisplayName());
	    replaceScore(objective, 8, "§f➠ §7Rang : " + CoreMain.getInstance().getAccountManager().getAccount(UUID).getPrefixAccordingToSettings());
	    replaceScore(objective, 7, "§d");
	    replaceScore(objective, 6, "§7Vous avez §e" + pulpeAmount + " pulpes");
	    replaceScore(objective, 5, "§7Vous avez §6" + lemonsAmount + " citrons");
	    replaceScore(objective, 4, "§4");
	    replaceScore(objective, 3, "§f➠ §7Joueurs : §a" + main.getApi().bungeeOnlinePlayers);
	    replaceScore(objective, 2, "§f➠ §7Lobby : §d#" + main.getApi().getServerManager().getServer().getID());
	    replaceScore(objective, 1, "§7");
	    replaceScore(objective, 0, CoreMain.getInstance().getScoreboardAnimator().getFooter());
	    if(objective.getDisplaySlot() != DisplaySlot.SIDEBAR) objective.setDisplaySlot(DisplaySlot.SIDEBAR);
	    p.setScoreboard(score);

	}
	
	public static String getEntryFromScore(Objective o, int score) {
	    if(o == null) return null;
	    if(!hasScoreTaken(o, score)) return null;
	    for (String s : o.getScoreboard().getEntries()) {
	        if(o.getScore(s).getScore() == score) return o.getScore(s).getEntry();
	    }
	    return null;
	}

	public static boolean hasScoreTaken(Objective o, int score) {
	    for (String s : o.getScoreboard().getEntries()) {
	        if(o.getScore(s).getScore() == score) return true;
	    }
	    return false;
	}

	public static void replaceScore(Objective o, int score, String name) {
	    if(hasScoreTaken(o, score)) {
	        if(getEntryFromScore(o, score).equalsIgnoreCase(name)) return;
	        if(!(getEntryFromScore(o, score).equalsIgnoreCase(name))) o.getScoreboard().resetScores(getEntryFromScore(o, score));
	    }
	    o.getScore(name).setScore(score);
	}
}
