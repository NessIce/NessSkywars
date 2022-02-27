package br.com.ness.skywars.game;

import br.com.ness.core.bukkit.CorePlugin;
import br.com.ness.core.bukkit.game.GameCore;
import br.com.ness.core.bukkit.itens.ItemBuilder;
import br.com.ness.core.bukkit.message.JsonMessage;
import br.com.ness.core.bukkit.profile.Profile;
import br.com.ness.core.bukkit.title.Titles;
import br.com.ness.core.bukkit.utils.SerializerCore;
import br.com.ness.skywars.Skywars;
import br.com.ness.skywars.game.chest.ChestClass;
import br.com.ness.skywars.game.chest.ChestManager;
import br.com.ness.skywars.game.team.GameTeam;
import br.com.ness.skywars.game.tasks.FinishingGameTask;
import br.com.ness.skywars.game.tasks.PlayingGameTask;
import br.com.ness.skywars.game.tasks.PreparingGameTask;
import br.com.ness.skywars.game.tasks.StartingGameTask;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.*;

public class Game implements GameCore {

    private FileConfiguration file;

    private String name;
    private GameType type;
    private GameStatus status;

    private int min;
    private int max;

    private Location joinSpawn;

    private List<Location> spawns;

    private GameCuboid cuboidGame;
    private GameCuboid cuboidSpawn;

    private Map<Location, ChestClass> chests;

    private List<Profile> players;
    private List<Profile> spectators;

    private List<GameTeam> gameTeams;
    private Map<GameTeam, Location> spawnTeams;
    private Map<GameTeam, GameCuboid> cageTeams;

    private Map<Profile, Integer> killPlayers;
    private Map<Profile, String> votePlayers;

    private String timeGamePlay;

    private PreparingGameTask preparingGameTask;
    private StartingGameTask startingGameTask;
    private PlayingGameTask playingGameTask;
    private FinishingGameTask finishingGameTask;


    public Game(FileConfiguration file){
        this.file = file;

        this.name = file.getString("game.name");
        this.type = GameType.valueOf(file.getString("game.type").toUpperCase());
        this.status = GameStatus.WAITING;

        this.min = file.getInt("game.min");
        this.max = file.getInt("game.max");

        this.joinSpawn = SerializerCore.getLocation(file.getString("game.joinSpawn"));

        this.spawns = new ArrayList<>();
        file.getStringList("game.spawns").forEach(s -> this.spawns.add(SerializerCore.getLocation(s).add(0,5,0)));

        Location cuboidGame1 = SerializerCore.getLocation(file.getString("game.cuboidGame.corner-1"));
        Location cuboidGame2 = SerializerCore.getLocation(file.getString("game.cuboidGame.corner-2"));
        this.cuboidGame = new GameCuboid(cuboidGame1, cuboidGame2);

        Location cuboidSpawn1 = SerializerCore.getLocation(file.getString("game.cuboidSpawn.corner-1"));
        Location cuboidSpawn2 = SerializerCore.getLocation(file.getString("game.cuboidSpawn.corner-2"));
        this.cuboidSpawn = new GameCuboid(cuboidSpawn1, cuboidSpawn2);

        this.chests = new HashMap<>();
        file.getStringList("game.chests.basic").forEach(s -> this.chests.put(SerializerCore.getLocation(s), ChestClass.BASIC));
        file.getStringList("game.chests.complement").forEach(s -> this.chests.put(SerializerCore.getLocation(s), ChestClass.COMPLEMENT));
        file.getStringList("game.chests.feast").forEach(s -> this.chests.put(SerializerCore.getLocation(s), ChestClass.FEAST));

        this.players = new ArrayList<>();
        this.spectators = new ArrayList<>();

        this.gameTeams = new ArrayList<>();
        this.spawnTeams = new HashMap<>();
        this.cageTeams = new HashMap<>();

        this.killPlayers = new HashMap<>();
        this.votePlayers = new HashMap<>();

        this.timeGamePlay = "00:00";

        this.preparingGameTask = new PreparingGameTask(this);
        this.startingGameTask = new StartingGameTask(this);
        this.playingGameTask = new PlayingGameTask(this);
        this.finishingGameTask = new FinishingGameTask(this);

        this.cuboidGame.paste(name.toLowerCase(), "schematics");
    }



    public void joinGame(Profile profile){

    }

