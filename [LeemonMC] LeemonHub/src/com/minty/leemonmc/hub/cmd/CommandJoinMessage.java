package com.minty.leemonmc.hub.cmd;

import com.minty.leemonmc.basics.core.Rank;
import com.minty.leemonmc.basics.core.cache.Account;
import com.minty.leemonmc.hub.LeemonHub;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandJoinMessage implements CommandExecutor {
	
	private LeemonHub main;
	
	public CommandJoinMessage(LeemonHub _main) {
		this.main = _main;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {
		
		if(sender instanceof Player)
		{
			Player player = (Player) sender;
			String UUID = player.getUniqueId().toString();
			Account account = main.getApi().getAccountManager().getAccount(UUID);
			
			if(account.getRank().getPower() < Rank.CUSTOM.getPower())
			{
				player.sendMessage("§c§m---------------------------------------");
				player.sendMessage("");
				player.sendMessage("§f§l➜  §eTu n'as pas le grade nécessaire pour");
				player.sendMessage("      §emodifier cette option !");
				player.sendMessage("");
				player.sendMessage("§f§l➜  §eUn meilleur grade ?");
				player.sendMessage("      §6https://store.leemonmc.fr");
				player.sendMessage("");
				player.sendMessage("§c§m---------------------------------------");
				return false;
			}
			
			if(args.length < 1)
			{
				player.sendMessage("§6§lLeemonMC §f» §cUtilisation : /joinmsg <message>");
				player.sendMessage("§6Tip : §7Vous pouvez également utiliser des codes couleurs !");
				return false;
			}
			
			String content = "";
			for(String arg : args) {
				content += arg + " ";
			}
			
			if(account.getSetting("join_message").equalsIgnoreCase(content))
			{
				player.sendMessage("§6§lLeemonMC §f» §cVotre message de connexion est déjà défini sur ce paramètre !");
				return false;
			}
			
			account.setSetting("join_message", content);
			player.sendMessage("§6§lLeemonMC §f» §7Votre §6message de connexion §7a bien été modifié ! Pour qu'il prenne effet, merci de vous déconnecter puis reconnecter !");
			
			return false;
			
		} else {
			sender.sendMessage("§cErreur: Cette commande peut seulement être exécutée par un joueur !");
			return false;
		}

	}

}
