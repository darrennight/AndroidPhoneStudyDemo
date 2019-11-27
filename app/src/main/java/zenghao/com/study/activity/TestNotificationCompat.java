package zenghao.com.study.activity;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
//import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import zenghao.com.study.MainActivity;
import zenghao.com.study.R;
import zenghao.com.study.view.notifyUtil.NotifyUtil;
import zenghao.com.study.view.notifyUtil.TestBaseBuilder;
import zenghao.com.study.view.notifyUtil.TestNotifyBuildActivity;

/**
 * Created by zenghao on 15/12/30.
 * 5.0以后需要设置大小icon  需要设计透明icon 否则线上不出来图标
 *
 * NotificationAdapter 通过这个来设置通知栏颜色适配不同手机
 * https://blog.dreamtobe.cn/2016/01/09/notification_best_practise/
 */
public class TestNotificationCompat extends AppCompatActivity {

    private Button mShowNotify;
    private Button mShowBigNotify;
    private Button mShowProgressNotify;

    private Button mTestBuild;
    private Button mTestBuild2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notificationcompat);
        initView();

        for (int i=0 ;i<=10;i++){
            String s = "11111";
            Log.e("====","===="+s);
            if(i==5){
                Log.e("====","===="+i);
            }
        }

        mTestBuild = ((Button) findViewById(R.id.btn_test_build));
        mTestBuild.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TestNotificationCompat.this, TestNotifyBuildActivity.class));
            }
        });
        mTestBuild2 = ((Button) findViewById(R.id.btn_test_build2));
        mTestBuild2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //simpleNotify();
                //test();
                /**一般正常显示*/
                /*int icon = R.drawable.img_push_notifi_small;
                Bitmap largeIcon = BitmapFactory.decodeResource(getResources(),  R.drawable.notification_icon);
                new TestBaseBuilder.Builder(getApplicationContext())
                        .setSmallIcon(icon)
                        .setLargIcon(largeIcon)
                        .setTicker("normalNitify")
                        .setContentTitle("标题标题标题")
                        .setContentText("内容内容内容")
                        .setSubText("333333")
                        .setWhen(System.currentTimeMillis())
                        .setSound(true)
                        .setId(100)
                        .build();*/


                //showProgress();

                /**大文本*/
                /*android.support.v4.app.NotificationCompat.BigTextStyle style = new android.support.v4.app.NotificationCompat.BigTextStyle();
                style.bigText("这里是点击通知后要显示的正文，可以换行可以显示很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长");
                style.setBigContentTitle("点击后的标题");
                //SummaryText没什么用 可以不设置
                style.setSummaryText("末尾只一行的文字内容");
                int icon = R.drawable.img_push_notifi_small;
                Bitmap largeIcon = BitmapFactory.decodeResource(getResources(),  R.drawable.notification_icon);
                new TestBaseBuilder.Builder(getApplicationContext())
                        .setSmallIcon(icon)
                        .setLargIcon(largeIcon)
                        .setTicker("normalNitify")
                        .setContentTitle("标题标题标题")
                        .setContentText("内容内容内容")
                        .setSubText("333333")
                        .setStyle(style)
                        .setWhen(System.currentTimeMillis())
                        .setSound(true)
                        .setId(100)
                        .build();*/

                //大图显示
                /*android.support.v4.app.NotificationCompat.BigPictureStyle style = new android.support.v4.app.NotificationCompat.BigPictureStyle();
                style.setBigContentTitle("BigContentTitle");
                style.setSummaryText("SummaryText");
                style.bigPicture(BitmapFactory.decodeResource(getResources(),R.drawable.timg2));
                int icon = R.drawable.img_push_notifi_small;
                Bitmap largeIcon = BitmapFactory.decodeResource(getResources(),  R.drawable.notification_icon);
                new TestBaseBuilder.Builder(getApplicationContext())
                        .setSmallIcon(icon)
                        .setLargIcon(largeIcon)
                        .setTicker("normalNitify")
                        .setContentTitle("标题标题标题")
                        .setContentText("内容内容内容")
                        .setSubText("333333")
                        .setWhen(System.currentTimeMillis())
                        .setSound(true)
                        .setStyle(style)
                        .setId(100)
                        .build();*/


                // TODO 添加多个按钮

            }
        });
    }

    int progresses = 0;
    private void showProgress() {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(progresses >=100){
                    return;
                }
                progresses = progresses +10;
                int icon = R.drawable.img_push_notifi_small;
                Bitmap largeIcon = BitmapFactory.decodeResource(getResources(),  R.drawable.notification_icon);
                new TestBaseBuilder.Builder(getApplicationContext())
                        .setSmallIcon(icon)
                        .setLargIcon(largeIcon)
                        .setContentTitle("标题标题标题")
                        .setMaxProgress(100)
                        .setProgress(progresses)
                        .setWhen(System.currentTimeMillis())
                        .setSound(false)
                        .setId(100)
                        .build();
                showProgress();

            }
        },500);

    }

    private void initView() {
        //heard-up 风格
        /*if (android.os.Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            builder.setDefaults(Notification.DEFAULT_SOUND)
                    .setPriority(android.support.v4.app.NotificationCompat.PRIORITY_MAX);
        }*/
        mShowNotify = ((Button) this.findViewById(R.id.bt_show_notifi));
        mShowNotify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(TestNotificationCompat.this);
                mBuilder.setSmallIcon(R.drawable.ic_dialog_info);
                mBuilder.setContentTitle("5 new message");
                mBuilder.setContentText("twain@android.com");

                mBuilder.setTicker("New message");//第一次提示消息的时候显示在通知栏上 5.0以后不再显示

                mBuilder.setNumber(12);
                //mBuilder.setLargeIcon(R.mipmap.ic_launcher);

                mBuilder.setAutoCancel(true);//自己维护通知的消失

//                mBuilder.setPriority(NotificationCompat.PRIORITY_MAX);
                mBuilder.setUsesChronometer(true);

                /*if (android.os.Build.VERSION.SDK_INT >= 21) {
                    mBuilder.setVisibility(Notification.VISIBILITY_PUBLIC);
                }*/

                if (android.os.Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
                    mBuilder.setDefaults(Notification.DEFAULT_SOUND)
                            .setPriority(android.support.v4.app.NotificationCompat.PRIORITY_MAX);
                }

                //构建一个Intent
                Intent resultIntent = new Intent(TestNotificationCompat.this,
                        PermissionActivity.class);
                //封装一个Intent
                PendingIntent resultPendingIntent = PendingIntent.getActivity(
                        TestNotificationCompat.this, 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);

//                mBuilder.setFullScreenIntent(resultPendingIntent, false);

                // 设置通知主题的意图
                mBuilder.setContentIntent(resultPendingIntent);



                //获取通知管理器对象
                NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                Notification noti =  mBuilder.build();

                new Notification();
//                noti.icon = R.drawable.ic_dialog_info;
                mNotificationManager.notify(0,noti);
            }
        });




        mShowBigNotify = ((Button) this.findViewById(R.id.bt_show_big_notifi));
        mShowBigNotify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bitmap btm = BitmapFactory.decodeResource(getResources(),
                        R.mipmap.ic_launcher);
                Intent intent = new Intent(TestNotificationCompat.this,
                        PermissionActivity.class);

                PendingIntent pendingIntent = PendingIntent.getActivity(
                        TestNotificationCompat.this, 0, intent,
                        PendingIntent.FLAG_CANCEL_CURRENT);


                new NotificationCompat.InboxStyle()
                        .addLine(
                                "M.Twain (Google+) Haiku is more than a cert...")
                        .addLine("M.Twain Reminder")
                        .addLine("M.Twain Lunch?")
                        .addLine("M.Twain Revised Specs")
                        .addLine("M.Twain ")
                        .addLine(
                                "Google Play Celebrate 25 billion apps with Goo..")
                        .addLine(
                                "Stack Exchange StackOverflow weekly Newsl...")
                        .setBigContentTitle("6 new message")
                        .setSummaryText("mtwain@android.com");

                NotificationCompat.BigPictureStyle pictureStyle = new NotificationCompat.BigPictureStyle();
                pictureStyle.setBigContentTitle("BigContentTitle")
                        .setSummaryText("SummaryText").bigPicture(btm);


                NotificationCompat.BigTextStyle textStyle = new NotificationCompat.BigTextStyle();
                textStyle
                        .setBigContentTitle("BigContentTitle")
                        .setSummaryText("SummaryText")
                        .bigText(
                                "I am Big Texttttttttttttttttttttttttttttttttttttttttttt!!!!!!!!!!!!!!!!!!!......");

                Notification noti = new NotificationCompat.Builder(
                        TestNotificationCompat.this)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setLargeIcon(btm)
                        .setNumber(13)
                        .setContentIntent(pendingIntent)
                        .setStyle( textStyle)
                        .build();

                NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                mNotificationManager.notify(1, noti);


            }
        });



        mShowProgressNotify = ((Button) this.findViewById(R.id.bt_show_progress_notifi));
        mShowProgressNotify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               final NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
               final NotificationCompat.Builder builder = new NotificationCompat.Builder(TestNotificationCompat.this);
                builder .setSmallIcon(R.mipmap.ic_launcher);
                builder .setContentTitle("Picture Download");
                builder .setContentText("Download in progress");
                builder.setAutoCancel(true);
                //通过一个子线程，动态增加进度条刻度
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        int incr;
                        for (incr = 0; incr <= 100; incr += 5) {
                            builder.setProgress(100, incr, false);//builder.setProgress(0, 0, true);//设置为true，表示流动
                            manager.notify(0, builder.build());
                            try {
                                Thread.sleep(300);
                            } catch (InterruptedException e) {
                            }
                        }
                        builder.setContentText("Download complete")
                                .setProgress(0, 0, false);
                        manager.notify(0, builder.build());
                    }
                }).start();
            }
        });
    }


    public static final int TYPE_Normal = 1;
    private void simpleNotify(){
        NotificationManager manger = (NotificationManager)getSystemService(Activity.NOTIFICATION_SERVICE);
        //为了版本兼容  选择V7包下的NotificationCompat进行构造
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        //Ticker是状态栏显示的提示
        builder.setTicker("简单Notification");
        //第一行内容  通常作为通知栏标题
        builder.setContentTitle("标题");
        //第二行内容 通常是通知正文
        builder.setContentText("通知内容");
        //第三行内容 通常是内容摘要什么的 在低版本机器上不一定显示
        builder.setSubText("这里显示的是通知第三行内容！");
        //ContentInfo 在通知的右侧 时间的下面 用来展示一些其他信息
        //builder.setContentInfo("2");
        //number设计用来显示同种通知的数量和ContentInfo的位置一样，如果设置了ContentInfo则number会被隐藏
        builder.setNumber(2);
        //可以点击通知栏的删除按钮删除
        builder.setAutoCancel(true);
        //系统状态栏显示的小图标
        builder.setSmallIcon(R.drawable.img_push_notifi_small);
        //下拉显示的大图标
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(),R.drawable.notification_icon));
        Intent intent = new Intent(this,MainActivity.class);
        PendingIntent pIntent = PendingIntent.getActivity(this,1,intent,0);
        //点击跳转的intent
        builder.setContentIntent(pIntent);
        //通知默认的声音 震动 呼吸灯
        builder.setDefaults(NotificationCompat.DEFAULT_ALL);
        Notification notification = builder.build();
        manger.notify(TYPE_Normal,notification);
    }


    private void test(){
        int icon = R.drawable.img_push_notifi_small;
        Bitmap largeIcon = BitmapFactory.decodeResource(getResources(),  R.drawable.notification_icon);
        long when = System.currentTimeMillis();
        NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);


            String title = "title";
            Intent notificationIntent = new Intent();
            int nId = (int) (Math.random() * 1000000);
            PendingIntent intent = PendingIntent.getBroadcast(this, nId, notificationIntent, 0);
            android.support.v4.app.NotificationCompat.Builder builder = new  android.support.v4.app.NotificationCompat.Builder(this);

            builder.setAutoCancel(true);
            builder.setContentTitle(title);
            builder.setContentText("netChannelMessage.msg");
            builder.setSmallIcon(icon);
            builder.setLargeIcon(largeIcon);
            builder.setWhen(when);
            builder.setContentIntent(intent);
            builder.build();
            Notification notification = builder.getNotification();
            notificationManager.notify(nId, notification);
    }


    private void mediaStyle(){
        /*NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setContentTitle("MediaStyle");
        builder.setContentText("Song Title");
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(),R.drawable.notification));
        builder.setDefaults(NotificationCompat.DEFAULT_ALL);
        Intent intent = new Intent(this,ImageActivity.class);
        PendingIntent pIntent = PendingIntent.getActivity(this,1,intent,0);
        builder.setContentIntent(pIntent);
        //第一个参数是图标资源id 第二个是图标显示的名称，第三个图标点击要启动的PendingIntent
        builder.addAction(R.drawable.ic_previous_white,"",null);
        builder.addAction(R.drawable.ic_stop_white,"",null);
        builder.addAction(R.drawable.ic_play_arrow_white_18dp,"",pIntent);
        builder.addAction(R.drawable.ic_next_white,"",null);
        NotificationCompat.MediaStyle style = new NotificationCompat.MediaStyle();
        style.setMediaSession(new MediaSessionCompat(this,"MediaSession",
                new ComponentName(MainActivity.this,Intent.ACTION_MEDIA_BUTTON),null).getSessionToken());
        //CancelButton在5.0以下的机器有效
        style.setCancelButtonIntent(pIntent);
        style.setShowCancelButton(true);
        //设置要现实在通知右方的图标 最多三个
        style.setShowActionsInCompactView(2,3);
        builder.setStyle(style);
        builder.setShowWhen(false);
        Notification notification = builder.build();
        manger.notify(TYPE_Media,notification);*/
    }

}
//自定义视图
//btnCustomNotification.setOnClickListener(new View.OnClickListener() {
//@Override
//public void onClick(View v) {
//        RemoteViews contentViews = new RemoteViews(getPackageName(),
//        R.layout.custom_notification);
//        //通过控件的Id设置属性
//        contentViews
//        .setImageViewResource(R.id.imageNo, R.drawable.btm1);
//        contentViews.setTextViewText(R.id.titleNo, "自定义通知标题");
//        contentViews.setTextViewText(R.id.textNo, "自定义通知内容");
//
//        Intent intent = new Intent(MainActivity.this,
//        ResultActivity.class);
//
//        PendingIntent pendingIntent = PendingIntent.getActivity(
//        MainActivity.this, 0, intent,
//        PendingIntent.FLAG_CANCEL_CURRENT);
//        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
//        MainActivity.this).setSmallIcon(R.drawable.ic_launcher)
//        .setContentTitle("My notification")
//        .setTicker("new message");
//        mBuilder.setAutoCancel(true);
//
//        mBuilder.setContentIntent(pendingIntent);
//        mBuilder.setContent(contentViews);
//        mBuilder.setAutoCancel(true);
//        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//        mNotificationManager.notify(10, mBuilder.build());
//        }
//        });


/*
对于有些通知，需要调用一些设备的资源，使用户能更快的发现有新通知，一般可设定的响应有：铃声、闪光灯、震动。对于这三个属性，NotificationCompat.Builder提供了三个方法设定：

        setSound(Uri sound)：设定一个铃声，用于在通知的时候响应。传递一个Uri的参数，格式为“file:///mnt/sdcard/Xxx.mp3”。
        setLights(int argb, int onMs, int offMs)：设定前置LED灯的闪烁速率，持续毫秒数，停顿毫秒数。
        setVibrate(long[] pattern)：设定震动的模式，以一个long数组保存毫秒级间隔的震动。
        　　大多数时候，我们并不需要设定一个特定的响应效果，只需要遵照用户设备上系统通知的效果即可，那么可以使用setDefaults(int)方法设定默认响应参数，在Notification中，对它的参数使用常量定义了，我们只需使用即可：

        DEFAULT_ALL：铃声、闪光、震动均系统默认。
        DEFAULT_SOUND：系统默认铃声。
        DEFAULT_VIBRATE：系统默认震动。
        DEFAULT_LIGHTS：系统默认闪光。*/
