package br.com.ness.skywars.playnpc;

import org.bukkit.Material;

public enum NPCType {

    SOLO("soloNPC",Material.LEATHER_CHESTPLATE, "§b§lMODO SOLO"),
    DUEL("duelNPC",Material.IRON_CHESTPLATE, "§b§lMODO DUELO"),
    DOUBLE("doubleNPC",Material.GOLD_CHESTPLATE, "§b§lMODO DUPLA"),
    TEAM("teamNPC",Material.DIAMOND_CHESTPLATE, "§b§lMODO EQUIPE");

    private final String metadata;
    private final Material material;
    private final String displayName;

    NPCType(String metadata, Material material, String displayName){
        this.metadata = metadata;
        this.material = material;
        this.displayName = displayName;
    }

    public String getMetadata(){
        return metadata;
    }
    public Material getMaterial(){
        return material;
    }
    public String getDisplayName(){
        return displayName;
    }

    public static boolean has(String s){
        for(NPCType values : values()){
            if(values.name().equalsIgnoreCase(s.toUpperCase())){
                return true;
            }
        }
        return false;
    }
}
