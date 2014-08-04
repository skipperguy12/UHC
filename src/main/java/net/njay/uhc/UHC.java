package net.njay.uhc;

import com.sk89q.bukkit.util.CommandsManagerRegistration;
import com.sk89q.minecraft.util.commands.*;
import net.njay.MenuFramework;
import net.njay.MenuRegistry;
import net.njay.uhc.listeners.ConnectionListener;
import net.njay.uhc.match.MatchManager;
import net.njay.uhc.player.PlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class UHC extends JavaPlugin {
    private static UHC instance;
    // sk89q's command framework
    private CommandsManager<CommandSender> commands;
    private static PlayerManager playerManager;
    private static MatchManager matchManager;


    public static UHC getInstance() {
        return instance;
    }

    public static MatchManager getMatchManager() {
        return matchManager;
    }

    public static PlayerManager getPlayerManager() {
        return playerManager;
    }

    public UHC() {
        instance = this;
    }

    public void onEnable() {
        instance = this;

        getConfig().options().copyDefaults(true);
        saveConfig();
        reloadConfig();

        playerManager = new PlayerManager();
        MenuFramework.enable(new MenuRegistry(getInstance()), playerManager);

        matchManager = new MatchManager(2);

        setupCommands();
        registerListeners();
    }

    public void onDisable() {
        instance = null;
    }

    // register all listeners that are not intended to be disabled
    private void registerListeners() {
        registerEvents(new ConnectionListener());
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