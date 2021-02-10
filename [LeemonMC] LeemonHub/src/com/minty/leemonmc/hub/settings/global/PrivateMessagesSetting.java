package com.minty.leemonmc.hub.settings.global;

import com.minty.leemonmc.basics.core.Rank;
import com.minty.leemonmc.hub.LeemonHub;
import com.minty.leemonmc.hub.settings.core.LeemonSetting;
import com.minty.leemonmc.hub.settings.core.LeemonSettingOption;
import com.minty.leemonmc.hub.settings.core.SettingsHandler;
import org.bukkit.ChatColor;
import org.bukkit.Material;

public class PrivateMessagesSetting extends LeemonSetting
{

	public PrivateMessagesSetting(LeemonHub _main, SettingsHandler settingsHandler) {
		super(_main, settingsHandler, "msg_allow");
	}

	@Override
	public void setup()
	{
		super.setup();
		registerOption(new LeemonSettingOption(this, "ENABLED", "Activ�", ChatColor.GREEN, Rank.PLAYER, null));
		registerOption(new LeemonSettingOption(this, "FRIENDS", "Amis seulement", ChatColor.LIGHT_PURPLE, Rank.PLAYER, null));
		registerOption(new LeemonSettingOption(this, "DISABLED", "D�sactiv�", ChatColor.RED, Rank.PLAYER, null));
	}
	
	@Override
	public String getName() {
		return "�6R�ception des messages priv�s";
	}
	
	@Override
	public Material getIcon()
	{
		return Material.NAME_TAG;
	}
	
	@Override
	public String getDescription()
	{
		return "�7D�cide de �equi �7aura l'autorisation de \n �et'envoyer �7des messages �epriv�s �7!";
	}
	
}
