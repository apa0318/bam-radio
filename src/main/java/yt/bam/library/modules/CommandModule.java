//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package yt.bam.library.modules;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.Plugin;
import yt.bam.library.Helpers;
import yt.bam.library.ICommand;
import yt.bam.library.commands.CmdHelp;

public class CommandModule {
    public static final Logger logger = Bukkit.getLogger();
    public Plugin Plugin;
    public static TranslationModule TranslationManager;
    public static ArrayList<String> RootCommands;
    public static ArrayList<ICommand> Commands;

    public CommandModule(Plugin plugin, TranslationModule translationManager, String[] rootCommands, ArrayList<ICommand> commands) {
        this.Plugin = plugin;
        TranslationManager = translationManager;
        RootCommands = new ArrayList();
        String[] arr$ = rootCommands;
        int len$ = rootCommands.length;

        for(int i$ = 0; i$ < len$; ++i$) {
            String rootCommand = arr$[i$];
            RootCommands.add(rootCommand.toLowerCase());
        }

        Commands = commands;
    }

    public static boolean onCommand(CommandSender sender, Command root, String commandLabel, String[] args) {
        if (!RootCommands.contains(root.getName().toLowerCase())) {
            return false;
        } else {
            ICommand command = null;
            if (args.length == 0) {
                command = new CmdHelp();
            } else {
                Iterator i$ = Commands.iterator();

                label134:
                while(true) {
                    ICommand cmd;
                    String[] cmdargs;
                    do {
                        if (!i$.hasNext()) {
                            break label134;
                        }

                        cmd = (ICommand)i$.next();
                        cmdargs = cmd.getName();
                    } while(args.length < cmdargs.length);

                    int index = 0;
                    boolean found = true;
                    String[] arr$ = cmdargs;
                    int len$ = cmdargs.length;

                    for(int j = 0; j < len$; ++j) {
                        String cmdarg = arr$[j];
                        if (!cmdarg.equals(args[index])) {
                            found = false;
                        }
                    }

                    if (found) {
                        command = cmd;
                        break;
                    }
                }
            }

            if (command == null) {
                Helpers.sendMessage(sender, ChatColor.RED + TranslationManager.getTranslation("COMMAND_MANAGER_UNKNOWN_COMMAND"));
                return true;
            } else {
                try {
                    Permission permission = ((ICommand)command).getPermissions();
                    if (permission != null && !hasPermission(sender, permission)) {
                        return true;
                    } else {
                        if (!((ICommand)command).allowedInConsole() && !(sender instanceof Player)) {
                            Helpers.sendMessage(sender, ChatColor.RED + TranslationManager.getTranslation("COMMAND_MANAGER_ONLY_CHAT"));
                        } else {
                            ((ICommand)command).execute(sender, commandLabel, args);
                        }

                        boolean var20 = true;
                        return true;
                    }
                } catch (Exception var17) {
                    sender.sendMessage(commandLabel);
                    logger.info(var17.getMessage());
                    return true;
                } finally {
                    ;
                }
            }
        }
    }

    public static boolean hasPermission(CommandSender player, Permission permission) {
        if (player.hasPermission(permission)) {
            return true;
        } else {
            Helpers.sendMessage(player, ChatColor.RED + TranslationManager.getTranslation("COMMAND_MANAGER_NO_PERMISSION") + " (" + permission.getName() + ")");
            return false;
        }
    }

    public void onEnable() {
    }

    public void onDisable() {
    }
}
