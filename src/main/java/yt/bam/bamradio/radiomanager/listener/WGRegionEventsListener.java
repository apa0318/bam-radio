//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package yt.bam.bamradio.radiomanager.listener;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import yt.bam.bamradio.BAMradio;
import yt.bam.bamradio.radiomanager.listener.worldguard.RegionEnterEvent;
import yt.bam.bamradio.radiomanager.listener.worldguard.RegionEnteredEvent;
import yt.bam.bamradio.radiomanager.listener.worldguard.RegionLeaveEvent;
import yt.bam.bamradio.radiomanager.listener.worldguard.RegionLeftEvent;

public class WGRegionEventsListener implements Listener {
    private Map<Player, Set<ProtectedRegion>> playerRegions = new HashMap();

    public WGRegionEventsListener() {
    }

    @EventHandler
    public void onPlayerKick(PlayerKickEvent e) {
        Set<ProtectedRegion> regions = (Set)this.playerRegions.remove(e.getPlayer());
        if (regions != null) {
            Iterator i$ = regions.iterator();

            while(i$.hasNext()) {
                ProtectedRegion region = (ProtectedRegion)i$.next();
                RegionLeaveEvent leaveEvent = new RegionLeaveEvent(region, e.getPlayer(), MovementWay.DISCONNECT);
                RegionLeftEvent leftEvent = new RegionLeftEvent(region, e.getPlayer(), MovementWay.DISCONNECT);
                BAMradio.Instance.getServer().getPluginManager().callEvent(leaveEvent);
                BAMradio.Instance.getServer().getPluginManager().callEvent(leftEvent);
            }
        }

    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {
        Set<ProtectedRegion> regions = (Set)this.playerRegions.remove(e.getPlayer());
        if (regions != null) {
            Iterator i$ = regions.iterator();

            while(i$.hasNext()) {
                ProtectedRegion region = (ProtectedRegion)i$.next();
                RegionLeaveEvent leaveEvent = new RegionLeaveEvent(region, e.getPlayer(), MovementWay.DISCONNECT);
                RegionLeftEvent leftEvent = new RegionLeftEvent(region, e.getPlayer(), MovementWay.DISCONNECT);
                BAMradio.Instance.getServer().getPluginManager().callEvent(leaveEvent);
                BAMradio.Instance.getServer().getPluginManager().callEvent(leftEvent);
            }
        }

    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e) {
        e.setCancelled(this.updateRegions(e.getPlayer(), MovementWay.MOVE, e.getTo()));
    }

    @EventHandler
    public void onPlayerTeleport(PlayerTeleportEvent e) {
        e.setCancelled(this.updateRegions(e.getPlayer(), MovementWay.TELEPORT, e.getTo()));
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        this.updateRegions(e.getPlayer(), MovementWay.SPAWN, e.getPlayer().getLocation());
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent e) {
        this.updateRegions(e.getPlayer(), MovementWay.SPAWN, e.getRespawnLocation());
    }

    private synchronized boolean updateRegions(final Player player, final MovementWay movement, Location to) {
        HashSet regions;
        if (this.playerRegions.get(player) == null) {
            regions = new HashSet();
        } else {
            regions = new HashSet((Collection)this.playerRegions.get(player));
        }

        Set<ProtectedRegion> oldRegions = new HashSet(regions);
        RegionManager rm = WorldGuard.getInstance().getPlatform().getRegionContainer().get(BukkitAdapter.adapt(to.getWorld()));
        if (rm == null) {
            return false;
        } else {
            ApplicableRegionSet appRegions = rm.getApplicableRegions(BlockVector3.at(to.getX(),to.getY(),to.getZ()));
            Iterator i$ = appRegions.iterator();

            while(i$.hasNext()) {
                final ProtectedRegion region = (ProtectedRegion)i$.next();
                if (!regions.contains(region)) {
                    RegionEnterEvent e = new RegionEnterEvent(region, player, movement);
                    BAMradio.Instance.getServer().getPluginManager().callEvent(e);
                    if (e.isCancelled()) {
                        regions.clear();
                        regions.addAll(oldRegions);
                        return true;
                    }

                    RegionEnteredEvent regionEnteredEvent = new RegionEnteredEvent(region, player, movement);
                    BAMradio.Instance.getServer().getPluginManager().callEvent(regionEnteredEvent);

                    regions.add(region);
                }
            }

            Collection<ProtectedRegion> app = (Collection)this.getPrivateValue(appRegions, "applicable");
            Iterator itr = regions.iterator();

            while(itr.hasNext()) {
                final ProtectedRegion region = (ProtectedRegion)itr.next();
                if (!app.contains(region)) {
                    if (rm.getRegion(region.getId()) != region) {
                        itr.remove();
                    } else {
                        RegionLeaveEvent e = new RegionLeaveEvent(region, player, movement);
                        BAMradio.Instance.getServer().getPluginManager().callEvent(e);
                        if (e.isCancelled()) {
                            regions.clear();
                            regions.addAll(oldRegions);
                            return true;
                        }

                        RegionLeftEvent regionLeftEvent = new RegionLeftEvent(region, player, movement);
                        BAMradio.Instance.getServer().getPluginManager().callEvent(regionLeftEvent);

                        itr.remove();
                    }
                }
            }

            this.playerRegions.put(player, regions);
            return false;
        }
    }

    private Object getPrivateValue(Object obj, String name) {
        try {
            Field f = obj.getClass().getDeclaredField(name);
            f.setAccessible(true);
            return f.get(obj);
        } catch (Exception var4) {
            return null;
        }
    }
}
