package com.minty.leemonmc.hub.gui.social;

import com.minty.leemonmc.core.CoreMain;
import com.minty.leemonmc.core.util.GuiBuilder;
import com.minty.leemonmc.core.util.GuiUtils;
import com.minty.leemonmc.hub.gui.profile.ProfileMenu;
import com.minty.leemonmc.social.core.Party;
import com.minty.leemonmc.social.core.PartyHandler;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GroupMenu implements GuiBuilder {

	private GuiUtils utils;
	private Map<Integer, PartyItem> partyItems = new HashMap<>();
	private List<Integer> slots = new ArrayList<Integer>();
	
	public GroupMenu(GuiUtils utils) {
		this.utils = utils;
	}
	
	@Override
	public void contents(Player player, Inventory inv) {
		
		String UUID = player.getUniqueId().toString();
		Party party = PartyHandler.getPartyOfPlayer(UUID);
		
		/* 
		 * Init
		 * */
		
		slots.add(10);
		slots.add(11);
		slots.add(12);
		slots.add(13);
		slots.add(14);
		slots.add(15);
		slots.add(16);
		
		slots.add(10 * 2);
		slots.add(11 * 2);
		slots.add(12 * 2);
		slots.add(13 * 2);
		slots.add(14 * 2);
		slots.add(15 * 2);
		slots.add(16 * 2);
		
		slots.add(10 * 3);
		slots.add(11 * 3);
		slots.add(12 * 3);
		slots.add(13 * 3);
		slots.add(14 * 3);
		slots.add(15 * 3);
		slots.add(16 * 3);
		
		/* 
		 * LORES
		 * */

		
		/* 
		 * DECORATION
		 * */
		
		utils.fillWithItem(utils.pane(), 0, 3, inv);
		utils.fillWithItem(utils.pane(), 6, 9, inv);
		
		inv.setItem(9, utils.pane());
		inv.setItem(9 * 2, utils.pane());
		inv.setItem(9 * 3, utils.pane());
		inv.setItem(9 * 4, utils.pane());
		inv.setItem(9 * 5, utils.pane());
		
		inv.setItem(8 * 2 + 1, utils.pane());
		
		inv.setItem(8 * 3 + 2, utils.pane());
		inv.setItem(8 * 4 + 3, utils.pane());
		
		inv.setItem(8 * 5 + 4, utils.pane());
		
		utils.fillWithItem(utils.pane(), 45, 48, inv);
		utils.fillWithItem(utils.pane(), 51, 54, inv);
		
		/* 
		 * CONTENT
		 * */
		
		inv.setItem(49, utils.cancelItem());
		inv.setItem(48, utils.backItem());
		
		if(party == null)
		{
			inv.setItem(22, getVoidItem());
			return;
		}
		
		inv.setItem(4, getPartyItem(party));
		
		for(int i = 0; i < party.getPlayers().size(); i++)
		{
			int slot = slots.get(i);
			String member = party.getPlayers().get(i);

			inv.setItem(slot, getPartyPlayerItem(player, member, party));
			partyItems.put(slot, new PartyItem(member, party));
		}

	}
	
	@SuppressWarnings("static-access")
	private ItemStack getPartyPlayerItem(Player player, String member, Party party)
	{
		String username = CoreMain.getInstance().getAccountManager().UUIDtoUsername(member);
		ItemStack it = utils.getHead(username);
		ItemMeta meta = it.getItemMeta();
		
		boolean isOwner = party.getOwner().equalsIgnoreCase(member);
		if(!isOwner) {
			meta.setDisplayName("§6" + username);
		} else {
			meta.setDisplayName("§6" + username + " §6§lCHEF");
		}
		List<String> lore = new ArrayList<>();
		
		if(party.getOwner().equalsIgnoreCase(player.getUniqueId().toString()) && !party.getOwner().equalsIgnoreCase(member))
		{
			lore.add("");
			lore.add("§6» §eClic-Gauche : §cExpulser du groupe");
			lore.add("§6» §eClic-Droit : §dNommer chef du groupe");
		}
		if(member.equalsIgnoreCase(player.getUniqueId().toString())) {
			lore.add("");
			lore.add("§7§oC'est toi ;D");
		}
		lore.add("");
		meta.setLore(lore);
		
		it.setItemMeta(meta);
		return it;
	}
	
	@SuppressWarnings("static-access")
	private ItemStack getPartyItem(Party party)
	{
		String ownername = CoreMain.getInstance().getAccountManager().UUIDtoUsername(party.getOwner());
		ItemStack it = utils.getHead(ownername);
		ItemMeta meta = it.getItemMeta();
		
		meta.setDisplayName("§6§nGroupe de " + ownername);
		List<String> lore = new ArrayList<>();
		lore.add("");
		lore.add("§8- §7Membres : §e" + party.getPlayers().size());
		lore.add("§8- §7Chef : §6" + ownername);
		lore.add("");
		lore.add("§e/g invite <joueur> §7pour inviter !");
		lore.add("");
		meta.setLore(lore);
		
		it.setItemMeta(meta);
		return it;
	}
	
	private ItemStack getVoidItem()
	{
		List<String> lore = new ArrayList<>();
		lore.add("");
		lore.add("§7Pour §eaccéder à ce menu§7, crée un groupe");
		lore.add("§7en y §einvitant tes amis §7avec la commande");
		lore.add("§e/g invite <joueur> §7 !");
		lore.add("");
		ItemStack it = utils.createItem(Material.MINECART, "§cIl n'y a rien par ici... :(", (byte) 0, lore);
		return it;
	}
	
	@Override
	public int getSize() {
		return 54;
	}

	@Override
	public String name() {
		return "§6Profil » Groupe";
	}

	@Override
	public void onClick(Player player, Inventory inv, ItemStack it, int slot)
	{
		switch(it.getType()) {
		case ARROW:
			CoreMain.getInstance().getGuiManager().open(player, ProfileMenu.class);
			break;
		case BARRIER:
			player.closeInventory();
			break;
			default:
				break;
		}
		
		if(partyItems.containsKey(slot))
		{
			PartyItem item = partyItems.get(slot);
			if(item.getParty().getOwner().equalsIgnoreCase(player.getUniqueId().toString()))
			{
				String username = CoreMain.getInstance().getAccountManager().UUIDtoUsername(item.getPlayerUUID());
				PartyHandler.removePlayerFromParty(player.getUniqueId().toString(), item.getPlayerUUID());
				CoreMain.getInstance().getGuiManager().open(player, GroupMenu.class);
			}
		}
	}

	@Override
	public void onRightClick(Player player, Inventory inv, ItemStack it, int slot) {
		
		if(partyItems.containsKey(slot))
		{
			PartyItem item = partyItems.get(slot);
			if(item.getParty().getOwner().equalsIgnoreCase(player.getUniqueId().toString()))
			{
				String username = CoreMain.getInstance().getAccountManager().UUIDtoUsername(item.getPlayerUUID());
				PartyHandler.setOwner(player.getUniqueId().toString(), item.getPlayerUUID());
				CoreMain.getInstance().getGuiManager().open(player, GroupMenu.class);
			}
		}
		
	}

}
