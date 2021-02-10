package com.minty.leemonmc.hub.cmd;

import com.minty.leemonmc.basics.core.Rank;
import com.minty.leemonmc.basics.core.cache.Account;
import com.minty.leemonmc.hub.LeemonHub;
import com.mysql.jdbc.StringUtils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandVipPrefix implements CommandExecutor {
	
	private LeemonHub main;
	
	public CommandVipPrefix(LeemonHub _main) {
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
				player.sendMessage("§6§lLeemonMC §f» §cUtilisation : /prefix <message>");
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
			
			
			if(content.contains("&4"))
			{
				player.sendMessage("§6§lLeemonMC §f» §cVotre préfixe personnalisé ne peut pas contenir \"&4\" !");
				return false;
			}
			
			if(content.contains("&k"))
			{
				player.sendMessage("§6§lLeemonMC §f» §cVotre préfixe personnalisé ne peut pas contenir \"&k\" !");
				return false;
			}
			
			if(content.contains("&l"))
			{
				player.sendMessage("§6§lLeemonMC §f» §cVotre préfixe personnalisé ne peut pas contenir \"&l\" !");
				return false;
			}
			
			if(content.length() >= 16)
			{
				player.sendMessage("§6§lLeemonMC §f» §cVotre préfixe est trop long ! (" + content.length() + " >= 16)");
				return false;
			}
			
			for(Rank rank : Rank.values())
			{
				String cnt = ChatColor.stripColor(content.replace("&", "§")).replaceAll("\\s+","");
				if(ChatColor.stripColor(rank.getdisplayChatMen()).replaceAll("\\s+","").equalsIgnoreCase(cnt) || ChatColor.stripColor(rank.getdisplayChatWomen()).replaceAll("\\s+","").equalsIgnoreCase(cnt))
				{
					player.sendMessage("§6§lLeemonMC §f» §cVotre préfixe ne peut pas être celui d'un autre grade !");
					return false;
				}
			}

			if(content.contains("?") || content.contains("!") || content.contains("/")  || content.contains("^"))
			{
				player.sendMessage("§6§lLeemonMC §f» §cVotre préfixe contient des caractères qui ne sont pas alphanumériques !");
				return false;
			}
			
			account.setSetting("vip_prefix", content.replaceAll("\\s+",""));
			player.sendMessage("§6§lLeemonMC §f» §7Votre §6préfixe §7a bien été modifié ! Pour qu'il prenne effet, merci de vous déconnecter puis reconnecter !");
			
			return false;
			
		} else {
			sender.sendMessage("§cErreur: Cette commande peut seulement être exécutée par un joueur !");
			return false;
		}

	}

}
