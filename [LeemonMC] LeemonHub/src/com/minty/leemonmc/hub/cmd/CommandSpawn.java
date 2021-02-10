package com.minty.leemonmc.hub.cmd;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.minty.leemonmc.hub.LeemonHub;

public class CommandSpawn implements CommandExecutor {
	
	private LeemonHub main;
	
	public CommandSpawn(LeemonHub _main) {
		this.main = _main;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {
		
		if(sender instanceof Player)
		{
			Player player = (Player) sender;
			
			int x = main.getConfig().getInt("spawn.x");
			int y = main.getConfig().getInt("spawn.y");
			int z = main.getConfig().getInt("spawn.z");
			int pitch = main.getConfig().getInt("spawn.pitch");
			int yaw = main.getConfig().getInt("spawn.yaw");
			
			Location loc = new Location(player.getWorld(), x, y, z, yaw, pitch);
			
			player.teleport(loc);
			player.sendMessage("§6§lLeemonMC §f» §7Téléportation au spawn !");
			
			return false;
			
		} else {
			sender.sendMessage("§cErreur: Cette commande peut seulement être exécutée par un joueur !");
			return false;
		}

	}

}
