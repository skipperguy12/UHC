package net.njay.uhc.event.party;

import net.njay.uhc.match.party.Party;
import net.njay.uhc.player.UHCPlayer;

public class PlayerLeavePartyEvent extends PartyEvent {

    public UHCPlayer player;

    public PlayerLeavePartyEvent(Party party, UHCPlayer player){
        super(party);
        this.player = player;
    }

    public UHCPlayer getPlayer(){
        return this.player;
    }

}
