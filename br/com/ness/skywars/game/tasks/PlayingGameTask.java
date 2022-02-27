package br.com.ness.skywars.game.tasks;

import br.com.ness.skywars.game.Game;
import org.bukkit.scheduler.BukkitRunnable;

public class PlayingGameTask extends BukkitRunnable {

    private final Game game;
    private Long time;

    public PlayingGameTask(Game game) {
        this.game = game;
        this.time = 0L;
    }

    @Override
    public void run() {
        String varSeconds = (time % 60L < 10L) ? ("0" + this.time % 60L) : (this.time % 60L + "");
        String varMinutes = (time / 60L % 60L < 10L) ? ("0" + this.time / 60L % 60L) : (this.time / 60L % 60L + "");
        String timer = varMinutes + ":" + varSeconds;
        game.setTimeGamePlay(timer);
        time++;
    }
}