    public void quitGame(Profile profile){

    }

    public void joinSpectator(Profile profile, boolean winner) {
//        profile.getPlayer().teleport(getSpawnsTeam().get(getTeamPlayer(profile)));
//
//        profile.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE,1), true);
//        profile.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 40,1), true);
//
//        int coinsKills = getKillsPlayer().get(profile)*10;
//        int coinsWins = winner?50:0;
//        int total = coinsKills+coinsWins;
//
//        profile.sendMessage("");
//        profile.sendMessage(" §a"+total+" coins ganhos nessa partida:");
//        profile.sendMessage("");
//        if(winner) profile.sendMessage("  §a+50 §fcoins por vencer o jogo");
//        profile.sendMessage("  §a+"+coinsKills+" §fcoins por §7"+getKillsPlayer().get(profile)+" §fabates");
//        profile.sendMessage("");
//
//        profile.sendMessage("");
//        JsonMessage vote = new JsonMessage();
//        vote.addText("§7Gostou do mapa? Deixe sua nota: ");
//        vote.addText("§4§l[1] ", ClickEvent.Action.RUN_COMMAND, "/vote horrivel", HoverEvent.Action.SHOW_TEXT,"§4Horrível");
//        vote.addText("§c§l[2] ", ClickEvent.Action.RUN_COMMAND, "/vote ruim", HoverEvent.Action.SHOW_TEXT,"§cRuim");
//        vote.addText("§6§l[3] ", ClickEvent.Action.RUN_COMMAND, "/vote aceitavel", HoverEvent.Action.SHOW_TEXT,"§6Aceitável");
//        vote.addText("§e§l[4] ", ClickEvent.Action.RUN_COMMAND, "/vote bom", HoverEvent.Action.SHOW_TEXT,"§eBom");
//        vote.addText("§a§l[5] ", ClickEvent.Action.RUN_COMMAND, "/vote otimo", HoverEvent.Action.SHOW_TEXT,"§aMuito Bom");
//        vote.sendMessage(profile.getPlayer());
//        profile.sendMessage("§7Dessa forma poderemos melhorar os futuros mapas!");
//        profile.sendMessage("");
//
//        profile.getPlayer().playSound(profile.getPlayer().getLocation(), Sound.ORB_PICKUP,1,1);
//        profile.addCoins(total);
//
//        CorePlugin.getCore().getFood().addPlayer(profile.getPlayer());
//
//        profile.getPlayer().setHealth(20.0);
//        profile.getPlayer().setFoodLevel(20);
//        profile.getPlayer().getInventory().clear();
//        profile.getPlayer().getInventory().setArmorContents(null);
//        profile.getPlayer().setGameMode(GameMode.ADVENTURE);
//        profile.getPlayer().setAllowFlight(true);
//        profile.getPlayer().setFlying(true);
//
//        profile.getPlayer().getInventory().setItem(2, new ItemBuilder(Material.COMPASS, "§aEspectar jogadores").getItem());
//        profile.getPlayer().getInventory().setItem(6, new ItemBuilder(Material.PAPER, "§aJogar novamente").getItem());
//
//        getSpectators().add(profile);
//        getTeams().forEach(t->t.forEach(p->p.getScoreManager().getActual().getTeam("a_spectator").addEntry(profile.getName())));
    }


    public void waitingGame(){
        setStatus(GameStatus.WAITING);

        //teamsForEach(t -> t.forEach(p-> p.getScoreManager().updateScore("waiting", "Aguardando...")));

        this.preparingGameTask.cancel();
        this.preparingGameTask = new PreparingGameTask(this);
    }

    public void preparingGame(){
        setStatus(GameStatus.PREPARING);
        this.preparingGameTask.runTaskTimer(Skywars.getPlugin(), 0, 20);
        sendMessage("§eA partida irá iniciar em §f60 §esegundos!");
    }

    public void startingGame(){
        setStatus(GameStatus.STARTING);

        getPlayers().forEach(this::addProfileTeam);

        getGameTeams().forEach(gameTeam -> getSpawnTeams().put(gameTeam, getSpawns().get(getGameTeams().indexOf(gameTeam))));
        getSpawnTeams().forEach((gameTeam, location)-> spawnCages(gameTeam, location, "padrao"));

        getGameTeams().forEach(this::teleportCages);

        getChests().forEach((location, chestClass)-> ChestManager.applyChest(location, getType(), chestClass));

        getCuboidSpawn().clear();

        this.startingGameTask.runTaskTimer(Skywars.getPlugin(), 0, 20);
        sendMessage("§bOs jogadores serão libertados em §f10 §bsegundos...");
    }

