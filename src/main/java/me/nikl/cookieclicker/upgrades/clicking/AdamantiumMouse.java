package me.nikl.cookieclicker.upgrades.clicking;

import me.nikl.cookieclicker.Game;
import me.nikl.cookieclicker.upgrades.Upgrade;
import me.nikl.cookieclicker.upgrades.UpgradeType;
import org.bukkit.Material;
import org.bukkit.material.MaterialData;

/**
 * Created by Niklas on 09.07.2017.
 *
 */
public class AdamantiumMouse extends Upgrade{

    public AdamantiumMouse(Game game) {
        super(game, 78);
        this.cost = 50000000000.;
        setClickCookieReq(1000000000.);

        icon = new MaterialData(Material.ARROW).toItemStack();
        icon.setAmount(1);

        gain = "+1%";
        loadLanguage(UpgradeType.GAIN_MOUSE_PER_CPS);
    }

    @Override
    public void onActivation() {
        game.cookiesPerClickPerCPS += 0.01;
        active = true;
    }


}
