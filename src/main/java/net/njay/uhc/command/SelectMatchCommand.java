package net.njay.uhc.command;

import com.sk89q.minecraft.util.commands.Command;
import com.sk89q.minecraft.util.commands.CommandContext;
import com.sk89q.minecraft.util.commands.CommandException;
import net.njay.uhc.UHC;
import net.njay.uhc.player.UHCPlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SelectMatchCommand {

    @Command(aliases = { "match", "join" }, desc = "Open match selection GUI", usage = "", min = -1, max = -1)
    public static void join(CommandContext args, CommandSender sender) throws CommandException {
        if (!(sender instanceof Player)) return;
        UHCPlayer player = UHC.getPlayerManager().getPlayer((Player)sender);
        UHC.getMenu().show(player.getBukkit());
    }
}
