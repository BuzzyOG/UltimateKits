package com.zandor300.ultimatekits.inventories;

import com.zandor300.ultimatekits.UltimateKits;
import com.zandor300.ultimatekits.enums.Kit;
import com.zandor300.zsutilities.inventorysystem.Item;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitTask;

import java.util.Arrays;
import java.util.HashMap;

/**
 * Created by Zandor on 3/23/15.
 */
public class KitMenu implements Listener {

	private static HashMap<String, BukkitTask> playerTask = new HashMap<String, BukkitTask>();

	public static void open(final Player player) {
		final Inventory inventory = Bukkit.createInventory(null, 54, ChatColor.DARK_PURPLE + "UltimateKits");//new Inventory(ChatColor.DARK_PURPLE + "UltimateKits", 54);
		playerTask.put(player.getName(), Bukkit.getScheduler().runTaskTimer(UltimateKits.getPlugin(), new Runnable() {
			@Override
			public void run() {
				int i = 0;
				for(Kit kit : Kit.getAllKits()) {
					if(kit.getPlayerCooldown().containsKey(player.getName())) {
						ItemStack item = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 14);
						ItemMeta meta = item.getItemMeta();
						meta.setDisplayName(ChatColor.RED + kit.getName());
						meta.setLore(Arrays.asList("", ChatColor.GREEN + "Cooldown will expire in " +
								kit.getPlayerCooldown().get(player.getName()) + " seconds"));
						item.setItemMeta(meta);
						inventory.setItem(i, item);
					} else {
						ItemStack item = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 13);
						ItemMeta meta = item.getItemMeta();
						meta.setDisplayName(ChatColor.GREEN + kit.getName());
						item.setItemMeta(meta);
						inventory.setItem(i, item);
					}
					i++;
				}
			}
		}, 0l, 20l));
		player.openInventory(inventory);
	}

	@EventHandler
	public void onInventoryClose(InventoryCloseEvent event) {
		if (!event.getInventory().getName().equalsIgnoreCase(ChatColor.DARK_PURPLE + "UltimateKits"))
			return;

		BukkitTask task = playerTask.get(event.getPlayer().getName());
		if (task != null) {
			task.cancel();
			playerTask.remove(event.getPlayer().getName());
		}
	}

	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		if (!event.getInventory().getName().equalsIgnoreCase(ChatColor.DARK_PURPLE + "UltimateKits"))
			return;
		event.setCancelled(true);

		if(event.getCurrentItem() == null || event.getCurrentItem().getType() == null || event.getCurrentItem().getType().equals(Material.AIR))
			return;

		Player player = (Player) event.getWhoClicked();
		String name = event.getCurrentItem().getItemMeta().getDisplayName().replaceAll(ChatColor.RED + "", "")
				.replaceAll(ChatColor.GREEN + "", "");
		Kit kit = Kit.getKit(name);
		if(kit == null)
			return;

		if(kit.getPlayerCooldown().containsKey(player.getName())) {
			player.playSound(player.getLocation(), Sound.NOTE_BASS, 1, 1);
			return;
		}

		kit.giveKit(player);
		player.playSound(player.getLocation(), Sound.ORB_PICKUP, 1, 1);
	}
}
