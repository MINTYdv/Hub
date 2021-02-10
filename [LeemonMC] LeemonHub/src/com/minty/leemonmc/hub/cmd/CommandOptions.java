package com.minty.leemonmc.hub.cmd;

import com.minty.leemonmc.hub.LeemonHub;
import com.minty.leemonmc.hub.settings.gui.PlayerSettingsMenu;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandOptions implements CommandExecutor {
	
	private LeemonHub main;
	
	public CommandOptions(LeemonHub _main) {
		this.main = _main;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {
		
		if(sender instanceof Player)
		{
			Player player = (Player) sender;
			
			main.leeCore.getGuiManager().open(player, PlayerSettingsMenu.class);
			
		} else {
			sender.sendMessage("§cErreur: Cette commande peut seulement être exécutée par un joueur !");
			return false;
		}
		
		return false;
	}

}
