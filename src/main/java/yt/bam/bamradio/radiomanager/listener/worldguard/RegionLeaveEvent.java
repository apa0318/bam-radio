//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package yt.bam.bamradio.radiomanager.listener.worldguard;

import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import yt.bam.bamradio.radiomanager.listener.MovementWay;

public class RegionLeaveEvent extends RegionEvent implements Cancellable {
    private boolean cancelled = false;
    private boolean cancellable = true;

    public RegionLeaveEvent(ProtectedRegion region, Player player, MovementWay movement) {
        super(region, player, movement);
        if (movement == MovementWay.SPAWN || movement == MovementWay.DISCONNECT) {
            this.cancellable = false;
        }

    }

    public void setCancelled(boolean cancelled) {
        if (this.cancellable) {
            this.cancelled = cancelled;
        }
    }

    public boolean isCancelled() {
        return this.cancelled;
    }

    public boolean isCancellable() {
        return this.cancellable;
    }

    protected void setCancellable(boolean cancellable) {
        this.cancellable = cancellable;
        if (!this.cancellable) {
            this.cancelled = false;
        }

    }
}
