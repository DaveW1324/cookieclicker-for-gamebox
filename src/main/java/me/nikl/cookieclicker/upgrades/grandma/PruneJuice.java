package me.nikl.cookieclicker.upgrades.grandma;

import me.nikl.cookieclicker.Game;
import me.nikl.cookieclicker.buildings.Buildings;
import me.nikl.cookieclicker.upgrades.Upgrade;
import me.nikl.cookieclicker.upgrades.UpgradeType;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

/**
 * Created by Niklas on 09.07.2017.
 */
public class PruneJuice extends Upgrade{

    public PruneJuice(Game game) {
        super(game, 44);
        this.cost = 5000000;
        productionsRequirements.put(Buildings.GRANDMA, 50);

        icon = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
        icon.setAmount(1);
        SkullMeta skullMeta = (SkullMeta) icon.getItemMeta();
        skullMeta.setOwner("MHF_Villager");
        icon.setItemMeta(skullMeta);

        loadLanguage(UpgradeType.CLASSIC, Buildings.GRANDMA);
    }

    @Override
    public void onActivation() {
        game.getBuilding(Buildings.GRANDMA).multiply(2);
        game.getBuilding(Buildings.GRANDMA).visualize(game.getInventory());
        active = true;
    }


}
