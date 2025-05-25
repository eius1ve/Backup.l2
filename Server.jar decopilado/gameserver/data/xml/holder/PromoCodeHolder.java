/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  gnu.trove.TObjectIntHashMap
 */
package l2.gameserver.data.xml.holder;

import gnu.trove.TObjectIntHashMap;
import java.util.HashMap;
import java.util.Map;
import l2.commons.data.xml.AbstractHolder;
import l2.gameserver.dao.PromoCodeDAO;
import l2.gameserver.dao.PromoCodeLimitDAO;
import l2.gameserver.dao.PromoCodeUserDAO;
import l2.gameserver.model.Player;
import l2.gameserver.model.PromoCode;
import l2.gameserver.model.promoCode.PromoCodeReward;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class PromoCodeHolder
extends AbstractHolder {
    private final Map<String, PromoCode> X = new HashMap<String, PromoCode>();
    private final TObjectIntHashMap<String> a = PromoCodeLimitDAO.INSTANCE.select();
    private static final PromoCodeHolder a = new PromoCodeHolder();

    public static PromoCodeHolder getInstance() {
        return a;
    }

    public void addPromoCode(String string, PromoCode promoCode) {
        this.X.put(string, promoCode);
    }

    public PromoCode getPromoCode(String string) {
        return this.X.get(string);
    }

    public ActivateResult tryActivate(Player player, String string) {
        return this.tryActivate(player, this.getPromoCode(string));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public ActivateResult tryActivate(Player player, PromoCode promoCode) {
        if (promoCode == null) {
            return ActivateResult.INVALID_TRY_LATER;
        }
        String string = player.getAccountName();
        String string2 = player.getNetConnection().getHwid();
        String string3 = player.getNetConnection().getIpAddr();
        int n = player.getLevel();
        String string4 = promoCode.getName();
        if (PromoCodeDAO.INSTANCE.isActivated(string, string4)) {
            return ActivateResult.ALREADY;
        }
        if (promoCode.isLimitByHWID() && PromoCodeDAO.INSTANCE.isHwidActivated(string2, string4)) {
            return ActivateResult.HWID_LIMITED;
        }
        if (promoCode.isLimitByIP() && PromoCodeDAO.INSTANCE.isIpActivated(string3, string4)) {
            return ActivateResult.IP_LIMITED;
        }
        if (promoCode.getMinLevel() > 0 && n < promoCode.getMinLevel()) {
            return ActivateResult.MIN_LEVEL_LIMITED;
        }
        if (promoCode.getMaxLevel() > 0 && n > promoCode.getMaxLevel()) {
            return ActivateResult.MAX_LEVEL_LIMITED;
        }
        if (promoCode.getLimit() > 0) {
            TObjectIntHashMap<String> tObjectIntHashMap = this.a;
            synchronized (tObjectIntHashMap) {
                int n2 = this.a.get((Object)string4);
                if (n2 >= promoCode.getLimit()) {
                    return ActivateResult.OUT_OF_LIMIT;
                }
                this.a.put((Object)string4, ++n2);
                PromoCodeLimitDAO.INSTANCE.insert(string4, n2);
            }
        }
        long l = System.currentTimeMillis();
        if (promoCode.getFromDate() > 0L && l < promoCode.getFromDate()) {
            return ActivateResult.OUT_OF_DATE;
        }
        if (promoCode.getToDate() > 0L && l > promoCode.getToDate()) {
            return ActivateResult.OUT_OF_DATE;
        }
        if (promoCode.isLimitByUser() && !PromoCodeUserDAO.INSTANCE.isContains(string, string4)) {
            return ActivateResult.NOT_FOR_YOU;
        }
        for (PromoCodeReward promoCodeReward : promoCode.getRewards()) {
            if (promoCodeReward.validate()) continue;
            return ActivateResult.INVALID_TRY_LATER;
        }
        for (PromoCodeReward promoCodeReward : promoCode.getRewards()) {
            promoCodeReward.giveReward(player);
        }
        PromoCodeDAO.INSTANCE.insert(string, string4);
        if (promoCode.isLimitByIP()) {
            PromoCodeDAO.INSTANCE.insertIpLimit(string3, string4);
        }
        if (promoCode.isLimitByHWID()) {
            PromoCodeDAO.INSTANCE.insertHwidLimit(string2, string4);
        }
        return ActivateResult.OK;
    }

    @Override
    public int size() {
        return this.X.size();
    }

    @Override
    public void clear() {
        this.X.clear();
    }

    public static final class ActivateResult
    extends Enum<ActivateResult> {
        public static final /* enum */ ActivateResult ALREADY = new ActivateResult();
        public static final /* enum */ ActivateResult OUT_OF_DATE = new ActivateResult();
        public static final /* enum */ ActivateResult OUT_OF_LIMIT = new ActivateResult();
        public static final /* enum */ ActivateResult NOT_FOR_YOU = new ActivateResult();
        public static final /* enum */ ActivateResult HWID_LIMITED = new ActivateResult();
        public static final /* enum */ ActivateResult IP_LIMITED = new ActivateResult();
        public static final /* enum */ ActivateResult MIN_LEVEL_LIMITED = new ActivateResult();
        public static final /* enum */ ActivateResult MAX_LEVEL_LIMITED = new ActivateResult();
        public static final /* enum */ ActivateResult INVALID_TRY_LATER = new ActivateResult();
        public static final /* enum */ ActivateResult OK = new ActivateResult();
        private static final /* synthetic */ ActivateResult[] a;

        public static ActivateResult[] values() {
            return (ActivateResult[])a.clone();
        }

        public static ActivateResult valueOf(String string) {
            return Enum.valueOf(ActivateResult.class, string);
        }

        private static /* synthetic */ ActivateResult[] a() {
            return new ActivateResult[]{ALREADY, OUT_OF_DATE, OUT_OF_LIMIT, NOT_FOR_YOU, HWID_LIMITED, IP_LIMITED, MIN_LEVEL_LIMITED, MAX_LEVEL_LIMITED, INVALID_TRY_LATER, OK};
        }

        static {
            a = ActivateResult.a();
        }
    }
}
