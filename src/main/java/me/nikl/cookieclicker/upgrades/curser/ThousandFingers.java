package me.nikl.cookieclicker.upgrades.curser;

import me.nikl.cookieclicker.CCGame;
import me.nikl.cookieclicker.CookieClicker;
import me.nikl.cookieclicker.buildings.Buildings;
import me.nikl.cookieclicker.upgrades.Upgrade;
import me.nikl.cookieclicker.upgrades.UpgradeType;
import org.bukkit.Material;
import org.bukkit.material.MaterialData;

/**
 * @author Niklas Eicker
 */
public class ThousandFingers extends Upgrade {

    public ThousandFingers(CookieClicker game) {
        super(game, 3);
        this.cost = 100000;
        productionsRequirements.put(Buildings.CURSOR, 25);

        icon = new MaterialData(Material.ARROW).toItemStack();
        icon.setAmount(1);

        gain = "+0.1";
        loadLanguage(UpgradeType.GAIN_MOUSE_AND_OTHER, Buildings.CURSOR);
    }

    @Override
    public void onActivation(CCGame game) {
        for (Buildings buildings : Buildings.values()) {
            if (buildings == Buildings.CURSOR) continue;
            game.addBuildingBonus(Buildings.CURSOR, buildings, 0.1);
            game.addClickBonus(buildings, 0.1);
        }
    }


}
