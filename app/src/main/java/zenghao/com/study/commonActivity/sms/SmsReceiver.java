package zenghao.com.study.commonActivity.sms;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
 

class SmsReceiver extends BroadcastReceiver {
    private OnSmsCatchListener<String> callback;
    private String phoneNumberFilter;
    private String filter;
 
    /** 
     * Set result callback 
     * @param callback OnSmsCatchListener 
     */ 
    public void setCallback(OnSmsCatchListener<String> callback) {
        this.callback = callback;
    } 
 
    @Override 
    public void onReceive(Context context, Intent intent) {
        final Bundle bundle = intent.getExtras();
        try { 
            if (bundle != null) {
                final Object[] pdusObj = (Object[]) bundle.get("pdus");

                /*TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
                int type  = tm.getPhoneType();
                String format = (PHONE_TYPE_CDMA == type) ? SmsConstants.FORMAT_3GPP2 : SmsConstants.FORMAT_3GPP;*/

                String format = intent.getStringExtra("format");

                for (int i = 0; i < pdusObj.length; i++) {
                    SmsMessage currentMessage;
                    if(Build.VERSION.SDK_INT < 23){
                        currentMessage = SmsMessage.createFromPdu((byte[]) pdusObj[i]) ;
                    }else{
                        currentMessage = SmsMessage.createFromPdu((byte[]) pdusObj[i],format) ;
                    }
                    String phoneNumber = currentMessage.getDisplayOriginatingAddress();
 
                    if (phoneNumberFilter != null && !phoneNumber.equals(phoneNumberFilter)) {
                        return; 
                    } 
                    String message = currentMessage.getDisplayMessageBody();
                    if (filter != null && !message.matches(filter)) {
                        return; 
                    } 
 
                    if (callback != null) {
                        callback.onSmsCatch(message);
                    } 
                } // end for loop 
            } // bundle is null 
 
        } catch (Exception e) {
            Log.e("SmsReceiver", "Exception smsReceiver" + e);
        } 
    } 
 
    /** 
     * Set phone number filter 
     * 
     * @param phoneNumberFilter phone number 
     */ 
    public void setPhoneNumberFilter(String phoneNumberFilter) {
        this.phoneNumberFilter = phoneNumberFilter;
    } 
 
    /** 
     * set message filter with regexp 
     * 
     * @param regularExpression regexp 
     */ 
    public void setFilter(String regularExpression) {
        this.filter = regularExpression;
    } 
} 