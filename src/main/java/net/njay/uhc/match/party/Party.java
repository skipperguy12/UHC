package net.njay.uhc.match.party;

import com.google.common.collect.Lists;
import net.minecraft.util.com.google.common.collect.ImmutableList;
import net.njay.uhc.player.UHCPlayer;

import java.util.List;

public class Party {

    private List<UHCPlayer> members;
    private PartyScoreboard scoreboard;

    public Party(UHCPlayer owner){
       members = Lists.newArrayList(owner);
       scoreboard = new PartyScoreboard(this);
    }

    public void addPlayer(UHCPlayer player){
        members.add(player);
        scoreboard.addMember(player);
    }

    public void removePlayer(UHCPlayer player){
        members.remove(player);
        scoreboard.removeMember(player);
    }

    public List<UHCPlayer> getMembers(){
        return ImmutableList.copyOf(members);
    }

}
