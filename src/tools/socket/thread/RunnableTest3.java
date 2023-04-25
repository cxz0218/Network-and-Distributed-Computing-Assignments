package tools.socket.thread;
//学习链接：https://blog.csdn.net/fen_fen/article/details/121466128 解决多线程安全—synchronized

class RunnableTest3  implements Runnable{
    private int ticket =10;
    @Override
    public void run() {
        //for (int i = 0; i < 20; i++) {
        while (this.ticket > 0){
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized(this) {
                if (this.ticket > 0) {
                    System.out.println("票号:" + ticket-- + ",已被售卖，售卖窗口:" + Thread.currentThread().getName());
                }
                else {
                    System.out.println("票已卖完，没票可买了" + Thread.currentThread().getName());
                }
            }
        }

    }
}