package com.minty.leemonmc.hub.settings.global;

import com.minty.leemonmc.basics.core.Rank;
import com.minty.leemonmc.hub.LeemonHub;
import com.minty.leemonmc.hub.settings.core.LeemonSetting;
import com.minty.leemonmc.hub.settings.core.LeemonSettingOption;
import com.minty.leemonmc.hub.settings.core.SettingsHandler;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class GenderSetting extends LeemonSetting
{

	public GenderSetting(LeemonHub _main, SettingsHandler settingsHandler) {
		super(_main, settingsHandler, "global_gender");
	}

	@Override
	public void setup()
	{
		super.setup();
		ItemStack homme = super.getMain().getApi().getLeemonUtils().getSkull("http://textures.minecraft.net/texture/65e5223317a890a30351f6f78d0abf8dd76cbd08df6f918883934564d28e58e");
		ItemStack femme = super.getMain().getApi().getLeemonUtils().getSkull("http://textures.minecraft.net/texture/19f8f4e92d26c4c9b98cdddbf28ecdc3c16ea302fa449734e91d8474b78b26");

		registerOption(new LeemonSettingOption(this, "MALE", "Homme", ChatColor.YELLOW, Rank.PLAYER, homme));
		registerOption(new LeemonSettingOption(this, "FEMALE", "Femme", ChatColor.YELLOW, Rank.PLAYER, femme));
		registerOption(new LeemonSettingOption(this, "NEUTRAL", "Neutre", ChatColor.YELLOW, Rank.PLAYER, null));
	}
	
	@Override
	public String getName() {
		return "§6Votre Sexe";
	}
	
	@Override
	public Material getIcon()
	{
		return Material.SKULL_ITEM;
	}
	
	@Override
	public String getDescription()
	{
		return "§7Paramètre la façon dont le §eserveur §7te fera \n §7référence. Tes §egrades §7seront modifiés \n §7automatiquement en fonction de ton genre !";
	}
	
}
