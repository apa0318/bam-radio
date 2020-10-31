//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package yt.bam.library;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Helpers {
    public Helpers() {
    }

    public static void sendMessage(Player player, String message) {
        player.sendMessage(ChatColor.GRAY + "[" + BAMLibrary.Plugin.getName() + "] " + ChatColor.BLUE + message);
    }

    public static void sendMessage(CommandSender player, String message) {
        player.sendMessage(ChatColor.GRAY + "[" + BAMLibrary.Plugin.getName() + "] " + ChatColor.BLUE + message);
    }
}
