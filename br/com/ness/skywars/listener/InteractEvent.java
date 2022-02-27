package br.com.ness.skywars.listener;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class InteractEvent implements Listener {

    @EventHandler
    public void onInteractEvent(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        if (player.getItemInHand() == null || player.getItemInHand().getType() == Material.AIR) {
            return;
        }

        ItemStack item = player.getItemInHand();
        if (!item.hasItemMeta() || !item.getItemMeta().hasDisplayName()) {
            return;
        }

//        if (item.getItemMeta().getDisplayName().equals("§aJogar novamente")) {
//            e.setCancelled(true);
//            if(Skywars.getProfile(player).isGaming()){
//                Skywars.getProfile(player).getGame().getType().getPlayMenu(player).open();
//            }
//        }
    }
}
