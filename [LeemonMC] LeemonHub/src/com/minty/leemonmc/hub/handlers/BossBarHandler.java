package com.minty.leemonmc.hub.handlers;

import com.minty.leemonmc.hub.LeemonHub;
import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class BossBarHandler {

	public BossBar hubBar;
	private LeemonHub main;
	
	public BossBarHandler(LeemonHub main) {
		this.main = main;
	}
	
	public void setupBossBar()
	{
		System.out.println("boss bar setup");
		List<String> messages = new ArrayList<>();
        messages.add("§fBienvenue sur §e§lLeemonMC §f!");
        messages.add("§fLe serveur est en §cbêta§f! §fMerci de signaler les bugs!");
        messages.add("§aBesoin d'aide? §fLes modérateurs sont là pour vous aider !");
        messages.add("§fUn tricheur? Utilisez le §c/report");
        messages.add("§fRejoignez notre §bServeur Discord §f! §b§l/discord");
        messages.add("§fPour soutenir le serveur, rendez-vous sur la §dboutique §f!");
        messages.add("§fNews, Forum & Boutique sur §ewww.LeemonMC.fr §f!");
        
        List<BarColor> colors = new ArrayList<>();
        colors.add(BarColor.YELLOW);
        colors.add(BarColor.YELLOW);
        colors.add(BarColor.GREEN);
        colors.add(BarColor.RED);
        colors.add(BarColor.BLUE);
        colors.add(BarColor.PURPLE);
        colors.add(BarColor.WHITE);
        
        hubBar = Bukkit.createBossBar("§fBienvenue sur §e§lLeemonMC §f!", BarColor.YELLOW, BarStyle.SEGMENTED_6, new BarFlag[0]);
        
        for(Player players : Bukkit.getOnlinePlayers())
        {
        	hubBar.addPlayer(players);
        }
        
        new BukkitRunnable() {

        	double d = 0.0;
        	int i = 0;
        	boolean goingBack = false;
        	
			@Override
			public void run() {
				if(goingBack == false) {
					d += 0.01;
				} else {
					d -= 0.01;
				}
				
				if(d > 1) {
					goingBack = true;
					d = 1.0;
					i++;
					
					if(i == messages.size()) {
						i = 0;
					}
				}
				
				if(d < 0) {
					goingBack = false;
					d = 0;
					
					i++;
					
					if(i == messages.size()) {
						i = 0;
					}
				}
				
				hubBar.setTitle(messages.get(i));
				hubBar.setColor(colors.get(i));
				
				hubBar.setProgress(d);
				
		        for(Player players : Bukkit.getOnlinePlayers())
		        {
		        	hubBar.addPlayer(players);
		        }
			}
        	
        }.runTaskTimer(main, 1, 1);
	}
	
}
