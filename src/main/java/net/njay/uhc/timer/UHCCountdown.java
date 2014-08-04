package net.njay.uhc.timer;

import net.njay.uhc.UHC;
import org.bukkit.scheduler.BukkitRunnable;

public abstract class UHCCountdown extends BukkitRunnable {

    protected int length;
    protected int timeLeft;

    public UHCCountdown(int length){
        this.length = length;
        this.timeLeft = length;
    }

    public int getLength(){ return this.length; }
    public int getTimeLeft(){ return this.timeLeft; }

    @Override
    public void run() {
        if (timeLeft-- <= 0){
            timeLeft = 0;
            onFinish();
            end();
        }
    }

    public UHCCountdown start(){
        runTaskTimer(UHC.getInstance(), 0, 20);
        return this;
    }

    public UHCCountdown end(){
        cancel();
        return this;
    }

    public abstract void onFinish();

}
