package me.nikl.cookieclicker.buildings;

import me.nikl.cookieclicker.Main;
import org.bukkit.Material;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;

/**
 * Created by Niklas on 05.07.2017.
 */
public class Shipment extends Building {

    public Shipment(Main plugin, int slot, Buildings building) {
        super(plugin, slot, building);

        icon = new MaterialData(Material.FIREWORK).toItemStack();
        icon.setAmount(1);
        ItemMeta meta = icon.getItemMeta();
        meta.setDisplayName(lang.GAME_BUILDING_NAME.replace("%name%", name));
        icon.setItemMeta(meta);

        this.productionPerSecond = 260000;
        this.baseCost = 5100000000.;
    }
}
