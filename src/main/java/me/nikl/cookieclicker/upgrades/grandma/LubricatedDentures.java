package me.nikl.cookieclicker.upgrades.grandma;

import me.nikl.cookieclicker.CCGame;
import me.nikl.cookieclicker.buildings.Buildings;
import me.nikl.cookieclicker.upgrades.Upgrade;
import me.nikl.cookieclicker.upgrades.UpgradeType;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

/**
 * @author Niklas Eicker
 */
public class LubricatedDentures extends Upgrade {

    public LubricatedDentures(CCGame game) {
        super(game, 9);
        this.cost = 50000;
        productionsRequirements.put(Buildings.GRANDMA, 25);

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
