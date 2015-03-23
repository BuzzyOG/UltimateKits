package com.zandor300.ultimatekits.enums;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Zandor on 3/23/15.
 */
public class Kit {

	private static ArrayList<Kit> allKits = new ArrayList<Kit>();
	private final HashMap<String, Integer> playerCooldown = new HashMap<String, Integer>();

	private final String name;
	private final HashMap<Material, Integer> items;
	private final ArmorSet armor;
	private final int cooldown;

	public Kit(String name, HashMap<Material, Integer> items, ArmorSet armor, int cooldown) {
		this.name = name;
		this.items = items;
		this.armor = armor;
		this.cooldown = cooldown;

		allKits.add(this);
	}

	public static ArrayList<Kit> getAllKits() {
		return allKits;
	}

	public HashMap<String, Integer> getPlayerCooldown() {
		return playerCooldown;
	}

	public static Kit getKit(String name) {
		for (Kit kit : allKits)
			if (kit.getName().equalsIgnoreCase(name))
				return kit;
		return null;
	}

	public int getCooldown(Player player) {
		return playerCooldown.get(player.getName());
	}

	public String getName() {
		return name;
	}

	public HashMap<Material, Integer> getItems() {
		return items;
	}

	public ArmorSet getArmor() {
		return armor;
	}

	public void giveKit(Player player) {
		playerCooldown.put(player.getName(), cooldown);

		PlayerInventory inventory = player.getInventory();
		int i = 0;
		for (Map.Entry<Material, Integer> entry : items.entrySet()) {
			for (int a = 0; a < entry.getValue(); a++)
				inventory.setItem(i, new ItemStack(entry.getKey(), a));
			i++;
		}

		inventory.setHelmet(new ItemStack(armor.getHelmet()));
		inventory.setChestplate(new ItemStack(armor.getChestplate()));
		inventory.setLeggings(new ItemStack(armor.getLeggings()));
		inventory.setBoots(new ItemStack(armor.getBoots()));
	}
}
