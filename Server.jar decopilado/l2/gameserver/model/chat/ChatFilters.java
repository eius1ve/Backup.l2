/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.lang3.ArrayUtils
 */
package l2.gameserver.model.chat;

import l2.commons.data.xml.AbstractHolder;
import l2.gameserver.model.chat.chatfilter.ChatFilter;
import org.apache.commons.lang3.ArrayUtils;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class ChatFilters
extends AbstractHolder {
    private static final ChatFilters a = new ChatFilters();
    private ChatFilter[] a = new ChatFilter[0];

    public static final ChatFilters getinstance() {
        return a;
    }

    private ChatFilters() {
    }

    public ChatFilter[] getFilters() {
        return this.a;
    }

    public void add(ChatFilter chatFilter) {
        this.a = (ChatFilter[])ArrayUtils.add((Object[])this.a, (Object)chatFilter);
    }

    @Override
    public void log() {
        this.info(String.format("loaded %d filter(s).", this.size()));
    }

    @Override
    public int size() {
        return this.a.length;
    }

    @Override
    public void clear() {
        this.a = new ChatFilter[0];
    }
}
