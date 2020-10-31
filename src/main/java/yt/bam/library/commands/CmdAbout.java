//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package yt.bam.library.commands;

import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.permissions.Permission;
import yt.bam.library.BAMLibrary;
import yt.bam.library.Helpers;
import yt.bam.library.ICommand;

public class CmdAbout implements ICommand {
    public static final Logger logger = Bukkit.getLogger();

    public CmdAbout() {
    }

    public void execute(CommandSender sender, String commandLabel, String[] args) {
        Helpers.sendMessage(sender, ChatColor.GREEN + BAMLibrary.Plugin.getName() + " " + ChatColor.WHITE + BAMLibrary.Instance.Translation.getTranslation("COMMAND_ABOUT_BY") + ChatColor.GREEN + " " + BAMLibrary.Plugin.getDescription().getAuthors());
        Helpers.sendMessage(sender, ChatColor.GREEN + "Proudly presenting BAMcraft (bam.yt)");
    }

    public String getHelp() {
        return BAMLibrary.Instance.Translation.getTranslation("COMMAND_ABOUT_HELP");
    }

    public String getSyntax() {
        return "/" + BAMLibrary.Instance.RootCommands[0] + " about";
    }

    public Permission getPermissions() {
        return null;
    }

    public String[] getName() {
        return new String[]{"about"};
    }

    public String getExtendedHelp() {
        return null;
    }

    public boolean allowedInConsole() {
        return true;
    }
}
