package net.njay.uhc.event.party;

import net.njay.uhc.match.party.Party;
import net.njay.uhc.player.UHCPlayer;

public class PlayerInviteToPartyEvent extends PartyEvent {

    private UHCPlayer invited;

    public PlayerInviteToPartyEvent(Party party, UHCPlayer invited) {
        super(party);
        this.invited = invited;
    }

    public UHCPlayer getInvitedPlayer(){
        return this.invited;
    }
}
