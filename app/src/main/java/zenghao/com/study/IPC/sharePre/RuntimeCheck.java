package zenghao.com.study.IPC.sharePre;

/**
 * Created by cyhan on 5/31/16.
 */
public class RuntimeCheck {

    public static String SERVICE_PROCESSNAME = ":uploadspot";

    private static Thread  s_mainThread			    = null;
    private static boolean s_bIsUiProcess		    = false;
    private static boolean s_bIsServiceProcess	    = false;

    public static void Init(String procName){
        s_mainThread = Thread.currentThread();
        if ( procName.contains(SERVICE_PROCESSNAME) ){
            s_bIsServiceProcess = true;
        }
        else {
            s_bIsUiProcess = true;
        }

    }

    public static void SetServiceProcess(){
        s_bIsUiProcess = false;
        s_bIsServiceProcess = true;
    }


    public static void CheckUiProcess(){
        if ( !s_bIsUiProcess ){
            throw new RuntimeException("Must run in UI Process");
        }
    }

    public static void CheckServiceProcess(){
        if ( !s_bIsServiceProcess ){
            throw new RuntimeException("Must run in Service Process");
        }
    }


    public static boolean IsUIProcess(){
        return s_bIsUiProcess;
    }

    public static boolean IsServiceProcess(){
        return s_bIsServiceProcess;
    }

}
