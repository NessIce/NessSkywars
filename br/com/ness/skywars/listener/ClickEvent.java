package br.com.ness.skywars.listener;

import br.com.ness.core.bukkit.profile.Profile;
import br.com.ness.skywars.Skywars;
import br.com.ness.skywars.game.Game;
import br.com.ness.skywars.game.GameStatus;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class ClickEvent implements Listener {

    @EventHandler
    public void onClickEvent(InventoryClickEvent e){
        if(e.getWhoClicked() instanceof Player){
            Player player = (Player)e.getWhoClicked();
            if(Profile.getByPlayer(player).isGaming() && ((Game)Profile.getByPlayer(player).getGame()).getStatus()== GameStatus.PLAYING){
                return;
            }
            if (!player.getGameMode().equals(GameMode.CREATIVE)) {
                e.setCancelled(true);
            }
        }
    }
}
