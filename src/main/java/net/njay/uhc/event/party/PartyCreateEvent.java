package net.njay.uhc.event.party;

import net.njay.uhc.event.UHCEvent;
import net.njay.uhc.match.party.Party;
import net.njay.uhc.player.UHCPlayer;

public class PartyCreateEvent extends PartyEvent {

    public PartyCreateEvent(Party party) {
        super(party);
    }

    public UHCPlayer getCreator(){
        return getParty().getOwner();
    }

}
