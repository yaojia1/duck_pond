package frame;

import units.impl.duck;
import units.impl.lily;

import java.io.IOException;

public class mainframe {
    public static void main(String[] args) throws IOException {
        MyFrame mm;
        lily ll= new lily();;
        duck dd =new duck();
        System.out.println(""+ll.x+ll.y+dd.x+dd.y);
        //mm = new MyFrame();
        //mm.setVisible(true);
    }
}
