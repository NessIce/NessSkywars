package br.com.ness.skywars.game.creator;

import br.com.ness.core.bukkit.config.ConfigFile;
import br.com.ness.core.bukkit.itens.ItemBuilder;
import br.com.ness.core.bukkit.profile.Profile;
import br.com.ness.core.bukkit.utils.SerializerCore;
import br.com.ness.skywars.game.Game;
import br.com.ness.skywars.game.GameCuboid;
import br.com.ness.skywars.game.chest.ChestClass;
import br.com.ness.skywars.matches.Loader;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class GameCreator implements Listener {

    private static final Map<Profile, GamePreset> creatorMode = new HashMap<>();
    public static Map<Profile, GamePreset> getCreatorMode(){ return creatorMode; }
    public static boolean inCreatorMode(Profile profile){ return creatorMode.containsKey(profile); }
    public static GamePreset getGamePreset(Profile profile){ return creatorMode.get(profile); }
    public static void addCreatorMode(Profile profile, ConfigFile file){
        creatorMode.put(profile, new GamePreset(file));

        ItemBuilder spawns = new ItemBuilder(Material.SADDLE, "§aAdcionar Spawn", Collections.singletonList("§7Clique com direito!"));
        ItemBuilder joinSpawn = new ItemBuilder(Material.BEACON, "§aSpawn de Entrada", Collections.singletonList("§7Clique com direito!"));
        ItemBuilder cuboidGame = new ItemBuilder(Material.BLAZE_ROD, "§aCuboid do Jogo", Collections.singletonList("§7Clique com direito/esquerdo!"));
        ItemBuilder cuboidSpawn = new ItemBuilder(Material.STICK, "§aCuboid do Spawn", Collections.singletonList("§7Clique com direito/esquerdo!"));

        ItemBuilder chest1 = new ItemBuilder(Material.CHEST, "§aAdcionar Bau Basico", Collections.singletonList("§7Clique para setar!"));
        ItemBuilder chest2 = new ItemBuilder(Material.CHEST, "§aAdcionar Bau Complemento", Collections.singletonList("§7Clique para setar!"));
        ItemBuilder chestFeast = new ItemBuilder(Material.CHEST, "§aAdcionar Bau Feast", Collections.singletonList("§7Clique para setar!"));

        ItemBuilder save = new ItemBuilder(Material.INK_SACK, "§aSalvar",10, Collections.singletonList("§7Clique com direito!"));

        ItemStack[] items = new ItemStack[9];
        items[0] = spawns.getItem();
        items[1] = joinSpawn.getItem();
        items[2] = cuboidGame.getItem();
        items[3] = cuboidSpawn.getItem();
        items[5] = chest1.getItem();
        items[6] = chest2.getItem();
        items[7] = chestFeast.getItem();
        items[8] = save.getItem();

        profile.getPlayer().getInventory().setContents(items);
        profile.getPlayer().getInventory().setArmorContents(null);

        profile.getPlayer().setFlySpeed(0.3f);
        profile.getPlayer().setGameMode(GameMode.CREATIVE);

        profile.registerScoreCore();
        profile.getScoreCore().setSidebar(new CreatorScore());
        profile.getScoreCore().apply();
    }
    public static void removeCreatorMode(Profile profile){
        profile.registerScoreCore();
        creatorMode.remove(profile);
        profile.getPlayer().setFlySpeed(0.1f);
    }

    @EventHandler
    public void onClickEvent(PlayerInteractEvent e){
        Profile profile = Profile.getByPlayer(e.getPlayer());

        if(!inCreatorMode(profile)){
            return;
        }

        if(e.getItem() == null || !e.getItem().hasItemMeta() || !e.getItem().getItemMeta().hasDisplayName()){
            return;
        }

        ItemStack item = e.getItem();
        e.setCancelled(true);

        GamePreset gamePreset = getGamePreset(profile);

        if(item.getItemMeta().getDisplayName().equalsIgnoreCase("§aSalvar")){
            profile.sendMessage("§eSalvando o Jogo...");

            ConfigFile file = gamePreset.getFile();

            file.getConfig().set("game.joinSpawn", SerializerCore.getLocationString(gamePreset.getJoinSpawn()));
            file.getConfig().set("game.world", gamePreset.getWorld().getName());

            List<String> spawns = new ArrayList<>();
            gamePreset.getSpawns().forEach(s-> spawns.add(SerializerCore.getLocationString(s)));
            file.getConfig().set("game.spawns", spawns);

            file.getConfig().set("game.cuboidGame.corner-1", SerializerCore.getLocationString(gamePreset.getCuboidGame1()));
            file.getConfig().set("game.cuboidGame.corner-2", SerializerCore.getLocationString(gamePreset.getCuboidGame2()));

            file.getConfig().set("game.cuboidSpawn.corner-1", SerializerCore.getLocationString(gamePreset.getCuboidSpawn1()));
            file.getConfig().set("game.cuboidSpawn.corner-2", SerializerCore.getLocationString(gamePreset.getCuboidSpawn2()));

            List<String> basic = new ArrayList<>();
            List<String> complement = new ArrayList<>();
            List<String> feast = new ArrayList<>();
            for(Location location : gamePreset.getChests().keySet()){
                switch (gamePreset.getChests().get(location)){
                    case BASIC:{
                        basic.add(SerializerCore.getLocationString(location));
                        break;
                    }
                    case COMPLEMENT:{
                        complement.add(SerializerCore.getLocationString(location));
                        break;
                    }
                    case FEAST:{
                        feast.add(SerializerCore.getLocationString(location));
                        break;
                    }
                }
            }
            file.getConfig().set("game.chests.basic", basic);
            file.getConfig().set("game.chests.complement", complement);
            file.getConfig().set("game.chests.feast", feast);

            GameCuboid cuboid = new GameCuboid(gamePreset.getCuboidGame1(), gamePreset.getCuboidGame2());

            file.save();
            cuboid.save(gamePreset.getName().toLowerCase(), "schematics");

            removeCreatorMode(profile);

            Game game = new Game(file.getConfig());
            Loader.registerGame(game);

            profile.sendMessage("");
            profile.sendMessage(" §2§lSUCESSO");
            profile.sendMessage(" §aVocê acabou de criar um novo jogo!");
            profile.sendMessage("");
            profile.sendMessage(" §aNome: §f"+gamePreset.getName());
            profile.sendMessage(" §aModo: §f"+gamePreset.getType().getName());
            profile.sendMessage(" §aMinimo: §f"+gamePreset.getMin());
            profile.sendMessage(" §aMaximo: §f"+gamePreset.getMax());
            profile.sendMessage(" §aBaus: §f"+gamePreset.getChests().size());
            profile.sendMessage("");
            return;
        }


        switch (item.getItemMeta().getDisplayName()){
            case "§aAdcionar Spawn":{
                if(e.getClickedBlock()==null || e.getClickedBlock().getType()==Material.AIR){
                    return;
                }

                if(gamePreset.getSpawns().size() >= gamePreset.getMax()){
                    profile.sendMessage("§cVocê já alcançou o limite de spawns desse jogo!");
                    return;
                }

                if(gamePreset.getSpawns().contains(e.getClickedBlock().getLocation())){
                    profile.sendMessage("§cJá existe um spawn setado nessa localização!");
                    return;
                }

                gamePreset.getSpawns().add(e.getClickedBlock().getLocation());
                profile.sendMessage("§aVocê adicionou um spawn para esta localização, quantidade atual: §f"+gamePreset.getSpawns().size()+"§a!");
                break;
            }

            case "§aSpawn de Entrada":{
                if(e.getClickedBlock()==null || e.getClickedBlock().getType()==Material.AIR){
                    return;
                }

                gamePreset.setJoinSpawn(e.getClickedBlock().getLocation().add(0.5,1,0.5));
                gamePreset.setWorld(e.getClickedBlock().getWorld());
                profile.sendMessage("§aVocê definiu o spawn de entrada deste jogo!");
                break;
            }

            case "§aCuboid do Jogo":{
                if(e.getClickedBlock()==null || e.getClickedBlock().getType()==Material.AIR){
                    return;
                }

                if(e.getAction() == Action.LEFT_CLICK_BLOCK){
                    gamePreset.setCuboidGame1(e.getClickedBlock().getLocation());
                    profile.sendMessage("§aVocê definiu a posição §f1§a do cuboid do jogo!");
                    return;
                }
                if(e.getAction() == Action.RIGHT_CLICK_BLOCK){
                    gamePreset.setCuboidGame2(e.getClickedBlock().getLocation());
                    profile.sendMessage("§aVocê definiu a posição §f2§a do cuboid do jogo!");
                }
                break;
            }

            case "§aCuboid do Spawn":{
                if(e.getClickedBlock()==null || e.getClickedBlock().getType()==Material.AIR){
                    return;
                }

                if(e.getAction() == Action.LEFT_CLICK_BLOCK){
                    gamePreset.setCuboidSpawn1(e.getClickedBlock().getLocation());
                    profile.sendMessage("§aVocê definiu a posição §f1§a do cuboid do spawn!");
                    return;
                }
                if(e.getAction() == Action.RIGHT_CLICK_BLOCK){
                    gamePreset.setCuboidSpawn2(e.getClickedBlock().getLocation());
                    profile.sendMessage("§aVocê definiu a posição §f2§a do cuboid do spawn!");
                }
                break;
            }

            case "§aAdcionar Bau Basico":{
                if(e.getClickedBlock()==null || e.getClickedBlock().getType()==Material.AIR){
                    return;
                }

                if(e.getClickedBlock().getType() != Material.CHEST){
                    profile.sendMessage("§cVocê precisa clicar em um bau!");
                    return;
                }

                if(e.getAction() == Action.LEFT_CLICK_BLOCK){
                    gamePreset.getChests().put(e.getClickedBlock().getLocation(), ChestClass.BASIC);
                    profile.sendMessage("§aVocê definou este bau para classe: Basico!");
                    return;
                }

                if(e.getAction() == Action.RIGHT_CLICK_BLOCK){
                    if(gamePreset.getChests().containsKey(e.getClickedBlock().getLocation())){
                        profile.sendMessage("§eEste bau é do tipo: §f"+gamePreset.getChests().get(e.getClickedBlock().getLocation()).getName());
                        return;
                    }
                    profile.sendMessage("§cEste bau não tem um tipo definido!");
                }
                break;
            }

            case "§aAdcionar Bau Complemento":{
                if(e.getClickedBlock()==null || e.getClickedBlock().getType()==Material.AIR){
                    return;
                }

                if(e.getClickedBlock().getType() != Material.CHEST){
                    profile.sendMessage("§cVocê precisa clicar em um bau!");
                    return;
                }

                if(e.getAction() == Action.LEFT_CLICK_BLOCK){
                    gamePreset.getChests().put(e.getClickedBlock().getLocation(), ChestClass.COMPLEMENT);
                    profile.sendMessage("§aVocê definou este bau para a classe: Complemento!");
                    return;
                }

                if(e.getAction() == Action.RIGHT_CLICK_BLOCK){
                    if(gamePreset.getChests().containsKey(e.getClickedBlock().getLocation())){
                        profile.sendMessage("§eEste bau é do tipo: §f"+gamePreset.getChests().get(e.getClickedBlock().getLocation()).getName());
                        return;
                    }
                    profile.sendMessage("§cEste bau não tem um tipo definido!");
                }
                break;
            }

            case "§aAdcionar Bau Feast":{
                if(e.getClickedBlock()==null || e.getClickedBlock().getType()==Material.AIR){
                    return;
                }

                if(e.getClickedBlock().getType() != Material.CHEST){
                    profile.sendMessage("§cVocê precisa clicar em um bau!");
                    return;
                }

                if(e.getAction() == Action.LEFT_CLICK_BLOCK){
                    gamePreset.getChests().put(e.getClickedBlock().getLocation(), ChestClass.FEAST);
                    profile.sendMessage("§aVocê definou este bau para a classe: Feast!");
                    return;
                }

                if(e.getAction() == Action.RIGHT_CLICK_BLOCK){
                    if(gamePreset.getChests().containsKey(e.getClickedBlock().getLocation())){
                        profile.sendMessage("§eEste bau é do tipo: §f"+gamePreset.getChests().get(e.getClickedBlock().getLocation()).getName());
                        return;
                    }
                    profile.sendMessage("§cEste bau não tem um tipo definido!");
                }
                break;
            }
        }
    }
}
