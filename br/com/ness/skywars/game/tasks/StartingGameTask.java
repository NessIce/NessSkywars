package br.com.ness.skywars.game.tasks;

import br.com.ness.core.bukkit.title.Titles;
import br.com.ness.skywars.game.Game;
import org.bukkit.Sound;
import org.bukkit.scheduler.BukkitRunnable;

public class StartingGameTask extends BukkitRunnable {

    private final Game game;
    private int cowndown;

    public StartingGameTask(Game game) {
        this.game = game;
        this.cowndown = 10;
    }

    @Override
    public void run() {
        if(cowndown==3||cowndown==2||cowndown==1){
            Titles titles = new Titles("§c§l"+cowndown, "§ePrepare-se",5,20,5);
            game.getPlayers().forEach(profile -> titles.send(profile.getPlayer()));
            game.getPlayers().forEach(profile -> profile.getPlayer().playSound(profile.getPlayer().getLocation(), Sound.ORB_PICKUP,1,1));
        }

        if(cowndown==0){
            game.playingGame();
            cancel();

            game.getPlayers().forEach(profile -> profile.getPlayer().playSound(profile.getPlayer().getLocation(), Sound.LEVEL_UP,1,1));
            return;
        }
        cowndown--;
    }
}
