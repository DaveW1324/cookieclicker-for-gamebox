package me.nikl.cookieclicker.upgrades.alchemylab;

import me.nikl.cookieclicker.Game;
import me.nikl.cookieclicker.buildings.Buildings;
import me.nikl.cookieclicker.upgrades.Upgrade;
import me.nikl.cookieclicker.upgrades.UpgradeType;
import org.bukkit.Material;
import org.bukkit.material.MaterialData;

/**
 * Created by Niklas on 09.07.2017.
 *
 */
public class Ambrosia extends Upgrade{

    public Ambrosia(Game game) {
        super(game, 49);
        this.cost = 3750000000000000.;
        productionsRequirements.put(Buildings.ALCHEMY_LAB, 50);

        loadLanguage(UpgradeType.CLASSIC, Buildings.ALCHEMY_LAB);
    }

    @Override
    public void onActivation() {
        game.getBuilding(Buildings.ALCHEMY_LAB).multiply(2);
        game.getBuilding(Buildings.ALCHEMY_LAB).visualize(game.getInventory());
        active = true;
    }


}
