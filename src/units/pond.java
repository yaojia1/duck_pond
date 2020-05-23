package units;

import frame.MyFrame;
import units.impl.duck;
import units.impl.lily;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class pond {

    public static Integer pond_width=800;
    public static Integer pond_highth=800;

    ExecutorService service;

    private static Integer lily_amount = 15;
    private static Integer duck_amount = 10;

    /**
     * duck
     */
    public duck has_chief=null;

    private List<duck> duck_list = new ArrayList<duck>();
    private ReadWriteLock rwl = new ReentrantReadWriteLock();
    private Lock duck_readLock = rwl.readLock();
    private Lock duck_writeLock = rwl.writeLock();

    /**
     * lily
     */
    private List<lily> lily_list;

    //private ArrayList<duck> chief_list = new ArrayList<duck>();
    //private ReadWriteLock rwchief = new ReentrantReadWriteLock();
    //private Lock readchief = rwchief.readLock();
    //private Lock writechief = rwchief.writeLock();

    public pond(){
        int n=0;
        lily_list=new ArrayList<lily>();
        System.out.println("pond init \n构造lily :");
        do {
            lily_list.add(new lily());
            System.out.println("lily "+n+" is created: "+lily_list.get(n).x+","+lily_list.get(n).y);
            n ++;
        } while (n <= lily_amount);

        //鸭鸭在线程里创造，run()里添加到list
    }

    public ExecutorService run_pond() throws IOException {
        MyFrame mm = new MyFrame(this);
        mm.setVisible(true);
        Thread frame = new Thread(mm);

        Thread lilymang=new Thread(new lilymange());
        lilymang.setDaemon(true);

        Thread duckmang = new Thread(new duckmanage());
        duckmang.setDaemon(true);

        service = Executors.newFixedThreadPool(duck_amount);

        //service.submit(frame);
        //service.submit(lilymang);
        //service.submit(duckmang);

        frame.start();
        lilymang.start();
        duckmang.start();

        Thread duckth;
        int n=0;
        duck new_duck;
        while (n<duck_amount){
            new_duck=new duck();
            //add_duck(new_duck);
            new_duck.visible=false;
            duckth=new Thread(new ducklife(new_duck));
            service.submit(duckth);
            System.out.println("111111111111111111111111");
            n+=1;
        }

        //lilymang.start();
        //duckmang.start();
        return service;
    }

    /**
     * @return duck列表
     */

    public List<duck> getducks() {
        return duck_list;
    }

    public List<lily> getlilies() {
        return lily_list;
    }

    public void add_duck(duck e) {
        duck_writeLock.lock();
        try {
            duck_list.add(e);
        }
        finally {
            duck_writeLock.unlock();
        }
    }

    public void remove_duck(duck e){
        duck_writeLock.lock();
        try {
            duck_list.remove(e);
            //e=null;
        }
        finally {
            duck_writeLock.unlock();
        }
    }

    public void sort_lily(){

    }

    public void sort_duck(duck chief_duck){
        /**
         * duck对于队长鸭的相对距离升序排序
         */
        duck finalChief_duck = chief_duck;
        Collections.sort(duck_list, new Comparator<duck>() {
            @Override
            public int compare(duck o1 , duck o2) {
                return o1.getduckpos(finalChief_duck).compareTo(o2.getduckpos(finalChief_duck));
            }
        });

        //把后一个鸭的目标鸭设为list里的前一个

        for (duck duckc : duck_list){
            if(duckc==chief_duck){
                continue;
            }
            duckc.setFollow_duck(chief_duck);
            duckc.inqueue=true;
            duckc.ischief=false;
            chief_duck=duckc;
        }

    }

    //产生鸭鸭领袖list
    public void create_chieflist(duck chief_duck){
        duck_writeLock.lock();
        has_chief=chief_duck;
        chief_duck.ischief=true;
        chief_duck.inqueue=true;
        has_chief=chief_duck;
        try {
            System.out.println("new duck chief! size:" + chief_duck.getsize());
            sort_duck(chief_duck);
        }finally {
            duck_writeLock.unlock();
        }

    }
    //删除鸭鸭队列

    private void remove_queue() {
        duck_writeLock.lock();
        try {
            for (duck ducc:duck_list){
                ducc.inqueue=false;
                ducc.ischief=false;
            }
            has_chief=null;
        }finally {
            duck_writeLock.unlock();
        }

    }

    //最大的鸭

    public duck find_maxduck(){
        duck_readLock.lock();
        duck duck_chief=Collections.max(duck_list , new Comparator<duck>() {
            @Override
            public int compare(duck o1 , duck o2) {
                return o1.getpicsize().compareTo(o2.getpicsize());
            }
        });
        duck_readLock.unlock();
        return duck_chief;
    }

    //寻找duck最近的Lily

    public lily find_nearlily(duck duck_near){
        duck_readLock.lock();
        duck duck_eat=duck_near;
        try {
            lily lily_near=Collections.min(lily_list,new Comparator<lily>(){
                @Override
                public int compare(lily o1 , lily o2) {
                    return o1.getduckpos(duck_eat).compareTo(o2.getduckpos(duck_eat));
                }
            });
            return lily_near;
        }finally {
            duck_readLock.unlock();
        }
    }

    public void remove_lily(lily e){
        //duck_writeLock.lock();
        try {
            System.out.println("eat !!!!!!!!"+e.x+", "+e.y);
            lily_list.remove(e);
        }
        finally {
            //duck_writeLock.unlock();
        }
    }

    public void add_lily(lily lily_new){
        duck_writeLock.lock();
        try {
            lily_list.add(lily_new);
        }finally {
            duck_writeLock.unlock();
        }
    }

    //鸭鸭吃Lily,输入最近的Lily，修改Lily表


    public boolean duck_eatlily(duck duck_eat,lily lily_eat){
        duck_writeLock.lock();
        try {
            if(lily_eat.getduckpos(duck_eat)<=(lily_eat.width+lily_eat.heights+duck_eat.width+duck_eat.higth)/3){
                try {
                    remove_lily(lily_eat);
                    duck_eat.grow();
                    return true;

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            else {
                return false;
            }
        }finally {
            duck_writeLock.unlock();
        }
        return false;

    }

    public void removediedduck(){
        //duck_writeLock.lock();
        try {
            for (duck duckc : duck_list){
                if(!duckc.isalive || !duckc.visible){
                    if(has_chief==duckc){
                        has_chief=null;
                    }
                    duckc.visible=false;
                    //remove_duck(duckc);
                    System.out.println("死亡鸭鸭??");
                    continue;
                }
            }
        }catch (Exception e){
            System.out.println("移除死亡鸭鸭失败。。。。。。。。。。。。。");
            e.printStackTrace();

        }
        finally {
            //duck_writeLock.unlock();
        }
    }

    public void updategoal(){
        duck_writeLock.lock();
        try {
            duck duckpass;
            duckpass=has_chief;
            for (duck duckc : duck_list){
                if(duckc==has_chief){
                    continue;
                }
                duckc.setFollow_duck(duckpass);
                duckc.inqueue=true;
                duckc.ischief=false;
                duckpass=duckc;
            }
        }finally {
            duck_writeLock.unlock();
        }
    }
    private void updategoal(duck new_duck) {
        duck_readLock.lock();
        try {
            new_duck.setFollow_duck(duck_list.get(duck_list.indexOf(new_duck)-1));
        }finally {
            duck_readLock.unlock();
        }
    }




    /**
     * lily

    private List<lily> lily_list;
    private ReadWriteLock rwl2 = new ReentrantReadWriteLock();
    private Lock lily_readLock = rwl2.readLock();
    private Lock lily_writeLock = rwl2.writeLock();
*/



    /**
     * 水仙池
     */
    public class lilymange implements Runnable{

        /**
         * 500后检查lily数量是否小于
         */
        @Override
        public void run() {
            while (true){
                if(lily_list.size()<lily_amount){
                    add_lily(new lily());
                    //System.out.println("lily is created!");
                }
                try {
                    //System.out.println("lily pool 线程睡眠");
                    Thread.sleep(1000);
                    //System.out.println("lily pool 线程唤醒");
                } catch (InterruptedException e) {
                    System.out.println("lily pool 线程错误");
                    e.printStackTrace();
                }

            }
        }


    }

    public class duckmanage implements Runnable{

        duck duck_leader;


        @Override
        public void run() {
            //管理所以鸭鸭list

            has_chief=null;
            while (true){
                System.out.println("管理鸭鸭线程running");
                //removediedduck();
                if(duck_list.size()>4 && has_chief==null){

                    duck_leader=find_maxduck();
                    if(duck_leader.getsize()>=duck.MAX_normalsize){
                        if (!duck_leader.ischief){
                            create_chieflist(duck_leader);
                        }

                    }else if (duck_leader.inqueue){
                        //太小
                        //System.out.println("DSFRCCFVSCGGDSCDcxdv");
                        remove_queue();
                    }
                }else {
                    Thread duckth;
                    int n=duck_list.size();
                    duck new_duck;
                    while (n<duck_amount){
                        System.out.println("补充鸭鸭");
                        new_duck=new duck();

                        new_duck.visible=false;
                        duckth=new Thread(new ducklife(new_duck));
                        service.submit(duckth);
                        n+=1;
                    }
                }

                if(has_chief!=null){
                    updategoal();
                }

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    System.out.println("管理鸭鸭线程错误");
                    e.printStackTrace();
                }
            }
        }
    }



    public class ducklife implements Runnable {
        duck new_duck;

        public ducklife(duck duckk){
            this.new_duck=duckk;
        }

        @Override
        public void run() {
            duckinit();
            Random ra =new Random();
            int nn=0;
            lily nearlily = find_nearlily(new_duck);
            if(duck_eatlily(new_duck,nearlily)){
                nearlily = find_nearlily(new_duck);
            }
            new_duck.setgoal(nearlily.x,nearlily.y);
            while (true){

                if(new_duck.visible && new_duck.isalive){
                    if(!new_duck.lose_weight()){
                        break;
                    }
                    if(!new_duck.isalive){
                        break;
                    }

                    if (new_duck.move()){
                        if (new_duck.inqueue && !new_duck.ischief){
                            updategoal(new_duck);
                        }else {
                            nearlily = find_nearlily(new_duck);
                            if(duck_eatlily(new_duck,nearlily)){
                                nearlily = find_nearlily(new_duck);
                            }
                            new_duck.setgoal(nearlily.x,nearlily.y);
                            /**if (new_duck.move()){
                                System.out.println("出错了，鸭鸭动不了");
                            }*/
                            nn=1;
                        }
                    }

                    /**
                     * no chief
                     */
                    if(nn%10==0){
                        if(has_chief==null || new_duck.ischief){
                            nearlily = find_nearlily(new_duck);
                            if(duck_eatlily(new_duck,nearlily)){
                                nearlily = find_nearlily(new_duck);
                            }
                            new_duck.setgoal(nearlily.x,nearlily.y);
                        }else {
                            nearlily = find_nearlily(new_duck);
                            duck_eatlily(new_duck,nearlily);
                        }


                    }

                    if(new_duck.getgoalpos()==0){
                        new_duck.setgoal(ra.nextInt(800-new_duck.width),ra.nextInt(800-new_duck.higth));
                    }

                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        System.out.println("鸭鸭失眠....");
                        e.printStackTrace();
                    }

                    /**
                     * 管理鸭鸭线程里  挑选
                     */
                }else {
                    break;
                }
                nn+=1;

            }
            /**
             * 结束自删
             */
            if (new_duck==has_chief){
                has_chief=null;

            }
            new_duck.inqueue=false;
            new_duck.visible=false;
            new_duck.ischief=false;
            new_duck.isalive=false;
            try {
                remove_duck(new_duck);
            }catch (Exception e){
                System.out.println("鸭鸭删除失败！！！！！！\n！！！！！！！！！！！！！！！！！！！！！");
            }
            //
            System.out.println("鸭鸭死亡");
        }



        private void duckinit() {
            //new_duck=new duck();
            add_duck(new_duck);
            new_duck.visible=true;

        }
    }



}
