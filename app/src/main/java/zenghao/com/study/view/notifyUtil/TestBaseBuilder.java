package zenghao.com.study.view.notifyUtil;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;
import android.widget.Button;

/**
 * TODO
 *
 * @author zenghao
 * @since 17/2/14 下午5:44
 */
public class TestBaseBuilder {


    protected NotificationManager mManager;

    protected NotificationCompat.Builder mBuilder;
    private TestBaseBuilder(){};

    private TestBaseBuilder(Context context,Builder builder){
        mManager = (NotificationManager) context.getSystemService(Activity.NOTIFICATION_SERVICE);
        mBuilder = new NotificationCompat.Builder(context);

        /**通知的各种属性设置*/

        mBuilder.setContentIntent(builder.intent);
        if(builder.mSmallIcon>0){
            mBuilder.setSmallIcon(builder.mSmallIcon);
        }

        if(builder.bigIcon>0){
            mBuilder.setLargeIcon(BitmapFactory.decodeResource(context.getResources(),builder.bigIcon));
        }

        if(builder.largIcon!=null){
            mBuilder.setLargeIcon(builder.largIcon);
        }

        mBuilder.setTicker(builder.mTicker);
        mBuilder.setContentTitle(builder.mContentTitle);

        if(!TextUtils.isEmpty(builder.mContentText)){
            mBuilder.setContentText(builder.mContentText);
        }

        /*
         * 将AutoCancel设为true后，当你点击通知栏的notification后，它会自动被取消消失,
		 * 不设置的话点击消息后也不清除，但可以滑动删除
		 */
        mBuilder.setAutoCancel(true);

        // 将Ongoing设为true 那么notification将不能滑动删除
        // notifyBuilder.setOngoing(true);

        mBuilder.setPriority(builder.priority);

        if(builder.sound){
            builder.setTipDefaults(builder.tipDefaults | Notification.DEFAULT_SOUND);
        }

        if(builder.vibrate){
            builder.setTipDefaults(builder.tipDefaults | Notification.DEFAULT_VIBRATE);
        }

        if(builder.lights){
            builder.setTipDefaults(builder.tipDefaults | Notification.DEFAULT_LIGHTS);
        }

        mBuilder.setDefaults(builder.tipDefaults);


        if(builder.headup){
            mBuilder.setPriority(NotificationCompat.PRIORITY_MAX);
            mBuilder.setDefaults(NotificationCompat.DEFAULT_ALL);
        }

        if(builder.when>0){
            mBuilder.setWhen(builder.when);
        }

        mBuilder.setOngoing(builder.onGoing);
        mBuilder.setProgress(builder.max_progress,builder.progress,false);
        if(builder.mStyle!=null){
            mBuilder.setStyle(builder.mStyle);
        }


        //mBuilder.setVisibility()


        mManager.notify(builder.id,mBuilder.build());
    }






    public static class Builder{
        private Context mContext;

        private int mSmallIcon;
        private CharSequence mContentTitle;
        private CharSequence mContentText;

        private boolean headup;
        private CharSequence summaryText;

        private int id;
        private int bigIcon;
        private Bitmap largIcon;

        private CharSequence mTicker = "您有新的消息";
        private  CharSequence mSubText;

        private int notiflag = NotificationCompat.FLAG_AUTO_CANCEL;
        private int priority = NotificationCompat.PRIORITY_DEFAULT;



        private int tipDefaults = NotificationCompat.DEFAULT_LIGHTS;//默认只有走马灯提醒
        private   boolean sound = true;
        private boolean vibrate = true;
        private boolean lights = true;

        private PendingIntent intent;
        private long when;
        private boolean onGoing;
        private int max_progress;
        private int progress;
        private NotificationCompat.Style mStyle;

        public Builder(Context context){
                this.mContext = context;
        }

        public Builder setSmallIcon(int resId){
                this.mSmallIcon = resId;
            return this;
        }

        public Builder setContentTitle(String txt){
                this.mContentTitle = txt;
            return this;
        }

        public Builder setContentText(String txt){
                this.mContentText = txt;
            return this;
        }

        public Builder setHeadup(boolean flag){
                this.headup = false;
            return this;
        }

        public Builder setSummaryText(String txt){
                this.summaryText = txt;
            return this;
        }

        public Builder setId(int id){
                this.id = id;
            return this;
        }

        public Builder setBigIcon(int resId){
                this.bigIcon = resId;
            return this;
        }

        public Builder setTicker(String txt){
            this.mTicker = txt;
            return this;
        }

        public Builder setSubText(String txt){
            this.mSubText = txt;
            return this;
        }

        public Builder setNotiFlag(int flag){
                this.notiflag = flag;
            return this;
        }

        public Builder setPriority(int priority){
            this.priority = priority;
            return this;
        }

        public Builder setTipDefaults(int tipDefaults){
                this.tipDefaults = tipDefaults;
            return this;
        }

        public Builder setSound(boolean isSound){
            this.sound = isSound;
            return this;
        }

        public Builder setVibrate(boolean isVibrate){
            this.vibrate = isVibrate;
            return this;
        }

        public Builder setLight(boolean isLight){
            this.lights = isLight;
            return this;
        }

        public Builder setPendingIntent(PendingIntent intent){
                this.intent = intent;
            return this;
        }

        public Builder setWhen(long when){
                this.when = when;
            return this;
        }
        public Builder setLargIcon(Bitmap bitmap){
                this.largIcon = bitmap;
            return this;
        }
        public Builder setOnGoing(boolean onGoing){
            this.onGoing = onGoing;
            return this;
        }

        public Builder setMaxProgress(int max){
            this.max_progress = max;
            return this;
        }
        public Builder setProgress(int progress){
            this.progress = progress;
            return this;
        }

        public Builder setStyle(NotificationCompat.Style style){
                this.mStyle = style;
            return this;
        }
        public void build(){
            new TestBaseBuilder(mContext,this);
        }

    }

}
