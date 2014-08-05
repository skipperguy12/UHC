package net.njay.uhc.timer;

import net.njay.uhc.UHC;
import net.njay.uhc.match.Match;
import org.bukkit.scheduler.BukkitRunnable;

public abstract class UHCCountdown extends BukkitRunnable {

    protected Match match;
    private int seconds;

    public UHCCountdown(Match match) {
        this.match = match;
    }

    public int start(int seconds) {
        this.seconds = seconds;
        super.runTaskTimer(UHC.getInstance(), 0, 20);
        return super.getTaskId();
    }

    public int getSeconds() {
        return this.seconds;
    }

    public Match getMatch() {
        return this.match;
    }

    @Override
    public void run() {
        if (seconds <= 0) {
            this.end();
            super.cancel();
        } else {
            this.tick(seconds--);
        }
    }

    public abstract void tick(int seconds);

    public abstract void end();

}
