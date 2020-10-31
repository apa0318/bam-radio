//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package yt.bam.library.modules;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

public class TranslationModule {
    private static final Logger logger = Bukkit.getLogger();
    private Plugin Plugin;
    private String Language;
    private Map<String, String> defaultTranslation;
    private Map<String, String> translation;
    private FileConfiguration loadedlanguage;

    public TranslationModule(Plugin plugin, String language, Map<String, String> defaultTranslation) {
        this.Plugin = plugin;
        this.defaultTranslation = defaultTranslation;
        this.Language = language;
    }

    public void reloadTranslation() {
        if (this.Language.equals("en")) {
            this.translation = this.defaultTranslation;
        } else {
            File f = new File(this.Plugin.getDataFolder() + File.separator + this.Language + ".yml");
            if (f.exists()) {
                File languageFile = new File(this.Plugin.getDataFolder() + File.separator + this.Language + ".yml");
                this.loadedlanguage = YamlConfiguration.loadConfiguration(languageFile);
                this.loadTranslation();
            } else {
                logger.warning("Languagefile " + this.Language + ".yml not found, falling back to english language!");
            }
        }

    }

    public String getTranslation(String key) {
        if (this.translation != null) {
            String value = (String)this.translation.get("translation." + key);
            return value != null && !value.isEmpty() ? value : "Translation missing: " + key;
        } else {
            return (String)this.defaultTranslation.get(key);
        }
    }

    private void loadTranslation() {
        this.translation = new HashMap();
        Iterator i$ = this.loadedlanguage.getValues(true).entrySet().iterator();

        while(i$.hasNext()) {
            Entry<String, Object> entry = (Entry)i$.next();
            this.translation.put(entry.getKey(), entry.getValue().toString());
        }

    }

    public void onEnable() {
        this.reloadTranslation();
    }

    public void onDisable() {
    }
}
