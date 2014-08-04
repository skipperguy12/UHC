package net.njay.uhc.menu.join;

import net.njay.Menu;
import net.njay.MenuManager;
import net.njay.annotation.MenuInventory;
import net.njay.uhc.UHC;
import net.njay.uhc.match.Match;
import net.njay.uhc.match.MatchManager;
import net.njay.uhc.match.MatchState;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class JoinMenu extends Menu implements Listener {
    private MatchManager matchManager;

    public JoinMenu(MatchManager matchManager, Inventory inv) {
        super(null, inv);
        this.matchManager = matchManager;

        Bukkit.getPluginManager().registerEvents(this, UHC.getInstance());
    }

    public void show(Player player) {
        player.openInventory(getInventory());
    }

    public void populate() {
        getInventory().clear();
        for (int i = 0; i < matchManager.getMatches().size(); i++) {
            Match match = matchManager.getMatches().get(i);
            ItemStack wool = new ItemStack(Material.WOOL, 1, getWoolColorFromMatchState(match.getState()));

            ItemMeta itemMeta = wool.getItemMeta();
            itemMeta.setDisplayName(getChatColorFromMatchState(match.getState()) + "Match #" + match.getId());
            itemMeta.setLore(Arrays.asList(ChatColor.GOLD.toString() + UHC.getPlayerManager().getPlayers(match).size() + "/24"));
            wool.setItemMeta(itemMeta);

            getInventory().setItem(i, wool);
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if (!e.getInventory().getName().equals(getInventory().getName())) return;
        if (e.getRawSlot() < matchManager.getMatches().size()) {
            Match toJoin = matchManager.getMatches().get(e.getRawSlot());

            if (toJoin.getState() == MatchState.STARTING)
                UHC.getPlayerManager().getPlayer((Player) e.getWhoClicked()).setMatch(toJoin);
            else
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
