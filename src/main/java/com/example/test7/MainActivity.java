package com.example.test7;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import io.reactivex.*;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.*;
import io.reactivex.observables.GroupedObservable;
import io.reactivex.schedulers.Schedulers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        foo1();
//        foo2();
//        foo3();
//        foo4();
//        foo5();
//        foo6();
        foo7();
//        foo8();
//        foo9();
//        foo10();
//        foo11();
//        foo12();
//        foo13();
//        foo14();
//        foo15();
//        foo16();
//        foo17();
//        foo18();
//        foo19();
//        foo20();
//        foo21();
//        foo22();
//        foo23();
//        foo24();
//        foo25();
//        foo26();


    }

    private void foo26() {
/**toList():表示将多项数据处理时，每次都会调用onNext方法，改成组成一个Observable集合，一次性的调用onNext()方法
 * 体现在方法中就是在Consumer中返回的是一个list集合
 * toSortList:类似上面，但是可以排序
 */
        Observable.just(1, 2, 3).toList().subscribe(new Consumer<List<Integer>>() {
            @Override
            public void accept(List<Integer> integers) throws Exception {
                for (int i = 0; i < integers.size(); i++) {
                    Log.e("TAG", "toList==" + integers.get(i));
                }
            }
        });
        Observable.just(3, 2, 1).toSortedList().subscribe(new Consumer<List<Integer>>() {
            @Override
            public void accept(List<Integer> integers) throws Exception {
                for (int i = 0; i < integers.size(); i++) {
                    Log.e("TAG", "toSortedList==" + integers.get(i));
                }
            }
        });

//        toMap:  默认转化为HashMap,然后发射这个map,也可以提供生成Map的key函数
        TestBean testBean1 = new TestBean("啦啦啦", "A");
        TestBean testBean2 = new TestBean("啦啦啦1", "SS");
        TestBean testBean3 = new TestBean("啦啦啦2", "S");
        TestBean testBean4 = new TestBean("啦啦啦3", "A");
        TestBean testBean5 = new TestBean("啦啦啦4", "S");
        TestBean testBean6 = new TestBean("啦啦啦5", "SS");

        Observable.just(testBean1, testBean2, testBean3, testBean4, testBean5).toMap(new Function<TestBean, String>() {
            @Override
            public String apply(TestBean testBean) throws Exception {
                return testBean.getLevel();
            }

        }).subscribe(new Consumer<Map<String, TestBean>>() {
            @Override
            public void accept(Map<String, TestBean> stringTestBeanMap) throws Exception {
//             会获取到相应key最后的对应的数据
                Log.e("TAG", "stringTestBeanMap==" + stringTestBeanMap.get("A").getName());
            }
        });

    }

    private void foo25() {
/**条件操作符
 * amb：会优先发送排名最前的数据。
 * defaultIfEmpty: 表示如果没有数据，就会发送默认的数据
 */
        Observable.fromIterable(new ArrayList<Integer>())
                .defaultIfEmpty(1)
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.e("TAG", "defaultIfEmpty===" + integer);
                    }
                });


    }

    private void foo24() {
// all：对所有的数据进行判断，然后返回判断的结果,如果全部符合，那么就返回true,否则就返回false
        Observable.just(1, 2, 3).all(new Predicate<Integer>() {
            @Override
            public boolean test(Integer integer) throws Exception {
                return integer > 2;
            }
        }).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) throws Exception {
                Log.e("TAG", "aBoolean===" + aBoolean);
            }
        });

//        contains:判断是否包含，如果包含就包括就返回true,否则就是false

        Observable.just(1, 2, 3).contains(1).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) throws Exception {
                Log.e("TAG", "contains===" + aBoolean);
            }
        });

//        isEmpty:表示是否为空数据
        Observable.fromIterable(new ArrayList<Integer>()).isEmpty().subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) throws Exception {
                Log.e("TAG", "isEmpty===" + aBoolean);
            }
        });

    }

    private void foo23() {
//        retry：表示出现错误后，再重试的次数
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(0);
                emitter.onError(new NullPointerException());
                emitter.onComplete();
            }
