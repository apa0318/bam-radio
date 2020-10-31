//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package yt.bam.bamradio.radiomanager;

import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.Sound;

public class Instrument {
    public static final Logger logger = Bukkit.getLogger();

    public Instrument() {
    }

    public static Sound getInstrument(int patch, int channel) {
        if (channel == 10) {
            return null;
        } else if (channel == 9) {
            return null;
        } else if (patch >= 0 && patch <= 7 || patch >= 80 && patch <= 103 || patch >= 64 && patch <= 71) {
            return Sound.NOTE_PIANO;
        } else if (patch >= 8 && patch <= 15) {
            return Sound.NOTE_PLING;
        } else if ((patch < 16 || patch > 23) && (patch < 44 || patch > 46)) {
            if ((patch < 28 || patch > 40) && (patch < 56 || patch > 63)) {
                if (patch >= 113 && patch <= 119) {
                    return Sound.NOTE_BASS_DRUM;
                } else if (patch >= 120 && patch <= 127) {
                    return Sound.NOTE_SNARE_DRUM;
                } else {
                    return patch >= 120 && patch <= 127 ? Sound.NOTE_SNARE_DRUM : Sound.NOTE_PLING;
                }
            } else {
                return Sound.NOTE_BASS;
            }
        } else {
            return Sound.NOTE_BASS_GUITAR;
        }
    }
}
