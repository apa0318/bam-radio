//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package yt.bam.bamradio.radiomanager;

import java.util.logging.Logger;
import javax.sound.midi.Track;
import org.bukkit.Bukkit;

public class MidiTrack {
    public static final Logger logger = Bukkit.getLogger();
    private final MinecraftMidiPlayer player;
    private final Track track;
    private int event = 0;

    public MidiTrack(MinecraftMidiPlayer player, Track track) {
        this.player = player;
        this.track = track;
    }

    public void nextTick(float currentTick) {
        while(this.event < this.track.size() - 1 && (float)this.track.get(this.event).getTick() <= currentTick) {
            this.player.onMidiMessage(this.track.get(this.event).getMessage());
            ++this.event;
        }

    }
}
