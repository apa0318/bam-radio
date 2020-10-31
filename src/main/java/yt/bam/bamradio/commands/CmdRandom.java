//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package yt.bam.bamradio.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.permissions.Permission;
import yt.bam.bamradio.BAMradio;
import yt.bam.library.ICommand;

public class CmdRandom implements ICommand {
    public CmdRandom() {
    }

    public void execute(CommandSender sender, String commandLabel, String[] args) {
        if (BAMradio.Instance.RadioManager.isNowPlaying()) {
            BAMradio.Instance.RadioManager.stopPlaying();
        }

        BAMradio.Instance.RadioManager.playRandomSong();
    }

    public String getHelp() {
        return BAMradio.Library.Translation.getTranslation("COMMAND_RANDOM_HELP");
    }

    public String getSyntax() {
        return "/br random";
    }

    public Permission getPermissions() {
        return new Permission("bamradio.play");
    }

    public String[] getName() {
        return new String[]{"random"};
    }

    public String getExtendedHelp() {
        return null;
    }

    public boolean allowedInConsole() {
        return true;
    }
}
