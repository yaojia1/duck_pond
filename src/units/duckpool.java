package units;

import units.impl.duck;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public abstract class duckpool implements Runnable {}
    /**
     * list 所有的鸭子
     * chief_list排队的鸭子，第一个是坠大的鸭
     */
  /**  private ArrayList<duck> list = new ArrayList<duck>();
    private ReadWriteLock rwl = new ReentrantReadWriteLock();
    private Lock readLock = rwl.readLock();
    private Lock writeLock = rwl.writeLock();

    private ArrayList<duck> chief_list = new ArrayList<duck>();
    private ReadWriteLock rwchief = new ReentrantReadWriteLock();
    private Lock readchief = rwchief.readLock();
    private Lock writechief = rwchief.writeLock();

    public lilypool lilypool2 =new lilypool();


    @Override
    public void run() {
        //lilypool2.run();
        Thread lilypool1=new Thread(lilypool2);
        ExecutorService service = Executors.newFixedThreadPool(30);
        service.submit(lilypool1);
        Thread killduck = new Thread(()->{
            while (true){
                for(duck ducc :list){
                    if(!ducc.isalive){
                        move(ducc);
                        remove_queue(ducc);
                        ducc=null;
                        System.out.println("删除鸭鸭");
                    }
                }
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
                );
        service.submit(killduck);
        for (duck duckss : list) {
            service.submit(new Thread(duckss));
        }



    }



    public duckpool(){
        int n=0;
        duck tem;

        System.out.println("构造duckpool");
        do {
            tem=new duck();
            list.add(tem);
            tem.setChieflist(chief_list);
            tem.setList(lilypool2);
            System.out.println("duck "+n+" is created: "+list.get(n).x+","+list.get(n).y);
            n ++;
        } while (n <= 50);
        chief_list=null;
    }

    public List<duck> get_duckline(){
        readchief.lock();
        try {
            return chief_list;
        }
        finally {
            readchief.unlock();
        }
    }
    public duck getlistduck(int index) {
        readLock.lock();
        try {
            return list.get(index);
        }
        finally {
            readLock.unlock();
        }
    }

    public List<duck> getducks() {
        readLock.lock();
        try {
            return list;
        }
        finally {
            readLock.unlock();
        }
    }


    //  如果有最大的鸭子大于二倍size，返回true并创建队列


    public boolean findmaxsize(){
        Optional<duck> userOp= list.stream().max(Comparator.comparingInt(duck ::getsize));
        duck maxduck = userOp.get();
        chief_list.clear();
        if(maxduck.getsize()>2){
            createqueue(maxduck);
            return true;
        }else {
            return false;
        }
    }

    private void createqueue(duck chief) {
        writechief.lock();
        chief.inqueue=true;
        chief.ischief=true;
        //add_queue(chief);
        Integer xx=chief.x;
        Integer yy=chief.y;
        for (duck duc : list){
            duc.chief_x=xx;
            duc.chief_y=yy;
        }
        try {
            Collections.sort(list);

            for (duck duc : list){
                System.out.println(duc.x+","+duc.y+" at list pos:"+ list.indexOf(duc));

                 //* 跟随前一个鸭子

                add_queue(duc);
                duc.chief_x=xx;
                duc.chief_y=yy;
                duc.inqueue=true;
                xx=duc.x;
                yy=duc.y;
            }
        }finally {
            writechief.unlock();
        }

    }

    public void add_queue(duck e) {
        writechief.lock();
        try {
            chief_list.add(e);
        }
        finally {
            writechief.unlock();
        }
    }

    public void remove_queue(duck e){
        writechief.lock();
        try {
            chief_list.remove(e);
            e.inqueue=false;
            e.ischief=false;
        }
        finally {
            writechief.unlock();
        }
    }

    public void add(duck e) {
        writeLock.lock();
        try {
            list.add(e);
        }
        finally {
            writeLock.unlock();
        }
    }

    public void move(duck e){
        writeLock.lock();
        try {
            list.remove(e);
            e=null;
        }
        finally {
            writeLock.unlock();
        }
    }

}
*/