//            表示出现异常后，再重试三次
        }).retry(3)
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.e("TAG", "==" + integer);
                    }
                });


    }

    private void foo22() {
/**错误处理符  catch
 *  onErrorReturn:  当出现错误的时候，会拦截onError调用，
 *  onErrorResumeNext:
 *  onExceptionResumeNext:
 */
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
//                for (int i = 0; i < 5; i++) {
//                    if (i > 2) {
//                        emitter.onError(new Throwable("人为异常"));
//                    }
//                    emitter.onNext(i);
//                }

                emitter.onNext(1);
                emitter.onError(new NullPointerException());
                emitter.onComplete();
            }
//            onExceptionResumeNext只能拦截一个异常？？
        }).onExceptionResumeNext(new ObservableSource<Integer>() {
            @Override
            public void subscribe(Observer<? super Integer> observer) {
                Log.e("TAG", "处理异常");
                observer.onNext(0);
                observer.onComplete();
            }
        }).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                Log.e("TAG", "==" + integer);
            }
        });

        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
//                for (int i = 0; i < 5; i++) {
//                    if (i > 2) {
//                        emitter.onError(new Throwable("人为异常"));
//                    }
//                    emitter.onNext(i);
//                }

                emitter.onNext(1);
                emitter.onError(new Throwable("人为异常"));
//                emitter.onError(new Throwable("人为异常2"));
                emitter.onComplete();
            }
//            onErrorResumeNext只能拦截一个异常？？
        }).onErrorResumeNext(new Function<Throwable, ObservableSource<? extends Integer>>() {
            @Override
            public ObservableSource<? extends Integer> apply(Throwable throwable) throws Exception {
                return Observable.just(1);
            }
        }).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                Log.e("TAG", "==" + integer);
            }
        });


        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
//                for (int i = 0; i < 5; i++) {
//                    if (i > 2) {
//                        emitter.onError(new Throwable("人为异常"));
//                    }
//                    emitter.onNext(i);
//                }

                emitter.onNext(1);
                emitter.onError(new Throwable("人为异常"));
//                emitter.onError(new Throwable("人为异常2"));
                emitter.onComplete();
            }
//            onErrorReturn只能拦截一个异常？？
        }).onErrorReturn(new Function<Throwable, Integer>() {
            @Override
            public Integer apply(Throwable throwable) throws Exception {
                Log.e("TAG", "在onErrorReturn处理了错误: " + throwable.toString());
                return 666;
            }
        }).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                Log.e("TAG", "==" + integer);
            }
        });
    }

    private void foo21() {
        /** subscribeOn操作符用于指定的Observable自身在哪个线程上运行，比如耗时操作可以在这个线程中运行
         *  observerOn:用来指定Observer所运行的线程，也就是在哪个线程中使用。
         *  timeout：表示的是在超时后就会切换到你指定的一个备用Observable进行处理
         */
        /**
         *Schedulers.immediate()：直接在当前线程执行
         * Schedulers.newThread():总是启动新线程
         * Schedulers.io()  内部实现是用一个无数量上线的线程池，可以重用空闲线程，一般比Scheduler.newThread()更有效率
         * Schedulers.computation():使用的是固定线程池，大小为cpu个数，不要把i/o操作放在computaion()中，io的操作等待时间会浪费cpu
         * Schedulers.trampoline():并不会立即执行，先进行入队，然后调度器会处理他的队列，并按顺序运行队列的每个人物
         * AndroidSchedulers.mainThread()
         */

        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                emitter.onNext(Thread.currentThread().getName() + "我从异步线程处理");
                emitter.onComplete();

            }
        }).timeout(1000, TimeUnit.MILLISECONDS, Observable.just("补充内容"))
                .subscribeOn(Schedulers.newThread())//指定在哪个线程中注册
                .observeOn(AndroidSchedulers.mainThread())//指定在哪个线程中观察
                .subscribe(new Consumer<String>() { //定义观察者
                    @Override
                    public void accept(String s) throws Exception {
                        Log.e("TAG", Thread.currentThread().getName() + "===收到数据===" + s);
                    }
                });

    }

    private void foo20() {
/**
 * delay:延时操作
 *
 * Do系列操作符
 *   doOnEach:当Observable每发射一项就会调用一次，包裹onNext onError  onCompleted
 *   doOnNext:当onNext的时候会调用
 *   doOnSubscribe:当观察者订阅Observable时就会被调用
 *   doOnUnsubscribe:不存在这个方法
 *   doOnCompleted:
 *   doOnError:
 *   doOnTerminate:当Observable无论是正常终止还是异常终止之前都会调用
 *   finallyDo:当Observable终止后调用
 *
 *   执行顺序
 *   doOnEach
 *   doOnNext
 *   subscribe
 *   doOnComplete
 *   doOnTerminate
 *   doFinally
 *
 */
        Observable.just(1, 2, 3).doOnEach(new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Integer integer) {
                Log.e("TAG", "doOnEach==" + integer);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        }).doOnNext(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                Log.e("TAG", "doOnNext==" + integer);
            }
        }).doOnSubscribe(new Consumer<Disposable>() {
            @Override
            public void accept(Disposable disposable) throws Exception {
                Log.e("TAG", "doOnSubscribe==" + disposable);
            }
        }).doOnComplete(new Action() {
            @Override
            public void run() throws Exception {
                Log.e("TAG", "doOnComplete==");
            }
        }).doOnError(new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                Log.e("TAG", "doOnError==");
            }
        }).doOnTerminate(new Action() {
            @Override
            public void run() throws Exception {
                Log.e("TAG", "doOnTerminate==");
            }
        }).doFinally(new Action() {
            @Override
            public void run() throws Exception {
                Log.e("TAG", "doFinally==");
            }
        }).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                Log.e("TAG", "subscribe==" + integer);
            }
        });

    }

    private void foo19() {
//        使用merge将多个Observable进行合并操作
        Observable<Integer> just1 = Observable.just(1, 2, 3);
        Observable<Integer> just2 = Observable.just(4, 5, 6);

        Observable.merge(just1, just2).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                Log.e("TAG", "merge==" + integer);
            }
        });

