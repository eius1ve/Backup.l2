/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model.mail;

import java.util.HashSet;
import java.util.Set;
import l2.commons.dao.JdbcEntity;
import l2.commons.dao.JdbcEntityState;
import l2.gameserver.dao.MailDAO;
import l2.gameserver.model.items.ItemInstance;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class Mail
implements Comparable<Mail>,
JdbcEntity {
    private static final long cD = -8704970972611917153L;
    public static final int DELETED = 0;
    public static final int READED = 1;
    public static final int REJECTED = 2;
    private static final MailDAO b = MailDAO.getInstance();
    private int messageId;
    private int oJ;
    private String dE;
    private int oK;
    private String dF;
    private int oL;
    private String dG;
    private String dH;
    private long cE;
    private SenderType a;
    private boolean dH;
    private Set<ItemInstance> y;
    private JdbcEntityState a = SenderType.NORMAL;

    public Mail() {
        this.y = new HashSet<ItemInstance>();
        this.a = JdbcEntityState.CREATED;
    }

    public int getMessageId() {
        return this.messageId;
    }

    public void setMessageId(int n) {
        this.messageId = n;
    }

    public int getSenderId() {
        return this.oJ;
    }

    public void setSenderId(int n) {
        this.oJ = n;
    }

    public String getSenderName() {
        return this.dE;
    }

    public void setSenderName(String string) {
        this.dE = string;
    }

    public int getReceiverId() {
        return this.oK;
    }

    public void setReceiverId(int n) {
        this.oK = n;
    }

    public String getReceiverName() {
        return this.dF;
    }

    public void setReceiverName(String string) {
        this.dF = string;
    }

    public int getExpireTime() {
        return this.oL;
    }

    public void setExpireTime(int n) {
        this.oL = n;
    }

    public String getTopic() {
        return this.dG;
    }

    public void setTopic(String string) {
        this.dG = string;
    }

    public String getBody() {
        return this.dH;
    }

    public void setBody(String string) {
        this.dH = string;
    }

    public boolean isPayOnDelivery() {
        return this.cE > 0L;
    }

    public long getPrice() {
        return this.cE;
    }

    public void setPrice(long l) {
        this.cE = l;
    }

    public boolean isUnread() {
        return this.dH;
    }

    public void setUnread(boolean bl) {
        this.dH = bl;
    }

    public Set<ItemInstance> getAttachments() {
        return this.y;
    }

    public void addAttachment(ItemInstance itemInstance) {
        this.y.add(itemInstance);
    }

    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (object == null) {
            return false;
        }
        if (object.getClass() != this.getClass()) {
            return false;
        }
        return ((Mail)object).getMessageId() == this.getMessageId();
    }

    @Override
    public void setJdbcState(JdbcEntityState jdbcEntityState) {
        this.a = jdbcEntityState;
    }

    @Override
    public JdbcEntityState getJdbcState() {
        return this.a;
    }

    @Override
    public void save() {
        b.save(this);
    }

    @Override
    public void update() {
        b.update(this);
    }

    @Override
    public void delete() {
        b.delete(this);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public Mail reject() {
        Mail mail = new Mail();
        mail.setSenderId(this.getReceiverId());
        mail.setSenderName(this.getReceiverName());
        mail.setReceiverId(this.getSenderId());
        mail.setReceiverName(this.getSenderName());
        mail.setTopic(this.getTopic());
        mail.setBody(this.getBody());
        Set<ItemInstance> set = this.getAttachments();
        synchronized (set) {
            for (ItemInstance itemInstance : this.getAttachments()) {
                mail.addAttachment(itemInstance);
            }
            this.getAttachments().clear();
        }
        mail.setType(SenderType.NEWS_INFORMER);
        mail.setUnread(true);
        return mail;
    }

    public Mail reply() {
        Mail mail = new Mail();
        mail.setSenderId(this.getReceiverId());
        mail.setSenderName(this.getReceiverName());
        mail.setReceiverId(this.getSenderId());
        mail.setReceiverName(this.getSenderName());
        mail.setTopic("[Re]" + this.getTopic());
        mail.setBody(this.getBody());
        mail.setType(SenderType.NEWS_INFORMER);
        mail.setUnread(true);
        return mail;
    }

    @Override
    public int compareTo(Mail mail) {
        return mail.getMessageId() - this.getMessageId();
    }

    public SenderType getType() {
        return this.a;
    }

    public void setType(SenderType senderType) {
        this.a = senderType;
    }

    public static final class SenderType
    extends Enum<SenderType> {
        public static final /* enum */ SenderType NORMAL = new SenderType();
        public static final /* enum */ SenderType NEWS_INFORMER = new SenderType();
        public static final /* enum */ SenderType NONE = new SenderType();
        public static final /* enum */ SenderType BIRTHDAY = new SenderType();
        public static SenderType[] VALUES;
        private static final /* synthetic */ SenderType[] a;

        public static SenderType[] values() {
            return (SenderType[])a.clone();
        }

        public static SenderType valueOf(String string) {
            return Enum.valueOf(SenderType.class, string);
        }

        private static /* synthetic */ SenderType[] a() {
            return new SenderType[]{NORMAL, NEWS_INFORMER, NONE, BIRTHDAY};
        }

        static {
            a = SenderType.a();
            VALUES = SenderType.values();
        }
    }
}
