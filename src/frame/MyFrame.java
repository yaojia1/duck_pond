package frame;

import units.impl.duck;
import units.impl.lily;
import units.impl.pond;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class MyFrame extends JFrame implements Runnable {

    public static final String TITLE = "duck pool";

    public static final int WIDTH = pond.pond_width;
    public static final int HEIGHT = pond.pond_highth;

    private Mpanel panel;


    private List<lily> lilylist;
    private List<duck> duckList;

    private pond ponds;
    //private AudioClip explode;

    private musicStuff musicObject;
    private String filepath;
    private String file_whistle;
    private musicStuff musicObject2;


    public MyFrame(pond apond) throws IOException {
        super();
        ponds=apond;
        this.lilylist=ponds.getlilies();
        this.duckList=ponds.getducks();
        initFrame();
    }


    private void initFrame() throws IOException {

        // 设置 窗口标题 和 窗口大小
        setTitle(TITLE);
        setSize(WIDTH, HEIGHT);

        // 设置窗口关闭按钮的默认操作(点击关闭时退出进程)
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // 把窗口位置设置到屏幕的中心
        setLocationRelativeTo(null);

        // 设置窗口的内容面板
        panel = new Mpanel(this);

        setContentPane(panel);
        filepath = "./music/1.wav";
        musicObject = new musicStuff();

        file_whistle="./music/whistle.wav";
        musicObject2 = new musicStuff();



        //explode = Applet.newAudioClip(getClass().getResource("./music/1.wav"));
    }

    @Override
    public void run() {
        while (true){

            this.lilylist=ponds.getlilies();
            this.duckList=ponds.getducks();
            panel.revalidate();
            panel.repaint();
            //System.out.println("重绘");
           // explode.play();

            musicObject.playMusic(filepath);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    public class Mpanel extends JPanel{
        private MyFrame frame;
        public Mpanel(MyFrame frame) {
            super();
            this.frame = frame;
        }

        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            validate();
            try {
                File folderInput = new File("E:\\projs\\Day3\\duck_pond\\src\\pic\\pond.png");

                BufferedImage img = ImageIO.read(folderInput);
                //int w = img.getWidth(null);
                //int h = img.getHeight(null);
                g.drawImage(img,0,0,MyFrame.WIDTH,MyFrame.HEIGHT, null);

                /**
                 * TODO 写循环遍历鸭子位置、大小、方向、是否chief
                 * TODO 水草同理
                 */

                duckList=ponds.getducks();
                for(duck dd : duckList){
                    if(dd.visible && dd.isalive){
                        drawImageduck(g,dd.x,dd.y,dd.width,dd.higth,dd.angle,dd.ischief);
                    }else {
                        System.out.println("死亡鸭鸭没有移除");
                        ponds.remove_duck(dd);
                    }
                    //System.out.println("ducks:paint"+dd.x+dd.y+dd.width+dd.higth);

                }

                lilylist=ponds.getlilies();
                for(lily ll :lilylist){
                    drawImagelily(g,ll.x,ll.y,ll.width,ll.heights);
                }
                /**
                lily ll;//= new lily();;
                duck dd ;//=new duck();
                //System.out.println(ll.x+ll.y+dd.x+dd.y);

                for (Integer i=0;i<50;i++){
                    ll= new lily();
                    dd=new duck();
                    System.out.println(ll.x+ll.y+dd.x+dd.y);
                    drawImageduck(g,dd.x,dd.y,dd.width,dd.higth,0,1);
                    drawImagelily(g,ll.x,ll.y);

                }
                drawImageduck(g,200,200,50,50,0,1);
                drawImagelily(g,355,355);
                drawImagelily(g,25,355);
                 */

                //drawImageduck2(g,250,250,50,50);
            } catch (IOException e) {
                e.printStackTrace();
            }
            /**
            try {
                g.setColor(Color.white);
                g.fillRect(0, 0, getWidth(), getHeight());
                g.setColor(Color.black);
                g.setFont(new Font("Dialog", Font.BOLD, 24));
                g.drawString("Java 2D is great!", 10, 80);
                drawImage(g);
            } catch (IOException e) {
                e.printStackTrace();
            }*/
        }

        private void drawImageduck(Graphics g, Integer x, Integer y, Integer w, Integer h, Integer angel, boolean ischief) throws IOException {
            File folderInput = null;
            if(ischief){
                musicObject2.playMusic(file_whistle);
                drawString(g,x,y-5);
                if(angel==180){
                    folderInput = new File("E:\\projs\\Day3\\duck_pond\\src\\pic\\chief_r.png");
                }else if (angel==0){
                    folderInput = new File("E:\\projs\\Day3\\duck_pond\\src\\pic\\chief.png");
                }else if (angel==90){
                    folderInput = new File("E:\\projs\\Day3\\duck_pond\\src\\pic\\chief_0.png");
                }else {
                    folderInput = new File("E:\\projs\\Day3\\duck_pond\\src\\pic\\chief_1.png");
                }

            }else{
                if(angel==180){
                    folderInput = new File("E:\\projs\\Day3\\duck_pond\\src\\pic\\duck_r.png");
                }else if (angel==0){
                    folderInput = new File("E:\\projs\\Day3\\duck_pond\\src\\pic\\duck.png");
                }else if (angel==90){
                    folderInput = new File("E:\\projs\\Day3\\duck_pond\\src\\pic\\duck_0.png");
                }else {
                    folderInput = new File("E:\\projs\\Day3\\duck_pond\\src\\pic\\duck_1.png");
                }
            }


            BufferedImage img = ImageIO.read(folderInput);

            //g.drawImage(img,x,y,w,h,null);
            g.drawImage(img,x,y,w,h,null);
            /**
            if(angel!=0){
                BufferedImage des1 = Rotate(img, angel);
                g.drawImage(des1,x,y+50,w,h,null);
            }else {
                g.drawImage(img,x,y,w,h,null);
            }*/
        }

        private void drawImagelily(Graphics g,Integer x,Integer y, Integer w, Integer h) throws IOException {
            File folderInput = new File("E:\\projs\\Day3\\duck_pond\\src\\pic\\lily.png");
            BufferedImage img = ImageIO.read(folderInput);

            g.drawImage(img,x,y, w,h ,null);

        }

        private void drawString(Graphics g,Integer x,Integer y) {
            Graphics2D g2d = (Graphics2D) g.create();

            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // 设置字体样式, null 表示使用默认字体, Font.PLAIN 为普通样式, 大小为 25px
            g2d.setFont(new Font(null, Font.PLAIN, 10));

            // 绘制文本, 其中坐标参数指的是文本绘制后的 左下角 的位置
            // 首次绘制需要初始化字体, 可能需要较耗时
            g2d.drawString("Whistling!", x, y);

            g2d.dispose();
        }


        public BufferedImage Rotate(Image src , int angel) {
            int src_width = src.getWidth(null);
            int src_height = src.getHeight(null);
            // 计算旋转后图片的尺寸
            Rectangle rect_des = CalcRotatedSize(new Rectangle(new Dimension(
                    src_width, src_height)), angel);
            BufferedImage res = null;
            res = new BufferedImage(rect_des.width, rect_des.height,
                    BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2 = res.createGraphics();
            // 进行转换
            g2.translate((rect_des.width - src_width) / 2,
                    (rect_des.height - src_height) / 2);
            g2.rotate(Math.toRadians(angel), src_width / 2, src_height / 2);

            g2.drawImage(src, null, null);
            return res;
        }

        public Rectangle CalcRotatedSize(Rectangle src , int angel){
            // if angel is greater than 90 degree,we need to do some conversion.
            if(angel > 90){
                if(angel / 9%2 ==1){
                    int temp = src.height;
                    src.height = src.width;
                    src.width = temp;
                }
                angel = angel % 90;
            }

            double r = Math.sqrt(src.height * src.height + src.width * src.width ) / 2 ;
            double len = 2 * Math.sin(Math.toRadians(angel) / 2) * r;
            double angel_alpha = (Math.PI - Math.toRadians(angel)) / 2;
            double angel_dalta_width = Math.atan((double) src.height / src.width);
            double angel_dalta_height = Math.atan((double) src.width / src.height);

            int len_dalta_width = (int) (len * Math.cos(Math.PI - angel_alpha
                    - angel_dalta_width));
            int len_dalta_height = (int) (len * Math.cos(Math.PI - angel_alpha
                    - angel_dalta_height));
            int des_width = src.width + len_dalta_width * 2;
            int des_height = src.height + len_dalta_height * 2;
            return new java.awt.Rectangle(new Dimension(des_width, des_height));
        }


    }

}
