/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package l2.gameserver.templates;

import java.lang.reflect.Constructor;
import l2.commons.geometry.Polygon;
import l2.gameserver.ai.CharacterAI;
import l2.gameserver.ai.DoorAI;
import l2.gameserver.model.instances.DoorInstance;
import l2.gameserver.scripts.Scripts;
import l2.gameserver.templates.CharTemplate;
import l2.gameserver.templates.StatsSet;
import l2.gameserver.utils.Location;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class DoorTemplate
extends CharTemplate {
    private static final Logger dC = LoggerFactory.getLogger(DoorTemplate.class);
    public static final Constructor<DoorAI> DEFAULT_AI_CONSTRUCTOR = CharacterAI.class.getConstructors()[0];
    private final int Ej;
    private final String gi;
    private final DoorType a;
    private final boolean gK;
    private final boolean gL;
    private final boolean gM;
    private final boolean gN;
    private final Polygon a;
    private final Location Y;
    private final int Ek;
    private final int El;
    private final int Em;
    private final int En;
    private final int Eo;
    private StatsSet d;
    private Class<DoorAI> c = DoorAI.class;
    private Constructor<DoorAI> a = DEFAULT_AI_CONSTRUCTOR;

    public DoorTemplate(StatsSet statsSet) {
        super(statsSet);
        this.Ej = statsSet.getInteger("uid");
        this.gi = statsSet.getString("name");
        this.a = statsSet.getEnum("door_type", DoorType.class, DoorType.DOOR);
        this.gK = statsSet.getBool("unlockable", false);
        this.gL = statsSet.getBool("show_hp", false);
        this.gM = statsSet.getBool("opened", false);
        this.gN = statsSet.getBool("targetable", true);
        this.Y = (Location)statsSet.get("pos");
        this.a = (Polygon)statsSet.get("shape");
        this.Ek = statsSet.getInteger("key", 0);
        this.El = statsSet.getInteger("open_time", 0);
        this.Em = statsSet.getInteger("random_time", 0);
        this.En = statsSet.getInteger("close_time", 0);
        this.Eo = statsSet.getInteger("master_door", 0);
        this.d = (StatsSet)statsSet.getObject("ai_params", StatsSet.EMPTY);
        this.g(statsSet.getString("ai", "DoorAI"));
    }

    private void g(String string) {
        Class<?> clazz = null;
        try {
            clazz = Class.forName("l2.gameserver.ai." + string);
        }
        catch (ClassNotFoundException classNotFoundException) {
            clazz = Scripts.getInstance().getClasses().get("ai.door." + string);
        }
        if (clazz == null) {
            dC.error("Not found ai class for ai: " + string + ". DoorId: " + this.Ej);
        } else {
            this.c = clazz;
            this.a = this.c.getConstructors()[0];
        }
        if (this.c.isAnnotationPresent(Deprecated.class)) {
            dC.error("Ai type: " + string + ", is deprecated. DoorId: " + this.Ej);
        }
    }

    public CharacterAI getNewAI(DoorInstance doorInstance) {
        try {
            return (CharacterAI)((Constructor)((Object)this.a)).newInstance(doorInstance);
        }
        catch (Exception exception) {
            dC.error("Unable to create ai of doorId " + this.Ej, (Throwable)exception);
            return new DoorAI(doorInstance);
        }
    }

    @Override
    public int getNpcId() {
        return this.Ej;
    }

    public String getName() {
        return this.gi;
    }

    public DoorType getDoorType() {
        return this.a;
    }

    public boolean isUnlockable() {
        return this.gK;
    }

    public boolean isHPVisible() {
        return this.gL;
    }

    public Polygon getPolygon() {
        return this.a;
    }

    public int getKey() {
        return this.Ek;
    }

    public boolean isOpened() {
        return this.gM;
    }

    public Location getLoc() {
        return this.Y;
    }

    public int getOpenTime() {
        return this.El;
    }

    public int getRandomTime() {
        return this.Em;
    }

    public int getCloseTime() {
        return this.En;
    }

    public boolean isTargetable() {
        return this.gN;
    }

    public int getMasterDoor() {
        return this.Eo;
    }

    public StatsSet getAIParams() {
        return this.d;
    }

    public static final class DoorType
    extends Enum<DoorType> {
        public static final /* enum */ DoorType DOOR = new DoorType();
        public static final /* enum */ DoorType WALL = new DoorType();
        private static final /* synthetic */ DoorType[] a;

        public static DoorType[] values() {
            return (DoorType[])a.clone();
        }

        public static DoorType valueOf(String string) {
            return Enum.valueOf(DoorType.class, string);
        }

        private static /* synthetic */ DoorType[] a() {
            return new DoorType[]{DOOR, WALL};
        }

        static {
            a = DoorType.a();
        }
    }
}
