package net.njay.uhc.match.party;

import net.njay.uhc.player.UHCPlayer;
import org.bukkit.Bukkit;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;

public class PartyScoreboard {

    private static int id = 0;

    private Party party;
    private ScoreboardManager manager;
    private Scoreboard board;
    private Team team;

    public PartyScoreboard(Party party){
        this.party = party;
        manager = Bukkit.getScoreboardManager();
        board = manager.getNewScoreboard();
        this.team = board.registerNewTeam("partyteam");
        this.team.setAllowFriendlyFire(false);

        for (UHCPlayer member : party.getMembers())
            addMember(member);
    }

    public void addMember(UHCPlayer player){
        team.addPlayer(player.getBukkit());
        player.getBukkit().setScoreboard(board);
    }

    public void removeMember(UHCPlayer player){
        board.resetScores(player.getBukkit());
        team.removePlayer(player.getBukkit());
        player.getBukkit().setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
    }

}

