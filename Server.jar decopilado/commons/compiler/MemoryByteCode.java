/*
 * Decompiled with CFR 0.152.
 */
package l2.commons.compiler;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.net.URI;
import javax.tools.JavaFileObject;
import javax.tools.SimpleJavaFileObject;

public class MemoryByteCode
extends SimpleJavaFileObject {
    private ByteArrayOutputStream a;
    private final String aK;

    public MemoryByteCode(String string, URI uRI) {
        super(uRI, JavaFileObject.Kind.CLASS);
        this.aK = string;
    }

    @Override
    public OutputStream openOutputStream() {
        this.a = new ByteArrayOutputStream();
        return this.a;
    }

    public byte[] getBytes() {
        return this.a.toByteArray();
    }

    @Override
    public String getName() {
        return this.aK;
    }
}