//        使用concat将多个Observable进行合并操作,但是是严格按照顺序发射
        Observable.concat(just1, just2).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                Log.e("TAG", "concat==" + integer);
            }
        });

        Observable<String> just3 = Observable.just("A", "B", "C");
//        使用zip进行合并  其中BiFunction中第一个参数是第一个Observable中的类型，  第二个参数是第二个Observable中的类型
        Observable.zip(just1, just3, new BiFunction<Integer, String, Object>() {
            @Override
            public Object apply(Integer integer, String s) throws Exception {
                return integer + s;
            }
        }).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                Log.e("TAG", "zip==" + o);
            }
        });

//  combineLatest:就是将第一个Observable的最后一条数据和第二个observable的每一条数据都合并。
        Observable.combineLatest(just1, just3, new BiFunction<Integer, String, Object>() {
            @Override
            public Object apply(Integer integer, String s) throws Exception {
                return integer + s;
            }
        }).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                Log.e("TAG", "combineLatest==" + o);
            }
        });
    }

    private void foo18() {
//        startWith:会在原有数据前添加新数据
        ArrayList<Integer> integers = new ArrayList<>();
        integers.add(7);
        integers.add(8);
        Observable.just(1, 3, 4, 6, 5, 6).startWith(integers).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                Log.e("TAG", "startWith==" + integer);
            }
        });
    }

    private void foo17() {
//      throttleWithTimeout：是通过时间进行限流，在Observable每次发射出来的一个数据后就开始计时，
//      如果在设定好的时间内有新的数据发射出来, 那么就会将当前的数据清空，并且重新计时,否则就处理数据。
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                for (int i = 0; i < 100; i++) {
                    emitter.onNext(i);
                    Thread.sleep(100);
                }
                emitter.onComplete();
            }
        }).throttleWithTimeout(101, TimeUnit.MILLISECONDS).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {

                Log.e("TAG", "throttleFirst==" + integer);
            }
        });
    }


    private void foo16() {
//    throttleFirst:表示会定期发射这个时间段中的第一个数据   throttle表示的是限流的意思
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                for (int i = 0; i < 100; i++) {
                    emitter.onNext(i);
                    Thread.sleep(100);
                }

                emitter.onComplete();

            }