    public void playingGame(){
        setStatus(GameStatus.PLAYING);

        sendMessage("");
        sendMessage(" §e§lComo jogar SkyWars?");
        sendMessage("");
        sendMessage(" §7Utilize os itens encontrados em baús em sua ilha para se equipar, eliminar outros jogadores e chegar ao");
        sendMessage(" §7centro do mapa, onde encontrará itens ainda melhores. É um jogo rápido de muito combate. Vencerá o último");
        sendMessage(" §7jogador ou equipe viva.");
        sendMessage("");
        sendMessage("");
        sendMessage(" §b§lAVISO! §7Aliar-se a outras equipes ou jogadores não é permitido e resulta em punição. Denuncie por meio do");
        sendMessage(" §7comando §f/reportar§7.");
        sendMessage("");

//        cagesTeam.values().forEach(GameCuboid::clear);
//
//        teamsForEach(t -> t.forEach(p -> killsPlayer.put(p, 0)));
//        teamsForEach(t -> t.forEach(p -> p.getScoreManager().applyPlaying()));
//        teamsForEach(t -> t.forEach(p -> CorePlugin.getCore().getFood().removePlayer(p.getPlayer())));
//        teamsForEach(t -> t.forEach(p -> p.getPlayer().setGameMode(GameMode.SURVIVAL)));
//        teamsForEach(t -> t.forEach(p -> p.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 60,20))));
//        teamsForEach(t -> t.forEach(p -> p.getPlayer().getInventory().clear()));

        this.playingGameTask.runTaskTimer(Skywars.getPlugin(), 0, 20);
    }

    public void finishingGame(){
        setStatus(GameStatus.FINISHING);

        //GameTeam winner = getTeams().get(0);
//        sendMessage("§a"+winner.getName()+" §avenceu a partida!");
//
//        Titles titleWinner = new Titles("§a§lVITÓRIA", "§fVocê venceu!", 5, 200, 5);
//        winner.forEach(p -> titleWinner.send(p.getPlayer()));
//
//        for (Profile players : getSpectators()) {
//            Titles titleLoser = new Titles("§c§lFIM DE JOGO", "§7Vencedor: "+winner.getName(), 5, 200, 5);
//            titleLoser.send(players.getPlayer());
//        }
//
//        for(Profile profile : winner.getTeam()){ joinSpectator(profile, true); }
        //getTeams().remove(winner);

        this.finishingGameTask.runTaskTimer(Skywars.getPlugin(), 0, 20);
    }


