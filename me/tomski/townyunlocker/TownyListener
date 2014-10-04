package me.tomski.townyunlocker;

import com.palmergames.bukkit.towny.event.PlotClearEvent;
import org.bukkit.event.EventHandler;
import com.palmergames.bukkit.towny.exceptions.NotRegisteredException;
import com.palmergames.bukkit.towny.event.TownUnclaimEvent;
import me.tomski.townyunlocker.TownyUnlocker;
import org.bukkit.event.Listener;

public class TownyListener implements Listener
{
    private TownyUnlocker plugin;
    
    public TownyListener(final TownyUnlocker townyUnlocker) {
        super();
        this.plugin = townyUnlocker;
    }
    
    @EventHandler
    public void onUnclaim(final TownUnclaimEvent e) throws NotRegisteredException {
        this.plugin.townUnclaimRemoveProtections(this.plugin.getPlotCoords(e.getWorldCoord()), this.plugin.getServer().getWorld(e.getTown().getWorld().getName()));
    }
    
    @EventHandler
    public void onPlotClear(final PlotClearEvent e) throws NotRegisteredException {
        this.plugin.plotClearRemoveProtections(this.plugin.getPlotCoords(e.getTownBlock().getWorldCoord()), this.plugin.getServer().getWorld(e.getTownBlock().getWorld().getName()));
    }
}
