package me.nikl.cookieclicker.upgrades.farm;

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
public class PulsarSprinklers extends Upgrade{

    public PulsarSprinklers(Game game) {
        super(game, 193);
        this.cost = 550000000000.;
        productionsRequirements.put(Buildings.FARM, 150);

        icon = new MaterialData(Material.DIRT).toItemStack();
        icon.setAmount(1);

        loadLanguage(UpgradeType.CLASSIC, Buildings.FARM);
    }

    @Override
    public void onActivation() {
        game.getBuilding(Buildings.FARM).multiply(2);
        game.getBuilding(Buildings.FARM).visualize(game.getInventory());
        active = true;
    }


}
