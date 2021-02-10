package com.minty.leemonmc.hub.misc;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;

public class LaunchPad implements Listener {
	
	@EventHandler
	public void onMove(PlayerMoveEvent e)
	{
		Player player = e.getPlayer();
		
		if(e.getTo().getBlock().getRelative(BlockFace.DOWN).getType() == Material.SEA_LANTERN)
		{
            e.getPlayer().setVelocity(e.getPlayer().getLocation().getDirection().multiply(3));
            e.getPlayer().setVelocity(new Vector(e.getPlayer().getVelocity().getX(), 1.0D, e.getPlayer().getVelocity().getZ()));
            player.playSound(player.getLocation(), Sound.ENTITY_WITHER_SHOOT, 1f, 1f);
		}
	}
	
}
