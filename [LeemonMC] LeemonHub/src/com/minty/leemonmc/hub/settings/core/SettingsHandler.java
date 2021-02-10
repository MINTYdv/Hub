package com.minty.leemonmc.hub.settings.core;

import com.minty.leemonmc.basics.core.Rank;
import com.minty.leemonmc.basics.core.cache.Account;
import com.minty.leemonmc.core.CoreMain;
import com.minty.leemonmc.hub.LeemonHub;
import com.minty.leemonmc.hub.settings.global.FriendsRequestsSetting;
import com.minty.leemonmc.hub.settings.global.GenderSetting;
import com.minty.leemonmc.hub.settings.global.GroupInvitesSetting;
import com.minty.leemonmc.hub.settings.global.PrivateMessagesSetting;
import com.minty.leemonmc.hub.settings.vip.HubJoinEffectSetting;
import com.minty.leemonmc.hub.settings.vip.HubMovementSetting;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class SettingsHandler implements Listener
{

	private List<LeemonSetting> settings = new ArrayList<>();
	
	private LeemonHub main;
	
	public SettingsHandler(LeemonHub _main) {
		this.main = _main;
		this.setup();
	}
	
	private void setup()
	{
		/* Settings VIP */
		register(new HubMovementSetting(main, this));
		register(new HubJoinEffectSetting(main, this));
		
		/* Settings Profile */
		register(new GenderSetting(main, this));
		register(new GroupInvitesSetting(main, this));
		register(new FriendsRequestsSetting(main, this));
		register(new PrivateMessagesSetting(main, this));
	}
	
	public ItemStack getSetting(LeemonSetting setting, Player player)
	{
		for(LeemonSetting s : settings)
		{
			if(s.getClass() == setting.getClass())
			{

				LeemonSettingOption selectedOption = getSelectedoptionofPlayer(player, s);
				
				ItemStack it = selectedOption.getIcon();
				ItemMeta meta = it.getItemMeta();
				
				meta.setDisplayName(s.getName());
				
				List<String> lore = new ArrayList<>();
				lore.add("");
				String[] split = s.getDescription().split(" \n ");
				for(String string : split)
				{
					lore.add(string);
				}
				lore.add("");
				for(LeemonSettingOption opt : s.getOptions())
				{
					if(opt.getMinimumRank() != Rank.PLAYER)
					{
						if(selectedOption == opt) {
							lore.add("§8- §7État : " + opt.getColor() + opt.getName() + " §7(" + opt.getMinimumRank().getdisplayChatMen() + "§7 et +)");
						} else {
							lore.add("§8- §7État : " + opt.getName() + " §7(" + opt.getMinimumRank().getdisplayChatMen() + "§7 et +)");
						}
					} else {
						if(selectedOption == opt) {
							lore.add("§8- §7État : " + opt.getColor() + opt.getName());
						} else {
							lore.add("§8- §7État : " + opt.getName());
						}
					}
				}
				lore.add("");
				lore.add("§6» §eCliquez pour changer");
				
				meta.setLore(lore);
				
				it.setItemMeta(meta);
				return it;
			}
		}
		return null;
	}
	
	public LeemonSettingOption getSelectedoptionofPlayer(Player player, LeemonSetting setting)
	{

		String UUID = player.getUniqueId().toString();
		Account account = CoreMain.getInstance().getAccountManager().getAccount(UUID);
		
		if(account.getSetting(setting.getNameID()).equalsIgnoreCase("404")) {
			account.setSetting(setting.getNameID(), setting.getOptions().get(0).getNameID());
			CoreMain.getInstance().getSql().saveData(player);
		}
		
		String param = account.getSetting(setting.getNameID());
		LeemonSettingOption option = setting.stringToOption(param);
		return option;
	}
	
	@EventHandler
	public void onClick(InventoryClickEvent e)
	{
		Player player = (Player) e.getWhoClicked();
		ItemStack it = e.getCurrentItem();
		
		if(player == null) return;
		if(it == null) return;
		if(it.getType() == Material.AIR) return;
		if(it.getType() == Material.BARRIER) return;
		
		if(e.getInventory().getType() != InventoryType.CHEST) return;
		if(!e.getInventory().getName().startsWith("§6Profil")) return;
		
		for(LeemonSetting setting : getSettings())
		{
			if(getSetting(setting, player).getItemMeta().getDisplayName() == it.getItemMeta().getDisplayName())
			{
				setting.clicked(player, e.getClickedInventory(), e.getSlot());
				break;
			}
		}
	}
	
	public LeemonSetting stringToSetting(String string)
	{
		for(LeemonSetting setting : getSettings()) {
			if(setting.getNameID().equalsIgnoreCase(string)) {
				return setting;
			}
		}
		return null;
	}
	
	public void updateSettings(Player player)
	{
		/* 
		 * HUB MOVEMENT
		 * */
		
		try { 
			if(getSelectedoptionofPlayer(player, stringToSetting("hub_movement")).getNameID().equalsIgnoreCase("FLY"))
			{
				player.setAllowFlight(true);
			} else if (getSelectedoptionofPlayer(player, stringToSetting("hub_movement")).getNameID().equalsIgnoreCase("WALK")){
				if(player.getGameMode() != GameMode.CREATIVE || player.getGameMode() != GameMode.SPECTATOR)
				{
					if(player.isFlying()) {
						player.setFlying(false);
					}
				}
			}
			
			if(getSelectedoptionofPlayer(player, stringToSetting("hub_movement")).getNameID().equalsIgnoreCase("SPEED"))
			{
				player.setAllowFlight(false);
				player.setWalkSpeed(1f);
			} else {
				if(getSelectedoptionofPlayer(player, stringToSetting("hub_movement")).getNameID().equalsIgnoreCase("WALK"))
				{
					player.setAllowFlight(false);
				} else {
					player.setAllowFlight(true);
				}
				player.setWalkSpeed(0.2f);
			}
			
		} catch(Exception e) {
			// Ignore it
			e.printStackTrace();
		}
		

	}
	
	public void register(LeemonSetting setting)
	{
		System.out.println("Registering " + setting.getNameID() + " setting");
		settings.add(setting);
		setting.setup();
	}
	
	public List<LeemonSetting> getSettings()
	{
		return settings;
	}
	
}
