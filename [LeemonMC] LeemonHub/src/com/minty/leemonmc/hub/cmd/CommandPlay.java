package com.minty.leemonmc.hub.cmd;

import com.minty.leemonmc.hub.LeemonHub;
import com.minty.leemonmc.hub.gui.play.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandPlay implements CommandExecutor {
	
	private LeemonHub main;
	
	public CommandPlay(LeemonHub _main) {
		this.main = _main;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {
		
		if(sender instanceof Player)
		{
			Player player = (Player) sender;

			if(args.length == 1)
			{
				if(args[0].equalsIgnoreCase("gemsdefender"))
				{
					main.getApi().getGuiManager().open(player, PlayGemsMenu.class);
					return false;
				}
				
				if(args[0].equalsIgnoreCase("skyrush"))
				{
					main.getApi().getGuiManager().open(player, PlaySkyRushMenu.class);
					return false;
				}
				
				if(args[0].equalsIgnoreCase("ffaskywars"))
				{
					main.getApi().getGuiManager().open(player, PlayFFASkyMenu.class);
					return false;
				}
				
				if(args[0].equalsIgnoreCase("underground"))
				{
					main.getApi().getGuiManager().open(player, PlayUndergroundMenu.class);
					return false;
				}
				
			}
			
			main.getApi().getGuiManager().open(player, PlayMenu.class);
			return false;
			
		} else {
			sender.sendMessage("§cErreur: Cette commande peut seulement être exécutée par un joueur !");
			return false;
		}

	}

}
