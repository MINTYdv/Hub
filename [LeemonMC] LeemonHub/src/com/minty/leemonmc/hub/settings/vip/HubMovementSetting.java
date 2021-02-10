package com.minty.leemonmc.hub.settings.vip;

import com.minty.leemonmc.basics.core.Rank;
import com.minty.leemonmc.hub.LeemonHub;
import com.minty.leemonmc.hub.settings.core.LeemonSetting;
import com.minty.leemonmc.hub.settings.core.LeemonSettingOption;
import com.minty.leemonmc.hub.settings.core.SettingsHandler;
import org.bukkit.ChatColor;
import org.bukkit.Material;

public class HubMovementSetting extends LeemonSetting
{

	public HubMovementSetting(LeemonHub _main, SettingsHandler settingsHandler) {
		super(_main, settingsHandler, "hub_movement");
	}

	@Override
	public void setup()
	{
		super.setup();
		registerOption(new LeemonSettingOption(this, "WALK", "Marche", ChatColor.YELLOW, Rank.PLAYER, null));
		registerOption(new LeemonSettingOption(this, "DOUBLE_JUMP", "Double Jump", ChatColor.YELLOW, Rank.VIP, null));
		registerOption(new LeemonSettingOption(this, "SPEED", "Speed", ChatColor.YELLOW, Rank.VIP_PLUS, null));
		registerOption(new LeemonSettingOption(this, "FLY", "Voler", ChatColor.YELLOW, Rank.LEMON, null));
	}
	
	@Override
	public String getName() {
		return "§6Déplacement dans le hub";
	}
	
	@Override
	public Material getIcon()
	{
		return Material.FEATHER;
	}
	
	@Override
	public String getDescription()
	{
		return "§7Paramètre ta façon de te §edéplacer \n §7dans le §elobby§7.";
	}
	
}
