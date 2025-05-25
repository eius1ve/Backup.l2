/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.components;

import l2.gameserver.model.Player;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.s2c.ExStartScenePlayer;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public final class SceneMovie
extends Enum<SceneMovie>
implements IStaticPacket {
    public static final /* enum */ SceneMovie LINDVIOR_SPAWN = new SceneMovie(1, 45500);
    public static final /* enum */ SceneMovie ECHMUS_OPENING = new SceneMovie(2, 62000);
    public static final /* enum */ SceneMovie ECHMUS_SUCCESS = new SceneMovie(3, 18000);
    public static final /* enum */ SceneMovie ECHMUS_FAIL = new SceneMovie(4, 17000);
    public static final /* enum */ SceneMovie TIAT_OPENING = new SceneMovie(5, 54200);
    public static final /* enum */ SceneMovie TIAT_SUCCESS = new SceneMovie(6, 26100);
    public static final /* enum */ SceneMovie TIAT_FAIL = new SceneMovie(7, 24800);
    public static final /* enum */ SceneMovie SSQ_SERIES_OF_DOUBT = new SceneMovie(8, 26000);
    public static final /* enum */ SceneMovie SSQ_DYING_MESSAGE = new SceneMovie(9, 27000);
    public static final /* enum */ SceneMovie SSQ_MAMMONS_CONTRACT = new SceneMovie(10, 98000);
    public static final /* enum */ SceneMovie SSQ_SECRET_RITUAL_PRIEST = new SceneMovie(11, 30000);
    public static final /* enum */ SceneMovie SSQ_SEAL_EMPEROR_1 = new SceneMovie(12, 18000);
    public static final /* enum */ SceneMovie SSQ_SEAL_EMPEROR_2 = new SceneMovie(13, 26000);
    public static final /* enum */ SceneMovie SSQ_EMBRYO = new SceneMovie(14, 28000);
    public static final /* enum */ SceneMovie FREYA_OPENING = new SceneMovie(15, 53500);
    public static final /* enum */ SceneMovie FREYA_PHASE_CHANGE_A = new SceneMovie(16, 21100);
    public static final /* enum */ SceneMovie FREYA_PHASE_CHANGE_B = new SceneMovie(17, 21500);
    public static final /* enum */ SceneMovie KEGOR_INTRUSION = new SceneMovie(18, 27000);
    public static final /* enum */ SceneMovie FREYA_ENDING_A = new SceneMovie(19, 16000);
    public static final /* enum */ SceneMovie FREYA_ENDING_B = new SceneMovie(20, 56000);
    public static final /* enum */ SceneMovie FREYA_FORCED_DEFEAT = new SceneMovie(21, 21000);
    public static final /* enum */ SceneMovie FREYA_DEFEAT = new SceneMovie(22, 20500);
    public static final /* enum */ SceneMovie ICE_HEAVY_KNIGHT_SPAWN = new SceneMovie(23, 7000);
    public static final /* enum */ SceneMovie SSQ2_HOLY_BURIAL_GROUND_OPENING = new SceneMovie(24, 23000);
    public static final /* enum */ SceneMovie SSQ2_HOLY_BURIAL_GROUND_CLOSING = new SceneMovie(25, 22000);
    public static final /* enum */ SceneMovie SSQ2_SOLINA_TOMB_OPENING = new SceneMovie(26, 25000);
    public static final /* enum */ SceneMovie SSQ2_SOLINA_TOMB_CLOSING = new SceneMovie(27, 15000);
    public static final /* enum */ SceneMovie SSQ2_ELYSS_NARRATION = new SceneMovie(28, 59000);
    public static final /* enum */ SceneMovie SSQ2_BOSS_OPENING = new SceneMovie(29, 60000);
    public static final /* enum */ SceneMovie SSQ2_BOSS_CLOSING = new SceneMovie(30, 60000);
    public static final /* enum */ SceneMovie LANDING_KSERTH_LEFT = new SceneMovie(1000, 10000);
    public static final /* enum */ SceneMovie LANDING_KSERTH_RIGHT = new SceneMovie(1001, 10000);
    public static final /* enum */ SceneMovie LANDING_INFINITY = new SceneMovie(1002, 10000);
    public static final /* enum */ SceneMovie LANDING_DESTRUCTION = new SceneMovie(1003, 10000);
    public static final /* enum */ SceneMovie LANDING_ANNIHILATION = new SceneMovie(1004, 15000);
    private final int sr;
    private final int ss;
    private final L2GameServerPacket a;
    private static final /* synthetic */ SceneMovie[] a;

    public static SceneMovie[] values() {
        return (SceneMovie[])a.clone();
    }

    public static SceneMovie valueOf(String string) {
        return Enum.valueOf(SceneMovie.class, string);
    }

    private SceneMovie(int n2, int n3) {
        this.sr = n2;
        this.ss = n3;
        this.a = new ExStartScenePlayer(this);
    }

    public int getId() {
        return this.sr;
    }

    public int getDuration() {
        return this.ss;
    }

    @Override
    public L2GameServerPacket packet(Player player) {
        return this.a;
    }

    private static /* synthetic */ SceneMovie[] a() {
        return new SceneMovie[]{LINDVIOR_SPAWN, ECHMUS_OPENING, ECHMUS_SUCCESS, ECHMUS_FAIL, TIAT_OPENING, TIAT_SUCCESS, TIAT_FAIL, SSQ_SERIES_OF_DOUBT, SSQ_DYING_MESSAGE, SSQ_MAMMONS_CONTRACT, SSQ_SECRET_RITUAL_PRIEST, SSQ_SEAL_EMPEROR_1, SSQ_SEAL_EMPEROR_2, SSQ_EMBRYO, FREYA_OPENING, FREYA_PHASE_CHANGE_A, FREYA_PHASE_CHANGE_B, KEGOR_INTRUSION, FREYA_ENDING_A, FREYA_ENDING_B, FREYA_FORCED_DEFEAT, FREYA_DEFEAT, ICE_HEAVY_KNIGHT_SPAWN, SSQ2_HOLY_BURIAL_GROUND_OPENING, SSQ2_HOLY_BURIAL_GROUND_CLOSING, SSQ2_SOLINA_TOMB_OPENING, SSQ2_SOLINA_TOMB_CLOSING, SSQ2_ELYSS_NARRATION, SSQ2_BOSS_OPENING, SSQ2_BOSS_CLOSING, LANDING_KSERTH_LEFT, LANDING_KSERTH_RIGHT, LANDING_INFINITY, LANDING_DESTRUCTION, LANDING_ANNIHILATION};
    }

    static {
        a = SceneMovie.a();
    }
}
