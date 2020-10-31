//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package yt.bam.bamradio.commands;

import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.permissions.Permission;
import yt.bam.bamradio.BAMradio;
import yt.bam.library.Helpers;
import yt.bam.library.ICommand;

public class CmdVolume implements ICommand {
    public static final Logger logger = Bukkit.getLogger();

    public CmdVolume() {
    }

    public void execute(CommandSender sender, String commandLabel, String[] args) {
        if (args.length == 2) {
            try {
                BAMradio.Instance.RadioManager.Volume = Integer.parseInt(args[1].toString());
                Helpers.sendMessage(sender, ChatColor.GREEN + BAMradio.Library.Translation.getTranslation("COMMAND_VOLUME_CHANGED") + args[1].toString());
            } catch (Exception var5) {
                Helpers.sendMessage(sender, ChatColor.RED + BAMradio.Library.Translation.getTranslation("COMMAND_MANAGER_INVALID_PARAMETER") + " \"" + args[1] + "\"");
            }
        }

    }

    public String getHelp() {
        BAMradio var10000 = BAMradio.Instance;
        return BAMradio.Library.Translation.getTranslation("COMMAND_VOLUME_HELP");
    }

    public String getSyntax() {
        return "/br volume <volume>";
    }

    public Permission getPermissions() {
        return new Permission("bamradio.volume");
    }

    public String[] getName() {
        return new String[]{"volume"};
    }

    public String getExtendedHelp() {
        return null;
    }

    public boolean allowedInConsole() {
        return true;
    }
}
