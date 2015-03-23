package com.zandor300.ultimatekits;

import com.zandor300.ultimatekits.enums.Kit;
import com.zandor300.ultimatekits.inventories.KitMenu;
import com.zandor300.zsutilities.utilities.Chat;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.mcstats.Metrics;

import java.io.IOException;
import java.util.Map;

/**
 * Created by Zandor on 3/23/15.
 */
public class UltimateKits extends JavaPlugin {

	private static Chat chat = new Chat("UltimateKits", ChatColor.DARK_PURPLE);
	private static UltimateKits plugin;

	public static Chat getChat() {
		return chat;
	}

	public static UltimateKits getPlugin() {
		return plugin;
	}

	@Override
	public void onEnable() {
		chat.sendConsoleMessage("Setting things up!");

		plugin = this;
		PluginManager pm = Bukkit.getPluginManager();

		chat.sendConsoleMessage("Starting metrics...");
		try {
			new Metrics(this).start();
			chat.sendConsoleMessage("Submitted stats to MCStats.org.");
		} catch (IOException e) {
			chat.sendConsoleMessage("Couldn't submit stats to MCStats.org...");
		}

		chat.sendConsoleMessage("Registering events...");
		pm.registerEvents(new KitMenu(), this);
		chat.sendConsoleMessage("Registered events.");

		chat.sendConsoleMessage("Starting timers...");
		Bukkit.getScheduler().runTaskTimerAsynchronously(this, new Runnable() {
			@Override
			public void run() {
				for(Kit kit : Kit.getAllKits())
					for (Map.Entry<String, Integer> entry : kit.getPlayerCooldown().entrySet()) {
						if (entry.getValue() <= 0)
							kit.getPlayerCooldown().remove(entry.getKey());
						else
							kit.getPlayerCooldown().put(entry.getKey(), entry.getValue() - 1);
					}
			}
		}, 20l, 20l);
		chat.sendConsoleMessage("Started timers.");

		chat.sendConsoleMessage("Everything is setup!");
		chat.sendConsoleMessage("Enabled.");
	}
}
