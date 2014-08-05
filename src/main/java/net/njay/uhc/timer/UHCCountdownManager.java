package net.njay.uhc.timer;

public class UHCCountdownManager {

    private UHCCountdown currentCountdown;

    public int start(UHCCountdown countdown, int seconds) {
        UHCCountdown oldCountdown = this.currentCountdown;
        if (oldCountdown != null)
            oldCountdown.cancel();
        int taskId = countdown.start(seconds);
        this.currentCountdown = countdown;
        return taskId;
    }

    public UHCCountdown getCurrentCountdown() {
        return this.currentCountdown;
    }

}
