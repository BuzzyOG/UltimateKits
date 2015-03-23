package com.zandor300.ultimatekits.enums;

import org.bukkit.Material;

/**
 * Created by Zandor on 3/23/15.
 */
public class ArmorSet {

	private final Material helmet;
	private final Material chestplate;
	private final Material leggings;
	private final Material boots;

	public ArmorSet(Material helmet, Material chestplate, Material leggings, Material boots) {
		this.helmet = helmet;
		this.chestplate = chestplate;
		this.leggings = leggings;
		this.boots = boots;
	}

	public Material getHelmet() {
		return helmet;
	}

	public Material getChestplate() {
		return chestplate;
	}

	public Material getLeggings() {
		return leggings;
	}

	public Material getBoots() {
		return boots;
	}
}
