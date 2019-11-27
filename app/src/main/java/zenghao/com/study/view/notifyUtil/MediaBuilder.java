package zenghao.com.study.view.notifyUtil;

import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.media.app.NotificationCompat.MediaStyle;
import android.support.v4.media.session.MediaSessionCompat;


/** 
 * Created by Administrator on 2017/2/13 0013. 
 */ 
 
public class MediaBuilder extends BaseBuilder{ 
 
    @Override 
    public void build() { 
        super.build();
        MediaStyle style = new MediaStyle();
        style.setMediaSession(new MediaSessionCompat(NotifyUtil.context,"MediaSession",
                new ComponentName(NotifyUtil.context, Intent.ACTION_MEDIA_BUTTON),null).getSessionToken());
        //设置要现实在通知右方的图标 最多三个 
        style.setShowActionsInCompactView(2,3); 
        style.setShowCancelButton(true); 
        cBuilder.setStyle(style); 
        cBuilder.setShowWhen(false); 
    } 
 
    @Override 
    public BaseBuilder addBtn(int icon, CharSequence text, PendingIntent pendingIntent) {
        return super.addBtn(icon, text, pendingIntent);
    } 
} 