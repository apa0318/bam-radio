//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package yt.bam.bamradio.radiomanager;

import com.xxmicloxx.NoteBlockAPI.NBSDecoder;
import com.xxmicloxx.NoteBlockAPI.RadioSongPlayer;
import com.xxmicloxx.NoteBlockAPI.Song;
import com.xxmicloxx.NoteBlockAPI.SongPlayer;
import java.io.File;
import java.util.Iterator;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import yt.bam.bamradio.BAMradio;

public class NoteBlockPlayer implements MidiPlayer, Listener {
    private RadioManager manager;
    public SongPlayer Player;

    public NoteBlockPlayer(RadioManager manager) {
        this.manager = manager;
    }

    public void tuneIn(Player player) {
        if (this.Player != null) {
            this.Player.addPlayer(player);
        }

    }

    public void tuneOut(Player player) {
        if (this.Player != null) {
            this.Player.removePlayer(player);
        }

    }

    public void stopPlaying() {
        if (this.Player != null) {
            this.Player.setPlaying(false);
        }

        this.manager.nowPlaying = false;
    }

    public boolean playSong(String fileName) {
        this.manager.nowPlayingFile = fileName;
        this.manager.nowPlaying = true;

        try {
            Song s = NBSDecoder.parse(new File(BAMradio.Instance.getDataFolder(), fileName + ".nbs"));
            this.Player = new RadioSongPlayer(s);
            this.Player.setAutoDestroy(false);
            Iterator i$ = this.manager.tunedIn.iterator();

            while(i$.hasNext()) {
                Player player = (Player)i$.next();
                this.Player.addPlayer(player);
                this.manager.NowPlaying(player, false);
            }

            this.Player.setPlaying(true);
            return true;
        } catch (Exception var5) {
            System.err.println(BAMradio.Library.Translation.getTranslation("RADIO_MANAGER_CORRUPT_NBS") + " " + fileName + " (" + var5.getMessage() + ")");
            return false;
        }
    }
}
