/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.geometry.Rectangle
 *  l2.commons.geometry.Shape
 *  l2.gameserver.ai.DoorAI
 *  l2.gameserver.model.GameObject
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.Territory
 *  l2.gameserver.model.instances.DoorInstance
 *  l2.gameserver.model.instances.NpcInstance
 *  org.apache.commons.lang3.ArrayUtils
 */
package ai.door;

import l2.commons.geometry.Rectangle;
import l2.commons.geometry.Shape;
import l2.gameserver.ai.DoorAI;
import l2.gameserver.model.GameObject;
import l2.gameserver.model.Player;
import l2.gameserver.model.Territory;
import l2.gameserver.model.instances.DoorInstance;
import l2.gameserver.model.instances.NpcInstance;
import org.apache.commons.lang3.ArrayUtils;

public class SSQDoor
extends DoorAI {
    private static final Territory a = new Territory().add((Shape)new Rectangle(-89696, 217741, -88858, 218085).setZmin(-7520).setZmax(-7320));
    private static final Territory b = new Territory().add((Shape)new Rectangle(-88773, 220765, -88429, 219596).setZmin(-7520).setZmax(-7320));
    private static final Territory c = new Territory().add((Shape)new Rectangle(-87485, 220463, -86501, 220804).setZmin(-7520).setZmax(-7320));
    private static final Territory d = new Territory().add((Shape)new Rectangle(-85646, 219054, -84787, 219408).setZmin(-7520).setZmax(-7320));
    private static final Territory e = new Territory().add((Shape)new Rectangle(-87739, 216646, -87159, 217842).setZmin(-7520).setZmax(-7320));
    private static final int[] z = new int[]{17240102, 17240104, 17240106, 17240108, 17240110};

    public SSQDoor(DoorInstance doorInstance) {
        super(doorInstance);
    }

    public void onEvtTwiceClick(Player player) {
        DoorInstance doorInstance = this.getActor();
        if (doorInstance.getReflection().isDefault()) {
            return;
        }
        if (!ArrayUtils.contains((int[])z, (int)doorInstance.getDoorId())) {
            return;
        }
        if (!player.isInRange((GameObject)doorInstance, 150L)) {
            return;
        }
        switch (doorInstance.getDoorId()) {
            case 17240102: {
                for (NpcInstance npcInstance : doorInstance.getReflection().getNpcs()) {
                    if (!a.isInside(npcInstance.getX(), npcInstance.getY(), npcInstance.getZ()) || npcInstance.isDead()) continue;
                    return;
                }
                break;
            }
            case 17240104: {
                for (NpcInstance npcInstance : doorInstance.getReflection().getNpcs()) {
                    if (!b.isInside(npcInstance.getX(), npcInstance.getY(), npcInstance.getZ()) || npcInstance.isDead()) continue;
                    return;
                }
                break;
            }
            case 17240106: {
                for (NpcInstance npcInstance : doorInstance.getReflection().getNpcs()) {
                    if (!c.isInside(npcInstance.getX(), npcInstance.getY(), npcInstance.getZ()) || npcInstance.isDead()) continue;
                    return;
                }
                break;
            }
            case 17240108: {
                for (NpcInstance npcInstance : doorInstance.getReflection().getNpcs()) {
                    if (!d.isInside(npcInstance.getX(), npcInstance.getY(), npcInstance.getZ()) || npcInstance.isDead()) continue;
                    return;
                }
                break;
            }
            case 17240110: {
                for (NpcInstance npcInstance : doorInstance.getReflection().getNpcs()) {
                    if (!e.isInside(npcInstance.getX(), npcInstance.getY(), npcInstance.getZ()) || npcInstance.isDead()) continue;
                    return;
                }
                break;
            }
        }
        doorInstance.getReflection().openDoor(doorInstance.getDoorId());
    }
}
