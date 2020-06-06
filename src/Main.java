import units.impl.pond;

import java.io.IOException;
import java.util.concurrent.ExecutorService;

public class Main {
    //private lilypool lilies = new lilypool();
    //private duckpool duckpool=new duckpool();

    public Main() {
    }

    public static void main(String[] args) throws IOException {

            /**
             * 水草资源，一个线程造水草，线程池鸭子们吃水草
             */

        pond pond=new pond();
        ExecutorService runing = pond.run_pond();




        /**
        duckpool duckpond=new duckpool();
        duckpond.run();
        mm = new MyFrame(duckpond,duckpond.lilypool2);
        mm.setVisible(true);
        mm.run();
         */

        }




}
