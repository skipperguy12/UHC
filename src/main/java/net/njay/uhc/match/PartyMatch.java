package net.njay.uhc.match;

import com.google.common.collect.Lists;
import net.minecraft.util.com.google.common.collect.ImmutableList;
import net.njay.uhc.match.party.Party;
import net.njay.uhc.player.UHCPlayer;
import org.bukkit.World;

import java.util.List;

public class PartyMatch extends Match {

    private int partySize;
    private List<Party> parties;

    public PartyMatch(int id, World world, int partySize) {
        super(id, world);
        this.partySize = partySize;
        this.parties = Lists.newArrayList();
    }

    public int getPartySize(){ return this.partySize; }
    public List<Party> getParties(){ return ImmutableList.copyOf(parties); }

    public void addParty(Party party){
        parties.add(party);
        //TODO: SendMessage
    }

    public void removeParty(Party party){
        parties.remove(party);
        //TODO: SendMessage
    }

    public Party getParty(UHCPlayer player){
        for (Party party : parties)
            for (UHCPlayer partyPlayer : party.getMembers())
                if (player.equals(partyPlayer))
                    return party;
        return null;
    }

}
