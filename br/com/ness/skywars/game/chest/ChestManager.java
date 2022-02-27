package br.com.ness.skywars.game.chest;

import br.com.ness.skywars.game.GameType;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Chest;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class ChestManager {

    private static final Map<GameType, GameChest> chests;

    static{
        chests = new HashMap<>();

        chests.put(GameType.SOLO, new GameChest(GameType.SOLO));
        chests.put(GameType.DUEL, new GameChest(GameType.DUEL));
        chests.put(GameType.DOUBLE, new GameChest(GameType.DOUBLE));
        chests.put(GameType.TEAM, new GameChest(GameType.TEAM));
    }

    public static void applyChest(Location location, GameType chestType, ChestClass chestClass){
        List<ChestItem> itens = getChest(chestType).getChestItems(chestClass);

        Chest chest = (Chest) location.getBlock().getState();
        chest.getInventory().clear();

        Random slotRandom = new Random();
        for(ChestItem item : itens){
            int random = new Random().nextInt(100)+1;
            if(random<=item.getSort()){
                int slot = slotRandom.nextInt(chest.getInventory().getSize());
                if(chest.getInventory().getItem(slot)==null){
                    chest.getInventory().setItem(slot, item.getItemStack());
                }else {
                    chest.getInventory().addItem(item.getItemStack());
                }
            }
        }
    }

    public static GameChest getChest(GameType chestType) {
        return chests.get(chestType);
    }
}
