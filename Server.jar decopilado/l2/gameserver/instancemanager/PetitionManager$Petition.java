/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.lang3.StringUtils
 */
package l2.gameserver.instancemanager;

import java.util.ArrayList;
import java.util.List;
import l2.gameserver.dao.CharacterDAO;
import l2.gameserver.instancemanager.PetitionManager;
import l2.gameserver.model.Player;
import l2.gameserver.model.World;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;
import l2.gameserver.network.l2.s2c.Say2;
import l2.gameserver.network.l2.s2c.SystemMessage;
import org.apache.commons.lang3.StringUtils;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
private class PetitionManager.Petition {
    private long aQ = System.currentTimeMillis();
    private long aR = -1L;
    private int _id;
    private PetitionManager.PetitionType a;
    private PetitionManager.PetitionState a = PetitionManager.PetitionState.Pending;
    private String cn;
    private List<Say2> ay = new ArrayList<Say2>();
    private int fP;
    private int fQ;

    public PetitionManager.Petition(Player player, String string, int n) {
        this._id = PetitionManager.this.getNextId();
        this.a = PetitionManager.PetitionType.values()[n - 1];
        this.cn = string;
        this.fP = player.getObjectId();
    }

    protected boolean addLogMessage(Say2 say2) {
        return this.ay.add(say2);
    }

    protected List<Say2> getLogMessages() {
        return this.ay;
    }

    public boolean endPetitionConsultation(PetitionManager.PetitionState petitionState) {
        this.setState(petitionState);
        this.aR = System.currentTimeMillis();
        if (this.getResponder() != null && this.getResponder().isOnline()) {
            if (petitionState == PetitionManager.PetitionState.Responder_Reject) {
                this.getPetitioner().sendMessage("Your petition was rejected. Please try again later.");
            } else {
                this.getResponder().sendPacket((IStaticPacket)new SystemMessage(SystemMsg.ENDING_PETITION_CONSULTATION_WITH_C1).addString(this.getPetitionerName()));
                if (petitionState == PetitionManager.PetitionState.Petitioner_Cancel) {
                    this.getResponder().sendPacket((IStaticPacket)new SystemMessage(SystemMsg.RECEIPT_NO_S1_PETITION_CANCELLED).addNumber(this.getId()));
                }
            }
        }
        if (this.isPetitionerOnline()) {
            this.getPetitioner().sendPacket((IStaticPacket)new SystemMessage(SystemMsg.THIS_ENDS_THE_GM_PETITION_CONSULTATION_NPLEASE_GIVE_US_FEEDBACK_ON_THE_PETITION_SERVICE));
        }
        PetitionManager.this.getCompletedPetitions().put(this.getId(), this);
        return PetitionManager.this.getPendingPetitions().remove(this.getId()) != null;
    }

    public String getContent() {
        return this.cn;
    }

    public int getId() {
        return this._id;
    }

    public Player getPetitioner() {
        return World.getPlayer(this.fP);
    }

    public boolean isPetitionerOnline() {
        Player player = World.getPlayer(this.fP);
        if (player != null) {
            return player.isOnline();
        }
        return false;
    }

    public String getPetitionerName() {
        Player player = World.getPlayer(this.fP);
        if (player != null) {
            return player.getName();
        }
        return StringUtils.defaultString((String)CharacterDAO.getInstance().getNameByObjectId(this.fP), (String)"[Unknown]");
    }

    public Player getResponder() {
        return World.getPlayer(this.fQ);
    }

    public long getEndTime() {
        return this.aR;
    }

    public long getSubmitTime() {
        return this.aQ;
    }

    public PetitionManager.PetitionState getState() {
        return this.a;
    }

    public String getTypeAsString() {
        return this.a.toString().replace("_", " ");
    }

    public void sendPetitionerPacket(L2GameServerPacket l2GameServerPacket) {
        Player player = World.getPlayer(this.fP);
        if (player != null) {
            player.sendPacket((IStaticPacket)l2GameServerPacket);
        }
    }

    public void sendResponderPacket(L2GameServerPacket l2GameServerPacket) {
        if (this.getResponder() == null || !this.getResponder().isOnline()) {
            this.endPetitionConsultation(PetitionManager.PetitionState.Responder_Missing);
            return;
        }
        this.getResponder().sendPacket((IStaticPacket)l2GameServerPacket);
    }

    public void setState(PetitionManager.PetitionState petitionState) {
        this.a = petitionState;
    }

    public void setResponder(Player player) {
        if (this.getResponder() != null) {
            return;
        }
        this.fQ = player.getObjectId();
    }
}
