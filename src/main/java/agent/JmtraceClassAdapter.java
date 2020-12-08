package agent;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class JmtraceClassAdapter extends ClassVisitor {
    private boolean verbose = false;
    public JmtraceClassAdapter(ClassVisitor classVisitor, boolean _verbose) {
        super(Opcodes.ASM7, classVisitor);
        verbose = _verbose;
    }
    @Override
    public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions){
        MethodVisitor mv = super.visitMethod(access, name, descriptor, signature, exceptions);
        return new JmtraceMethodAdapter(mv, verbose);
    }
}