    //Game Properties
    public FileConfiguration getFile() {
        return file;
    }
    public void setFile(FileConfiguration file) { this.file = file; }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }


    //Game Enums
    public GameType getType() {
        return type;
    }
    public void setType(GameType type) {
        this.type = type;
    }
    public GameStatus getStatus() {
        return status;
    }
    public void setStatus(GameStatus status) {
        this.status = status;
    }


    //Game Limites
    public int getMin() {
        return min;
    }
    public void setMin(int min) {
        this.min = min;
    }
    public int getMax() {
        return max;
    }
    public void setMax(int max) { this.max = max; }


    //Game Locations
    public Location getJoinSpawn() {
        return joinSpawn;
    }
    public void setJoinSpawn(Location joinSpawn) {
        this.joinSpawn = joinSpawn;
    }
    public List<Location> getSpawns() {
        return spawns;
    }
    public void setSpawns(List<Location> spawns) {
        this.spawns = spawns;
    }


    //Game Cuboids
    public GameCuboid getCuboidGame() {
        return cuboidGame;
    }
    public void setCuboidGame(GameCuboid cuboidGame) {
        this.cuboidGame = cuboidGame;
    }
    public GameCuboid getCuboidSpawn() {
        return cuboidSpawn;
    }
    public void setCuboidSpawn(GameCuboid cuboidSpawn) {
        this.cuboidSpawn = cuboidSpawn;
    }


    //Game Chests
    public Map<Location, ChestClass> getChests() {
        return chests;
    }
    public void setChests(Map<Location, ChestClass> chests) {
        this.chests = chests;
    }


    //Game Players
    public List<Profile> getPlayers() { return players; }
    public void setPlayers(List<Profile> players) { this.players = players; }
    public boolean isPlayer(Profile profile){ return players.contains(profile); }
    public List<Profile> getSpectators() {
        return spectators;
    }
    public void setSpectators(List<Profile> spectators) {
        this.spectators = spectators;
    }
    public boolean isSpectator(Profile profile){ return spectators.contains(profile); }


    //Game Location to Players
    public Map<GameTeam, Location> getSpawnTeams() { return spawnTeams; }
    public void setSpawnTeams(Map<GameTeam, Location> spawnTeams) { this.spawnTeams = spawnTeams; }
    public Map<GameTeam, GameCuboid> getCageTeams() { return cageTeams; }
    public void setCageTeams(Map<GameTeam, GameCuboid> cageTeams) { this.cageTeams = cageTeams; }


    //Game Player Informations
    public Map<Profile, Integer> getKillPlayers() { return killPlayers; }
    public void setKillPlayers(Map<Profile, Integer> killPlayers) { this.killPlayers = killPlayers; }
    public Map<Profile, String> getVotePlayers() { return votePlayers; }
    public void setVotePlayers(Map<Profile, String> votePlayers) { this.votePlayers = votePlayers; }


    //Game Informations
    public String getTimeGamePlay() {
        return timeGamePlay;
    }
    public void setTimeGamePlay(String timeGamePlay) {
        this.timeGamePlay = timeGamePlay;
    }


    //Game Tasks
    public PreparingGameTask getPreparingGameTask() {
        return preparingGameTask;
    }
    public void setPreparingGameTask(PreparingGameTask preparingGameTask) { this.preparingGameTask = preparingGameTask; }
    public StartingGameTask getStartingGameTask() {
        return startingGameTask;
    }
    public void setStartingGameTask(StartingGameTask startingGameTask) {
        this.startingGameTask = startingGameTask;
    }
    public PlayingGameTask getPlayingGameTask() {
        return playingGameTask;
    }
    public void setPlayingGameTask(PlayingGameTask playingGameTask) {
        this.playingGameTask = playingGameTask;
    }
    public FinishingGameTask getFinishingGameTask() {
        return finishingGameTask;
    }
    public void setFinishingGameTask(FinishingGameTask finishingGameTask) { this.finishingGameTask = finishingGameTask; }


    //Game Teams
    public List<GameTeam> getGameTeams() { return gameTeams; }
    public void setGameTeams(List<GameTeam> gameTeams) { this.gameTeams = gameTeams; }
    public void addProfileTeam(Profile profile){
        if(gameTeams.isEmpty()){
            gameTeams.add(new GameTeam(type, 0));
        }

        for(GameTeam team : gameTeams){
            if(team.hasSpace()){
                team.addPlayer(profile);
                return;
            }
        }

        GameTeam team = new GameTeam(getType(), gameTeams.size());
        gameTeams.add(team);
        team.addPlayer(profile);
    }
    public void removeProfileTeam(Profile profile){
        GameTeam team = getProfileTeam(profile);

        team.removePlayer(profile);
        if(team.getTeam().isEmpty()){
            getGameTeams().remove(team);
        }
    }
    public GameTeam getProfileTeam(Profile profile){
        for (GameTeam gameTeam : getGameTeams()){
            if(gameTeam.hasPlayer(profile)){
                return gameTeam;
            }
        }
        return null;
    }


    //Game Moderation
    public void spawnCages(GameTeam gameTeam, Location location, String cageName){
        GameCuboid cage = new GameCuboid(location.clone().subtract(1,1,1), location.clone().add(1,3,1));
        getCageTeams().put(gameTeam, cage);
        cage.paste(cageName, "cages");
    }
    public void teleportCages(GameTeam gameTeam){
        gameTeam.forEach(profile -> profile.getPlayer().teleport(getSpawnTeams().get(gameTeam).clone().add(0.5,1,0.5)));
    }
    public boolean isPlayable(){
        return getStatus() == GameStatus.WAITING || getStatus() == GameStatus.PREPARING;
    }
    public void sendMessage(String message){ getPlayers().forEach(p->p.sendMessage(message)); }
}
