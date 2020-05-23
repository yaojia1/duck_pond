package units;

import units.impl.duck;

public interface duck_model {
    Integer initwidth=50;
    Integer inithighth=50;

    double MAX_chiefsize = 2;
    double MAX_normalsize = 1.8;

    Integer speed = 2;

    long Time_to_eat=50000;


    /**
     * duck life circle
     * @return
     */
    boolean lose_weight();
    void grow();
    boolean death();
    boolean move();

    Integer getX();
    Integer getY();
    double getsize();
    Integer getpicsize();
    Integer getgoalpos();
    Integer getduckpos(duck dd);

    void setFollow_duck(duck follow_duck);
    void setgoal(Integer x , Integer y);

}
