/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver;

import java.util.TimerTask;
import l2.gameserver.Announcements;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.network.l2.s2c.SystemMessage;

private class Shutdown.ShutdownCounter
extends TimerTask {
    private Shutdown.ShutdownCounter() {
    }

    @Override
    public void run() {
        switch (Shutdown.this.fi) {
            case 60: 
            case 120: 
            case 180: 
            case 240: 
            case 300: 
            case 600: 
            case 900: 
            case 1800: {
                switch (Shutdown.this.fh) {
                    case 0: {
                        Announcements.getInstance().announceByCustomMessage("THE_SERVER_WILL_BE_COMING_DOWN_IN_S1_MINUTES", new String[]{String.valueOf(Shutdown.this.fi / 60)});
                        break;
                    }
                    case 2: {
                        Announcements.getInstance().announceByCustomMessage("THE_SERVER_WILL_BE_COMING_RESTARTED_IN_S1_MINUTES", new String[]{String.valueOf(Shutdown.this.fi / 60)});
                    }
                }
                break;
            }
            case 5: 
            case 10: 
            case 20: 
            case 30: {
                Announcements.getInstance().announceToAll((SystemMessage)new SystemMessage(SystemMsg.THE_SERVER_WILL_BE_COMING_DOWN_IN_S1_SECONDS__PLEASE_FIND_A_SAFE_PLACE_TO_LOG_OUT).addNumber(Shutdown.this.fi));
                break;
            }
            case 0: {
                switch (Shutdown.this.fh) {
                    case 0: {
                        Runtime.getRuntime().exit(0);
                        break;
                    }
                    case 2: {
                        Runtime.getRuntime().exit(2);
                    }
                }
                this.cancel();
                return;
            }
        }
        --Shutdown.this.fi;
    }
}
