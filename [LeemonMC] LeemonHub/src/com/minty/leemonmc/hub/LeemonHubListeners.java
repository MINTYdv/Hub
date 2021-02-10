package com.minty.leemonmc.hub;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerArmorStandManipulateEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerGameModeChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.minty.leemonmc.basics.core.ServerGroup;
import com.minty.leemonmc.hub.gui.play.PlayMenu;
import com.minty.leemonmc.hub.gui.play.PlayServersMenu;
import com.minty.leemonmc.hub.gui.profile.ProfileMenu;
import com.minty.leemonmc.hub.shop.gui.ShopMenu;

public class LeemonHubListeners implements Listener {

	public LeemonHub main;

	
	public LeemonHubListeners(LeemonHub _main) {
		main = _main;
	}
	
	@EventHandler
	public void onOffhand(PlayerSwapHandItemsEvent e)
	{
		e.setCancelled(true);
	}
	
	@EventHandler
	public void onDamage(EntityDamageEvent e)
	{
		if(e.getEntityType() == EntityType.ARMOR_STAND) {
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onArmorStand(PlayerArmorStandManipulateEvent e)
	{
		e.setCancelled(true);
	}
	
	@EventHandler
	public void onGmChange(PlayerGameModeChangeEvent e) {
		Player player = e.getPlayer();
		
		if(e.getNewGameMode() == GameMode.CREATIVE || e.getNewGameMode() == GameMode.SPECTATOR) {
			player.setAllowFlight(true);
		}
	}
	
	// HANDLE MENUS
	@EventHandler
	public void OnInteract(PlayerInteractEvent e)
	{
		Player player = e.getPlayer();
		Action action = e.getAction();
		ItemStack it = e.getItem();
		
		if(player == null) return;
		if(action == null) return;
		
		if(action == Action.RIGHT_CLICK_BLOCK)
		{
			if(player.getGameMode() == GameMode.ADVENTURE) {
				
				e.setCancelled(true);
			}
		}
		
		if(it == null) return;
		
		ItemMeta meta = it.getItemMeta();
		
		if(meta == null) return;
		if(action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK || action == Action.LEFT_CLICK_AIR)
		{
			if(meta.getDisplayName() == null || meta.getDisplayName().isEmpty()) return;
			
			// Rightclick on "Jouer"
			if(meta.getDisplayName().equalsIgnoreCase(main.getConfigItemName("play")))
			{
				main.getApi().getGuiManager().open(player, PlayMenu.class);
			}
			
			// Rightclick on "Boutique"
			if(meta.getDisplayName().equalsIgnoreCase(main.getConfigItemName("shop")))
			{
				main.getApi().getGuiManager().open(player, ShopMenu.class);
			}
			
			// Rightclick on "Changement de Lobby"
			if(meta.getDisplayName().equalsIgnoreCase(main.getConfigItemName("selector")))
			{
				openServersMenu(player, ServerGroup.lobby);
			}
			
			// Rightclick on "Profile"
			if(meta.getDisplayName().equalsIgnoreCase(main.getConfigItemName("profile")))
			{
				main.getApi().getGuiManager().open(player, ProfileMenu.class);
			}
		}
		
	}
	
	public void openServersMenu(Player player, ServerGroup group)
	{
		PlayServersMenu gui = new PlayServersMenu(null, main, group);
		main.getApi().getGuiManager().addMenu(gui);
		main.getApi().getGuiManager().open(player, gui.getClass());
	}
	
	// Stop players from falling
	@EventHandler
	public void OnMove(PlayerMoveEvent e) {
		Player player = e.getPlayer();
		
		if(player.getLocation().getY() <= 0)
		{
			Location loc = new Location(player.getWorld(), -32, 143, 1, -1, 1);
			
			player.teleport(loc);
		}
	}
	
	// Block block breaking in the hub
	@EventHandler
	public void OnBreak(BlockBreakEvent e) {
		Player player = e.getPlayer();
		
		if(player.getGameMode() == GameMode.ADVENTURE) {
			e.setCancelled(true);
		}
	}
	
	// Block damage in the hub
	@EventHandler
	public void OnDamage(EntityDamageEvent e)
	{
	
        if (e.getEntity() instanceof Player){
        	Player player = (Player) e.getEntity();
        	
        	if(player.getGameMode() == GameMode.ADVENTURE) {
                e.setCancelled(true);
        	}

        }
        
	}
	
	// Block block placement in the hub
	@EventHandler
	public void OnPlace(BlockPlaceEvent e)
	{
		Player player = e.getPlayer();
		
		if(player.getGameMode() == GameMode.ADVENTURE)
		{
			e.setCancelled(true);
		}
		
	}
	
	// Remove the quit message
	@EventHandler
	public void OnQuit(PlayerQuitEvent e)
	{
		e.setQuitMessage(null);
	}
	
	// Block item drop in the hub
	@EventHandler
	public void OnDrop(PlayerDropItemEvent e) {
		Player player = e.getPlayer();
		if(player.getGameMode() == GameMode.ADVENTURE)
		{
			e.setCancelled(true);
		}
	}
	
	// Block hunger in the hub
	@EventHandler
	public void OnHungerLoss(FoodLevelChangeEvent e)
	{
		e.setCancelled(true);
	}
	
	// Block weather changing in the hub
	@EventHandler
	public void OnWeatherChange(WeatherChangeEvent e)
	{
		e.setCancelled(true);
	}
	
	// Block item position changing in the hub
	@EventHandler
	public void OnItemMove(InventoryClickEvent e)
	{
		Player player = (Player) e.getWhoClicked();
		if(player == null) return;
		if(player.getGameMode() == GameMode.ADVENTURE)
		{
			e.setCancelled(true);
		}

	}
	
}
