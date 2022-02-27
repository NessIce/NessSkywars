package br.com.ness.skywars.matches;

import br.com.ness.skywars.Skywars;
import br.com.ness.skywars.game.Game;
import br.com.ness.skywars.game.GameType;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.*;

public class Loader {

    public static Map<GameType, List<Game>> games = new HashMap<>();

    static {
        File gamesFile = new File(Skywars.getPlugin().getDataFolder().getPath()+"/games");

        games.put(GameType.SOLO, new ArrayList<>());
        games.put(GameType.DUEL, new ArrayList<>());
        games.put(GameType.DOUBLE, new ArrayList<>());
        games.put(GameType.TEAM, new ArrayList<>());

        for(File file : Objects.requireNonNull(gamesFile.listFiles())){
            Game game = new Game(YamlConfiguration.loadConfiguration(file));
            games.get(game.getType()).add(game);
        }
    }

    public static List<Game> getGames(GameType gameType){
        return games.get(gameType);
    }

    public static void registerGame(Game game){
        games.get(game.getType()).add(game);
    }
    public static void unregisterGame(Game game){
        games.get(game.getType()).remove(game);
    }
}
