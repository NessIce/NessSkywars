package br.com.ness.skywars.menus.playmap;

import br.com.ness.core.bukkit.CorePlugin;
import br.com.ness.core.bukkit.itens.ItemBuilder;
import br.com.ness.core.bukkit.menusystem.Menu;
import br.com.ness.core.bukkit.menusystem.MenuManager;
import br.com.ness.core.bukkit.menusystem.MenuUtils;
import br.com.ness.core.bukkit.utils.InventoryCore;
import br.com.ness.skywars.game.Game;
import br.com.ness.skywars.game.GameStatus;
import br.com.ness.skywars.matches.Matches;
import br.com.ness.skywars.menus.playnpc.TeamNPCMenu;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class TeamMapMenu extends Menu {


    public TeamMapMenu(MenuUtils menuUtils) {
        super(menuUtils);
    }

    @Override
    public String getMenuName() {
        return "§8Mapas - SkyWars Equipe";
    }

    @Override
    public int getSlots() {
        return 6*9;
    }

    @Override
    public void handlerMenu(InventoryClickEvent e) {
        Player player = (Player)e.getWhoClicked();

        switch (e.getCurrentItem().getType()){
            case ARROW:{
                new TeamNPCMenu(MenuManager.getMenu(player)).open();
                break;
            }
        }
    }

    @Override
    public void setMenuItens() {
        List<Integer> slots = InventoryCore.slotsCenter(inventory);
        for(Game games : Matches.TEAM.getGames()){
            String color = games.getStatus()==GameStatus.WAITING||games.getStatus()==GameStatus.PREPARING?"§a":"§c";
            ItemBuilder game = new ItemBuilder(Material.MAP, color+games.getName(),Arrays.asList(
                    "§8Modo Equipe",
                    "",
                    "§fEstado: "+color+games.getStatus().getStatus(),
                    "§fJogando: §7"+games.getPlayers().size(),
                    "",
                    "§eClique para jogar!"));

            inventory.setItem(slots.get(Matches.TEAM.getGames().indexOf(games)), game.getItem());
        }

        ItemBuilder back = new ItemBuilder(Material.ARROW, "§aVoltar", Collections.singletonList("§7Clique para voltar."));
        inventory.setItem(49, back.getItem());
    }
}
