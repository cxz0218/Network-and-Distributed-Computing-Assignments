package socket.thread;
//学习链接：https://blog.csdn.net/fen_fen/article/details/121461905 实现Runnable接口方式实现多线程

public class RunnableDemo3 {
    public static void main(String[] args){
        RunnableTest3 rt=new RunnableTest3();
        Thread t1 = new Thread(rt,"一号窗口");//在创建Thread 对象的时候可以为该Thread对象指定一个名字
        Thread t2 = new Thread(rt,"二号窗口");
        Thread t3 = new Thread(rt,"三号窗口");
        t1.start();
        t2.start();
        t3.start();
    }
}
