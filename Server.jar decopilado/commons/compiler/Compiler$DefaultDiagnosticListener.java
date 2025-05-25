/*
 * Decompiled with CFR 0.152.
 */
package l2.commons.compiler;

import java.util.Locale;
import javax.tools.Diagnostic;
import javax.tools.DiagnosticListener;
import javax.tools.JavaFileObject;

private class Compiler.DefaultDiagnosticListener
implements DiagnosticListener<JavaFileObject> {
    private Compiler.DefaultDiagnosticListener() {
    }

    @Override
    public void report(Diagnostic<? extends JavaFileObject> diagnostic) {
        ac.error(diagnostic.getSource().getName() + (String)(diagnostic.getPosition() == -1L ? "" : ":" + diagnostic.getLineNumber() + "," + diagnostic.getColumnNumber()) + ": " + diagnostic.getMessage(Locale.getDefault()));
    }
}
