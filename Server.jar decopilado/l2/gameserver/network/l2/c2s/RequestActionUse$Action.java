/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  gnu.trove.TIntObjectHashMap
 */
package l2.gameserver.network.l2.c2s;

import gnu.trove.TIntObjectHashMap;
import java.lang.reflect.Field;
import java.util.ArrayList;

public static class RequestActionUse.Action {
    public static RequestActionUse.Action ACTION0 = new RequestActionUse.Action(0, 0, 0, 1);
    public static RequestActionUse.Action ACTION1 = new RequestActionUse.Action(1, 0, 0, 0);
    public static RequestActionUse.Action ACTION7 = new RequestActionUse.Action(7, 0, 0, 1);
    public static RequestActionUse.Action ACTION10 = new RequestActionUse.Action(10, 0, 0, 1);
    public static RequestActionUse.Action ACTION28 = new RequestActionUse.Action(28, 0, 0, 1);
    public static RequestActionUse.Action ACTION37 = new RequestActionUse.Action(37, 0, 0, 1);
    public static RequestActionUse.Action ACTION38 = new RequestActionUse.Action(38, 0, 0, 1);
    public static RequestActionUse.Action ACTION51 = new RequestActionUse.Action(51, 0, 0, 1);
    public static RequestActionUse.Action ACTION61 = new RequestActionUse.Action(61, 0, 0, 1);
    public static RequestActionUse.Action ACTION96 = new RequestActionUse.Action(96, 0, 0, 1);
    public static RequestActionUse.Action ACTION97 = new RequestActionUse.Action(97, 0, 0, 1);
    public static RequestActionUse.Action ACTION67 = new RequestActionUse.Action(67, 0, 0, 1);
    public static RequestActionUse.Action ACTION68 = new RequestActionUse.Action(68, 0, 0, 1);
    public static RequestActionUse.Action ACTION69 = new RequestActionUse.Action(69, 0, 0, 1);
    public static RequestActionUse.Action ACTION70 = new RequestActionUse.Action(70, 0, 0, 1);
    public static RequestActionUse.Action ACTION90 = new RequestActionUse.Action(90, 0, 0, 1);
    public static RequestActionUse.Action ACTION78 = new RequestActionUse.Action(78, 0, 1, 0);
    public static RequestActionUse.Action ACTION79 = new RequestActionUse.Action(79, 0, 1, 0);
    public static RequestActionUse.Action ACTION80 = new RequestActionUse.Action(80, 0, 1, 0);
    public static RequestActionUse.Action ACTION81 = new RequestActionUse.Action(81, 0, 1, 0);
    public static RequestActionUse.Action ACTION82 = new RequestActionUse.Action(82, 0, 1, 0);
    public static RequestActionUse.Action ACTION83 = new RequestActionUse.Action(83, 0, 1, 0);
    public static RequestActionUse.Action ACTION84 = new RequestActionUse.Action(84, 0, 1, 0);
    public static RequestActionUse.Action ACTION85 = new RequestActionUse.Action(85, 0, 1, 0);
    public static RequestActionUse.Action ACTION15 = new RequestActionUse.Action(15, 1, 0, 0);
    public static RequestActionUse.Action ACTION16 = new RequestActionUse.Action(16, 1, 0, 0);
    public static RequestActionUse.Action ACTION17 = new RequestActionUse.Action(17, 1, 0, 0);
    public static RequestActionUse.Action ACTION19 = new RequestActionUse.Action(19, 1, 0, 0);
    public static RequestActionUse.Action ACTION21 = new RequestActionUse.Action(21, 1, 0, 0);
    public static RequestActionUse.Action ACTION22 = new RequestActionUse.Action(22, 1, 0, 0);
    public static RequestActionUse.Action ACTION23 = new RequestActionUse.Action(23, 1, 0, 0);
    public static RequestActionUse.Action ACTION52 = new RequestActionUse.Action(52, 1, 0, 0);
    public static RequestActionUse.Action ACTION53 = new RequestActionUse.Action(53, 1, 0, 0);
    public static RequestActionUse.Action ACTION54 = new RequestActionUse.Action(54, 1, 0, 0);
    public static RequestActionUse.Action ACTION12 = new RequestActionUse.Action(12, 3, 2, 2);
    public static RequestActionUse.Action ACTION13 = new RequestActionUse.Action(13, 3, 3, 2);
    public static RequestActionUse.Action ACTION14 = new RequestActionUse.Action(14, 3, 4, 2);
    public static RequestActionUse.Action ACTION24 = new RequestActionUse.Action(24, 3, 6, 2);
    public static RequestActionUse.Action ACTION25 = new RequestActionUse.Action(25, 3, 5, 2);
    public static RequestActionUse.Action ACTION26 = new RequestActionUse.Action(26, 3, 7, 2);
    public static RequestActionUse.Action ACTION29 = new RequestActionUse.Action(29, 3, 8, 2);
    public static RequestActionUse.Action ACTION30 = new RequestActionUse.Action(30, 3, 9, 2);
    public static RequestActionUse.Action ACTION31 = new RequestActionUse.Action(31, 3, 10, 2);
    public static RequestActionUse.Action ACTION33 = new RequestActionUse.Action(33, 3, 11, 2);
    public static RequestActionUse.Action ACTION34 = new RequestActionUse.Action(34, 3, 12, 2);
    public static RequestActionUse.Action ACTION35 = new RequestActionUse.Action(35, 3, 13, 2);
    public static RequestActionUse.Action ACTION62 = new RequestActionUse.Action(62, 3, 14, 2);
    public static RequestActionUse.Action ACTION66 = new RequestActionUse.Action(66, 3, 15, 2);
    public static RequestActionUse.Action ACTION87 = new RequestActionUse.Action(87, 3, 28, 2);
    public static RequestActionUse.Action ACTION88 = new RequestActionUse.Action(88, 3, 29, 2);
    public static RequestActionUse.Action ACTION89 = new RequestActionUse.Action(89, 3, 30, 2);
    public static RequestActionUse.Action ACTION71 = new RequestActionUse.Action(71, 4, 16, 2);
    public static RequestActionUse.Action ACTION72 = new RequestActionUse.Action(72, 4, 17, 2);
    public static RequestActionUse.Action ACTION73 = new RequestActionUse.Action(73, 4, 18, 2);
    public int id;
    public int type;
    public int value;
    public int transform;
    private static RequestActionUse.Action[] a;
    private static TIntObjectHashMap<RequestActionUse.Action> y;

    private RequestActionUse.Action(int n, int n2, int n3, int n4) {
        this.id = n;
        this.type = n2;
        this.value = n3;
        this.transform = n4;
    }

    private static RequestActionUse.Action[] a() {
        if (a != null) {
            return a;
        }
        ArrayList<RequestActionUse.Action> arrayList = new ArrayList<RequestActionUse.Action>();
        for (Field field : RequestActionUse.Action.class.getDeclaredFields()) {
            if (!RequestActionUse.Action.class.isAssignableFrom(field.getType())) continue;
            try {
                arrayList.add((RequestActionUse.Action)field.get(null));
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
        a = arrayList.toArray(new RequestActionUse.Action[arrayList.size()]);
        return a;
    }

    public static RequestActionUse.Action find(int n) {
        if (y == null) {
            TIntObjectHashMap tIntObjectHashMap = new TIntObjectHashMap();
            for (RequestActionUse.Action action : RequestActionUse.Action.a()) {
                tIntObjectHashMap.put(action.id, (Object)action);
            }
            y = tIntObjectHashMap;
        }
        return (RequestActionUse.Action)y.get(n);
    }
}
