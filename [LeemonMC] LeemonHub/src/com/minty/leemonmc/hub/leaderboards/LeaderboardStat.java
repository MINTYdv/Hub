package com.minty.leemonmc.hub.leaderboards;

public class LeaderboardStat {

	private String statsHandlerName;
	private String displayName;
	
	public LeaderboardStat(String statsHandlerName, String displayName) {
		super();
		this.statsHandlerName = statsHandlerName;
		this.displayName = displayName;
	}

	public String getStatsHandlerName() {
		return statsHandlerName;
	}
	
	public String getDisplayName() {
		return displayName;
	}
	
}
