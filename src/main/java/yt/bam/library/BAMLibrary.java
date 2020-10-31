//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package yt.bam.library;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.mcstats.MetricsLite;
import yt.bam.library.commands.CmdAbout;
import yt.bam.library.commands.CmdHelp;
import yt.bam.library.modules.CommandModule;
import yt.bam.library.modules.ConfigurationModule;
import yt.bam.library.modules.TranslationModule;

public class BAMLibrary {
    public static BAMLibrary Instance;
    public static JavaPlugin Plugin;
    public ConfigurationModule Configuration = null;
    public CommandModule Command = null;
    public TranslationModule Translation = null;
    public Map<String, String> DefaultTranslation;
    public ArrayList<ICommand> Commands;
    public String[] RootCommands;

    public BAMLibrary(JavaPlugin plugin, Map<String, String> defaultTranslation, ArrayList<ICommand> commands, String[] rootCommands, boolean enableMetrics, boolean enableUpdateNotification) {
        Instance = this;
        Plugin = plugin;
        this.DefaultTranslation = defaultTranslation;
        this.Commands = commands;
        this.Commands.add(new CmdHelp());
        this.Commands.add(new CmdAbout());
        this.RootCommands = rootCommands;
        this.Configuration = new ConfigurationModule(plugin);
        this.Translation = new TranslationModule(plugin, this.Configuration.getString("translation", "en"), this.DefaultTranslation);
        this.Command = new CommandModule(plugin, this.Translation, this.RootCommands, this.Commands);
        MetricsLite latestVersion;
        if (enableMetrics && this.Configuration.getBoolean("enable-metrics", true)) {
            try {
                latestVersion = new MetricsLite(plugin);
                latestVersion.start();
            } catch (IOException var13) {
            }
        }

        if (enableUpdateNotification && this.Configuration.getBoolean("check-for-updates", true)) {
            try {
                latestVersion = null;
                String updateUrl = null;
                Scanner sc = new Scanner((new URL("http://dev.bam.yt/version.php?plugin=" + Plugin.getName())).openStream(), "UTF-8");
                String latestVersion2 = sc.useDelimiter(";").next();
                updateUrl = sc.useDelimiter(";").next();
                if (latestVersion2 != null && !latestVersion2.isEmpty() && updateUrl != null && !updateUrl.isEmpty()) {
                    int lVersion = Integer.parseInt(this.ensure3Digits(latestVersion2.replace(".", "").replace("v", "")));
                    int cVersion = Integer.parseInt(this.ensure3Digits(plugin.getDescription().getVersion().replace(".", "").replace("v", "")));
                    if (lVersion > cVersion) {
                        plugin.getLogger().info("A new version " + latestVersion2 + " is available!");
                        plugin.getLogger().info("Get it on " + updateUrl);
                    }
                }
            } catch (Exception var12) {
            }
        }

    }

    public String ensure3Digits(String in) {
        if (in.length() == 3) {
            return in;
        } else if (in.length() == 2) {
            return in + "0";
        } else {
            return in.length() == 1 ? in + "00" : "000";
        }
    }

    public void onEnable() {
        this.Command.onEnable();
        this.Translation.onEnable();
        this.Configuration.onEnable();
    }

    public void onDisable() {
        this.Command.onDisable();
        this.Translation.onDisable();
        this.Configuration.onDisable();
    }

    public boolean onCommand(CommandSender sender, Command root, String commandLabel, String[] args) {
        CommandModule var10000 = this.Command;
        return CommandModule.onCommand(sender, root, commandLabel, args);
    }
}
