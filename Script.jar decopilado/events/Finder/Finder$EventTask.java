/*
 * Decompiled with CFR 0.152.
 */
package events.Finder;

private class Finder.EventTask
implements Runnable {
    private Finder.EventTask() {
    }

    @Override
    public void run() {
        Finder.this.spawnHostageAndRaider();
    }
}
