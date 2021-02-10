package com.minty.leemonmc.hub.settings.global;

import com.minty.leemonmc.basics.core.Rank;
import com.minty.leemonmc.hub.LeemonHub;
import com.minty.leemonmc.hub.settings.core.LeemonSetting;
import com.minty.leemonmc.hub.settings.core.LeemonSettingOption;
import com.minty.leemonmc.hub.settings.core.SettingsHandler;
import org.bukkit.ChatColor;
import org.bukkit.Material;

public class FriendsRequestsSetting extends LeemonSetting
{

	public FriendsRequestsSetting(LeemonHub _main, SettingsHandler settingsHandler) {
		super(_main, settingsHandler, "friends_requests");
	}

	@Override
	public void setup()
	{
		super.setup();
		registerOption(new LeemonSettingOption(this, "ENABLED", "Activé", ChatColor.GREEN, Rank.PLAYER, null));
		registerOption(new LeemonSettingOption(this, "DISABLED", "Désactivé", ChatColor.RED, Rank.PLAYER, null));
		registerOption(new LeemonSettingOption(this, "FRIENDS_OF_FRIENDS", "Amis d'amis", ChatColor.DARK_PURPLE, Rank.PLAYER, null));
	}
	
	@Override
	public String getName() {
		return "§6Requêtes d'amis";
	}
	
	@Override
	public Material getIcon()
	{
		return Material.BED;
	}
	
	@Override
	public String getDescription()
	{
		return "§7Décide de §equi §7aura l'autorisation de \n §et'envoyer §7des requêtes d'amis !";
	}
	
}
