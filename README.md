1.JUC概述
1.1什么是juc

在 Java 中，线程部分是一个重点，本篇文章说的 JUC 也是关于线程的。JUC就是 java.util .concurrent 工具包的简称。这是一个处理线程的工具包，JDK 1.5 开始出现的。

1.2 线程和进程概念

1.进程和线程

进程（Process） 是计算机中的程序关于某数据集合上的一次运行活动，是系统进行资源分配和调度的基本单位，是操作系统结构的基础。 在当代面向线程设计的计算机结构中，进程是线程的容器。程序是指令、数据及其组织形式的描述，进程是程序的实体。是计算机中的程序关于某数据集合上的一次运行活动，是系统进行资源分配和调度的基本单位，是操作系统结构的基础。程序是指令、数据及其组织形式的描述，进程是程序的实体。

线程（thread） 是操作系统能够进行运算调度的最小单位。它被包含在进程之中，是进程中的实际运作单位。一条线程指的是进程中一个单一顺序的控制流，一个进程中可以并发多个线程，每条线程并行执行不同的任务。

总结来说:
进程：指在系统中正在运行的一个应用程序；程序一旦运行就是进程；进程——资源分配的最小单位。
线程：系统分配处理器时间资源的基本单元，或者说进程之内独立执行的一个单元执行流。线程——程序执行的最小单位。

2.线程的状态

class Thread implements Runnable{
      
    public enum State {
            NEW,//1.新建
            RUNNABLE,//2.准备就绪
            BLOCKED,//3.阻塞
            WAITING,//4.等待，不见不散
            TIMED_WAITING,//5.等待，过时不候
            TERMINATED;//6.终结
        }
    }

3.wati和sleep

（1）sleep 是 Thread 的静态方法，wait 是 Object 的方法，任何对象实例都能调用。
（2）sleep 不会释放锁，它也不需要占用锁。wait 会释放锁，但调用它的前提是当前线程占有锁(即代码要在 synchronized 中)。
（3）它们都可以被 interrupted 方法中断。

4.并发和并行

1.串行模式

串行表示所有任务都一一按先后顺序进行。串行意味着必须先装完一车柴才能运送这车柴，只有运送到了，才能卸下这车柴，并且只有完成了这整个三个步骤，才能进行下一个步骤。
串行是一次只能取得一个任务，并执行这个任务。

2.并行模式

并行意味着可以同时取得多个任务，并同时去执行所取得的这些任务。并行模式相当于将长长的一条队列，划分成了多条短队列，所以并行缩短了任务队列的长度。并行的效率从代码层次上强依赖于多进程/多线程代码，从硬件角度上则依赖于多核 CPU。

3. 并发

并发(concurrent)指的是多个程序可以同时运行的现象，更细化的是多进程可以同时运行或者多指令可以同时运行。但这不是重点，在描述并发的时候也不会去扣这种字眼是否精确，并发的重点在于它是一种现象, 并发描述的是多进程同时运行的现象。但实际上，对于单核心 CPU 来说，同一时刻只能运行一个线程。所以，这里的"同时运行"表示的不是真的同一时刻有多个线程运行的现象，这是并行的概念，而是提供一种功能让用户看来多个程序同时运行起来了，但实际上这些程序中的进程不是一直霸占 CPU 的，而是执行一会停一会。要解决大并发问题，通常是将大任务分解成多个小任务, 由于操作系统对程的调度是随机的，所以切分成多个小任务后，可能会从任一小任务处执行。这可能会出现一些现象：

●  可能出现一个小任务执行了多次，还没开始下个任务的情况。这时一般会采用
队列或类似的数据结构来存放各个小任务的成果 
●  可能出现还没准备好第一步就执行第二步的可能。这时，一般采用多路复用或
异步的方式，比如只有准备好产生了事件通知才执行某个任务。 
●  可以多进程/多线程的方式并行执行这些小任务。也可以单进程/单线程执行这
些小任务，这时很可能要配合多路复用才能达到较高的效率 

4. 小结(重点)

并发：同一时刻多个线程在访问同一个资源，多个线程对一个点
例子：春运抢票 电商秒杀…
并行：多项工作一起执行，之后再汇总
例子：泡方便面，电水壶烧水，一边撕调料倒入桶中

5.管程

操作系统中叫监视器，java中叫锁

