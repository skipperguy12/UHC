package net.njay.uhc.util.location.teleport.spawn;

import net.minecraft.server.v1_7_R3.*;
import net.njay.uhc.UHC;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_7_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;

public class ForceRespawnUtil {

    public static void forceRespawn(final Player p){
        ((CraftPlayer)p).getHandle().playerConnection.a(new PacketPlayInClientCommand(EnumClientCommand.PERFORM_RESPAWN));
    }

}
