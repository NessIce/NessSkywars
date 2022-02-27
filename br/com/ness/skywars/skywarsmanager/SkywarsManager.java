package br.com.ness.skywars.skywarsmanager;

import br.com.ness.core.bukkit.profile.Profile;
import br.com.ness.core.bukkit.profile.hotbar.Hotbar;
import br.com.ness.skywars.game.Game;
import br.com.ness.skywars.skywarsmanager.scoreboard.SkywarsScore;
import org.bukkit.GameMode;

public class SkywarsManager {

    public static void joinLobby(Profile profile){
        profile.getPlayer().getActivePotionEffects().forEach(p -> profile.getPlayer().removePotionEffect(p.getType()));
        profile.getPlayer().setFoodLevel(20);
        profile.getPlayer().setHealth(20.0);
        profile.getPlayer().setExp(0.0f);
        profile.getPlayer().setLevel(0);

        profile.getPlayer().setAllowFlight(profile.isFly());
        profile.getPlayer().setGameMode(GameMode.ADVENTURE);

        Hotbar.getHotbarById("skywars").apply(profile);

        profile.registerScoreCore();

        profile.getScoreCore().setSidebar(new SkywarsScore());
        profile.getScoreCore().apply();
    }

    public static void joinGame(Profile profile, Game game){

    }

    public static void quitGame(Profile profile){

    }

    public static void joinSpectator(Profile profile){

    }

    public static void quitSpectator(Profile profile){

    }
}
