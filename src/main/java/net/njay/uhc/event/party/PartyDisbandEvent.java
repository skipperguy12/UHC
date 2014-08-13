package net.njay.uhc.event.party;

import net.njay.uhc.match.party.Party;

public class PartyDisbandEvent extends PartyEvent {

    public PartyDisbandEvent(Party party) {
        super(party);
    }

}