//            会拿到200毫秒内的发射的数据的第一个
        }).throttleFirst(200, TimeUnit.MILLISECONDS).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {

                Log.e("TAG", "throttleFirst==" + integer);
            }
        });
    }

    private void foo15() {
//        skip：跳过前n项, take:只取前n项。
//        skipLast:         takeLast
        Observable.just(1, 3, 4, 6, 5, 6).skipLast(2).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                Log.e("TAG", "distinct==" + integer);
            }
        });

    }

    private void foo14() {
//        distinct:用于去重
        Observable.just(1, 3, 4, 6, 5, 6).distinct().subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                Log.e("TAG", "distinct==" + integer);
            }
        });
    }

    private void foo13() {
//        elementAt：用于返回指定位置的数据
        Observable.just(1, 3, 4, 5, 6).elementAt(2).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                Log.e("TAG", "elementAt==" + integer);
            }
        });
    }

    private void foo12() {
//        filter：过滤操作符
        Observable.just(1, 3, 4, 5, 6).filter(new Predicate<Integer>() {
            @Override
            public boolean test(Integer integer) throws Exception {
                return integer > 2 && integer < 5;
            }
        }).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                Log.e("TAG", "filter==" + integer);
            }
        });

    }

    private void foo11() {
        TestBean testBean1 = new TestBean("啦啦啦", "A");
        TestBean testBean2 = new TestBean("啦啦啦1", "SS");
        TestBean testBean3 = new TestBean("啦啦啦2", "S");
        TestBean testBean4 = new TestBean("啦啦啦3", "A");
        TestBean testBean5 = new TestBean("啦啦啦4", "S");
        TestBean testBean6 = new TestBean("啦啦啦5", "SS");
//        使用groupBy能对集合中的数据进行分组，返回的是分组完毕的Observable
        Observable<GroupedObservable<String, TestBean>> groupedObservableObservable = Observable.just(testBean1,
                testBean2, testBean3, testBean4, testBean5, testBean6)
                .groupBy(new Function<TestBean, String>() {
                    @Override
                    public String apply(TestBean testBean) throws Exception {
                        return testBean.getLevel();
                    }
                });

//        concat进行顺序发射
        Observable.concat(groupedObservableObservable).subscribe(new Consumer<TestBean>() {
            @Override
            public void accept(TestBean testBean) throws Exception {
                Log.e("TAG", "testBean==" + testBean);
            }
        });
    }

    private void foo10() {
//    使用buffer操作符可以控制每次发射的个数，下面就是3个元素放在一起发射
        Observable.just(1, 2, 3, 4, 5, 6).buffer(3).subscribe(new Consumer<List<Integer>>() {
            @Override
            public void accept(List<Integer> integers) throws Exception {
                for (int i = 0; i < integers.size(); i++) {
                    Log.e("TAG", "integers==" + integers.get(i));
                }
            }
        });
    }

    private void foo9() {
//        使用flatMapIterable可以将数据包裹成Iterable,也就是list。
        Observable.just(1, 2, 3).flatMapIterable(new Function<Integer, Iterable<?>>() {
            @Override
            public Iterable<?> apply(Integer integer) throws Exception {
                ArrayList<Integer> integers = new ArrayList<>();
                integers.add(4);
                integers.add(6);
                return integers;
            }
        }).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                Log.e("TAG", "o===" + o);
            }
        });

    }

    private void foo8() {
//      使用concatMap功能类似于flatMap，并且解决了数据交叉的问题
        final ArrayList<String> strings = new ArrayList<>();
        strings.add("eee");
        strings.add("eee2");
        strings.add("eee3");
        Observable.fromIterable(strings).concatMap(new Function<String, ObservableSource<?>>() {
            @Override
            public ObservableSource<?> apply(String s) throws Exception {
                s = s + "----";
                return Observable.just(s);
            }
        }).cast(String.class)
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        Log.e("TAG", "s==" + s);
                    }
                });
    }

    private void foo7() {
//    1、返回值上面：
//    map变换后可以返回任意值，而flatMap则只能返回ObservableSource类型
//    2、变换后的输出：
//    map只能进行一对一的变换，而flatMap则可以进行一对一，一对多，多对多的变换，具体的变换规则根据我们设置的变换函数mapper来定
//    因此使用flatMap比map更加的灵活

//    Observable实现了ObservableSource接口,并且使用cast()方法将返回的Object转化为String.
//    flatMap有可能会导致交错的发送事件，导致结果顺序不一致

        Observable.just(1, 2, 3).flatMap(new Function<Integer, ObservableSource<?>>() {
            @Override
            public ObservableSource<?> apply(Integer integer) throws Exception {
                return Observable.just(integer, integer + 1);
            }
        }).cast(Integer.class).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                Log.e("TAG", "integer==" + integer);
            }
        });

        final ArrayList<String> strings = new ArrayList<>();
        strings.add("eee");
        strings.add("eee2");
        strings.add("eee3");
