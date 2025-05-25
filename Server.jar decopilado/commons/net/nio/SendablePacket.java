/*
 * Decompiled with CFR 0.152.
 */
package l2.commons.net.nio;

import l2.commons.net.nio.AbstractPacket;

public abstract class SendablePacket<T>
extends AbstractPacket<T> {
    protected void writeC(int n) {
        this.getByteBuffer().put((byte)n);
    }

    protected void writeC(boolean bl) {
        this.writeC(bl ? 1 : 0);
    }

    protected void writeF(double d) {
        this.getByteBuffer().putDouble(d);
    }

    protected void writeE(double d) {
        this.getByteBuffer().putFloat((float)d);
    }

    protected void writeH(int n) {
        this.getByteBuffer().putShort((short)n);
    }

    protected void writeD(int n) {
        this.getByteBuffer().putInt(n);
    }

    protected void writeQ(long l) {
        this.getByteBuffer().putLong(l);
    }

    protected void writeB(byte[] byArray) {
        this.getByteBuffer().put(byArray);
    }

    protected void writeS(CharSequence charSequence) {
        if (charSequence != null) {
            int n = charSequence.length();
            for (int i = 0; i < n; ++i) {
                this.getByteBuffer().putChar(charSequence.charAt(i));
            }
        }
        this.getByteBuffer().putChar('\u0000');
    }

    protected void writeStringWithSize(CharSequence charSequence) {
        if (charSequence == null || charSequence.length() == 0) {
            this.writeH(0);
            return;
        }
        this.writeH(charSequence.length());
        for (int i = 0; i < charSequence.length(); ++i) {
            this.writeH(charSequence.charAt(i));
        }
    }

    protected abstract boolean write();
}
