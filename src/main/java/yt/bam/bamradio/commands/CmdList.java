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
import yt.bam.bamradio.radiomanager.RadioManager;
import yt.bam.library.Helpers;
import yt.bam.library.ICommand;

public class CmdList implements ICommand {
    public static final Logger logger = Bukkit.getLogger();

    public CmdList() {
    }

    public void execute(CommandSender sender, String commandLabel, String[] args) {
        Helpers.sendMessage(sender, ChatColor.GREEN + BAMradio.Library.Translation.getTranslation("COMMAND_LIST_TITLE"));
        RadioManager var10000 = BAMradio.Instance.RadioManager;
        String[] fileList = RadioManager.listRadioFilesWithExtensions();
        int i = 0;
        String[] arr$ = fileList;
        int len$ = fileList.length;

        for(int i$ = 0; i$ < len$; ++i$) {
            String name = arr$[i$];
            String suffix = "";
            if (name.contains(".mid")) {
                name = name.substring(0, name.lastIndexOf(".mid"));
                suffix = ChatColor.DARK_BLUE + "MID";
            }

            if (name.contains(".nbs")) {
                name = name.substring(0, name.lastIndexOf(".nbs"));
                suffix = ChatColor.DARK_GREEN + "NBS";
            }

            sender.sendMessage(ChatColor.GREEN + "[" + ChatColor.BOLD + i + ChatColor.RESET + "" + ChatColor.GREEN + "] " + suffix + " " + ChatColor.RESET + name);
            ++i;
        }

    }

    public String getHelp() {
        return BAMradio.Library.Translation.getTranslation("COMMAND_LIST_HELP");
    }

    public String getSyntax() {
        return "/br list";
    }

    public Permission getPermissions() {
        return new Permission("bamradio.list");
    }

    public String[] getName() {
        return new String[]{"list"};
    }

    public String getExtendedHelp() {
        return null;
    }

    public boolean allowedInConsole() {
        return true;
    }
}
