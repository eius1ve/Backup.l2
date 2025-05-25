/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.eclipse.jdt.internal.compiler.tool.EclipseCompiler
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package l2.commons.compiler;

import java.io.File;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Locale;
import javax.tools.Diagnostic;
import javax.tools.DiagnosticListener;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import l2.commons.compiler.MemoryClassLoader;
import l2.commons.compiler.MemoryJavaFileManager;
import org.eclipse.jdt.internal.compiler.tool.EclipseCompiler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class Compiler {
    private static final Logger ac = LoggerFactory.getLogger(Compiler.class);
    private static final JavaCompiler a = new EclipseCompiler();
    private final DiagnosticListener<JavaFileObject> a;
    private final StandardJavaFileManager a;
    private final MemoryClassLoader a;
    private final MemoryJavaFileManager a = new MemoryJavaFileManager(this.a, this.a);

    public boolean compile(File ... fileArray) {
        ArrayList<String> arrayList = new ArrayList<String>();
        arrayList.add("-Xlint:all");
        arrayList.add("-warn:none");
        arrayList.add("-g");
        StringWriter stringWriter = new StringWriter();
        JavaCompiler.CompilationTask compilationTask = a.getTask(stringWriter, this.a, (DiagnosticListener<? super JavaFileObject>)((Object)this.a), arrayList, null, this.a.getJavaFileObjects(fileArray));
        return compilationTask.call() != false;
    }

    public boolean compile(Collection<File> collection) {
        return this.compile(collection.toArray(new File[collection.size()]));
    }

    public MemoryClassLoader getClassLoader() {
        return this.a;
    }

    private class DefaultDiagnosticListener
    implements DiagnosticListener<JavaFileObject> {
        private DefaultDiagnosticListener() {
        }

        @Override
        public void report(Diagnostic<? extends JavaFileObject> diagnostic) {
            ac.error(diagnostic.getSource().getName() + (String)(diagnostic.getPosition() == -1L ? "" : ":" + diagnostic.getLineNumber() + "," + diagnostic.getColumnNumber()) + ": " + diagnostic.getMessage(Locale.getDefault()));
        }
    }
}
