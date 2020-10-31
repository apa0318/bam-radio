//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package yt.bam.bamradio.radiomanager;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Logger;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MetaMessage;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Track;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import yt.bam.bamradio.BAMradio;

public class MinecraftMidiPlayer implements MidiPlayer {
    public static final Logger logger = Bukkit.getLogger();
    public static final long MILLIS_PER_TICK = 3L;
    private final List<MidiTrack> midiTracks;
    private final Map<Integer, Integer> channelPatches;
    private float tempo;
    private int resolution;
    private long timeLeft;
    private float currentTick = 0.0F;
    private Timer timer;
    private RadioManager manager;

    public MinecraftMidiPlayer(RadioManager manager) {
        this.manager = manager;
        this.timer = new Timer();
        this.midiTracks = new ArrayList();
        this.channelPatches = new HashMap();
    }

    public void stopPlaying() {
        synchronized(this.midiTracks) {
            this.manager.nowPlaying = false;
            this.midiTracks.clear();
            this.timer.cancel();
            this.timer = new Timer();
        }
    }

    public boolean playSong(String midiName) {
        this.manager.nowPlayingFile = midiName;
        File midiFile = this.manager.getMidiFile(midiName);
        if (midiFile == null) {
            return false;
        } else {
            try {
                Sequence midi = MidiSystem.getSequence(midiFile);
                int microsPerQuarterNote = 0;
                Track firstTrack = midi.getTracks()[0];

                int i;
                label49:
                for(i = 0; i < firstTrack.size(); ++i) {
                    if (firstTrack.get(i).getMessage().getStatus() == 255 && firstTrack.get(i).getMessage().getMessage()[1] == 81) {
                        MetaMessage message = (MetaMessage)firstTrack.get(i).getMessage();
                        byte[] data = message.getData();
                        byte[] arr$ = data;
                        int len$ = data.length;
                        int i$ = 0;

                        while(true) {
                            if (i$ >= len$) {
                                break label49;
                            }

                            byte b = arr$[i$];
                            microsPerQuarterNote <<= 8;
                            microsPerQuarterNote += b;
                            ++i$;
                        }
                    }
                }

                this.tempo = 500000.0F / (float)microsPerQuarterNote * 0.8F * 0.15F;
                this.timeLeft = midi.getMicrosecondLength() / 1000L;
                this.resolution = (int)Math.floor((double)(midi.getResolution() / 24));

                for(i = 0; i < midi.getTracks().length; ++i) {
                    MidiTrack midiTrack = new MidiTrack(this, midi.getTracks()[i]);
                    this.midiTracks.add(midiTrack);
                }
            } catch (InvalidMidiDataException var13) {
                System.err.println(BAMradio.Library.Translation.getTranslation("MIDI_MANAGER_INVALID_MIDI") + " " + midiName);
            } catch (IOException var14) {
                System.err.println(BAMradio.Library.Translation.getTranslation("MIDI_MANAGER_CORRUPT_MIDI") + " " + midiName);
            }

            Iterator i$ = this.manager.tunedIn.iterator();

            while(i$.hasNext()) {
                Player player = (Player)i$.next();
                this.manager.NowPlaying(player, false);
            }

            this.timer.scheduleAtFixedRate(new MinecraftMidiPlayer.TickTask(), 3L, 3L);
            return true;
        }
    }

    public void onMidiMessage(MidiMessage event) {
        int microsPerQuarterNote;
        int channel;
        int i$;
        if (event instanceof ShortMessage) {
            ShortMessage message = (ShortMessage)event;
            if (message.getCommand() == 144) {
                microsPerQuarterNote = message.getData1();
                float volume = (float)message.getData2();
                if (volume == 0.0F) {
                    volume = 1.0F;
                }

                volume += (float)BAMradio.Instance.RadioManager.Volume;
                int note = Integer.valueOf((microsPerQuarterNote - 6) % 24);
                channel = message.getChannel();
                i$ = 1;
                if (this.channelPatches.containsKey(channel)) {
                    i$ = (Integer)this.channelPatches.get(channel);
                }

                Sound instrument = Instrument.getInstrument(i$, channel);
                float notePitch = NotePitch.getPitch(note);
                if (instrument != null) {
                    Iterator iterator = this.manager.tunedIn.iterator();

                    while(iterator.hasNext()) {
                        Player player = (Player)iterator.next();
                        player.playSound(player.getLocation(), instrument, volume, notePitch);
                    }
                }
            } else if (message.getCommand() == 192) {
                this.channelPatches.put(message.getChannel(), message.getData1());
            } else if (message.getCommand() == 252) {
                this.stopPlaying();
                this.manager.playNextSong();
            }
        } else if (event instanceof MetaMessage) {
            MetaMessage message = (MetaMessage)event;
            if (message.getType() == 81) {
                microsPerQuarterNote = 0;
                byte[] data = message.getData();
                byte[] arr$ = data;
                channel = data.length;

                for(i$ = 0; i$ < channel; ++i$) {
                    byte b = arr$[i$];
                    microsPerQuarterNote <<= 8;
                    microsPerQuarterNote += b;
                }

                this.tempo = 500000.0F / (float)microsPerQuarterNote * 0.8F * 0.15F;
            }
        }

    }

    public class TickTask extends TimerTask {
        public TickTask() {
            MinecraftMidiPlayer.this.manager.nowPlaying = true;
            MinecraftMidiPlayer.this.currentTick = 0.0F;
        }

        public void run() {
            if (MinecraftMidiPlayer.this.manager.nowPlaying) {
                MinecraftMidiPlayer.this.currentTick = MinecraftMidiPlayer.this.tempo * (float)MinecraftMidiPlayer.this.resolution;
                synchronized(MinecraftMidiPlayer.this.midiTracks) {
                    Iterator i$ = MinecraftMidiPlayer.this.midiTracks.iterator();

                    while(true) {
                        if (!i$.hasNext()) {
                            break;
                        }

                        MidiTrack track = (MidiTrack)i$.next();
                        track.nextTick(MinecraftMidiPlayer.this.currentTick);
                    }
                }

                MinecraftMidiPlayer.this.timeLeft = 3L;
                if (MinecraftMidiPlayer.this.timeLeft <= 0L) {
                    MinecraftMidiPlayer.this.stopPlaying();
                    if (MinecraftMidiPlayer.this.manager.AutoPlayNext) {
                        (new BukkitRunnable() {
                            public void run() {
                                MinecraftMidiPlayer.this.manager.playNextSong();
                            }
                        }).runTask(BAMradio.Instance);
                    }
                }
            } else {
                this.cancel();
            }

        }
    }
}
