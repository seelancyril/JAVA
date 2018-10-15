package programs.concepts;

class MyThread extends Thread{
    public void run(){
        System.out.println("chils thread");
    }
}

public class DemoThreading {

    public static void main(String args[]){
        MyThread m = new MyThread();
        m.start();
    }

}
