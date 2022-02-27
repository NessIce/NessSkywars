package br.com.ness.skywars.game.tasks;

import br.com.ness.core.bukkit.title.Titles;
import br.com.ness.skywars.game.Game;
import org.bukkit.Sound;
import org.bukkit.scheduler.BukkitRunnable;

public class PreparingGameTask extends BukkitRunnable {

    private final Game game;
    private int cowndown;

    public PreparingGameTask(Game game) {
        this.game = game;
        cowndown = 60;
    }

    @Override
    public void run() {
        if(cowndown==30||cowndown==10||cowndown<=5) {
            if (cowndown == 0) {
                game.startingGame();
                cancel();
                return;
            }
            game.sendMessage("§eA partida irá iniciar em §f" + cowndown + "§e segundo" + (cowndown == 1 ? "!" : "s!"));

            if (cowndown <= 5) {
                String color = cowndown == 5 || cowndown == 4 ? "§c§l" : cowndown == 3 || cowndown == 2 ? "§e§l" : "§a§l";
                Titles titles = new Titles(color + cowndown, "", 5, 20, 5);
                game.getPlayers().forEach(profile -> titles.send(profile.getPlayer()));
            }
            game.getPlayers().forEach(profile -> profile.getPlayer().playSound(profile.getPlayer().getLocation(), Sound.NOTE_PLING, 1, 1));
        }
        cowndown--;
    }
}