**管程(monitor)**是保证了同一时刻只有一个进程在管程内活动,即管程内定义的操作在同一时刻只被一个进程调用(由编译器实现).但是这样并不能保证进程以设计的顺序执行JVM 中同步是基于进入和退出管程(monitor)对象实现的，每个对象都会有一个管程(monitor)对象，管程(monitor)会随着 java 对象一同创建和销毁执行线程首先要持有管程对象，然后才能执行方法，当方法完成之后会释放管程，方法在执行时候会持有管程，其他线程无法再获取同一个管程

6.用户线程和守护线程

**用户线程:**平时用到的普通线程,自定义线程
**守护线程:**运行在后台,是一种特殊的线程,比如垃圾回收
当主线程结束后,用户线程还在运行,JVM 存活，如果没有用户线程,都是守护线程,JVM 结束

代码1：

public static void main(String[] args) {
   
    Thread aa = new Thread(() -> {
   
        System.out.println(Thread.currentThread().getName()+"用户线程");
        while(true){
   

        }
    }, "aa");
    aa.start();
    System.out.println(aa.isDaemon()+"==是否是守护线程");
    System.out.println(Thread.currentThread().getName()+"====over");
}

//输出如下，且服务未停止
false==是否是守护线程
main====over
aa用户线程

代码2：

public static void main(String[] args) {
   
    Thread aa = new Thread(() -> {
   
        System.out.println(Thread.currentThread().getName()+"用户线程"); 
        while(true){
   }
    }, "aa");
    aa.setDaemon(true);
    aa.start();
    System.out.println(aa.isDaemon()+"==是否是守护线程");
    System.out.println(Thread.currentThread().getName()+"====over");
}

//输出如下，且服务已停止
true==是否是守护线程
main====over
aa用户线程

2.LOCK接口

2.1 复习Synchronized

1.Synchronized作用范围

synchronized 是 Java 中的关键字，是一种同步锁。它修饰的对象有以下几种：

1. 修饰一个代码块，被修饰的代码块称为同步语句块，其作用的范围是大括号{}括起来的代码，作用的对象是调用这个代码块的对象；

synchronized(this){
   
}

2.  修饰一个方法，被修饰的方法称为同步方法，其作用的范围是整个方法，作用的对象是调用这个方法的对象；o 虽然可以使用 synchronized 来定义方法，但 synchronized 并不属于方法定义的一部分，因此，synchronized 关键字不能被继承。如果在父类中的某个方法使用了synchronized 关键字，而在子类中覆盖了这个方法，在子类中的这个方法默认情况下并不是同步的，而必须显式地在子类的这个方法中加上synchronized 关键字才可以。当然，还可以在子类方法中调用父类中相应的方法，这样虽然子类中的方法不是同步的，但子类调用了父类的同步方法，因此，子类的方法也就相当于同步了。 
3.  修改一个静态的方法，其作用的范围是整个静态方法，作用的对象是这个类的所有对象； 

4.  修改一个类，其作用的范围是 synchronized 后面括号括起来的部分，作用主的对象是这个类的所有对象。 

2.Synchronized实现卖票例子

3个售票员 卖出30张票

//1.定义资源，设置资源的操作方式
class Ticket{
   
    private int num=30;
    public synchronized void sale(){
   
        if(num>0){
   
            System.out.println(Thread.currentThread().getName()+"卖出了"+num--+",剩余:"+num);
        }
    }
}
public class SaleTicket {
   
    public static void main(String[] args) { 
   
        Ticket ticket = new Ticket();
        //2.创建多线程操作
        new Thread(new Runnable() {
   
            @Override
            public void run() {
   
                for (int i=0;i<30;i++){
   
                    ticket.sale();
                }
            }
        },"AA").start();

        new Thread(new Runnable() {
   
            @Override
            public void run() {
   
                for (int i=0;i<30;i++){
   
                    ticket.sale();
                }
            }
        },"BB").start();

        new Thread(new Runnable() {
   
            @Override
            public void run() {
   
                for (int i=0;i<30;i++){
   
                    ticket.sale();
                }
            }
        },"CC").start();
    }
}

3.多线程编程步骤（上)

1.第一 创建资源类，创建属性和操作方法

2.第二 创建多线程调用资源类的方法

2.2 什么是Lock接口

Lock 锁实现提供了比使用同步方法和语句可以获得的更广泛的锁操作。它们允许更灵活的结构，可能具有非常不同的属性，并且可能支持多个关联的条件对象。Lock 提供了比 synchronized 更多的功能。

Lock 与的 Synchronized 区别:

