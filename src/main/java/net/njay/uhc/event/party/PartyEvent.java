package net.njay.uhc.event.party;

import net.njay.uhc.event.UHCEvent;
import net.njay.uhc.match.party.Party;

public class PartyEvent extends UHCEvent{

    protected Party party;

    public PartyEvent(Party party){
        this.party = party;
    }

    public Party getParty(){
        return this.party;
    }

}
