package br.com.ness.skywars.game.creator;

import br.com.ness.core.bukkit.config.ConfigFile;
import br.com.ness.skywars.game.GameCuboid;
import br.com.ness.skywars.game.GameType;
import br.com.ness.skywars.game.chest.ChestClass;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GamePreset {

    private String name;
    private ConfigFile file;

    private GameType type;

    private int min;
    private int max;

    private Location joinSpawn;
    private World world;

    private List<Location> spawns;

    private Location cuboidGame1;
    private Location cuboidGame2;

    private Location cuboidSpawn1;
    private Location cuboidSpawn2;

    private Map<Location, ChestClass> chests;

    public GamePreset(ConfigFile file){
        this.name = file.getConfig().getString("game.name");
        this.type = GameType.valueOf(file.getConfig().getString("game.type"));
        this.min = file.getConfig().getInt("game.min");
        this.max = file.getConfig().getInt("game.max");

        this.file = file;
        this.spawns = new ArrayList<>();
        this.chests = new HashMap<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public GameType getType() {
        return type;
    }

    public void setType(GameType type) {
        this.type = type;
    }

    public ConfigFile getFile() {
        return file;
    }

    public void setFile(ConfigFile file) {
        this.file = file;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public Location getJoinSpawn() {
        return joinSpawn;
    }

    public void setJoinSpawn(Location joinSpawn) {
        this.joinSpawn = joinSpawn;
    }

    public World getWorld() {
        return world;
    }

    public void setWorld(World world) {
        this.world = world;
    }

    public List<Location> getSpawns() {
        return spawns;
    }

    public void setSpawns(List<Location> spawns) {
        this.spawns = spawns;
    }

    public Location getCuboidGame1() {
        return cuboidGame1;
    }

    public void setCuboidGame1(Location cuboidGame1) {
        this.cuboidGame1 = cuboidGame1;
    }

    public Location getCuboidGame2() {
        return cuboidGame2;
    }

    public void setCuboidGame2(Location cuboidGame2) {
        this.cuboidGame2 = cuboidGame2;
    }

    public Location getCuboidSpawn1() {
        return cuboidSpawn1;
    }

    public void setCuboidSpawn1(Location cuboidSpawn1) {
        this.cuboidSpawn1 = cuboidSpawn1;
    }

    public Location getCuboidSpawn2() {
        return cuboidSpawn2;
    }

    public void setCuboidSpawn2(Location cuboidSpawn2) {
        this.cuboidSpawn2 = cuboidSpawn2;
    }

    public GameCuboid getCuboidGame() {
        if(getCuboidGame1()==null||getCuboidGame2()==null){
            return null;
        }
        return new GameCuboid(getCuboidGame1(), getCuboidGame2());
    }

    public GameCuboid getCuboidSpawn(){
        if(getCuboidSpawn1()==null||getCuboidSpawn2()==null){
            return null;
        }
        return new GameCuboid(getCuboidSpawn1(), getCuboidSpawn2());
    }

    public Map<Location, ChestClass> getChests() {
        return chests;
    }

    public void setChests(Map<Location, ChestClass> chests) {
        this.chests = chests;
    }

    public List<ChestClass> getChestsClass(ChestClass chestClass){
        List<ChestClass> chestClassesList = new ArrayList<>();
        for(ChestClass chestClasses : chests.values()){
            if(chestClasses.equals(chestClass)){
                chestClassesList.add(chestClasses);
            }
        }
        return chestClassesList;
    }
}