● Lock 不是 Java 语言内置的，synchronized 是 Java 语言的关键字，因此是内置特性。Lock 是一个类，通过这个类可以实现同步访问；
● Lock 和 synchronized 有一点非常大的不同，采用 synchronized 不需要用户去手动释放锁，当 synchronized 方法或者 synchronized 代码块执行完之后，系统会自动让线程释放对锁的占用；而 Lock 则必须要用户去手动释放锁，如果没有主动释放锁，就有可能导致出现死锁现象。

2.3 Lock实现可重入锁

ReentrantLock

2.4 创建线程的多种方式

●  1.继承Thread类 
●  2.实现Runnable接口 
●  3.使用Callable接口 
●  4.使用线程池 

2.5 使用Lock实现卖票

class LTicket{
   
    private Integer num = 30;
    //创建可重入锁
    ReentrantLock reentrantLock = new ReentrantLock();
    public void sale(){
   
        reentrantLock.lock();
        try{
   
            if(num>0){
   
                System.out.println(Thread.currentThread().getName()+"卖出了"+num--+"还剩下"+num);
            }
        }finally {
   
            reentrantLock.unlock();
        }
    }
}
public class SaleTicket {
   
    public static void main(String[] args) {
   
        LTicket lTicket = new LTicket();
        new Thread(()->{
   
            for (int i=0;i<30;i++){
   
                lTicket.sale();
            }
        },"AA").start();

        new Thread(()->{
   
            for (int i=0;i<30;i++){
   
                lTicket.sale();
            }
        },"BB").start();

        new Thread(()->{
   
            for (int i=0;i<30;i++){
   
                lTicket.sale();
            }
        },"CC").start();
    }
}

start()方法调用时候线程是否会立刻创建?不一定，start()方法源码如下，start0()中的native java代码无能为力，指令给操作系统，具体由操作系统决定。

class Thread implements Runnable {
   
    public synchronized void start() {
   
        /**
         * This method is not invoked for the main method thread or "system"
         * group threads created/set up by the VM. Any new functionality added
         * to this method in the future may have to also be added to the VM.
         *
         * A zero status value corresponds to state "NEW".
         */
        if (threadStatus != 0)
            throw new IllegalThreadStateException();

        /* Notify the group that this thread is about to be started
         * so that it can be added to the group's list of threads
         * and the group's unstarted count can be decremented. */
        group.add(this);

        boolean started = false;
        try {
   
            start0();
            started = true;
        } finally {
   
            try {
   
                if (!started) {
   
                    group.threadStartFailed(this);
                }
            } catch (Throwable ignore) {
   
                /* do nothing. If start0 threw a Throwable then
                  it will be passed up the call stack */
            }
        }
    }
    private native void start0();
}

Lock 和 synchronized 有以下几点不同：

1. Lock 是一个接口，而 synchronized 是 Java 中的关键字，synchronized 是内置的语言实现；
2. synchronized 在发生异常时，会自动释放线程占有的锁，因此不会导致死锁现象发生；而 Lock 在发生异常时，如果没有主动通过 unLock()去释放锁，则很
可能造成死锁现象，因此使用 Lock 时需要在 finally 块中释放锁；
3. Lock 可以让等待锁的线程响应中断，而 synchronized 却不行，使用synchronized 时，等待的线程会一直等待下去，不能够响应中断；4. 通过 Lock 可以知道有没有成功获取锁，而 synchronized 却无法办到。
4. Lock 可以提高多个线程进行读操作的效率。
在性能上来说，如果竞争资源不激烈，两者的性能是差不多的，而当竞争资源
非常激烈时（即有大量线程同时竞争），此时 Lock 的性能要远远优于
synchronized。

3.线程间通信

3.1多线程编程步骤（中)

第二步：在资源类操作方法：

1.判断

2.干活

3.通知

例：有两个线程

实现对一个初始值是0的变量

一个线程对值：+1

一个线程对值：-1

实现+1，-1，+1，-1的交替完成，这个过程就叫做线程间通信

public class Object {
   
    public final void wait() throws InterruptedException {
   
        wait(0);
    }
    public final native void notify();
    public final native void notifyAll();
}

线程间通信-synchronized实现：

class Share{
   
    //第1步:定义资源，资源的操作方法
    private int num = 0;
    //第2步:判断，干活，通知

    //生产
    public synchronized void incr() throws InterruptedException {
   
        //2.1 判断
        if(num!=0){
   
            this.wait();
        }
        //2.2 干活
        num++;
        System.out.println(Thread.currentThread().getName()+"==="+num);
        //2.3 通知
        this.notifyAll();
    }

