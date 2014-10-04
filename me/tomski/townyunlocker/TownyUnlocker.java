package me.tomski.townyunlocker;

import java.lang.String;
import com.palmergames.bukkit.towny.exceptions.NotRegisteredException;
import com.griefcraft.model.Protection;
import java.util.Iterator;
import java.lang.Object;
import org.bukkit.Material;
import org.bukkit.World;
import java.util.ArrayList;
import me.tomski.townyunlocker.Vec3;
import com.palmergames.bukkit.towny.object.WorldCoord;
import com.palmergames.bukkit.towny.TownySettings;
import org.bukkit.plugin.Plugin;
import org.bukkit.event.Listener;
import me.tomski.townyunlocker.TownyListener;
import java.lang.Integer;
import java.util.List;
import com.griefcraft.lwc.LWC;
import org.bukkit.plugin.java.JavaPlugin;

public class TownyUnlocker extends JavaPlugin
{
    public LWC ELUUC;
    public int townBlockSize;
    private List<Integer> plotUnclaimIds;
    
    public void onEnable() {
        this.getConfig().options().copyDefaults(true);
        this.saveConfig();
        this.getServer().getPluginManager().registerEvents((Listener)new TownyListener(this), (Plugin)this);
        this.ELUUC = LWC.getInstance();
        this.townBlockSize = TownySettings.getTownBlockSize();
        this.plotUnclaimIds = (List<Integer>)this.getConfig().getIntegerList("unclaim-unlock-list");
    }
    
    public void onDisable() {
    }
    
    public List<Vec3> getPlotCoords(final WorldCoord coord) {
        final Vec3 newcoord = this.parseToRealCoords(coord);
        final List<Vec3> list = new ArrayList<Vec3>();
        for (int x = newcoord.getX(); x < newcoord.getX() + this.townBlockSize; ++x) {
            for (int z = newcoord.getZ(); z < newcoord.getZ() + this.townBlockSize; ++z) {
                list.add(new Vec3(x, z));
            }
        }
        return list;
    }
    
    private Vec3 parseToRealCoords(final WorldCoord coord) {
        final Vec3 vec = new Vec3(coord.getX() * 16, coord.getZ() * 16);
        return vec;
    }
    
    public void plotClearRemoveProtections(final List<Vec3> coords, final World world) throws NotRegisteredException {
        for (final Vec3 coord : coords) {
            for (int y = 0; y < 256; ++y) {
                final Protection prot = this.ELUUC.findProtection(world.getBlockAt(coord.getX(), y, coord.getZ()));
                if (prot != null) {
                    if (prot.getBlock().getType().equals((Object)Material.AIR)) {
                        this.removeProtection(prot);
                    }
                }
            }
        }
    }
    
    public void townUnclaimRemoveProtections(final List<Vec3> coords, final World world) throws NotRegisteredException {
        for (final Vec3 coord : coords) {
            for (int y = 0; y < 256; ++y) {
                final Protection prot = this.ELUUC.findProtection(world.getBlockAt(coord.getX(), y, coord.getZ()));
                if (prot != null) {
                    if (this.plotUnclaimIds.contains(prot.getBlockId())) {
                        this.removeProtection(prot);
                    }
                    else if (prot.getBlock().getType().equals((Object)Material.AIR)) {
                        this.removeProtection(prot);
                    }
                }
            }
        }
    }
    
    private void removeProtection(final Protection prot) {
        final String playername = prot.getOwner();
        if (this.getServer().getPlayer(playername).isOnline()) {
            this.getServer().getPlayer(playername).sendMessage("Your locks on a plot belonging to you have been unlocked!");
        }
        prot.remove();
        prot.removeAllPermissions();
        prot.removeCache();
    }
}
