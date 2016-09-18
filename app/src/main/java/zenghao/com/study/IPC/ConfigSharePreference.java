package zenghao.com.study.IPC;


/**
 * Created by cyhan on 5/31/16.
 */
public class ConfigSharePreference {

    public static final String PREFS_NAME = "config_preference";

    public PreferencesWriter getmPreferencesWriter() {
        return mPreferencesWriter;
    }

    private PreferencesWriter mPreferencesWriter;
    private ConfigSharePreference(){
        mPreferencesWriter = new PreferencesWriter() {
            @Override
            protected String initFileName() {
                return PREFS_NAME;
            }

            @Override
            protected void initPreferenceChanges(int preferVersion) {

            }
        };
    }
    private static ConfigSharePreference mInstance;
    public static ConfigSharePreference getInstance(){

        if(mInstance == null){
            synchronized (ConfigSharePreference.class) {
                if(mInstance == null) {
                    mInstance = new ConfigSharePreference();
                }
            }
        }

        return mInstance;
    }


    public int getIntValue(String key,int defualt){
        if(RuntimeCheck.IsUIProcess()){
            return getInstance().getmPreferencesWriter().getInt(key,defualt);
        }else{
            return ConfigProvider.getIntValue(key,defualt);
        }
    }

    public long getLongValue(String key,long defualt){
        if(RuntimeCheck.IsUIProcess()){
            return getInstance().getmPreferencesWriter().getLong(key,defualt);
        }else{
            return ConfigProvider.getLongValue(key,defualt);
        }
    }
    public boolean getBooleanValue(String key,boolean defualt){
        if(RuntimeCheck.IsUIProcess()){
            return getInstance().getmPreferencesWriter().getBoolean(key,defualt);
        }else{
            return ConfigProvider.getBooleanValue(key,defualt);
        }
    }
    public String getStringValue(String key,String defualt){
        if(RuntimeCheck.IsUIProcess()){
            return getInstance().getmPreferencesWriter().getString(key,defualt);
        }else{
            return ConfigProvider.getStringValue(key,defualt);
        }
    }

    public void setIntValue(String key,int value){
        if(RuntimeCheck.IsUIProcess()) {
            getInstance().getmPreferencesWriter().updateValue(key, value);
        }else{
            ConfigProvider.setIntValue(key,value);
        }
    }

    public void setLongValue(String key,long value){
        if(RuntimeCheck.IsUIProcess()) {
            getInstance().getmPreferencesWriter().updateValue(key, value);
        }else{
            ConfigProvider.setLongValue(key,value);
        }
    }

    public void setBooleanValue(String key,boolean value){
        if(RuntimeCheck.IsUIProcess()) {
            getInstance().getmPreferencesWriter().updateValue(key, value);
        }else{
            ConfigProvider.setBooleanValue(key,value);
        }
    }

    public void setStringValue(String key,String value){
        if(RuntimeCheck.IsUIProcess()) {
            getInstance().getmPreferencesWriter().updateValue(key, value);
        }else{
            ConfigProvider.setStringValue(key,value);
        }
    }

    public static String KEY_USER_TOKEN = "user_token";
    public String getCookie(){
        return getStringValue(KEY_USER_TOKEN,"");
    }

    public void setCookie(String cookie){
        setStringValue(KEY_USER_TOKEN,cookie);
    }

}
