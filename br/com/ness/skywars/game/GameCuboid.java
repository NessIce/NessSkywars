package br.com.ness.skywars.game;

import br.com.ness.skywars.Skywars;
import com.sk89q.worldedit.*;
import com.sk89q.worldedit.blocks.BaseBlock;
import com.sk89q.worldedit.blocks.BlockType;
import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.extent.Extent;
import com.sk89q.worldedit.extent.clipboard.BlockArrayClipboard;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormat;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardReader;
import com.sk89q.worldedit.function.mask.ExistingBlockMask;
import com.sk89q.worldedit.function.operation.ForwardExtentCopy;
import com.sk89q.worldedit.function.operation.Operation;
import com.sk89q.worldedit.function.operation.Operations;
import com.sk89q.worldedit.regions.CuboidRegion;
import com.sk89q.worldedit.session.ClipboardHolder;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class GameCuboid {

    private World world;

    private int minX;
    private int minY;
    private int minZ;
    private int maxX;
    private int maxY;
    private int maxZ;

    public GameCuboid(Location location1, Location location2){
        world = location1.getWorld();

        minX = Math.min(location1.getBlockX(), location2.getBlockX());
        minY = Math.min(location1.getBlockY(), location2.getBlockY());
        minZ = Math.min(location1.getBlockZ(), location2.getBlockZ());

        maxX = Math.max(location1.getBlockX(), location2.getBlockX());
        maxY = Math.max(location1.getBlockY(), location2.getBlockY());
        maxZ = Math.max(location1.getBlockZ(), location2.getBlockZ());
    }

    public List<Block> getBlocks(){
        List<Block> blocks = new ArrayList<>();
        for(int x = minX ; x<maxX;x++){
            for(int y = minY ; y<maxY;y++){
                for(int z = minZ ; z<maxZ;z++){
                    blocks.add(world.getBlockAt(x,y,z));
                }
            }
        }
        return blocks;
    }


    public void save(String schematicName, String folder) {
        try {
            Vector pos1 = new Vector(getMinX(), getMinY(), getMinZ());
            Vector pos2 = new Vector(getMaxX(), getMaxY(), getMaxZ());

            File schematic = new File(Skywars.getPlugin().getDataFolder().getPath() + "/"+folder+"/", schematicName + ".schematic");
            File dir = new File(Skywars.getPlugin().getDataFolder().getPath() + "/"+folder+"/");
            if (!dir.exists()) {
                dir.mkdirs();
            }
            EditSession editSession = WorldEdit.getInstance().getEditSessionFactory().getEditSession(new BukkitWorld(getMinPoint().getWorld()), -1);
            editSession.enableQueue();
            CuboidRegion region = new CuboidRegion(editSession.getWorld(), pos1, pos2);
            BlockArrayClipboard clipboard = new BlockArrayClipboard(region);
            Extent source = WorldEdit.getInstance().getEditSessionFactory().getEditSession(region.getWorld(), -1);
            ForwardExtentCopy copy = new ForwardExtentCopy(source, region, clipboard.getOrigin(), clipboard, pos1);
            copy.setSourceMask(new ExistingBlockMask(source));
            Operations.completeLegacy(copy);
            ClipboardFormat.SCHEMATIC.getWriter(new FileOutputStream(schematic)).write(clipboard, editSession.getWorld().getWorldData());
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void paste(String schematicName, String folder) {
        try {
            File dir = new File(Skywars.getPlugin().getDataFolder().getPath() + "/"+folder+"/", schematicName + ".schematic");
            EditSession editSession = WorldEdit.getInstance().getEditSessionFactory().getEditSession(new BukkitWorld(getMinPoint().getWorld()), -1);
            editSession.enableQueue();
            com.sk89q.worldedit.world.World w = editSession.getWorld();
            ClipboardFormat format = ClipboardFormat.findByFile(dir);
            assert format != null;
            ClipboardReader reader = format.getReader(new FileInputStream(dir));
            Clipboard clipboard = reader.read(w.getWorldData());
            Operation operation = new ClipboardHolder(clipboard, w.getWorldData()).createPaste(editSession, w.getWorldData()).to(new Vector(getMinPoint().getX(), getMinPoint().getY(), getMinPoint().getZ())).ignoreAirBlocks(false).build();
            Operations.complete(operation);
            editSession.flushQueue();
        }catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void clear(){
        Vector min = new Vector(getMinX(), getMinY(), getMinZ());
        Vector max = new Vector(getMaxX(), getMaxY(), getMaxZ());

        CuboidRegion selection = new CuboidRegion(new BukkitWorld(getMinPoint().getWorld()), min, max);
        try{
            EditSession editSession = WorldEdit.getInstance().getEditSessionFactory().getEditSession(new BukkitWorld(getMinPoint().getWorld()), -1);
            editSession.setBlocks(selection, new BaseBlock(BlockType.AIR.getID()));
            editSession.flushQueue();
        } catch (MaxChangedBlocksException ex) {
            ex.printStackTrace();
        }
    }

    public Location getMinPoint(){
        return new Location(world, minX, minY, minZ);
    }

    public Location getMaxPoint(){
        return new Location(world, maxX, maxY, maxZ);
    }

    public int getMinX() {
        return minX;
    }

    public void setMinX(int minX) {
        this.minX = minX;
    }

    public int getMinY() {
        return minY;
    }

    public void setMinY(int minY) {
        this.minY = minY;
    }

    public int getMinZ() {
        return minZ;
    }

    public void setMinZ(int minZ) {
        this.minZ = minZ;
    }

    public int getMaxX() {
        return maxX;
    }

    public void setMaxX(int maxX) {
        this.maxX = maxX;
    }

    public int getMaxY() {
        return maxY;
    }

    public void setMaxY(int maxY) {
        this.maxY = maxY;
    }

    public int getMaxZ() {
        return maxZ;
    }

    public void setMaxZ(int maxZ) {
        this.maxZ = maxZ;
    }

    public World getWorld() {
        return world;
    }

    public void setWorld(World world) {
        this.world = world;
    }
}
