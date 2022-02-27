package br.com.ness.skywars.game.chest;

import br.com.ness.core.bukkit.config.ConfigFile;
import br.com.ness.skywars.Skywars;
import br.com.ness.skywars.game.GameType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameChest {

    private Map<ChestClass, List<ChestItem>> classes;

    public GameChest(GameType type){
        this.classes = new HashMap<>();

        ConfigFile chestFile = new ConfigFile("chests", Skywars.getPlugin().getDataFolder().getPath(), true, Skywars.getPlugin());

        List<String> basicData = chestFile.getStringList("chests."+type.getType()+".basic");
        List<ChestItem> basicItens = new ArrayList<>();
        for(String s : basicData){
            String[] data = s.split(" :: ");
            ChestItem chestItem = new ChestItem(Integer.parseInt(data[0]), data[1]);
            basicItens.add(chestItem);
        }
        this.classes.put(ChestClass.BASIC, basicItens);


        List<String> complementData = chestFile.getStringList("chests."+type.getType()+".complement");
        List<ChestItem> complementItens = new ArrayList<>();
        for(String s : complementData){
            String[] data = s.split(" :: ");
            ChestItem chestItem = new ChestItem(Integer.parseInt(data[0]), data[1]);
            complementItens.add(chestItem);
        }
        this.classes.put(ChestClass.COMPLEMENT, complementItens);


        List<String> feastData = chestFile.getStringList("chests."+type.getType()+".feast");
        List<ChestItem> feastItens = new ArrayList<>();
        for(String s : feastData){
            String[] data = s.split(" :: ");
            ChestItem chestItem = new ChestItem(Integer.parseInt(data[0]), data[1]);
            feastItens.add(chestItem);
        }
        this.classes.put(ChestClass.FEAST, feastItens);
    }

    public List<ChestItem> getChestItems(ChestClass chestClass){
        return classes.get(chestClass);
    }
    public Map<ChestClass, List<ChestItem>> getClasses() {
        return classes;
    }
    public void setClasses(Map<ChestClass, List<ChestItem>> classes) {
        this.classes = classes;
    }
}
