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

public class CmdPlay implements ICommand {
    public static final Logger logger = Bukkit.getLogger();

    public CmdPlay() {
    }

    public void execute(CommandSender sender, String commandLabel, String[] args) {
        try {
            if (args.length == 1) {
                CmdList list = new CmdList();
                list.execute(sender, commandLabel, args);
            } else {
                if (BAMradio.Instance.RadioManager.isNowPlaying()) {
                    BAMradio.Instance.RadioManager.stopPlaying();
                }

                if (this.isInteger(args[1])) {
                    int index = Integer.parseInt(args[1]);
                    RadioManager var10000 = BAMradio.Instance.RadioManager;
                    String[] fileList = RadioManager.listRadioFiles();
                    if (index < fileList.length) {
                        BAMradio.Instance.RadioManager.playSong(fileList[index]);
                    } else {
                        Helpers.sendMessage(sender, ChatColor.RED + BAMradio.Library.Translation.getTranslation("COMMAND_PLAY_EXCEPTION_NOT_FOUND") + " \"" + args[1] + "\"");
                    }
                } else if (!BAMradio.Instance.RadioManager.playSong(args[1])) {
                    Helpers.sendMessage(sender, ChatColor.RED + BAMradio.Library.Translation.getTranslation("COMMAND_PLAY_EXCEPTION_NOT_FOUND") + " \"" + args[1] + "\"");
                }
            }
        } catch (Exception var6) {
            Helpers.sendMessage(sender, ChatColor.RED + var6.getMessage());
        }

    }

    public boolean isInteger(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (Exception var3) {
            return false;
        }
    }

    public String getHelp() {
        return BAMradio.Library.Translation.getTranslation("COMMAND_PLAY_HELP");
    }

    public String getSyntax() {
        return "/br play <name|index>";
    }

    public Permission getPermissions() {
        return new Permission("bamradio.play");
    }

    public String[] getName() {
        return new String[]{"play"};
    }

    public String getExtendedHelp() {
        return BAMradio.Library.Translation.getTranslation("COMMAND_PLAY_EXTENDED_HELP");
    }

    public boolean allowedInConsole() {
        return true;
    }
}
