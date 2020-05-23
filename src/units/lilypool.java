package units;

import units.impl.lily;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class lilypool implements Runnable{
    private List<lily> list = new ArrayList<lily>();
    private ReadWriteLock rwl = new ReentrantReadWriteLock();
    private Lock readLock = rwl.readLock();
    private Lock writeLock = rwl.writeLock();
    public lilypool(){
        System.out.println("构造Lilypool");
        Integer n=0;
        do {
            list.add(new lily());
            System.out.println("lily is created!"+n+list.get(n).x+list.get(n).y);
            n ++;
        } while (n <= 30);
    }

    @Override
    public void run() {
        while (true){
            if(list.size()<30){
                add(new lily());
                System.out.println("lily is created!");
            }
            try {
                //System.out.println("lily pool 线程睡眠");
                Thread.sleep(500);
                //System.out.println("lily pool 线程唤醒");
            } catch (InterruptedException e) {
                System.out.println("lily pool 线程错误");
                e.printStackTrace();
            }

        }
    }

    public lily getlily(Integer index){
        readLock.lock();
        try {
            return list.get(index);
        }
        finally {
            readLock.unlock();
        }
    }
    public List<lily> get() {
        readLock.lock();
        try {
            return list;
        }
        finally {
            readLock.unlock();
        }
    }

    public void add(lily e) {
        writeLock.lock();
        try {
            list.add(e);
        }
        finally {
            writeLock.unlock();
        }
    }
    
    public void move(lily e){
        writeLock.lock();
        try {
            System.out.println("eat !!!!!!!!"+e.x+", "+e.y);
            list.remove(e);
        }
        finally {
            writeLock.unlock();
        }
    }

    public void remove(Integer index){
        writeLock.lock();
        try {
            System.out.println("eat !!!!!!!!"+list.get(index).x+", "+list.get(index).y);

            list.remove(index);
            System.out.println("eat !!!!!!!!"+list.get(index).x+", "+list.get(index).y);
        }
        finally {
            writeLock.unlock();
        }
    }

}
