package com.zandor300.ultimatekits.commands;

import com.zandor300.ultimatekits.UltimateKits;
import com.zandor300.zsutilities.commandsystem.Command;
import org.bukkit.command.CommandSender;

/**
 * Created by Zandor on 3/23/15.
 */
public class UltimateKitsCommand extends Command {

	public UltimateKitsCommand() {
		super("ultimatekits", "Show info.");
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		UltimateKits.getChat().sendMessage(sender, "UltimateKits 1.0.0 by Zandor300");
	}
}
