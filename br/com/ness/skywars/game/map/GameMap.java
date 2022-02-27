package br.com.ness.skywars.game.map;

import br.com.ness.skywars.Skywars;
import br.com.ness.skywars.game.GameType;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.map.MapCanvas;
import org.bukkit.map.MapPalette;
import org.bukkit.map.MapView;
import org.bukkit.map.MinecraftFont;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class GameMap implements Cloneable{

    private ItemStack map;
    private MapView mapView;

    public GameMap(GameType gameType, String player, String kills, String coins, String time){
        ItemStack i = new ItemStack(Material.MAP, 1);
        MapView view = Bukkit.createMap(Bukkit.getWorlds().get(0));
        ItemMeta meta = i.getItemMeta();
        meta.setDisplayName("§aResumo da partida");
        i.setItemMeta(meta);

        view.setScale(MapView.Scale.FARTHEST);

        view.getRenderers().forEach(view::removeRenderer);

        view.addRenderer(new MapImage(gameType.getType()));
        view.addRenderer(new MapResume(player, kills, coins, time));
        i.setDurability(view.getId());
        this.map = i;
        this.mapView = view;
    }


    private final String player;
    private final String kills;
    private final String coins;
    private final String time;

    public MapResume(String player, String kills, String coins, String time){
        this.player = player;
        this.kills = kills;
        this.coins = coins;
        this.time = time;
    }

    @Override
    public void render(MapView mapView, MapCanvas mapCanvas, Player player) {
        try {
            mapCanvas.drawText(33, 53, MinecraftFont.Font, "§"+ MapPalette.matchColor(Color.BLACK)+";"+this.player);
            mapCanvas.drawText(96, 77, MinecraftFont.Font, "§"+MapPalette.matchColor(Color.BLACK)+";"+this.kills);
            mapCanvas.drawText(96, 94, MinecraftFont.Font, "§"+MapPalette.matchColor(Color.BLACK)+";"+this.coins);
            mapCanvas.drawText(85, 111, MinecraftFont.Font, "§"+MapPalette.matchColor(Color.BLACK)+";"+this.time);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ItemStack getMap() {
        return map;
    }

    public void setMap(ItemStack map) {
        this.map = map;
    }

    public MapView getMapView() {
        return mapView;
    }

    public void setMapView(MapView mapView) {
        this.mapView = mapView;
    }
}
