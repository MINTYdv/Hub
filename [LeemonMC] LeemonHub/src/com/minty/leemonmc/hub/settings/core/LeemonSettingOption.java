package com.minty.leemonmc.hub.settings.core;

import com.minty.leemonmc.basics.core.Rank;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;

public class LeemonSettingOption
{

	private String nameID = "error";
	private String name = "§cy'a un probleme";
	private ChatColor color = ChatColor.DARK_RED;
	private Rank minimumRank = Rank.PLAYER;
	private ItemStack icon;
	private LeemonSetting parent;
	
	public LeemonSettingOption(LeemonSetting _parent, String _nameID, String _name, ChatColor _color, Rank miniRank, ItemStack _icon)
	{
		parent = _parent;
		nameID = _nameID;
		name = _name;
		color = _color;
		minimumRank = miniRank;
		icon = _icon;
	}
	
	public String getNameID() {
		return nameID;
	}
	
	// Exemple : "Désactivé, Uniquement amis, Fly, double jump,..."
	public String getName()
	{
		return name;
	}
	
	// Exemple : custom skull
	public ItemStack getIcon() {
		if(icon == null) {
			return new ItemStack(getParent().getIcon(), 1);
		}
		return icon;
	}
	
	// Exemple : "Désactivé = Rouge, Activé = Vert"
	public ChatColor getColor()
	{
		return color;
	}
	
	// Exemple : "Fly = Lemon"
	public Rank getMinimumRank()
	{
		return minimumRank;
	}
	
	public LeemonSetting getParent() {
		return parent;
	}
	
}
