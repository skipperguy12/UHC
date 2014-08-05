package net.njay.uhc.util.location;

import org.bukkit.Location;
import org.bukkit.Material;

import java.util.Random;

public class LocationUtil {

    public static Location findSuitableLocation(Location center, int radius){
        Random rand = new Random();
        Location top = center.clone()
                .add(-radius, 0, -radius)
                .add(rand.nextInt(radius*2), 0, rand.nextInt(radius*2));
        top.setY(200);
        Location current = top.clone();
        do{
            current.add(0, -1, 0);
        }while(current.getBlock().getType() == Material.AIR);
        return current.add(0,1,0);
    }

}
