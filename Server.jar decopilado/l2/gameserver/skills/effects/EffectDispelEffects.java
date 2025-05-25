/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.lang3.ArrayUtils
 */
package l2.gameserver.skills.effects;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import l2.commons.lang.reference.HardReference;
import l2.commons.threading.RunnableImpl;
import l2.commons.util.Rnd;
import l2.gameserver.Config;
import l2.gameserver.ThreadPoolManager;
import l2.gameserver.model.Effect;
import l2.gameserver.model.Player;
import l2.gameserver.model.Skill;
import l2.gameserver.model.base.TeamType;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.network.l2.s2c.SystemMessage;
import l2.gameserver.skills.effects.EffectTemplate;
import l2.gameserver.stats.Env;
import l2.gameserver.stats.Stats;
import org.apache.commons.lang3.ArrayUtils;

public class EffectDispelEffects
extends Effect {
    private final String fW;
    private final int CY;
    private final String[] aP;
    private final int CZ;
    private final int Da;
    private static boolean fD;

    public EffectDispelEffects(Env env, EffectTemplate effectTemplate) {
        super(env, effectTemplate);
        this.fW = effectTemplate.getParam().getString("dispelType", "");
        this.CY = effectTemplate.getParam().getInteger("cancelRate", 0);
        this.CZ = effectTemplate.getParam().getInteger("negateCount", 5);
        this.aP = effectTemplate.getParam().getString("negateStackTypes", "").split(";");
        this.Da = effectTemplate.getParam().getInteger("reApplyDelay", 0);
        fD = effectTemplate.getParam().getBool("reApplyOnDead", false);
    }

    @Override
    public void onStart() {
        ArrayList<Effect> arrayList = new ArrayList<Effect>();
        ArrayList<Effect> arrayList2 = new ArrayList<Effect>();
        for (Effect effect : this._effected.getEffectList().getAllEffects()) {
            if (this.fW.equals("cancellation")) {
                if (effect.isOffensive() || effect.getSkill().isToggle() || !effect.isCancelable()) continue;
                if (effect.getSkill().isMusic()) {
                    arrayList.add(effect);
                    continue;
                }
                arrayList2.add(effect);
                continue;
            }
            if (this.fW.equals("bane")) {
                if (!effect.isCancelable() || !ArrayUtils.contains((Object[])this.aP, (Object)effect.getStackType()) && !ArrayUtils.contains((Object[])this.aP, (Object)effect.getStackType2())) continue;
                arrayList2.add(effect);
                continue;
            }
            if (!this.fW.equals("cleanse") || !effect.isOffensive() || !effect.isCancelable()) continue;
            arrayList2.add(effect);
        }
        List list = new ArrayList();
        list.addAll(arrayList);
        list.addAll(arrayList2);
        if (list.isEmpty()) {
            return;
        }
        double d = this._effected.calcStat(Stats.CANCEL_RESIST, 0.0, null, null);
        Collections.shuffle(list);
        list = list.subList(0, Math.min(this.CZ, list.size()));
        LinkedHashSet<Skill> linkedHashSet = new LinkedHashSet<Skill>();
        LinkedHashMap<Skill, List<StopEffectRecord>> linkedHashMap = new LinkedHashMap<Skill, List<StopEffectRecord>>();
        for (Effect object : list) {
            int n;
            double d2 = object.getSkill().getMagicLevel();
            double d3 = (double)this.getSkill().getMagicLevel() - (d2 == 0.0 ? (double)this._effected.getLevel() : d2);
            double d4 = (2.0 * d3 + (double)this.CY + (double)((n = object.getTimeLeft()) / 120)) * (d = 1.0 - d * 0.01);
            if (!Rnd.chance(this.a(d4, this._effector.isPlayable()))) continue;
            linkedHashSet.add(object.getSkill());
        }
        for (Skill skill : linkedHashSet) {
            List<Effect> list2 = this._effected.getEffectList().getEffectsBySkill(skill);
            ArrayList<StopEffectRecord> arrayList3 = new ArrayList<StopEffectRecord>();
            if (list2 == null || list2.isEmpty()) continue;
            for (Effect effect : list2) {
                if (this.Da > 0) {
                    arrayList3.add(new StopEffectRecord(effect));
                }
                effect.exit();
            }
            if (!(arrayList3.isEmpty() || skill.isOffensive() || skill.isToggle() || skill.isTrigger())) {
                linkedHashMap.put(skill, arrayList3);
            }
            this._effected.sendPacket((IStaticPacket)new SystemMessage(SystemMsg.THE_EFFECT_OF_S1_HAS_BEEN_REMOVED).addSkillName(skill));
        }
        if (!linkedHashMap.isEmpty() && this._effected.isPlayer()) {
            ThreadPoolManager.getInstance().schedule(new ReApplyEffects(this._effected.getPlayer(), linkedHashMap), (long)this.Da * 1000L);
        }
    }

    private double a(double d, boolean bl) {
        switch (this.fW) {
            case "bane": {
                if (d < (double)Config.SKILLS_BANE_MOD_MIN) {
                    return Config.SKILLS_BANE_MOD_MIN;
                }
                if (!(d > (double)Config.SKILLS_BANE_MOD_MAX)) break;
                return Config.SKILLS_BANE_MOD_MAX;
            }
            case "cancellation": {
                return Math.max((double)Config.SKILLS_DISPEL_MOD_MIN, Math.min((double)Config.SKILLS_DISPEL_MOD_MAX, d));
            }
            case "cleanse": {
                return this.CY;
            }
        }
        return d;
    }

    @Override
    protected boolean onActionTime() {
        return false;
    }

    private static final class StopEffectRecord {
        private final EffectTemplate a;
        private final int Db;
        private final long dE;
        private final long dF;

        public StopEffectRecord(EffectTemplate effectTemplate, int n, long l, long l2) {
            this.a = effectTemplate;
            this.Db = n;
            this.dE = l;
            this.dF = l2;
        }

        public StopEffectRecord(Effect effect) {
            this(effect.getTemplate(), effect.getCount(), effect.getTime(), effect.getPeriod());
        }

        public void apply(Skill skill, Player player) {
            Env env = new Env(player, player, skill);
            Effect effect = this.a.getEffect(env);
            if (effect == null || effect.isOneTime()) {
                return;
            }
            effect.setCount(this.Db);
            effect.setPeriod(this.Db == 1 ? this.dF - this.dE : this.dF);
            player.getEffectList().addEffect(effect);
        }
    }

    private static final class ReApplyEffects
    extends RunnableImpl {
        private final HardReference<Player> aa;
        private final Map<Skill, List<StopEffectRecord>> bG;

        private ReApplyEffects(HardReference<Player> hardReference, Map<Skill, List<StopEffectRecord>> map) {
            this.aa = hardReference;
            this.bG = map;
        }

        public ReApplyEffects(Player player, Map<Skill, List<StopEffectRecord>> map) {
            this(player.getRef(), map);
        }

        @Override
        public void runImpl() throws Exception {
            Player player = this.aa.get();
            if (player == null || player.isLogoutStarted() || player.isOutOfControl() || !fD && (player.isDead() || player.isAlikeDead()) || player.isInDuel() || player.isOlyParticipant() || player.isFlying() || player.isSitting() || player.getTeam() != TeamType.NONE || player.isInStoreMode()) {
                return;
            }
            for (Map.Entry<Skill, List<StopEffectRecord>> entry : this.bG.entrySet()) {
                Skill skill = entry.getKey();
                List<StopEffectRecord> list = entry.getValue();
                for (StopEffectRecord stopEffectRecord : list) {
                    stopEffectRecord.apply(skill, player);
                }
            }
        }
    }
}
