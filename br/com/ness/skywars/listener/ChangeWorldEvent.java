package br.com.ness.skywars.listener;

import br.com.ness.skywars.Skywars;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class ChangeWorldEvent implements Listener {

    @EventHandler
    public void onChangeWorldEvent(PlayerTeleportEvent e) {
        Player player = e.getPlayer();

        new BukkitRunnable() {
            public void run() {
                for (Player players : Bukkit.getOnlinePlayers()) {
                    if (!player.getWorld().equals(players.getWorld())) {
                        player.hidePlayer(players);
                        players.hidePlayer(player);
                        return;
                    }

                    player.showPlayer(players);
                    players.showPlayer(player);
                }
            }
        }.runTaskLater(Skywars.getPlugin(), 2L);
    }
}
