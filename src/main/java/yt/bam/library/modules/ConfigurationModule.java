//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package yt.bam.library.modules;

import org.bukkit.plugin.Plugin;

public class ConfigurationModule {
    public Plugin Plugin;

    public ConfigurationModule(Plugin plugin) {
        this.Plugin = plugin;
    }

    public boolean getBoolean(String key, boolean def) {
        return this.Plugin.getConfig().get(key) == null ? def : this.Plugin.getConfig().getBoolean(key);
    }

    public String getString(String key, String def) {
        return this.Plugin.getConfig().get(key) == null ? def : this.Plugin.getConfig().getString(key);
    }

    public int getInt(String key, int def) {
        return this.Plugin.getConfig().get(key) == null ? def : this.Plugin.getConfig().getInt(key);
    }

    public void onEnable() {
        this.Plugin.saveDefaultConfig();
        this.Plugin.reloadConfig();
    }

    public void onDisable() {
    }
}
