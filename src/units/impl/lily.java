package units.impl;

import units.lilies;
import units.pond;

import java.util.Random;

public class lily implements lilies {
    private Random ra ;

    public Integer x;
    public Integer y;
    public lily(){
        ra =new Random();
        //System.out.println(ra.nextInt(10));
        x=ra.nextInt(800-width);
        y=ra.nextInt(800- heights);
    }

    public Integer getduckpos(duck dd){
        return Math.abs((x+width/2)-(dd.getX()+dd.width/2))+Math.abs((y+heights/2)-(dd.getY()+dd.higth/2));
    }

    @Override
    public void draw() {

    }

    @Override
    public void eat() {

    }
}
