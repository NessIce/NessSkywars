package br.com.ness.skywars.listener;

import br.com.ness.core.bukkit.profile.Profile;
import br.com.ness.core.bukkit.title.Titles;
import br.com.ness.skywars.Skywars;
import br.com.ness.skywars.game.Game;
import br.com.ness.skywars.game.GameStatus;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class DeathEvent implements Listener {

    @EventHandler
    public void onDeathEvent(PlayerDeathEvent e) {
        Profile profile = Profile.getByPlayer(e.getEntity());

        if(!profile.isGaming()){
            return;
        }

        Game game = (Game) profile.getGame();

        profile.add("skywars", "deaths-"+game.getType().name().toLowerCase(), 1);
        e.setDeathMessage(null);

        if(e.getEntity().getKiller() != null){
            Profile killer = Profile.getByPlayer(e.getEntity().getKiller());

            killer.add("skywars", "kills-"+game.getType().name().toLowerCase(), 1);
            //game.addKillPlayer(killer);

            game.sendMessage(profile.getColorName() + " §emorreu para " + killer.getColorName() + "§e!");

            Titles title = new Titles("§c§lVOCÊ MORREU", "§7por: "+killer.getColorName(), 3, 60, 3);
            title.send(profile.getPlayer());
        }else{

            game.sendMessage(profile.getColorName() + " §emorreu sozinho!");

            Titles title = new Titles("§c§lVOCÊ MORREU", "", 3, 60, 3);
            title.send(profile.getPlayer());
        }

        Bukkit.getScheduler().scheduleSyncDelayedTask(Skywars.getPlugin(), () -> profile.getPlayer().spigot().respawn(), 1L);

        game.joinSpectator(profile, false);
        game.removeProfileTeam(profile);

        if (game.getStatus() == GameStatus.PLAYING && game.getGameTeams().size() == 1) {
            game.finishingGame();
            game.getPlayingGameTask().cancel();
        }
    }
}
