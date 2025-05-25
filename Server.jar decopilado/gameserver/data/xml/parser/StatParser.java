/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.data.xml.parser;

import l2.commons.data.xml.AbstractDirParser;
import l2.commons.data.xml.AbstractHolder;
import l2.gameserver.data.xml.parser.StatProcessor;

public abstract class StatParser<H extends AbstractHolder>
extends AbstractDirParser<H> {
    private final StatProcessor b = new StatProcessor(){

        @Override
        protected String getSourceName() {
            return StatParser.this.getCurrentFileName();
        }
    };

    protected StatParser(H h) {
        super(h);
    }

    protected StatProcessor getStatProcessor() {
        return this.b;
    }
}
