package com.minty.leemonmc.hub.settings.core;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import com.minty.leemonmc.basics.core.Rank;
import com.minty.leemonmc.core.CoreMain;
import com.minty.leemonmc.hub.LeemonHub;

public class LeemonSetting
{

	private List<LeemonSettingOption> options = new ArrayList<>();
	private LeemonSettingOption defaultOption;

	private String nameID;
	
	private LeemonHub main;
	
	public LeemonSetting(LeemonHub _main, SettingsHandler settingsHandler, String _nameID) {
		nameID = _nameID;
		main = _main;
	}
	
	public void setup()
	{
		
	}
	
	public void setSelectedOption(Player player, LeemonSettingOption opt)
	{
		if(player != null && opt != null)
		{
			String UUID = player.getUniqueId().toString();
			CoreMain.getInstance().getAccountManager().getAccount(UUID).setSetting(getNameID(), opt.getNameID());
			CoreMain.getInstance().getSql().saveData(player);
		}
	}
	
	public String getNameID() {
		return nameID;
	}
	
	public void setNameID(String nameID) {
		this.nameID = nameID;
	}
	
	public int getOptionID(LeemonSettingOption opt)
	{
		for(int i = 0; i < getOptions().size(); i++)
		{
			LeemonSettingOption target = getOptions().get(i);
			if(target == opt)
			{
				return i;
			}
		}
		return 0;
	}
	
	public LeemonSettingOption stringToOption(String _target)
	{
		for(LeemonSettingOption opt : getOptions())
		{
			if(opt.getNameID().equalsIgnoreCase(_target))
			{
				return opt;
			}
		}
		return null;
	}
	
	public void registerOption(LeemonSettingOption opt)
	{
		if(options.contains(opt)) return;
		
		options.add(opt);
		System.out.println("Registering option " + opt.getNameID());
		if(getDefaultOption() == null) {
			setDefaultOption(opt);
		}
	}
	
	public LeemonHub getMain() {
		return main;
	}
	
	public LeemonSettingOption getDefaultOption() {
		return defaultOption;
	}
	
	public void clicked(Player player, Inventory inv, int slot)
	{
		String UUID = player.getUniqueId().toString();
		Rank rank = CoreMain.getInstance().getAccountManager().getAccount(UUID).getRank();
		
		int currentID = getOptionID(stringToOption(CoreMain.getInstance().getAccountManager().getAccount(UUID).getSetting(getNameID())));
		
		List<LeemonSettingOption> possibleOptions = new ArrayList<>();
		for(LeemonSettingOption opt : getOptions())
		{
			if(opt.getMinimumRank().getPower() <= rank.getPower()) {
				possibleOptions.add(opt);
			}
		}
		
		if(possibleOptions.size() <= 1)
		{
			player.playSound(player.getLocation(), Sound.ENTITY_BLAZE_DEATH, 1f, 1f);
			
			player.sendMessage("§c§m---------------------------------------");
			player.sendMessage("");
			player.sendMessage("§f§l➜  §eTu n'as pas le grade nécessaire pour");
			player.sendMessage("      §emodifier cette option !");
			player.sendMessage("");
			player.sendMessage("§f§l➜  §eUn meilleur grade ?");
			player.sendMessage("      §6https://store.leemonmc.fr");
			player.sendMessage("");
			player.sendMessage("§c§m---------------------------------------");
		}
		
		currentID++;
		if(currentID >= possibleOptions.size())
		{
			currentID = 0;
		}
		LeemonSettingOption nextOption = possibleOptions.get(currentID);

		while(nextOption.getMinimumRank().getPower() > rank.getPower())
		{
			clicked(player, inv, slot);
			return;
		}
		
		CoreMain.getInstance().getAccountManager().getAccount(UUID).setSetting(nameID, nextOption.getNameID());
		setSelectedOption(player, nextOption);
		inv.setItem(slot, LeemonHub.getInstance().getSettingsHandler().getSetting(this, player));
		
		player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 1f, 1f);
		main.getSettingsHandler().updateSettings(player);
	}
	
	public void setDefaultOption(LeemonSettingOption defaultOption) {
		this.defaultOption = defaultOption;
	}
	
	public String getName()
	{
		return "§clol";
	}
	
	public String getDescription()
	{
		return "§7Si tu vois cette description, c'est que \n y'a un sérieux probleme ^^";
	}
	
	public Material getIcon()
	{
		return null;
	}
	
	public List<LeemonSettingOption> getOptions() {
		return options;
	}
	
}
