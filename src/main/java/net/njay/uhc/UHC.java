package net.njay.uhc;

import com.sk89q.bukkit.util.CommandsManagerRegistration;
import com.sk89q.minecraft.util.commands.*;
import net.njay.uhc.listeners.ConnectionListener;
import net.njay.uhc.listeners.match.*;
import net.njay.uhc.match.MatchManager;
import net.njay.uhc.menu.join.JoinMenu;
import net.njay.uhc.player.PlayerManager;
import net.njay.uhc.util.location.SportBukkitUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;

public class UHC extends JavaPlugin {
    private static UHC instance;

    // UHC internal properties
    private static Properties properties = null;

    // sk89q's command framework
    private CommandsManager<CommandSender> commands;
    private static PlayerManager playerManager;
    private static MatchManager matchManager;
    private static JoinMenu joinMenu;


    public static UHC getInstance() {
        return instance;
    }

    public String getCommit() {
        return properties == null ? "??" : properties.getProperty("git-sha-1");
    }

    public static MatchManager getMatchManager() {
        return matchManager;
    }

    public static PlayerManager getPlayerManager() {
        return playerManager;
    }

    public static JoinMenu getMenu() {
        return joinMenu;
    }

    public UHC() {
        instance = this;
    }

    public void onEnable() {
        instance = this;

        saveDefaultConfig();

        // get properties (for the git commit id)
        InputStream propStream = getResource("uhc.properties");
        if (propStream != null) try {
            properties = new Properties();
            properties.load(propStream);
        } catch (IOException e) {
            Debug.log("Failed to load properties file.", Debug.LogLevel.SEVERE);
        }

        playerManager = new PlayerManager();

        matchManager = new MatchManager(2);

        joinMenu = new JoinMenu(matchManager, Bukkit.createInventory(null, 27, ChatColor.GOLD + "Join a match!"));

        setupCommands();
        registerListeners();

        // wrap up, debug to follow this message
        getLogger().info(this.getName() + " (" + Bukkit.getName() + ") loaded successfully" +
                (SportBukkitUtil.hasSportBukkitApi() ? " with SportBukkit API" : "") + ".");
        getLogger().info(this.getName() + " Git commit id: " + this.getCommit());
    }

    public void onDisable() {
        instance = null;
    }

    // register all listeners that are not intended to be disabled
    private void registerListeners() {
        registerEvents(new ConnectionListener());
        registerEvents(new BoundaryListener());
        registerEvents(new PreMatchListener());
        registerEvents(new PlayerMatchListener());
        registerEvents(new PlayerLeaveMatchListener());
        registerEvents(new RespawnListener());

        registerEvents(joinMenu);
    }

    // registers events for a Listener
    private void registerEvents(Listener listener) {
        Bukkit.getPluginManager().registerEvents(listener, getInstance());
    }

    // sets up the sk89q command framework, registers all commands
    private void setupCommands() {
        this.commands = new CommandsManager<CommandSender>() {
            @Override
            public boolean hasPermission(CommandSender sender, String perm) {
                return sender instanceof ConsoleCommandSender || sender.hasPermission(perm);
            }
        };
        CommandsManagerRegistration cmdRegister = new CommandsManagerRegistration(this, this.commands);
        //Register your commands here
    }

    // Passes commands from Bukkit to sk89q
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        try {
            this.commands.execute(cmd.getName(), args, sender, sender);
        } catch (CommandPermissionsException e) {
            sender.sendMessage(ChatColor.RED + "You don't have permission.");
        } catch (MissingNestedCommandException e) {
            sender.sendMessage(ChatColor.RED + e.getUsage());
        } catch (CommandUsageException e) {
            sender.sendMessage(ChatColor.RED + e.getMessage());
            sender.sendMessage(ChatColor.RED + e.getUsage());
        } catch (WrappedCommandException e) {
            if (e.getCause() instanceof NumberFormatException) {
                sender.sendMessage(ChatColor.RED + "Number expected, string received instead.");
            } else {
                sender.sendMessage(ChatColor.RED + "An error has occurred. See console.");
                e.printStackTrace();
            }
        } catch (CommandException e) {
            sender.sendMessage(ChatColor.RED + e.getMessage());
        }

        return true;
    }
}
