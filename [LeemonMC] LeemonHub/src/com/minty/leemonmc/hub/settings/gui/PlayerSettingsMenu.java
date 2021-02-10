package com.minty.leemonmc.hub.settings.gui;

import com.minty.leemonmc.core.util.GuiBuilder;
import com.minty.leemonmc.core.util.GuiUtils;
import com.minty.leemonmc.hub.LeemonHub;
import com.minty.leemonmc.hub.gui.profile.ProfileMenu;
import com.minty.leemonmc.hub.settings.global.FriendsRequestsSetting;
import com.minty.leemonmc.hub.settings.global.GenderSetting;
import com.minty.leemonmc.hub.settings.global.GroupInvitesSetting;
import com.minty.leemonmc.hub.settings.global.PrivateMessagesSetting;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class PlayerSettingsMenu implements GuiBuilder {

	private GuiUtils utils;
	private LeemonHub main;
	
	public PlayerSettingsMenu(GuiUtils utils, LeemonHub _main) {
		this.utils = utils;
		this.main = _main;
	}
	
	@Override
	public void contents(Player player, Inventory inv) {
		
		/* 
		 * REAL CONTENT (SETTINGS)
		 * */

		inv.setItem(21, main.getSettingsHandler().getSetting(new GroupInvitesSetting(main, main.getSettingsHandler()), player));
		inv.setItem(22, main.getSettingsHandler().getSetting(new GenderSetting(main, main.getSettingsHandler()), player));
		inv.setItem(23, main.getSettingsHandler().getSetting(new FriendsRequestsSetting(main, main.getSettingsHandler()), player));
		inv.setItem(31, main.getSettingsHandler().getSetting(new PrivateMessagesSetting(main, main.getSettingsHandler()), player));
		
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
		
		inv.setItem(4, utils.getProfileItem(player.getUniqueId().toString()));
	
	}

	@Override
	public int getSize() {
		return 54;
	}

	@Override
	public String name() {
		return "§6Profil » Options";
	}

	@Override
	public void onClick(Player player, Inventory inv, ItemStack it, int slot)
	{
		switch(it.getType()) {
			case BARRIER:
				player.closeInventory();
				break;
			case ARROW:
				player.closeInventory();
				LeemonHub.getInstance().getApi().getGuiManager().open(player, ProfileMenu.class);
				break;
			default:
				break;
		}
		
	}

	@Override
	public void onRightClick(Player arg0, Inventory arg1, ItemStack arg2, int arg3) {
		// TODO Auto-generated method stub
		
	}

}
