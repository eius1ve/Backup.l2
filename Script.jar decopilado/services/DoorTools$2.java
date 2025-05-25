/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.threading.RunnableImpl
 *  l2.gameserver.Config
 *  l2.gameserver.instancemanager.ReflectionManager
 *  l2.gameserver.model.instances.DoorInstance
 */
package services;

import l2.commons.threading.RunnableImpl;
import l2.gameserver.Config;
import l2.gameserver.instancemanager.ReflectionManager;
import l2.gameserver.model.instances.DoorInstance;

class DoorTools.2
extends RunnableImpl {
    DoorTools.2() {
    }

    public void runImpl() throws Exception {
        DoorInstance doorInstance = ReflectionManager.DEFAULT.getDoor(Config.ON_TIME_OPEN_DOOR_ID);
        if (doorInstance != null) {
            doorInstance.openMe(null, true);
        }
    }
}
