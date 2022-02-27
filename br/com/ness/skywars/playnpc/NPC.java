package br.com.ness.skywars.playnpc;

import br.com.ness.core.bukkit.config.ConfigFile;
import br.com.ness.core.bukkit.utils.SerializerCore;
import br.com.ness.skywars.Skywars;
import br.com.ness.skywars.game.GameType;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Giant;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NPC {

    private static final Map<String, NPC> NPCS = new HashMap<>();

    public static void register(Skywars plugin){
        ConfigFile configFile = new ConfigFile("locations", plugin.getDataFolder().getPath(), true, plugin);

        ConfigurationSection section = configFile.getSection("locations.playnpc");

        for(String id : section.getKeys(false)) {

            Location location = SerializerCore.getLocation(section.getString(id + ".location"));
            NPCType type = NPCType.valueOf(section.getString( id + ".type"));

            NPC npc = new NPC(id, location, type);
            npc.spawn();

            NPCS.put(id,npc);
        }
    }
    public static void unregister(){
        for(NPC npc : NPCS.values()){
            npc.getEntities().forEach(Entity::remove);
        }
        NPCS.clear();
    }
    public static List<NPC> getNPC(GameType gameType){
        List<NPC> npcList = new ArrayList<>();
        for (NPC npc : NPCS.values()){
            if(npc.getType().name().equals(gameType.name())){
                npcList.add(npc);
            }
        }
        return npcList;
    }
    public static NPC getNPC(String id){ return NPCS.get(id); }
    public static Map<String, NPC> getNPCs(){ return NPCS; }

    private String id;
    private NPCType type;
    private List<Entity> entities;
    private Location origin;
    private Location location;
    private Entity count;

    public NPC(String id, Location location, NPCType type){
        this.id = id;
        this.type = type;

        this.entities = new ArrayList<>();
        this.origin = location;

        this.location = getFinalLocation(origin);
    }

    public void spawn(){
        spawnNPC(location, origin, type.getMetadata(), type.getMaterial(), type.getDisplayName());
        NPCS.put(id,this);
    }
    public void despawn(){
        entities.forEach(Entity::remove);
        NPCS.remove(id);
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public NPCType getType() {
        return type;
    }
    public void setType(NPCType type) {
        this.type = type;
    }

    public List<Entity> getEntities() {
        return entities;
    }
    public void setEntities(List<Entity> entities) {
        this.entities = entities;
    }

    public Location getOrigin() { return origin; }
    public void setOrigin(Location origin) { this.origin = origin; }

    public Location getLocation() {
        return location;
    }
    public void setLocation(Location location) {
        this.location = location;
    }

    public Entity getCount() { return count; }
    public void setCount(Entity count) { this.count = count; }
    public void setCount(int value){ getCount().setCustomName("§f"+value+" Jogadores"); }

    public Location getFinalLocation(Location loc){
        Location location = loc.clone().add(getLeftHeadDirection(loc).multiply(2));
        Location location1 = location.add(getBehind(location).multiply(-3.5));
        Location locationFinal = location1.subtract(0,9.7,0);
        locationFinal.setYaw(loc.clone().getYaw());
        Location finalBlock = locationFinal.getBlock().getLocation();
        finalBlock.add(0.5,0.2,1.0);
        return finalBlock;
    }
    public static Vector getBehind(Location location) {
        Vector direction = location.getDirection().normalize();
        return new Vector(direction.getX(), 0.0, direction.getZ()).normalize();
    }
    public static Vector getLeftHeadDirection(Location location) {
        Vector direction = location.getDirection().normalize();
        return new Vector(direction.getZ(), 0.0, -direction.getX()).normalize();
    }

    private void spawnNPC(Location location, Location origin, String metadata, Material material, String name){
        Giant giant = spawnGiant(location, material, metadata);
        getEntities().add(giant);

        ArmorStand seat = spawnArmorStand(false, "", location, metadata);
        seat.setPassenger(giant);
        getEntities().add(seat);

        ArmorStand holo1 = spawnArmorStand(true, "§eClique para jogar", origin.clone().add(0,1.2,0), metadata);
        getEntities().add(holo1);

        ArmorStand holo2 = spawnArmorStand(true, "§f0 Jogadores", origin.clone().add(0,1.5,0), metadata);
        getEntities().add(holo2);
        setCount(holo2);

        ArmorStand holo3 = spawnArmorStand(true, name, origin.clone().add(0,1.8,0), metadata);
        getEntities().add(holo3);
    }
    private Giant spawnGiant(Location location, Material material, String metadata) {
        Giant giant = location.getWorld().spawn(location, Giant.class);
        giant.setRemoveWhenFarAway(false);
        giant.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 1));
        giant.getEquipment().setItemInHand(new ItemStack(material));
        giant.setMetadata(metadata, new FixedMetadataValue(Skywars.getPlugin(), giant));
        return giant;
    }
    private ArmorStand spawnArmorStand(boolean name, String message, Location location, String metadata) {
        ArmorStand holo = location.getWorld().spawn(location, ArmorStand.class);
        holo.setGravity(false);
        holo.setCustomName(message);
        holo.setCustomNameVisible(name);
        holo.setVisible(false);
        holo.setMetadata(metadata, new FixedMetadataValue(Skywars.getPlugin(), holo));
        return holo;
    }
}
