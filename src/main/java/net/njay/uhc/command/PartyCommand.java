package net.njay.uhc.command;

import com.sk89q.minecraft.util.commands.Command;
import com.sk89q.minecraft.util.commands.CommandContext;
import com.sk89q.minecraft.util.commands.CommandException;
import net.njay.uhc.Config;
import net.njay.uhc.UHC;
import net.njay.uhc.match.MatchState;
import net.njay.uhc.match.PartyMatch;
import net.njay.uhc.match.party.Party;
import net.njay.uhc.player.UHCPlayer;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PartyCommand {

    @Command(aliases = { "create", "new" }, desc = "Creates a new party", usage = "<name>", min = 1, max = 1)
    public static void create(CommandContext args, CommandSender sender) throws CommandException {
        if (!(sender instanceof Player)) return;
        UHCPlayer player = UHC.getPlayerManager().getPlayer((Player)sender);
        runCommonChecks(player);
        PartyMatch partyMatch = (PartyMatch) player.getMatch();
        if (partyMatch.getParty(player) != null)
            throw new CommandException("You cannot create a party while in a party!");
        partyMatch.addParty(new Party(args.getString(0), player));
        player.getBukkit().sendMessage("Party created");
    }

    @Command(aliases = { "join", "j" }, desc = "Creates a new party", usage = "<name>", min = 1, max = 1)
    public static void join(CommandContext args, CommandSender sender) throws CommandException {
        if (!(sender instanceof Player)) return;
        UHCPlayer player = UHC.getPlayerManager().getPlayer((Player)sender);
        runCommonChecks(player);
        PartyMatch partyMatch = (PartyMatch) player.getMatch();
        if (partyMatch.getParty(player) != null)
           throw new CommandException("You cannot create a party while in a party!");
        Player toJoinPartyPlayer = Bukkit.getPlayer(args.getString(0));
        if (toJoinPartyPlayer == null)
            throw new CommandException("Not a valid player n00b!");
        Party toJoin = partyMatch.getParty(UHC.getPlayerManager().getPlayer(toJoinPartyPlayer));
        if (toJoin == null)
            throw new CommandException("This player no in paty!");
        if (!toJoin.isInvited(player))
            throw new CommandException("U is no invite!");
        if (toJoin.getMembers().size() >= partyMatch.getPartySize())
            throw new CommandException("Too many poople in da partaaaay");
        toJoin.addPlayer(player);
        player.getBukkit().sendMessage("U is join. ok");
    }

    @Command(aliases = { "invite", "inv" }, desc = "Invites player to party", usage = "<player>", min = 1, max = 1)
    public static void invite(CommandContext args, CommandSender sender) throws CommandException {
        if (!(sender instanceof Player)) return;
        UHCPlayer player = UHC.getPlayerManager().getPlayer((Player)sender);
        runCommonChecks(player);
        PartyMatch partyMatch = (PartyMatch) player.getMatch();
        if (partyMatch.getParty(player) == null)
            throw new CommandException("You are no in party!");
        if (partyMatch.getParty(player).getOwner() != player)
            throw new CommandException("Only owner can invite");
        Player toInvite = Bukkit.getPlayer(args.getString(0));
        if (toInvite == null || UHC.getPlayerManager().getPlayer(toInvite).getMatch() != player.getMatch())
            throw new CommandException("Invalid player: " + args.getString(0));
        partyMatch.getParty(player).invite(UHC.getPlayerManager().getPlayer(toInvite));
        player.getBukkit().sendMessage("Invited.");
        toInvite.sendMessage(player.getBukkit().getName() + " has invited you to join the party " + partyMatch.getParty(player).getName() + "! Join with /uhc p j " + partyMatch.getParty(player).getName());
    }

    @Command(aliases = { "disband", "destroy", "d" }, desc = "Creates a new party", usage = "<name>", min = -1, max = -1)
    public static void disband(CommandContext args, CommandSender sender) throws CommandException {
        if (!(sender instanceof Player)) return;
        UHCPlayer player = UHC.getPlayerManager().getPlayer((Player) sender);
        runCommonChecks(player);
        PartyMatch partyMatch = (PartyMatch) player.getMatch();
        if (partyMatch.getParty(player) == null)
            throw new CommandException("m8 wot u trying to pull u no in paty");
        if (partyMatch.getParty(player).getOwner() != player)
            throw new CommandException("m8 pls u aint da owner of dis dank party");
        partyMatch.removeParty(partyMatch.getParty(player));
        player.getBukkit().sendMessage("Da dark deed is done");
        //TODO: SEND MESSAGE TO ALL
    }

    @Command(aliases = { "leave", "l" }, desc = "Creates a new party", usage = "<name>", min = -1, max = -1)
    public static void leave(CommandContext args, CommandSender sender) throws CommandException {
        if (!(sender instanceof Player)) return;
        UHCPlayer player = UHC.getPlayerManager().getPlayer((Player) sender);
        runCommonChecks(player);
        PartyMatch partyMatch = (PartyMatch) player.getMatch();
        Party party = partyMatch.getParty(player);
        if (party.getOwner() == player)
            disband(args, sender);
        else {
            party.removePlayer(player);
            player.getBukkit().sendMessage("U is leaf");
        }
    }
    @Command(aliases = { "info", "i" }, desc = "Creates a new party", usage = "<name>", min = -1, max = -1)
    public static void info(CommandContext args, CommandSender sender) throws CommandException {
        if (!(sender instanceof Player)) return;
        UHCPlayer player = UHC.getPlayerManager().getPlayer((Player) sender);
        runCommonChecks(player);
        PartyMatch partyMatch = (PartyMatch) player.getMatch();
        Party party = partyMatch.getParty(player);
        if (party == null)
            throw new CommandException("You are not in a party!");
        player.getBukkit().sendMessage("---------------------------");
           player.getBukkit().sendMessage("Party: " + party.getName() + "   Owner: " + party.getOwner().getBukkit().getName());
            for (int i = 1; i < party.getMembers().size(); i++)
                player.getBukkit().sendMessage("Member: " +  party.getMembers().get(i).getBukkit().getName());
        player.getBukkit().sendMessage("---------------------------");
    }


    private static void runCommonChecks(UHCPlayer player) throws CommandException{
        if (!(player.getMatch() instanceof PartyMatch) )
            throw new CommandException("You can only perform party commands in party enabled matches");
        if (player.getMatch().getState() != MatchState.STARTING)
            throw new CommandException("You may not perform party commands at this time");
    }

}