    //消费
    public synchronized void decr() throws InterruptedException {
   
        //2.1 判断
        if(num!=1){
   
            this.wait();
        }
        //2.2 干活
        num--;
        System.out.println(Thread.currentThread().getName()+"==="+num);
        //2.3 通知
        this.notifyAll();
    }

}
public class ThreadDemo {
   
    public static void main(String[] args) {
   
        Share share = new Share();

        new Thread(() -> {
   
            try {
   
                for(int i=1;i<=10;i++){
   
                    share.incr();
                }
            } catch (InterruptedException e) {
   
                e.printStackTrace();
            }
        },"生产者").start();

        new Thread(() -> {
   
            try {
   
                for(int i=1;i<=10;i++){
   
                    share.decr();
                }
            } catch (InterruptedException e) {
   
                e.printStackTrace();
            }
        },"消费者").start();
    }
}

3.2 虚假唤醒问题

给上述线程间通信-synchronized实现各自多增加一个生产者和消费者，即会出现小于0或者大于1的情况出现，究其原因在于生产者1生产后，生产者2被唤醒并抢到资源，然后wait(),后生产者1又被唤醒，生产者2又被唤醒，因此从wait()地方醒来，继续执行后续逻辑，即产生超过1的数值，消费者小于0同理。

//生产
public synchronized void incr() throws InterruptedException {
   
    //2.1 判断
    while(num!=0){
   
        this.wait();
    }
    //2.2 干活
    num++;
    System.out.println(Thread.currentThread().getName()+"==="+num);
    //2.3 通知
    this.notifyAll();
}

//消费
public synchronized void decr() throws InterruptedException {
   
    //2.1 判断
    while(num!=1){
   
        this.wait();
    }
    //2.2 干活
    num--;
    System.out.println(Thread.currentThread().getName()+"==="+num);
    //2.3 通知
    this.notifyAll();
}

将上述判断中的if替换为while，即完成在唤醒后的再次判断。

3.3 多线程编程步骤（下)

第四步：防止虚假唤醒问题

3.4 多线程编程步骤总结：

●  1.第一步： 创建资源类，创建属性和操作方法 
●  2.第二步：在资源类操作方法： 
  ○ 1.判断
  ○ 2.干活
  ○ 3.通知
●  3.第三步： 创建多线程调用资源类的方法 
●  4.第二步：防止虚假唤醒问题 

线程间通信-lock实现：

//java.util.concurrent.locks包下Lock接口
public interface Lock {
   
    //获取锁
    void lock();
    //如果当前线程未被终端，则获取锁
    void lockInterruptibly() throws InterruptedException;
    //仅在调用时锁为空闲状态才获取该锁
    boolean tryLock();
    //如果锁在给定的等待时间内空闲，并且当前线程未被终端，则获取锁
    boolean tryLock(long time, TimeUnit unit) throws InterruptedException;
    //释放锁
    void unlock();
    //返回绑定到此Lock实例的新Condition实例
    Condition newCondition();
}

public interface Condition {
   
    //造成当前线程在接到信号或被中断之前一直处于等待状态
    void await() throws InterruptedException;
    //造成当前线程在接到信号之前一直处于等待状态
    void awaitUninterruptibly();
    //造成当前线程在接到信号、被终端或道道等待时间之前一直处于等待状态
    long awaitNanos(long nanosTimeout) throws InterruptedException;
    //造成当前线程在街道信号、被终端或道道指定等待事件之前一直处于等待状态
    boolean await(long time, TimeUnit unit) throws InterruptedException;
    //造成当前线程在接到信号、被终端或到达指定最后期限之前一直处于等待状态
    boolean awaitUntil(Date deadline) throws InterruptedException;
    //唤醒一个等待线程
    void signal();
    //唤醒所有等待线程
    void signalAll();
}

//1.定义资源，资源的操作
class Share{
   
    private int num=0;
    Lock lock = new ReentrantLock();
    Condition condition = lock.newCondition();
    //2.资源操作里，判断，干活，通知
    //+1
    public void incr() throws InterruptedException {
   
        lock.lock();
        try {
   
            //4.防止虚假唤醒
            while(num!=0){
   
                condition.await();
            }
            num++;
            System.out.println(Thread.currentThread().getName()+"::"+num);
            condition.signalAll();
        } finally {
   
            lock.unlock();
        }

    }
    //-1
    public void decr() throws InterruptedException {
   
        lock.lock();
        try{
   
             //4.防止虚假唤醒
            while (num!=1){
   
                condition.await();
            }
            num--;
            System.out.println(Thread.currentThread().getName(
