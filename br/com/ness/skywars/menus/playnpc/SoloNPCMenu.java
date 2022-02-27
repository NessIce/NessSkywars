package br.com.ness.skywars.menus.playnpc;

import br.com.ness.core.bukkit.itens.ItemBuilder;
import br.com.ness.core.bukkit.menusystem.Menu;
import br.com.ness.core.bukkit.menusystem.MenuManager;
import br.com.ness.core.bukkit.menusystem.MenuUtils;
import br.com.ness.skywars.Skywars;
import br.com.ness.skywars.matches.Matches;
import br.com.ness.skywars.menus.playmap.SoloMapMenu;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.Arrays;
import java.util.Collections;

public class SoloNPCMenu extends Menu {

    public SoloNPCMenu(MenuUtils menuUtils) {
        super(menuUtils);
    }

    @Override
    public String getMenuName() {
        return "§8Sky Wars Solo";
    }

    @Override
    public int getSlots() {
        return 3*9;
    }

    @Override
    public void handlerMenu(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        switch (e.getCurrentItem().getType()){
            case ENDER_PEARL:{
                player.closeInventory();
                //Skywars.getProfile(player).joinGame(true, Matches.SOLO, null);
                break;
            }
            case MAP:{
                new SoloMapMenu(MenuManager.getMenu(player)).open();
                break;
            }
        }
    }

    @Override
    public void setMenuItens() {
        ItemBuilder play = new ItemBuilder(Material.ENDER_PEARL, "§aSkywars Solo", Arrays.asList(
                "§712 ilhas, 12 jogadores.",
                "",
                "§fEm espera: §a" + Matches.SOLO.inWaiting(),
                "§fJogando: §a" + Matches.SOLO.inPlaying(),
                "",
                "§eClique para jogar!"));

        ItemBuilder maps = new ItemBuilder(Material.MAP, "§aSelecionar um Mapa", Collections.singletonList(
                "§eClique para jogar em um mapa específico."));

        inventory.setItem(12, play.getItem());
        inventory.setItem(14, maps.getItem());
    }
}
