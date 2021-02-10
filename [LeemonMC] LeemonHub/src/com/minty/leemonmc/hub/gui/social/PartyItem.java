package com.minty.leemonmc.hub.gui.social;

import com.minty.leemonmc.social.core.Party;

public class PartyItem
{

	private String playerUUID;
	private Party party;
	
	public PartyItem(String playerUUID, Party party) {
		super();
		this.playerUUID = playerUUID;
		this.party = party;
	}
	
	public String getPlayerUUID() {
		return playerUUID;
	}
	
	public Party getParty() {
		return party;
	}
	
}
