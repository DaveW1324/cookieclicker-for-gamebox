package me.nikl.cookieclicker;

import me.nikl.gamebox.GameBox;
import me.nikl.gamebox.Permissions;
import me.nikl.gamebox.data.SaveType;
import me.nikl.gamebox.data.Statistics;
import me.nikl.gamebox.game.IGameManager;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.logging.Level;

/**
 * Created by Niklas.
 *
 * GameManager
 */

public class GameManager implements IGameManager {
    private Main plugin;

    private Map<UUID, Game> games = new HashMap<>();
    private Language lang;

    private Map<String,GameRules> gameTypes;
    private File savesFile;
    private FileConfiguration saves;

    private Statistics statistics;



    public GameManager(Main plugin){
        this.plugin = plugin;
        this.statistics = plugin.gameBox.getStatistics();
        this.lang = plugin.lang;

        savesFile = new File(plugin.getDataFolder().toString() + File.separatorChar + "saves.yml");
        if(!savesFile.exists()){
            try {
                savesFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        try {
            this.saves = YamlConfiguration.loadConfiguration(new InputStreamReader(new FileInputStream(savesFile), "UTF-8"));
        } catch (UnsupportedEncodingException | FileNotFoundException e) {
            e.printStackTrace();
        }
    }


    @Override
    public boolean onInventoryClick(InventoryClickEvent inventoryClickEvent) {
        if(!games.keySet().contains(inventoryClickEvent.getWhoClicked().getUniqueId())) return false;

        Game game = games.get(inventoryClickEvent.getWhoClicked().getUniqueId());

        game.onClick(inventoryClickEvent);
        return true;
    }


    @Override
    public boolean onInventoryClose(InventoryCloseEvent inventoryCloseEvent) {
        if(!games.keySet().contains(inventoryCloseEvent.getPlayer().getUniqueId())) return false;

        // do same stuff as on removeFromGame()
        removeFromGame(inventoryCloseEvent.getPlayer().getUniqueId());
        return true;
    }


    @Override
    public boolean isInGame(UUID uuid) {
        return games.containsKey(uuid);
    }


    @Override
    public int startGame(Player[] players, boolean playSounds, String... strings) {
        if (strings.length != 1) {
            Bukkit.getLogger().log(Level.WARNING, " unknown number of arguments to start a game: " + Arrays.asList(strings));
            return GameBox.GAME_NOT_STARTED_ERROR;
        }

        GameRules rule = gameTypes.get(strings[0]);

        if (rule == null) {
            Bukkit.getLogger().log(Level.WARNING, " unknown gametype: " + Arrays.asList(strings));
            return GameBox.GAME_NOT_STARTED_ERROR;
        }

        if (!pay(players, rule.getCost())) {
            return GameBox.GAME_NOT_ENOUGH_MONEY;
        }

        if(saves.isConfigurationSection(rule.getKey() + "." + players[0].getUniqueId())) {
            games.put(players[0].getUniqueId(), new Game(rule, plugin, players[0], playSounds, saves.getConfigurationSection(rule.getKey() + "." + players[0].getUniqueId())));
        } else {
            games.put(players[0].getUniqueId(), new Game(rule, plugin, players[0], playSounds, null));
        }

        return GameBox.GAME_STARTED;
    }


    @Override
    public void removeFromGame(UUID uuid) {

        Game game = games.get(uuid);

        if(game == null) return;

        game.cancel();
        game.onGameEnd();

        games.remove(uuid);
    }


    public void setGameTypes(Map<String, GameRules> gameTypes) {
        this.gameTypes = gameTypes;
    }


    private boolean pay(Player[] player, double cost) {
        if (plugin.isEconEnabled() && !player[0].hasPermission(Permissions.BYPASS_ALL.getPermission()) && !player[0].hasPermission(Permissions.BYPASS_GAME.getPermission(Main.gameID)) && cost > 0.0) {
            if (Main.econ.getBalance(player[0]) >= cost) {
                Main.econ.withdrawPlayer(player[0], cost);
                player[0].sendMessage(GameBox.chatColor(lang.PREFIX + plugin.lang.GAME_PAYED.replaceAll("%cost%", String.valueOf(cost))));
                return true;
            } else {
                player[0].sendMessage(GameBox.chatColor(lang.PREFIX + plugin.lang.GAME_NOT_ENOUGH_MONEY));
                return false;
            }
        } else {
            return true;
        }
    }

    public void saveGame(GameRules rule, UUID uuid, Map<String, Double> cookies, Map<String, Integer> productions, List<Integer> upgrades) {

        for(String key : cookies.keySet()){
            saves.set(rule.getKey() + "." + uuid.toString() + "." + "cookies" + "." + key, Math.floor(cookies.get(key)));
        }

        for(String production : productions.keySet()){
            saves.set(rule.getKey() + "." + uuid.toString() + "." + "productions" + "." + production, productions.get(production));
        }

        saves.set(rule.getKey() + "." + uuid.toString() + "." + "upgrades", upgrades);
        statistics.addStatistics(uuid, Main.gameID, rule.getKey(), Math.floor(cookies.get("total")), SaveType.HIGH_NUMBER_SCORE);
    }

    public void onShutDown(){
        // save all open games!
        for(Game game : games.values()){
            game.cancel();
            game.onGameEnd();
        }

        try {
            saves.save(savesFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void restart(String key){
        GameRules rule = gameTypes.get(key);
        if(rule == null) return;

        Set<Player> players = new HashSet<>();

        for(Game game : games.values()){
            if(game.getRule().getKey().equals(key)){
                players.add(game.getPlayer());
            }
        }

        for(Player player : players){
            if(player != null) player.closeInventory();
        }

        // here pay out any rewards and gather the top players


        // ToDo: update GameBox and allow for removing of statistics

        // delete saves
        if (saves.isConfigurationSection(key)){
            saves.set(key, null);
        }

    }
}
