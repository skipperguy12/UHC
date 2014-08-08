package net.njay.uhc.timer;

public abstract class UHCCountdownInfo {
    public final void cancel() {
    }

    public void start() {
    }

    public abstract void onFinish();

    public abstract void onTick(int secsUntilFinished);
}
