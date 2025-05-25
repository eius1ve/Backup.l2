/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.handler.voicecommands;

import java.util.HashMap;
import java.util.Map;
import l2.commons.data.xml.AbstractHolder;
import l2.gameserver.handler.voicecommands.IVoicedCommandHandler;
import l2.gameserver.handler.voicecommands.impl.Augments;
import l2.gameserver.handler.voicecommands.impl.AutoFarm;
import l2.gameserver.handler.voicecommands.impl.Banking;
import l2.gameserver.handler.voicecommands.impl.CWHPrivileges;
import l2.gameserver.handler.voicecommands.impl.Cfg;
import l2.gameserver.handler.voicecommands.impl.Help;
import l2.gameserver.handler.voicecommands.impl.ItemRemaining;
import l2.gameserver.handler.voicecommands.impl.Jump;
import l2.gameserver.handler.voicecommands.impl.Mammon;
import l2.gameserver.handler.voicecommands.impl.Offline;
import l2.gameserver.handler.voicecommands.impl.Online;
import l2.gameserver.handler.voicecommands.impl.OpenBoxService;
import l2.gameserver.handler.voicecommands.impl.PCBang;
import l2.gameserver.handler.voicecommands.impl.Ping;
import l2.gameserver.handler.voicecommands.impl.PvPEventRegistration;
import l2.gameserver.handler.voicecommands.impl.Relocate;
import l2.gameserver.handler.voicecommands.impl.Relog;
import l2.gameserver.handler.voicecommands.impl.ServerInfo;
import l2.gameserver.handler.voicecommands.impl.Services;
import l2.gameserver.handler.voicecommands.impl.Sponsor;
import l2.gameserver.handler.voicecommands.impl.Wedding;
import l2.gameserver.handler.voicecommands.impl.WhoAmI;

public class VoicedCommandHandler
extends AbstractHolder {
    private static final VoicedCommandHandler a = new VoicedCommandHandler();
    private Map<String, IVoicedCommandHandler> ad = new HashMap<String, IVoicedCommandHandler>();

    public static VoicedCommandHandler getInstance() {
        return a;
    }

    private VoicedCommandHandler() {
        this.registerVoicedCommandHandler(new Offline());
        this.registerVoicedCommandHandler(new Online());
        this.registerVoicedCommandHandler(new PCBang());
        this.registerVoicedCommandHandler(new ServerInfo());
        this.registerVoicedCommandHandler(new Wedding());
        this.registerVoicedCommandHandler(new Services());
        this.registerVoicedCommandHandler(new Sponsor());
        this.registerVoicedCommandHandler(new WhoAmI());
        this.registerVoicedCommandHandler(new Help());
        this.registerVoicedCommandHandler(new Relog());
        this.registerVoicedCommandHandler(new Ping());
        this.registerVoicedCommandHandler(new Cfg());
        this.registerVoicedCommandHandler(new OpenBoxService());
        this.registerVoicedCommandHandler(new CWHPrivileges());
        this.registerVoicedCommandHandler(new Augments());
        this.registerVoicedCommandHandler(new Relocate());
        this.registerVoicedCommandHandler(new ItemRemaining());
        this.registerVoicedCommandHandler(new Banking());
        this.registerVoicedCommandHandler(new Mammon());
        this.registerVoicedCommandHandler(new PvPEventRegistration());
        this.registerVoicedCommandHandler(new AutoFarm());
        this.registerVoicedCommandHandler(new Jump());
    }

    public void registerVoicedCommandHandler(IVoicedCommandHandler iVoicedCommandHandler) {
        String[] stringArray;
        for (String string : stringArray = iVoicedCommandHandler.getVoicedCommandList()) {
            this.ad.put(string, iVoicedCommandHandler);
        }
    }

    public IVoicedCommandHandler getVoicedCommandHandler(String string) {
        String string2 = string;
        if (string.indexOf(" ") != -1) {
            string2 = string.substring(0, string.indexOf(" "));
        }
        return this.ad.get(string2);
    }

    @Override
    public int size() {
        return this.ad.size();
    }

    @Override
    public void clear() {
        this.ad.clear();
    }
}
