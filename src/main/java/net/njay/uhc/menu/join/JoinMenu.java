package net.njay.uhc.menu.join;

import com.google.common.collect.Lists;
import net.njay.Menu;
import net.njay.MenuManager;
import net.njay.annotation.MenuInventory;
import net.njay.uhc.Config;
import net.njay.uhc.UHC;
import net.njay.uhc.match.Match;
import net.njay.uhc.match.MatchManager;
import net.njay.uhc.match.MatchState;
import net.njay.uhc.util.location.LocationUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;

public class JoinMenu extends Menu implements Listener {
    private MatchManager matchManager;

    public JoinMenu(MatchManager matchManager, Inventory inv) {
        super(null, inv);
        this.matchManager = matchManager;

        Bukkit.getScheduler().runTaskTimer(UHC.getInstance(), new Runnable(){ public void run() { populate(); } }, 20, 20);
    }

    public void show(final Player player) {
        Bukkit.getScheduler().runTaskLater(UHC.getInstance(), new Runnable() { public void run(){ player.openInventory(getInventory()); }}, 2);
    }

    public void populate() {
        getInventory().clear();
        for (int i = 0; i < matchManager.getMatches().size(); i++) {
            Match match = matchManager.getMatches().get(i);
            ItemStack wool = new ItemStack(Material.WOOL, 1, getWoolColorFromMatchState(match.getState()));

            ItemMeta itemMeta = wool.getItemMeta();
            itemMeta.setDisplayName(getChatColorFromMatchState(match.getState()) + "Match #" + (match.getId()+1));
            List<String> lore = itemMeta.getLore();
            if (lore == null) lore = Lists.newArrayList();
            lore.add(ChatColor.GOLD.toString() + UHC.getPlayerManager().getPlayers(match).size() + "/24");
            lore.add(match.getStatusMessage());
            itemMeta.setLore(lore);
            wool.setItemMeta(itemMeta);

            getInventory().setItem(i, wool);
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if (!e.getInventory().getName().equals(getInventory().getName())) return;
        if (e.getRawSlot() < matchManager.getMatches().size() && e.getRawSlot() >= 0) {
            Match toJoin = matchManager.getMatches().get(e.getRawSlot());
            Player player = (Player) e.getWhoClicked();
            if (toJoin.getState() == MatchState.STARTING){
                UHC.getPlayerManager().getPlayer((Player) e.getWhoClicked()).setMatch(toJoin);
                player.teleport(LocationUtil.findSuitableLocation(new Location(toJoin.getWorld(), 0, 0, 0), Config.Match.matchRadius)); //TODO: Change to lobby spawn
            }else if (toJoin.getState() == MatchState.IDLE){
                toJoin.setState(MatchState.STARTING); //TODO: Add a match starting countdown that doesn't start the match until minimum number of players is reached
                UHC.getPlayerManager().getPlayer(player).setMatch(toJoin);
                player.teleport(LocationUtil.findSuitableLocation(new Location(toJoin.getWorld(), 0, 0, 0), Config.Match.matchRadius)); //TODO: Change to lobby spawn
            }else
                UHC.getPlayerManager().getPlayer((Player) e.getWhoClicked()).getBukkit().sendMessage(ChatColor.RED + "The match state currently does not allow for players to join!");
        }
        e.setCancelled(true);
    }

    private short getWoolColorFromMatchState(MatchState state) {
        switch (state) {
            case IDLE:
                return 8;
            case STARTING:
                return 4;
            case RUNNING:
                return 5;
            case FINISHED:
                return 14;
            default:
                return 15;
        }
    }

    private ChatColor getChatColorFromMatchState(MatchState state) {
        switch (state) {
            case IDLE:
                return ChatColor.GRAY;
            case STARTING:
                return ChatColor.YELLOW;
            case RUNNING:
                return ChatColor.GREEN;
            case FINISHED:
                return ChatColor.RED;
            default:
                return ChatColor.BLACK;
        }
    }
}
