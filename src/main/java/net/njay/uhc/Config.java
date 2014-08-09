package net.njay.uhc;

import com.google.common.collect.Lists;
import com.sk89q.minecraft.util.commands.ChatColor;
import net.njay.uhc.util.location.LocationUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.util.ArrayList;

/**
 * Class to interface with Bukkits Configuration system
 */
public class Config {
    /**
     * Gets the Bukkit Configuration
     *
     * @return the Configuration in use by Bukkit from the Main class
     */
    public static Configuration getBukkitConfig() {
        // Singleton instance of Main class
        UHC plugin = UHC.getInstance();
        // result from plugin
        FileConfiguration res = plugin.getConfig();

        // return res if not null, else return a brand new YamlConfiguration
        if (res != null) return res;
        else return new YamlConfiguration();
    }

    /**
     * Saves the Bukkit config
     */
    public static void saveBukkitConfig() {
        UHC.getInstance().saveConfig();
    }

    /**
     * Gets an element form the Configuration file
     *
     * @param path path to element
     * @param <T>  type of element
     * @return found element
     */
    @SuppressWarnings("unchecked")
    public static <T> T get(String path) {
        return (T) getBukkitConfig().get(path);
    }

    /**
     * Gets an element form the Configuration file, returning def if not found
     *
     * @param path path to element
     * @param def  default value to return if element is not found
     * @param <T>  type of element
     * @return found element, def if null
     */
    @SuppressWarnings("unchecked")
    public static <T> T get(String path, Object def) {
        return (T) getBukkitConfig().get(path, def);
    }

    /**
     * Sets a value in the configuration at path
     *
     * @param path path to element
     * @param val  value to set at path
     */
    public static void set(String path, Object val) {
        getBukkitConfig().set(path, val);
    }

    public static class Map {
        private static final String sectionRoot = "maps.";
        public static final String mapsDir = get(sectionRoot + "maps-dir", "maps");
    }

    public static class Rotation {
        private static final String sectionRoot = "rotation.";
        public static final String rotationFileName = get(sectionRoot + "rotation-file-name", "rotation.txt");
    }

    public static class Match {
        private static final String sectionRoot = "match.";
        public static final int matchRadius = get(sectionRoot + "radius", 250);
        public static final int playerwaitTime= get(sectionRoot + "time.playerwait", 10);
        public static final int lobbyTime = get(sectionRoot + "time.lobbysolo", 15);
        public static final int partyLobbyTime = get(sectionRoot + "time.lobbyparty", 60);
        public static final int endTime = get(sectionRoot + "time.end", 10);
    }

    public static class Component {
        private static final String sectionRoot = "component.";
        public static final ArrayList<String> enabledComponents = get(sectionRoot + "enabled-components", Lists.newArrayList());
    }

    public static class Spawns {
        private static final String sectionRoot = "spawns.";
        public static final Location serverSpawn = get(sectionRoot + "spawn", LocationUtil.placeOnGround(new Location(Bukkit.getWorlds().get(0), 0, 200, 0)));
    }

    public static class Messages{
        private static final String sectionRoot = "messages.";
        public static final String joinMessage = get(sectionRoot + "join", ChatColor.GREEN + "Welcome to our UHC server!");
        public static final String leaveMatchFail = get(sectionRoot + "leaveMatchFail", ChatColor.RED + "You cannot leave a match because you are not in one!");
        public static final String alreadyInMatch = get(sectionRoot + "alreadyInMatch", ChatColor.RED + "Please leave the current match before joining a new one!");
    }
}
