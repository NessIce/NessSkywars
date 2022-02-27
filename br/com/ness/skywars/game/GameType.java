package br.com.ness.skywars.game;

import br.com.ness.core.bukkit.menusystem.Menu;
import br.com.ness.core.bukkit.menusystem.MenuManager;
import br.com.ness.skywars.menus.playnpc.DoubleNPCMenu;
import br.com.ness.skywars.menus.playnpc.DuelNPCMenu;
import br.com.ness.skywars.menus.playnpc.SoloNPCMenu;
import br.com.ness.skywars.menus.playnpc.TeamNPCMenu;
import org.bukkit.entity.Player;

import java.io.Serializable;

public enum GameType implements Serializable {
    SOLO("Solo",1, true, "solo"){
        @Override
        public Menu getPlayMenu(Player player) {
            return new SoloNPCMenu(MenuManager.getMenu(player));
        }
    },
    DUEL("Duelo",1, true, "duel"){
        @Override
        public Menu getPlayMenu(Player player) {
            return new DuelNPCMenu(MenuManager.getMenu(player));
        }
    },
    DOUBLE("Dupla",2, false, "double"){
        @Override
        public Menu getPlayMenu(Player player) {
            return new DoubleNPCMenu(MenuManager.getMenu(player));
        }
    },
    TEAM("Equipe",4, false, "team"){
        @Override
        public Menu getPlayMenu(Player player) {
            return new TeamNPCMenu(MenuManager.getMenu(player));
        }
    };

    private final String name;
    private final int size;
    private final boolean single;
    private final String type;

    GameType(String name, int size, boolean single, String type){
        this.name = name;
        this.size = size;
        this.single = single;
        this.type = type;
    }

    public abstract Menu getPlayMenu(Player player);

    public int getSize() { return size; }

    public String getName() { return name; }

    public boolean isSingle() { return single; }

    public String getType() { return type; }

    public static boolean has(String s){
        for(GameType values : values()){
            if(values.name().equalsIgnoreCase(s.toUpperCase())){
                return true;
            }
        }
        return false;
    }
}
