package com.minty.leemonmc.hub.settings.vip;

import com.minty.leemonmc.basics.core.Rank;
import com.minty.leemonmc.hub.LeemonHub;
import com.minty.leemonmc.hub.settings.core.LeemonSetting;
import com.minty.leemonmc.hub.settings.core.LeemonSettingOption;
import com.minty.leemonmc.hub.settings.core.SettingsHandler;
import org.bukkit.ChatColor;
import org.bukkit.Material;

public class HubJoinEffectSetting extends LeemonSetting
{

	public HubJoinEffectSetting(LeemonHub _main, SettingsHandler settingsHandler) {
		super(_main, settingsHandler, "join_effect");
	}

	@Override
	public void setup()
	{
		super.setup();
		registerOption(new LeemonSettingOption(this, "NOTHING", "Rien", ChatColor.YELLOW, Rank.PLAYER, null));
		registerOption(new LeemonSettingOption(this, "STORM", "Éclair", ChatColor.YELLOW, Rank.VIP_PLUS, null));
		registerOption(new LeemonSettingOption(this, "WOLF", "Aboiement", ChatColor.YELLOW, Rank.LEMON, null));
	}
	
	@Override
	public String getName() {
		return "§6Effet lors de la connexion";
	}
	
	@Override
	public Material getIcon()
	{
		return Material.BLAZE_POWDER;
	}
	
	@Override
	public String getDescription()
	{
		return "§7Paramètre l'effet lors de ta §econnexion \n §7dans un §elobby.";
	}
	
}
