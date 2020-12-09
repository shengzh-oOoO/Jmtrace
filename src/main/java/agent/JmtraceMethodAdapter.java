package agent;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

public class JmtraceMethodAdapter extends MethodVisitor {
    private boolean verbose = false;
    public JmtraceMethodAdapter(MethodVisitor methodVisitor, boolean _verbose) {
        super(Opcodes.ASM7, methodVisitor);
        verbose = _verbose;
    }
    private static final int[] xaload = {Opcodes.AALOAD, Opcodes.IALOAD, Opcodes.LALOAD, Opcodes.FALOAD, Opcodes.DALOAD, Opcodes.BALOAD, Opcodes.CALOAD, Opcodes.SALOAD};
    private boolean isXALOAD(int opcode){
        for (int i = 0; i < xaload.length; i++){
            if(opcode == xaload[i]){
                return true;
            }
        }
        return false;
    }

    private static final int[] xastore = {Opcodes.AASTORE, Opcodes.IASTORE, Opcodes.LASTORE, Opcodes.FASTORE, Opcodes.DASTORE, Opcodes.BASTORE, Opcodes.CASTORE, Opcodes.SASTORE};
    private boolean isXASTORE(int opcode){
        for (int i = 0; i < xastore.length; i++){
            if(opcode == xastore[i]){
                return true;
            }
        }
        return false;
    }

    @Override
    public void visitInsn(int opcode){
        if (isXALOAD(opcode)){
            super.visitInsn(Opcodes.DUP2);
            super.visitMethodInsn(Opcodes.INVOKESTATIC, Type.getInternalName(MyPrint.class), "PrintXALOAD", "(Ljava/lang/Object;I)V", false);
        }
        else if(isXASTORE(opcode)){
            if (opcode == Opcodes.LASTORE || opcode == Opcodes.DASTORE){
                super.visitInsn(Opcodes.DUP2_X2);
                super.visitInsn(Opcodes.POP2);

                super.visitInsn(Opcodes.DUP2);
                super.visitMethodInsn(Opcodes.INVOKESTATIC, Type.getInternalName(MyPrint.class), "PrintXASTORE", "(Ljava/lang/Object;I)V", false);
                
                super.visitInsn(Opcodes.DUP2_X2);
                super.visitInsn(Opcodes.POP2);
            }
            else{
                super.visitInsn(Opcodes.DUP_X2);
                super.visitInsn(Opcodes.POP);

                super.visitInsn(Opcodes.DUP2);
                super.visitMethodInsn(Opcodes.INVOKESTATIC, Type.getInternalName(MyPrint.class), "PrintXASTORE", "(Ljava/lang/Object;I)V", false);

                super.visitInsn(Opcodes.DUP2_X1);
                super.visitInsn(Opcodes.POP2);
            }
            
        }

        super.visitInsn(opcode);
    }

    @Override
    public void visitFieldInsn(int opcode, String owner, String name, String descriptor) {
        switch (opcode){
        	case Opcodes.GETSTATIC:
                super.visitLdcInsn(owner);
                super.visitLdcInsn(name);
                super.visitMethodInsn(Opcodes.INVOKESTATIC, Type.getInternalName(MyPrint.class), "PrintGETSTATIC", "(Ljava/lang/String;Ljava/lang/String;)V", false);
                break;
            case Opcodes.PUTSTATIC:
                super.visitLdcInsn(owner);
                super.visitLdcInsn(name);
                super.visitMethodInsn(Opcodes.INVOKESTATIC, Type.getInternalName(MyPrint.class), "PrintPUTSTATIC", "(Ljava/lang/String;Ljava/lang/String;)V", false);
                break;

            case Opcodes.GETFIELD:
                super.visitInsn(Opcodes.DUP);
                super.visitLdcInsn(name);
                super.visitMethodInsn(Opcodes.INVOKESTATIC, Type.getInternalName(MyPrint.class), "PrintGETFIELD", "(Ljava/lang/Object;Ljava/lang/String;)V", false);
                break;
            case Opcodes.PUTFIELD:
                if (descriptor.equals("J") || descriptor.equals("D")){
                    super.visitInsn(Opcodes.DUP2_X1);
                    super.visitInsn(Opcodes.POP2);

                    super.visitInsn(Opcodes.DUP);
                    super.visitLdcInsn(name);
                    super.visitMethodInsn(Opcodes.INVOKESTATIC, Type.getInternalName(MyPrint.class), "PrintPUTFIELD", "(Ljava/lang/Object;Ljava/lang/String;)V", false);
                    
                    super.visitInsn(Opcodes.DUP_X2);
                    super.visitInsn(Opcodes.POP);
                }
                else{
                    super.visitInsn(Opcodes.SWAP);

                    super.visitInsn(Opcodes.DUP);
                    super.visitLdcInsn(name);
                    super.visitMethodInsn(Opcodes.INVOKESTATIC, Type.getInternalName(MyPrint.class), "PrintPUTFIELD", "(Ljava/lang/Object;Ljava/lang/String;)V", false);

                    super.visitInsn(Opcodes.SWAP);
                }
                break;
            default:
        }
        super.visitFieldInsn(opcode, owner, name, descriptor);
    }
}
