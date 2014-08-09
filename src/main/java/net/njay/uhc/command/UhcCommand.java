package net.njay.uhc.command;

import com.sk89q.minecraft.util.commands.Command;
import com.sk89q.minecraft.util.commands.CommandContext;
import com.sk89q.minecraft.util.commands.CommandException;
import com.sk89q.minecraft.util.commands.NestedCommand;
import net.njay.uhc.Config;
import net.njay.uhc.UHC;
import net.njay.uhc.event.match.player.PlayerLeaveMatchEvent;
import net.njay.uhc.player.UHCPlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class UhcCommand {

    public static class UhcParentCommand{
        @Command(aliases = { "uhc" }, desc = "UHC supercommand", min = 0, max = -1)
        @NestedCommand(UhcCommand.class)
        public static void partyParent(CommandContext args, CommandSender sender) throws CommandException { }
    }

    @Command(aliases = { "match", "join" }, desc = "Open match selection GUI", usage = "", min = -1, max = -1)
    public static void join(CommandContext args, CommandSender sender) throws CommandException {
        if (!(sender instanceof Player)) return;
        UHCPlayer player = UHC.getPlayerManager().getPlayer((Player)sender);
        if (player.getMatch() != null){
            player.getBukkit().sendMessage(Config.Messages.alreadyInMatch);
            return;
        }
        UHC.getMenu().show(player.getBukkit());
    }

    @Command(aliases = { "leave", "exit" }, desc = "Leaves the current match", usage = "", min = -1, max = -1)
    public static void leave(CommandContext args, CommandSender sender) throws CommandException {
        if (!(sender instanceof Player)) return;
        UHCPlayer player = UHC.getPlayerManager().getPlayer((Player)sender);
        if (player.getMatch() == null) {
            player.getBukkit().sendMessage(Config.Messages.leaveMatchFail);
            return;
        }
        PlayerLeaveMatchEvent event = new PlayerLeaveMatchEvent(player.getMatch(), player);
        event.call();
    }

    @Command(aliases = { "p", "party" }, desc = "UHC Party supercommand", min = 0, max = -1)
    @NestedCommand(PartyCommand.class)
    public static void partyParent(CommandContext args, CommandSender sender) throws CommandException { }
}
