/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.model.Player
 *  l2.gameserver.network.l2.c2s.L2GameClientPacket
 *  l2.gameserver.network.l2.components.IStaticPacket
 *  l2.gameserver.skills.AbnormalEffect
 */
package dressmeEngine.A;

import java.util.logging.Level;
import java.util.logging.Logger;
import l2.gameserver.model.Player;
import l2.gameserver.network.l2.c2s.L2GameClientPacket;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.s2c.UserInfo;
import l2.gameserver.skills.AbnormalEffect;

public class A
extends L2GameClientPacket {
    private static final String E = "[C] C7 RequestPreviewItem";
    protected static final Logger _log = Logger.getLogger(A.class.getName());
    private int D;
    private int B;
    private int A;
    private int[] C;

    protected void readImpl() {
        this.D = this.readD();
        this.B = this.readD();
        this.A = this.readD();
        if (this.A < 0) {
            this.A = 0;
        }
        if (this.A > 100) {
            return;
        }
        this.C = new int[this.A];
        int i = 0;
        while (i < this.A) {
            this.C[i] = this.readD();
            ++i;
        }
    }

    protected void runImpl() {
    }

    public static void refreshVisual(Player Player2) {
        Player2.startAbnormalEffect(AbnormalEffect.AIR_BATTLE_SLOW);
        Player2.stopAbnormalEffect(AbnormalEffect.AIR_BATTLE_SLOW);
    }

    public String getType() {
        return E;
    }

    public static class _A
    implements Runnable {
        private final Player A;

        public _A(Player Player2) {
            this.A = Player2;
        }

        @Override
        public void run() {
            try {
                this.A.unsetVar("tryEnchant");
                this.A.sendPacket((IStaticPacket)new UserInfo(this.A));
            }
            catch (Exception e) {
                _log.log(Level.SEVERE, "", e);
            }
        }
    }
}
