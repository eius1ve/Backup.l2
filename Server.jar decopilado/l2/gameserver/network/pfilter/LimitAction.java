/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.pfilter;

import l2.gameserver.model.Player;
import l2.gameserver.network.l2.GameClient;
import l2.gameserver.network.l2.s2c.ActionFail;
import l2.gameserver.network.l2.s2c.LeaveWorld;
import l2.gameserver.utils.Log;

abstract class LimitAction {
    private static final LimitAction a = new LimitAction(){

        @Override
        public void doIt(GameClient gameClient) {
        }
    };
    private static final LimitAction b = new LimitAction(){

        @Override
        public void doIt(GameClient gameClient) {
            gameClient.sendPacket(ActionFail.STATIC);
        }
    };
    private static final LimitAction c = new LimitAction(){

        @Override
        public void doIt(GameClient gameClient) {
            Player player = gameClient.getActiveChar();
            if (player != null) {
                player.kick();
            } else {
                gameClient.close(LeaveWorld.STATIC);
            }
        }
    };

    LimitAction() {
    }

    public static LimitAction doDrop() {
        return a;
    }

    public static LimitAction doActionFail() {
        return b;
    }

    public static LimitAction doKick() {
        return c;
    }

    public static LimitAction doLog(String string) {
        return new DoLogLimitAction(string);
    }

    public static LimitAction doMsg(String string) {
        return new DoMsgLimitAction(string);
    }

    public abstract void doIt(GameClient var1);

    public boolean dropPacket() {
        return true;
    }

    private static final class DoLogLimitAction
    extends LimitAction {
        private final String fP;

        private DoLogLimitAction(String string) {
            this.fP = string;
        }

        @Override
        public void doIt(GameClient gameClient) {
            String string = this.fP;
            Player player = gameClient.getActiveChar();
            string = string.replace("%user_name%", player != null ? player.getName() : "");
            string = string.replace("%account_name%", gameClient.getLogin() != null ? gameClient.getLogin() : "EMPTY LOGIN");
            string = string.replace("%ip%", gameClient.getIpAddr() != null ? gameClient.getIpAddr() : "?.?.?.?");
            string = string.replace("%hwid%", gameClient.getHwid() != null ? gameClient.getHwid() : "EMPTY HWID");
            Log.network("RateLimit: " + string);
        }

        @Override
        public boolean dropPacket() {
            return false;
        }
    }

    private static final class DoMsgLimitAction
    extends LimitAction {
        private final String fQ;

        private DoMsgLimitAction(String string) {
            this.fQ = string;
        }

        @Override
        public void doIt(GameClient gameClient) {
            gameClient.sendMessage(this.fQ);
        }

        @Override
        public boolean dropPacket() {
            return false;
        }
    }
}
