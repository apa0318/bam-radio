//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package yt.bam.bamradio.commands;

import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.permissions.Permission;
import yt.bam.bamradio.BAMradio;
import yt.bam.library.ICommand;

public class CmdNext implements ICommand {
    public static final Logger logger = Bukkit.getLogger();

    public CmdNext() {
    }

    public void execute(CommandSender sender, String commandLabel, String[] args) {
        if (BAMradio.Instance.RadioManager.isNowPlaying()) {
            BAMradio.Instance.RadioManager.stopPlaying();
        }

        BAMradio.Instance.RadioManager.playNextSong();
    }

    public String getHelp() {
        BAMradio var10000 = BAMradio.Instance;
        return BAMradio.Library.Translation.getTranslation("COMMAND_NEXT_HELP");
    }

    public String getSyntax() {
        return "/br next";
    }

    public Permission getPermissions() {
        return new Permission("bamradio.next");
    }

    public String[] getName() {
        return new String[]{"next"};
    }

    public String getExtendedHelp() {
        return null;
    }

    public boolean allowedInConsole() {
        return true;
    }
}
