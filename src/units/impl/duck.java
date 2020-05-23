package units.impl;

import units.duck_model;

import java.util.Random;

//TODO 把compareable删了
public class duck implements duck_model {//,Runnable {
    //位置
    private Random ra = new Random();
    public Integer x = 0;
    public Integer y = 0;


    public Integer chief_x = x;
    public Integer chief_y = y;
    //有无chief
    public boolean inqueue = false;
    //是否还活着
    public boolean isalive = true;
    //大小 = size*w，h
    public double size = 1;
    public Integer width;
    public Integer higth;
    //是否是队长
    public boolean ischief = false;
    //移动角度
    public Integer angle = 0;
    //上次进食时间
    long lasteat_time;
    //子线程
    //删除

 /**private duckmove selfmove;
    //食物
    private lilypool list;
    //领队
    private List<duck> chieflist;
  */
    public boolean visible = false;

    public duck() {
        x = ra.nextInt(800 - initwidth);
        y = ra.nextInt(800 - inithighth);
        width = initwidth;
        higth = inithighth;
        lasteat_time = System.currentTimeMillis();
        System.out.println("duck is created");
    }

    public Integer getX() {
        return x;
    }

    public Integer getY() {
        return y;
    }

    public Integer getgoalpos() {
        return Math.abs(x - chief_x) + Math.abs(y - chief_y);
    }

    public Integer getduckpos(duck dd) {
        return Math.abs(x - dd.getX()) + Math.abs(y - dd.getY());
    }


    public double getsize() {
        return size;
    }

    public void grow() {

        if(ischief){
            if(size<=MAX_chiefsize){
                size += 0.1;
            }
        }else {
            if(size<=MAX_normalsize){
                size += 0.1;
            }
        }
        width = (int) (initwidth * size);
        higth = (int) (inithighth * size);
        lasteat_time = System.currentTimeMillis();
        System.out.println("duck growed 0.1 , width:" + width + ", highth: " + higth);
    }

    public boolean lose_weight() {
        //TODO 修改不进食时间
        if (System.currentTimeMillis() - lasteat_time > Time_to_eat) {
            size -= 0.1;
            lasteat_time = System.currentTimeMillis();
            System.out.println("lose weight");
        }
        if (size < 1) {
            return death();

        } else {
            return true;
        }
    }

    public boolean death() {
        isalive = false;
        visible = false;
        ischief=false;
        inqueue=false;
        return false;
    }

    public boolean move() {
        //根据角度修改xy
        double ssqrt = Math.sqrt(Math.pow(chief_x - x , 2) + Math.pow(chief_y - y , 2));
        double cos = (chief_x - x) / ssqrt;
        double sin = (chief_y - y) / ssqrt;
        //System.out.println("移动前" + x + "," + y);
        int mmove = (int) Math.abs(speed * cos + speed * sin);


        if(mmove<getgoalpos()){
            if (!inqueue || ischief||getgoalpos()>=initwidth){
                x = (int) (x + speed * cos);
                y = (int) (y + speed * sin);
            }

            if (cos > 0.75) {
                angle = 180;
            } else if (cos<-0.75){
                angle = 0;
            }else if (sin>=0.75){
                angle = 90;
            }else if(sin<-0.75){
                angle = -90;
            }

        }else {
            if (!inqueue || ischief){
                x=chief_x;
                y=chief_y;
            }
        }
        return mmove==0;
        //System.out.println("移动后" + x + "," + y);
        //System.out.println("移动方向" + speed * cos + "," + speed * sin);
    }
    public void setFollow_duck(duck follow_duck) {
        chief_x=follow_duck.getX();
        chief_y=follow_duck.getY();
        //this.follow_duck = follow_duck;
    }

    public void setgoal(Integer x , Integer y) {
        this.chief_x=x;
        this.chief_y=y;
    }

