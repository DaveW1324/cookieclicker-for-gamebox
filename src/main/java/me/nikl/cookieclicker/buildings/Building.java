package me.nikl.cookieclicker.buildings;

import me.nikl.cookieclicker.Language;
import me.nikl.cookieclicker.Main;
import me.nikl.gamebox.util.NumberUtil;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Niklas on 05.07.2017.
 */
public class Building {
    protected int count = 0;
    protected double baseCost = 0.;
    protected int slot;
    protected ItemStack icon;
    protected double productionPerSecond = 0.;
    protected double multiplier = 1.;
    protected double otherBuildingsBonus = 0.;
    protected List<String > lore;

    protected String name;

    protected Language lang;

    public Building(Main plugin, int slot, Buildings building){
        this.lang = plugin.lang;
        this.name = lang.buildingName.get(building);

        lore = new ArrayList<>();
        for(String line : lang.buildingLore.get(building)){
            lore.add(line.replace("%name%", name));
        }

        if(slot < 0 || slot > 53){
            slot = 0;
        }

        this.slot = slot;
    }

    public double getAllInAllProductionPerSecond(){
        return  getProductionPerSecondPerItem() * count;
    }

    public void addProductions(int amount){
        this.count += amount;
    }

    /***
     * Add a multiplier
     *
     * 0.5 => 50%
     * 1 => 100%
     * @param toAdd production to add to multiplier
     */
    public void addMultiplier(double toAdd) {
        this.multiplier += toAdd;
    }

    /**
     * Multiply the current production
     *
     * The current multiplier is multiplied by the
     * given factor
     * @param multiplier
     */
    public void multiply(double multiplier){
        this.multiplier = this.multiplier * multiplier;
    }

    public int getCount() {
        return count;
    }

    /**
     * Calculate and return the cost for the next building
     * @return
     */
    public double getCost() {
        return (baseCost * Math.pow(1.15, count));
    }

    public double getProductionPerSecondPerItem() {
        return productionPerSecond * multiplier + otherBuildingsBonus;
    }

    public void visualize(Inventory inventory){
        if(icon == null || lore == null) return;
        List<String> newLore = new ArrayList<>();
        for (String line : lore) {
            newLore.add(line.replace("%count%", String.valueOf(count))
                    .replace("%cost%", NumberUtil.convertHugeNumber(getCost()))
                    .replace("%cookies_per_second_per_item%", NumberUtil.convertHugeNumber(getProductionPerSecondPerItem()))
                    .replace("%cookies_per_second%", NumberUtil.convertHugeNumber(getAllInAllProductionPerSecond())));
        }
        ItemMeta meta = icon.getItemMeta();
        meta.setLore(newLore);
        icon.setItemMeta(meta);

        inventory.setItem(slot, icon);
    }

    public void setOtherBuildingsBonus(double bonus){
        this.otherBuildingsBonus = bonus;
    }

    public ItemStack getIcon(){
        return icon.clone();
    }
}
