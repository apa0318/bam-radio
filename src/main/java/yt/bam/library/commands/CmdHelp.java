//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package yt.bam.library.commands;

import java.util.Iterator;
import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.permissions.Permission;
import yt.bam.library.BAMLibrary;
import yt.bam.library.ICommand;

public class CmdHelp implements ICommand {
    public static final Logger logger = Bukkit.getLogger();

    public CmdHelp() {
    }

    public void execute(CommandSender sender, String commandLabel, String[] args) {
        sender.sendMessage(ChatColor.BOLD + "" + ChatColor.GREEN + "#####################################################");
        sender.sendMessage(ChatColor.BOLD + "" + ChatColor.GREEN + BAMLibrary.Plugin.getName() + " " + BAMLibrary.Plugin.getDescription().getVersion() + " by " + BAMLibrary.Plugin.getDescription().getAuthors());
        Iterator i$ = BAMLibrary.Instance.Commands.iterator();

        while(true) {
            ICommand cmd;
            do {
                if (!i$.hasNext()) {
                    sender.sendMessage(ChatColor.BOLD + "" + ChatColor.GREEN + "#####################################################");
                    return;
                }

                cmd = (ICommand)i$.next();
            } while(cmd.getPermissions() != null && !sender.hasPermission(cmd.getPermissions()));

            sender.sendMessage(ChatColor.WHITE + cmd.getSyntax() + " - " + cmd.getHelp());
        }
    }

    public String getHelp() {
        return BAMLibrary.Instance.Translation.getTranslation("COMMAND_HELP_HELP");
    }

    public String getSyntax() {
        return "/" + BAMLibrary.Instance.RootCommands[0] + " help";
    }

    public Permission getPermissions() {
        return null;
    }

    public String[] getName() {
        return new String[]{"help"};
    }

    public String getExtendedHelp() {
        return null;
    }

    public boolean allowedInConsole() {
        return true;
    }
}
