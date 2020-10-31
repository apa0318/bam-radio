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
import yt.bam.library.ICommand;

public class CmdUnmute implements ICommand {
    public static final Logger logger = Bukkit.getLogger();

    public CmdUnmute() {
    }

    public void execute(CommandSender sender, String commandLabel, String[] args) {
        BAMradio.Instance.RadioManager.tuneOut((Player)sender);
        BAMradio.Instance.RadioManager.tuneIn((Player)sender);
    }

    public String getHelp() {
        return BAMradio.Library.Translation.getTranslation("COMMAND_UNMUTE_HELP");
    }

    public String getSyntax() {
        return "/br unmute";
    }

    public Permission getPermissions() {
        return new Permission("bamradio.mute");
    }

    public String[] getName() {
        return new String[]{"unmute"};
    }

    public String getExtendedHelp() {
        return null;
    }

    public boolean allowedInConsole() {
        return false;
    }
}
