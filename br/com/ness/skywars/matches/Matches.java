package br.com.ness.skywars.matches;

import br.com.ness.skywars.game.Game;
import br.com.ness.skywars.game.GameStatus;
import br.com.ness.skywars.game.GameType;
import br.com.ness.skywars.playnpc.NPCType;

import java.util.List;

public enum Matches {

    SOLO{
        @Override
        public List<Game> getGames() {
            return Loader.getGames(GameType.SOLO);
        }

        @Override
        public Game getRandomGame() {
            Game game = null;
            for(Game games : this.getGames()){
                if(games.isPlayable()){
                    if(games.getPlayers().size()>=1){
                        game=games;
                        break;
                    }
                    game = games;
                }
            }
            return game;
        }

        @Override
        public int inPlaying() {
            int i = 0;
            for(Game game : this.getGames()){
                if(game.getStatus() == GameStatus.PLAYING){
                    i+=game.getPlayers().size();
                }
            }
            return i;
        }

        @Override
        public int inWaiting() {
            int i = 0;
            for(Game game : this.getGames()){
                if(game.getStatus() == GameStatus.WAITING || game.getStatus() == GameStatus.PREPARING){
                    i+=game.getPlayers().size();
                }
            }
            return i;
        }
    },
    DUEL{
        @Override
        public List<Game> getGames() {
            return Loader.getGames(GameType.DUEL);
        }

        @Override
        public Game getRandomGame() {
            Game game = null;
            for(Game games : this.getGames()){
                if(games.isPlayable()){
                    if(games.getPlayers().size()>=1){
                        game=games;
                        break;
                    }
                    game = games;
                }
            }
            return game;
        }

        @Override
        public int inPlaying() {
            int i = 0;
            for(Game game : this.getGames()){
                if(game.getStatus() == GameStatus.PLAYING){
                    i+=game.getPlayers().size();
                }
            }
            return i;
        }

        @Override
        public int inWaiting() {
            int i = 0;
            for(Game game : this.getGames()){
                if(game.getStatus() == GameStatus.WAITING || game.getStatus() == GameStatus.PREPARING){
                    i+=game.getPlayers().size();
                }
            }
            return i;
        }
    },
    DOUBLE{
        @Override
        public List<Game> getGames() {
            return Loader.getGames(GameType.DOUBLE);
        }

        @Override
        public Game getRandomGame() {
            Game game = null;
            for(Game games : this.getGames()){
                if(games.isPlayable()){
                    if(games.getPlayers().size()>=1){
                        game=games;
                        break;
                    }
                    game = games;
                }
            }
            return game;
        }

        @Override
        public int inPlaying() {
            int i = 0;
            for(Game game : this.getGames()){
                if(game.getStatus() == GameStatus.PLAYING){
                    i+=game.getPlayers().size();
                }
            }
            return i;
        }

        @Override
        public int inWaiting() {
            int i = 0;
            for(Game game : this.getGames()){
                if(game.getStatus() == GameStatus.WAITING || game.getStatus() == GameStatus.PREPARING){
                    i+=game.getPlayers().size();
                }
            }
            return i;
        }
    },
    TEAM{
        @Override
        public List<Game> getGames() {
            return Loader.getGames(GameType.TEAM);
        }

        @Override
        public Game getRandomGame() {
            Game game = null;
            for(Game games : this.getGames()){
                if(games.isPlayable()){
                    if(games.getPlayers().size()>=1){
                        game=games;
                        break;
                    }
                    game = games;
                }
            }
            return game;
        }

        @Override
        public int inPlaying() {
            int i = 0;
            for(Game game : this.getGames()){
                if(game.getStatus() == GameStatus.PLAYING){
                    i+=game.getPlayers().size();
                }
            }
            return i;
        }

        @Override
        public int inWaiting() {
            int i = 0;
            for(Game game : this.getGames()){
                if(game.getStatus() == GameStatus.WAITING || game.getStatus() == GameStatus.PREPARING){
                    i+=game.getPlayers().size();
                }
            }
            return i;
        }
    };

    public abstract List<Game> getGames();
    public abstract Game getRandomGame();
    public abstract int inPlaying();
    public abstract int inWaiting();

    public static boolean has(String s){
        for(Matches values : values()){
            if(values.name().equalsIgnoreCase(s.toUpperCase())){
                return true;
            }
        }
        return false;
    }
}
