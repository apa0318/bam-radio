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

public class CmdInfo implements ICommand {
    public static final Logger logger = Bukkit.getLogger();

    public CmdInfo() {
    }

    public void execute(CommandSender sender, String commandLabel, String[] args) {
        if (BAMradio.Instance.RadioManager.isNowPlaying()) {
            BAMradio.Instance.RadioManager.NowPlaying(sender, true);
        }

    }

    public String getHelp() {
        return BAMradio.Library.Translation.getTranslation("COMMAND_INFO_HELP");
    }

    public String getSyntax() {
        return "/br info";
    }

    public Permission getPermissions() {
        return null;
    }

    public String[] getName() {
        return new String[]{"info"};
    }

    public String getExtendedHelp() {
        return null;
    }

    public boolean allowedInConsole() {
        return true;
    }
}
