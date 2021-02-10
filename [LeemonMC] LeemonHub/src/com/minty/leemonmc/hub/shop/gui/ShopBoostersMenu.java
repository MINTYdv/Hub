package com.minty.leemonmc.hub.shop.gui;

import com.minty.leemonmc.core.util.GuiBuilder;
import com.minty.leemonmc.core.util.GuiUtils;
import com.minty.leemonmc.hub.LeemonHub;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class ShopBoostersMenu implements GuiBuilder {

	private GuiUtils utils;
	
	public ShopBoostersMenu(GuiUtils _utils) {
		this.utils = _utils;
	}
	
	@Override
	public void contents(Player player, Inventory inv) {

		/* 
		 * LORES
		 * */
		
		
		/* 
		 * DECORATION
		 * */
		
		utils.fillWithItem(utils.pane(), 0, 9, inv);
		
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
		
		inv.setItem(49, utils.cancelItem());
		inv.setItem(48, utils.backItem());
		
		/* 
		 * CONTENT
		 * */

	}
	
	@Override
	public int getSize()
	{
		return 54;
	}

	@Override
	public String name()
	{
		return "§6Boutique » Boosters";
	}

	@Override
	public void onClick(Player player, Inventory inv, ItemStack it, int slot)
	{
		switch(it.getType())
		{
			case BARRIER:
				player.closeInventory();
				break;
			case ARROW:
				LeemonHub.getInstance().leeCore.getGuiManager().open(player, ShopMenu.class);
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
