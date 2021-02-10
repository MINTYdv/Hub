package com.minty.leemonmc.hub;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.PluginMessageListener;
import org.bukkit.scheduler.BukkitRunnable;

import com.minty.leemonmc.core.CoreMain;
import com.minty.leemonmc.core.util.GuiManager;
import com.minty.leemonmc.core.util.Title;
import com.minty.leemonmc.hub.cmd.CommandJoinMessage;
import com.minty.leemonmc.hub.cmd.CommandOptions;
import com.minty.leemonmc.hub.cmd.CommandPlay;
import com.minty.leemonmc.hub.cmd.CommandSpawn;
import com.minty.leemonmc.hub.cmd.CommandVipPrefix;
import com.minty.leemonmc.hub.gui.play.PlayFFASkyMenu;
import com.minty.leemonmc.hub.gui.play.PlayGemsMenu;
import com.minty.leemonmc.hub.gui.play.PlayMenu;
import com.minty.leemonmc.hub.gui.play.PlaySkyRushMenu;
import com.minty.leemonmc.hub.gui.play.PlayUndergroundMenu;
import com.minty.leemonmc.hub.gui.profile.ProfileMenu;
import com.minty.leemonmc.hub.gui.profile.ProfileStatsMenu;
import com.minty.leemonmc.hub.gui.social.GroupMenu;
import com.minty.leemonmc.hub.handlers.BossBarHandler;
import com.minty.leemonmc.hub.handlers.HologramsHandler;
import com.minty.leemonmc.hub.misc.DoubleJump;
import com.minty.leemonmc.hub.misc.HiderItem;
import com.minty.leemonmc.hub.misc.LaunchPad;
import com.minty.leemonmc.hub.settings.core.SettingsHandler;
import com.minty.leemonmc.hub.settings.gui.PlayerSettingsMenu;
import com.minty.leemonmc.hub.settings.gui.VIPSettingsMenu;
import com.minty.leemonmc.hub.shop.gui.ShopBoostersMenu;
import com.minty.leemonmc.hub.shop.gui.ShopMenu;
import com.minty.leemonmc.hub.shop.gui.ShopRanksMenu;
import com.minty.leemonmc.hub.shop.handlers.ShopArticlesHandler;

public class LeemonHub extends JavaPlugin implements PluginMessageListener {

	@Deprecated
	public CoreMain leeCore = (CoreMain) Bukkit.getServer().getPluginManager().getPlugin("LeemonCore");
	
	public Title title;
	
	// Item names
	
	private BossBarHandler bossBarHandler = new BossBarHandler(this);
	private SettingsHandler settingsHandler = new SettingsHandler(this);
	private DoubleJump doubleJump;
	private HologramsHandler hologramsHandler;
	private ShopArticlesHandler shopArticlesHandler;
	
	private static LeemonHub instance;
	
	private HiderItem hiderItem;
	
	@Override
	public void onEnable() {
		
		leeCore = (CoreMain) Bukkit.getServer().getPluginManager().getPlugin("LeemonCore");
		saveDefaultConfig();
		
		/* Check dependencies */
		
		if (!Bukkit.getPluginManager().isPluginEnabled("HolographicDisplays")) {
			getLogger().severe("*** HolographicDisplays is not installed or not enabled. ***");
			getLogger().severe("*** This plugin will be disabled. ***");
			this.setEnabled(false);
			return;
		}
		
		/* 
		 * REGISTER OBJECTS AND LISTENERS AND MAKE REFERENCESS
		 * */
		
		registerReferences();
		registerListeners();
		registerCommands();
		registerChannels();
		registerUpdater();
		
		System.out.println("[LeemonHub] Plugin actif !");
	}
	
	@Override
	public void onDisable() {
		if(bossBarHandler.hubBar != null) {
			bossBarHandler.hubBar.setVisible(false);
		}	
		getHiderItem().saveCustomConfig();
		System.out.println("[LeemonHub] Plugin inactif !");
	}
	
	private void registerChannels()
	{
	    getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
	    getServer().getMessenger().registerIncomingPluginChannel(this, "BungeeCord", this);
	}
	
	private void registerCommands()
	{
		getCommand("opt").setExecutor(new CommandOptions(this));
		getCommand("options").setExecutor(new CommandOptions(this));
		getCommand("play").setExecutor(new CommandPlay(this));
		getCommand("joinmsg").setExecutor(new CommandJoinMessage(this));
		getCommand("spawn").setExecutor(new CommandSpawn(this));
		getCommand("prefix").setExecutor(new CommandVipPrefix(this));
	}
	
