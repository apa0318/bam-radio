//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package yt.bam.bamradio.radiomanager;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.midi.MidiUnavailableException;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import yt.bam.bamradio.BAMradio;
import yt.bam.library.Helpers;

public class RadioManager {
    public static final Logger logger = Bukkit.getLogger();
    public boolean AutoPlay;
    public boolean AutoPlayNext;
    public String RegionName = "";
    public int Volume = 0;
    public List<Player> tunedIn;
    public boolean nowPlaying = false;
    public String nowPlayingFile = "";
    public int nowPlayingIndex = 0;
    public boolean ForceSoftwareSequencer;
    private MidiPlayer MidiPlayer;
    private NoteBlockPlayer NoteBlockPlayer;

    public RadioManager(boolean autoPlay, boolean autoPlayNext, boolean forceSoftwareSequencer, String regionName, int volume) {
        this.AutoPlay = autoPlay;
        this.AutoPlayNext = autoPlayNext;
        this.ForceSoftwareSequencer = forceSoftwareSequencer;
        this.RegionName = regionName;
        this.Volume = volume;
        this.tunedIn = new ArrayList();
        if (BAMradio.Instance.NoteBlockAPI) {
            this.NoteBlockPlayer = new NoteBlockPlayer(this);
            BAMradio.Instance.getServer().getPluginManager().registerEvents(new SongListener(), BAMradio.Instance);
        } else {
            this.NoteBlockPlayer = null;
        }

    }

    public void tuneIn(Player player) {
        this.tunedIn.add(player);
        if (this.NoteBlockPlayer != null) {
            this.NoteBlockPlayer.tuneIn(player);
        }

        this.NowPlaying(player, false);
    }

    public void tuneOut(Player player) {
        this.tunedIn.remove(player);
        if (this.NoteBlockPlayer != null) {
            this.NoteBlockPlayer.tuneOut(player);
        }

    }

    public boolean isNowPlaying() {
        return this.nowPlaying;
    }

    public void playNextSong() {
        ++this.nowPlayingIndex;
        String[] midiFileNames = listRadioFiles();
        if (this.nowPlayingIndex >= midiFileNames.length) {
            this.nowPlayingIndex = 0;
        }

        this.playSong(midiFileNames[this.nowPlayingIndex]);
    }

    public void playRandomSong() {
        String[] midiFileNames = listRadioFiles();
        this.nowPlayingIndex = (int)(Math.random() * (double)(midiFileNames.length + 1));
        if (this.nowPlayingIndex >= midiFileNames.length) {
            this.nowPlayingIndex = 0;
        }

        this.playSong(midiFileNames[this.nowPlayingIndex]);
    }

    public boolean playSong(String fileName) {
        if (this.nowPlaying) {
            this.stopPlaying();
        }

        File file = this.getMidiFile(fileName);
        if (file != null && this.MidiPlayer != null) {
            return this.MidiPlayer.playSong(fileName);
        } else {
            file = this.getNoteBlockFile(fileName);
            if (file != null) {
                if (this.NoteBlockPlayer != null) {
                    return this.NoteBlockPlayer.playSong(fileName);
                }

                BAMradio.Instance.getLogger().log(Level.WARNING, "NoteBlockAPI not found, can not play NBS file!");
            }

            return false;
        }
    }

    public void stopPlaying() {
        this.MidiPlayer.stopPlaying();
        if (this.NoteBlockPlayer != null) {
            this.NoteBlockPlayer.stopPlaying();
        }

    }

    public File getMidiFile(String fileName) {
        File midiFile = new File(BAMradio.Instance.getDataFolder(), fileName.replace(".mid", "") + ".mid");
        return !midiFile.exists() ? null : midiFile;
    }

    public File getNoteBlockFile(String fileName) {
        File noteBlockFile = new File(BAMradio.Instance.getDataFolder(), fileName.replace(".nbs", "") + ".nbs");
        return !noteBlockFile.exists() ? null : noteBlockFile;
    }

    public static String[] listRadioFiles() {
        File[] files = BAMradio.Instance.getDataFolder().listFiles();
        List<String> radioFiles = new ArrayList();
        if (files == null) {
            return null;
        } else {
            File[] arr$ = files;
            int len$ = files.length;

            for(int i$ = 0; i$ < len$; ++i$) {
                File file = arr$[i$];
                if (file.getName().endsWith(".mid")) {
                    radioFiles.add(file.getName().substring(0, file.getName().lastIndexOf(".mid")));
                }

                if (BAMradio.Instance.NoteBlockAPI && file.getName().endsWith(".nbs")) {
                    radioFiles.add(file.getName().substring(0, file.getName().lastIndexOf(".nbs")));
                }
            }

            return (String[])radioFiles.toArray(new String[0]);
        }
    }

    public static String[] listRadioFilesWithExtensions() {
        File[] files = BAMradio.Instance.getDataFolder().listFiles();
        List<String> radioFiles = new ArrayList();
        File[] arr$ = files;
        int len$ = files.length;

        for(int i$ = 0; i$ < len$; ++i$) {
            File file = arr$[i$];
            if (file.getName().endsWith(".mid")) {
                radioFiles.add(file.getName());
            }

            if (BAMradio.Instance.NoteBlockAPI && file.getName().endsWith(".nbs")) {
                radioFiles.add(file.getName());
            }
        }

        return (String[])radioFiles.toArray(new String[0]);
    }

    public void onEnable() {
        if (this.ForceSoftwareSequencer) {
            this.MidiPlayer = new MinecraftMidiPlayer(this);
        } else {
            try {
                this.MidiPlayer = new SequencerMidiPlayer(this);
            } catch (MidiUnavailableException var6) {
                logger.severe(BAMradio.Library.Translation.getTranslation("MIDI_MANAGER_EXCEPTION_MIDI_UNAVAILABLE"));
                this.MidiPlayer = new MinecraftMidiPlayer(this);
            }
        }

        Player[] onlinePlayerList = BAMradio.Instance.getServer().getOnlinePlayers().toArray(new Player[0]);
        Player[] arr$ = onlinePlayerList;
        int len$ = onlinePlayerList.length;

        for(int i$ = 0; i$ < len$; ++i$) {
            Player player = arr$[i$];
            this.tuneIn(player);
        }

        if (this.AutoPlay) {
            String[] midis = listRadioFiles();
            if (midis != null && midis.length > 0) {
                this.playSong(midis[0]);
            }
        }

    }

    public void onDisable() {
        this.stopPlaying();
    }

    public void NowPlaying(CommandSender player, boolean force) {
        if (force || BAMradio.Library.Configuration.getBoolean("show-current-track", true)) {
            if (this.nowPlaying) {
                Helpers.sendMessage(player, BAMradio.Library.Translation.getTranslation("MIDI_MANAGER_NOW_PLAYING") + " " + ChatColor.YELLOW + this.nowPlayingFile.replace("_", " "));
            }

        }
    }
}
