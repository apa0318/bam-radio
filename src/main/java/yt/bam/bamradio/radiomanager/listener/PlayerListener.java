//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package yt.bam.bamradio.radiomanager.listener;

import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import yt.bam.bamradio.BAMradio;

public class PlayerListener implements Listener {
    public static Logger logger = Bukkit.getLogger();

    public PlayerListener() {
    }

    @EventHandler
    public static void onPlayerQuit(PlayerQuitEvent event) {
        if (BAMradio.Instance != null && BAMradio.Instance.RadioManager != null) {
            BAMradio.Instance.RadioManager.tuneOut(event.getPlayer());
        }

    }

    @EventHandler
    public static void onPlayerJoin(PlayerJoinEvent event) {
        if (BAMradio.Instance != null && BAMradio.Instance.RadioManager != null) {
            BAMradio.Instance.RadioManager.tuneIn(event.getPlayer());
        }

    }
}
