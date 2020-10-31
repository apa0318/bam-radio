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
import yt.bam.library.Helpers;
import yt.bam.library.ICommand;

public class CmdStop implements ICommand {
    public static final Logger logger = Bukkit.getLogger();

    public CmdStop() {
    }

    public void execute(CommandSender sender, String commandLabel, String[] args) {
        if (BAMradio.Instance.RadioManager.isNowPlaying()) {
            BAMradio.Instance.RadioManager.stopPlaying();
            Helpers.sendMessage(sender, BAMradio.Library.Translation.getTranslation("COMMAND_STOP_MESSAGE"));
        }

    }

    public String getHelp() {
        return BAMradio.Library.Translation.getTranslation("COMMAND_STOP_HELP");
    }

    public String getSyntax() {
        return "/br stop";
    }

    public Permission getPermissions() {
        return new Permission("bamradio.stop");
    }

    public String[] getName() {
        return new String[]{"stop"};
    }

    public String getExtendedHelp() {
        return null;
    }

    public boolean allowedInConsole() {
        return true;
    }
}
