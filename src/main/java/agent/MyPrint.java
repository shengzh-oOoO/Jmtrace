package agent;

public class MyPrint {
    private static boolean verbose = false;
    public static void PrintXALOAD(Object obj, int index) {
        if (verbose){
            System.out.println("XALOAD:");
        }
        String tid = Long.toString(Thread.currentThread().getId());
        String hash = Long.toHexString(System.identityHashCode(obj));
        String arraytype = obj.getClass().getComponentType().getCanonicalName();
        System.out.println("R "+tid+" "+hash+" "+arraytype+"["+Integer.toString(index)+"]");
    }
    public static void PrintXASTORE(Object obj, int index) {
        if (verbose){
            System.out.println("XASTORE:");
        }
        String tid = Long.toString(Thread.currentThread().getId());
        String hash = Long.toHexString(System.identityHashCode(obj));
        String arraytype = obj.getClass().getComponentType().getCanonicalName();
        System.out.println("W "+tid+" "+hash+" "+arraytype+"["+Integer.toString(index)+"]");
    }

    public static void PrintGETFIELD(Object obj, String fieldname) {
        if (verbose){
            System.out.println("GETFIELD:");
        }
        String tid = Long.toString(Thread.currentThread().getId());
        String hash = Long.toHexString(System.identityHashCode(obj));
        String classname = obj.getClass().getCanonicalName();
        System.out.println("R "+tid+" "+hash+" "+classname+"."+fieldname);
    }
    public static void PrintPUTFIELD(Object obj, String fieldname) {
        if (verbose){
            System.out.println("PUTFIELD:");
        }
        String tid = Long.toString(Thread.currentThread().getId());
        String hash = Long.toHexString(System.identityHashCode(obj));
        String classname = obj.getClass().getCanonicalName();
        System.out.println("W "+tid+" "+hash+" "+classname+"."+fieldname);
    }

    public static void PrintGETSTATIC(String classname, String fieldname) {
        if (verbose){
            System.out.println("GETSTATIC:");
        }
        classname = classname.replace("/", ".");
        Object classObj = null;
        try {
            classObj = Class.forName(classname);
        } catch (ClassNotFoundException exception) {
            System.out.println("Exception! location: PrintGETSTATIC!");
        }
        String tid = Long.toString(Thread.currentThread().getId());
        String hash = Long.toHexString(System.identityHashCode(classObj));
        System.out.println("R "+tid+" "+hash+" "+classname+"."+fieldname);

    }
    public static void PrintPUTSTATIC(String classname, String fieldname) {
        if (verbose){
            System.out.println("PUTSTATIC:");
        }
        classname = classname.replace("/", ".");
        Object classObj = null;
        try {
            classObj = Class.forName(classname);
        } catch (ClassNotFoundException exception) {
            System.out.println("Exception! location: PrintPUTSTATIC!");
        }
        String tid = Long.toString(Thread.currentThread().getId());
        String hash = Long.toHexString(System.identityHashCode(classObj));
        System.out.println("W "+tid+" "+hash+" "+classname+"."+fieldname);
    }
}
