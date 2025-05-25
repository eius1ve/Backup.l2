/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.data.xml.holder;

import java.util.HashMap;
import java.util.Map;
import l2.commons.data.xml.AbstractHolder;
import l2.gameserver.model.base.ClassId;
import l2.gameserver.templates.PlayerTemplate;

public class CharacterTemplateHolder
extends AbstractHolder {
    private static final CharacterTemplateHolder a = new CharacterTemplateHolder();
    private final Map<ClassId, PlayerTemplate> H = new HashMap<ClassId, PlayerTemplate>();
    private final Map<ClassId, PlayerTemplate> I = new HashMap<ClassId, PlayerTemplate>();

    public static final CharacterTemplateHolder getInstance() {
        return a;
    }

    public void addTemplate(PlayerTemplate playerTemplate, boolean bl) {
        if (bl) {
            this.H.put(playerTemplate.classId, playerTemplate);
        } else {
            this.I.put(playerTemplate.classId, playerTemplate);
        }
    }

    public PlayerTemplate getTemplate(ClassId classId, boolean bl) {
        return bl ? this.H.get((Object)classId) : this.I.get((Object)classId);
    }

    @Override
    public int size() {
        return this.H.size() + this.I.size();
    }

    @Override
    public void clear() {
        this.H.clear();
        this.I.clear();
    }
}
