//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package yt.bam.bamradio.radiomanager.listener.worldguard;

import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import org.bukkit.entity.Player;
import yt.bam.bamradio.radiomanager.listener.MovementWay;

public class RegionLeftEvent extends RegionEvent {
    public RegionLeftEvent(ProtectedRegion region, Player player, MovementWay movement) {
        super(region, player, movement);
    }
}