    public Integer getpicsize() {
        return width;
    }
}
    /**@Override
    public int compareTo(Object o) {
        return (Math.abs(this.getpos()-chief_x-chief_y) <Math.abs( ((duck) o).getpos() -chief_x-chief_y)? 1 :
                (Math.abs(this.getpos()-chief_x-chief_y) == Math.abs( ((duck) o).getpos() -chief_x-chief_y) ? 0 : 1));

    }*/

    /**
    @Override
    public void run() {
        visible=true;
        System.out.println("duck outer method run");
        Thread selfmove = new Thread(new duckmove()) ;
        selfmove.start();
        try {
            selfmove.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        visible=false;
    }

    public void setChieflist(List<duck> chieflist) {
        this.chieflist = chieflist;
    }

    public void setList(lilypool list) {
        this.list = list;
    }

   public duck getFollow_duck() {
        return follow_duck;
    }



    /**TODO：
     * 本线程run里写根据时间减小size
     *
     * 找寻有没有chief
     * 有chief目标为chief 的 queue的末尾，谁先追上谁先加入然后目标变成最后一位的目标
     * 没有chief寻找最近的莲花，设为目标
     * 向目标移动修改xy坐标
     * 如果周围有莲花，调用eat子线程移除莲花
     * 然后调用grow子进程改变大小
     *
     * 然后沉睡
     */

    /**class duckmove implements Runnable {



        private Integer goal_x=x;
        private Integer goal_y=y;
        private List<lily> listlily=new ArrayList<>();
        private lilypool lilies;
        private List<duck> listduck=new ArrayList<>();

        public duckmove() {
            this.listlily =  list.get();
            this.lilies=list;
            this.listduck=chieflist;
        }

        @Override
        public void run() {
            Integer xx=800;
            Integer yy=800;
            Integer index=0;
            System.out.println("duck inner method run");
            while (true){
                if(ischief || !inqueue){
                    listlily=list.get();
                    for(lily lily : listlily) {
                        if (xx + yy > Math.abs(lily.x - x + lily.y - y)) {
                            xx = Math.abs(lily.x - x);
                            yy = Math.abs(lily.y - y);
                            index = listlily.indexOf(lily);
                        }
                    }
                    System.out.println("eat "+xx+","+yy);
                    //eat
                    if(xx+yy<50){
                        System.out.println("eat !!!!!!!!"+list.getlily(index).x+", "+list.getlily(index).y);
                        list.move(list.getlily(index));
                        grow();
                        continue;
                    }
                    //move
                    if(index!=0 && xx+yy!=1600){
                        chief_x=listlily.get(index).x;
                        chief_y=listlily.get(index).y;
                        System.out.println("移动"+chief_x+","+chief_y);
                    }
                    move();
                    if(!lose_weight()){
                        System.out.println("duck died!");
                        break;
                    }
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                else if(inqueue){

                    int pos=this.listduck.indexOf(this);
                    if(pos!=0){
                        chief_x=listduck.get(pos-1).x;
                        chief_y=listduck.get(pos-1).y;
                    }
                    for(lily lily : listlily) {
                        if (xx + yy > lily.x - x + lily.y - y) {
                            xx = lily.x - x;
                            yy = lily.y - y;
                            index = listlily.indexOf(lily);
                        }
                    }
                    //eat
                    if(xx+yy<50){
                        System.out.println("eat a lily");
                        lilies.remove(index);
                        grow();
                        continue;
                    }
                    move();
                    if(!lose_weight()){
                        System.out.println("duck died!");
                        break;
                    }
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                else {
                    System.out.println("duck ??????");
                    break;
                }
            }

            System.out.println("死了！！！！！！！！！！！！！！");

        }
    }
    /**
    @Override
    public void eat() {

    }

    @Override
    public void birth() {

    }

    @Override
    public void grow() {

    }

    @Override
    public void death() {

    }

    @Override
    public void move() {

    }

    @Override
    public void draw() {

    }
}*/
