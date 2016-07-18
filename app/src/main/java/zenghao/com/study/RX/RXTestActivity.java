package zenghao.com.study.RX;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func0;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import zenghao.com.study.R;
import zenghao.com.study.bean.User;

/**
 * Created by zenghao on 16/5/25.
 */
public class RXTestActivity extends AppCompatActivity {

    private String TAG = "====RXTestActivity";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_test);


    }


    /***
     * 正常使用
      */
    private void user1(){
        //observable调用subscribe方法执行call回调到Subscriber
        Observable observable = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("Hello");
                subscriber.onNext("Hi");
                subscriber.onNext("Aloha");
                subscriber.onCompleted();
            }

        });

        //这个方法里面到参数可以是 Observer Subscriber ActionX FuncX
        observable.subscribe(new Subscriber<String>() {
            @Override
            public void onStart() {
                super.onStart();
                Log.e(TAG,"startstart");
            }

            @Override
            public void onCompleted() {
                Log.e(TAG,"onCompletedonCompletedonCompleted");
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String o) {
                Log.e(TAG,o);
            }
        });
  }

    /***
     * 正常使用2
     */
    private void user2(){
                Observable.just(1, 2, 3, 4)
                .subscribeOn(Schedulers.io()) // 指定 subscribe() 发生在 IO 线程
                .observeOn(AndroidSchedulers.mainThread()) // 指定 Subscriber 的回调发生在主线程
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer number) {
                        Log.d(TAG, "number:" + number);
                    }
                });
    }


    /***
     * 正常使用3
     */
    private void user3(){
        String[] words = {"Hello", "Hi", "Aloha"};
        Observable.from(words)
                .subscribeOn(Schedulers.io()) // 指定 subscribe() 发生在 IO 线程
                .observeOn(AndroidSchedulers.mainThread()) // 指定 Subscriber 的回调发生在主线程
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String number) {
                        Log.d(TAG, "number:" + number);
                    }
                });


    }

    public int i=10;

    /***
     * defer 使用
     */
    private void userdefer(){

        Observable justObservable = Observable.just(i);
        i=12;
        Observable deferObservable = Observable.defer(new Func0<Observable<Integer>>() {
            @Override
            public Observable<Integer> call() {
                return Observable.just(i);
            }
        });
        i=15;

        justObservable.subscribe(new Subscriber() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Object o) {
                System.out.println("just result:" + o.toString());
            }
        });

        deferObservable.subscribe(new Subscriber() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Object o) {
                System.out.println("defer result:" + o.toString());
            }
        });


