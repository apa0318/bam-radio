//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package yt.bam.bamradio.commands;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.charset.Charset;
import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.permissions.Permission;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import yt.bam.bamradio.BAMradio;
import yt.bam.library.Helpers;
import yt.bam.library.ICommand;

public class CmdGet implements ICommand {
    public static final Logger logger = Bukkit.getLogger();

    public CmdGet() {
    }

    public void execute(CommandSender sender, String commandLabel, String[] args) {
        try {
            if (args.length >= 2 && this.isInteger(args[1])) {
                int id = Integer.parseInt(args[1]);
                JSONArray json = readJsonFromUrl("http://radio.bam.yt/?f=json&id=" + id);
                if (json.length() == 1) {
                    JSONObject row = json.getJSONObject(0);
                    String filename = row.getString("filename");
                    String surl = "http://radio.bam.yt/?f=download&name=" + filename;
                    File file = new File(BAMradio.Instance.getDataFolder() + File.separator + filename);
                    if (file.exists() || !surl.endsWith(".mid") && !surl.endsWith(".midi") && !surl.endsWith(".nbs")) {
                        Helpers.sendMessage(sender, ChatColor.RED + BAMradio.Library.Translation.getTranslation("COMMAND_MANAGER_INVALID_PARAMETER"));
                    } else {
                        URL url = new URL(surl);
                        ReadableByteChannel rbc = Channels.newChannel(url.openStream());
                        FileOutputStream fos = new FileOutputStream(BAMradio.Instance.getDataFolder() + File.separator + filename);
                        fos.getChannel().transferFrom(rbc, 0L, 9223372036854775807L);
                    }

                    if (!BAMradio.Instance.RadioManager.playSong(filename)) {
                        Helpers.sendMessage(sender, ChatColor.RED + BAMradio.Library.Translation.getTranslation("COMMAND_PLAY_EXCEPTION_NOT_FOUND") + " \"" + args[1] + "\"");
                    }
                } else {
                    Helpers.sendMessage(sender, ChatColor.RED + BAMradio.Library.Translation.getTranslation("COMMAND_GET_NOT_FOUND"));
                }
            } else {
                Helpers.sendMessage(sender, ChatColor.RED + BAMradio.Library.Translation.getTranslation("COMMAND_MANAGER_INVALID_PARAMETER"));
            }
        } catch (Exception var13) {
            Helpers.sendMessage(sender, ChatColor.RED + var13.getMessage());
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

    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();

        int cp;
        while((cp = rd.read()) != -1) {
            sb.append((char)cp);
        }

        return sb.toString();
    }

    public static JSONArray readJsonFromUrl(String url) throws IOException, JSONException {
        InputStream is = (new URL(url)).openStream();

        JSONArray var5;
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd).trim();
            JSONArray json = new JSONArray(jsonText);
            var5 = json;
        } finally {
            is.close();
        }

        return var5;
    }

    public String getHelp() {
        return BAMradio.Library.Translation.getTranslation("COMMAND_GET_HELP");
    }

    public String getSyntax() {
        return "/br get <id>";
    }

    public Permission getPermissions() {
        return new Permission("bamradio.get");
    }

    public String[] getName() {
        return new String[]{"get"};
    }

    public String getExtendedHelp() {
        return null;
    }

    public boolean allowedInConsole() {
        return true;
    }
}
