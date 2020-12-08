package agent;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;
import java.lang.instrument.IllegalClassFormatException;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;


public class JmtraceAgent {
    private static boolean verbose = false;
    public static void premain(String agentArgs, Instrumentation inst) {
        if (agentArgs==null){
            verbose = false;
        }
        else if (agentArgs.equals(new String("verbose"))){
            verbose = true;
            System.out.println("verbose");
        }
        else{
            System.out.println("illegal agentArgs!");
            System.exit(0);
        }

        inst.addTransformer(new ClassFileTransformer() {
            @Override
            public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
                
                if (className.startsWith("java") ||className.startsWith("jdk") ||className.startsWith("sun")){
                    return classfileBuffer;
                }
                if (verbose == true){
                    System.out.println("transform Class:\t" + className);
                }
                ClassReader creader = new ClassReader(classfileBuffer);
                ClassWriter cwriter = new ClassWriter(creader, ClassWriter.COMPUTE_FRAMES);
                JmtraceClassAdapter adapter = new JmtraceClassAdapter(cwriter, verbose);
                creader.accept(adapter, ClassWriter.COMPUTE_FRAMES);
                return cwriter.toByteArray();
            }
        },false);
    }
}