//       使用FromIterable，可以将集合数据传递进去
        Observable.fromIterable(strings).flatMap(new Function<String, ObservableSource<?>>() {
            @Override
            public ObservableSource<?> apply(String s) throws Exception {
                s = s + "----";
                return Observable.just(s);
            }
        }).cast(String.class)
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String o) throws Exception {
                        Log.e("TAG", "o===" + o);
                    }
                });

    }

    private void foo6() {
//        map  ：将一个Observable转化为另一个新的Observable并发射，观察者将受到新的Observable并处理。
//        需要指定Function对象
        Observable.just("eee").map(new Function<String, String>() {
            @Override
            public String apply(String s) throws Exception {
                if (s.equals("eee")) {
                    s = "lalla";
                }
                return s;
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                Log.e("TAG", "s==" + s);
            }
        });
    }

    private void foo5() {
        final ArrayList<String> strings = new ArrayList<>();
        strings.add("eee");
        strings.add("eee2");
        strings.add("eee3");

//        repeat:表示重复几次
        Observable.range(0, strings.size()).repeat(2).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                Log.e("TAG", "str==" + strings.get(integer));
            }
        });

    }

    private void foo4() {
//        range  取代for循环
        final ArrayList<String> strings = new ArrayList<>();
        strings.add("eee");
        strings.add("eee2");
        strings.add("eee3");

        Observable.range(0, strings.size()).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                Log.e("TAG", "str==" + strings.get(integer));
            }
        });


    }

    private void foo3() {
//       设置定时器  使用Schedulers.shutdown();方法进行取消定时器。在interval中也可以指定线程
//        但是就不能使用Schedulers.newThread().shutdown()等方法取消了
        final Observable<Long> observable = Observable.interval(2, TimeUnit.SECONDS);
        observable.subscribe(new Consumer<Long>() {
            @Override
            public void accept(Long aLong) throws Exception {
                Log.e("TAG", "interval==" + aLong);
                if (aLong == 2) {
                    Log.e("TAG", "取消注册");
                    Schedulers.shutdown();
                }
            }
        });

//        timer：延时执行

        Observable.timer(2, TimeUnit.SECONDS).subscribe(new Consumer<Long>() {
            @Override
            public void accept(Long aLong) throws Exception {
                Log.e("TAG", "timer==" + aLong);
            }
        });

    }

    private void foo2() {
//        just:将事件依次执行，然后依次在Consumer中获取
        Observable<String> observable = Observable.just("啦啦", "www", "ev");
        observable.subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                Log.e("TAG", "foo2--just---" + s);
            }
        });

//       fromArray:将数组中的数据依次执行
        String[] strings = {"ee", "xxx"};
        Observable.fromArray(strings).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                Log.e("TAG", "foo2--fromArray---" + s);
            }
        });

    }


    private void foo1() {
        //观察者，也是决定事件触发后应该进行怎样的行为。
        Observer observer = new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(String s) {
                Log.e("TAG", "Observer===" + s);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };

        //也是观察者，是Observer的简化形式
        Consumer<String> consumer = new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                Log.e("TAG", "Consumer===" + s);
            }
        };

//        被观察者,决定要触发怎样的事件
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
//                在这里可以做相应的逻辑的处理，将结果通过emitter.onNext()发射出去，可以指定在这里运行的线程
                emitter.onNext("啦啦");
                emitter.onNext("哇哇");
                emitter.onComplete();
            }
        }).subscribe(observer);



        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                emitter.onNext("啦啦");
                emitter.onNext("哇哇");
                emitter.onComplete();
            }
        }).subscribe(consumer);

    }


}