//        其中i是类的成员变量，运行结果如下：
//        just result:10
//        defer result:15
//
//        可以看到，just操作符是在创建Observable就进行了赋值操作，而defer是在订阅者订阅时才创建Observable，此时才进行真正的赋值操作

    }




    /***
     * 使用map转换
     */
    private void user4(){
        //通过map 将数据进行操作 继续传递  可以联系调用多个map
            Observable.just("hello word")
                    .map(new Func1<String, String>() {
                        @Override
                        public String call(String s) {
                            return s+"yesyes";
                        }
                    })
                    .subscribe(new Action1<String>() {
                        @Override
                        public void call(String o) {
                            Log.d(TAG, "mapmap:" + o);
                        }
                    });




        /*Observable.just("images/logo.png") // 输入类型 String
                .map(new Func1<String, Bitmap>() {
                    @Override
                    public Bitmap call(String filePath) { // 参数类型 String
                        return getBitmapFromPath(filePath); // 返回类型 Bitmap
                    }
                })
                .subscribe(new Action1<Bitmap>() {
                    @Override
                    public void call(Bitmap bitmap) { // 参数类型 Bitmap
                        showBitmap(bitmap);
                    }
                });*/
    }

    /**
     * flatMap使用
     */
    private void user5(){

        //传入User call里面 返回 user中到一个数组
        User[] students = null;
        final String [] s = null;

        Subscriber<String> subscriber = new Subscriber<String>() {
            @Override
            public void onNext(String course) {
                Log.d(TAG, course);
            }

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }
        };
        Observable.from(students)
                .flatMap(new Func1<User, Observable<String>>() {
                    @Override
                    public Observable<String> call(User student) {
                        return Observable.from(s);
                    }
                })
                .subscribe(subscriber);



        /*Student[] students = ...;
        Subscriber<Course> subscriber = new Subscriber<Course>() {
            @Override
            public void onNext(Course course) {
                Log.d(tag, course.getName());
            }
            ...
        };
        Observable.from(students)
                .flatMap(new Func1<Student, Observable<Course>>() {
                    @Override
                    public Observable<Course> call(Student student) {
                        return Observable.from(student.getCourses());
                    }
                })
                .subscribe(subscriber);*/



    }


    /**
     * concat 使用
     * 前面任何一个条件满足，就不会执行后面的
     * Concat操作符将多个Observable结合成一个Observable并发射数据，
     * 并且严格按照先后顺序发射数据，前一个Observable的数据没有发射完，
     * 是不能发射后面Observable的数据的。
     */
    private void user6(){
       /* final Observable<String> memory = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                if (memoryCache != null) {
                    subscriber.onNext(memoryCache);
                } else {
                    subscriber.onCompleted();
                }
            }
        });
        Observable<String> disk = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                String cachePref = rxPreferences.getString("cache").get();
                if (!TextUtils.isEmpty(cachePref)) {
                    subscriber.onNext(cachePref);
                } else {
                    subscriber.onCompleted();
                }
            }
        });

        Observable<String> network = Observable.just("network");

        //主要就是靠concat operator来实现
        Observable.concat(memory, disk, network)
                .first()
                .subscribeOn(Schedulers.newThread())
                .subscribe(s -> {
                    memoryCache = "memory";
                    System.out.println("--------------subscribe: " + s);
                });*/
    }

    /**
     * merge 使用
     *界面需要等到多个接口并发取完数据，再更新
     */
    private void user7(){
        //拼接两个Observable的输出，不保证顺序，按照事件产生的顺序发送给订阅者

        /*Observable<String> observable1 = DemoUtils.createObservable1().subscribeOn(Schedulers.newThread());
        Observable<String> observable2 = DemoUtils.createObservable2().subscribeOn(Schedulers.newThread());

        Observable.merge(observable1, observable2)
                .subscribeOn(Schedulers.newThread())
                .subscribe(System.out::println);*/
    }


    private void temp(){


        /*Observable.just("1", "2", "2", "3", "4", "5")
                .map(Integer::parseInt)
                .filter(s -> s > 1)
                .distinct()
                .take(3)
                .reduce((integer, integer2) -> integer.intValue() + integer2.intValue())
                .subscribe(System.out::println);//9*/



        Observable.create(new Observable.OnSubscribe<Object>() {
            @Override
            public void call(Subscriber<? super Object> subscriber) {

            }
        }).subscribe();


        Observer<String> observer = new Observer<String>() {
            @Override
            public void onNext(String s) {
            }

            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
            }
        };

        Subscriber<String> subscriber = new Subscriber<String>() {

            @Override
            public void onStart() {
                super.onStart();
            }

            @Override
            public void onNext(String s) {
            }

            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
            }


        };



        /*Action1<String> onNextAction = new Action1<String>() {
            // onNext()
            @Override
            public void call(String s) {
                Log.d(tag, s);
            }
        };
        Action1<Throwable> onErrorAction = new Action1<Throwable>() {
            // onError()
            @Override
            public void call(Throwable throwable) {
                // Error handling
            }
        };
        Action0 onCompletedAction = new Action0() {
            // onCompleted()
            @Override
            public void call() {
                Log.d(tag, "completed");
            }
        };

        自定义
        // 自动创建 Subscriber ，并使用 onNextAction 来定义 onNext()
                observable.subscribe(onNextAction);
        // 自动创建 Subscriber ，并使用 onNextAction 和 onErrorAction 来定义 onNext() 和 onError()
                observable.subscribe(onNextAction, onErrorAction);
        // 自动创建 Subscriber ，并使用 onNextAction、 onErrorAction 和 onCompletedAction 来定义 onNext()、 onError() 和 onCompleted()
                observable.subscribe(onNextAction, onErrorAction, onCompletedAction);*/


        /*Observable.just(1, 2, 3, 4)
                .subscribeOn(Schedulers.io()) // 指定 subscribe() 发生在 IO 线程
                .observeOn(AndroidSchedulers.mainThread()) // 指定 Subscriber 的回调发生在主线程
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer number) {
                        Log.d(tag, "number:" + number);
                    }
                });
        上面这段代码中，由于 subscribeOn(Schedulers.io()) 的指定，被创建的事件的内容 1、2、3、4 将会在 IO 线程发出；
        而由于 observeOn(AndroidScheculers.mainThread()) 的指定，因此 subscriber 数字的打印将发生在主线程 。
        事实上，这种在 subscribe() 之前写上两句 subscribeOn(Scheduler.io()) 和
        observeOn(AndroidSchedulers.mainThread()) 的使用方式非常常见，它适用于多数的 『后台线程取数据，主线程显示』的程序策略。*/






        /*int drawableRes = ...;
        ImageView imageView = ...;
        Observable.create(new OnSubscribe<Drawable>() {
            @Override
            public void call(Subscriber<? super Drawable> subscriber) {
                Drawable drawable = getTheme().getDrawable(drawableRes));
                subscriber.onNext(drawable);
                subscriber.onCompleted();
            }
        })
                .subscribeOn(Schedulers.io()) // 指定 subscribe() 发生在 IO 线程
                .observeOn(AndroidSchedulers.mainThread()) // 指定 Subscriber 的回调发生在主线程
                .subscribe(new Observer<Drawable>() {
                    @Override
                    public void onNext(Drawable drawable) {
                        imageView.setImageDrawable(drawable);
                    }

                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(activity, "Error!", Toast.LENGTH_SHORT).show();
                    }
                });*/



         /*
         *定时
        Observable.timer(2,TimeUnit.SECONDS,AndroidSchedulers.mainThread()).map(new Func1<Long, Object>() {
            @Override
            public Object call(Long aLong) {
                startActivity(new Intent(RXTestActivity.this, MainActivity.class));
                finish();
                return null;
            }
        }).subscribe();*/



        /*Observable.just("images/logo.png") // 输入类型 String
                .map(new Func1<String, Bitmap>() {
                    @Override
                    public Bitmap call(String filePath) { // 参数类型 String
                        return getBitmapFromPath(filePath); // 返回类型 Bitmap
                    }
                })
                .subscribe(new Action1<Bitmap>() {
                    @Override
                    public void call(Bitmap bitmap) { // 参数类型 Bitmap
                        showBitmap(bitmap);
                    }
                });*/

//        Observable observable = Observable.just("Hello", "Hi", "Aloha");
        // 将会依次调用：
        // onNext("Hello");
        // onNext("Hi");
        // onNext("Aloha");
        // onCompleted();


//        String[] words = {"Hello", "Hi", "Aloha"};
//        Observable observable = Observable.from(words);
        // 将会依次调用：
        // onNext("Hello");
        // onNext("Hi");
        // onNext("Aloha");
        // onCompleted();
    }
}
