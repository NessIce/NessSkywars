package br.com.ness.skywars.skywarsmanager;

import br.com.ness.core.bukkit.config.ConfigFile;
import br.com.ness.core.bukkit.profile.Profile;
import br.com.ness.core.bukkit.profile.hotbar.Hotbar;
import br.com.ness.core.bukkit.profile.hotbar.HotbarAction;
import br.com.ness.core.bukkit.profile.hotbar.HotbarActionType;
import br.com.ness.core.bukkit.profile.hotbar.HotbarButton;
import br.com.ness.skywars.Skywars;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;

public class SkywarsHotbar extends HotbarActionType {

    @Override
    public void execute(Profile profile, String action) {
        switch (action.toLowerCase()){
            case "shop":{
                profile.sendMessage("Acessando a loja");
                //new ShopMenu(MenuManager.getMenu(profile.getPlayer())).open();
                break;
            }

            case "kits":{
                //new ShopMenu(MenuManager.getMenu(profile.getPlayer())).open();
                break;
            }

            case "perks":{
                //new ShopMenu(MenuManager.getMenu(profile.getPlayer())).open();
                break;
            }

            case "play":{
                //new ShopMenu(MenuManager.getMenu(profile.getPlayer())).open();
                break;
            }

            case "spec":{
                //new ShopMenu(MenuManager.getMenu(profile.getPlayer())).open();
                break;
            }
        }
    }

    public static void register(Skywars plugin) {
        HotbarActionType.addActionType("skywars", new SkywarsHotbar());

        ConfigFile config = new ConfigFile("hotbars", plugin.getDataFolder().getPath(), true, plugin);

        Hotbar hotbarSkywars = new Hotbar("skywars");
        ConfigurationSection hbs = config.getSection("skywars");
        for (String button : hbs.getKeys(false)) {
            try {
                hotbarSkywars.getButtons().add(new HotbarButton(hbs.getInt(button + ".slot"), new HotbarAction(hbs.getString(button + ".execute")), hbs.getString(button + ".icon")));
            } catch (Exception ex) {
                Bukkit.getConsoleSender().sendMessage("§cFalha ao carregar botão da hotbar!");
            }
        }
        Hotbar.addHotbar(hotbarSkywars);

        Hotbar waiting = new Hotbar("waiting");
        ConfigurationSection hbw = config.getSection("waiting");
        for (String button : hbw.getKeys(false)) {
            try {
                waiting.getButtons().add(new HotbarButton(hbw.getInt(button + ".slot"), new HotbarAction(hbw.getString(button + ".execute")), hbw.getString(button + ".icon")));
            } catch (Exception ex) {
                Bukkit.getConsoleSender().sendMessage("§cFalha ao carregar botão da hotbar!");
            }
        }
        Hotbar.addHotbar(waiting);

        Hotbar playAgain = new Hotbar("playagain");
        ConfigurationSection hbp = config.getSection("playagain");
        for (String button : hbp.getKeys(false)) {
            try {
                playAgain.getButtons().add(new HotbarButton(hbp.getInt(button + ".slot"), new HotbarAction(hbp.getString(button + ".execute")), hbp.getString(button + ".icon")));
            } catch (Exception ex) {
                Bukkit.getConsoleSender().sendMessage("§cFalha ao carregar botão da hotbar!");
            }
        }
        Hotbar.addHotbar(playAgain);
    }
}
