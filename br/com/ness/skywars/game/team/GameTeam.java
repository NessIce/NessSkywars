package br.com.ness.skywars.game.team;

import br.com.ness.core.bukkit.profile.Profile;
import br.com.ness.skywars.game.GameCuboid;
import br.com.ness.skywars.game.GameType;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class GameTeam {

    public String[] teamName = new String[]{"A","B","C","D","E","F","G","H","I","J","K","L"};

    private String name;
    private GameType type;
    private List<Profile> team;

    private Location spawn;
    private GameCuboid cage;

    public GameTeam(GameType type, int position) {
        this.name = teamName[position];
        this.type = type;
        this.team = new ArrayList<>();
    }

    public void addPlayer(Profile profile){
        team.add(profile);

        if(type.isSingle()){
            this.name = profile.getColorName();
        }
    }
    public void removePlayer(Profile profile){
        team.remove(profile);
    }

    public Location getSpawn() { return spawn; }
    public void setSpawn(Location spawn) {
        this.spawn = spawn;
    }

    public GameCuboid getCage() {
        return cage;
    }
    public void setCage(GameCuboid cage) {
        this.cage = cage;
    }

    public boolean hasSpace(){ return team.size()<type.getSize();}
    public boolean hasPlayer(Profile profile){ return team.contains(profile); }
    public boolean isEmpty(){ return team.size()==0; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public GameType getType() { return type; }
    public void setType(GameType type) { this.type = type; }

    public List<Profile> getTeam() { return team; }
    public void setTeam(List<Profile> team) {
        this.team = team;
    }

    public void sendMessage(String message){ getTeam().forEach(gamePlayer -> gamePlayer.sendMessage(message)); }
    public void forEach(Consumer<? super Profile> action){
        team.forEach(action);
    }
}
