package com.zandor300.ultimatekits;

import com.zandor300.ultimatekits.commands.KitCommand;
import com.zandor300.ultimatekits.commands.UltimateKitsCommand;
import com.zandor300.ultimatekits.enums.ArmorSet;
import com.zandor300.ultimatekits.enums.Kit;
import com.zandor300.ultimatekits.inventories.KitMenu;
import com.zandor300.zsutilities.commandsystem.CommandManager;
import com.zandor300.zsutilities.utilities.Chat;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.mcstats.Metrics;

import java.io.IOException;
import java.util.HashMap;
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
		saveDefaultConfig();
		this.getConfig().options().copyDefaults(true);
		PluginManager pm = Bukkit.getPluginManager();

		chat.sendConsoleMessage("Starting metrics...");
		try {
			new Metrics(this).start();
			chat.sendConsoleMessage("Submitted stats to MCStats.org.");
		} catch (IOException e) {
			chat.sendConsoleMessage("Couldn't submit stats to MCStats.org...");
		}

		chat.sendConsoleMessage("Registering kits...");
		for(String kit : this.getConfig().getConfigurationSection("kits").getKeys(false)) {
			String name = kit;
			HashMap<Material, Integer> items = new HashMap<Material, Integer>();
			for(String item : this.getConfig().getConfigurationSection("kits." + kit + ".items").getKeys(false)) {
				String[] item1 = item.split(":");
				items.put(Material.getMaterial(Integer.valueOf(item1[0])), Integer.valueOf(item1[1]));
			}
			Material helmet = Material.getMaterial(this.getConfig().getInt("kits." + kit + ".helmet"));
			Material chestplate = Material.getMaterial(this.getConfig().getInt("kits." + kit + ".chestplate"));
			Material leggings = Material.getMaterial(this.getConfig().getInt("kits." + kit + ".leggings"));
			Material boots = Material.getMaterial(this.getConfig().getInt("kits." + kit + ".boots"));
			ArmorSet armor = new ArmorSet(helmet, chestplate, leggings, boots);
			int cooldown = this.getConfig().getInt("kits." + kit + ".cooldown");
			new Kit(name, items, armor, cooldown);
		}
		chat.sendConsoleMessage("Registered kits.");

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

		chat.sendConsoleMessage("Registering commands...");
		CommandManager cm = new CommandManager();
		cm.registerCommand(new KitCommand(), this);
		cm.registerCommand(new UltimateKitsCommand(), this);
		chat.sendConsoleMessage("Registered commands.");

		chat.sendConsoleMessage("Everything is setup!");
		chat.sendConsoleMessage("Enabled.");
	}
}
