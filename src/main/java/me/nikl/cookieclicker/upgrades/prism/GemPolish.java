package me.nikl.cookieclicker.upgrades.prism;

import me.nikl.cookieclicker.Game;
import me.nikl.cookieclicker.buildings.Buildings;
import me.nikl.cookieclicker.upgrades.Upgrade;
import me.nikl.cookieclicker.upgrades.UpgradeType;

/**
 * Created by Niklas on 09.07.2017.
 *
 */
public class GemPolish extends Upgrade{

    public GemPolish(Game game) {
        super(game, 175);
        this.cost = 21000000000000000.;
        productionsRequirements.put(Buildings.PRISM, 1);

        // for the standard upgrade type the building icon is used
        loadLanguage(UpgradeType.CLASSIC, Buildings.PRISM);
    }

    @Override
    public void onActivation() {
        game.getBuilding(Buildings.PRISM).multiply(2);
        game.getBuilding(Buildings.PRISM).visualize(game.getInventory());
        active = true;
    }
}
