//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package yt.bam.bamradio.commands;

import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;
import yt.bam.bamradio.BAMradio;
import yt.bam.library.Helpers;
import yt.bam.library.ICommand;

public class CmdMute implements ICommand {
    public static final Logger logger = Bukkit.getLogger();

    public CmdMute() {
    }

    public void execute(CommandSender sender, String commandLabel, String[] args) {
        BAMradio.Instance.RadioManager.tuneOut((Player)sender);
        Helpers.sendMessage(sender, BAMradio.Library.Translation.getTranslation("COMMAND_MUTE_MESSAGE"));
    }

    public String getHelp() {
        return BAMradio.Library.Translation.getTranslation("COMMAND_MUTE_HELP");
    }

    public String getSyntax() {
        return "/br mute";
    }

    public Permission getPermissions() {
        return new Permission("bamradio.mute");
    }

    public String[] getName() {
        return new String[]{"mute"};
    }

    public String getExtendedHelp() {
        return null;
    }

    public boolean allowedInConsole() {
        return false;
    }
}
