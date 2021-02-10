package com.minty.leemonmc.hub.misc;

import com.minty.leemonmc.hub.LeemonHub;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleFlightEvent;

import java.util.HashMap;
import java.util.Map;

public class DoubleJump implements Listener
{

	private LeemonHub main;
	
	private Map<String, Boolean> cooldownStates = new HashMap<>();
	
	public DoubleJump(LeemonHub _main)
	{
		main = _main;
	}
	
    @EventHandler
    public void setVelocity(PlayerToggleFlightEvent e) {
     
    	Player player = e.getPlayer();
    	
		if(main.getSettingsHandler().getSelectedoptionofPlayer(player, main.getSettingsHandler().stringToSetting("hub_movement")).getNameID() == "FLY")
		{
			return;
		}
    	
        if (player.getGameMode() == GameMode.CREATIVE || player.getGameMode() == GameMode.SPECTATOR || player.isFlying()) {
         
            e.setCancelled(false);	
            return;
         
        } else {
            e.setCancelled(true);
        	if(main.getSettingsHandler().getSelectedoptionofPlayer(player, main.getSettingsHandler().stringToSetting("hub_movement")).getNameID() == "DOUBLE_JUMP")
        	{
                player.setAllowFlight(false);
                player.setFlying(false);
             
                player.setVelocity(e.getPlayer().getLocation().getDirection().multiply(1.5).setY(1));
                player.playSound(player.getLocation(), Sound.ENTITY_SLIME_SQUISH, 1f, 1f);
                
                player.setAllowFlight(true);
        	}

        }

    }
	
	public Map<String, Boolean> getCooldownStates() {
		return cooldownStates;
	}
	
}
