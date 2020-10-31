//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package yt.bam.bamradio.radiomanager.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import yt.bam.bamradio.BAMradio;
import yt.bam.bamradio.radiomanager.listener.worldguard.RegionEnterEvent;
import yt.bam.bamradio.radiomanager.listener.worldguard.RegionLeaveEvent;

public class RegionListener implements Listener {
    public RegionListener() {
    }

    @EventHandler
    public void onRegionEnter(RegionEnterEvent e) {
        if (e.getRegion().getId() != null && !e.getRegion().getId().isEmpty() && e.getRegion().getId().toLowerCase().equals(BAMradio.Library.Configuration.getString("region", "").toLowerCase()) && (BAMradio.Library.Configuration.getString("world", "").equals("") || e.getPlayer().getWorld().getName().equals(BAMradio.Library.Configuration.getString("world", "").toLowerCase()))) {
            BAMradio.Instance.RadioManager.tuneOut(e.getPlayer());
            BAMradio.Instance.RadioManager.tuneIn(e.getPlayer());
        }

    }

    @EventHandler
    public void onRegionLeave(RegionLeaveEvent e) {
        if (e.getRegion().getId() != null && !e.getRegion().getId().isEmpty() && e.getRegion().getId().toLowerCase().equals(BAMradio.Library.Configuration.getString("region", "").toLowerCase()) && (BAMradio.Library.Configuration.getString("world", "").equals("") || e.getPlayer().getWorld().getName().equals(BAMradio.Library.Configuration.getString("world", "").toLowerCase()))) {
            BAMradio.Instance.RadioManager.tuneOut(e.getPlayer());
        }

    }
}
