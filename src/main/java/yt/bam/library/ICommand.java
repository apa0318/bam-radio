//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package yt.bam.library;

import org.bukkit.command.CommandSender;
import org.bukkit.permissions.Permission;

public interface ICommand {
    void execute(CommandSender var1, String var2, String[] var3);

    String getHelp();

    String getExtendedHelp();

    String getSyntax();

    Permission getPermissions();

    String[] getName();

    boolean allowedInConsole();
}
