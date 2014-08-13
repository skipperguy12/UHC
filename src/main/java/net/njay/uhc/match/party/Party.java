package net.njay.uhc.match.party;

import com.google.common.collect.Lists;
import net.minecraft.util.com.google.common.collect.ImmutableList;
import net.njay.uhc.event.party.PartyCreateEvent;
import net.njay.uhc.event.party.PlayerInviteToPartyEvent;
import net.njay.uhc.event.party.PlayerJoinPartyEvent;
import net.njay.uhc.event.party.PlayerLeavePartyEvent;
import net.njay.uhc.player.UHCPlayer;

import java.util.List;

public class Party {

    private String name;
    private List<UHCPlayer> members;
    private List<UHCPlayer> invites;
    private PartyScoreboard scoreboard;

    public Party(String name, UHCPlayer owner){
       this.name = name;
       members = Lists.newArrayList(owner);
       invites = Lists.newArrayList();
       scoreboard = new PartyScoreboard(this);
       new PartyCreateEvent(this).call();
    }

    public String getName(){ return this.name; }

    public void addPlayer(UHCPlayer player){
        members.add(player);
        scoreboard.addMember(player);
        new PlayerJoinPartyEvent(this, player).call();
    }

    public void removePlayer(UHCPlayer player){
        members.remove(player);
        scoreboard.removeMember(player);
        new PlayerLeavePartyEvent(this, player);
    }

    public List<UHCPlayer> getMembers(){
        return ImmutableList.copyOf(members);
    }

    public void invite(UHCPlayer player){
        invites.add(player);
        new PlayerInviteToPartyEvent(this, player).call();
    }

    public boolean isInvited(UHCPlayer player){
        return invites.contains(player);
    }

    public UHCPlayer getOwner(){
        return getMembers().get(0);
    }

}
