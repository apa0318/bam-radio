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
import java.util.Map;
import java.util.logging.Logger;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Receiver;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.ShortMessage;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import yt.bam.bamradio.BAMradio;

public class SequencerMidiPlayer implements MidiPlayer, Receiver {
    public static final Logger logger = Bukkit.getLogger();
    private final Sequencer sequencer;
    private final Map<Integer, Byte> channelPatches;
    private RadioManager manager;

    public SequencerMidiPlayer(RadioManager manager) throws MidiUnavailableException {
        this.manager = manager;
        manager.tunedIn = new ArrayList();
        this.channelPatches = new HashMap();
        this.sequencer = MidiSystem.getSequencer();
        this.sequencer.open();
        this.sequencer.getTransmitter().setReceiver(this);
    }

    public void stopPlaying() {
        this.sequencer.stop();
        BAMradio.Instance.getServer().getScheduler().cancelTasks(BAMradio.Instance);
    }

    public boolean playSong(String midiName) {
        try {
            this.manager.nowPlayingFile = midiName;
            File midiFile = this.manager.getMidiFile(midiName);
            if (midiFile == null) {
                return false;
            }

            try {
                Sequence midi = MidiSystem.getSequence(midiFile);
                this.sequencer.setSequence(midi);
                this.sequencer.start();
                this.manager.nowPlaying = true;
            } catch (InvalidMidiDataException var5) {
                System.err.println(BAMradio.Library.Translation.getTranslation("MIDI_MANAGER_INVALID_MIDI") + " " + midiName);
            } catch (IOException var6) {
                System.err.println(BAMradio.Library.Translation.getTranslation("MIDI_MANAGER_CORRUPT_MIDI") + " " + midiName);
            }

            Iterator i$ = this.manager.tunedIn.iterator();

            while(i$.hasNext()) {
                Player player = (Player)i$.next();
                this.manager.NowPlaying(player, false);
            }

            (new BukkitRunnable() {
                public void run() {
                    if (!SequencerMidiPlayer.this.manager.nowPlaying) {
                        this.cancel();
                    }

                    if (!SequencerMidiPlayer.this.sequencer.isRunning() || SequencerMidiPlayer.this.sequencer.getMicrosecondPosition() > SequencerMidiPlayer.this.sequencer.getMicrosecondLength()) {
                        SequencerMidiPlayer.this.stopPlaying();
                        if (SequencerMidiPlayer.this.manager.AutoPlayNext) {
                            SequencerMidiPlayer.this.manager.playNextSong();
                        }
                    }

                }
            }).runTaskTimer(BAMradio.Instance, 20L, 20L);
        } catch (Exception var7) {
            System.err.println(BAMradio.Library.Translation.getTranslation("MIDI_MANAGER_CORRUPT_MIDI") + " " + midiName + " (" + var7.getMessage() + ")");
        }

        return true;
    }

    protected void finalize() {
        this.sequencer.close();
    }

    public void close() {
    }

    public void send(MidiMessage message, long timeStamp) {
        if (message instanceof ShortMessage) {
            ShortMessage event = (ShortMessage)message;
            if (event.getCommand() == 144) {
                int midiNote = event.getData1();
                float volume = (float)(event.getData2() / 127);
                if (volume == 0.0F) {
                    volume = 1.0F;
                }

                volume += (float)BAMradio.Instance.RadioManager.Volume;
                int note = Integer.valueOf((midiNote - 6) % 24);
                int channel = event.getChannel();
                byte patch = 1;
                if (this.channelPatches.containsKey(channel)) {
                    patch = (Byte)this.channelPatches.get(channel);
                }

                Iterator i$ = this.manager.tunedIn.iterator();

                while(i$.hasNext()) {
                    Player player = (Player)i$.next();
                    Sound sound = Instrument.getInstrument(patch, channel);
                    if (sound != null) {
                        if (sound == Sound.BLOCK_NOTE_BLOCK_PLING) {
                            player.playSound(player.getLocation().add(0.0D, 20.0D, 0.0D), sound, volume, NotePitch.getPitch(note));
                        } else {
                            player.playSound(player.getLocation(), sound, volume, NotePitch.getPitch(note));
                        }
                    }
                }
            } else if (event.getCommand() == 192) {
                this.channelPatches.put(event.getChannel(), (byte)event.getData1());
            } else if (event.getCommand() == 252) {
                this.stopPlaying();
                this.manager.playNextSong();
            }

        }
    }
}
