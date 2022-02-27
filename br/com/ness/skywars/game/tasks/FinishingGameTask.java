package br.com.ness.skywars.game.tasks;

import br.com.ness.core.bukkit.profile.Profile;
import br.com.ness.skywars.game.Game;
import br.com.ness.skywars.matches.Loader;
import org.bukkit.scheduler.BukkitRunnable;

public class FinishingGameTask extends BukkitRunnable {

    private final Game game;
    private int cowndown;

    public FinishingGameTask(Game game) {
        this.game = game;
        this.cowndown = 0;
    }

    @Override
    public void run() {
        if (cowndown == 10) {
            for (Profile profile : game.getSpectators()) {
                profile.setGame(null);
                //profile.sendLobby();
            }

            Game newGame = new Game(game.getFile());

            Loader.unregisterGame(game);
            Loader.registerGame(newGame);
            cancel();
        }
        cowndown++;
    }
}
