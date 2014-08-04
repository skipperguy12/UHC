package net.njay.uhc;

import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

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

    public static class Maps {
        private static final String sectionRoot = "maps.";
        public static final String mapsDir = get(sectionRoot + "maps-dir", "maps");
    }

    public static class Rotation {
        private static final String sectionRoot = "rotation.";
        public static final String rotationFileName = get(sectionRoot + "rotation-file-name", "rotation.txt");
    }


}
