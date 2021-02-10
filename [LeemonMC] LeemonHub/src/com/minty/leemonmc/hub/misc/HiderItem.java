package com.minty.leemonmc.hub.misc;

import com.minty.leemonmc.basics.core.cache.Account;
import com.minty.leemonmc.core.events.dataLoadedEvent;
import com.minty.leemonmc.hub.LeemonHub;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class HiderItem implements Listener
{

	private LeemonHub main;
	
	private String datFileName = "hider.yml";
	
    private Map<String, Boolean> visibleData = new HashMap<>();
    private Map<String, Long> lastTries = new HashMap<>();
    
	public HiderItem(LeemonHub _main)
	{
		main = _main;
		setup();
	}
	
	private void setup()
	{
		createCustomConfig();
		loadData();
	}
	
	/* 
	 * MAIN FUNCTIONS
	 * */
	
	private boolean getVisible(String uuid)
	{
		if(getVisibleData().containsKey(uuid))
		{
			return getVisibleData().get(uuid);
		} else {
			getVisibleData().put(uuid, true);
		}
		return false;
	}
	
	public void setVisible(String uuid, boolean value) {
		getVisible(uuid);
		
		getVisibleData().remove(uuid);
		getVisibleData().put(uuid, value);
	}
	
	private void setLastTry(String uuid)
	{
		getLastTry(uuid);
		
		getLastTries().remove(uuid);
		getLastTries().put(uuid, System.currentTimeMillis());
	}
	
	private long getLastTry(String uuid)
	{
		if(getLastTries().containsKey(uuid))
		{
			return getLastTries().get(uuid);
		} else {
			getLastTries().put(uuid, (long) 0);
			return 0;
		}
	}
	
	public Map<String, Long> getLastTries() {
		return lastTries;
	}
	
	private void loadData()
	{
		ConfigurationSection section = getCustomConfig().getConfigurationSection("data");
		for(String key : section.getKeys(false))
		{
			if(!getVisibleData().containsKey(key))
			{
				boolean value = getCustomConfig().getBoolean("data." + key + ".visible");
				getVisibleData().put(key, value);
			}
		
		}
	}
	
	/* 
	 * LISTENERS
	 * */
	
	@EventHandler
	public void onInteract(PlayerInteractEvent e)
	{
		Player player = e.getPlayer();
		String uuid = player.getUniqueId().toString();
		
		ItemStack it = e.getItem();
		Action action = e.getAction();
		if(it == null) return;
		
		if(action == Action.LEFT_CLICK_AIR || action == Action.LEFT_CLICK_BLOCK || action == Action.RIGHT_CLICK_BLOCK) return;
		
		if(!it.hasItemMeta()) return;
		if(it.getType() == Material.INK_SACK)
		{
			if(System.currentTimeMillis() - getLastTry(uuid) < 5000)
			{
				return;
			}
			
			toggleVisibility(player);
		}
	}
	
	private void toggleVisibility(Player player)
	{
		String uuid = player.getUniqueId().toString();
		
		if(getVisible(uuid))
		{
			setVisible(uuid, false);
			player.getInventory().setItem(7, main.getApi().getGuiUtils().createItem(Material.INK_SACK, main.getConfigItemName("vanish-off"), (byte) 8));
			player.sendMessage("§6§lLeeonMC §f» §7Tous les joueurs ont disparus !");
			
			updateAll();
			
			setLastTry(uuid);
			
		} else
		{
			setVisible(uuid, true);
			player.getInventory().setItem(7, main.getApi().getGuiUtils().createItem(Material.INK_SACK, main.getConfigItemName("vanish-on"), (byte) 10));
			player.sendMessage("§6§lLeemonMC §f» §7Réapparation des joueurs !");
			setLastTry(uuid);
			
			updateAll();
		}
		
		new BukkitRunnable() {
			
			@Override
			public void run()
			{
				if(player.getGameMode() != GameMode.ADVENTURE) return;
				ItemStack itemStack = player.getInventory().getItem(7);
				if(itemStack == null) return;
				
				int cooldownSeconds = (int) (System.currentTimeMillis() - getLastTry(uuid)) / 1000;
				
				if((5 - cooldownSeconds) <= 0)
				{
					// Cooldown is over
					itemStack.setAmount(1);
					this.cancel();
				} else
				{
					// Cooldown is still active
					itemStack.setAmount((5 - cooldownSeconds) * -1);
				}
			}
		}.runTaskTimer(main, 2, 2);
		
		player.playSound(player.getLocation(), Sound.ITEM_ARMOR_EQUIP_GENERIC, 1f, 1f);
	}
	
	@EventHandler
	public void onJoin(dataLoadedEvent e)
	{
		Player player = e. getPlayer();
		String uuid = player.getUniqueId().toString();
		
		getVisible(uuid);
		
		PlayerInventory inv = player.getInventory();
		ItemStack it = null;

		updateAll();
		
		if(getVisible(uuid))
		{
			it = main.getApi().getGuiUtils().createItem(Material.INK_SACK, main.getConfigItemName("vanish-on"), (byte) 10);
			
		} else {
			it = main.getApi().getGuiUtils().createItem(Material.INK_SACK, main.getConfigItemName("vanish-off"), (byte) 8);
		}
		
		inv.setItem(7, it);
	}
	
	private void showAll(Player player)
	{
		for(Player pls : Bukkit.getOnlinePlayers())
		{
			Account account = main.getApi().getAccountManager().getAccount(pls.getUniqueId().toString());
			if(!account.isModEnabled())
			{
				player.showPlayer(pls);
			} else {
				player.hidePlayer(pls);
			}
		}
	}
	
	private void hideAll(Player player)
	{
		for(Player pls : Bukkit.getOnlinePlayers())
		{
			player.hidePlayer(pls);
		}
	}
	
	@SuppressWarnings("unused")
	private void updateAll()
	{
		for(Player pls : Bukkit.getOnlinePlayers())
		{
			for(Player pls2 : Bukkit.getOnlinePlayers())
			{
				String uuid = pls2.getUniqueId().toString();
				if(getVisible(uuid))
				{
					showAll(pls2);
				} else {
					hideAll(pls2);
				}
			}
		}
		
	}
	
	/* 
	 * CONFIGURATION HANDLERS
	 * */
	
    private File customConfigFile;
    private FileConfiguration customConfig;
	
	public void saveCustomConfig()
	{
		for(Entry<String, Boolean> entry : getVisibleData().entrySet())
		{
			getCustomConfig().set("data." + entry.getKey() + ".visible", entry.getValue());
		}
		
		try {
			customConfig.save(new File(main.getDataFolder(), datFileName));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
    private void createCustomConfig() {
        customConfigFile = new File(main.getDataFolder(), datFileName);
        if (!customConfigFile.exists()) {
            customConfigFile.getParentFile().mkdirs();
            main.saveResource(datFileName, false);
         }

        customConfig= new YamlConfiguration();
        try {
            customConfig.load(customConfigFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }
	
    /* 
     * GETTERS AND SETTERS
     * */
    
    public Map<String, Boolean> getVisibleData() {
		return visibleData;
	}
    
    public FileConfiguration getCustomConfig() {
        return this.customConfig;
    }
	
}