	private void registerListeners()
	{
		getServer().getPluginManager().registerEvents(new LeemonHubListeners(this), this);
		getServer().getPluginManager().registerEvents(getHiderItem(), this);
		getServer().getPluginManager().registerEvents(new LaunchPad(), this);
		getServer().getPluginManager().registerEvents(getSettingsHandler(), this);
		getServer().getPluginManager().registerEvents(new LeeHubOnJoin(this, getApi().getGuiUtils()), this);
		getServer().getPluginManager().registerEvents(getDoubleJump(), this);
	}
	
	public void registerUpdater()
	{
		new BukkitRunnable() {
			
			@Override
			public void run() {
        		for(Player online : Bukkit.getOnlinePlayers())
        		{
        			if(getApi().getAccountManager().getAccount(online.getUniqueId().toString()) != null)
        			{
            			PlayerScoreboard.scoreGame(online);
        			}
        		}
			}
		}.runTaskTimer(this, 0, getApi().getScoreboardAnimator().getUpdateTime() + 1);
		
        new BukkitRunnable() {
        	@Override
        	public void run()
        	{
    			int nonMod = 0;
    			for(Player player : Bukkit.getOnlinePlayers())
    			{
    				if(getApi().getAccountManager().getAccount(player.getUniqueId().toString()).isModEnabled() == false)
    				{
    					nonMod++;
    				}
    			}
    			getApi().getServerManager().getServer().setPlayingPlayers(nonMod);
    			getApi().getServerManager().getServer().setMaxPlayers(Bukkit.getMaxPlayers());
        	}
        }.runTaskTimer(this, 0, 20);
        
        GuiManager manager = getApi().getGuiManager();
        
		manager.addMenu(new PlayMenu(getApi().getGuiUtils()));
		manager.addMenu(new PlaySkyRushMenu(getApi().getGuiUtils()));
		manager.addMenu(new PlayGemsMenu(getApi().getGuiUtils()));
		manager.addMenu(new PlayFFASkyMenu(getApi().getGuiUtils()));
		manager.addMenu(new PlayUndergroundMenu(getApi().getGuiUtils()));
		
		//Profile Menus
		manager.addMenu(new ProfileMenu(getApi().getGuiUtils()));
		manager.addMenu(new ProfileStatsMenu(getApi().getGuiUtils(), this));
		manager.addMenu(new GroupMenu(getApi().getGuiUtils()));
		
		// Settings Menus
		manager.addMenu(new VIPSettingsMenu(getApi().getGuiUtils(), this));
		manager.addMenu(new PlayerSettingsMenu(getApi().getGuiUtils(), this));
		
		// Shop menus
		manager.addMenu(new ShopMenu(getApi().getGuiUtils()));
		manager.addMenu(new ShopRanksMenu(getApi().getGuiUtils()));
		manager.addMenu(new ShopBoostersMenu(getApi().getGuiUtils()));
	}
	
	private void registerReferences()
	{
		instance = this;
		
		title = new Title();
		bossBarHandler = new BossBarHandler(this);
		bossBarHandler.setupBossBar();
		settingsHandler = new SettingsHandler(this);
		hiderItem = new HiderItem(this);
		doubleJump = new DoubleJump(this);
		hologramsHandler = new HologramsHandler(this);
		shopArticlesHandler = new ShopArticlesHandler();
		getShopArticlesHandler().setup();
		getHologramsHandler().setup();
	}
	
	/* 
	 * Getters & Setters
	 * */
	
	public String getConfigItemName(String arg)
	{
		return getConfig().getString("names." + arg);
	}
	
	public SettingsHandler getSettingsHandler() {
		return settingsHandler;
	}
	
	public DoubleJump getDoubleJump() {
		return doubleJump;
	}
	
	public CoreMain getApi() {
		return leeCore;
	}
	
	public HiderItem getHiderItem() {
		return hiderItem;
	}
	
	public static LeemonHub getInstance() {
		return instance;
	}
	
	public HologramsHandler getHologramsHandler() {
		return hologramsHandler;
	}
	
	public ShopArticlesHandler getShopArticlesHandler() {
		return shopArticlesHandler;
	}
	
	@Override
	public void onPluginMessageReceived(String arg0, Player arg1, byte[] arg2) {
		// TODO Auto-generated method stub
		
	}
	

}
