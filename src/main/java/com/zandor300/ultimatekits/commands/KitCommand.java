package com.zandor300.ultimatekits.commands;

import com.zandor300.ultimatekits.UltimateKits;
import com.zandor300.ultimatekits.inventories.KitMenu;
import com.zandor300.zsutilities.commandsystem.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Zandor on 3/23/15.
 */
public class KitCommand extends Command {

	public KitCommand() {
		super("kit", "Open the kit gui.");
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		if(!(sender instanceof Player)) {
			UltimateKits.getChat().sendConsoleMessage("You have to be a player.");
			return;
		}
		KitMenu.open((Player) sender);
	}
}
